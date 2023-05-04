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
import net.democritus.usr.UserAgent;
// anchor:imports:end

// anchor:custom-imports:start
import java.util.List;
import net.democritus.sys.FlowResult;
import net.democritus.sys.TaskResult;
// anchor:custom-imports:end

public class WorkflowAgent implements WorkflowAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(WorkflowAgent.class);

  private static final WorkflowProxy workflowProxy = WorkflowProxy.getWorkflowProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private WorkflowAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static WorkflowAgent getWorkflowAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new WorkflowAgent(context, userContext);
  }

  @Deprecated
  public static WorkflowAgent getWorkflowAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new WorkflowAgent(context, userContext);
  }

  @Deprecated
  public static WorkflowAgent getWorkflowAgent() {
    return getWorkflowAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    WorkflowFindAllWorkflowsDetails finder = new WorkflowFindAllWorkflowsDetails();
    return workflowProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<WorkflowInfo> findAllInfos() {
    WorkflowFindAllWorkflowsDetails finder = new WorkflowFindAllWorkflowsDetails();
    return workflowProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long workflowId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(workflowId));
    CrudsResult<DataRef> result = workflowProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public WorkflowInfo getInfo(Long workflowId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(workflowId));
    CrudsResult<WorkflowInfo> result = workflowProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new WorkflowInfo();
    }
  }

  public WorkflowDetails getDetails_old(Long workflowId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(workflowId));
    CrudsResult<WorkflowDetails> result = workflowProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new WorkflowDetails();
    }
  }

  public CrudsResult<WorkflowDetails> getDetails(Long workflowId) {
    return workflowProxy.getDetails(createParameter(workflowId));
  }

  public CrudsResult<WorkflowDetails> getDetails(DataRef dataRef) {
    return workflowProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return workflowProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return workflowProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long workflowId) {
    CrudsResult<String> result = workflowProxy.getName(createParameter(workflowId));
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

  public Long getId(String workflowName) {
    CrudsResult<DataRef> result = workflowProxy.getId(createParameter(workflowName));
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
    return workflowProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Workflow instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(WorkflowDetails details) {
    return workflowProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Workflow instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(WorkflowDetails details) {
    return workflowProxy.modify(createParameter(details));
  }

  /**
   * Create a Workflow instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return workflowProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Workflow instance by id.
   *
   * @deprecated Use {@link WorkflowAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return workflowProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Workflow instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return workflowProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Workflow element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends WorkflowFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return workflowProxy.find(createParameter(searchDetails));
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
   * Import a file containing Workflow data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return workflowProxy.importFile(createParameter(importFile));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  public FlowResult<Void> runWorkflowWithEngineService(DataRef engineServiceParameter) {
    return workflowProxy.runWorkflowWithEngineService(createParameter(engineServiceParameter));
  }

  public FlowResult<Void> runWorkflowWithTargets(DataRef workflowParameter, List<DataRef> targetInstances) {
    return workflowProxy.runWorkflowWithTargets(createParameter(workflowParameter), targetInstances);
  }

  public TaskResult<Void> recoverWorkflow(DataRef workflow) {
    return workflowProxy.recoverWorkflow(createParameter(workflow));
  }
  // anchor:custom-methods:end
}

