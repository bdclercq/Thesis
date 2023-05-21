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
// @anchor:imports:end

// anchor:imports:start
import cabBookingCore.PaymentData;
import cabBookingCore.PaymentDetails;
import cabBookingCore.PaymentCrudsInternal;
import cabBookingCore.CustomerData;
import cabBookingCore.CustomerDetails;
import cabBookingCore.CustomerCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless
// @anchor:annotations:start
@Local({InvoiceCrudsLocal.class, InvoiceCrudsInternal.class})
// @anchor:annotations:end
public class InvoiceCruds /*@anchor:interfaces:start@*/implements InvoiceCrudsLocal, InvoiceCrudsInternal/*@anchor:interfaces:end@*/ {

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("onlineCabBookingComp", "Invoice");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  public static final String DISABLED_ERROR_MSG_KEY = "onlineCabBookingComp.invoice.alreadyDisabled";

  @PersistenceContext(unitName="OnlineCabBooking_onlineCabBookingComp")
  private EntityManager entityManager;

  @Resource
  private SessionContext sessionContext;

  private InvoiceFinderLocal invoiceFinder;

  /* linked bean variables */
  // anchor:link-variables:start
  @EJB
  private PaymentCrudsInternal paymentLocal;
  @EJB
  private CustomerCrudsInternal customerLocal;
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(InvoiceCruds.class);
  // @anchor:variables:end

  /* custom variables */
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private InvoiceDataRefProjector dataRefProjector;
  private InvoiceDetailsProjector detailsProjector;
  private InvoiceInfoProjector infoProjector;

  private DataElementProjectorManager<InvoiceData> elementProjectorManager;


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
    InvoiceCrudsInternal invoiceCrudsInternal = sessionContext.getBusinessObject(InvoiceCrudsInternal.class);

    detailsProjector = new InvoiceDetailsProjector(invoiceCrudsInternal);
    infoProjector = new InvoiceInfoProjector(invoiceCrudsInternal);
    dataRefProjector = new InvoiceDataRefProjector(invoiceCrudsInternal);

    elementProjectorManager = new DataElementProjectorManager<InvoiceData>();
    elementProjectorManager.addProjector(detailsProjector);
    elementProjectorManager.addProjector(infoProjector);
    elementProjectorManager.addProjector(dataRefProjector);

    // anchor:additional-projectors:start
    // anchor:additional-projectors:end

    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  private void initFinder() {
    invoiceFinder = new InvoiceFinderBean(entityManager);
  }

