package net.democritus.wfe.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
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

public class TimeWindowDeleter extends ActionSupport implements Preparable {

  private CrudsResult<Void> crudsResult;

  // @anchor:variables:start
  private Long timeWindowId = null;
  private String timeWindowOid = "";
  private String timeWindowName = "";
  private TimeWindowDetails timeWindowDetails = new TimeWindowDetails();
  private TimeWindowAgent timeWindowAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

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

    if (!timeWindowOid.equals("")) {
      timeWindowId = new Long(timeWindowOid);
    } else {
      timeWindowId = 0L;
    }

    crudsResult = timeWindowAgent.delete(timeWindowId);

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return Action.SUCCESS;
  }

  public JsonResult<String> getJsonResult() {
    if (crudsResult.isSuccess()) {
      return JsonResult.createValue("item deleted");
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  // @anchor:methods:start
  public TimeWindowAgent getTimeWindowAgent() {
    return timeWindowAgent;
  }

  public TimeWindowDetails getTimeWindowDetails() {
    return timeWindowDetails;
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

