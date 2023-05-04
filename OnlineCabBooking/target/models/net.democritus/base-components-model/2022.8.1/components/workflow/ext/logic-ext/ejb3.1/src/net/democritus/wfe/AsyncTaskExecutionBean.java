package net.democritus.wfe;

import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.workflow.FlowParameterContext;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.concurrent.Future;

import net.democritus.workflow.StateTaskDetails;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

// @feature:parallel-execution

/**
 * Implement Asynchronous task execution in JEE6+
 * Used for parallel execution of tasks
 */
@Stateless
@Local(AsyncTaskExecutionLocal.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class AsyncTaskExecutionBean implements AsyncTaskExecutionLocal {
  private static final Logger logger = LoggerFactory.getLogger("net.democritus.wfe.AsyncTaskExecution");

  @Asynchronous
  @Override
  public <T> Future<FlowResult<T>> executeTaskAsync(final FlowParameterContext<DataRef, StateTaskDetails> parameter) {
    try {
      TaskSequencer<T> taskSequencer = new TaskSequencerFactory().makeTaskSequencer(parameter);
      FlowResult<T> result = taskSequencer.sequence(parameter);
      return new AsyncResult<FlowResult<T>>(result);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Failed to execute task asynchronously", e
          );
      }
      return new AsyncResult<FlowResult<T>>(FlowResult.<T>error());
    }
  }

}
