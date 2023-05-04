package workflow.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.wfe.EngineNodeAgent;
import net.democritus.wfe.EngineNodeAgentIf;
import net.democritus.wfe.EngineNodeServiceAgent;
import net.democritus.wfe.EngineNodeServiceAgentIf;
import net.democritus.wfe.EngineServiceAgent;
import net.democritus.wfe.EngineServiceAgentIf;
import net.democritus.workflow.StateTaskAgent;
import net.democritus.workflow.StateTaskAgentIf;
import net.democritus.workflow.StateTimerAgent;
import net.democritus.workflow.StateTimerAgentIf;
import net.democritus.workflow.TimeTaskAgent;
import net.democritus.workflow.TimeTaskAgentIf;
import net.democritus.wfe.TimeWindowAgent;
import net.democritus.wfe.TimeWindowAgentIf;
import net.democritus.wfe.TimeWindowGroupAgent;
import net.democritus.wfe.TimeWindowGroupAgentIf;
import net.democritus.workflow.WorkflowAgent;
import net.democritus.workflow.WorkflowAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class WorkflowProxyAgents implements WorkflowAgents {

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
    return new WorkflowProxyAgents(context);
  }

  @Deprecated
  public static WorkflowAgents getWorkflowAgents(UserContext userContext) {
    return new WorkflowProxyAgents(userContext);
  }

  public WorkflowProxyAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public WorkflowProxyAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public EngineNodeAgentIf getEngineNodeAgent() {
    if (engineNodeAgent == null) {
      engineNodeAgent = EngineNodeAgent.getEngineNodeAgent(context);
    }
    return engineNodeAgent;
  }

  public EngineNodeServiceAgentIf getEngineNodeServiceAgent() {
    if (engineNodeServiceAgent == null) {
      engineNodeServiceAgent = EngineNodeServiceAgent.getEngineNodeServiceAgent(context);
    }
    return engineNodeServiceAgent;
  }

  public EngineServiceAgentIf getEngineServiceAgent() {
    if (engineServiceAgent == null) {
      engineServiceAgent = EngineServiceAgent.getEngineServiceAgent(context);
    }
    return engineServiceAgent;
  }

  public StateTaskAgentIf getStateTaskAgent() {
    if (stateTaskAgent == null) {
      stateTaskAgent = StateTaskAgent.getStateTaskAgent(context);
    }
    return stateTaskAgent;
  }

  public StateTimerAgentIf getStateTimerAgent() {
    if (stateTimerAgent == null) {
      stateTimerAgent = StateTimerAgent.getStateTimerAgent(context);
    }
    return stateTimerAgent;
  }

  public TimeTaskAgentIf getTimeTaskAgent() {
    if (timeTaskAgent == null) {
      timeTaskAgent = TimeTaskAgent.getTimeTaskAgent(context);
    }
    return timeTaskAgent;
  }

  public TimeWindowAgentIf getTimeWindowAgent() {
    if (timeWindowAgent == null) {
      timeWindowAgent = TimeWindowAgent.getTimeWindowAgent(context);
    }
    return timeWindowAgent;
  }

  public TimeWindowGroupAgentIf getTimeWindowGroupAgent() {
    if (timeWindowGroupAgent == null) {
      timeWindowGroupAgent = TimeWindowGroupAgent.getTimeWindowGroupAgent(context);
    }
    return timeWindowGroupAgent;
  }

  public WorkflowAgentIf getWorkflowAgent() {
    if (workflowAgent == null) {
      workflowAgent = WorkflowAgent.getWorkflowAgent(context);
    }
    return workflowAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
