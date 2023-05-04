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
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
import javax.persistence.Query;
// anchor:custom-imports:end

@Stateless
// @anchor:annotations:start
@Local({EngineNodeCrudsLocal.class, EngineNodeCrudsInternal.class})
// @anchor:annotations:end
public class EngineNodeCruds /*@anchor:interfaces:start@*/implements EngineNodeCrudsLocal, EngineNodeCrudsInternal/*@anchor:interfaces:end@*/ {

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("workflow", "EngineNode");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  public static final String DISABLED_ERROR_MSG_KEY = "workflow.engineNode.alreadyDisabled";

  @PersistenceContext(unitName="OnlineCabBooking_workflow")
  private EntityManager entityManager;

  @Resource
  private SessionContext sessionContext;

  private EngineNodeFinderLocal engineNodeFinder;

  /* linked bean variables */
  // anchor:link-variables:start
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeCruds.class);
  // @anchor:variables:end

  /* custom variables */
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private EngineNodeDataRefProjector dataRefProjector;
  private EngineNodeDetailsProjector detailsProjector;
  private EngineNodeInfoProjector infoProjector;

  private DataElementProjectorManager<EngineNodeData> elementProjectorManager;


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
    EngineNodeCrudsInternal engineNodeCrudsInternal = sessionContext.getBusinessObject(EngineNodeCrudsInternal.class);

    detailsProjector = new EngineNodeDetailsProjector(engineNodeCrudsInternal);
    infoProjector = new EngineNodeInfoProjector(engineNodeCrudsInternal);
    dataRefProjector = new EngineNodeDataRefProjector(engineNodeCrudsInternal);

    elementProjectorManager = new DataElementProjectorManager<EngineNodeData>();
    elementProjectorManager.addProjector(detailsProjector);
    elementProjectorManager.addProjector(infoProjector);
    elementProjectorManager.addProjector(dataRefProjector);

    // anchor:additional-projectors:start
    // anchor:additional-projectors:end

    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  private void initFinder() {
    engineNodeFinder = new EngineNodeFinderBean(entityManager);
  }

  // anchor:create:start
  public CrudsResult<DataRef> create(ParameterContext<EngineNodeDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    EngineNodeDetails details = detailsParameter.getValue();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    EngineNodeData engineNodeData = new EngineNodeData();
    try {

      // @anchor:create-beforeProjection:start
      // @anchor:create-beforeProjection:end

      // anchor:custom-create-beforeProjection:start
      // anchor:custom-create-beforeProjection:end

      detailsProjector.toData(engineNodeData, detailsParameter);

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
      entityManager.persist(engineNodeData);

      // @anchor:implicitNameFieldOnly-afterPersist:start
      // @anchor:implicitNameFieldOnly-afterPersist:end

      if (engineNodeData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Created EngineNodeCruds with { id : " + engineNodeData.getId() + ", name: " + engineNodeData.getName() + " }"
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

    CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(engineNodeData));

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // anchor:create:end

  // anchor:modify:start
  public CrudsResult<DataRef> modify(ParameterContext<EngineNodeDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    EngineNodeDetails engineNodeDetails = detailsParameter.getValue();
    DataRef dataRef = engineNodeDetails.getDataRef();

    // @anchor:preModify:start
    // @anchor:preModify:end
    // anchor:custom-preModify:start
    // anchor:custom-preModify:end
    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    try {
      CrudsResult<EngineNodeData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      EngineNodeData engineNodeData = dataResult.getValue();

      // @anchor:modify-beforeProjection:start
      // @anchor:modify-beforeProjection:end
      // anchor:custom-modify-beforeProjection:start
      // anchor:custom-modify-beforeProjection:end

      detailsProjector.toData(engineNodeData, detailsParameter);

      // @anchor:modify-afterProjection:start
      // @anchor:modify-afterProjection:end
      // anchor:custom-modify-afterProjection:start
      // anchor:custom-modify-afterProjection:end

      entityManager.flush();
      CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(engineNodeData));

      if (engineNodeData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Modified EngineNodeCruds with { id : " + engineNodeData.getId() + ", name: " + engineNodeData.getName() + " }"
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
            "EngineNodeCruds.modify() failed with ID = " + dataRef.getId(), e
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

    if (projection instanceof EngineNodeDetails) {
      EngineNodeDetails details = (EngineNodeDetails) projection;
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
      CrudsResult<EngineNodeData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      EngineNodeData engineNodeData = dataResult.getValue();

      // anchor:custom-delete:start
      // anchor:custom-delete:end
      // @anchor:delete:start
      entityManager.remove(engineNodeData);
      // @anchor:delete:end

      // @anchor:postDelete:start
      // @anchor:postDelete:end
      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      if (engineNodeData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Deleted EngineNodeCruds with { id : " + engineNodeData.getId() + ", name: " + engineNodeData.getName() + " }"
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
  public CrudsResult<DataRef> getDataRefFromData(ParameterContext<EngineNodeData> dataParameter) {
    DataRef dataRef = dataRefProjector.project(dataParameter);
    return CrudsResult.success(dataRef);
  }

  public CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();
    if (id == null || id == 0L) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    try {
      EngineNodeData engineNodeData = entityManager.find(EngineNodeData.class, id);
      // @anchor:getDataRefFromId-afterFetch:start
      // @anchor:getDataRefFromId-afterFetch:end
      // anchor:custom-getDataRefFromId-afterFetch:start
      // anchor:custom-getDataRefFromId-afterFetch:end
      return getDataRefFromData(idParameter.construct(engineNodeData));
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

  private CrudsResult<EngineNodeData> getDataById(ParameterContext<Long> idParameter) {
    EngineNodeData data = entityManager.find(EngineNodeData.class, idParameter.getValue());
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

    CrudsResult<EngineNodeData> dataResult = getDataByName(nameParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }

    // @anchor:getDataRefFromName-afterFetch:start
    // @anchor:getDataRefFromName-afterFetch:end
    // anchor:custom-getDataRefFromName-afterFetch:start
    // anchor:custom-getDataRefFromName-afterFetch:end
    return getDataRefFromData(nameParameter.construct(dataResult.getValue()));
  }

  private CrudsResult<EngineNodeData> getDataByName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    try {
      EngineNodeFindByNameEqDetails findByNameEqDetails = new EngineNodeFindByNameEqDetails();
      findByNameEqDetails.setName(name);

      SearchDetails<EngineNodeFindByNameEqDetails> searchDetails =
        new SearchDetails<>(findByNameEqDetails);

      ParameterContext<SearchDetails<EngineNodeFindByNameEqDetails>> searchParameter =
        nameParameter.construct(searchDetails);

      SearchResult<EngineNodeData> searchResult = findData(searchParameter);

      if (searchResult.isError()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      List<EngineNodeData> list = searchResult.getResults();
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

  public CrudsResult<EngineNodeData> getData(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    Long id = dataRef.getId();
    CrudsResult<EngineNodeData> result;
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

  private CrudsResult<EngineNodeData> getDataFromStrategy(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    Long id = dataRef.getId();
    CrudsResult<EngineNodeData> result = getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    // @anchor:getData-strategy:start
    // @anchor:getData-strategy:end
    return getDataByName(dataRefParameter.construct(dataRef.getName()));
  }
  // anchor:findMethods:end

  // anchor:searchMethods:start
  /* searching */

  public <S extends EngineNodeFindDetails> SearchResult<EngineNodeData> findData(ParameterContext<SearchDetails<S>> searchParameter) {
    return engineNodeFinder.find(searchParameter);
  }

  public <S extends EngineNodeFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<EngineNodeData> dataResult = findData(searchParameter);

    if (dataResult.isError()) {
      return SearchResult.error(dataResult.getDiagnostics());
    }

    String projection = searchParameter.getValue().getProjection();
    IDataElementProjector<EngineNodeData, T> projector = getElementProjector(projection);

    List<T> projected = project(searchParameter.getContext(), dataResult.getResults(), projector);
    return SearchResult.success(projected, dataResult.getTotalNumberOfItems());
  }

  /**
   * Internal method, do not use
   */
  private <T> List<T> project(Context context, List<EngineNodeData> fromItems, IDataElementProjector<EngineNodeData, T> projector) {
    return fromItems.stream()
        .map(context::withParameter)
        .map(projector::project)
        .collect(Collectors.toList());
  }

  /**
   * Internal method, do not use
   */
  @Deprecated
  private <T> List<T> project(UserContext userContext, List<EngineNodeData> fromItems, IDataElementProjector<EngineNodeData, T> projector) {
    return project(Context.from(userContext), fromItems, projector);
  }

  @Override
  public <P> SearchResult<P> project(ParameterContext<List<EngineNodeData>> listParameter, String projection) {
    IDataElementProjector<EngineNodeData, P> elementProjector = getElementProjector(projection);
    List<EngineNodeData> list = listParameter.getValue();
    List<P> resultList = project(listParameter.getContext(), list, elementProjector);

    return SearchResult.success(resultList);
  }

  // anchor:searchMethods:end

  // anchor:projectors:start
  // anchor:projectors:end

  // anchor:link-getters:start
  // anchor:link-getters:end

  // anchor:link-setters:start
  // anchor:link-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-method-wrappers:start
  // anchor:calculation-method-wrappers:end

  // anchor:custom-projections:start
  // anchor:custom-projections:end

  // anchor:projectMethods:start
  private <P> IDataElementProjector<EngineNodeData, P> getElementProjector(P projection) {
    @SuppressWarnings("unchecked")
    Class<P> projectionClass = (Class<P>) projection.getClass();
    return elementProjectorManager.getProjector(projectionClass);
  }

  @Override
  public String getDisplayName(ParameterContext<EngineNodeData> dataParameter) {
    Option<String> optCustomDisplayName = getCustomDisplayName(dataParameter);
    if (optCustomDisplayName.isDefined()) {
      return optCustomDisplayName.getValue();
    }
    EngineNodeData data = dataParameter.getValue();
    String displayName;

    displayName = data.getName();

    return displayName;
  }

  private Option<String> getCustomDisplayName(ParameterContext<EngineNodeData> dataParameter) {
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
  private <T> IDataElementProjector<EngineNodeData, T> getElementProjector(String projectionName) {
    IDataElementProjector<EngineNodeData, T> projector = elementProjectorManager.getProjector(projectionName);
    if (projector == null) {
      throw new InvalidProjectionException(new ElementRef("workflow", "EngineNode"), projectionName);
    }
    return projector;
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    ProjectionRef projectionRef = projectionRefParameter.getValue();

    DataRef dataRef = projectionRef.getDataRef();
    CrudsResult<EngineNodeData> dataResult = getData(projectionRefParameter.construct(dataRef));
    EngineNodeData engineNodeData;
    if (dataResult.isSuccess()) {
      engineNodeData = dataResult.getValue();
    } else {
      engineNodeData = new EngineNodeData();
    }

    // anchor:custom-preProject:start
    // anchor:custom-preProject:end

    IDataElementProjector<EngineNodeData, T> projector = getElementProjector(projectionRef.getProjection());
    T projection = projector.project(projectionRefParameter.construct(engineNodeData));

    if (engineNodeData != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Retrieved EngineNodeCruds:" + projectionRef.getProjection() + " { id : " + engineNodeData.getId() + ", name: " + engineNodeData.getName() + " }"
        );
      }
    }

    return CrudsResult.success(projection);
  }

  public CrudsResult<EngineNodeDetails> getDetails(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<EngineNodeData> result = getData(dataRefParameter);
    if (result.isError()) {
      return result.convertError();
    }

    EngineNodeData engineNodeData = result.getValue();
    EngineNodeDetails engineNodeDetails = detailsProjector.project(dataRefParameter.construct(engineNodeData));

    return CrudsResult.success(engineNodeDetails);
  }

  public CrudsResult<List<EngineNodeDetails>> getDetailsListFromData(ParameterContext<Collection<EngineNodeData>> dataListParameter) {
    Collection<EngineNodeData> dataList = dataListParameter.getValue();
    List<EngineNodeDetails> list = new ArrayList<>(dataList.size());

    for (EngineNodeData engineNodeData: dataList) {
      EngineNodeDetails engineNodeDetails = detailsProjector.project(dataListParameter.construct(engineNodeData));
      list.add(engineNodeDetails);
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
    CrudsResult<EngineNodeData> dataResult = getData(dataRefParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }
    EngineNodeData data = dataResult.getValue();
    return getDataRefFromData(dataRefParameter.construct(data));
  }

  // anchor:projectMethods:end

  // @anchor:methods:start

  /*========== Exposed Field Methods ==========*/

  // anchor:exposed-fields:start
  public CrudsResult<Boolean> getMaster(ParameterContext<DataRef> idParameter) {
    CrudsResult<EngineNodeData> dataResult = this.getData(idParameter.construct(idParameter.getDataRef()));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }
    EngineNodeData engineNodeData = dataResult.getValue();
    return CrudsResult.success(engineNodeData.getMaster());
  }

  public CrudsResult<Void> setMaster(ParameterContext<Boolean> idParameter) {
    DataRef dataRef = idParameter.getDataRef();
    CrudsResult<EngineNodeData> dataResult = this.getData(idParameter.construct(dataRef));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }

    EngineNodeData engineNodeData = dataResult.getValue();
    engineNodeData.setMaster(idParameter.getValue());

    entityManager.flush();

    return CrudsResult.success();
  }

  public CrudsResult<Date> getLastActive(ParameterContext<DataRef> idParameter) {
    CrudsResult<EngineNodeData> dataResult = this.getData(idParameter.construct(idParameter.getDataRef()));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }
    EngineNodeData engineNodeData = dataResult.getValue();
    return CrudsResult.success(engineNodeData.getLastActive());
  }

  public CrudsResult<Void> setLastActive(ParameterContext<Date> idParameter) {
    DataRef dataRef = idParameter.getDataRef();
    CrudsResult<EngineNodeData> dataResult = this.getData(idParameter.construct(dataRef));
    if (dataResult.isError()) {
        return dataResult.convertError();
    }

    EngineNodeData engineNodeData = dataResult.getValue();
    engineNodeData.setLastActive(idParameter.getValue());

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
      Query query = entityManager.createNamedQuery(EngineNodeData.QUERY_COMPARE_SET_STATUS);
      query.setParameter("id", target.getId());
      query.setParameter("expectedStatus", expectedStatus);
      query.setParameter("targetStatus", targetStatus);

      int nbUpdatedEntities = query.executeUpdate();
      entityManager.flush();

      if (nbUpdatedEntities == 1) {
        return CrudsResult.success();
      } else {
        CrudsResult<EngineNodeDetails> detailsResult = getDetails(parameter.construct(target));
        String actualStatus = detailsResult.isSuccess() ? detailsResult.getValue().getStatus() : "???";
        if (logger.isDebugEnabled()) {
          logger.debug(
              "compareAndSetStatus failed for EngineNode with id=" + target.getId() + ", expectedStatus='" + expectedStatus + "', actualStatus='" + actualStatus + "', targetStatus='" + targetStatus + "'"
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
  @Override
  public CrudsResult<Void> promoteToMaster(ParameterContext<DataRef> parameter) {
    DataRef nodeRef = parameter.getValue();

    Query query = entityManager.createNamedQuery(EngineNodeData.QUERY_PROMOTE_MASTER);
    query.setParameter("id", nodeRef.getId());

    int nbUpdatedEntities = query.executeUpdate();
    entityManager.flush();

    if (nbUpdatedEntities == 1) {
      return CrudsResult.success();
    } else {
      logger.warn("Could not promote EngineNode with name=" + nodeRef.getName() + "' to master");
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
      "EngineNode"
    );
  }
}