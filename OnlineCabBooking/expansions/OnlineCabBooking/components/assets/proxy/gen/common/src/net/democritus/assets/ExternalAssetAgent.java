package net.democritus.assets;

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
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ExternalAssetAgent implements ExternalAssetAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(ExternalAssetAgent.class);

  private static final ExternalAssetProxy externalAssetProxy = ExternalAssetProxy.getExternalAssetProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private ExternalAssetAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static ExternalAssetAgent getExternalAssetAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new ExternalAssetAgent(context, userContext);
  }

  @Deprecated
  public static ExternalAssetAgent getExternalAssetAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new ExternalAssetAgent(context, userContext);
  }

  @Deprecated
  public static ExternalAssetAgent getExternalAssetAgent() {
    return getExternalAssetAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    ExternalAssetFindAllExternalAssetsDetails finder = new ExternalAssetFindAllExternalAssetsDetails();
    return externalAssetProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<ExternalAssetInfo> findAllInfos() {
    ExternalAssetFindAllExternalAssetsDetails finder = new ExternalAssetFindAllExternalAssetsDetails();
    return externalAssetProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long externalAssetId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(externalAssetId));
    CrudsResult<DataRef> result = externalAssetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public ExternalAssetInfo getInfo(Long externalAssetId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(externalAssetId));
    CrudsResult<ExternalAssetInfo> result = externalAssetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ExternalAssetInfo();
    }
  }

  public ExternalAssetDetails getDetails_old(Long externalAssetId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(externalAssetId));
    CrudsResult<ExternalAssetDetails> result = externalAssetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ExternalAssetDetails();
    }
  }

  public CrudsResult<ExternalAssetDetails> getDetails(Long externalAssetId) {
    return externalAssetProxy.getDetails(createParameter(externalAssetId));
  }

  public CrudsResult<ExternalAssetDetails> getDetails(DataRef dataRef) {
    return externalAssetProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return externalAssetProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return externalAssetProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long externalAssetId) {
    CrudsResult<String> result = externalAssetProxy.getName(createParameter(externalAssetId));
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

  public Long getId(String externalAssetName) {
    CrudsResult<DataRef> result = externalAssetProxy.getId(createParameter(externalAssetName));
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
    return externalAssetProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new ExternalAsset instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(ExternalAssetDetails details) {
    return externalAssetProxy.create(createParameter(details));
  }

  /**
   * Modify an existing ExternalAsset instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(ExternalAssetDetails details) {
    return externalAssetProxy.modify(createParameter(details));
  }

  /**
   * Create a ExternalAsset instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return externalAssetProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target ExternalAsset instance by id.
   *
   * @deprecated Use {@link ExternalAssetAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return externalAssetProxy.delete(createParameter(oid));
  }

  /**
   * Delete target ExternalAsset instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return externalAssetProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the ExternalAsset element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends ExternalAssetFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return externalAssetProxy.find(createParameter(searchDetails));
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

