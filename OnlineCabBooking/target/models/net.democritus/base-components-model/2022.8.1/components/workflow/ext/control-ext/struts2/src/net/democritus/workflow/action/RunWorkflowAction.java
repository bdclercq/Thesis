package net.democritus.workflow.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.wfe.action.FlowJsonResult;
import net.democritus.wfe.action.FlowResultToStrutsMapper;
import net.democritus.workflow.WorkflowAgent;
import org.apache.struts2.ServletActionContext;
import workflow.context.ContextRetriever;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static net.democritus.sys.DataRefValidation.isDataRefDefined;

public class RunWorkflowAction extends ActionSupport {

  private final transient WorkflowAgent workflowAgent = createWorkflowAgent();

  private DataRef workflow;
  private List<DataRef> targetInstances;
  private DataRef engineService;
  private FlowResult<Void> flowResult = FlowResult.error();

  @Override
  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
    if (!httpServletRequest.getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }

    if (isDataRefDefined(engineService)) {
      flowResult = workflowAgent.runWorkflowWithEngineService(engineService);
    } else if (isDataRefDefined(workflow) && targetInstances != null && !targetInstances.isEmpty()) {
      flowResult = workflowAgent.runWorkflowWithTargets(workflow, targetInstances);
    } else {
      addActionError("Either a parameter 'engineService' or 'workflow' and 'targetInstances' should be provided");
    }

    new FlowResultToStrutsMapper(this).mapDiagnostics(flowResult.getDiagnostics());
    return Action.SUCCESS;
  }


  public FlowJsonResult getJsonResult() {

    return FlowJsonResult.from(flowResult, getActionErrors());
  }

  public DataRef getWorkflow() {
    return workflow;
  }

  public void setWorkflow(DataRef workflow) {
    this.workflow = workflow;
  }

  public List<DataRef> getTargetInstances() {
    return targetInstances;
  }

  public void setTargetInstances(List<DataRef> targetInstances) {
    this.targetInstances = targetInstances;
  }

  public DataRef getEngineService() {
    return engineService;
  }

  public void setEngineService(DataRef engineService) {
    this.engineService = engineService;
  }

  private static WorkflowAgent createWorkflowAgent() {
    return WorkflowAgent.getWorkflowAgent(getContext());
  }

  private static Context getContext() {
    return ContextRetriever.getContext();
  }

}
