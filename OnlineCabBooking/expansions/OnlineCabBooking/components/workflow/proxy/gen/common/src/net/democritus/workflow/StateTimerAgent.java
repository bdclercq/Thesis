package net.democritus.workflow;

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
// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import net.democritus.workflow.WorkflowAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class StateTimerAgent implements StateTimerAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(StateTimerAgent.class);

  private static final StateTimerProxy stateTimerProxy = StateTimerProxy.getStateTimerProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private StateTimerAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static StateTimerAgent getStateTimerAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new StateTimerAgent(context, userContext);
  }

  @Deprecated
  public static StateTimerAgent getStateTimerAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new StateTimerAgent(context, userContext);
  }

  @Deprecated
  public static StateTimerAgent getStateTimerAgent() {
    return getStateTimerAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    StateTimerFindAllStateTimersDetails finder = new StateTimerFindAllStateTimersDetails();
    return stateTimerProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<StateTimerInfo> findAllInfos() {
    StateTimerFindAllStateTimersDetails finder = new StateTimerFindAllStateTimersDetails();
    return stateTimerProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long stateTimerId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(stateTimerId));
    CrudsResult<DataRef> result = stateTimerProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public StateTimerInfo getInfo(Long stateTimerId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(stateTimerId));
    CrudsResult<StateTimerInfo> result = stateTimerProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new StateTimerInfo();
    }
  }

  public StateTimerDetails getDetails_old(Long stateTimerId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(stateTimerId));
    CrudsResult<StateTimerDetails> result = stateTimerProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new StateTimerDetails();
    }
  }

  public CrudsResult<StateTimerDetails> getDetails(Long stateTimerId) {
    return stateTimerProxy.getDetails(createParameter(stateTimerId));
  }

  public CrudsResult<StateTimerDetails> getDetails(DataRef dataRef) {
    return stateTimerProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return stateTimerProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return stateTimerProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long stateTimerId) {
    CrudsResult<String> result = stateTimerProxy.getName(createParameter(stateTimerId));
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

  public Long getId(String stateTimerName) {
    CrudsResult<DataRef> result = stateTimerProxy.getId(createParameter(stateTimerName));
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
    return stateTimerProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new StateTimer instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(StateTimerDetails details) {
    return stateTimerProxy.create(createParameter(details));
  }

  /**
   * Modify an existing StateTimer instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(StateTimerDetails details) {
    return stateTimerProxy.modify(createParameter(details));
  }

  /**
   * Create a StateTimer instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return stateTimerProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target StateTimer instance by id.
   *
   * @deprecated Use {@link StateTimerAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return stateTimerProxy.delete(createParameter(oid));
  }

  /**
   * Delete target StateTimer instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return stateTimerProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the StateTimer element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends StateTimerFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return stateTimerProxy.find(createParameter(searchDetails));
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

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

