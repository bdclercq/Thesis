package net.democritus.wfe;

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
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

// @anchor:imports:start
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.workflow.WorkflowAgent;
import net.democritus.wfe.TimeWindowGroupAgent;
import net.democritus.wfe.EngineNodeServiceAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineServiceAgent implements EngineServiceAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(EngineServiceAgent.class);

  private static final EngineServiceProxy engineServiceProxy = EngineServiceProxy.getEngineServiceProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private EngineServiceAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static EngineServiceAgent getEngineServiceAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new EngineServiceAgent(context, userContext);
  }

  @Deprecated
  public static EngineServiceAgent getEngineServiceAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new EngineServiceAgent(context, userContext);
  }

  @Deprecated
  public static EngineServiceAgent getEngineServiceAgent() {
    return getEngineServiceAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    EngineServiceFindAllEngineServicesDetails finder = new EngineServiceFindAllEngineServicesDetails();
    return engineServiceProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<EngineServiceInfo> findAllInfos() {
    EngineServiceFindAllEngineServicesDetails finder = new EngineServiceFindAllEngineServicesDetails();
    return engineServiceProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long engineServiceId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(engineServiceId));
    CrudsResult<DataRef> result = engineServiceProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public EngineServiceInfo getInfo(Long engineServiceId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(engineServiceId));
    CrudsResult<EngineServiceInfo> result = engineServiceProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new EngineServiceInfo();
    }
  }

  public EngineServiceDetails getDetails_old(Long engineServiceId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(engineServiceId));
    CrudsResult<EngineServiceDetails> result = engineServiceProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new EngineServiceDetails();
    }
  }

  public CrudsResult<EngineServiceDetails> getDetails(Long engineServiceId) {
    return engineServiceProxy.getDetails(createParameter(engineServiceId));
  }

  public CrudsResult<EngineServiceDetails> getDetails(DataRef dataRef) {
    return engineServiceProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return engineServiceProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return engineServiceProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long engineServiceId) {
    CrudsResult<String> result = engineServiceProxy.getName(createParameter(engineServiceId));
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

  public Long getId(String engineServiceName) {
    CrudsResult<DataRef> result = engineServiceProxy.getId(createParameter(engineServiceName));
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
    return engineServiceProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new EngineService instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(EngineServiceDetails details) {
    return engineServiceProxy.create(createParameter(details));
  }

  /**
   * Modify an existing EngineService instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(EngineServiceDetails details) {
    return engineServiceProxy.modify(createParameter(details));
  }

  /**
   * Create a EngineService instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return engineServiceProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target EngineService instance by id.
   *
   * @deprecated Use {@link EngineServiceAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return engineServiceProxy.delete(createParameter(oid));
  }

  /**
   * Delete target EngineService instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return engineServiceProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the EngineService element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends EngineServiceFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return engineServiceProxy.find(createParameter(searchDetails));
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

  /*========= Handle commands ========*/

  /**
   * Perform target command
   *
   * @param command a command object representing the command
   * @return success if the command was executed successfully
   */
  public <T extends ICommand> CommandResult perform(T command) {
    return engineServiceProxy.perform(createParameter(command));
  }

  // @anchor:methods:start
  /**
   * Import a file containing EngineService data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return engineServiceProxy.importFile(createParameter(importFile));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

