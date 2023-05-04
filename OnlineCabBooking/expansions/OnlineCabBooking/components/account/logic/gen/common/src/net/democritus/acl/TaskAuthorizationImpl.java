package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.TaskAccessRights;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
// anchor:custom-imports:start
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

import java.util.List;
// anchor:custom-imports:end

/**
 * Example implementation delegate for the task element TaskAuthorization.
 */
public class TaskAuthorizationImpl implements TaskAuthorization {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TaskAuthorizationImpl.class);
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TaskResult<TaskAccessRights> perform(ParameterContext<DataAccessQuery> targetParameter) {
    UserContext userContext = targetParameter.getUserContext();
    Context context = targetParameter.getContext();
    TaskResult<TaskAccessRights> taskResult = TaskResult.error();
    if (logger.isDebugEnabled()) {
      logger.debug(
          "Performing the TaskAuthorizationImpl implementation"
      );
    }
    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
      ElementAccessManager elementAccessManager = new ElementAccessManager(context);
      DataAccessQuery dataAccessQuery = targetParameter.getValue();
      String element = dataAccessQuery.getElement();
      TaskAccessRights taskAccessRights = new TaskAccessRights();
      taskAccessRights.setTaskName(element);
      taskAccessRights.setCanExecute(true);
      FunctionalityDescriptor functionalityDescriptor = new FunctionalityDescriptor()
          .setComponent(dataAccessQuery.getComponent())
          .setElement(element)
          .setFunctionality(dataAccessQuery.getFunctionality());
      List<DataAccessDetails> userDataAccessList = elementAccessManager.getUserTaskAccess(functionalityDescriptor);
      List<DataAccessDetails> userGroupDataAccessList = elementAccessManager.getUserGroupTaskAccess(functionalityDescriptor);
      List<DataAccessDetails> profileDataAccessList = elementAccessManager.getProfileTaskAccess(functionalityDescriptor);
      //Handle accessRights from less specific to more specific.
      taskAccessRights = processDataAccess(taskAccessRights, userGroupDataAccessList);
      taskAccessRights = processDataAccess(taskAccessRights, profileDataAccessList);
      taskAccessRights = processDataAccess(taskAccessRights, userDataAccessList);
      taskResult = TaskResult.success(taskAccessRights);
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
            "Exception in TaskAuthorizationImpl implementation", e
        );
      }
    }
    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  private TaskAccessRights processDataAccess(TaskAccessRights taskAccess, List<DataAccessDetails> dataAccessList) throws Exception {
    if (isDefined(dataAccessList)) {
      boolean canExecute = getRights(dataAccessList);
      taskAccess.setCanExecute(canExecute);
    }
    return taskAccess;
  }

  private boolean isDefined(List<DataAccessDetails> dataAccessList) {
    return (dataAccessList != null && !dataAccessList.isEmpty());
  }

  private boolean getRights(List<DataAccessDetails> dataAccessList) throws Exception {
    if (!isDefined(dataAccessList)) {
      throw new Exception("no rights given for current user");
    }
    boolean isAuthorized = false;
    for (DataAccessDetails dataAccessDetails : dataAccessList) {
      String authorization = dataAccessDetails.getAuthorized();
      isAuthorized = isAuthorized || isAuthorized(authorization);
    }
    return isAuthorized;
  }

  private boolean isAuthorized(String authorized) {
    return authorized == null || !authorized.toLowerCase().startsWith("n");
  }
  // anchor:custom-methods:end

}

