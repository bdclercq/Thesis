package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.search.SearchDetails;
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import net.democritus.support.Paging;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserLocalAgent implements UserAgentIf {

  private final UserLocal userLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static UserLocalAgent getUserAgent(UserContext userContext) {
    return new UserLocalAgent(getLocalInstance(), userContext);
  }

  public static UserLocalAgent getUserAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new UserLocalAgent(getLocalInstance(), userContext, context);
  }

  private static UserLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("account");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("User");
      }

      return (UserLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.usr.UserLocal'", e);
    }
  }

  public UserLocalAgent(UserLocal userLocal, UserContext userContext) {
    this.userLocal = userLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public UserLocalAgent(UserLocal userLocal, UserContext userContext, Context context) {
    this.userLocal = userLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new User instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(UserDetails userDetails) {
    return userLocal.create(createParameter(userDetails));
  }

  /**
   * Modify an existing User instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(UserDetails userDetails) {
    return userLocal.modify(createParameter(userDetails));
  }

  /**
   * Create a User instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return userLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target User instance by id.
   *
   * @deprecated Use {@link UserAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return userLocal.delete(createParameter(id));
  }

  /**
   * Delete target User instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return userLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return userLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return userLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<UserDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the User element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends UserFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return userLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<UserDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    UserFindAllUsersDetails finder = new UserFindAllUsersDetails();
    return userLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<UserInfo> findAllInfos() {
    UserFindAllUsersDetails finder = new UserFindAllUsersDetails();
    return userLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return userLocal.getId(createParameter(name));
  }

  /**
   * Perform target command
   *
   * @param command a command object representing the command
   * @return success if the command was executed successfully
   */
  @Override public <T extends ICommand> CommandResult perform(T command) {
    return userLocal.perform(createParameter(command));
  }

  private <T> ParameterContext<T> createParameter(T value) {
    return context.withParameter(value);
  }

  private ParameterContext<Void> createEmptyParameter() {
    return context.emptyParameter();
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  @Override
  public <V> AuthenticationResult authenticate(AuthenticationDetails<V> authenticationDetails) {
    return userLocal.authenticate(createParameter(authenticationDetails));
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  public CrudsResult<Boolean> checkEncryptedPassword(ParameterContext<UserInput> userInputParameterContext) {
    return userLocal.checkEncryptedPassword(userInputParameterContext);
  }

  public CrudsResult<Boolean> checkPassword(ParameterContext<UserInput> userInputParameterContext) {
    return userLocal.checkPassword(userInputParameterContext);
  }
  // anchor:custom-methods:end
}
