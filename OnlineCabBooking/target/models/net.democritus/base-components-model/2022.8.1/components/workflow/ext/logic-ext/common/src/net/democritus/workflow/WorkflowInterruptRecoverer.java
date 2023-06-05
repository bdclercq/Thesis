package net.democritus.workflow;

import net.democritus.metadata.FlowElementDef;
import net.democritus.metadata.FlowElementRetriever;
import net.democritus.state.StateRecoverer;
import net.democritus.state.StateRecovererFactory;
import net.democritus.sys.Context;
import net.democritus.sys.ElementRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.search.SearchDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

// @feature:workflow-recovery
public class WorkflowInterruptRecoverer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowInterruptRecoverer.class);

  private final Context context;
  private final WorkflowLocalAgent workflowAgent;

  private boolean recoverClaimingFlows = false;
  private boolean recoverNonClaimingFlows = false;

  public WorkflowInterruptRecoverer(Context context) {
    this.context = context;
    workflowAgent = WorkflowLocalAgent.getWorkflowAgent(context);
  }

  /**
   * @deprecated Use {@link WorkflowInterruptRecoverer#WorkflowInterruptRecoverer(Context)} instead
   */
  @Deprecated
  public WorkflowInterruptRecoverer(UserContext userContext) {
    this.context = Context.from(userContext);
    workflowAgent = WorkflowLocalAgent.getWorkflowAgent(userContext);
  }

  public void recoverInterruptedInstances() {
    LOGGER.debug("Recovering interrupted instances");

    SearchResult<WorkflowDetails> searchResult = findWorkflows();
    for (WorkflowDetails workflowDetails : searchResult.getResults()) {
      FlowElementDef flowElementDef = getFlowElementDef(workflowDetails);
      if (flowElementDef.usesClaims() && recoverClaimingFlows
          || !flowElementDef.usesClaims() && recoverNonClaimingFlows) {
        recover(workflowDetails);
      }
    }
  }

  private SearchResult<WorkflowDetails> findWorkflows() {
    SearchDetails<WorkflowFindAllWorkflowsDetails> searchDetails = SearchDetails.fetchAll(new WorkflowFindAllWorkflowsDetails());
    searchDetails.setProjection("details");
    return workflowAgent.find(searchDetails);
  }

  public void recover(WorkflowDetails workflowDetails) {
    try {
      ElementRef workflowRef = getElementRef(workflowDetails);
      StateRecoverer<WorkflowDetails> stateRecoverer = new StateRecovererFactory().makeStateRecoverer(workflowRef);
      stateRecoverer.recover(context.withParameter(workflowDetails));
    } catch (Exception e) {
      LOGGER.warn("Workflow '" + workflowDetails.getName() + "' could not be recovered due to exception", e);
    }
  }

  private FlowElementDef getFlowElementDef(WorkflowDetails workflowDetails) {
    ElementRef workflowRef = getElementRef(workflowDetails);
    return new FlowElementRetriever().getFlowElementDef(workflowRef);
  }

  private ElementRef getElementRef(WorkflowDetails workflowDetails) {
    return new ElementRef(workflowDetails.getComponentName(), workflowDetails.getClassName());
  }

  public void setRecoverClaimingFlows(boolean recoverClaimingFlows) {
    this.recoverClaimingFlows = recoverClaimingFlows;
  }

  public void setRecoverNonClaimingFlows(boolean recoverNonClaimingFlows) {
    this.recoverNonClaimingFlows = recoverNonClaimingFlows;
  }

}
