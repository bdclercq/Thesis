package net.democritus.wfe;

import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.workflow.StateTaskDetails;

// @feature:workflow-sequencing
public interface TaskSequencer<RESULT> {
  FlowResult<RESULT> sequence(FlowParameterContext<DataRef, StateTaskDetails> flowParameters);
}
