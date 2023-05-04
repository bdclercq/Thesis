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
import net.democritus.usr.menu.ScreenProfileAgent;
import net.democritus.usr.menu.ScreenProfileDetails;

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

public class ScreenProfileEnterer extends ActionSupport implements Preparable {

  private String screenProfileOid = "";
  private ScreenProfileDetails screenProfileDetails = new ScreenProfileDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ScreenProfileEnterer.class);
  private ScreenProfileAgent screenProfileAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ScreenProfileAgent getScreenProfileAgent() {
    return screenProfileAgent;
  }

  public ScreenProfileDetails getScreenProfileDetails() {
    return screenProfileDetails;
  }

  // convenience method, that skips the 'Details' part
  public ScreenProfileDetails getScreenProfile() {
    return getScreenProfileDetails();
  }

  public String getScreenProfileOid() {
    return screenProfileOid;
  }

  public void setScreenProfileOid(String screenProfileOid) {
    this.screenProfileOid = screenProfileOid;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    screenProfileAgent = createScreenProfileAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(screenProfileDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      screenProfileOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      screenProfileOid = "";

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

  public ScreenProfileDetails getJsonRoot() {
    return screenProfileDetails;
  }

  private boolean hasscreenProfileOid() {
    return !(screenProfileOid.equals("") || screenProfileOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ScreenProfileDetails screenProfileDetails) {
    boolean isNew;

    if (hasscreenProfileOid()) {
      screenProfileDetails.setId(new Long(screenProfileOid));
    }

    Long id = screenProfileDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return screenProfileAgent.create(screenProfileDetails);
    } else {
      return screenProfileAgent.modify(screenProfileDetails);
    }
  }

  // @anchor:methods:start
  private static ScreenProfileAgent createScreenProfileAgent() {
    return ScreenProfileAgent.getScreenProfileAgent(getContext());
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