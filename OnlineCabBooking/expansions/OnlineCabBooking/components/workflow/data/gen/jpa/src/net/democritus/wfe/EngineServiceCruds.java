package net.democritus.wfe;

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
import java.util.Date;
import net.democritus.state.StateUpdate;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.workflow.WorkflowData;
import net.democritus.workflow.WorkflowDetails;
import net.democritus.workflow.WorkflowCrudsInternal;
import net.democritus.wfe.TimeWindowGroupData;
import net.democritus.wfe.TimeWindowGroupDetails;
import net.democritus.wfe.TimeWindowGroupCrudsInternal;
import net.democritus.wfe.EngineNodeServiceData;
import net.democritus.wfe.EngineNodeServiceDetails;
import net.democritus.wfe.EngineNodeServiceCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import net.democritus.valuetype.basic.DateTime;
import net.democritus.valuetype.basic.DateTimeConverter;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless
// @anchor:annotations:start
@Local({EngineServiceCrudsLocal.class, EngineServiceCrudsInternal.class})
// @anchor:annotations:end
public class EngineServiceCruds /*@anchor:interfaces:start@*/implements EngineServiceCrudsLocal, EngineServiceCrudsInternal/*@anchor:interfaces:end@*/ {

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("workflow", "EngineService");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  public static final String DISABLED_ERROR_MSG_KEY = "workflow.engineService.alreadyDisabled";

  @PersistenceContext(unitName="OnlineCabBooking_workflow")
  private EntityManager entityManager;

  @Resource
  private SessionContext sessionContext;

  private EngineServiceFinderLocal engineServiceFinder;

