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

public class StateTaskLocalAgent implements StateTaskAgentIf {

  private final StateTaskLocal stateTaskLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static StateTaskLocalAgent getStateTaskAgent(UserContext userContext) {
    return new StateTaskLocalAgent(getLocalInstance(), userContext);
  }

  public static StateTaskLocalAgent getStateTaskAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new StateTaskLocalAgent(getLocalInstance(), userContext, context);
  }

  private static StateTaskLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("StateTask");
      }

      return (StateTaskLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'net.democritus.workflow.StateTaskLocal'", e);
    }
  }

  public StateTaskLocalAgent(StateTaskLocal stateTaskLocal, UserContext userContext) {
    this.stateTaskLocal = stateTaskLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public StateTaskLocalAgent(StateTaskLocal stateTaskLocal, UserContext userContext, Context context) {
    this.stateTaskLocal = stateTaskLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new StateTask instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(StateTaskDetails stateTaskDetails) {
    return stateTaskLocal.create(createParameter(stateTaskDetails));
  }

  /**
   * Modify an existing StateTask instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(StateTaskDetails stateTaskDetails) {
    return stateTaskLocal.modify(createParameter(stateTaskDetails));
  }

  /**
   * Create a StateTask instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return stateTaskLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target StateTask instance by id.
   *
   * @deprecated Use {@link StateTaskAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return stateTaskLocal.delete(createParameter(id));
  }

  /**
   * Delete target StateTask instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return stateTaskLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return stateTaskLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return stateTaskLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<StateTaskDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the StateTask element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends StateTaskFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return stateTaskLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<StateTaskDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    StateTaskFindAllStateTasksDetails finder = new StateTaskFindAllStateTasksDetails();
    return stateTaskLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<StateTaskInfo> findAllInfos() {
    StateTaskFindAllStateTasksDetails finder = new StateTaskFindAllStateTasksDetails();
    return stateTaskLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return stateTaskLocal.getId(createParameter(name));
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
  public SearchResult<StateTaskDetails> getStateTasksByWorkflowId(Long workflowId) {
    return stateTaskLocal.getStateTasksByWorkflowId(createParameter(workflowId));
  }
  // anchor:custom-methods:end
}
