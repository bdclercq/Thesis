package net.democritus.wfe;

import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.FlowProcessingContext;
import net.democritus.workflow.StateTaskDetails;

// @feature:workflow-sequencing
public class TaskSequencerFactory {

  public <R> TaskSequencer<R> makeTaskSequencer(FlowParameterContext<?, StateTaskDetails> parameter) {
    FlowProcessingContext<StateTaskDetails> processingContext = parameter.getProcessingContext();
    String sequencingStrategy = processingContext.getSequencingStrategy();

    if (sequencingStrategy == null || sequencingStrategy.isEmpty()) {
      return new DefaultTaskSequencer<R>();
    } else if (sequencingStrategy.equalsIgnoreCase("greedy")) {
      return new GreedyTaskSequencer<R>();
    } else {
      return new DefaultTaskSequencer<R>();
    }
  }

}
