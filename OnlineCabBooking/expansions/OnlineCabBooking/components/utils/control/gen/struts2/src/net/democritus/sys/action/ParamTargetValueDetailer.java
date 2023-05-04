package net.democritus.sys.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
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
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParamTargetValueDetails;

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

public class ParamTargetValueDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<ParamTargetValueDetails> crudsResult;

  // @anchor:variables:start
  private Long paramTargetValueId = null;
  private String paramTargetValueOid = "";
  private ParamTargetValueDetails paramTargetValueDetails = new ParamTargetValueDetails();
  private ParamTargetValueAgent paramTargetValueAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<ParamTargetValueDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<ParamTargetValueDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    paramTargetValueAgent = createParamTargetValueAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (paramTargetValueOid.length() > 0) {
      if (shouldClone(paramTargetValueOid)) {
        paramTargetValueId = new Long(paramTargetValueOid.substring(1));
        paramTargetValueOid = "";
      } else {
        paramTargetValueId = new Long(paramTargetValueOid);
      }

      crudsResult = paramTargetValueAgent.getDetails(paramTargetValueId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        paramTargetValueDetails = crudsResult.getValue();
        actionResult = Action.SUCCESS;
      }

    } else {
      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(paramTargetValueDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("utils", "ParamTargetValue", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String paramTargetValueOid) {
    return paramTargetValueOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = ParamTargetValueAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(paramTargetValueAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = ParamTargetValueDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(paramTargetValueDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public ParamTargetValueAgent getParamTargetValueAgent() {
    return paramTargetValueAgent;
  }

  public ParamTargetValueDetails getParamTargetValueDetails() {
    return paramTargetValueDetails;
  }

  public String getParamTargetValueOid() {
    return paramTargetValueOid;
  }

  public void setParamTargetValueOid(String paramTargetValueOid) {
    this.paramTargetValueOid = paramTargetValueOid;
  }
  private static ParamTargetValueAgent createParamTargetValueAgent() {
    return ParamTargetValueAgent.getParamTargetValueAgent(getContext());
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
