package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.democritus.usr.UserInput;
import net.democritus.usr.UserLocalAgent;
import net.democritus.sys.UserContext;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProcessingContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ProjectionRef;
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task Authentication.
 */

@Stateless
@Remote(AuthenticationRemote.class)
@Local(AuthenticationLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class AuthenticationBean implements AuthenticationRemote, AuthenticationLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<UserContext> performOnTarget(ParameterContext<DataRef> targetParameter) {
    UserLocalAgent userAgent = UserLocalAgent.getUserAgent(targetParameter.getContext());
    // anchor:get-instance:start
    CrudsResult<UserInput> result = userAgent.getProjection(new ProjectionRef("input", targetParameter.getValue()));
    // anchor:get-instance:end

    if (result.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to retrieve target instance for 'User'"
        );
      }
      return TaskResult.error();
    }
    return perform(targetParameter.construct(result.getValue()));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<UserContext> perform(ParameterContext<UserInput> targetParameter) {

    TaskResult<UserContext> taskResult = null;

    // @anchor:prePerform:start
    final Authentication implementation;
    try {
      String implName = targetParameter.getProcessingContext().getImplementation();
      implementation = (Authentication) Class.forName(implName).newInstance();
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to initialize the implementation class", e
        );
      }
      return TaskResult.error(Diagnostic.error("account", "Authentication", "NO_VALID_IMPLEMENTATION_CLASS"));
    }
    // @anchor:prePerform:end
    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      // @anchor:perform:start
      taskResult = implementation.perform(targetParameter);
      // @anchor:perform:end
      // anchor:custom-perform:start
      // anchor:custom-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform Authentication on UserInput", e
        );
      }
    }

    // @anchor:postPerform:start
    // @anchor:postPerform:end
    // anchor:custom-postPerform:start
    // anchor:custom-postPerform:end

    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

