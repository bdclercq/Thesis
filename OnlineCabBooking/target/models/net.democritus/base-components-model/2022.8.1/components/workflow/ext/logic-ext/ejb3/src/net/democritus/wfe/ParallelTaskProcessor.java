package net.democritus.wfe;

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.RunId;
import net.democritus.workflow.StateTaskDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *  Used for parallel processing of tasks.
 *  The object keeps a list of all running tasks and provides a blocking method to add new tasks.
 */
// @feature:parallel-execution
public class ParallelTaskProcessor<T> implements TaskProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(ParallelTaskProcessor.class);

  private RunId batchId;
  private int poolSize;
  private FlowResult<T> flowResult;

  private List<TaskJob> taskJobs;
  private List<TaskJob> completeJobs;
  private AsyncTaskExecutionLocal asyncTaskExecutionBean;

  private long startTime;
  private WorkflowParameterContextFactory parameterContextFactory;

  private List<StateTaskDetails> taskChain;

  @Override
  public void init(ParameterContext<BatchDescription> batchParameter) {
    BatchDescription batchDescription = batchParameter.getValue();
    this.taskChain = batchDescription.getTaskChain();

    taskJobs = Collections.synchronizedList(new LinkedList<TaskJob>());
    completeJobs = Collections.synchronizedList(new LinkedList<TaskJob>());

    batchId = batchDescription.getRunId();
    poolSize = getMaxNumberOfConcurrentTasks();
    asyncTaskExecutionBean = getAsyncTaskExecutionBean();

    parameterContextFactory = new WorkflowParameterContextFactory(
        batchParameter.construct(batchDescription.getWorkflow()),
        batchDescription.getTaskChain(),
        batchId
    );
    parameterContextFactory.init();

    flowResult = FlowResult.success(null);
    startTime = System.nanoTime();
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
    if (timeout != null) {
      LOGGER.warn(batchId + ": registering task with timeout will not work with ParallelTaskProcessor, consider upgrading to ExecutorServiceTaskProcessor");
    }

    LOGGER.debug(batchId + ": registering new task, target=" + target.getId());
    try {
      waitForFreeTaskJobSlot(target);
      TaskJob taskJob = startTaskJob(target, timeout);
      taskJobs.add(taskJob);
      LOGGER.debug(batchId + ": task running, target=" + target.getId());
    } catch (Exception e) {
      throw new ProcessingException(batchId + ": Could not process task due to exception", e);
    }
  }

  private void waitForFreeTaskJobSlot(DataRef target) {
    filterCompletedTasks();

    long start = System.currentTimeMillis();
    if (taskJobs.size() < poolSize) {
      return;
    }

    LOGGER.debug(batchId + ": checked all running tasks, target=" + target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");

    start = System.currentTimeMillis();
    LOGGER.debug(batchId + ": pool is full, target=" + target.getId() + ", timeSinceStart=" + (System.nanoTime() - startTime)/1000000D + "ms");
    TaskJob taskJob = taskJobs.get(0);
    taskJob.waitForCompletion();
    filterCompletedTasks();
    LOGGER.debug(batchId + ": wait ended, target=" + target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");
    startTime = System.nanoTime();
  }

  private boolean addToCompletedTasks(TaskJob taskJob) {
    try {
      // Blocking get() should not be an issue as `taskJob.jobResult.isDone()` is validated before calling this method
      FlowResult<T> jobFlowResult = taskJob.jobResult.get();
      flowResult.join(jobFlowResult);
    } catch (Exception e) {
      LOGGER.error("Could not retrieve flowResult from taskJob", e);
    }
    return completeJobs.add(taskJob);
  }

  /**
   * Take all tasks that have been completed up to now.
   *
   * @return a list of targets that have been completed
   */
  public synchronized List<DataRef> takeCompletedTasks() throws ProcessingException {
    filterCompletedTasks();

    ArrayList<DataRef> completedTargets = new ArrayList<>();
    for (TaskJob completeJob : completeJobs) {
      completedTargets.add(completeJob.target);
    }
    completeJobs.clear();
    return completedTargets;
  }

  private void filterCompletedTasks() {
    Iterator<TaskJob> iterator = taskJobs.iterator();
    int nbCompletedJobs = 0;
    int nbCancelledJobs = 0;

    while (iterator.hasNext()) {
      TaskJob taskJob = iterator.next();
      if (taskJob.jobResult.isCancelled()) {
        iterator.remove();
        addToCompletedTasks(taskJob);
        nbCancelledJobs++;
      } else if (taskJob.jobResult.isDone()) {
        iterator.remove();
        addToCompletedTasks(taskJob);
        nbCompletedJobs++;
      }
    }

    if (nbCompletedJobs> 0 || nbCancelledJobs > 0) {
      LOGGER.debug(batchId + ": tasks have been completed or cancelled" +
          ", nbCompletedJobs=" + nbCompletedJobs +
          ", nbCancelledJobs=" + nbCancelledJobs);
    }
  }

  /**
   * Checks if there are still tasks that are being processed or have been processed.
   *
   * @return true if there are some tasks that are busy or completed
   */
  public boolean hasMoreTasks() throws ProcessingException {
    return !completeJobs.isEmpty() || !taskJobs.isEmpty();
  }

  /**
   * Returns the target of all tasks that has been completed.
   * Can block if no result is available.
   *
   * @return the targets of the completed tasks
   */
  public synchronized List<DataRef> takeNextCompletedTasks() throws ProcessingException {
    try {
      filterCompletedTasks();
      if (!completeJobs.isEmpty()) {
        return takeAllCompletedTasks();
      }

      if (taskJobs.isEmpty()) {
        throw new ProcessingException("no more results");
      }

      long start = System.currentTimeMillis();
      TaskJob taskJob = taskJobs.get(0);
      LOGGER.debug(batchId + ": waiting for task to finish, target=" + taskJob.target.getId());
      taskJob.waitForCompletion();
      LOGGER.debug(batchId + ": wait ended, target=" + taskJob.target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");

      filterCompletedTasks();
      return takeAllCompletedTasks();
    } catch (Exception e) {
      throw new ProcessingException("Could not retrieve next task", e);
    }
  }

  private List<DataRef> takeAllCompletedTasks() {
    List<DataRef> completedTasks = new ArrayList<>();
    for (TaskJob completeJob : completeJobs) {
      completedTasks.add(completeJob.target);
    }
    completeJobs.clear();
    return completedTasks;
  }

  public void cancelRunningTasks() {
    int nbTasksCancelled = 0;
    for (TaskJob taskJob : taskJobs) {
      if (!taskJob.jobResult.isDone()) {
        taskJob.jobResult.cancel(true);
        nbTasksCancelled++;
      }
    }
    if (nbTasksCancelled > 0) {
      LOGGER.debug(batchId + ": Running tasks cancelled, count=" + nbTasksCancelled);
    }
  }

  class TaskJob {
    private final Future<FlowResult<T>> jobResult;
    private final DataRef target;
    private final Long expireTime;

    TaskJob(Future<FlowResult<T>> jobResult, DataRef target, Long timeout) {
      this.jobResult = jobResult;
      this.target = target;
      this.expireTime = timeout == null ? null : System.currentTimeMillis() + timeout;
    }

    boolean isExpired() {
      return expireTime != null && System.currentTimeMillis() > expireTime;
    }

    void waitForCompletion() {
      try {
        if (expireTime == null) {
          jobResult.get();
        } else {
          long remainingTime = expireTime - System.currentTimeMillis();
          if (remainingTime < 0) remainingTime = 0L;
          jobResult.get(remainingTime, TimeUnit.MILLISECONDS);
        }
      } catch (Exception e) {
        LOGGER.debug(batchId + ": Waiting resulted in exception", e);
      }
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

  private AsyncTaskExecutionLocal getAsyncTaskExecutionBean() {
    try {
      InitialContext ic = new InitialContext();
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      String jndiName = componentJNDI.getTaskLocalName("AsyncTaskExecution");
      return (AsyncTaskExecutionLocal) ic.lookup(jndiName);
    } catch (Exception e) {
      throw new TaskExecutionException("Failed to lookup AsyncTaskExecution", e);
    }
  }

  private TaskJob startTaskJob(DataRef target, Long timeout) {
    FlowParameterContext<DataRef, StateTaskDetails> parameter = parameterContextFactory.createContext(target);

    long start = System.currentTimeMillis();
    Future<FlowResult<T>> future = asyncTaskExecutionBean.executeTaskAsync(parameter);
    LOGGER.debug(batchId + ": started task job, target=" + target.getId() + ", duration=" + (System.currentTimeMillis() - start) + "ms");

    return new TaskJob(
        future,
        target,
        timeout
    );
  }

  @Override
  public FlowResult<T> getFlowResult() {
    return flowResult;
  }
}
