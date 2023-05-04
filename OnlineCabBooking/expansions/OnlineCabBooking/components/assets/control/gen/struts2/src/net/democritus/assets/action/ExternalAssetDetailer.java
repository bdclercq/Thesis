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

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

public class ExternalAssetDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<ExternalAssetDetails> crudsResult;

  // @anchor:variables:start
  private Long externalAssetId = null;
  private String externalAssetOid = "";
  private String externalAssetName = "";
  private ExternalAssetDetails externalAssetDetails = new ExternalAssetDetails();
  private ExternalAssetAgent externalAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<ExternalAssetDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<ExternalAssetDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    externalAssetAgent = createExternalAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (externalAssetOid.length() > 0) {
      if (shouldClone(externalAssetOid)) {
        externalAssetId = new Long(externalAssetOid.substring(1));
        externalAssetOid = "";
      } else {
        externalAssetId = new Long(externalAssetOid);
      }

      crudsResult = externalAssetAgent.getDetails(externalAssetId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        externalAssetDetails = crudsResult.getValue();
        externalAssetName = externalAssetDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      externalAssetName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(externalAssetDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("assets", "ExternalAsset", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String externalAssetOid) {
    return externalAssetOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = ExternalAssetAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(externalAssetAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = ExternalAssetDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(externalAssetDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public ExternalAssetAgent getExternalAssetAgent() {
    return externalAssetAgent;
  }

  public ExternalAssetDetails getExternalAssetDetails() {
    return externalAssetDetails;
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
