package net.democritus.workflow.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.json.JsonResult;
import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.TaskResult;
import net.democritus.wfe.action.FlowResultToStrutsMapper;
import net.democritus.workflow.WorkflowAgent;
import org.apache.struts2.ServletActionContext;
import workflow.context.ContextRetriever;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecoverWorkflowAction extends ActionSupport {

  private DataRef workflow;
  private TaskResult<Void> taskResult = TaskResult.error();

  @Override
  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
    if (!httpServletRequest.getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }

    WorkflowAgent workflowAgent = WorkflowAgent.getWorkflowAgent(getContext());
    taskResult = workflowAgent.recoverWorkflow(workflow);

    new FlowResultToStrutsMapper(this).mapDiagnostics(taskResult.getDiagnostics());
    return Action.SUCCESS;
  }

  public JsonResult<String> getJsonResult() {
    if (taskResult == null || taskResult.isSuccess()) {
      return JsonResult.createValue("Recovery complete");
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public DataRef getWorkflow() {
    return workflow;
  }

  public void setWorkflow(DataRef workflow) {
    this.workflow = workflow;
  }

  private static Context getContext() {
    return ContextRetriever.getContext();
  }

}
