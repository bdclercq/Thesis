package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.ArrayList;
import java.util.List;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;


import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;

import net.democritus.sys.search.SearchDetails;
import net.democritus.support.Paging;
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

// @anchor:imports:start
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.AccountAgent;
import net.democritus.usr.ProfileAgent;
import net.democritus.usr.UserGroupAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserAgent implements UserAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(UserAgent.class);

  private static final UserProxy userProxy = UserProxy.getUserProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private UserAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static UserAgent getUserAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new UserAgent(context, userContext);
  }

  @Deprecated
  public static UserAgent getUserAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new UserAgent(context, userContext);
  }

  @Deprecated
  public static UserAgent getUserAgent() {
    return getUserAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    UserFindAllUsersDetails finder = new UserFindAllUsersDetails();
    return userProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<UserInfo> findAllInfos() {
    UserFindAllUsersDetails finder = new UserFindAllUsersDetails();
    return userProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long userId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(userId));
    CrudsResult<DataRef> result = userProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public UserInfo getInfo(Long userId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(userId));
    CrudsResult<UserInfo> result = userProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new UserInfo();
    }
  }

  public UserDetails getDetails_old(Long userId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(userId));
    CrudsResult<UserDetails> result = userProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new UserDetails();
    }
  }

  public CrudsResult<UserDetails> getDetails(Long userId) {
    return userProxy.getDetails(createParameter(userId));
  }

  public CrudsResult<UserDetails> getDetails(DataRef dataRef) {
    return userProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return userProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return userProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long userId) {
    CrudsResult<String> result = userProxy.getName(createParameter(userId));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      if (logger.isErrorEnabled()) {
          logger.error(
            "getName() failed"
          );
      }
      return "";
    }
  }

  public Long getId(String userName) {
    CrudsResult<DataRef> result = userProxy.getId(createParameter(userName));
    if (result.isSuccess()) {
      return result.getValue().getId();
    } else {
      if (logger.isErrorEnabled()) {
          logger.error(
            "getId() failed"
          );
      }
      return null;
    }
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return userProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new User instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(UserDetails details) {
    return userProxy.create(createParameter(details));
  }

  /**
   * Modify an existing User instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(UserDetails details) {
    return userProxy.modify(createParameter(details));
  }

  /**
   * Create a User instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return userProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target User instance by id.
   *
   * @deprecated Use {@link UserAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return userProxy.delete(createParameter(oid));
  }

  /**
   * Delete target User instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return userProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the User element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends UserFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return userProxy.find(createParameter(searchDetails));
  }

  /*========== Context ==========*/

  private <T> ParameterContext<T> createParameter(T value) {
    return mContext.withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========= Handle commands ========*/

  /**
   * Perform target command
   *
   * @param command a command object representing the command
   * @return success if the command was executed successfully
   */
  public <T extends ICommand> CommandResult perform(T command) {
    return userProxy.perform(createParameter(command));
  }

  // @anchor:methods:start
  /**
   * Import a file containing User data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return userProxy.importFile(createParameter(importFile));
  }
  @Override
  public <V>AuthenticationResult authenticate(AuthenticationDetails<V> authenticationDetails) {
    return userProxy.authenticate(createParameter(authenticationDetails));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
    public CrudsResult<Boolean> checkPassword(UserInput userLoginInput) {
        return userProxy.checkPassword(createParameter(userLoginInput));
    }

    public CrudsResult<Boolean> checkEncryptedPassword(UserInput userLoginInput) {
        return userProxy.checkEncryptedPassword(createParameter(userLoginInput));
    }
  // anchor:custom-methods:end
}

