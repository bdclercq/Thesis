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
import net.democritus.wfe.EngineServiceAgent;
import net.democritus.wfe.EngineServiceDetails;

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

public class EngineServiceEnterer extends ActionSupport implements Preparable {

  private String engineServiceOid = "";
  private String engineServiceName = null;
  private EngineServiceDetails engineServiceDetails = new EngineServiceDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineServiceEnterer.class);
  private EngineServiceAgent engineServiceAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineServiceAgent getEngineServiceAgent() {
    return engineServiceAgent;
  }

  public EngineServiceDetails getEngineServiceDetails() {
    return engineServiceDetails;
  }

  // convenience method, that skips the 'Details' part
  public EngineServiceDetails getEngineService() {
    return getEngineServiceDetails();
  }

  public String getEngineServiceOid() {
    return engineServiceOid;
  }

  public void setEngineServiceOid(String engineServiceOid) {
    this.engineServiceOid = engineServiceOid;
  }

  public String getEngineServiceName() {
    return engineServiceName;
  }

  public void setEngineServiceName(String engineServiceName) {
    this.engineServiceName = engineServiceName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    engineServiceAgent = createEngineServiceAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (engineServiceName != null) {
      engineServiceDetails.setName(engineServiceName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(engineServiceDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      engineServiceName = dataRef.getName();
      engineServiceOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      engineServiceOid = "";

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

  public EngineServiceDetails getJsonRoot() {
    return engineServiceDetails;
  }

  private boolean hasengineServiceOid() {
    return !(engineServiceOid.equals("") || engineServiceOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(EngineServiceDetails engineServiceDetails) {
    boolean isNew;

    if (hasengineServiceOid()) {
      engineServiceDetails.setId(new Long(engineServiceOid));
    }

    Long id = engineServiceDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return engineServiceAgent.create(engineServiceDetails);
    } else {
      return engineServiceAgent.modify(engineServiceDetails);
    }
  }

  // @anchor:methods:start
  private static EngineServiceAgent createEngineServiceAgent() {
    return EngineServiceAgent.getEngineServiceAgent(getContext());
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
