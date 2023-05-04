package net.democritus.acl;

import net.democritus.sys.Context;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A default implementation for the task element TaskAuthorization.
 */
final class TaskAuthorizationProcessor {

  private static final Logger logger = LoggerFactory.getLogger(TaskAuthorizationProcessor.class);

  private final Context context;
  private final AccessRightStrategy accessRightStrategy;

  public TaskAuthorizationProcessor(Context context, AccessRightStrategy accessRightStrategy) {
    this.context = context;
    this.accessRightStrategy = accessRightStrategy;
  }

  public TaskAccessRights processQuery(DataAccessQuery dataAccessQuery) throws Exception {
    final ElementAccessManager elementAccessManager = new ElementAccessManager(context);
    final TaskAccessRights TaskAccessRights = new TaskAccessRights(accessRightStrategy);

    final FunctionalityDescriptor functionalityDescriptor = new FunctionalityDescriptor()
        .setComponent(dataAccessQuery.getComponent())
        .setElement(dataAccessQuery.getElement())
        .setFunctionality(dataAccessQuery.getFunctionality());

    // Update rights in order, so they can be overridden at a more specific level
    updateAccessRights(TaskAccessRights, elementAccessManager.getUserGroupDataAccess(functionalityDescriptor));
    updateAccessRights(TaskAccessRights, elementAccessManager.getProfileDataAccess(functionalityDescriptor));
    updateAccessRights(TaskAccessRights, elementAccessManager.getUserDataAccess(functionalityDescriptor));

    return TaskAccessRights;
  }

  private void updateAccessRights(TaskAccessRights taskAccess, List<DataAccessDetails> dataAccessList) {
    Boolean isAuthorized = combineRights(dataAccessList);
    if (isAuthorized != null) {
      taskAccess.setCanExecute(isAuthorized);
    }
  }

  private Boolean combineRights(List<DataAccessDetails> dataAccessList) {
    Boolean result = null;
    for (DataAccessDetails dataAccessDetails : dataAccessList) {
      Boolean isAuthorized = getAuthorization(dataAccessDetails.getAuthorized());
      if (isAuthorized == null) {
        if (logger.isWarnEnabled()) {
          logger.warn(
              "DataAccess rule for task element '" + dataAccessDetails.getElement() +
                  "' has invalid authorization value '" + dataAccessDetails.getAuthorized() +
                  "'. Should be 'true' or 'false'."
          );
        }

        // Invalid access rule
        continue;
      }

      if (result != null) {
        result = accessRightStrategy.combineRights(result, isAuthorized);
      } else {
        result = isAuthorized;
      }
    }

    return result;
  }

  private static Boolean getAuthorization(String authorized) {
    if (authorized == null) {
      // Invalid right
      return null;
    }

    if (authorized.equalsIgnoreCase("y") || // Legacy way of defining values
        authorized.equalsIgnoreCase("yes") || // Legacy way of defining values
        Boolean.parseBoolean(authorized)) {
      return true;
    } else if (authorized.equalsIgnoreCase("n") || // Legacy way of defining values
        authorized.equalsIgnoreCase("no") || // Legacy way of defining values
        !Boolean.parseBoolean(authorized)) {
      return false;
    }

    // Invalid right
    return null;
  }

}
