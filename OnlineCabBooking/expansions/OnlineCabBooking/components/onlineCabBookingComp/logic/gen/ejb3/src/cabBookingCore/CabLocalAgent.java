package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.search.SearchDetails;
import net.democritus.support.Paging;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class CabLocalAgent implements CabAgentIf {

  private final CabLocal cabLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static CabLocalAgent getCabAgent(UserContext userContext) {
    return new CabLocalAgent(getLocalInstance(), userContext);
  }

  public static CabLocalAgent getCabAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new CabLocalAgent(getLocalInstance(), userContext, context);
  }

  private static CabLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("onlineCabBookingComp");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("Cab");
      }

      return (CabLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'cabBookingCore.CabLocal'", e);
    }
  }

  public CabLocalAgent(CabLocal cabLocal, UserContext userContext) {
    this.cabLocal = cabLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public CabLocalAgent(CabLocal cabLocal, UserContext userContext, Context context) {
    this.cabLocal = cabLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new Cab instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(CabDetails cabDetails) {
    return cabLocal.create(createParameter(cabDetails));
  }

  /**
   * Modify an existing Cab instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(CabDetails cabDetails) {
    return cabLocal.modify(createParameter(cabDetails));
  }

  /**
   * Create a Cab instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return cabLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Cab instance by id.
   *
   * @deprecated Use {@link CabAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return cabLocal.delete(createParameter(id));
  }

  /**
   * Delete target Cab instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return cabLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return cabLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return cabLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<CabDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the Cab element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends CabFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return cabLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<CabDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    CabFindAllCabsDetails finder = new CabFindAllCabsDetails();
    return cabLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<CabInfo> findAllInfos() {
    CabFindAllCabsDetails finder = new CabFindAllCabsDetails();
    return cabLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return cabLocal.getId(createParameter(name));
  }

  private <T> ParameterContext<T> createParameter(T value) {
    return context.withParameter(value);
  }

  private ParameterContext<Void> createEmptyParameter() {
    return context.emptyParameter();
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
