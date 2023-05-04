package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import javax.naming.Context;
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
import net.democritus.sys.DataRef;
import net.democritus.sys.PageRef;
import net.democritus.sys.SearchDataRef;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.Diagnostic;

import net.democritus.sys.TaskResult;
import net.democritus.sys.TaskPerformer;
import net.democritus.sys.ParameterContext;

import net.democritus.jndi.ComponentJNDI;

import net.democritus.acl.DataAccessQuery;
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton proxy to connect client sessions from a client or web tier,
 * to the actual implementation of the task AuthorizationManager in the logic tier
 */
public class AuthorizationManagerProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("account", "AuthorizationManager", "unknownError");

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AuthorizationManagerProxy.class);
  // @anchor:variables:end

  private AuthorizationManagerRemote authorizationManagerRemote = null;
  private static AuthorizationManagerProxy authorizationManagerProxy = null;
  private List authorizationManagerIdVector = null;
  private Date lastRefreshIdVector = new Date();

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private AuthorizationManagerProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("account");
      String remoteName = componentJNDI.getTaskRemoteName("AuthorizationManager");
      authorizationManagerRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static AuthorizationManagerProxy getAuthorizationManagerProxy() {
    if (authorizationManagerProxy == null) {
      authorizationManagerProxy = new AuthorizationManagerProxy();
    }
    return authorizationManagerProxy;
  }

  /*========== Business Methods ==========*/

  // anchor:custom-methods:start
  public TaskResult<DataAccessRights> getDataAccessRights(ParameterContext<DataAccessQuery> accessParameter) {
    try {
      return authorizationManagerRemote.getDataAccessRights(accessParameter);
    } catch (Exception e) {
      logger.error("Failed to execute getDataAccessRights()", e);
      return TaskResult.error();
    }
  }

  public TaskResult<TaskAccessRights> getTaskAccessRights(ParameterContext<DataAccessQuery> accessParameter) {
    try {
      return authorizationManagerRemote.getTaskAccessRights(accessParameter);
    } catch (Exception e) {
      logger.error("Failed to execute getTaskAccessRights()", e);
      return TaskResult.error();
    }
  }
  // anchor:custom-methods:end

}
