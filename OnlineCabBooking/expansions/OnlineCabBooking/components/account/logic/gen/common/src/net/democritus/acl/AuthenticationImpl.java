package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.usr.UserInput;
import net.democritus.sys.UserContext;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
// anchor:custom-imports:start
import net.democritus.usr.AuthenticationDetails;
import net.democritus.usr.AuthenticationResult;
import net.democritus.usr.UserLocalAgent;
import net.palver.util.Options;
// anchor:custom-imports:end

/**
 * Example implementation delegate for the task element Authentication.
 */
public class AuthenticationImpl implements Authentication {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationImpl.class);
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TaskResult<UserContext> perform(ParameterContext<UserInput> targetParameter) {
    UserContext userContext = targetParameter.getUserContext();
    Context context = targetParameter.getContext();
    TaskResult<UserContext> taskResult = TaskResult.error();
    if (logger.isDebugEnabled()) {
      logger.debug(
          "Performing the AuthenticationImpl implementation"
      );
    }
    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
      final UserLocalAgent agent = UserLocalAgent.getUserAgent(context);
      final AuthenticationResult result = agent.authenticate(AuthenticationDetails.from(targetParameter.getValue()));
      if (result.isError()) {
        return TaskResult.error(result.getDiagnostics());
      } else {
        Options.Option<UserContext> userContextOption = result.getContext().getContext(UserContext.class);
        if (userContextOption.isEmpty()) {
          return TaskResult.error();
        }
        return TaskResult.success(userContextOption.getValue());
      }
      // anchor:custom-perform:end
      // @anchor:post-perform:start
      // @anchor:post-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception in AuthenticationImpl implementation", e
        );
      }
    }
    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

