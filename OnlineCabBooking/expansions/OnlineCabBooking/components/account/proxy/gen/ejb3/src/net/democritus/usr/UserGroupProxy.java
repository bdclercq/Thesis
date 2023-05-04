package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.Diagnostic;

import net.democritus.sys.search.SearchDetails;

import net.democritus.sys.ParameterContext;
import net.democritus.jndi.ComponentJNDI;

// @anchor:imports:start
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton representing the agent in the webtier,
 * to the business logic of the UserGroup in the EJB tier
 */

public class UserGroupProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("account", "UserGroup", "unknownError");
  private static final Diagnostic CREATE_ERROR = Diagnostic.error("account", "UserGroup", "account.userGroup.createFailed");
  private static final Diagnostic MODIFY_ERROR = Diagnostic.error("account", "UserGroup", "account.userGroup.modifyFailed");
  private static final Diagnostic DELETE_ERROR = Diagnostic.error("account", "UserGroup", "account.userGroup.deleteFailed");
  private static final Diagnostic SEARCH_ERROR = Diagnostic.error("account", "UserGroup", "account.userGroup.findFailed");

  private static final Logger logger = LoggerFactory.getLogger(UserGroupProxy.class);

  private UserGroupRemote userGroupRemote = null;
  private static UserGroupProxy userGroupProxy = null;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private UserGroupProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("account");
      String remoteName = componentJNDI.getDataRemoteName("UserGroup");
      userGroupRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static UserGroupProxy getUserGroupProxy() {
    if (userGroupProxy == null) {
      userGroupProxy = new UserGroupProxy();
    }
    return userGroupProxy;
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance:start
  public CrudsResult<UserGroupDetails> getDetails(ParameterContext<Long> idParameter) {
    try {
      return userGroupRemote.getDetails(idParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getDetails() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    try {
      return (CrudsResult<T>) userGroupRemote.getProjection(projectionRefParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getProjection() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    try {
      return (CrudsResult<DataRef>) userGroupRemote.resolveDataRef(dataRefParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "resolveDataRef() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }
  // anchor:instance:end

  /*========== Individual finders ==========*/

  public <S extends UserGroupFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    try {
      return userGroupRemote.find(searchParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "find() failed", e
        );
      }
      return SearchResult.error(SEARCH_ERROR);
    }
  }

  /*========== Name/Id resolution methods ==========*/

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    try {
      return userGroupRemote.getName(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve name for userGroupId", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    try {
      return userGroupRemote.getId(nameParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve id for userGroupName", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  /*========== Create/Modify CRUD methods ==========*/

  // anchor:crud:start
  public CrudsResult<DataRef> create(ParameterContext<UserGroupDetails> detailsParameter) {
    try {
      return userGroupRemote.create(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create UserGroup", re
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }

  public CrudsResult<DataRef> modify(ParameterContext<UserGroupDetails> detailsParameter) {
    try {
      return userGroupRemote.modify(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not modify UserGroup", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    try {
      return userGroupRemote.createOrModify(projectionParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create nor modify UserGroup", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    try {
      return userGroupRemote.delete(idParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete UserGroup", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }

  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> parameter) {
    try {
      return userGroupRemote.deleteByDataRef(parameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete UserGroup", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }
  // anchor:crud:end

  // @anchor:methods:start
  public ImportResult importFile(ParameterContext<ImportFile> parameter) {
    try {
      return userGroupRemote.importFile(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not import file", e
        );
      }
      return ImportResult.globalError("Unexpected error");
    }
  }
  // @anchor:methods:end

  /*=========== Customisation ===========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

