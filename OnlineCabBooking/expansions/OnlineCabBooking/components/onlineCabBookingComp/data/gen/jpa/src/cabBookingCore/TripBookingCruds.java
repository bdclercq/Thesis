package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// anchor:base-imports:start
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;

import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.ElementRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.search.SearchDetails;

import net.democritus.projection.IDataElementProjector;
import net.democritus.projection.IDataProjectorRequired;

import net.democritus.projection.DataElementProjectorManager;
import net.democritus.projection.InvalidProjectionException;

import static net.palver.util.Options.none;
import static net.palver.util.Options.some;
import net.palver.util.Options.Option;
import net.palver.util.StringUtil;

import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.DiagnosticFieldFactory;
import net.democritus.sys.diagnostics.DiagnosticHelper;
import static net.democritus.sys.DiagnosticConstants.*;

import javax.persistence.Query;
import javax.persistence.TemporalType;
// anchor:base-imports:end

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.state.StateUpdate;
// @anchor:imports:end

// anchor:imports:start
import cabBookingCore.CustomerData;
import cabBookingCore.CustomerDetails;
import cabBookingCore.CustomerCrudsInternal;
import cabBookingCore.DriverData;
import cabBookingCore.DriverDetails;
import cabBookingCore.DriverCrudsInternal;
import cabBookingCore.AddressData;
import cabBookingCore.AddressDetails;
import cabBookingCore.AddressCrudsInternal;
import cabBookingCore.PaymentData;
import cabBookingCore.PaymentDetails;
import cabBookingCore.PaymentCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless
// @anchor:annotations:start
@Local({TripBookingCrudsLocal.class, TripBookingCrudsInternal.class})
// @anchor:annotations:end
public class TripBookingCruds /*@anchor:interfaces:start@*/implements TripBookingCrudsLocal, TripBookingCrudsInternal/*@anchor:interfaces:end@*/ {

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("onlineCabBookingComp", "TripBooking");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  public static final String DISABLED_ERROR_MSG_KEY = "onlineCabBookingComp.tripBooking.alreadyDisabled";

  @PersistenceContext(unitName="OnlineCabBooking_onlineCabBookingComp")
  private EntityManager entityManager;

  @Resource
  private SessionContext sessionContext;

  private TripBookingFinderLocal tripBookingFinder;

