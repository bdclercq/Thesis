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
import net.democritus.support.Paging;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineNodeServiceLocalAgent implements EngineNodeServiceAgentIf {

  private final EngineNodeServiceLocal engineNodeServiceLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static EngineNodeServiceLocalAgent getEngineNodeServiceAgent(UserContext userContext) {
    return new EngineNodeServiceLocalAgent(getLocalInstance(), userContext);
  }

  public static EngineNodeServiceLocalAgent getEngineNodeServiceAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new EngineNodeServiceLocalAgent(getLocalInstance(), userContext, context);
  }

  private static EngineNodeServiceLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("EngineNodeService");
      }

      return (EngineNodeServiceLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.wfe.EngineNodeServiceLocal'", e);
    }
  }

  public EngineNodeServiceLocalAgent(EngineNodeServiceLocal engineNodeServiceLocal, UserContext userContext) {
    this.engineNodeServiceLocal = engineNodeServiceLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public EngineNodeServiceLocalAgent(EngineNodeServiceLocal engineNodeServiceLocal, UserContext userContext, Context context) {
    this.engineNodeServiceLocal = engineNodeServiceLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new EngineNodeService instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(EngineNodeServiceDetails engineNodeServiceDetails) {
    return engineNodeServiceLocal.create(createParameter(engineNodeServiceDetails));
  }

  /**
   * Modify an existing EngineNodeService instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(EngineNodeServiceDetails engineNodeServiceDetails) {
    return engineNodeServiceLocal.modify(createParameter(engineNodeServiceDetails));
  }

  /**
   * Modify an existing EngineNodeService instance with a specific projection
   *
   * @param projection an alternative representation of the instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public <P> CrudsResult<DataRef> modifyWithProjection(P projection) {
    return engineNodeServiceLocal.modifyWithProjection(createParameter(projection));
  }

  /**
   * Create a EngineNodeService instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return engineNodeServiceLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target EngineNodeService instance by id.
   *
   * @deprecated Use {@link EngineNodeServiceAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return engineNodeServiceLocal.delete(createParameter(id));
  }

  /**
   * Delete target EngineNodeService instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return engineNodeServiceLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return engineNodeServiceLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return engineNodeServiceLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<EngineNodeServiceDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the EngineNodeService element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends EngineNodeServiceFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return engineNodeServiceLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<EngineNodeServiceDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    EngineNodeServiceFindAllEngineNodeServicesDetails finder = new EngineNodeServiceFindAllEngineNodeServicesDetails();
    return engineNodeServiceLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<EngineNodeServiceInfo> findAllInfos() {
    EngineNodeServiceFindAllEngineNodeServicesDetails finder = new EngineNodeServiceFindAllEngineNodeServicesDetails();
    return engineNodeServiceLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return engineNodeServiceLocal.getId(createParameter(name));
  }

  private <T> ParameterContext<T> createParameter(T value) {
    return context.withParameter(value);
  }

  private ParameterContext<Void> createEmptyParameter() {
    return context.emptyParameter();
  }

  // anchor:compare-set-methods:start
  public CrudsResult<Void> compareAndSetStatus(StateUpdate stateUpdate) {
    return engineNodeServiceLocal.compareAndSetStatus(createParameter(stateUpdate));
  }

  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  public CrudsResult<EngineNodeServiceDetails> getEngineNodeServiceForEngineService(ParameterContext<DataRef> parameter) {
    return engineNodeServiceLocal.getEngineNodeServiceForEngineService(parameter);
  }
  // anchor:custom-methods:end
}
