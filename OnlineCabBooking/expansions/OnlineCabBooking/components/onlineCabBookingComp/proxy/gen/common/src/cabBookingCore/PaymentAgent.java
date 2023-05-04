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
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class PaymentAgent implements PaymentAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(PaymentAgent.class);

  private static final PaymentProxy paymentProxy = PaymentProxy.getPaymentProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private PaymentAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static PaymentAgent getPaymentAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new PaymentAgent(context, userContext);
  }

  @Deprecated
  public static PaymentAgent getPaymentAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new PaymentAgent(context, userContext);
  }

  @Deprecated
  public static PaymentAgent getPaymentAgent() {
    return getPaymentAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    PaymentFindAllPaymentsDetails finder = new PaymentFindAllPaymentsDetails();
    return paymentProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<PaymentInfo> findAllInfos() {
    PaymentFindAllPaymentsDetails finder = new PaymentFindAllPaymentsDetails();
    return paymentProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long paymentId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(paymentId));
    CrudsResult<DataRef> result = paymentProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public PaymentInfo getInfo(Long paymentId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(paymentId));
    CrudsResult<PaymentInfo> result = paymentProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new PaymentInfo();
    }
  }

  public PaymentDetails getDetails_old(Long paymentId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(paymentId));
    CrudsResult<PaymentDetails> result = paymentProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new PaymentDetails();
    }
  }

  public CrudsResult<PaymentDetails> getDetails(Long paymentId) {
    return paymentProxy.getDetails(createParameter(paymentId));
  }

  public CrudsResult<PaymentDetails> getDetails(DataRef dataRef) {
    return paymentProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return paymentProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return paymentProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long paymentId) {
    CrudsResult<String> result = paymentProxy.getName(createParameter(paymentId));
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

  public Long getId(String paymentName) {
    CrudsResult<DataRef> result = paymentProxy.getId(createParameter(paymentName));
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
    return paymentProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Payment instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(PaymentDetails details) {
    return paymentProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Payment instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(PaymentDetails details) {
    return paymentProxy.modify(createParameter(details));
  }

  /**
   * Create a Payment instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return paymentProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Payment instance by id.
   *
   * @deprecated Use {@link PaymentAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return paymentProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Payment instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return paymentProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Payment element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends PaymentFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return paymentProxy.find(createParameter(searchDetails));
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

