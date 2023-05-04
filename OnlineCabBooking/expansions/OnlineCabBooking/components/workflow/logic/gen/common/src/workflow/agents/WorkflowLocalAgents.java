package workflow.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.wfe.EngineNodeLocalAgent;
import net.democritus.wfe.EngineNodeAgentIf;
import net.democritus.wfe.EngineNodeServiceLocalAgent;
import net.democritus.wfe.EngineNodeServiceAgentIf;
import net.democritus.wfe.EngineServiceLocalAgent;
import net.democritus.wfe.EngineServiceAgentIf;
import net.democritus.workflow.StateTaskLocalAgent;
import net.democritus.workflow.StateTaskAgentIf;
import net.democritus.workflow.StateTimerLocalAgent;
import net.democritus.workflow.StateTimerAgentIf;
import net.democritus.workflow.TimeTaskLocalAgent;
import net.democritus.workflow.TimeTaskAgentIf;
import net.democritus.wfe.TimeWindowLocalAgent;
import net.democritus.wfe.TimeWindowAgentIf;
import net.democritus.wfe.TimeWindowGroupLocalAgent;
import net.democritus.wfe.TimeWindowGroupAgentIf;
import net.democritus.workflow.WorkflowLocalAgent;
import net.democritus.workflow.WorkflowAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class WorkflowLocalAgents implements WorkflowAgents {

  private final Context context;

  // anchor:data-element-agent-variables:start
  private EngineNodeAgentIf engineNodeAgent;
  private EngineNodeServiceAgentIf engineNodeServiceAgent;
  private EngineServiceAgentIf engineServiceAgent;
  private StateTaskAgentIf stateTaskAgent;
  private StateTimerAgentIf stateTimerAgent;
  private TimeTaskAgentIf timeTaskAgent;
  private TimeWindowAgentIf timeWindowAgent;
  private TimeWindowGroupAgentIf timeWindowGroupAgent;
  private WorkflowAgentIf workflowAgent;
  // anchor:data-element-agent-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static WorkflowAgents getWorkflowAgents(Context context) {
    return new WorkflowLocalAgents(context);
  }

  @Deprecated
  public static WorkflowAgents getWorkflowAgents(UserContext userContext) {
    return new WorkflowLocalAgents(userContext);
  }

  public WorkflowLocalAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public WorkflowLocalAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public EngineNodeAgentIf getEngineNodeAgent() {
    if (engineNodeAgent == null) {
      engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(context);
    }
    return engineNodeAgent;
  }

  public EngineNodeServiceAgentIf getEngineNodeServiceAgent() {
    if (engineNodeServiceAgent == null) {
      engineNodeServiceAgent = EngineNodeServiceLocalAgent.getEngineNodeServiceAgent(context);
    }
    return engineNodeServiceAgent;
  }

  public EngineServiceAgentIf getEngineServiceAgent() {
    if (engineServiceAgent == null) {
      engineServiceAgent = EngineServiceLocalAgent.getEngineServiceAgent(context);
    }
    return engineServiceAgent;
  }

  public StateTaskAgentIf getStateTaskAgent() {
    if (stateTaskAgent == null) {
      stateTaskAgent = StateTaskLocalAgent.getStateTaskAgent(context);
    }
    return stateTaskAgent;
  }

  public StateTimerAgentIf getStateTimerAgent() {
    if (stateTimerAgent == null) {
      stateTimerAgent = StateTimerLocalAgent.getStateTimerAgent(context);
    }
    return stateTimerAgent;
  }

  public TimeTaskAgentIf getTimeTaskAgent() {
    if (timeTaskAgent == null) {
      timeTaskAgent = TimeTaskLocalAgent.getTimeTaskAgent(context);
    }
    return timeTaskAgent;
  }

  public TimeWindowAgentIf getTimeWindowAgent() {
    if (timeWindowAgent == null) {
      timeWindowAgent = TimeWindowLocalAgent.getTimeWindowAgent(context);
    }
    return timeWindowAgent;
  }

  public TimeWindowGroupAgentIf getTimeWindowGroupAgent() {
    if (timeWindowGroupAgent == null) {
      timeWindowGroupAgent = TimeWindowGroupLocalAgent.getTimeWindowGroupAgent(context);
    }
    return timeWindowGroupAgent;
  }

  public WorkflowAgentIf getWorkflowAgent() {
    if (workflowAgent == null) {
      workflowAgent = WorkflowLocalAgent.getWorkflowAgent(context);
    }
    return workflowAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
