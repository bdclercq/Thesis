package net.democritus.gui;

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

public class ThumbnailAgent implements ThumbnailAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(ThumbnailAgent.class);

  private static final ThumbnailProxy thumbnailProxy = ThumbnailProxy.getThumbnailProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private ThumbnailAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static ThumbnailAgent getThumbnailAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new ThumbnailAgent(context, userContext);
  }

  @Deprecated
  public static ThumbnailAgent getThumbnailAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new ThumbnailAgent(context, userContext);
  }

  @Deprecated
  public static ThumbnailAgent getThumbnailAgent() {
    return getThumbnailAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    ThumbnailFindAllThumbnailsDetails finder = new ThumbnailFindAllThumbnailsDetails();
    return thumbnailProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<ThumbnailInfo> findAllInfos() {
    ThumbnailFindAllThumbnailsDetails finder = new ThumbnailFindAllThumbnailsDetails();
    return thumbnailProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long thumbnailId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(thumbnailId));
    CrudsResult<DataRef> result = thumbnailProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public ThumbnailInfo getInfo(Long thumbnailId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(thumbnailId));
    CrudsResult<ThumbnailInfo> result = thumbnailProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ThumbnailInfo();
    }
  }

  public ThumbnailDetails getDetails_old(Long thumbnailId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(thumbnailId));
    CrudsResult<ThumbnailDetails> result = thumbnailProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ThumbnailDetails();
    }
  }

  public CrudsResult<ThumbnailDetails> getDetails(Long thumbnailId) {
    return thumbnailProxy.getDetails(createParameter(thumbnailId));
  }

  public CrudsResult<ThumbnailDetails> getDetails(DataRef dataRef) {
    return thumbnailProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return thumbnailProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return thumbnailProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long thumbnailId) {
    CrudsResult<String> result = thumbnailProxy.getName(createParameter(thumbnailId));
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

  public Long getId(String thumbnailName) {
    CrudsResult<DataRef> result = thumbnailProxy.getId(createParameter(thumbnailName));
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
    return thumbnailProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Thumbnail instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(ThumbnailDetails details) {
    return thumbnailProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Thumbnail instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(ThumbnailDetails details) {
    return thumbnailProxy.modify(createParameter(details));
  }

  /**
   * Create a Thumbnail instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return thumbnailProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Thumbnail instance by id.
   *
   * @deprecated Use {@link ThumbnailAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return thumbnailProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Thumbnail instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return thumbnailProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Thumbnail element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends ThumbnailFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return thumbnailProxy.find(createParameter(searchDetails));
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

