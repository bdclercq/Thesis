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
import net.democritus.assets.AssetAgent;
import net.democritus.assets.AssetDetails;

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

public class AssetEnterer extends ActionSupport implements Preparable {

  private String assetOid = "";
  private String assetName = null;
  private AssetDetails assetDetails = new AssetDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AssetEnterer.class);
  private AssetAgent assetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public AssetAgent getAssetAgent() {
    return assetAgent;
  }

  public AssetDetails getAssetDetails() {
    return assetDetails;
  }

  // convenience method, that skips the 'Details' part
  public AssetDetails getAsset() {
    return getAssetDetails();
  }

  public String getAssetOid() {
    return assetOid;
  }

  public void setAssetOid(String assetOid) {
    this.assetOid = assetOid;
  }

  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    assetAgent = createAssetAgent();
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

    if (assetName != null) {
      assetDetails.setName(assetName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(assetDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      assetName = dataRef.getName();
      assetOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      assetOid = "";

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

  public AssetDetails getJsonRoot() {
    return assetDetails;
  }

  private boolean hasassetOid() {
    return !(assetOid.equals("") || assetOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(AssetDetails assetDetails) {
    boolean isNew;

    if (hasassetOid()) {
      assetDetails.setId(new Long(assetOid));
    }

    Long id = assetDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return assetAgent.create(assetDetails);
    } else {
      return assetAgent.modify(assetDetails);
    }
  }

  // @anchor:methods:start
  private static AssetAgent createAssetAgent() {
    return AssetAgent.getAssetAgent(getContext());
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
