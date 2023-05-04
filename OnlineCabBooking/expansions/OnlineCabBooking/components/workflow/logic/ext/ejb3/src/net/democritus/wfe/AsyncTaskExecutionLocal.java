package net.democritus.wfe;

import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.workflow.StateTaskDetails;

import java.util.concurrent.Future;

// @feature:parallel-execution
public interface AsyncTaskExecutionLocal {
  <T> Future<FlowResult<T>> executeTaskAsync(FlowParameterContext<DataRef, StateTaskDetails> parameter);
}
