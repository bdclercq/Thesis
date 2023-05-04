package cabBookingCore;

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
import cabBookingCore.CarTypeAgent;
import cabBookingCore.DriverAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class CabAgent implements CabAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(CabAgent.class);

  private static final CabProxy cabProxy = CabProxy.getCabProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private CabAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static CabAgent getCabAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new CabAgent(context, userContext);
  }

  @Deprecated
  public static CabAgent getCabAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new CabAgent(context, userContext);
  }

  @Deprecated
  public static CabAgent getCabAgent() {
    return getCabAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    CabFindAllCabsDetails finder = new CabFindAllCabsDetails();
    return cabProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<CabInfo> findAllInfos() {
    CabFindAllCabsDetails finder = new CabFindAllCabsDetails();
    return cabProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long cabId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(cabId));
    CrudsResult<DataRef> result = cabProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public CabInfo getInfo(Long cabId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(cabId));
    CrudsResult<CabInfo> result = cabProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new CabInfo();
    }
  }

  public CabDetails getDetails_old(Long cabId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(cabId));
    CrudsResult<CabDetails> result = cabProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new CabDetails();
    }
  }

  public CrudsResult<CabDetails> getDetails(Long cabId) {
    return cabProxy.getDetails(createParameter(cabId));
  }

  public CrudsResult<CabDetails> getDetails(DataRef dataRef) {
    return cabProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return cabProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return cabProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long cabId) {
    CrudsResult<String> result = cabProxy.getName(createParameter(cabId));
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

  public Long getId(String cabName) {
    CrudsResult<DataRef> result = cabProxy.getId(createParameter(cabName));
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
    return cabProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Cab instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(CabDetails details) {
    return cabProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Cab instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(CabDetails details) {
    return cabProxy.modify(createParameter(details));
  }

  /**
   * Create a Cab instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return cabProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Cab instance by id.
   *
   * @deprecated Use {@link CabAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return cabProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Cab instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return cabProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Cab element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends CabFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return cabProxy.find(createParameter(searchDetails));
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

