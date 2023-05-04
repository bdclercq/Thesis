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

import net.democritus.acl.DataAccessQuery;
// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
import net.democritus.sys.TaskResult;
// anchor:custom-imports:end

/**
 * Client agent for a session in a client or web tier, connecting through a proxy
 * to the actual implementation of the task AuthorizationManager in the logic tier
 */
public class AuthorizationManagerAgent {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AuthorizationManagerAgent.class);
  // @anchor:variables:end

  private static final AuthorizationManagerProxy authorizationManagerProxy = AuthorizationManagerProxy.getAuthorizationManagerProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Agent constructor and retrieval ==========*/

  private AuthorizationManagerAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static AuthorizationManagerAgent getAuthorizationManagerAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new AuthorizationManagerAgent(context, userContext);
  }

  public static AuthorizationManagerAgent getAuthorizationManagerAgent(UserContext userContext) {
    return new AuthorizationManagerAgent(Context.from(userContext), userContext);
  }

  /*========== Handling the context ==========*/

  private <T> ParameterContext<T> createParameter(T value, String paramString) {
    return mContext.extend(getProcessingContext(paramString)).withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private BasicProcessingContext getProcessingContext(String paramString) {
    return new BasicProcessingContext("net.democritus.acl.AuthorizationManagerImpl", paramString);
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========== Business Methods ==========*/

  // anchor:custom-methods:start
    public boolean isDataAuthorized(DataAccessQuery accessQuery) {
        TaskResult<DataAccessRights> dataAccessRights = authorizationManagerProxy.getDataAccessRights(createParameter(accessQuery, ""));
        return dataAccessRights.isSuccess() && dataAccessRights.getValue().isAllowed(accessQuery.getFunctionality());
    }


    public boolean isTaskAuthorized(DataAccessQuery accessQuery) {
        accessQuery.setFunctionality("execute");

        TaskResult<TaskAccessRights> taskAccessRights = authorizationManagerProxy.getTaskAccessRights(createParameter(accessQuery, ""));
        return taskAccessRights.isSuccess() && taskAccessRights.getValue().isCanExecute();
    }

    public TaskResult<DataAccessRights> getDataAccessRights(DataAccessQuery accessQuery) {
        return authorizationManagerProxy.getDataAccessRights(createParameter(accessQuery, ""));
    }

    public TaskResult<TaskAccessRights> getTaskAccessRights(DataAccessQuery accessQuery) {
        return authorizationManagerProxy.getTaskAccessRights(createParameter(accessQuery, ""));
    }

  // anchor:custom-methods:end

}

