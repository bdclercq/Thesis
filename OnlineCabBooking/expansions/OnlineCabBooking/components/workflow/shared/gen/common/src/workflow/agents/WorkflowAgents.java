package workflow.agents;

import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.wfe.EngineNodeAgentIf;
import net.democritus.wfe.EngineNodeServiceAgentIf;
import net.democritus.wfe.EngineServiceAgentIf;
import net.democritus.workflow.StateTaskAgentIf;
import net.democritus.workflow.StateTimerAgentIf;
import net.democritus.workflow.TimeTaskAgentIf;
import net.democritus.wfe.TimeWindowAgentIf;
import net.democritus.wfe.TimeWindowGroupAgentIf;
import net.democritus.workflow.WorkflowAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface WorkflowAgents {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:data-element-agent-getters:start
  EngineNodeAgentIf getEngineNodeAgent();
  EngineNodeServiceAgentIf getEngineNodeServiceAgent();
  EngineServiceAgentIf getEngineServiceAgent();
  StateTaskAgentIf getStateTaskAgent();
  StateTimerAgentIf getStateTimerAgent();
  TimeTaskAgentIf getTimeTaskAgent();
  TimeWindowAgentIf getTimeWindowAgent();
  TimeWindowGroupAgentIf getTimeWindowGroupAgent();
  WorkflowAgentIf getWorkflowAgent();
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