  /* linked bean variables */
  // anchor:link-variables:start
  @EJB
  private CustomerCrudsInternal customerLocal;
  @EJB
  private DriverCrudsInternal driverLocal;
  @EJB
  private AddressCrudsInternal addressLocal;
  @EJB
  private PaymentCrudsInternal paymentLocal;
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingCruds.class);
  // @anchor:variables:end

  /* custom variables */
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private TripBookingDataRefProjector dataRefProjector;
  private TripBookingDetailsProjector detailsProjector;
  private TripBookingInfoProjector infoProjector;

  private DataElementProjectorManager<TripBookingData> elementProjectorManager;


  @PostConstruct
  private void init() {
    initFinder();
    initProjectorMapping();
    // @anchor:initialization:start
    // @anchor:initialization:end

    // anchor:custom-initialization:start
    // anchor:custom-initialization:end
  }

  private void initProjectorMapping() {
    TripBookingCrudsInternal tripBookingCrudsInternal = sessionContext.getBusinessObject(TripBookingCrudsInternal.class);

    detailsProjector = new TripBookingDetailsProjector(tripBookingCrudsInternal);
    infoProjector = new TripBookingInfoProjector(tripBookingCrudsInternal);
    dataRefProjector = new TripBookingDataRefProjector(tripBookingCrudsInternal);

    elementProjectorManager = new DataElementProjectorManager<TripBookingData>();
    elementProjectorManager.addProjector(detailsProjector);
    elementProjectorManager.addProjector(infoProjector);
    elementProjectorManager.addProjector(dataRefProjector);

    // anchor:additional-projectors:start
    // anchor:additional-projectors:end

    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  private void initFinder() {
    tripBookingFinder = new TripBookingFinderBean(entityManager);
  }

  // anchor:create:start
  public CrudsResult<DataRef> create(ParameterContext<TripBookingDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    TripBookingDetails details = detailsParameter.getValue();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    TripBookingData tripBookingData = new TripBookingData();
    try {

      // @anchor:create-beforeProjection:start
      // @anchor:create-beforeProjection:end

      // anchor:custom-create-beforeProjection:start
      // anchor:custom-create-beforeProjection:end

      detailsProjector.toData(tripBookingData, detailsParameter);

      // @anchor:create-afterProjection:start
      // @anchor:create-afterProjection:end

      // anchor:custom-create-afterProjection:start
      // anchor:custom-create-afterProjection:end

    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot fill data object", e
        );
      }

      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    // @anchor:implicitNameFieldOnly-beforePersist:start
    String identity = details.getName();
    if (identity != null) {
      identity = identity.trim();
      identity = identity.length() > 0 ? identity : null;
    }
    // @anchor:implicitNameFieldOnly-beforePersist:end

    try {
      entityManager.persist(tripBookingData);

      // @anchor:implicitNameFieldOnly-afterPersist:start
      if (identity == null) {
        identity = "T-" + tripBookingData.getId().toString();
      }
      tripBookingData.setName(identity);
      entityManager.flush();
      // @anchor:implicitNameFieldOnly-afterPersist:end

      if (tripBookingData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Created TripBookingCruds with { id : " + tripBookingData.getId() + ", name: " + tripBookingData.getName() + " }"
          );
        }
      }
    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot perform entry creation", e
        );
      }
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(tripBookingData));

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // anchor:create:end

  // anchor:modify:start
  public CrudsResult<DataRef> modify(ParameterContext<TripBookingDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    TripBookingDetails tripBookingDetails = detailsParameter.getValue();
    DataRef dataRef = tripBookingDetails.getDataRef();

    // @anchor:preModify:start
    // @anchor:preModify:end
    // anchor:custom-preModify:start
    // anchor:custom-preModify:end
    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    try {
      CrudsResult<TripBookingData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      TripBookingData tripBookingData = dataResult.getValue();

      // @anchor:modify-beforeProjection:start
      // @anchor:modify-beforeProjection:end
      // anchor:custom-modify-beforeProjection:start
      // anchor:custom-modify-beforeProjection:end

      detailsProjector.toData(tripBookingData, detailsParameter);

      // @anchor:modify-afterProjection:start
      // @anchor:modify-afterProjection:end
      // anchor:custom-modify-afterProjection:start
      // anchor:custom-modify-afterProjection:end

      entityManager.flush();
      CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(tripBookingData));

      if (tripBookingData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Modified TripBookingCruds with { id : " + tripBookingData.getId() + ", name: " + tripBookingData.getName() + " }"
          );
        }
      }

      // @anchor:postModify:start
      // @anchor:postModify:end
      // anchor:custom-postModify:start
      // anchor:custom-postModify:end

      return result;

    // @anchor:modify-exceptions:start
    // @anchor:modify-exceptions:end
    } catch(Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "TripBookingCruds.modify() failed with ID = " + dataRef.getId(), e
        );
      }
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }
  }
  // anchor:modify:end

  // anchor:createOrModify:start
  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    P projection = projectionParameter.getValue();
    net.democritus.sys.Context context = projectionParameter.getContext();
    CrudsResult<DataRef> result;

    // @anchor:preCreateOrModify:start
    // @anchor:preCreateOrModify:end
    // anchor:custom-preCreateOrModify:start
    // anchor:custom-preCreateOrModify:end

    if (projection instanceof TripBookingDetails) {
      TripBookingDetails details = (TripBookingDetails) projection;
      DataRef dataRef = details.getDataRef();
      CrudsResult<DataRef> dataRefResult = resolveDataRef(context.<DataRef>withParameter(dataRef));
      if (dataRefResult.isSuccess() && DataRefValidation.isDataRefDefined(dataRefResult.getValue())) {
        result = modify(context.withParameter(details));
      } else {
        result = create(context.withParameter(details));
      }
    } else {
      result = diagnosticHelper.createCrudsError("projection not supported");
      // @anchor:createOrModify-projection:start
      // @anchor:createOrModify-projection:end
      // anchor:custom-createOrModify-projection:start
      // anchor:custom-createOrModify-projection:end
    }

    if (result.isError()) {
      return result;
    }

    // @anchor:postCreateOrModify:start
    // @anchor:postCreateOrModify:end
    // anchor:custom-postCreateOrModify:start
    // anchor:custom-postCreateOrModify:end

    return result;
  }
  // anchor:createOrModify:end

  // anchor:delete:start
  public CrudsResult<Void> delete(ParameterContext<DataRef> dataRefParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }

    Context context = dataRefParameter.getContext();
    DataRef dataRef = dataRefParameter.getValue();

    // @anchor:preDelete-fetch:start
    // @anchor:preDelete-fetch:end
    // anchor:custom-preDelete-fetch:start
    // anchor:custom-preDelete-fetch:end

    CrudsResult<DataRef> dataRefResult = resolveDataRef(dataRefParameter);
    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    dataRef = dataRefResult.getValue();

    // @anchor:preDelete:start
    // @anchor:preDelete:end
    // anchor:custom-preDelete:start
    // anchor:custom-preDelete:end

    // @anchor:preDelete-validation:start
    // @anchor:preDelete-validation:end

    try {
      CrudsResult<TripBookingData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      TripBookingData tripBookingData = dataResult.getValue();

      // anchor:custom-delete:start
      // anchor:custom-delete:end
      // @anchor:delete:start
      entityManager.remove(tripBookingData);
      // @anchor:delete:end

      // @anchor:postDelete:start
      // @anchor:postDelete:end
      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      if (tripBookingData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Deleted TripBookingCruds with { id : " + tripBookingData.getId() + ", name: " + tripBookingData.getName() + " }"
          );
        }
      }

      return CrudsResult.success(null);
    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }
  }
  // anchor:delete:end

  // anchor:findMethods:start
  public CrudsResult<DataRef> getDataRefFromData(ParameterContext<TripBookingData> dataParameter) {
    DataRef dataRef = dataRefProjector.project(dataParameter);
    return CrudsResult.success(dataRef);
  }

  public CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();
    if (id == null || id == 0L) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    try {
      TripBookingData tripBookingData = entityManager.find(TripBookingData.class, id);
      // @anchor:getDataRefFromId-afterFetch:start
      // @anchor:getDataRefFromId-afterFetch:end
      // anchor:custom-getDataRefFromId-afterFetch:start
      // anchor:custom-getDataRefFromId-afterFetch:end
      return getDataRefFromData(idParameter.construct(tripBookingData));
    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Get DataRef from ID failed due to unexpected Exception", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }

  private CrudsResult<TripBookingData> getDataById(ParameterContext<Long> idParameter) {
    TripBookingData data = entityManager.find(TripBookingData.class, idParameter.getValue());
    if (data == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
    return CrudsResult.success(data);
  }

  public CrudsResult<DataRef> getDataRefFromName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    CrudsResult<TripBookingData> dataResult = getDataByName(nameParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }

    // @anchor:getDataRefFromName-afterFetch:start
    // @anchor:getDataRefFromName-afterFetch:end
    // anchor:custom-getDataRefFromName-afterFetch:start
    // anchor:custom-getDataRefFromName-afterFetch:end
    return getDataRefFromData(nameParameter.construct(dataResult.getValue()));
  }

  private CrudsResult<TripBookingData> getDataByName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    try {
      TripBookingFindByNameEqDetails findByNameEqDetails = new TripBookingFindByNameEqDetails();
      findByNameEqDetails.setName(name);

      SearchDetails<TripBookingFindByNameEqDetails> searchDetails =
        new SearchDetails<>(findByNameEqDetails);

      ParameterContext<SearchDetails<TripBookingFindByNameEqDetails>> searchParameter =
        nameParameter.construct(searchDetails);

      SearchResult<TripBookingData> searchResult = findData(searchParameter);

      if (searchResult.isError()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      List<TripBookingData> list = searchResult.getResults();
      if (list.isEmpty()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      return CrudsResult.success(list.get(0));

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot find object id " + name, e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }

  public CrudsResult<TripBookingData> getData(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    Long id = dataRef.getId();
    CrudsResult<TripBookingData> result;
    if (id != null && id.longValue() != 0L) {
      result = getDataById(dataRefParameter.construct(id));
    } else {
      result = getDataFromStrategy(dataRefParameter);
    }
    // @anchor:getData-after:start
    // @anchor:getData-after:end
    // anchor:custom-getData-after:start
    // anchor:custom-getData-after:end
    return result;
  }

  private CrudsResult<TripBookingData> getDataFromStrategy(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    Long id = dataRef.getId();
    CrudsResult<TripBookingData> result = getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    // @anchor:getData-strategy:start
    // @anchor:getData-strategy:end
    return getDataByName(dataRefParameter.construct(dataRef.getName()));
  }
  // anchor:findMethods:end

  // anchor:searchMethods:start
  /* searching */

  public <S extends TripBookingFindDetails> SearchResult<TripBookingData> findData(ParameterContext<SearchDetails<S>> searchParameter) {
    return tripBookingFinder.find(searchParameter);
  }

  public <S extends TripBookingFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<TripBookingData> dataResult = findData(searchParameter);

    if (dataResult.isError()) {
      return SearchResult.error(dataResult.getDiagnostics());
    }

    String projection = searchParameter.getValue().getProjection();
    IDataElementProjector<TripBookingData, T> projector = getElementProjector(projection);

    List<T> projected = project(searchParameter.getContext(), dataResult.getResults(), projector);
    return SearchResult.success(projected, dataResult.getTotalNumberOfItems());
  }

  /**
   * Internal method, do not use
   */
  private <T> List<T> project(Context context, List<TripBookingData> fromItems, IDataElementProjector<TripBookingData, T> projector) {
    return fromItems.stream()
        .map(context::withParameter)
        .map(projector::project)
        .collect(Collectors.toList());
  }

  /**
   * Internal method, do not use
   */
  @Deprecated
  private <T> List<T> project(UserContext userContext, List<TripBookingData> fromItems, IDataElementProjector<TripBookingData, T> projector) {
    return project(Context.from(userContext), fromItems, projector);
  }

  @Override
  public <P> SearchResult<P> project(ParameterContext<List<TripBookingData>> listParameter, String projection) {
    IDataElementProjector<TripBookingData, P> elementProjector = getElementProjector(projection);
    List<TripBookingData> list = listParameter.getValue();
    List<P> resultList = project(listParameter.getContext(), list, elementProjector);

    return SearchResult.success(resultList);
  }

  // anchor:searchMethods:end

  // anchor:projectors:start
  // anchor:projectors:end

  // anchor:link-getters:start
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getCustomer(ParameterContext<Long> idParameter) {
    try {
      return customerLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getDriver(ParameterContext<Long> idParameter) {
    try {
      return driverLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getFromLocation(ParameterContext<Long> idParameter) {
    try {
      return addressLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getToLocation(ParameterContext<Long> idParameter) {
    try {
      return addressLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getPayment(ParameterContext<Long> idParameter) {
    try {
      return paymentLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  public CrudsResult<CustomerDetails> getCustomerDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return customerLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<DriverDetails> getDriverDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return driverLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<AddressDetails> getFromLocationDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return addressLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<AddressDetails> getToLocationDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return addressLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<PaymentDetails> getPaymentDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return paymentLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  // anchor:link-getters:end

  // anchor:link-setters:start
  @Override
  public void setCustomer(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      tripBookingData.setCustomer(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = customerLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      tripBookingData.setCustomer(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Customer instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setDriver(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      tripBookingData.setDriver(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = driverLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      tripBookingData.setDriver(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Driver instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setFromLocation(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      tripBookingData.setFromLocation(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = addressLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      tripBookingData.setFromLocation(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Address instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setToLocation(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      tripBookingData.setToLocation(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = addressLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      tripBookingData.setToLocation(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Address instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setPayment(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      tripBookingData.setPayment(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = paymentLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      tripBookingData.setPayment(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Payment instance '" + dataRefParameter.getValue() + "'");
    }
  }

  // anchor:link-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-method-wrappers:start
  // anchor:calculation-method-wrappers:end

  // anchor:custom-projections:start
  // anchor:custom-projections:end

  // anchor:projectMethods:start
  private <P> IDataElementProjector<TripBookingData, P> getElementProjector(P projection) {
    @SuppressWarnings("unchecked")
    Class<P> projectionClass = (Class<P>) projection.getClass();
    return elementProjectorManager.getProjector(projectionClass);
  }

  @Override
  public String getDisplayName(ParameterContext<TripBookingData> dataParameter) {
    Option<String> optCustomDisplayName = getCustomDisplayName(dataParameter);
    if (optCustomDisplayName.isDefined()) {
      return optCustomDisplayName.getValue();
    }
    TripBookingData data = dataParameter.getValue();
    String displayName;

    displayName = data.getName();

    return displayName;
  }

  private Option<String> getCustomDisplayName(ParameterContext<TripBookingData> dataParameter) {
    Option<String> result = none();

    /* return some(string), to override default displayName */
    // anchor:custom-displayName:start
    // anchor:custom-displayName:end
    return result;
  }

  private String nameFromDataRef(CrudsResult<DataRef> result) {
    DataRef value = result.getValue();
    return value == null ? "-" : value.getName();
  }

  private String nameFromValue(Object value) {
    return value == null ? "-" : value.toString();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <T> IDataElementProjector<TripBookingData, T> getElementProjector(String projectionName) {
    IDataElementProjector<TripBookingData, T> projector = elementProjectorManager.getProjector(projectionName);
    if (projector == null) {
      throw new InvalidProjectionException(new ElementRef("onlineCabBookingComp", "TripBooking"), projectionName);
    }
    return projector;
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    ProjectionRef projectionRef = projectionRefParameter.getValue();

    DataRef dataRef = projectionRef.getDataRef();
    CrudsResult<TripBookingData> dataResult = getData(projectionRefParameter.construct(dataRef));
    TripBookingData tripBookingData;
    if (dataResult.isSuccess()) {
      tripBookingData = dataResult.getValue();
    } else {
      tripBookingData = new TripBookingData();
    }

    // anchor:custom-preProject:start
    // anchor:custom-preProject:end

    IDataElementProjector<TripBookingData, T> projector = getElementProjector(projectionRef.getProjection());
    T projection = projector.project(projectionRefParameter.construct(tripBookingData));

    if (tripBookingData != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Retrieved TripBookingCruds:" + projectionRef.getProjection() + " { id : " + tripBookingData.getId() + ", name: " + tripBookingData.getName() + " }"
        );
      }
    }

    return CrudsResult.success(projection);
  }

  public CrudsResult<TripBookingDetails> getDetails(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<TripBookingData> result = getData(dataRefParameter);
    if (result.isError()) {
      return result.convertError();
    }

    TripBookingData tripBookingData = result.getValue();
    TripBookingDetails tripBookingDetails = detailsProjector.project(dataRefParameter.construct(tripBookingData));

    return CrudsResult.success(tripBookingDetails);
  }

  public CrudsResult<List<TripBookingDetails>> getDetailsListFromData(ParameterContext<Collection<TripBookingData>> dataListParameter) {
    Collection<TripBookingData> dataList = dataListParameter.getValue();
    List<TripBookingDetails> list = new ArrayList<>(dataList.size());

    for (TripBookingData tripBookingData: dataList) {
      TripBookingDetails tripBookingDetails = detailsProjector.project(dataListParameter.construct(tripBookingData));
      list.add(tripBookingDetails);
    }

    return CrudsResult.success(list);
  }

  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return diagnosticHelper.createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
    if (dataRef.getId() != null && dataRef.getId() != 0L) {
      return CrudsResult.success(dataRef);
    }
    CrudsResult<TripBookingData> dataResult = getData(dataRefParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }
    TripBookingData data = dataResult.getValue();
    return getDataRefFromData(dataRefParameter.construct(data));
  }

  // anchor:projectMethods:end

  // @anchor:methods:start
  // anchor:compare-set-methods:start
  @Override
  public CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter) {
    StateUpdate stateUpdate = parameter.getValue();
    DataRef target = stateUpdate.getTarget();
    String expectedStatus = stateUpdate.getExpectedStatus();
    String targetStatus = stateUpdate.getTargetStatus();

    try {
      Query query = entityManager.createNamedQuery(TripBookingData.QUERY_COMPARE_SET_STATUS);
      query.setParameter("id", target.getId());
      query.setParameter("expectedStatus", expectedStatus);
      query.setParameter("targetStatus", targetStatus);

      int nbUpdatedEntities = query.executeUpdate();
      entityManager.flush();

      if (nbUpdatedEntities == 1) {
        return CrudsResult.success();
      } else {
        CrudsResult<TripBookingDetails> detailsResult = getDetails(parameter.construct(target));
        String actualStatus = detailsResult.isSuccess() ? detailsResult.getValue().getStatus() : "???";
        if (logger.isDebugEnabled()) {
          logger.debug(
              "compareAndSetStatus failed for TripBooking with id=" + target.getId() + ", expectedStatus='" + expectedStatus + "', actualStatus='" + actualStatus + "', targetStatus='" + targetStatus + "'"
          );
        }
        return CrudsResult.error();
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not update status", e
        );
      }
      return CrudsResult.error();
    }
  }
  // anchor:compare-set-methods:end
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

  /* utility methods */

  private DiagnosticHelper getDiagnosticHelper() {
    return diagnosticHelper;
  }

  private DiagnosticFactory getDiagnosticFactory() {
    return diagnosticFactory;
  }

  @Override
  public DataRef idToDataRef(Long id) {
    return new DataRef(
      id,
      "[no name]",
      "onlineCabBookingComp",
      "cabBookingCore",
      "TripBooking"
    );
  }
}