  // anchor:create:start
  public CrudsResult<DataRef> create(ParameterContext<InvoiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    InvoiceDetails details = detailsParameter.getValue();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    InvoiceData invoiceData = new InvoiceData();
    try {

      // @anchor:create-beforeProjection:start
      // @anchor:create-beforeProjection:end

      // anchor:custom-create-beforeProjection:start
      // anchor:custom-create-beforeProjection:end

      detailsProjector.toData(invoiceData, detailsParameter);

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
      entityManager.persist(invoiceData);

      // @anchor:implicitNameFieldOnly-afterPersist:start
      if (identity == null) {
        identity = "I-" + invoiceData.getId().toString();
      }
      invoiceData.setName(identity);
      entityManager.flush();
      // @anchor:implicitNameFieldOnly-afterPersist:end

      if (invoiceData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Created InvoiceCruds with { id : " + invoiceData.getId() + ", name: " + invoiceData.getName() + " }"
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

    CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(invoiceData));

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // anchor:create:end

  // anchor:modify:start
  public CrudsResult<DataRef> modify(ParameterContext<InvoiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    InvoiceDetails invoiceDetails = detailsParameter.getValue();
    DataRef dataRef = invoiceDetails.getDataRef();

    // @anchor:preModify:start
    // @anchor:preModify:end
    // anchor:custom-preModify:start
    // anchor:custom-preModify:end
    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    try {
      CrudsResult<InvoiceData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      InvoiceData invoiceData = dataResult.getValue();

      // @anchor:modify-beforeProjection:start
      // @anchor:modify-beforeProjection:end
      // anchor:custom-modify-beforeProjection:start
      // anchor:custom-modify-beforeProjection:end

      detailsProjector.toData(invoiceData, detailsParameter);

      // @anchor:modify-afterProjection:start
      // @anchor:modify-afterProjection:end
      // anchor:custom-modify-afterProjection:start
      // anchor:custom-modify-afterProjection:end

      entityManager.flush();
      CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(invoiceData));

      if (invoiceData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Modified InvoiceCruds with { id : " + invoiceData.getId() + ", name: " + invoiceData.getName() + " }"
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
            "InvoiceCruds.modify() failed with ID = " + dataRef.getId(), e
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

    if (projection instanceof InvoiceDetails) {
      InvoiceDetails details = (InvoiceDetails) projection;
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
      CrudsResult<InvoiceData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      InvoiceData invoiceData = dataResult.getValue();

      // anchor:custom-delete:start
      // anchor:custom-delete:end
      // @anchor:delete:start
      entityManager.remove(invoiceData);
      // @anchor:delete:end

      // @anchor:postDelete:start
      // @anchor:postDelete:end
      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      if (invoiceData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Deleted InvoiceCruds with { id : " + invoiceData.getId() + ", name: " + invoiceData.getName() + " }"
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
  public CrudsResult<DataRef> getDataRefFromData(ParameterContext<InvoiceData> dataParameter) {
    DataRef dataRef = dataRefProjector.project(dataParameter);
    return CrudsResult.success(dataRef);
  }

  public CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();
    if (id == null || id == 0L) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    try {
      InvoiceData invoiceData = entityManager.find(InvoiceData.class, id);
      // @anchor:getDataRefFromId-afterFetch:start
      // @anchor:getDataRefFromId-afterFetch:end
      // anchor:custom-getDataRefFromId-afterFetch:start
      // anchor:custom-getDataRefFromId-afterFetch:end
      return getDataRefFromData(idParameter.construct(invoiceData));
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

  private CrudsResult<InvoiceData> getDataById(ParameterContext<Long> idParameter) {
    InvoiceData data = entityManager.find(InvoiceData.class, idParameter.getValue());
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

    CrudsResult<InvoiceData> dataResult = getDataByName(nameParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }

    // @anchor:getDataRefFromName-afterFetch:start
    // @anchor:getDataRefFromName-afterFetch:end
    // anchor:custom-getDataRefFromName-afterFetch:start
    // anchor:custom-getDataRefFromName-afterFetch:end
    return getDataRefFromData(nameParameter.construct(dataResult.getValue()));
  }

  private CrudsResult<InvoiceData> getDataByName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    try {
      InvoiceFindByNameEqDetails findByNameEqDetails = new InvoiceFindByNameEqDetails();
      findByNameEqDetails.setName(name);

      SearchDetails<InvoiceFindByNameEqDetails> searchDetails =
        new SearchDetails<>(findByNameEqDetails);

      ParameterContext<SearchDetails<InvoiceFindByNameEqDetails>> searchParameter =
        nameParameter.construct(searchDetails);

      SearchResult<InvoiceData> searchResult = findData(searchParameter);

      if (searchResult.isError()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      List<InvoiceData> list = searchResult.getResults();
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

  public CrudsResult<InvoiceData> getData(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    Long id = dataRef.getId();
    CrudsResult<InvoiceData> result;
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

  private CrudsResult<InvoiceData> getDataFromStrategy(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    Long id = dataRef.getId();
    CrudsResult<InvoiceData> result = getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    // @anchor:getData-strategy:start
    // @anchor:getData-strategy:end
    return getDataByName(dataRefParameter.construct(dataRef.getName()));
  }
  // anchor:findMethods:end

  // anchor:searchMethods:start
  /* searching */

  public <S extends InvoiceFindDetails> SearchResult<InvoiceData> findData(ParameterContext<SearchDetails<S>> searchParameter) {
    return invoiceFinder.find(searchParameter);
  }

  public <S extends InvoiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<InvoiceData> dataResult = findData(searchParameter);

    if (dataResult.isError()) {
      return SearchResult.error(dataResult.getDiagnostics());
    }

    String projection = searchParameter.getValue().getProjection();
    IDataElementProjector<InvoiceData, T> projector = getElementProjector(projection);

    List<T> projected = project(searchParameter.getContext(), dataResult.getResults(), projector);
    return SearchResult.success(projected, dataResult.getTotalNumberOfItems());
  }

  /**
   * Internal method, do not use
   */
  private <T> List<T> project(Context context, List<InvoiceData> fromItems, IDataElementProjector<InvoiceData, T> projector) {
    return fromItems.stream()
        .map(context::withParameter)
        .map(projector::project)
        .collect(Collectors.toList());
  }

  /**
   * Internal method, do not use
   */
  @Deprecated
  private <T> List<T> project(UserContext userContext, List<InvoiceData> fromItems, IDataElementProjector<InvoiceData, T> projector) {
    return project(Context.from(userContext), fromItems, projector);
  }

  @Override
  public <P> SearchResult<P> project(ParameterContext<List<InvoiceData>> listParameter, String projection) {
    IDataElementProjector<InvoiceData, P> elementProjector = getElementProjector(projection);
    List<InvoiceData> list = listParameter.getValue();
    List<P> resultList = project(listParameter.getContext(), list, elementProjector);

    return SearchResult.success(resultList);
  }

  // anchor:searchMethods:end

  // anchor:projectors:start
  // anchor:projectors:end

  // anchor:link-getters:start
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
  // anchor:link-getters:end

  // anchor:link-setters:start
  @Override
  public void setPayment(InvoiceData invoiceData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      invoiceData.setPayment(0L);
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
      invoiceData.setPayment(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Payment instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setCustomer(InvoiceData invoiceData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      invoiceData.setCustomer(0L);
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
      invoiceData.setCustomer(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Customer instance '" + dataRefParameter.getValue() + "'");
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
  private <P> IDataElementProjector<InvoiceData, P> getElementProjector(P projection) {
    @SuppressWarnings("unchecked")
    Class<P> projectionClass = (Class<P>) projection.getClass();
    return elementProjectorManager.getProjector(projectionClass);
  }

  @Override
  public String getDisplayName(ParameterContext<InvoiceData> dataParameter) {
    Option<String> optCustomDisplayName = getCustomDisplayName(dataParameter);
    if (optCustomDisplayName.isDefined()) {
      return optCustomDisplayName.getValue();
    }
    InvoiceData data = dataParameter.getValue();
    String displayName;

    displayName = data.getName();

    return displayName;
  }

  private Option<String> getCustomDisplayName(ParameterContext<InvoiceData> dataParameter) {
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
  private <T> IDataElementProjector<InvoiceData, T> getElementProjector(String projectionName) {
    IDataElementProjector<InvoiceData, T> projector = elementProjectorManager.getProjector(projectionName);
    if (projector == null) {
      throw new InvalidProjectionException(new ElementRef("onlineCabBookingComp", "Invoice"), projectionName);
    }
    return projector;
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    ProjectionRef projectionRef = projectionRefParameter.getValue();

    DataRef dataRef = projectionRef.getDataRef();
    CrudsResult<InvoiceData> dataResult = getData(projectionRefParameter.construct(dataRef));
    InvoiceData invoiceData;
    if (dataResult.isSuccess()) {
      invoiceData = dataResult.getValue();
    } else {
      invoiceData = new InvoiceData();
    }

    // anchor:custom-preProject:start
    // anchor:custom-preProject:end

    IDataElementProjector<InvoiceData, T> projector = getElementProjector(projectionRef.getProjection());
    T projection = projector.project(projectionRefParameter.construct(invoiceData));

    if (invoiceData != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Retrieved InvoiceCruds:" + projectionRef.getProjection() + " { id : " + invoiceData.getId() + ", name: " + invoiceData.getName() + " }"
        );
      }
    }

    return CrudsResult.success(projection);
  }

  public CrudsResult<InvoiceDetails> getDetails(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<InvoiceData> result = getData(dataRefParameter);
    if (result.isError()) {
      return result.convertError();
    }

    InvoiceData invoiceData = result.getValue();
    InvoiceDetails invoiceDetails = detailsProjector.project(dataRefParameter.construct(invoiceData));

    return CrudsResult.success(invoiceDetails);
  }

  public CrudsResult<List<InvoiceDetails>> getDetailsListFromData(ParameterContext<Collection<InvoiceData>> dataListParameter) {
    Collection<InvoiceData> dataList = dataListParameter.getValue();
    List<InvoiceDetails> list = new ArrayList<>(dataList.size());

    for (InvoiceData invoiceData: dataList) {
      InvoiceDetails invoiceDetails = detailsProjector.project(dataListParameter.construct(invoiceData));
      list.add(invoiceDetails);
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
    CrudsResult<InvoiceData> dataResult = getData(dataRefParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }
    InvoiceData data = dataResult.getValue();
    return getDataRefFromData(dataRefParameter.construct(data));
  }

  // anchor:projectMethods:end

  // @anchor:methods:start
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
      "Invoice"
    );
  }
}
