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
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.TaskAccessRights;

// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Client agent for a session in a client or web tier, connecting through a proxy
 * to the actual implementation of the task TaskAuthorization in the logic tier
 */
public class TaskAuthorizationAgent {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TaskAuthorizationAgent.class);
  // @anchor:variables:end

  private static final TaskAuthorizationProxy taskAuthorizationProxy = TaskAuthorizationProxy.getTaskAuthorizationProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Agent constructor and retrieval ==========*/

  private TaskAuthorizationAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static TaskAuthorizationAgent getTaskAuthorizationAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new TaskAuthorizationAgent(context, userContext);
  }

  public static TaskAuthorizationAgent getTaskAuthorizationAgent(UserContext userContext) {
    return new TaskAuthorizationAgent(Context.from(userContext), userContext);
  }

  /*========== Handling the context ==========*/

  private <T> ParameterContext<T> createParameter(T value, String paramString) {
    return mContext.extend(getProcessingContext(paramString)).withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private BasicProcessingContext getProcessingContext(String paramString) {
    return new BasicProcessingContext("net.democritus.acl.TaskAuthorizationImpl", paramString);
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========== Business Methods ==========*/

  public TaskResult<TaskAccessRights> perform(DataAccessQuery dataAccessQuery) {
    return perform(dataAccessQuery, "");
  }

  public TaskResult<TaskAccessRights> perform(DataAccessQuery dataAccessQuery, String paramString) {
    TaskResult<TaskAccessRights> taskResult = null;
    ParameterContext<DataAccessQuery> parameterContext = createParameter(dataAccessQuery, paramString);

    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      taskResult = taskAuthorizationProxy.perform(parameterContext);
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the TaskAuthorization task", e
        );
      }
    }

    // anchor:custom-postPerform:start
    // anchor:custom-postPerform:end

    return taskResult;
  }
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

