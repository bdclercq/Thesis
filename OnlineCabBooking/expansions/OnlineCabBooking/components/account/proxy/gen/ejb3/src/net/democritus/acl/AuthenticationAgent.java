package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.BasicProcessingContext;

import net.democritus.sys.TaskResult;
import net.democritus.usr.UserInput;
import net.democritus.sys.UserContext;

// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
import account.settings.AccountApplicationSettings;

import net.palver.util.Options;
// anchor:custom-imports:end

/**
 * Client agent for a session in a client or web tier, connecting through a proxy
 * to the actual implementation of the task Authentication in the logic tier
 */
public class AuthenticationAgent {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationAgent.class);
  // @anchor:variables:end

  private static final AuthenticationProxy authenticationProxy = AuthenticationProxy.getAuthenticationProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Agent constructor and retrieval ==========*/

  private AuthenticationAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static AuthenticationAgent getAuthenticationAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new AuthenticationAgent(context, userContext);
  }

  public static AuthenticationAgent getAuthenticationAgent(UserContext userContext) {
    return new AuthenticationAgent(Context.from(userContext), userContext);
  }

  /*========== Handling the context ==========*/

  private <T> ParameterContext<T> createParameter(T value, String paramString) {
    return mContext.extend(getProcessingContext(paramString)).withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private BasicProcessingContext getProcessingContext(String paramString) {
    return new BasicProcessingContext("net.democritus.acl.AuthenticationImpl", paramString);
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========== Business Methods ==========*/

  public TaskResult<UserContext> perform(UserInput userInput) {
    return perform(userInput, "");
  }

  public TaskResult<UserContext> perform(UserInput userInput, String paramString) {
    TaskResult<UserContext> taskResult = null;
    ParameterContext<UserInput> parameterContext = createParameter(userInput, paramString);

    // anchor:custom-prePerform:start
    String implName = getAuthenticationImplementation().orElse("net.democritus.acl.AuthenticationImpl");
    String authenticationParams = getAuthenticationParams().orElse(paramString);
    parameterContext = ParameterContext.create(getUserContext(),
        new BasicProcessingContext(implName, authenticationParams),
        userInput);
    // anchor:custom-prePerform:end

    try {
      taskResult = authenticationProxy.perform(parameterContext);
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the Authentication task", e
        );
      }
    }

    // anchor:custom-postPerform:start
    // anchor:custom-postPerform:end

    return taskResult;
  }
  // anchor:custom-methods:start
  private Options.Option<String> getAuthenticationImplementation() {
    return AccountApplicationSettings.getProperty("authentication.implementation");
  }

  private Options.Option<String> getAuthenticationParams() {
    return AccountApplicationSettings.getProperty("authentication.params");
  }
  // anchor:custom-methods:end

}

