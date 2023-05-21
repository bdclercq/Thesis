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
import net.democritus.usr.ProfileAgent;
import net.democritus.usr.ProfileDetails;

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

public class ProfileEnterer extends ActionSupport implements Preparable {

  private String profileOid = "";
  private String profileName = null;
  private ProfileDetails profileDetails = new ProfileDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ProfileEnterer.class);
  private ProfileAgent profileAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ProfileAgent getProfileAgent() {
    return profileAgent;
  }

  public ProfileDetails getProfileDetails() {
    return profileDetails;
  }

  // convenience method, that skips the 'Details' part
  public ProfileDetails getProfile() {
    return getProfileDetails();
  }

  public String getProfileOid() {
    return profileOid;
  }

  public void setProfileOid(String profileOid) {
    this.profileOid = profileOid;
  }

  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    profileAgent = createProfileAgent();
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

    if (profileName != null) {
      profileDetails.setName(profileName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(profileDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      profileName = dataRef.getName();
      profileOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      profileOid = "";

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

  public ProfileDetails getJsonRoot() {
    return profileDetails;
  }

  private boolean hasprofileOid() {
    return !(profileOid.equals("") || profileOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ProfileDetails profileDetails) {
    boolean isNew;

    if (hasprofileOid()) {
      profileDetails.setId(new Long(profileOid));
    }

    Long id = profileDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return profileAgent.create(profileDetails);
    } else {
      return profileAgent.modify(profileDetails);
    }
  }

  // @anchor:methods:start
  private static ProfileAgent createProfileAgent() {
    return ProfileAgent.getProfileAgent(getContext());
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
