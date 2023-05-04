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

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

public class InternalAssetDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<InternalAssetDetails> crudsResult;

  // @anchor:variables:start
  private Long internalAssetId = null;
  private String internalAssetOid = "";
  private String internalAssetName = "";
  private InternalAssetDetails internalAssetDetails = new InternalAssetDetails();
  private InternalAssetAgent internalAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<InternalAssetDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<InternalAssetDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    internalAssetAgent = createInternalAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (internalAssetOid.length() > 0) {
      if (shouldClone(internalAssetOid)) {
        internalAssetId = new Long(internalAssetOid.substring(1));
        internalAssetOid = "";
      } else {
        internalAssetId = new Long(internalAssetOid);
      }

      crudsResult = internalAssetAgent.getDetails(internalAssetId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        internalAssetDetails = crudsResult.getValue();
        internalAssetName = internalAssetDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      internalAssetName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(internalAssetDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("assets", "InternalAsset", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String internalAssetOid) {
    return internalAssetOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = InternalAssetAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(internalAssetAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = InternalAssetDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(internalAssetDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public InternalAssetAgent getInternalAssetAgent() {
    return internalAssetAgent;
  }

  public InternalAssetDetails getInternalAssetDetails() {
    return internalAssetDetails;
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
