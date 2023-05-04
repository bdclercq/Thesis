package net.democritus.workflow;

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
import net.democritus.support.Paging;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class StateTimerLocalAgent implements StateTimerAgentIf {

  private final StateTimerLocal stateTimerLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static StateTimerLocalAgent getStateTimerAgent(UserContext userContext) {
    return new StateTimerLocalAgent(getLocalInstance(), userContext);
  }

  public static StateTimerLocalAgent getStateTimerAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new StateTimerLocalAgent(getLocalInstance(), userContext, context);
  }

  private static StateTimerLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("StateTimer");
      }

      return (StateTimerLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.workflow.StateTimerLocal'", e);
    }
  }

  public StateTimerLocalAgent(StateTimerLocal stateTimerLocal, UserContext userContext) {
    this.stateTimerLocal = stateTimerLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public StateTimerLocalAgent(StateTimerLocal stateTimerLocal, UserContext userContext, Context context) {
    this.stateTimerLocal = stateTimerLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new StateTimer instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(StateTimerDetails stateTimerDetails) {
    return stateTimerLocal.create(createParameter(stateTimerDetails));
  }

  /**
   * Modify an existing StateTimer instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(StateTimerDetails stateTimerDetails) {
    return stateTimerLocal.modify(createParameter(stateTimerDetails));
  }

  /**
   * Create a StateTimer instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return stateTimerLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target StateTimer instance by id.
   *
   * @deprecated Use {@link StateTimerAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return stateTimerLocal.delete(createParameter(id));
  }

  /**
   * Delete target StateTimer instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return stateTimerLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return stateTimerLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return stateTimerLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<StateTimerDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the StateTimer element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends StateTimerFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return stateTimerLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<StateTimerDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    StateTimerFindAllStateTimersDetails finder = new StateTimerFindAllStateTimersDetails();
    return stateTimerLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<StateTimerInfo> findAllInfos() {
    StateTimerFindAllStateTimersDetails finder = new StateTimerFindAllStateTimersDetails();
    return stateTimerLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return stateTimerLocal.getId(createParameter(name));
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
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
