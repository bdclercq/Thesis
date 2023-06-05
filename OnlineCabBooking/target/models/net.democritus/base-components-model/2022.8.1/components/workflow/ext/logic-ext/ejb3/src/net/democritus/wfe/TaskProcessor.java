package net.democritus.wfe;

import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.ParameterContext;

import java.util.List;

// @feature:parallel-execution
public interface TaskProcessor {

  void init(ParameterContext<BatchDescription> batchParameter);

  void processTask(DataRef target);

  default void processTask(DataRef target, Long timeout) {
    processTask(target);
  }

  void cancelRunningTasks();

  boolean hasMoreTasks() throws ProcessingException;

  List<DataRef> takeCompletedTasks() throws ProcessingException;

  List<DataRef> takeNextCompletedTasks() throws ProcessingException;

  FlowResult getFlowResult();

}
