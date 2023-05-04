package net.democritus.usr;

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

public class PortalAgent implements PortalAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(PortalAgent.class);

  private static final PortalProxy portalProxy = PortalProxy.getPortalProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private PortalAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static PortalAgent getPortalAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new PortalAgent(context, userContext);
  }

  @Deprecated
  public static PortalAgent getPortalAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new PortalAgent(context, userContext);
  }

  @Deprecated
  public static PortalAgent getPortalAgent() {
    return getPortalAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    PortalFindAllPortalsDetails finder = new PortalFindAllPortalsDetails();
    return portalProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<PortalInfo> findAllInfos() {
    PortalFindAllPortalsDetails finder = new PortalFindAllPortalsDetails();
    return portalProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long portalId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(portalId));
    CrudsResult<DataRef> result = portalProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public PortalInfo getInfo(Long portalId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(portalId));
    CrudsResult<PortalInfo> result = portalProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new PortalInfo();
    }
  }

  public PortalDetails getDetails_old(Long portalId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(portalId));
    CrudsResult<PortalDetails> result = portalProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new PortalDetails();
    }
  }

  public CrudsResult<PortalDetails> getDetails(Long portalId) {
    return portalProxy.getDetails(createParameter(portalId));
  }

  public CrudsResult<PortalDetails> getDetails(DataRef dataRef) {
    return portalProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return portalProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return portalProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long portalId) {
    CrudsResult<String> result = portalProxy.getName(createParameter(portalId));
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

  public Long getId(String portalName) {
    CrudsResult<DataRef> result = portalProxy.getId(createParameter(portalName));
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
    return portalProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Portal instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(PortalDetails details) {
    return portalProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Portal instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(PortalDetails details) {
    return portalProxy.modify(createParameter(details));
  }

  /**
   * Create a Portal instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return portalProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Portal instance by id.
   *
   * @deprecated Use {@link PortalAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return portalProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Portal instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return portalProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Portal element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends PortalFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return portalProxy.find(createParameter(searchDetails));
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

