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

public class MenuEnterer extends ActionSupport implements Preparable {

  private String menuOid = "";
  private String menuName = null;
  private MenuDetails menuDetails = new MenuDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(MenuEnterer.class);
  private MenuAgent menuAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public MenuAgent getMenuAgent() {
    return menuAgent;
  }

  public MenuDetails getMenuDetails() {
    return menuDetails;
  }

  // convenience method, that skips the 'Details' part
  public MenuDetails getMenu() {
    return getMenuDetails();
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

  public void prepare() throws Exception {
    // @anchor:prepare:start
    menuAgent = createMenuAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (menuName != null) {
      menuDetails.setName(menuName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(menuDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      menuName = dataRef.getName();
      menuOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      menuOid = "";

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

  public MenuDetails getJsonRoot() {
    return menuDetails;
  }

  private boolean hasmenuOid() {
    return !(menuOid.equals("") || menuOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(MenuDetails menuDetails) {
    boolean isNew;

    if (hasmenuOid()) {
      menuDetails.setId(new Long(menuOid));
    }

    Long id = menuDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return menuAgent.create(menuDetails);
    } else {
      return menuAgent.modify(menuDetails);
    }
  }

  // @anchor:methods:start
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
