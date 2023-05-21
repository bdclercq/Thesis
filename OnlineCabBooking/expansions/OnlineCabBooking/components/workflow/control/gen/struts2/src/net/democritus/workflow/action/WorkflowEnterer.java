package net.democritus.workflow.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import workflow.context.ContextRetriever;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import net.democritus.workflow.WorkflowAgent;
import net.democritus.workflow.WorkflowDetails;

import net.democritus.sys.DataRef;
import net.democritus.sys.PageRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchDataRef;
import net.democritus.sys.SearchResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import net.democritus.acl.struts2.UserContextRetriever;

import net.democritus.sys.UserContext;
import net.democritus.json.JsonResult;
import net.democritus.json.DiagnosticsToStrutsMapper;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class WorkflowEnterer extends ActionSupport implements Preparable {

  private String workflowOid = "";
  private String workflowName = null;
  private WorkflowDetails workflowDetails = new WorkflowDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(WorkflowEnterer.class);
  private WorkflowAgent workflowAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public WorkflowAgent getWorkflowAgent() {
    return workflowAgent;
  }

  public WorkflowDetails getWorkflowDetails() {
    return workflowDetails;
  }

  // convenience method, that skips the 'Details' part
  public WorkflowDetails getWorkflow() {
    return getWorkflowDetails();
  }

  public String getWorkflowOid() {
    return workflowOid;
  }

  public void setWorkflowOid(String workflowOid) {
    this.workflowOid = workflowOid;
  }

  public String getWorkflowName() {
    return workflowName;
  }

  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    workflowAgent = createWorkflowAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    if (!httpServletRequest.getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    String actionResult;

    if (workflowName != null) {
      workflowDetails.setName(workflowName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(workflowDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      workflowName = dataRef.getName();
      workflowOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      workflowOid = "";

      actionResult = Action.INPUT;
    }

    // @anchor:execute-fileUploadOnly-after:start
    // @anchor:execute-fileUploadOnly-after:end

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  public CrudsResult<DataRef> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<DataRef> getJsonResult() {

    if (crudsResult == null) {
      // there were validation or conversion errors
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }

    if (crudsResult.isSuccess()) {
      return JsonResult.createValue(crudsResult.getValue(), getActionMessages());
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public WorkflowDetails getJsonRoot() {
    return workflowDetails;
  }

  private boolean hasworkflowOid() {
    return !(workflowOid.equals("") || workflowOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(WorkflowDetails workflowDetails) {
    boolean isNew;

    if (hasworkflowOid()) {
      workflowDetails.setId(new Long(workflowOid));
    }

    Long id = workflowDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return workflowAgent.create(workflowDetails);
    } else {
      return workflowAgent.modify(workflowDetails);
    }
  }

  // @anchor:methods:start
  private static WorkflowAgent createWorkflowAgent() {
    return WorkflowAgent.getWorkflowAgent(getContext());
  }

  private static Context getContext() {
    return ContextRetriever.getContext();
  }

  /**
   * @deprecated Use {@link ContextRetriever} instead
   */
  @Deprecated
  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
