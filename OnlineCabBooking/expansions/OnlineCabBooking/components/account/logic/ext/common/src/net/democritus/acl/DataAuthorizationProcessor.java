package net.democritus.acl;

import net.democritus.sys.Context;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A default implementation for the task element DataAuthorization.
 */
final class DataAuthorizationProcessor {

  private static final Logger logger = LoggerFactory.getLogger(DataAuthorizationProcessor.class);

  private final Context context;
  private final AccessRightStrategy accessRightStrategy;

  public DataAuthorizationProcessor(Context context, AccessRightStrategy accessRightStrategy) {
    this.context = context;
    this.accessRightStrategy = accessRightStrategy;
  }

  public DataAccessRights processQuery(DataAccessQuery dataAccessQuery) throws Exception {
    final ElementAccessManager elementAccessManager = new ElementAccessManager(context);
    final DataAccessRights dataAccessRights = new DataAccessRights(accessRightStrategy);

    final FunctionalityDescriptor functionalityDescriptor = new FunctionalityDescriptor()
        .setComponent(dataAccessQuery.getComponent())
        .setElement(dataAccessQuery.getElement())
        .setFunctionality(dataAccessQuery.getFunctionality());

    // Update rights in order, so they can be overridden at a more specific level
    updateAccessRights(dataAccessRights, elementAccessManager.getUserGroupDataAccess(functionalityDescriptor));
    updateAccessRights(dataAccessRights, elementAccessManager.getProfileDataAccess(functionalityDescriptor));
    updateAccessRights(dataAccessRights, elementAccessManager.getUserDataAccess(functionalityDescriptor));

    return dataAccessRights;
  }

  private void updateAccessRights(DataAccessRights functionalityAccess, List<DataAccessDetails> dataAccessList) {
    for (Map.Entry<String, Boolean> entry : combineRights(dataAccessList).entrySet()) {
      functionalityAccess.addAction(entry.getKey(), entry.getValue());
    }
  }

  private Map<String, Boolean> combineRights(List<DataAccessDetails> dataAccessList) {
    final Map<String, Boolean> rights = new HashMap<>();
    for (DataAccessDetails dataAccessDetails : dataAccessList) {
      Boolean isAuthorized = getAuthorization(dataAccessDetails.getAuthorized());
      if (isAuthorized == null) {
        if (logger.isWarnEnabled()) {
          logger.warn(
              "DataAccess rule for element '" + dataAccessDetails.getElement() +
                  "' has invalid authorization value '" + dataAccessDetails.getAuthorized() +
                  "'. Should be 'true' or 'false'."
          );
        }

        // Invalid access rule
        continue;
      }

      String functionality = dataAccessDetails.getFunctionality();
      if (rights.containsKey(functionality)) {
        rights.put(functionality, accessRightStrategy.combineRights(rights.get(functionality), isAuthorized));
      } else {
        rights.put(functionality, isAuthorized);
      }
    }
    return rights;
  }

  @SuppressWarnings("java:S2447")
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
