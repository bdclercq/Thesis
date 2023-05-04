package net.democritus.usr.action;

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
import net.democritus.usr.UserGroupAgent;
import net.democritus.usr.UserGroupDetails;

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

public class UserGroupEnterer extends ActionSupport implements Preparable {

  private String userGroupOid = "";
  private String userGroupName = null;
  private UserGroupDetails userGroupDetails = new UserGroupDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(UserGroupEnterer.class);
  private UserGroupAgent userGroupAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public UserGroupAgent getUserGroupAgent() {
    return userGroupAgent;
  }

  public UserGroupDetails getUserGroupDetails() {
    return userGroupDetails;
  }

  // convenience method, that skips the 'Details' part
  public UserGroupDetails getUserGroup() {
    return getUserGroupDetails();
  }

  public String getUserGroupOid() {
    return userGroupOid;
  }

  public void setUserGroupOid(String userGroupOid) {
    this.userGroupOid = userGroupOid;
  }

  public String getUserGroupName() {
    return userGroupName;
  }

  public void setUserGroupName(String userGroupName) {
    this.userGroupName = userGroupName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    userGroupAgent = createUserGroupAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (userGroupName != null) {
      userGroupDetails.setName(userGroupName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(userGroupDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      userGroupName = dataRef.getName();
      userGroupOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      userGroupOid = "";

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

  public UserGroupDetails getJsonRoot() {
    return userGroupDetails;
  }

  private boolean hasuserGroupOid() {
    return !(userGroupOid.equals("") || userGroupOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(UserGroupDetails userGroupDetails) {
    boolean isNew;

    if (hasuserGroupOid()) {
      userGroupDetails.setId(new Long(userGroupOid));
    }

    Long id = userGroupDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return userGroupAgent.create(userGroupDetails);
    } else {
      return userGroupAgent.modify(userGroupDetails);
    }
  }

  // @anchor:methods:start
  private static UserGroupAgent createUserGroupAgent() {
    return UserGroupAgent.getUserGroupAgent(getContext());
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
