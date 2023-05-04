package net.democritus.sys;

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
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ExecutionAgent implements ExecutionAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(ExecutionAgent.class);

  private static final ExecutionProxy executionProxy = ExecutionProxy.getExecutionProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private ExecutionAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static ExecutionAgent getExecutionAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new ExecutionAgent(context, userContext);
  }

  @Deprecated
  public static ExecutionAgent getExecutionAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new ExecutionAgent(context, userContext);
  }

  @Deprecated
  public static ExecutionAgent getExecutionAgent() {
    return getExecutionAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    ExecutionFindAllExecutionsDetails finder = new ExecutionFindAllExecutionsDetails();
    return executionProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<ExecutionInfo> findAllInfos() {
    ExecutionFindAllExecutionsDetails finder = new ExecutionFindAllExecutionsDetails();
    return executionProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long executionId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(executionId));
    CrudsResult<DataRef> result = executionProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public ExecutionInfo getInfo(Long executionId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(executionId));
    CrudsResult<ExecutionInfo> result = executionProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ExecutionInfo();
    }
  }

  public ExecutionDetails getDetails_old(Long executionId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(executionId));
    CrudsResult<ExecutionDetails> result = executionProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ExecutionDetails();
    }
  }

  public CrudsResult<ExecutionDetails> getDetails(Long executionId) {
    return executionProxy.getDetails(createParameter(executionId));
  }

  public CrudsResult<ExecutionDetails> getDetails(DataRef dataRef) {
    return executionProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return executionProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return executionProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long executionId) {
    CrudsResult<String> result = executionProxy.getName(createParameter(executionId));
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

  public Long getId(String executionName) {
    CrudsResult<DataRef> result = executionProxy.getId(createParameter(executionName));
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
    return executionProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Execution instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(ExecutionDetails details) {
    return executionProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Execution instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(ExecutionDetails details) {
    return executionProxy.modify(createParameter(details));
  }

  /**
   * Create a Execution instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return executionProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Execution instance by id.
   *
   * @deprecated Use {@link ExecutionAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return executionProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Execution instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return executionProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Execution element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends ExecutionFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return executionProxy.find(createParameter(searchDetails));
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
   * Import a file containing Execution data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return executionProxy.importFile(createParameter(importFile));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

