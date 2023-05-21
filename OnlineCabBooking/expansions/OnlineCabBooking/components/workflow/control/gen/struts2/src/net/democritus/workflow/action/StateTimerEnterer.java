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
import net.democritus.workflow.StateTimerAgent;
import net.democritus.workflow.StateTimerDetails;

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

public class StateTimerEnterer extends ActionSupport implements Preparable {

  private String stateTimerOid = "";
  private String stateTimerName = null;
  private StateTimerDetails stateTimerDetails = new StateTimerDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StateTimerEnterer.class);
  private StateTimerAgent stateTimerAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public StateTimerAgent getStateTimerAgent() {
    return stateTimerAgent;
  }

  public StateTimerDetails getStateTimerDetails() {
    return stateTimerDetails;
  }

  // convenience method, that skips the 'Details' part
  public StateTimerDetails getStateTimer() {
    return getStateTimerDetails();
  }

  public String getStateTimerOid() {
    return stateTimerOid;
  }

  public void setStateTimerOid(String stateTimerOid) {
    this.stateTimerOid = stateTimerOid;
  }

  public String getStateTimerName() {
    return stateTimerName;
  }

  public void setStateTimerName(String stateTimerName) {
    this.stateTimerName = stateTimerName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    stateTimerAgent = createStateTimerAgent();
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

    if (stateTimerName != null) {
      stateTimerDetails.setName(stateTimerName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(stateTimerDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      stateTimerName = dataRef.getName();
      stateTimerOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      stateTimerOid = "";

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

  public StateTimerDetails getJsonRoot() {
    return stateTimerDetails;
  }

  private boolean hasstateTimerOid() {
    return !(stateTimerOid.equals("") || stateTimerOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(StateTimerDetails stateTimerDetails) {
    boolean isNew;

    if (hasstateTimerOid()) {
      stateTimerDetails.setId(new Long(stateTimerOid));
    }

    Long id = stateTimerDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return stateTimerAgent.create(stateTimerDetails);
    } else {
      return stateTimerAgent.modify(stateTimerDetails);
    }
  }

  // @anchor:methods:start
  private static StateTimerAgent createStateTimerAgent() {
    return StateTimerAgent.getStateTimerAgent(getContext());
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
