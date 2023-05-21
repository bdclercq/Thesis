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

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

public class TimeWindowGroupDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<TimeWindowGroupDetails> crudsResult;

  // @anchor:variables:start
  private Long timeWindowGroupId = null;
  private String timeWindowGroupOid = "";
  private String timeWindowGroupName = "";
  private TimeWindowGroupDetails timeWindowGroupDetails = new TimeWindowGroupDetails();
  private TimeWindowGroupAgent timeWindowGroupAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<TimeWindowGroupDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<TimeWindowGroupDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    timeWindowGroupAgent = createTimeWindowGroupAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    if (!httpServletRequest.getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    String actionResult;

    if (timeWindowGroupOid.length() > 0) {
      if (shouldClone(timeWindowGroupOid)) {
        timeWindowGroupId = new Long(timeWindowGroupOid.substring(1));
        timeWindowGroupOid = "";
      } else {
        timeWindowGroupId = new Long(timeWindowGroupOid);
      }

      crudsResult = timeWindowGroupAgent.getDetails(timeWindowGroupId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        timeWindowGroupDetails = crudsResult.getValue();
        timeWindowGroupName = timeWindowGroupDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      timeWindowGroupName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(timeWindowGroupDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("workflow", "TimeWindowGroup", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String timeWindowGroupOid) {
    return timeWindowGroupOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = TimeWindowGroupAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(timeWindowGroupAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = TimeWindowGroupDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(timeWindowGroupDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public TimeWindowGroupAgent getTimeWindowGroupAgent() {
    return timeWindowGroupAgent;
  }

  public TimeWindowGroupDetails getTimeWindowGroupDetails() {
    return timeWindowGroupDetails;
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
