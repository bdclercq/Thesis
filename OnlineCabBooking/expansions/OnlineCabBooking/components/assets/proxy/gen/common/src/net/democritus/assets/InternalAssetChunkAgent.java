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
import net.democritus.assets.InternalAssetAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class InternalAssetChunkAgent implements InternalAssetChunkAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(InternalAssetChunkAgent.class);

  private static final InternalAssetChunkProxy internalAssetChunkProxy = InternalAssetChunkProxy.getInternalAssetChunkProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private InternalAssetChunkAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static InternalAssetChunkAgent getInternalAssetChunkAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new InternalAssetChunkAgent(context, userContext);
  }

  @Deprecated
  public static InternalAssetChunkAgent getInternalAssetChunkAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new InternalAssetChunkAgent(context, userContext);
  }

  @Deprecated
  public static InternalAssetChunkAgent getInternalAssetChunkAgent() {
    return getInternalAssetChunkAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    InternalAssetChunkFindAllInternalAssetChunksDetails finder = new InternalAssetChunkFindAllInternalAssetChunksDetails();
    return internalAssetChunkProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<InternalAssetChunkInfo> findAllInfos() {
    InternalAssetChunkFindAllInternalAssetChunksDetails finder = new InternalAssetChunkFindAllInternalAssetChunksDetails();
    return internalAssetChunkProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long internalAssetChunkId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(internalAssetChunkId));
    CrudsResult<DataRef> result = internalAssetChunkProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public InternalAssetChunkInfo getInfo(Long internalAssetChunkId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(internalAssetChunkId));
    CrudsResult<InternalAssetChunkInfo> result = internalAssetChunkProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new InternalAssetChunkInfo();
    }
  }

  public InternalAssetChunkDetails getDetails_old(Long internalAssetChunkId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(internalAssetChunkId));
    CrudsResult<InternalAssetChunkDetails> result = internalAssetChunkProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new InternalAssetChunkDetails();
    }
  }

  public CrudsResult<InternalAssetChunkDetails> getDetails(Long internalAssetChunkId) {
    return internalAssetChunkProxy.getDetails(createParameter(internalAssetChunkId));
  }

  public CrudsResult<InternalAssetChunkDetails> getDetails(DataRef dataRef) {
    return internalAssetChunkProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return internalAssetChunkProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return internalAssetChunkProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long internalAssetChunkId) {
    CrudsResult<String> result = internalAssetChunkProxy.getName(createParameter(internalAssetChunkId));
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

  public Long getId(String internalAssetChunkName) {
    CrudsResult<DataRef> result = internalAssetChunkProxy.getId(createParameter(internalAssetChunkName));
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
    return internalAssetChunkProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new InternalAssetChunk instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(InternalAssetChunkDetails details) {
    return internalAssetChunkProxy.create(createParameter(details));
  }

  /**
   * Modify an existing InternalAssetChunk instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(InternalAssetChunkDetails details) {
    return internalAssetChunkProxy.modify(createParameter(details));
  }

  /**
   * Create a InternalAssetChunk instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return internalAssetChunkProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target InternalAssetChunk instance by id.
   *
   * @deprecated Use {@link InternalAssetChunkAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return internalAssetChunkProxy.delete(createParameter(oid));
  }

  /**
   * Delete target InternalAssetChunk instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return internalAssetChunkProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the InternalAssetChunk element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends InternalAssetChunkFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return internalAssetChunkProxy.find(createParameter(searchDetails));
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

