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
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton representing the agent in the webtier,
 * to the business logic of the User in the EJB tier
 */

public class UserProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("account", "User", "unknownError");
  private static final Diagnostic CREATE_ERROR = Diagnostic.error("account", "User", "account.user.createFailed");
  private static final Diagnostic MODIFY_ERROR = Diagnostic.error("account", "User", "account.user.modifyFailed");
  private static final Diagnostic DELETE_ERROR = Diagnostic.error("account", "User", "account.user.deleteFailed");
  private static final Diagnostic SEARCH_ERROR = Diagnostic.error("account", "User", "account.user.findFailed");

  private static final Logger logger = LoggerFactory.getLogger(UserProxy.class);

  private UserRemote userRemote = null;
  private static UserProxy userProxy = null;

  // @anchor:variables:start
  private static final Diagnostic AUTHENTICATION_ERROR = Diagnostic.error("account", "User", "account.user.authenticationFailed");
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private UserProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("account");
      String remoteName = componentJNDI.getDataRemoteName("User");
      userRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static UserProxy getUserProxy() {
    if (userProxy == null) {
      userProxy = new UserProxy();
    }
    return userProxy;
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance:start
  public CrudsResult<UserDetails> getDetails(ParameterContext<Long> idParameter) {
    try {
      return userRemote.getDetails(idParameter);
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
      return (CrudsResult<T>) userRemote.getProjection(projectionRefParameter);
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
      return (CrudsResult<DataRef>) userRemote.resolveDataRef(dataRefParameter);
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

  public <S extends UserFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    try {
      return userRemote.find(searchParameter);
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
      return userRemote.getName(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve name for userId", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    try {
      return userRemote.getId(nameParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve id for userName", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  /*========== Create/Modify CRUD methods ==========*/

  // anchor:crud:start
  public CrudsResult<DataRef> create(ParameterContext<UserDetails> detailsParameter) {
    try {
      return userRemote.create(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create User", re
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }

  public CrudsResult<DataRef> modify(ParameterContext<UserDetails> detailsParameter) {
    try {
      return userRemote.modify(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not modify User", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    try {
      return userRemote.createOrModify(projectionParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create nor modify User", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    try {
      return userRemote.delete(idParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete User", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }

  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> parameter) {
    try {
      return userRemote.deleteByDataRef(parameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete User", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }
  // anchor:crud:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  public <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter) {
    try {
      return userRemote.perform(commandParameter);
    } catch (Exception e) {
      T command = commandParameter.getValue();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not execute command " + command.getClass(), e
        );
      }
      return CommandResult.error(command.getCommandId(), "Could not execute command");
    }
  }
  public ImportResult importFile(ParameterContext<ImportFile> parameter) {
    try {
      return userRemote.importFile(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not import file", e
        );
      }
      return ImportResult.globalError("Unexpected error");
    }
  }
  public <V> AuthenticationResult authenticate(ParameterContext<AuthenticationDetails<V>> authenticationDetailsParameter) {
    try {
      return userRemote.authenticate(authenticationDetailsParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "authenticate() failed", e
        );
      }
      return AuthenticationResult.error(AUTHENTICATION_ERROR);
    }
  }
  // @anchor:methods:end

  /*=========== Customisation ===========*/

  // anchor:custom-methods:start
  public CrudsResult<Boolean> checkPassword(ParameterContext<UserInput> parameter) {
    try {
      return userRemote.checkPassword(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not check password", e
        );
      }
      return CrudsResult.error(UNKNOWN_ERROR);
    }
  }

  public CrudsResult<Boolean> checkEncryptedPassword(ParameterContext<UserInput> parameter) {
    try {
      return userRemote.checkEncryptedPassword(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not check password", e
        );
      }
      return CrudsResult.error(UNKNOWN_ERROR);
    }
  }
  // anchor:custom-methods:end
}

