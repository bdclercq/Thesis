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

public class EngineNodeLocalAgent implements EngineNodeAgentIf {

  private final EngineNodeLocal engineNodeLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static EngineNodeLocalAgent getEngineNodeAgent(UserContext userContext) {
    return new EngineNodeLocalAgent(getLocalInstance(), userContext);
  }

  public static EngineNodeLocalAgent getEngineNodeAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new EngineNodeLocalAgent(getLocalInstance(), userContext, context);
  }

  private static EngineNodeLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("EngineNode");
      }

      return (EngineNodeLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.wfe.EngineNodeLocal'", e);
    }
  }

  public EngineNodeLocalAgent(EngineNodeLocal engineNodeLocal, UserContext userContext) {
    this.engineNodeLocal = engineNodeLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public EngineNodeLocalAgent(EngineNodeLocal engineNodeLocal, UserContext userContext, Context context) {
    this.engineNodeLocal = engineNodeLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new EngineNode instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(EngineNodeDetails engineNodeDetails) {
    return engineNodeLocal.create(createParameter(engineNodeDetails));
  }

  /**
   * Modify an existing EngineNode instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(EngineNodeDetails engineNodeDetails) {
    return engineNodeLocal.modify(createParameter(engineNodeDetails));
  }

  /**
   * Create a EngineNode instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return engineNodeLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target EngineNode instance by id.
   *
   * @deprecated Use {@link EngineNodeAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return engineNodeLocal.delete(createParameter(id));
  }

  /**
   * Delete target EngineNode instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return engineNodeLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return engineNodeLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return engineNodeLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<EngineNodeDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the EngineNode element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends EngineNodeFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return engineNodeLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<EngineNodeDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    EngineNodeFindAllEngineNodesDetails finder = new EngineNodeFindAllEngineNodesDetails();
    return engineNodeLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<EngineNodeInfo> findAllInfos() {
    EngineNodeFindAllEngineNodesDetails finder = new EngineNodeFindAllEngineNodesDetails();
    return engineNodeLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return engineNodeLocal.getId(createParameter(name));
  }

  private <T> ParameterContext<T> createParameter(T value) {
    return context.withParameter(value);
  }

  private ParameterContext<Void> createEmptyParameter() {
    return context.emptyParameter();
  }

  // anchor:compare-set-methods:start
  public CrudsResult<Void> compareAndSetStatus(StateUpdate stateUpdate) {
    return engineNodeLocal.compareAndSetStatus(createParameter(stateUpdate));
  }

  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  public CrudsResult<Void> promoteToMaster(DataRef target) {
    return engineNodeLocal.promoteToMaster(createParameter(target));
  }

  public CrudsResult<Void> postHealthCheck(DataRef target) {
    return engineNodeLocal.postHealthCheck(createParameter(target));
  }

  public CrudsResult<Void> setNotResponding(DataRef target) {
    return engineNodeLocal.setNotResponding(createParameter(target));
  }

  public CrudsResult<Void> activateEngineNodes() {
    return engineNodeLocal.activateEngineNodes(createEmptyParameter());
  }

  public CrudsResult<EngineNodeDetails> getEngineNodeByName(String name) {
    return engineNodeLocal.getEngineNodeByName(createParameter(name));
  }
  public CrudsResult<EngineNodeDetails> getEngineNodeById(Long id) {
    return engineNodeLocal.getEngineNodeById(createParameter(id));
  }
  // anchor:custom-methods:end
}
