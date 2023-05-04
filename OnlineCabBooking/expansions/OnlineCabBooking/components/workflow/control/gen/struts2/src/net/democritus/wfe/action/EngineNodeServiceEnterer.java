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

public class EngineNodeServiceEnterer extends ActionSupport implements Preparable {

  private String engineNodeServiceOid = "";
  private String engineNodeServiceName = null;
  private EngineNodeServiceDetails engineNodeServiceDetails = new EngineNodeServiceDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeServiceEnterer.class);
  private EngineNodeServiceAgent engineNodeServiceAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineNodeServiceAgent getEngineNodeServiceAgent() {
    return engineNodeServiceAgent;
  }

  public EngineNodeServiceDetails getEngineNodeServiceDetails() {
    return engineNodeServiceDetails;
  }

  // convenience method, that skips the 'Details' part
  public EngineNodeServiceDetails getEngineNodeService() {
    return getEngineNodeServiceDetails();
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

  public void prepare() throws Exception {
    // @anchor:prepare:start
    engineNodeServiceAgent = createEngineNodeServiceAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (engineNodeServiceName != null) {
      engineNodeServiceDetails.setName(engineNodeServiceName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(engineNodeServiceDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      engineNodeServiceName = dataRef.getName();
      engineNodeServiceOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      engineNodeServiceOid = "";

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

  public EngineNodeServiceDetails getJsonRoot() {
    return engineNodeServiceDetails;
  }

  private boolean hasengineNodeServiceOid() {
    return !(engineNodeServiceOid.equals("") || engineNodeServiceOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(EngineNodeServiceDetails engineNodeServiceDetails) {
    boolean isNew;

    if (hasengineNodeServiceOid()) {
      engineNodeServiceDetails.setId(new Long(engineNodeServiceOid));
    }

    Long id = engineNodeServiceDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return engineNodeServiceAgent.create(engineNodeServiceDetails);
    } else {
      return engineNodeServiceAgent.modify(engineNodeServiceDetails);
    }
  }

  // @anchor:methods:start
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
