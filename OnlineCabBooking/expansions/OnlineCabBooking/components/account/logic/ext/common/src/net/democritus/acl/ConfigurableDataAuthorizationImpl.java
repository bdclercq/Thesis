package net.democritus.acl;

import net.democritus.sys.Context;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.TaskResult;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

/**
 * A default implementation for the task element DataAuthorization. This implementation defaults to denying all rights,
 * but can be configured by setting the ParamTargetValue {@code defaultAccessRights} to {@code allowAll}, such that
 * it will allow all rights by default. When denying by default, the DataAccess element should be used to define rules
 * to selectively assign permissions. When allowing by default, the DataAccess element should be used to define rules
 * to selectively revoke permissions.
 */
public class ConfigurableDataAuthorizationImpl implements DataAuthorization {

  private static final Logger logger = LoggerFactory.getLogger(ConfigurableDataAuthorizationImpl.class);

  public TaskResult<DataAccessRights> perform(ParameterContext<DataAccessQuery> targetParameter) {
    Context context = targetParameter.getContext();
    TaskResult<DataAccessRights> taskResult;
    if (logger.isDebugEnabled()) {
      logger.debug(
          "Performing the ConfigurableDataAuthorizationImpl implementation"
      );
    }
    try {
      final DataAuthorizationProcessor dataAuthorizationProcessor = new DataAuthorizationProcessor(
          context, getAccessRightStrategy(context));
      taskResult = TaskResult.success(dataAuthorizationProcessor.processQuery(targetParameter.getValue()));
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception in ConfigurableDataAuthorizationImpl implementation", e
        );
      }
    }
    return taskResult;
  }

  private static AccessRightStrategy getAccessRightStrategy(Context context) {
    final String paramTargetValue = ParamTargetValueAgent.getParamTargetValueAgent(context)
        .getParamTargetValue("defaultAccessRights", "default");
    return "allowAll".equalsIgnoreCase(paramTargetValue)
        ? new AllowDefaultAccessRightStrategy()
        : new DenyDefaultAccessRightStrategy();
  }

}
