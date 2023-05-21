package net.democritus.usr.menu.action;

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
import net.democritus.usr.menu.MenuAgent;
import net.democritus.usr.menu.MenuDetails;

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

public class MenuDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<MenuDetails> crudsResult;

  // @anchor:variables:start
  private Long menuId = null;
  private String menuOid = "";
  private String menuName = "";
  private MenuDetails menuDetails = new MenuDetails();
  private MenuAgent menuAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<MenuDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<MenuDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    menuAgent = createMenuAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    if (!httpServletRequest.getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    String actionResult;

    if (menuOid.length() > 0) {
      if (shouldClone(menuOid)) {
        menuId = new Long(menuOid.substring(1));
        menuOid = "";
      } else {
        menuId = new Long(menuOid);
      }

      crudsResult = menuAgent.getDetails(menuId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        menuDetails = crudsResult.getValue();
        menuName = menuDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      menuName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(menuDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("account", "Menu", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String menuOid) {
    return menuOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = MenuAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(menuAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = MenuDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(menuDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public MenuAgent getMenuAgent() {
    return menuAgent;
  }

  public MenuDetails getMenuDetails() {
    return menuDetails;
  }

  public String getMenuOid() {
    return menuOid;
  }

  public void setMenuOid(String menuOid) {
    this.menuOid = menuOid;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
  private static MenuAgent createMenuAgent() {
    return MenuAgent.getMenuAgent(getContext());
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
