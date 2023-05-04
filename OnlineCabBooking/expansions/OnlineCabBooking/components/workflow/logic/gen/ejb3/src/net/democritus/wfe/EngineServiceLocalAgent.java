package net.democritus.wfe;

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
import net.democritus.state.StateUpdate;
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import net.democritus.support.Paging;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
import java.util.Date;
// anchor:custom-imports:end

public class EngineServiceLocalAgent implements EngineServiceAgentIf {

  private final EngineServiceLocal engineServiceLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static EngineServiceLocalAgent getEngineServiceAgent(UserContext userContext) {
    return new EngineServiceLocalAgent(getLocalInstance(), userContext);
  }

  public static EngineServiceLocalAgent getEngineServiceAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new EngineServiceLocalAgent(getLocalInstance(), userContext, context);
  }

  private static EngineServiceLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("EngineService");
      }

      return (EngineServiceLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.wfe.EngineServiceLocal'", e);
    }
  }

  public EngineServiceLocalAgent(EngineServiceLocal engineServiceLocal, UserContext userContext) {
    this.engineServiceLocal = engineServiceLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public EngineServiceLocalAgent(EngineServiceLocal engineServiceLocal, UserContext userContext, Context context) {
    this.engineServiceLocal = engineServiceLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new EngineService instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(EngineServiceDetails engineServiceDetails) {
    return engineServiceLocal.create(createParameter(engineServiceDetails));
  }

  /**
   * Modify an existing EngineService instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(EngineServiceDetails engineServiceDetails) {
    return engineServiceLocal.modify(createParameter(engineServiceDetails));
  }

  /**
   * Create a EngineService instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return engineServiceLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target EngineService instance by id.
   *
   * @deprecated Use {@link EngineServiceAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return engineServiceLocal.delete(createParameter(id));
  }

  /**
   * Delete target EngineService instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return engineServiceLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return engineServiceLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return engineServiceLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<EngineServiceDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the EngineService element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends EngineServiceFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return engineServiceLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<EngineServiceDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    EngineServiceFindAllEngineServicesDetails finder = new EngineServiceFindAllEngineServicesDetails();
    return engineServiceLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<EngineServiceInfo> findAllInfos() {
    EngineServiceFindAllEngineServicesDetails finder = new EngineServiceFindAllEngineServicesDetails();
    return engineServiceLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return engineServiceLocal.getId(createParameter(name));
  }

  /**
   * Perform target command
   *
   * @param command a command object representing the command
   * @return success if the command was executed successfully
   */
  @Override public <T extends ICommand> CommandResult perform(T command) {
    return engineServiceLocal.perform(createParameter(command));
  }

  private <T> ParameterContext<T> createParameter(T value) {
    return context.withParameter(value);
  }

  private ParameterContext<Void> createEmptyParameter() {
    return context.emptyParameter();
  }

  // anchor:compare-set-methods:start
  public CrudsResult<Void> compareAndSetStatus(StateUpdate stateUpdate) {
    return engineServiceLocal.compareAndSetStatus(createParameter(stateUpdate));
  }

  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  public CrudsResult<Void> setBusy(DataRef engineRef, String busyValue) {
    ParameterContext<String> parameterContext = new ParameterContext<>(userContext, engineRef, busyValue);
    return engineServiceLocal.setBusy(parameterContext);
  }

  public CrudsResult<Void> setLastRunAt(DataRef engineRef, Date timestamp) {
    ParameterContext<Date> parameterContext = new ParameterContext<>(userContext, engineRef, timestamp);
    return engineServiceLocal.setLastRunAt(parameterContext);
  }

  public CrudsResult<Void> updateLastRunAt(DataRef engineRef) {
    ParameterContext<DataRef> parameterContext = context.withParameter(engineRef);
    return engineServiceLocal.updateLastRunAt(parameterContext);
  }
  // anchor:custom-methods:end
}
