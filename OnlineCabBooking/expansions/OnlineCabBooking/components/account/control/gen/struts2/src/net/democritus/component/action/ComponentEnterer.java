package net.democritus.component.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
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

public class ComponentEnterer extends ActionSupport implements Preparable {

  private String componentOid = "";
  private String componentName = null;
  private ComponentDetails componentDetails = new ComponentDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ComponentEnterer.class);
  private ComponentAgent componentAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ComponentAgent getComponentAgent() {
    return componentAgent;
  }

  public ComponentDetails getComponentDetails() {
    return componentDetails;
  }

  // convenience method, that skips the 'Details' part
  public ComponentDetails getComponent() {
    return getComponentDetails();
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

  public void prepare() throws Exception {
    // @anchor:prepare:start
    componentAgent = createComponentAgent();
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

    if (componentName != null) {
      componentDetails.setName(componentName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(componentDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      componentName = dataRef.getName();
      componentOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      componentOid = "";

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

  public ComponentDetails getJsonRoot() {
    return componentDetails;
  }

  private boolean hascomponentOid() {
    return !(componentOid.equals("") || componentOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ComponentDetails componentDetails) {
    boolean isNew;

    if (hascomponentOid()) {
      componentDetails.setId(new Long(componentOid));
    }

    Long id = componentDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return componentAgent.create(componentDetails);
    } else {
      return componentAgent.modify(componentDetails);
    }
  }

  // @anchor:methods:start
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
