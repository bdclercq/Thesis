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

public class AddressLocalAgent implements AddressAgentIf {

  private final AddressLocal addressLocal;
  private final UserContext userContext;
  private final Context context;

  private static ComponentJNDI componentJNDI;
  private static String localName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Deprecated
  public static AddressLocalAgent getAddressAgent(UserContext userContext) {
    return new AddressLocalAgent(getLocalInstance(), userContext);
  }

  public static AddressLocalAgent getAddressAgent(Context context) {
    UserContext userContext = context
            .getContext(UserContext.class)
            .orElse((UserContext) null);
    return new AddressLocalAgent(getLocalInstance(), userContext, context);
  }

  private static AddressLocal getLocalInstance() {
    try {
      if (componentJNDI == null) {
        componentJNDI = ComponentJNDI.getComponentJNDI("onlineCabBookingComp");
      }

      if (localName == null) {
        localName = componentJNDI.getDataLocalName("Address");
      }

      return (AddressLocal) componentJNDI.lookupLocal(localName);
    } catch (Exception e) {
      throw new RuntimeException("Could not get instance of 'cabBookingCore.AddressLocal'", e);
    }
  }

  public AddressLocalAgent(AddressLocal addressLocal, UserContext userContext) {
    this.addressLocal = addressLocal;
    this.userContext = userContext;
    this.context = Context.from(userContext);
  }

  public AddressLocalAgent(AddressLocal addressLocal, UserContext userContext, Context context) {
    this.addressLocal = addressLocal;
    this.userContext = userContext;
    this.context = context;
  }

  /**
   * Create a new Address instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  @Override public CrudsResult<DataRef> create(AddressDetails addressDetails) {
    return addressLocal.create(createParameter(addressDetails));
  }

  /**
   * Modify an existing Address instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  @Override public CrudsResult<DataRef> modify(AddressDetails addressDetails) {
    return addressLocal.modify(createParameter(addressDetails));
  }

  /**
   * Create a Address instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return addressLocal.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Address instance by id.
   *
   * @deprecated Use {@link AddressAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  @Override public CrudsResult<Void> delete(Long id) {
    return addressLocal.delete(createParameter(id));
  }

  /**
   * Delete target Address instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return addressLocal.deleteByDataRef(createParameter(target));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  @Override public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return addressLocal.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  @Override public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return addressLocal.resolveDataRef(createParameter(dataRef));
  }

  @Override public CrudsResult<AddressDetails> getDetails(DataRef dataRef) {
    return getProjection(new ProjectionRef("details", dataRef));
  }

  /**
   * Find instances of the Address element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  @Override public <S extends AddressFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return addressLocal.find(createParameter(searchDetails));
  }

  public CrudsResult<AddressDetails> getDetails(Long id) {
    return getDetails(new DataRef(id));
  }

  public SearchResult<DataRef> findAllDataRefs() {
    AddressFindAllAddresssDetails finder = new AddressFindAllAddresssDetails();
    return addressLocal.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<AddressInfo> findAllInfos() {
    AddressFindAllAddresssDetails finder = new AddressFindAllAddresssDetails();
    return addressLocal.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return addressLocal.getId(createParameter(name));
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
