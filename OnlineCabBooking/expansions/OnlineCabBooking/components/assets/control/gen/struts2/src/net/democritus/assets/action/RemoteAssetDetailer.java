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

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

public class RemoteAssetDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<RemoteAssetDetails> crudsResult;

  // @anchor:variables:start
  private Long remoteAssetId = null;
  private String remoteAssetOid = "";
  private String remoteAssetName = "";
  private RemoteAssetDetails remoteAssetDetails = new RemoteAssetDetails();
  private RemoteAssetAgent remoteAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<RemoteAssetDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<RemoteAssetDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    remoteAssetAgent = createRemoteAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (remoteAssetOid.length() > 0) {
      if (shouldClone(remoteAssetOid)) {
        remoteAssetId = new Long(remoteAssetOid.substring(1));
        remoteAssetOid = "";
      } else {
        remoteAssetId = new Long(remoteAssetOid);
      }

      crudsResult = remoteAssetAgent.getDetails(remoteAssetId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        remoteAssetDetails = crudsResult.getValue();
        remoteAssetName = remoteAssetDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      remoteAssetName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(remoteAssetDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("assets", "RemoteAsset", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String remoteAssetOid) {
    return remoteAssetOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = RemoteAssetAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(remoteAssetAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = RemoteAssetDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(remoteAssetDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
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
