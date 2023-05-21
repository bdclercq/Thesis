package net.democritus.component.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.democritus.sys.Context;
import account.context.ContextRetriever;
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
import net.democritus.component.ComponentAgent;
import net.democritus.component.ComponentDetails;

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

public class ComponentDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<ComponentDetails> crudsResult;

  // @anchor:variables:start
  private Long componentId = null;
  private String componentOid = "";
  private String componentName = "";
  private ComponentDetails componentDetails = new ComponentDetails();
  private ComponentAgent componentAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<ComponentDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<ComponentDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    componentAgent = createComponentAgent();
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

    if (componentOid.length() > 0) {
      if (shouldClone(componentOid)) {
        componentId = new Long(componentOid.substring(1));
        componentOid = "";
      } else {
        componentId = new Long(componentOid);
      }

      crudsResult = componentAgent.getDetails(componentId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        componentDetails = crudsResult.getValue();
        componentName = componentDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      componentName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(componentDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("account", "Component", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String componentOid) {
    return componentOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = ComponentAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(componentAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = ComponentDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(componentDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public ComponentAgent getComponentAgent() {
    return componentAgent;
  }

  public ComponentDetails getComponentDetails() {
    return componentDetails;
  }

  public String getComponentOid() {
    return componentOid;
  }

  public void setComponentOid(String componentOid) {
    this.componentOid = componentOid;
  }

  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }
  private static ComponentAgent createComponentAgent() {
    return ComponentAgent.getComponentAgent(getContext());
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
