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
import net.democritus.wfe.EngineNodeServiceAgent;
import net.democritus.wfe.EngineNodeServiceDetails;

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

public class EngineNodeServiceDeleter extends ActionSupport implements Preparable {

  private CrudsResult<Void> crudsResult;

  // @anchor:variables:start
  private Long engineNodeServiceId = null;
  private String engineNodeServiceOid = "";
  private String engineNodeServiceName = "";
  private EngineNodeServiceDetails engineNodeServiceDetails = new EngineNodeServiceDetails();
  private EngineNodeServiceAgent engineNodeServiceAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void prepare() throws Exception {
    // @anchor:prepare:start
    engineNodeServiceAgent = createEngineNodeServiceAgent();
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

    if (!engineNodeServiceOid.equals("")) {
      engineNodeServiceId = new Long(engineNodeServiceOid);
    } else {
      engineNodeServiceId = 0L;
    }

    crudsResult = engineNodeServiceAgent.delete(engineNodeServiceId);

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
  public EngineNodeServiceAgent getEngineNodeServiceAgent() {
    return engineNodeServiceAgent;
  }

  public EngineNodeServiceDetails getEngineNodeServiceDetails() {
    return engineNodeServiceDetails;
  }

  public String getEngineNodeServiceOid() {
    return engineNodeServiceOid;
  }

  public void setEngineNodeServiceOid(String engineNodeServiceOid) {
    this.engineNodeServiceOid = engineNodeServiceOid;
  }

  public String getEngineNodeServiceName() {
    return engineNodeServiceName;
  }

  public void setEngineNodeServiceName(String engineNodeServiceName) {
    this.engineNodeServiceName = engineNodeServiceName;
  }
  private static EngineNodeServiceAgent createEngineNodeServiceAgent() {
    return EngineNodeServiceAgent.getEngineNodeServiceAgent(getContext());
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

