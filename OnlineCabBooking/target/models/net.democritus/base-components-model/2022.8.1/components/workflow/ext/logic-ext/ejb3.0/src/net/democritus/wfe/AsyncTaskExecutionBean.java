package net.democritus.wfe;

import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.workflow.StateTaskDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

// @feature:parallel-execution
@Stateless
@Local(AsyncTaskExecutionLocal.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class AsyncTaskExecutionBean implements AsyncTaskExecutionLocal {
  private static final Logger logger = LoggerFactory.getLogger("net.democritus.wfe.AsyncTaskExecution");

  @Resource
  private TimerService timerService;

  @Override
  public <T> Future<FlowResult<T>> executeTaskAsync(final FlowParameterContext<DataRef, StateTaskDetails> parameter) {
    FutureTask<FlowResult<T>> futureTask = new FutureTask<FlowResult<T>>(
        new Callable<FlowResult<T>>() {
          @Override
          public FlowResult<T> call() throws Exception {
            TaskSequencer<T> sequencer = getSequencer(parameter);
            return sequencer.sequence(parameter);
          }
        });

    Date timeout = new Date(new Date().getTime() + 50);
    timerService.createTimer(timeout, new TaskExecutionParams(
        parameter,
        futureTask
    ));

    return futureTask;
  }

  @Timeout
  @SuppressWarnings("unused")
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void executeTask(Timer timer) {
    TaskExecutionParams params = (TaskExecutionParams) timer.getInfo();
    logger.debug("Running async task runId='" + params.parameter.getProcessingContext().getRunId() + "'");
    params.future.run();
  }

  private <T> TaskSequencer<T> getSequencer(FlowParameterContext<?, StateTaskDetails> parameter) {
    return new TaskSequencerFactory().makeTaskSequencer(parameter);
  }

  private class TaskExecutionParams implements Serializable {
    final FlowParameterContext<DataRef, StateTaskDetails> parameter;
    final FutureTask<? extends FlowResult<?>> future;

    TaskExecutionParams(FlowParameterContext<DataRef, StateTaskDetails> parameter, FutureTask<? extends FlowResult<?>> future) {
      this.parameter = parameter;
      this.future = future;
    }
  }

}
