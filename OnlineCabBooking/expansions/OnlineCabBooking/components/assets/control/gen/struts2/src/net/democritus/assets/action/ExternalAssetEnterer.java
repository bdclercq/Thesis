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
import net.democritus.assets.ExternalAssetAgent;
import net.democritus.assets.ExternalAssetDetails;

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

public class ExternalAssetEnterer extends ActionSupport implements Preparable {

  private String externalAssetOid = "";
  private String externalAssetName = null;
  private ExternalAssetDetails externalAssetDetails = new ExternalAssetDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ExternalAssetEnterer.class);
  private ExternalAssetAgent externalAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ExternalAssetAgent getExternalAssetAgent() {
    return externalAssetAgent;
  }

  public ExternalAssetDetails getExternalAssetDetails() {
    return externalAssetDetails;
  }

  // convenience method, that skips the 'Details' part
  public ExternalAssetDetails getExternalAsset() {
    return getExternalAssetDetails();
  }

  public String getExternalAssetOid() {
    return externalAssetOid;
  }

  public void setExternalAssetOid(String externalAssetOid) {
    this.externalAssetOid = externalAssetOid;
  }

  public String getExternalAssetName() {
    return externalAssetName;
  }

  public void setExternalAssetName(String externalAssetName) {
    this.externalAssetName = externalAssetName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    externalAssetAgent = createExternalAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (externalAssetName != null) {
      externalAssetDetails.setName(externalAssetName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(externalAssetDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      externalAssetName = dataRef.getName();
      externalAssetOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      externalAssetOid = "";

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

  public ExternalAssetDetails getJsonRoot() {
    return externalAssetDetails;
  }

  private boolean hasexternalAssetOid() {
    return !(externalAssetOid.equals("") || externalAssetOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ExternalAssetDetails externalAssetDetails) {
    boolean isNew;

    if (hasexternalAssetOid()) {
      externalAssetDetails.setId(new Long(externalAssetOid));
    }

    Long id = externalAssetDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return externalAssetAgent.create(externalAssetDetails);
    } else {
      return externalAssetAgent.modify(externalAssetDetails);
    }
  }

  // @anchor:methods:start
  private static ExternalAssetAgent createExternalAssetAgent() {
    return ExternalAssetAgent.getExternalAssetAgent(getContext());
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
