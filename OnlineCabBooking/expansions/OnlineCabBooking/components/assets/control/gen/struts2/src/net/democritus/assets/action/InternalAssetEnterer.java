package net.democritus.assets.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import assets.context.ContextRetriever;
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
import net.democritus.assets.InternalAssetAgent;
import net.democritus.assets.InternalAssetDetails;

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

public class InternalAssetEnterer extends ActionSupport implements Preparable {

  private String internalAssetOid = "";
  private String internalAssetName = null;
  private InternalAssetDetails internalAssetDetails = new InternalAssetDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(InternalAssetEnterer.class);
  private InternalAssetAgent internalAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public InternalAssetAgent getInternalAssetAgent() {
    return internalAssetAgent;
  }

  public InternalAssetDetails getInternalAssetDetails() {
    return internalAssetDetails;
  }

  // convenience method, that skips the 'Details' part
  public InternalAssetDetails getInternalAsset() {
    return getInternalAssetDetails();
  }

  public String getInternalAssetOid() {
    return internalAssetOid;
  }

  public void setInternalAssetOid(String internalAssetOid) {
    this.internalAssetOid = internalAssetOid;
  }

  public String getInternalAssetName() {
    return internalAssetName;
  }

  public void setInternalAssetName(String internalAssetName) {
    this.internalAssetName = internalAssetName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    internalAssetAgent = createInternalAssetAgent();
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

    if (internalAssetName != null) {
      internalAssetDetails.setName(internalAssetName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(internalAssetDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      internalAssetName = dataRef.getName();
      internalAssetOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      internalAssetOid = "";

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

  public InternalAssetDetails getJsonRoot() {
    return internalAssetDetails;
  }

  private boolean hasinternalAssetOid() {
    return !(internalAssetOid.equals("") || internalAssetOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(InternalAssetDetails internalAssetDetails) {
    boolean isNew;

    if (hasinternalAssetOid()) {
      internalAssetDetails.setId(new Long(internalAssetOid));
    }

    Long id = internalAssetDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return internalAssetAgent.create(internalAssetDetails);
    } else {
      return internalAssetAgent.modify(internalAssetDetails);
    }
  }

  // @anchor:methods:start
  private static InternalAssetAgent createInternalAssetAgent() {
    return InternalAssetAgent.getInternalAssetAgent(getContext());
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
