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

public class RemoteAssetAgent implements RemoteAssetAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(RemoteAssetAgent.class);

  private static final RemoteAssetProxy remoteAssetProxy = RemoteAssetProxy.getRemoteAssetProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private RemoteAssetAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static RemoteAssetAgent getRemoteAssetAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new RemoteAssetAgent(context, userContext);
  }

  @Deprecated
  public static RemoteAssetAgent getRemoteAssetAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new RemoteAssetAgent(context, userContext);
  }

  @Deprecated
  public static RemoteAssetAgent getRemoteAssetAgent() {
    return getRemoteAssetAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    RemoteAssetFindAllRemoteAssetsDetails finder = new RemoteAssetFindAllRemoteAssetsDetails();
    return remoteAssetProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<RemoteAssetInfo> findAllInfos() {
    RemoteAssetFindAllRemoteAssetsDetails finder = new RemoteAssetFindAllRemoteAssetsDetails();
    return remoteAssetProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long remoteAssetId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(remoteAssetId));
    CrudsResult<DataRef> result = remoteAssetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public RemoteAssetInfo getInfo(Long remoteAssetId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(remoteAssetId));
    CrudsResult<RemoteAssetInfo> result = remoteAssetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new RemoteAssetInfo();
    }
  }

  public RemoteAssetDetails getDetails_old(Long remoteAssetId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(remoteAssetId));
    CrudsResult<RemoteAssetDetails> result = remoteAssetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new RemoteAssetDetails();
    }
  }

  public CrudsResult<RemoteAssetDetails> getDetails(Long remoteAssetId) {
    return remoteAssetProxy.getDetails(createParameter(remoteAssetId));
  }

  public CrudsResult<RemoteAssetDetails> getDetails(DataRef dataRef) {
    return remoteAssetProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return remoteAssetProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return remoteAssetProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long remoteAssetId) {
    CrudsResult<String> result = remoteAssetProxy.getName(createParameter(remoteAssetId));
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

  public Long getId(String remoteAssetName) {
    CrudsResult<DataRef> result = remoteAssetProxy.getId(createParameter(remoteAssetName));
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
    return remoteAssetProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new RemoteAsset instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(RemoteAssetDetails details) {
    return remoteAssetProxy.create(createParameter(details));
  }

  /**
   * Modify an existing RemoteAsset instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(RemoteAssetDetails details) {
    return remoteAssetProxy.modify(createParameter(details));
  }

  /**
   * Create a RemoteAsset instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return remoteAssetProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target RemoteAsset instance by id.
   *
   * @deprecated Use {@link RemoteAssetAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return remoteAssetProxy.delete(createParameter(oid));
  }

  /**
   * Delete target RemoteAsset instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return remoteAssetProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the RemoteAsset element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends RemoteAssetFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return remoteAssetProxy.find(createParameter(searchDetails));
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

