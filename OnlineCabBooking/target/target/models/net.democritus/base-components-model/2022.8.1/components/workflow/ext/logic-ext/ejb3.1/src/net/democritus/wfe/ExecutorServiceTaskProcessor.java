package net.democritus.wfe;

import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.TaskResult;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.RunId;
import net.democritus.workflow.StateTaskDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ExecutorServiceTaskProcessor<T> implements TaskProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorServiceTaskProcessor.class);

  private static String executorServiceJndiName = null;

  private RunId batchId;
  private int poolSize;

  private final FlowResult<T> flowResult = FlowResult.success(null);
  private final AtomicInteger tasksRunning = new AtomicInteger();
  private final AtomicReference<ProcessorState> state = new AtomicReference<>();
  private final Set<DataRef> registeredJobs = Collections.synchronizedSet(new HashSet<>());
  private final Set<DataRef> completeJobs = Collections.synchronizedSet(new HashSet<>());
  private final Set<TaskJob> taskJobs = Collections.synchronizedSet(new HashSet<TaskJob>());


  private CompletionService<Void> completionService;

  private long startTime;
  private WorkflowParameterContextFactory parameterContextFactory;

  private List<StateTaskDetails> taskChain;


  @Override
  public void init(ParameterContext<BatchDescription> batchParameter) {
    Context context = batchParameter.getContext();
    state.set(ProcessorState.RUNNING);
    tasksRunning.set(0);
    registeredJobs.clear();
    completeJobs.clear();
    taskJobs.clear();

    BatchDescription batchDescription = batchParameter.getValue();
    this.taskChain = batchDescription.getTaskChain();
    batchId = batchDescription.getRunId();
    poolSize = getMaxNumberOfConcurrentTasks();

    ExecutorService executorService = getExecutorService(context);
    completionService = new ExecutorCompletionService<>(executorService);

    parameterContextFactory = new WorkflowParameterContextFactory(
        batchParameter.construct(batchDescription.getWorkflow()),
        batchDescription.getTaskChain(),
        batchId
    );
    parameterContextFactory.init();

    startTime = System.nanoTime();
  }

  private static ExecutorService getExecutorService(Context context) {
    if (executorServiceJndiName == null) {
      ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(context);
      executorServiceJndiName = paramTargetValueAgent.getParamTargetValue("workflow.executorService.jndiName", "ExecutorServiceTaskProcessor");
      if (executorServiceJndiName.isEmpty()) {
        executorServiceJndiName = "openejb:Resource/executorService";
      }
    }

    try {
      InitialContext initialContext = new InitialContext();
      return (ExecutorService) initialContext.lookup(executorServiceJndiName);
    } catch (NamingException e) {
      String jndiName = executorServiceJndiName;
      executorServiceJndiName = null;
      throw new IllegalStateException("Failed to find executorService, jndiName=" + jndiName +
          ". Please set the correct jndi name in a paramTargetValue with paramName=`workflow.executorService.jndiName`" +
          " and target=`ExecutorServiceTaskProcessor`", e);
    }
  }

  @Override
  public synchronized void processTask(DataRef target) {
    processTask(target, null);
  }

  /**
   * Add a new task. Waits if pool is full.
   *
   * @param target the target instance for the task
   */
  @Override
  public synchronized void processTask(DataRef target, Long timeout) {
    if (state.get() == ProcessorState.STOPPED) {
      throw new ProcessingException(batchId + ": Cannot register new tasks, processor has already been stopped");
    }

    LOGGER.debug(batchId + ": registering new task, target=" + target.getId());
    try {
      waitForFreeTaskJobSlot(target);
      tasksRunning.incrementAndGet();
      startTaskJob(target, timeout);
      registeredJobs.add(target);
      LOGGER.debug(batchId + ": task running, target=" + target.getId());
    } catch (Exception e) {
      throw new ProcessingException(batchId + ": Could not process task due to exception", e);
    }
  }

  private void waitForFreeTaskJobSlot(DataRef target) {
    long start = System.currentTimeMillis();
    if (tasksRunning.get() < poolSize) {
      return;
    }

    LOGGER.debug(batchId + ": checked all running tasks, target=" + target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");

    start = System.currentTimeMillis();
    LOGGER.debug(batchId + ": pool is full, target=" + target.getId() + ", timeSinceStart=" + (System.nanoTime() - startTime) / 1000000D + "ms");

    while (tasksRunning.get() >= poolSize) {
      waitForCompletedTask();
    }
    LOGGER.debug(batchId + ": wait ended, target=" + target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");
    startTime = System.nanoTime();
  }

  /**
   * Take all tasks that have been completed up to now.
   *
   * @return a list of targets that have been completed
   */
  public synchronized List<DataRef> takeCompletedTasks() throws ProcessingException {
    List<DataRef> completedTasks = new ArrayList<>(completeJobs);
    completeJobs.clear();
    taskJobs.removeIf(taskJob -> taskJob.future.isDone());
    return completedTasks;
  }


  /**
   * Checks if there are still tasks that are being processed or have been processed.
   *
   * @return true iff there are some tasks that are busy or completed
   */
  public boolean hasMoreTasks() throws ProcessingException {
    return !completeJobs.isEmpty() || tasksRunning.get() > 0;
  }

  private void cancelExpiredTasks() {
    for (TaskJob taskJob : taskJobs) {
      if (taskJob.isExpired() && !taskJob.future.isCancelled() && !taskJob.future.isDone()) {
        taskJob.future.cancel(true);
        LOGGER.warn(batchId + ": cancelled expired task");
      }
    }
  }

  /**
   * Returns the target of all tasks that has been completed.
   * Can block if no result is available.
   *
   * @return the targets of the completed tasks
   */
  public synchronized List<DataRef> takeNextCompletedTasks() throws ProcessingException {
    try {
      cancelExpiredTasks();

      if (!completeJobs.isEmpty()) {
        return takeCompletedTasks();
      }

      if (tasksRunning.get() == 0) {
        throw new ProcessingException("no more results");
      }

      long start = System.currentTimeMillis();
      LOGGER.debug(batchId + ": waiting for any task to finish");
      waitForCompletedTask();
      LOGGER.debug(batchId + ": wait ended, duration=" + (System.currentTimeMillis() - start) + "ms");

      return takeCompletedTasks();
    } catch (Exception e) {
      throw new ProcessingException("Could not retrieve next task", e);
    }
  }

  /**
   * Wait for a task to complete
   * Use `completionService.take()` if all tasks have no timeout set
   * Use `completionService.poll(timeout, ms) with the minimum remaining time otherwise
   */
  private void waitForCompletedTask() {
    try {
      Future<Void> result = null;
      while (result == null) {
        cancelExpiredTasks();
        Optional<Long> optionalMinRemainingTime = taskJobs.stream()
            .map(TaskJob::getRemainingTime)
            .filter(Objects::nonNull)
            .min(Long::compareTo);
        if (optionalMinRemainingTime.isPresent()) {
          result = completionService.poll(optionalMinRemainingTime.get(), TimeUnit.MILLISECONDS); // returns null on timeout
        } else {
          result = completionService.take();
        }
      }
    } catch (Exception e) {
      LOGGER.debug(batchId + ": Waiting resulted in exception", e);
    }
  }

  public void cancelRunningTasks() {
    state.set(ProcessorState.STOPPED);
    int nbTasksCancelled = 0;
    Future<Void> nextResult;
    while ((nextResult = completionService.poll()) != null) {
      if (!nextResult.isDone()) {
        nextResult.cancel(true);
        nbTasksCancelled++;
      }
    }
    completeJobs.addAll(registeredJobs);
    registeredJobs.clear();
    if (nbTasksCancelled > 0) {
      LOGGER.debug(batchId + ": Running tasks cancelled, count=" + nbTasksCancelled);
    }
  }

  private int getMaxNumberOfConcurrentTasks() {
    StateTaskDetails stateTask = taskChain.get(0);
    Integer maxNumberOfThreads = stateTask.getMaxConcurrentTasks();
    if (maxNumberOfThreads == null) {
      LOGGER.debug(batchId + ": No max number of concurrent tasks defined, max is 1");
      return 1;
    } else {
      LOGGER.debug(String.format("%s: maxConcurrentTasks=%d", batchId, maxNumberOfThreads));
      return maxNumberOfThreads;
    }
  }

  private void startTaskJob(final DataRef target, Long timeout) {
    final FlowParameterContext<DataRef, StateTaskDetails> parameter = parameterContextFactory.createContext(target);

    long start = System.currentTimeMillis();
    try {
      taskJobs.add(new TaskJob(
          completionService.submit(() -> runTask(parameter, target), null),
          timeout)
      );

    } catch (RejectedExecutionException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Failed to submit task to Executor, please check your ManagedExecutorService in your application server", e);
      }
      addTaskResult(TaskResult.notExecuted());
      finishTarget(target);
    }

    LOGGER.debug(batchId + ": started task job, target=" + target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");
  }

  /**
   * Synchronous method to run the task
   * @param parameter flowParameter
   * @param target target to run
   */
  private void runTask(final FlowParameterContext<DataRef, StateTaskDetails> parameter, DataRef target) {
    try {
      TaskSequencer<T> taskSequencer = new TaskSequencerFactory().makeTaskSequencer(parameter);
      final FlowResult<T> result = taskSequencer.sequence(parameter);
      joinFlowResult(result);
    } catch (Exception e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error(
            "Failed to execute task", e
        );
      }
      addTaskResult(TaskResult.error());
    } finally {
      finishTarget(target);
    }
  }

  private void finishTarget(DataRef target) {
    registeredJobs.remove(target);
    completeJobs.add(target);
    tasksRunning.decrementAndGet();
  }

  private void joinFlowResult(FlowResult<T> result) {
    synchronized (flowResult) {
      flowResult.join(result);
    }
  }

  private void addTaskResult(TaskResult<T> result) {
    synchronized (flowResult) {
      flowResult.addTaskResult(result);
    }
  }

  @Override
  public FlowResult<T> getFlowResult() {
    return flowResult;
  }

  private enum ProcessorState {
    RUNNING, STOPPED
  }

  static class TaskJob {
    private final Future<Void> future;
    private final Long expireTime;

    TaskJob(Future<Void> future, Long timeout) {
      this.future = future;
      this.expireTime = timeout == null ? null : System.currentTimeMillis() + timeout;
    }

    /**
     * Check if task is expired
     * @return true if tasks is running longer than maximum time
     */
    boolean isExpired() {
      return expireTime != null && System.currentTimeMillis() > expireTime;
    }

    /**
     * Get remaining time in ms until task timeout
     * @return remaining time for this task, null if no timeout is set
     */
    Long getRemainingTime() {
      if (future.isDone()) return 0L;
      if (expireTime == null) return null;
      long remaining = expireTime - System.currentTimeMillis();
      return remaining > 0 ? remaining : 0;
    }
  }
}
