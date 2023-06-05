package net.democritus.web.common;

import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.UserContext;
import net.democritus.usr.UserContextWithProfile;

import static net.democritus.sys.UserContext.NO_USER_CONTEXT;

// @feature:main-screen
public class LoginRedirectHelper {

  public LoginDestination getLoginDestination(UserContext userContext) {
    String profileName = "<not existing>";
    if (userContext instanceof UserContextWithProfile) {
      profileName = ((UserContextWithProfile) userContext).getProfileName();
    }

    String url = getParamTargetValueAgent().getParamTargetValue("mainScreen", profileName);

    if (url != null && !url.isEmpty()) {
      return new LoginDestination(
          LoginResult.REDIRECT,
          url
      );
    } else {
      return new LoginDestination(
          LoginResult.SUCCESS,
          "welcome"
      );
    }
  }

  private ParamTargetValueAgent getParamTargetValueAgent() {
    if (lazy_paramTargetValueAgent == null) {
      lazy_paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(NO_USER_CONTEXT);
    }

    return lazy_paramTargetValueAgent;
  }

  private ParamTargetValueAgent lazy_paramTargetValueAgent;

  public class LoginDestination {

    private final String actionResult;
    private final String destinationUrl;

    LoginDestination(String actionResult, String destinationUrl) {
      this.actionResult = actionResult;
      this.destinationUrl = destinationUrl;
    }

    public String getActionResult() {
      return actionResult;
    }

    public String getDestinationUrl() {
      return destinationUrl;
    }

  }

}