  /* linked bean variables */
  // anchor:link-variables:start
  @EJB
  private WorkflowCrudsInternal workflowLocal;
  @EJB
  private TimeWindowGroupCrudsInternal timeWindowGroupLocal;
  @EJB
  private EngineNodeServiceCrudsInternal engineNodeServiceLocal;
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineServiceCruds.class);
  // @anchor:variables:end

  /* custom variables */
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private EngineServiceDataRefProjector dataRefProjector;
  private EngineServiceDetailsProjector detailsProjector;
  private EngineServiceInfoProjector infoProjector;

  private DataElementProjectorManager<EngineServiceData> elementProjectorManager;


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
    EngineServiceCrudsInternal engineServiceCrudsInternal = sessionContext.getBusinessObject(EngineServiceCrudsInternal.class);

    detailsProjector = new EngineServiceDetailsProjector(engineServiceCrudsInternal);
    infoProjector = new EngineServiceInfoProjector(engineServiceCrudsInternal);
    dataRefProjector = new EngineServiceDataRefProjector(engineServiceCrudsInternal);

    elementProjectorManager = new DataElementProjectorManager<EngineServiceData>();
    elementProjectorManager.addProjector(detailsProjector);
    elementProjectorManager.addProjector(infoProjector);
    elementProjectorManager.addProjector(dataRefProjector);

    // anchor:additional-projectors:start
    elementProjectorManager.addProjector(new EngineServiceRunningStateProjector(engineServiceCrudsInternal));
    elementProjectorManager.addProjector(new EngineServiceDetailsWithoutRefsProjector(engineServiceCrudsInternal));
    // anchor:additional-projectors:end

    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  private void initFinder() {
    engineServiceFinder = new EngineServiceFinderBean(entityManager);
  }

  // anchor:create:start
  public CrudsResult<DataRef> create(ParameterContext<EngineServiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    EngineServiceDetails details = detailsParameter.getValue();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    EngineServiceData engineServiceData = new EngineServiceData();
    try {

      // @anchor:create-beforeProjection:start
      // @anchor:create-beforeProjection:end

      // anchor:custom-create-beforeProjection:start
      // anchor:custom-create-beforeProjection:end

      detailsProjector.toData(engineServiceData, detailsParameter);

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
    // @anchor:implicitNameFieldOnly-beforePersist:end

    try {
      entityManager.persist(engineServiceData);

      // @anchor:implicitNameFieldOnly-afterPersist:start
      // @anchor:implicitNameFieldOnly-afterPersist:end

      if (engineServiceData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Created EngineServiceCruds with { id : " + engineServiceData.getId() + ", name: " + engineServiceData.getName() + " }"
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

    CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(engineServiceData));

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // anchor:create:end

  // anchor:modify:start
  public CrudsResult<DataRef> modify(ParameterContext<EngineServiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    EngineServiceDetails engineServiceDetails = detailsParameter.getValue();
    DataRef dataRef = engineServiceDetails.getDataRef();

    // @anchor:preModify:start
    // @anchor:preModify:end
    // anchor:custom-preModify:start
    // anchor:custom-preModify:end
    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    try {
      CrudsResult<EngineServiceData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      EngineServiceData engineServiceData = dataResult.getValue();

      // @anchor:modify-beforeProjection:start
      // @anchor:modify-beforeProjection:end
      // anchor:custom-modify-beforeProjection:start
      // anchor:custom-modify-beforeProjection:end

      detailsProjector.toData(engineServiceData, detailsParameter);

      // @anchor:modify-afterProjection:start
      // @anchor:modify-afterProjection:end
      // anchor:custom-modify-afterProjection:start
      // anchor:custom-modify-afterProjection:end

      entityManager.flush();
      CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(engineServiceData));

      if (engineServiceData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Modified EngineServiceCruds with { id : " + engineServiceData.getId() + ", name: " + engineServiceData.getName() + " }"
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
            "EngineServiceCruds.modify() failed with ID = " + dataRef.getId(), e
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

    if (projection instanceof EngineServiceDetails) {
      EngineServiceDetails details = (EngineServiceDetails) projection;
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
      CrudsResult<EngineServiceData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      EngineServiceData engineServiceData = dataResult.getValue();

      // anchor:custom-delete:start
      // anchor:custom-delete:end
      // @anchor:delete:start
      entityManager.remove(engineServiceData);
      // @anchor:delete:end

      // @anchor:postDelete:start
      // @anchor:postDelete:end
      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      if (engineServiceData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Deleted EngineServiceCruds with { id : " + engineServiceData.getId() + ", name: " + engineServiceData.getName() + " }"
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
  public CrudsResult<DataRef> getDataRefFromData(ParameterContext<EngineServiceData> dataParameter) {
    DataRef dataRef = dataRefProjector.project(dataParameter);
    return CrudsResult.success(dataRef);
  }

  public CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();
    if (id == null || id == 0L) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    try {
      EngineServiceData engineServiceData = entityManager.find(EngineServiceData.class, id);
      // @anchor:getDataRefFromId-afterFetch:start
      // @anchor:getDataRefFromId-afterFetch:end
      // anchor:custom-getDataRefFromId-afterFetch:start
      // anchor:custom-getDataRefFromId-afterFetch:end
      return getDataRefFromData(idParameter.construct(engineServiceData));
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

  private CrudsResult<EngineServiceData> getDataById(ParameterContext<Long> idParameter) {
    EngineServiceData data = entityManager.find(EngineServiceData.class, idParameter.getValue());
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

    CrudsResult<EngineServiceData> dataResult = getDataByName(nameParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }

    // @anchor:getDataRefFromName-afterFetch:start
    // @anchor:getDataRefFromName-afterFetch:end
    // anchor:custom-getDataRefFromName-afterFetch:start
    // anchor:custom-getDataRefFromName-afterFetch:end
    return getDataRefFromData(nameParameter.construct(dataResult.getValue()));
  }

  private CrudsResult<EngineServiceData> getDataByName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    try {
      EngineServiceFindByNameEqDetails findByNameEqDetails = new EngineServiceFindByNameEqDetails();
      findByNameEqDetails.setName(name);

      SearchDetails<EngineServiceFindByNameEqDetails> searchDetails =
        new SearchDetails<>(findByNameEqDetails);

      ParameterContext<SearchDetails<EngineServiceFindByNameEqDetails>> searchParameter =
        nameParameter.construct(searchDetails);

      SearchResult<EngineServiceData> searchResult = findData(searchParameter);

      if (searchResult.isError()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      List<EngineServiceData> list = searchResult.getResults();
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

  public CrudsResult<EngineServiceData> getData(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    Long id = dataRef.getId();
    CrudsResult<EngineServiceData> result;
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

  private CrudsResult<EngineServiceData> getDataFromStrategy(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    Long id = dataRef.getId();
    CrudsResult<EngineServiceData> result = getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    // @anchor:getData-strategy:start
    // @anchor:getData-strategy:end
    return getDataByName(dataRefParameter.construct(dataRef.getName()));
  }
  // anchor:findMethods:end

  // anchor:searchMethods:start
  /* searching */

  public <S extends EngineServiceFindDetails> SearchResult<EngineServiceData> findData(ParameterContext<SearchDetails<S>> searchParameter) {
    return engineServiceFinder.find(searchParameter);
  }

  public <S extends EngineServiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<EngineServiceData> dataResult = findData(searchParameter);

    if (dataResult.isError()) {
      return SearchResult.error(dataResult.getDiagnostics());
    }

    String projection = searchParameter.getValue().getProjection();
    IDataElementProjector<EngineServiceData, T> projector = getElementProjector(projection);

    List<T> projected = project(searchParameter.getContext(), dataResult.getResults(), projector);
    return SearchResult.success(projected, dataResult.getTotalNumberOfItems());
  }

  /**
   * Internal method, do not use
   */
  private <T> List<T> project(Context context, List<EngineServiceData> fromItems, IDataElementProjector<EngineServiceData, T> projector) {
    return fromItems.stream()
        .map(context::withParameter)
        .map(projector::project)
        .collect(Collectors.toList());
  }

  /**
   * Internal method, do not use
   */
  @Deprecated
  private <T> List<T> project(UserContext userContext, List<EngineServiceData> fromItems, IDataElementProjector<EngineServiceData, T> projector) {
    return project(Context.from(userContext), fromItems, projector);
  }

  @Override
  public <P> SearchResult<P> project(ParameterContext<List<EngineServiceData>> listParameter, String projection) {
    IDataElementProjector<EngineServiceData, P> elementProjector = getElementProjector(projection);
    List<EngineServiceData> list = listParameter.getValue();
    List<P> resultList = project(listParameter.getContext(), list, elementProjector);

    return SearchResult.success(resultList);
  }

  // anchor:searchMethods:end

  // anchor:projectors:start
  // anchor:projectors:end

  // anchor:link-getters:start
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getWorkflow(ParameterContext<Long> idParameter) {
    try {
      return workflowLocal.getDataRefFromId(idParameter);
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
  public CrudsResult<DataRef> getTimeWindowGroup(ParameterContext<Long> idParameter) {
    try {
      return timeWindowGroupLocal.getDataRefFromId(idParameter);
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
  public CrudsResult<List<DataRef>> getEngineNodeServices(ParameterContext<EngineServiceData> engineServiceDataParameter) {
    EngineServiceData engineServiceData = engineServiceDataParameter.getValue();
    try {
      List<DataRef> dataRefVec = new ArrayList<DataRef>();
      Collection<EngineNodeServiceData> engineNodeServiceDatas = engineServiceData.getEngineNodeServices();

      if (engineNodeServiceDatas != null) {
        for (EngineNodeServiceData data: engineNodeServiceDatas) {
          ParameterContext<EngineNodeServiceData> dataParameter = engineServiceDataParameter.construct(data);
          CrudsResult<DataRef> result = engineNodeServiceLocal.getDataRefFromData(dataParameter);
          if (result.isSuccess()) {
            dataRefVec.add(result.getValue());
          } else {
            return result.convertError();
          }
        }
      }

      return CrudsResult.success(dataRefVec);

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link vector", e
        );
      }
      return getDiagnosticHelper().createCrudsError("cannot retrieve links in getEngineNodeServices");
    }
  }

  public CrudsResult<WorkflowDetails> getWorkflowDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return workflowLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<TimeWindowGroupDetails> getTimeWindowGroupDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return timeWindowGroupLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<List<EngineNodeServiceDetails>> getEngineNodeServicesDetails(ParameterContext<EngineServiceData> dataParameter) {
    EngineServiceData engineServiceData = dataParameter.getValue();
    List<EngineNodeServiceDetails> detailsVec = new ArrayList<EngineNodeServiceDetails>();

    try {
      CrudsResult<List<EngineNodeServiceDetails>> detailsListResult =
        engineNodeServiceLocal.getDetailsListFromData(dataParameter.construct(engineServiceData.getEngineNodeServices()));
      detailsVec.addAll(detailsListResult.getValue());

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link vector", e
        );
      }
    }
    return CrudsResult.success(detailsVec);
  }

  // anchor:link-getters:end

  // anchor:link-setters:start
  @Override
  public void setWorkflow(EngineServiceData engineServiceData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      engineServiceData.setWorkflow(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = workflowLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      engineServiceData.setWorkflow(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Workflow instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setTimeWindowGroup(EngineServiceData engineServiceData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      engineServiceData.setTimeWindowGroup(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = timeWindowGroupLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      engineServiceData.setTimeWindowGroup(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find TimeWindowGroup instance '" + dataRefParameter.getValue() + "'");
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
  private <P> IDataElementProjector<EngineServiceData, P> getElementProjector(P projection) {
    @SuppressWarnings("unchecked")
    Class<P> projectionClass = (Class<P>) projection.getClass();
    return elementProjectorManager.getProjector(projectionClass);
  }

  @Override
  public String getDisplayName(ParameterContext<EngineServiceData> dataParameter) {
    Option<String> optCustomDisplayName = getCustomDisplayName(dataParameter);
    if (optCustomDisplayName.isDefined()) {
      return optCustomDisplayName.getValue();
    }
    EngineServiceData data = dataParameter.getValue();
    String displayName;

    displayName = data.getName();

    return displayName;
  }

  private Option<String> getCustomDisplayName(ParameterContext<EngineServiceData> dataParameter) {
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
  private <T> IDataElementProjector<EngineServiceData, T> getElementProjector(String projectionName) {
    IDataElementProjector<EngineServiceData, T> projector = elementProjectorManager.getProjector(projectionName);
    if (projector == null) {
      throw new InvalidProjectionException(new ElementRef("workflow", "EngineService"), projectionName);
    }
    return projector;
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    ProjectionRef projectionRef = projectionRefParameter.getValue();

    DataRef dataRef = projectionRef.getDataRef();
    CrudsResult<EngineServiceData> dataResult = getData(projectionRefParameter.construct(dataRef));
    EngineServiceData engineServiceData;
    if (dataResult.isSuccess()) {
      engineServiceData = dataResult.getValue();
    } else {
      engineServiceData = new EngineServiceData();
    }

    // anchor:custom-preProject:start
    // anchor:custom-preProject:end

    IDataElementProjector<EngineServiceData, T> projector = getElementProjector(projectionRef.getProjection());
    T projection = projector.project(projectionRefParameter.construct(engineServiceData));

    if (engineServiceData != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Retrieved EngineServiceCruds:" + projectionRef.getProjection() + " { id : " + engineServiceData.getId() + ", name: " + engineServiceData.getName() + " }"
        );
      }
    }

    return CrudsResult.success(projection);
  }

  public CrudsResult<EngineServiceDetails> getDetails(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<EngineServiceData> result = getData(dataRefParameter);
    if (result.isError()) {
      return result.convertError();
    }

    EngineServiceData engineServiceData = result.getValue();
    EngineServiceDetails engineServiceDetails = detailsProjector.project(dataRefParameter.construct(engineServiceData));

    return CrudsResult.success(engineServiceDetails);
  }

  public CrudsResult<List<EngineServiceDetails>> getDetailsListFromData(ParameterContext<Collection<EngineServiceData>> dataListParameter) {
    Collection<EngineServiceData> dataList = dataListParameter.getValue();
    List<EngineServiceDetails> list = new ArrayList<>(dataList.size());

    for (EngineServiceData engineServiceData: dataList) {
      EngineServiceDetails engineServiceDetails = detailsProjector.project(dataListParameter.construct(engineServiceData));
      list.add(engineServiceDetails);
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
    CrudsResult<EngineServiceData> dataResult = getData(dataRefParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }
    EngineServiceData data = dataResult.getValue();
    return getDataRefFromData(dataRefParameter.construct(data));
  }

  // anchor:projectMethods:end

  // @anchor:methods:start

  /*========== Exposed Field Methods ==========*/

  // anchor:exposed-fields:start
  public CrudsResult<String> getBusy(ParameterContext<DataRef> idParameter) {
    CrudsResult<EngineServiceData> dataResult = this.getData(idParameter.construct(idParameter.getDataRef()));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }
    EngineServiceData engineServiceData = dataResult.getValue();
    return CrudsResult.success(engineServiceData.getBusy());
  }

  public CrudsResult<Void> setBusy(ParameterContext<String> idParameter) {
    DataRef dataRef = idParameter.getDataRef();
    CrudsResult<EngineServiceData> dataResult = this.getData(idParameter.construct(dataRef));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }

    EngineServiceData engineServiceData = dataResult.getValue();
    engineServiceData.setBusy(idParameter.getValue());

    entityManager.flush();

    return CrudsResult.success();
  }

  public CrudsResult<Date> getLastRunAt(ParameterContext<DataRef> idParameter) {
    CrudsResult<EngineServiceData> dataResult = this.getData(idParameter.construct(idParameter.getDataRef()));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }
    EngineServiceData engineServiceData = dataResult.getValue();
    return CrudsResult.success(engineServiceData.getLastRunAt());
  }

  public CrudsResult<Void> setLastRunAt(ParameterContext<Date> idParameter) {
    DataRef dataRef = idParameter.getDataRef();
    CrudsResult<EngineServiceData> dataResult = this.getData(idParameter.construct(dataRef));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }

    EngineServiceData engineServiceData = dataResult.getValue();
    engineServiceData.setLastRunAt(idParameter.getValue());

    entityManager.flush();

    return CrudsResult.success();
  }

  // anchor:exposed-fields:end
  // anchor:compare-set-methods:start
  @Override
  public CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter) {
    StateUpdate stateUpdate = parameter.getValue();
    DataRef target = stateUpdate.getTarget();
    String expectedStatus = stateUpdate.getExpectedStatus();
    String targetStatus = stateUpdate.getTargetStatus();

    try {
      Query query = entityManager.createNamedQuery(EngineServiceData.QUERY_COMPARE_SET_STATUS);
      query.setParameter("id", target.getId());
      query.setParameter("expectedStatus", expectedStatus);
      query.setParameter("targetStatus", targetStatus);

      int nbUpdatedEntities = query.executeUpdate();
      entityManager.flush();

      if (nbUpdatedEntities == 1) {
        return CrudsResult.success();
      } else {
        CrudsResult<EngineServiceDetails> detailsResult = getDetails(parameter.construct(target));
        String actualStatus = detailsResult.isSuccess() ? detailsResult.getValue().getStatus() : "???";
        if (logger.isDebugEnabled()) {
          logger.debug(
              "compareAndSetStatus failed for EngineService with id=" + target.getId() + ", expectedStatus='" + expectedStatus + "', actualStatus='" + actualStatus + "', targetStatus='" + targetStatus + "'"
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
  public CrudsResult<Void> updateLastRunAt(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<DataRef> dataRefCrudsResult = resolveDataRef(dataRefParameter);
    if (dataRefCrudsResult.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error("Could not update lastRunAt, engineServiceId is not defined");
      }
      return dataRefCrudsResult.convertError();
    }

    try {
      Long engineId = dataRefCrudsResult.getValue().getId();
      Query query = entityManager.createNamedQuery(EngineServiceData.QUERY_UPDATE_LASTRUNAT);
      query.setParameter("id", engineId);
      int nbUpdatedEntities = query.executeUpdate();
      entityManager.flush();

      if (nbUpdatedEntities == 1) {
        return CrudsResult.success();
      } else if (nbUpdatedEntities == 0) {
        if (logger.isErrorEnabled()) {
          logger.error("Could not update lastRunAt, no engineService exists with id " + engineId);
        }
        return CrudsResult.error();
      } else {
        if (logger.isWarnEnabled()) {
          logger.warn("Multiple engineServices updated lastRunAt with id " + engineId);
        }
        return CrudsResult.error();
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not update lastRunAt", e
        );
      }
      return CrudsResult.error();
    }
  }
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
      "workflow",
      "net.democritus.wfe",
      "EngineService"
    );
  }
}
