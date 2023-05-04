// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.web.action;

import net.democritus.acl.AuthenticationAgent;
import net.democritus.sys.Context;
import net.democritus.sys.TaskResult;
import net.democritus.sys.UserContext;
import net.democritus.usr.UserInput;
import net.democritus.web.common.DuplicateUserSessionException;
import net.democritus.web.common.LoginRedirectHelper;
import net.democritus.web.common.LoginResult;
import net.democritus.web.common.LoginUniqueSessionHelper;
import net.democritus.web.common.LoginUserSessionHelper;
import net.democritus.web.common.SessionKey;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;


public class LoginValidatorAction {

  private static final Logger logger = LoggerFactory.getLogger(LoginValidatorAction.class);

  private String destinationUrl;

  private final LoginUniqueSessionHelper uniqueSessionHelper = new LoginUniqueSessionHelper();
  private final LoginUserSessionHelper userSessionHelper = new LoginUserSessionHelper();
  private final LoginRedirectHelper redirectHelper = new LoginRedirectHelper();

  private String username;
  private String password;

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String execute() throws Exception {
    try {
      if (username == null) { // to 'welcome' without parameters
        return checkIfValidSessionExists();
      } else { // to 'welcome' with parameters provided
        return initiateNewSession(username, password);
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to find the user entry", e
        );
      }
    }
    return Action.LOGIN;
  }

  private String initiateNewSession(String username, String password) {
    AuthenticationAgent agent = AuthenticationAgent.getAuthenticationAgent(Context.emptyContext());
    UserInput userInput = new UserInput();
    userInput.setName(username);
    userInput.setPassword(password);
    TaskResult<UserContext> taskResult = agent.perform(userInput);
    if (taskResult.isError()) {
      return LoginResult.FAILED;
    }
    UserContext userContext = taskResult.getValue();

    try {
      uniqueSessionHelper.validateUniqueSession(userContext);
      userSessionHelper.createUserSession(userContext);
      LoginRedirectHelper.LoginDestination destination = redirectHelper.getLoginDestination(userContext);

      destinationUrl = destination.getDestinationUrl();

      return destination.getActionResult();
    } catch (DuplicateUserSessionException e) {
      userSessionHelper.createTemporaryUserSession(userContext);
      return LoginResult.DUPLICATE;
    }
  }

  private String checkIfValidSessionExists() {
    Map<String, Object> session = ActionContext.getContext().getSession();
    UserContext userContext = (UserContext) session.get(SessionKey.USERCONTEXT.getKey());

    if (userContext == null) {
      if (logger.isInfoEnabled()) {
        logger.info(
            "no valid session!"
        );
      }
      return Action.LOGIN;
    } else {
      if (logger.isInfoEnabled()) {
        logger.info(
            "valid session for user = '" + userContext.getUserName() + "'"
        );
      }

      LoginRedirectHelper.LoginDestination destination = redirectHelper.getLoginDestination(userContext);

      destinationUrl = destination.getDestinationUrl();

      return destination.getActionResult();
    }
  }

  public String getDestinationUrl() {
    return destinationUrl;
  }

}
