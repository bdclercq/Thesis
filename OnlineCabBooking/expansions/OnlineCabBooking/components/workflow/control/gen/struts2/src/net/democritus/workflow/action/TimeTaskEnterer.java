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
import net.democritus.workflow.TimeTaskAgent;
import net.democritus.workflow.TimeTaskDetails;

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

public class TimeTaskEnterer extends ActionSupport implements Preparable {

  private String timeTaskOid = "";
  private String timeTaskName = null;
  private TimeTaskDetails timeTaskDetails = new TimeTaskDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TimeTaskEnterer.class);
  private TimeTaskAgent timeTaskAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TimeTaskAgent getTimeTaskAgent() {
    return timeTaskAgent;
  }

  public TimeTaskDetails getTimeTaskDetails() {
    return timeTaskDetails;
  }

  // convenience method, that skips the 'Details' part
  public TimeTaskDetails getTimeTask() {
    return getTimeTaskDetails();
  }

  public String getTimeTaskOid() {
    return timeTaskOid;
  }

  public void setTimeTaskOid(String timeTaskOid) {
    this.timeTaskOid = timeTaskOid;
  }

  public String getTimeTaskName() {
    return timeTaskName;
  }

  public void setTimeTaskName(String timeTaskName) {
    this.timeTaskName = timeTaskName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    timeTaskAgent = createTimeTaskAgent();
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

    if (timeTaskName != null) {
      timeTaskDetails.setName(timeTaskName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(timeTaskDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      timeTaskName = dataRef.getName();
      timeTaskOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      timeTaskOid = "";

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

  public TimeTaskDetails getJsonRoot() {
    return timeTaskDetails;
  }

  private boolean hastimeTaskOid() {
    return !(timeTaskOid.equals("") || timeTaskOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(TimeTaskDetails timeTaskDetails) {
    boolean isNew;

    if (hastimeTaskOid()) {
      timeTaskDetails.setId(new Long(timeTaskOid));
    }

    Long id = timeTaskDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return timeTaskAgent.create(timeTaskDetails);
    } else {
      return timeTaskAgent.modify(timeTaskDetails);
    }
  }

  // @anchor:methods:start
  private static TimeTaskAgent createTimeTaskAgent() {
    return TimeTaskAgent.getTimeTaskAgent(getContext());
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
