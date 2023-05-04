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
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.workflow.WorkflowAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class TimeTaskAgent implements TimeTaskAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(TimeTaskAgent.class);

  private static final TimeTaskProxy timeTaskProxy = TimeTaskProxy.getTimeTaskProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private TimeTaskAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static TimeTaskAgent getTimeTaskAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new TimeTaskAgent(context, userContext);
  }

  @Deprecated
  public static TimeTaskAgent getTimeTaskAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new TimeTaskAgent(context, userContext);
  }

  @Deprecated
  public static TimeTaskAgent getTimeTaskAgent() {
    return getTimeTaskAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    TimeTaskFindAllTimeTasksDetails finder = new TimeTaskFindAllTimeTasksDetails();
    return timeTaskProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<TimeTaskInfo> findAllInfos() {
    TimeTaskFindAllTimeTasksDetails finder = new TimeTaskFindAllTimeTasksDetails();
    return timeTaskProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long timeTaskId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(timeTaskId));
    CrudsResult<DataRef> result = timeTaskProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public TimeTaskInfo getInfo(Long timeTaskId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(timeTaskId));
    CrudsResult<TimeTaskInfo> result = timeTaskProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new TimeTaskInfo();
    }
  }

  public TimeTaskDetails getDetails_old(Long timeTaskId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(timeTaskId));
    CrudsResult<TimeTaskDetails> result = timeTaskProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new TimeTaskDetails();
    }
  }

  public CrudsResult<TimeTaskDetails> getDetails(Long timeTaskId) {
    return timeTaskProxy.getDetails(createParameter(timeTaskId));
  }

  public CrudsResult<TimeTaskDetails> getDetails(DataRef dataRef) {
    return timeTaskProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return timeTaskProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return timeTaskProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long timeTaskId) {
    CrudsResult<String> result = timeTaskProxy.getName(createParameter(timeTaskId));
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

  public Long getId(String timeTaskName) {
    CrudsResult<DataRef> result = timeTaskProxy.getId(createParameter(timeTaskName));
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
    return timeTaskProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new TimeTask instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(TimeTaskDetails details) {
    return timeTaskProxy.create(createParameter(details));
  }

  /**
   * Modify an existing TimeTask instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(TimeTaskDetails details) {
    return timeTaskProxy.modify(createParameter(details));
  }

  /**
   * Create a TimeTask instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return timeTaskProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target TimeTask instance by id.
   *
   * @deprecated Use {@link TimeTaskAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return timeTaskProxy.delete(createParameter(oid));
  }

  /**
   * Delete target TimeTask instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return timeTaskProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the TimeTask element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends TimeTaskFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return timeTaskProxy.find(createParameter(searchDetails));
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
  /**
   * Import a file containing TimeTask data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return timeTaskProxy.importFile(createParameter(importFile));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

