package net.democritus.acl.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.democritus.sys.Context;
import account.context.ContextRetriever;
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
import net.democritus.acl.HelpInfoAgent;
import net.democritus.acl.HelpInfoDetails;

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

public class HelpInfoDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<HelpInfoDetails> crudsResult;

  // @anchor:variables:start
  private Long helpInfoId = null;
  private String helpInfoOid = "";
  private String helpInfoName = "";
  private HelpInfoDetails helpInfoDetails = new HelpInfoDetails();
  private HelpInfoAgent helpInfoAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<HelpInfoDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<HelpInfoDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    helpInfoAgent = createHelpInfoAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (helpInfoOid.length() > 0) {
      if (shouldClone(helpInfoOid)) {
        helpInfoId = new Long(helpInfoOid.substring(1));
        helpInfoOid = "";
      } else {
        helpInfoId = new Long(helpInfoOid);
      }

      crudsResult = helpInfoAgent.getDetails(helpInfoId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        helpInfoDetails = crudsResult.getValue();
        helpInfoName = helpInfoDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      helpInfoName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(helpInfoDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("account", "HelpInfo", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String helpInfoOid) {
    return helpInfoOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = HelpInfoAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(helpInfoAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = HelpInfoDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(helpInfoDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public HelpInfoAgent getHelpInfoAgent() {
    return helpInfoAgent;
  }

  public HelpInfoDetails getHelpInfoDetails() {
    return helpInfoDetails;
  }

  public String getHelpInfoOid() {
    return helpInfoOid;
  }

  public void setHelpInfoOid(String helpInfoOid) {
    this.helpInfoOid = helpInfoOid;
  }

  public String getHelpInfoName() {
    return helpInfoName;
  }

  public void setHelpInfoName(String helpInfoName) {
    this.helpInfoName = helpInfoName;
  }
  private static HelpInfoAgent createHelpInfoAgent() {
    return HelpInfoAgent.getHelpInfoAgent(getContext());
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
