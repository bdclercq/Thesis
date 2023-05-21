package net.democritus.usr.menu.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
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
import net.democritus.usr.menu.MenuItemAgent;
import net.democritus.usr.menu.MenuItemDetails;

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

public class MenuItemEnterer extends ActionSupport implements Preparable {

  private String menuItemOid = "";
  private String menuItemName = null;
  private MenuItemDetails menuItemDetails = new MenuItemDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(MenuItemEnterer.class);
  private MenuItemAgent menuItemAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public MenuItemAgent getMenuItemAgent() {
    return menuItemAgent;
  }

  public MenuItemDetails getMenuItemDetails() {
    return menuItemDetails;
  }

  // convenience method, that skips the 'Details' part
  public MenuItemDetails getMenuItem() {
    return getMenuItemDetails();
  }

  public String getMenuItemOid() {
    return menuItemOid;
  }

  public void setMenuItemOid(String menuItemOid) {
    this.menuItemOid = menuItemOid;
  }

  public String getMenuItemName() {
    return menuItemName;
  }

  public void setMenuItemName(String menuItemName) {
    this.menuItemName = menuItemName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    menuItemAgent = createMenuItemAgent();
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

    if (menuItemName != null) {
      menuItemDetails.setName(menuItemName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(menuItemDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      menuItemName = dataRef.getName();
      menuItemOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      menuItemOid = "";

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

  public MenuItemDetails getJsonRoot() {
    return menuItemDetails;
  }

  private boolean hasmenuItemOid() {
    return !(menuItemOid.equals("") || menuItemOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(MenuItemDetails menuItemDetails) {
    boolean isNew;

    if (hasmenuItemOid()) {
      menuItemDetails.setId(new Long(menuItemOid));
    }

    Long id = menuItemDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return menuItemAgent.create(menuItemDetails);
    } else {
      return menuItemAgent.modify(menuItemDetails);
    }
  }

  // @anchor:methods:start
  private static MenuItemAgent createMenuItemAgent() {
    return MenuItemAgent.getMenuItemAgent(getContext());
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
