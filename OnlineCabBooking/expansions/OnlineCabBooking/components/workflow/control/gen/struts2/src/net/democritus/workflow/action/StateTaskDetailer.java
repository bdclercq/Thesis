package net.democritus.workflow.action;

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
import net.democritus.workflow.StateTaskAgent;
import net.democritus.workflow.StateTaskDetails;

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

public class StateTaskDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<StateTaskDetails> crudsResult;

  // @anchor:variables:start
  private Long stateTaskId = null;
  private String stateTaskOid = "";
  private String stateTaskName = "";
  private StateTaskDetails stateTaskDetails = new StateTaskDetails();
  private StateTaskAgent stateTaskAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<StateTaskDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<StateTaskDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    stateTaskAgent = createStateTaskAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (stateTaskOid.length() > 0) {
      if (shouldClone(stateTaskOid)) {
        stateTaskId = new Long(stateTaskOid.substring(1));
        stateTaskOid = "";
      } else {
        stateTaskId = new Long(stateTaskOid);
      }

      crudsResult = stateTaskAgent.getDetails(stateTaskId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        stateTaskDetails = crudsResult.getValue();
        stateTaskName = stateTaskDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      stateTaskName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(stateTaskDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("workflow", "StateTask", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String stateTaskOid) {
    return stateTaskOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = StateTaskAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(stateTaskAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = StateTaskDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(stateTaskDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public StateTaskAgent getStateTaskAgent() {
    return stateTaskAgent;
  }

  public StateTaskDetails getStateTaskDetails() {
    return stateTaskDetails;
  }

  public String getStateTaskOid() {
    return stateTaskOid;
  }

  public void setStateTaskOid(String stateTaskOid) {
    this.stateTaskOid = stateTaskOid;
  }

  public String getStateTaskName() {
    return stateTaskName;
  }

  public void setStateTaskName(String stateTaskName) {
    this.stateTaskName = stateTaskName;
  }
  private static StateTaskAgent createStateTaskAgent() {
    return StateTaskAgent.getStateTaskAgent(getContext());
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
