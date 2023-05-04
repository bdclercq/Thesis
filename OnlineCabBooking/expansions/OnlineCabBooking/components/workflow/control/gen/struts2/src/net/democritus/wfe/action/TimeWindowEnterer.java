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
import net.democritus.wfe.TimeWindowAgent;
import net.democritus.wfe.TimeWindowDetails;

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

public class TimeWindowEnterer extends ActionSupport implements Preparable {

  private String timeWindowOid = "";
  private String timeWindowName = null;
  private TimeWindowDetails timeWindowDetails = new TimeWindowDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TimeWindowEnterer.class);
  private TimeWindowAgent timeWindowAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TimeWindowAgent getTimeWindowAgent() {
    return timeWindowAgent;
  }

  public TimeWindowDetails getTimeWindowDetails() {
    return timeWindowDetails;
  }

  // convenience method, that skips the 'Details' part
  public TimeWindowDetails getTimeWindow() {
    return getTimeWindowDetails();
  }

  public String getTimeWindowOid() {
    return timeWindowOid;
  }

  public void setTimeWindowOid(String timeWindowOid) {
    this.timeWindowOid = timeWindowOid;
  }

  public String getTimeWindowName() {
    return timeWindowName;
  }

  public void setTimeWindowName(String timeWindowName) {
    this.timeWindowName = timeWindowName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    timeWindowAgent = createTimeWindowAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (timeWindowName != null) {
      timeWindowDetails.setName(timeWindowName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(timeWindowDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      timeWindowName = dataRef.getName();
      timeWindowOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      timeWindowOid = "";

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

  public TimeWindowDetails getJsonRoot() {
    return timeWindowDetails;
  }

  private boolean hastimeWindowOid() {
    return !(timeWindowOid.equals("") || timeWindowOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(TimeWindowDetails timeWindowDetails) {
    boolean isNew;

    if (hastimeWindowOid()) {
      timeWindowDetails.setId(new Long(timeWindowOid));
    }

    Long id = timeWindowDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return timeWindowAgent.create(timeWindowDetails);
    } else {
      return timeWindowAgent.modify(timeWindowDetails);
    }
  }

  // @anchor:methods:start
  private static TimeWindowAgent createTimeWindowAgent() {
    return TimeWindowAgent.getTimeWindowAgent(getContext());
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
