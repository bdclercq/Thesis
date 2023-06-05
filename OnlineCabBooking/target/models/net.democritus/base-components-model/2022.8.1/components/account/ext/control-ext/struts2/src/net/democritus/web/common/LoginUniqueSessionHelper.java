package net.democritus.web.common;

import net.democritus.sys.UserContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

// @feature:unique-session
public class LoginUniqueSessionHelper {

  private static final Logger logger = LoggerFactory.getLogger(LoginUniqueSessionHelper.class);

  private final UniqueSessionRegistrar uniqueSessionRegistrar = new UniqueSessionRegistrar();
  private final AuthenticationParams authenticationParams = new AuthenticationParams();

  public void validateUniqueSession(UserContext userContext) throws DuplicateUserSessionException {
    if (authenticationParams.useUniqueSession()) {
      try {
        uniqueSessionRegistrar.registerSession(userContext);
      } catch (DuplicateUserSessionException e) {
        if (logger.isInfoEnabled()) {
          logger.info("Duplicate login for " + userContext.getUserName());
        }
        throw e;
      }
    }
  }

}
