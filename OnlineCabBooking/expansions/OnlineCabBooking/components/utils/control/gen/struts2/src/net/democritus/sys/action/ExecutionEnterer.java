package net.democritus.sys.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import utils.context.ContextRetriever;
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
import net.democritus.sys.ExecutionAgent;
import net.democritus.sys.ExecutionDetails;

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

public class ExecutionEnterer extends ActionSupport implements Preparable {

  private String executionOid = "";
  private String executionName = null;
  private ExecutionDetails executionDetails = new ExecutionDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ExecutionEnterer.class);
  private ExecutionAgent executionAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ExecutionAgent getExecutionAgent() {
    return executionAgent;
  }

  public ExecutionDetails getExecutionDetails() {
    return executionDetails;
  }

  // convenience method, that skips the 'Details' part
  public ExecutionDetails getExecution() {
    return getExecutionDetails();
  }

  public String getExecutionOid() {
    return executionOid;
  }

  public void setExecutionOid(String executionOid) {
    this.executionOid = executionOid;
  }

  public String getExecutionName() {
    return executionName;
  }

  public void setExecutionName(String executionName) {
    this.executionName = executionName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    executionAgent = createExecutionAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (executionName != null) {
      executionDetails.setName(executionName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(executionDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      executionName = dataRef.getName();
      executionOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      executionOid = "";

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

  public ExecutionDetails getJsonRoot() {
    return executionDetails;
  }

  private boolean hasexecutionOid() {
    return !(executionOid.equals("") || executionOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ExecutionDetails executionDetails) {
    boolean isNew;

    if (hasexecutionOid()) {
      executionDetails.setId(new Long(executionOid));
    }

    Long id = executionDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return executionAgent.create(executionDetails);
    } else {
      return executionAgent.modify(executionDetails);
    }
  }

  // @anchor:methods:start
  private static ExecutionAgent createExecutionAgent() {
    return ExecutionAgent.getExecutionAgent(getContext());
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
