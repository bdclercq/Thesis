package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.democritus.acl.DataAccessQuery;
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
import account.settings.AccountApplicationSettings;

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.BasicProcessingContext;
import net.democritus.sys.Context;
import net.democritus.sys.TaskPerformer;
import net.democritus.usr.UserContextWithRights;

import javax.naming.NamingException;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task AuthorizationManager.
 */

@Stateless
@Remote(AuthorizationManagerRemote.class)
@Local(AuthorizationManagerLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class AuthorizationManagerBean implements AuthorizationManagerRemote, AuthorizationManagerLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AuthorizationManagerBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  private TaskPerformer<DataAccessRights, DataAccessQuery> dataAuthorization = getDataAuthorizationBean();
  private TaskPerformer<TaskAccessRights, DataAccessQuery> taskAuthorization = getTaskAuthorizationBean();
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  @Override
  public TaskResult<DataAccessRights> getDataAccessRights(ParameterContext<DataAccessQuery> accessParameter) {
    DataAccessQuery accessQuery = accessParameter.getValue();
    UserContext userContext = accessParameter.getUserContext();

    if (userContext instanceof UserContextWithRights) {
      AccessRightCacheResult cacheEntry = ((UserContextWithRights) userContext).getAccessRights().getAccessRight(accessQuery);
      if (cacheEntry.isFound()) {
        return TaskResult.success((DataAccessRights) cacheEntry.getAccessRights());
      }
    }

    TaskResult<DataAccessRights> taskResult = retrieveDataAccessRights(accessQuery, accessParameter.getContext());

    if (taskResult.isSuccess() && userContext instanceof UserContextWithRights) {
      DataAccessRights dataAccessRights = taskResult.getValue();
      ((UserContextWithRights) userContext).getAccessRights().registerAccessRights(accessQuery, dataAccessRights);
    }
    return taskResult;
  }

  @Override
  public TaskResult<TaskAccessRights> getTaskAccessRights(ParameterContext<DataAccessQuery> accessParameter) {
    DataAccessQuery accessQuery = accessParameter.getValue();
    UserContext userContext = accessParameter.getUserContext();

    if (userContext instanceof UserContextWithRights) {
      AccessRightCacheResult cacheEntry = ((UserContextWithRights) userContext).getAccessRights().getAccessRight(accessQuery);
      if (cacheEntry.isFound()) {
        return TaskResult.success((TaskAccessRights) cacheEntry.getAccessRights());
      }
    }

    TaskResult<TaskAccessRights> taskResult = retrieveTaskAccessRights(accessQuery, accessParameter.getContext());

    if (taskResult.isSuccess() && userContext instanceof UserContextWithRights) {
      TaskAccessRights taskAccessRights = taskResult.getValue();
      ((UserContextWithRights) userContext).getAccessRights().registerAccessRights(accessQuery, taskAccessRights);
    }
    return taskResult;
  }

  private TaskResult<DataAccessRights> retrieveDataAccessRights(DataAccessQuery accessQuery, Context context) {
    ProcessingContext processingContext = new BasicProcessingContext(
        getDataAuthorizationImplementation(),
        getDataAuthorizationParams());

    ParameterContext<DataAccessQuery> parameterContext = context
        .extend(processingContext)
        .withParameter(accessQuery);

    return dataAuthorization.perform(parameterContext);
  }

  private TaskResult<TaskAccessRights> retrieveTaskAccessRights(DataAccessQuery accessQuery, Context context) {
    ProcessingContext processingContext = new BasicProcessingContext(
        getTaskAuthorizationImplementation(),
        getTaskAuthorizationParams());

    ParameterContext<DataAccessQuery> parameterContext = context
        .extend(processingContext)
        .withParameter(accessQuery);

    return taskAuthorization.perform(parameterContext);
  }

  private String getDataAuthorizationImplementation() {
    return AccountApplicationSettings.getProperty("authorization.data.implementation").orElse("net.democritus.acl.DataAuthorizationImpl");
  }

  private String getDataAuthorizationParams() {
    return AccountApplicationSettings.getProperty("authorization.data.params").orElse("");
  }

  private String getTaskAuthorizationImplementation() {
    return AccountApplicationSettings.getProperty("authorization.task.implementation").orElse("net.democritus.acl.TaskAuthorizationImpl");
  }

  private String getTaskAuthorizationParams() {
    return AccountApplicationSettings.getProperty("authorization.task.params").orElse("");
  }

  private TaskPerformer<TaskAccessRights, DataAccessQuery> getTaskAuthorizationBean() {
    ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("account");
    String localName = componentJNDI.getTaskLocalName("TaskAuthorization");
    String remoteName = componentJNDI.getTaskRemoteName("TaskAuthorization");

    try {
      return componentJNDI.lookupLocal(localName);
    } catch (NamingException e) {
      try {
        return componentJNDI.lookupRemote(remoteName);
      } catch (NamingException e1) {
        throw new IllegalStateException("Cannot find local or remote task authorization bean", e1);
      }
    }
  }

  private TaskPerformer<DataAccessRights, DataAccessQuery> getDataAuthorizationBean() {
    ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("account");
    String localName = componentJNDI.getTaskLocalName("DataAuthorization");
    String remoteName = componentJNDI.getTaskRemoteName("DataAuthorization");

    try {
      return componentJNDI.lookupLocal(localName);
    } catch (NamingException e) {
      try {
        return componentJNDI.lookupRemote(remoteName);
      } catch (NamingException e1) {
        throw new IllegalStateException("Cannot find local or remote data authorization bean", e1);
      }
    }
  }
  // anchor:custom-methods:end

}

