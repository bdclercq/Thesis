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

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

public class TimeWindowDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<TimeWindowDetails> crudsResult;

  // @anchor:variables:start
  private Long timeWindowId = null;
  private String timeWindowOid = "";
  private String timeWindowName = "";
  private TimeWindowDetails timeWindowDetails = new TimeWindowDetails();
  private TimeWindowAgent timeWindowAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<TimeWindowDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<TimeWindowDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    timeWindowAgent = createTimeWindowAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (timeWindowOid.length() > 0) {
      if (shouldClone(timeWindowOid)) {
        timeWindowId = new Long(timeWindowOid.substring(1));
        timeWindowOid = "";
      } else {
        timeWindowId = new Long(timeWindowOid);
      }

      crudsResult = timeWindowAgent.getDetails(timeWindowId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        timeWindowDetails = crudsResult.getValue();
        timeWindowName = timeWindowDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      timeWindowName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(timeWindowDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("workflow", "TimeWindow", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String timeWindowOid) {
    return timeWindowOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = TimeWindowAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(timeWindowAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = TimeWindowDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(timeWindowDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
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
