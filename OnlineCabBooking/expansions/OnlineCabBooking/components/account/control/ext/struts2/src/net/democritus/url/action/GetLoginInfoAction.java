package net.democritus.url.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.json.JsonResult;
import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.usr.InternalUserContext;
import net.democritus.usr.LoginInformation;
import net.democritus.usr.UserAgent;
import net.democritus.usr.UserContextWithProfile;
import net.democritus.usr.UserDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

// @feature:user-info
public class GetLoginInfoAction extends ActionSupport {

  private static final Logger logger = LoggerFactory.getLogger(GetLoginInfoAction.class);

  private LoginInformation loginInfo;
  private boolean error = false;

  public String execute() throws Exception {
    if (!ServletActionContext.getRequest().getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }

    UserContext userContext = getUserContext();

    try {
      if (userContext instanceof InternalUserContext) {
        loginInfo = getLoginInformationFromDatabase((InternalUserContext) userContext);
      } else {
        loginInfo = getLoginInformationFromUserContext(userContext);
      }
    } catch (Exception e) {
      error = true;
    }

    return Action.SUCCESS;
  }

  private LoginInformation getLoginInformationFromDatabase(InternalUserContext userContext) {
    UserDetails user = getUser(userContext);

    LoginInformation loginInfo = new LoginInformation();
    loginInfo.setUser(user);
    loginInfo.setUserPageUrl(getUserPageUrl(user));
    loginInfo.setAccountPageUrl(getAccountPageUrl(user));
    loginInfo.setAdmin(isAdmin(user, userContext));

    return loginInfo;
  }

  private LoginInformation getLoginInformationFromUserContext(UserContext userContext) {
    UserDetails user = new UserDetails();
    user.setName(userContext.getUserName());
    user.setLanguage(userContext.getLanguage());
    if (userContext instanceof UserContextWithProfile) {
      user.setProfile(DataRef.withName(((UserContextWithProfile) userContext).getProfileName()));
    }

    LoginInformation loginInfo = new LoginInformation();
    loginInfo.setUser(user);
    loginInfo.setUserPageUrl("welcome");
    loginInfo.setAccountPageUrl("welcome");
    loginInfo.setAdmin(false);

    return loginInfo;
  }

  private UserDetails getUser(UserContext userContext) {
    Long userId = userContext.getId();
    UserAgent userAgent = UserAgent.getUserAgent(Context.from(userContext));
    CrudsResult<UserDetails> details = userAgent.getDetails(userId);

    if (details.isError()) {
      logger.error("User logged in with unknown user");
      throw new IllegalStateException();
    }

    return details.getValue();
  }

  private String getUserPageUrl(UserDetails user) {
    return String.format("account/user/%s", user.getId());
  }

  private String getAccountPageUrl(UserDetails userDetails) {
    Long accountId = userDetails.getAccount().getId();
    return String.format("account/account/%s", accountId);
  }

  private boolean isAdmin(UserDetails userDetails, UserContext userContext) {
    String profileName;
    if (userContext instanceof UserContextWithProfile) {
      profileName = ((UserContextWithProfile) userContext).getProfileName();
    } else {
      profileName = userDetails.getProfileDetails().getName();
    }
    return "admin".equals(profileName) || "super".equals(profileName);
  }

  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }

  public JsonResult<LoginInformation> getJsonResult() {
    if (error) {
      return JsonResult.createError("Could not load login info");
    } else {
      return JsonResult.createValue(loginInfo);
    }
  }

}
