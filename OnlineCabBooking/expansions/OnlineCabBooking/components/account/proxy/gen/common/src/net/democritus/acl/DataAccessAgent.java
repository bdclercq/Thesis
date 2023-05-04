package net.democritus.acl;

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
import net.democritus.usr.ProfileAgent;
import net.democritus.usr.UserAgent;
import net.democritus.usr.UserGroupAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class DataAccessAgent implements DataAccessAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(DataAccessAgent.class);

  private static final DataAccessProxy dataAccessProxy = DataAccessProxy.getDataAccessProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private DataAccessAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static DataAccessAgent getDataAccessAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new DataAccessAgent(context, userContext);
  }

  @Deprecated
  public static DataAccessAgent getDataAccessAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new DataAccessAgent(context, userContext);
  }

  @Deprecated
  public static DataAccessAgent getDataAccessAgent() {
    return getDataAccessAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    DataAccessFindAllDataAccesssDetails finder = new DataAccessFindAllDataAccesssDetails();
    return dataAccessProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<DataAccessInfo> findAllInfos() {
    DataAccessFindAllDataAccesssDetails finder = new DataAccessFindAllDataAccesssDetails();
    return dataAccessProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long dataAccessId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(dataAccessId));
    CrudsResult<DataRef> result = dataAccessProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public DataAccessInfo getInfo(Long dataAccessId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(dataAccessId));
    CrudsResult<DataAccessInfo> result = dataAccessProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new DataAccessInfo();
    }
  }

  public DataAccessDetails getDetails_old(Long dataAccessId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(dataAccessId));
    CrudsResult<DataAccessDetails> result = dataAccessProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new DataAccessDetails();
    }
  }

  public CrudsResult<DataAccessDetails> getDetails(Long dataAccessId) {
    return dataAccessProxy.getDetails(createParameter(dataAccessId));
  }

  public CrudsResult<DataAccessDetails> getDetails(DataRef dataRef) {
    return dataAccessProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return dataAccessProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return dataAccessProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long dataAccessId) {
    CrudsResult<String> result = dataAccessProxy.getName(createParameter(dataAccessId));
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

  public Long getId(String dataAccessName) {
    CrudsResult<DataRef> result = dataAccessProxy.getId(createParameter(dataAccessName));
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
    return dataAccessProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new DataAccess instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(DataAccessDetails details) {
    return dataAccessProxy.create(createParameter(details));
  }

  /**
   * Modify an existing DataAccess instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(DataAccessDetails details) {
    return dataAccessProxy.modify(createParameter(details));
  }

  /**
   * Create a DataAccess instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return dataAccessProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target DataAccess instance by id.
   *
   * @deprecated Use {@link DataAccessAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return dataAccessProxy.delete(createParameter(oid));
  }

  /**
   * Delete target DataAccess instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return dataAccessProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the DataAccess element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends DataAccessFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return dataAccessProxy.find(createParameter(searchDetails));
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
   * Import a file containing DataAccess data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return dataAccessProxy.importFile(createParameter(importFile));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

