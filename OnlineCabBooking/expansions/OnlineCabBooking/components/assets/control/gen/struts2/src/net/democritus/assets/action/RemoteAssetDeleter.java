package net.democritus.assets.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
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
import net.democritus.assets.RemoteAssetAgent;
import net.democritus.assets.RemoteAssetDetails;

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

public class RemoteAssetDeleter extends ActionSupport implements Preparable {

  private CrudsResult<Void> crudsResult;

  // @anchor:variables:start
  private Long remoteAssetId = null;
  private String remoteAssetOid = "";
  private String remoteAssetName = "";
  private RemoteAssetDetails remoteAssetDetails = new RemoteAssetDetails();
  private RemoteAssetAgent remoteAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void prepare() throws Exception {
    // @anchor:prepare:start
    remoteAssetAgent = createRemoteAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    if (!remoteAssetOid.equals("")) {
      remoteAssetId = new Long(remoteAssetOid);
    } else {
      remoteAssetId = 0L;
    }

    crudsResult = remoteAssetAgent.delete(remoteAssetId);

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
  public RemoteAssetAgent getRemoteAssetAgent() {
    return remoteAssetAgent;
  }

  public RemoteAssetDetails getRemoteAssetDetails() {
    return remoteAssetDetails;
  }

  public String getRemoteAssetOid() {
    return remoteAssetOid;
  }

  public void setRemoteAssetOid(String remoteAssetOid) {
    this.remoteAssetOid = remoteAssetOid;
  }

  public String getRemoteAssetName() {
    return remoteAssetName;
  }

  public void setRemoteAssetName(String remoteAssetName) {
    this.remoteAssetName = remoteAssetName;
  }
  private static RemoteAssetAgent createRemoteAssetAgent() {
    return RemoteAssetAgent.getRemoteAssetAgent(getContext());
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

