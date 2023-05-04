package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.DataAccessRights;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
// anchor:custom-imports:start
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// anchor:custom-imports:end

/**
 * Example implementation delegate for the task element DataAuthorization.
 */
public class DataAuthorizationImpl implements DataAuthorization {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DataAuthorizationImpl.class);
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TaskResult<DataAccessRights> perform(ParameterContext<DataAccessQuery> targetParameter) {
    UserContext userContext = targetParameter.getUserContext();
    Context context = targetParameter.getContext();
    TaskResult<DataAccessRights> taskResult = TaskResult.error();
    if (logger.isDebugEnabled()) {
      logger.debug(
          "Performing the DataAuthorizationImpl implementation"
      );
    }
    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
      ElementAccessManager elementAccessManager = new ElementAccessManager(context);
      DataAccessQuery dataAccessQuery = targetParameter.getValue();
      String element = dataAccessQuery.getElement();
      DataAccessRights functionalityAccess = new DataAccessRights();
      FunctionalityDescriptor functionalityDescriptor = new FunctionalityDescriptor()
          .setComponent(dataAccessQuery.getComponent())
          .setElement(element)
          .setFunctionality(dataAccessQuery.getFunctionality());
      List<DataAccessDetails> userDataAccessList = elementAccessManager.getUserDataAccess(functionalityDescriptor);
      List<DataAccessDetails> userGroupDataAccessList = elementAccessManager.getUserGroupDataAccess(functionalityDescriptor);
      List<DataAccessDetails> profileDataAccessList = elementAccessManager.getProfileDataAccess(functionalityDescriptor);
      //Handle accessRights from less specific to more specific.
      functionalityAccess = processDataAccess(functionalityAccess, userGroupDataAccessList);
      functionalityAccess = processDataAccess(functionalityAccess, profileDataAccessList);
      functionalityAccess = processDataAccess(functionalityAccess, userDataAccessList);
      taskResult = TaskResult.success(functionalityAccess);
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
            "Exception in DataAuthorizationImpl implementation", e
        );
      }
    }
    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  private DataAccessRights processDataAccess(DataAccessRights functionalityAccess, List<DataAccessDetails> dataAccessList) {
    HashMap<String, Boolean> rights = getRights(dataAccessList);
    addToAccessRights(functionalityAccess, rights);
    return functionalityAccess;
  }

  private void addToAccessRights(DataAccessRights functionalityAccess, HashMap<String, Boolean> rights) {
    for (Map.Entry<String, Boolean> entry : rights.entrySet()) {
      functionalityAccess.addAction(entry.getKey(), entry.getValue());
    }
  }

  private HashMap<String, Boolean> getRights(List<DataAccessDetails> dataAccessList) {
    HashMap<String, Boolean> rights = new HashMap<>();
    for (DataAccessDetails dataAccessDetails : dataAccessList) {
      String functionality = dataAccessDetails.getFunctionality();
      if (rights.containsKey(functionality)) {
        rights.put(functionality, (rights.get(functionality) || isAuthorized(dataAccessDetails.getAuthorized())));
      } else {
        rights.put(functionality, isAuthorized(dataAccessDetails.getAuthorized()));
      }
    }
    return rights;
  }

  private Boolean isAuthorized(String authorized) {
    return authorized == null || !authorized.toLowerCase().startsWith("n");
  }
  // anchor:custom-methods:end

}

