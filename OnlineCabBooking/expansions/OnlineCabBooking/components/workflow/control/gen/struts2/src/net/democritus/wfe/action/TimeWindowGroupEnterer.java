package net.democritus.wfe.action;

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
import net.democritus.wfe.TimeWindowGroupAgent;
import net.democritus.wfe.TimeWindowGroupDetails;

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

public class TimeWindowGroupEnterer extends ActionSupport implements Preparable {

  private String timeWindowGroupOid = "";
  private String timeWindowGroupName = null;
  private TimeWindowGroupDetails timeWindowGroupDetails = new TimeWindowGroupDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TimeWindowGroupEnterer.class);
  private TimeWindowGroupAgent timeWindowGroupAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TimeWindowGroupAgent getTimeWindowGroupAgent() {
    return timeWindowGroupAgent;
  }

  public TimeWindowGroupDetails getTimeWindowGroupDetails() {
    return timeWindowGroupDetails;
  }

  // convenience method, that skips the 'Details' part
  public TimeWindowGroupDetails getTimeWindowGroup() {
    return getTimeWindowGroupDetails();
  }

  public String getTimeWindowGroupOid() {
    return timeWindowGroupOid;
  }

  public void setTimeWindowGroupOid(String timeWindowGroupOid) {
    this.timeWindowGroupOid = timeWindowGroupOid;
  }

  public String getTimeWindowGroupName() {
    return timeWindowGroupName;
  }

  public void setTimeWindowGroupName(String timeWindowGroupName) {
    this.timeWindowGroupName = timeWindowGroupName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    timeWindowGroupAgent = createTimeWindowGroupAgent();
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

    if (timeWindowGroupName != null) {
      timeWindowGroupDetails.setName(timeWindowGroupName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(timeWindowGroupDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      timeWindowGroupName = dataRef.getName();
      timeWindowGroupOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      timeWindowGroupOid = "";

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

  public TimeWindowGroupDetails getJsonRoot() {
    return timeWindowGroupDetails;
  }

  private boolean hastimeWindowGroupOid() {
    return !(timeWindowGroupOid.equals("") || timeWindowGroupOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(TimeWindowGroupDetails timeWindowGroupDetails) {
    boolean isNew;

    if (hastimeWindowGroupOid()) {
      timeWindowGroupDetails.setId(new Long(timeWindowGroupOid));
    }

    Long id = timeWindowGroupDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return timeWindowGroupAgent.create(timeWindowGroupDetails);
    } else {
      return timeWindowGroupAgent.modify(timeWindowGroupDetails);
    }
  }

  // @anchor:methods:start
  private static TimeWindowGroupAgent createTimeWindowGroupAgent() {
    return TimeWindowGroupAgent.getTimeWindowGroupAgent(getContext());
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
