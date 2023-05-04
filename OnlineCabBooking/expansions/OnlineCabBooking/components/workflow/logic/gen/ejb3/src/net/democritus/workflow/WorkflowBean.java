package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.naming.Context;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NamedQueries;
import javax.persistence.Query;

import net.democritus.support.ejb3.SearchDataRefToSearchDetailsMapper;

import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.diagnostics.DiagnosticHelper;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.IParameterContextFactory;
import net.democritus.sys.search.SearchDetails;
import net.democritus.projection.IDataElementProjector;

import static net.democritus.sys.DiagnosticConstants.*;
import static net.palver.util.Options.Option;



// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.workflow.WorkflowCsvImporterImpl;
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.UserLocal;
import net.democritus.usr.UserDetails;
// anchor:imports:end

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// anchor:custom-imports:start
import net.democritus.sys.BasicProcessingContext;
import net.democritus.sys.FlowOrchestrator;
import net.democritus.sys.FlowResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.FunctionTargetContext;
import net.democritus.sys.workflow.RunId;
import net.democritus.sys.BasicUserContext;
import net.democritus.usr.UserAgent;

import net.democritus.wfe.BatchProcessingContext;
import net.democritus.wfe.EngineNodeContext;
import net.democritus.wfe.EngineNodeContextRetriever;
import net.democritus.wfe.EngineServiceDetails;
import net.democritus.wfe.EngineServiceLocal;
import net.democritus.wfe.EngineServiceProcessingContext;
import net.democritus.wfe.WorkflowParameterContextFactory;

import java.util.stream.Collectors;
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(WorkflowRemote.class)
@Local(WorkflowLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class WorkflowBean /*@anchor:interfaces:start@*/implements WorkflowRemote, WorkflowLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("workflow", "Workflow");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  @EJB private UserLocal userLocal;

  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(WorkflowBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_workflow")
  private EntityManager entityManager;

  @EJB
  WorkflowCrudsLocal workflowCrudsLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
  @EJB
  private EngineServiceLocal engineServiceLocal;

  private static final SearchResultCacheProvider<WorkflowBean, WorkflowDetails> CACHE_PROVIDER = new SearchResultCacheProvider<>(
      (bean, ctx) -> {
        SearchDetails<WorkflowFindAllWorkflowsDetails> searchDetails = SearchDetails.fetchAllDetails(new WorkflowFindAllWorkflowsDetails());
        searchDetails.setSkipCount(true);
        return bean.find(ctx.withParameter(searchDetails));
      });
  private static final SearchResultCacheProvider<WorkflowBean, UserContext> responsibleUserContextCacheProvider = new SearchResultCacheProvider<>(
      (bean, ctx) -> {
        UserAgent userAgent = UserAgent.getUserAgent(ctx);
        List<UserContext> userContextList = CACHE_PROVIDER.getCache(bean, ctx).get().stream()
            .map(w -> w.getResponsible().getId())
            .distinct()
            .map(id -> userAgent.resolveDataRef(DataRef.withId(id)))
            .filter(CrudsResult::isSuccess)
            .map(CrudsResult::getValue)
            .map(BasicUserContext::new)
            .collect(Collectors.toList());
        return SearchResult.success(userContextList);
      }
  );
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<WorkflowDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    WorkflowDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = workflowCrudsLocal.create(context.withParameter(details));

    if (result.isError()) {
      return result;
    }

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    CACHE_PROVIDER.expireCaches();
    // anchor:custom-postCreate:end
    return result;
  }

  // @anchor:modify-annotations:start
  // @anchor:modify-annotations:end
  // anchor:custom-modify-annotations:start
  // anchor:custom-modify-annotations:end
  public CrudsResult<DataRef> modify(ParameterContext<WorkflowDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    WorkflowDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = workflowCrudsLocal.modify(context.withParameter(details));
    if (result.isError()) {
      return result;
    }

    // @anchor:postModify:start
    // @anchor:postModify:end

    // anchor:custom-postModify:start
    CACHE_PROVIDER.expireCaches();
    // anchor:custom-postModify:end

    return result;
  }

  // @anchor:createOrModify-annotations:start
  // @anchor:createOrModify-annotations:end
  // anchor:custom-createOrModify-annotations:start
  // anchor:custom-createOrModify-annotations:end
  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    P projection = projectionParameter.getValue();
    net.democritus.sys.Context context = projectionParameter.getContext();

    // @anchor:preCreateOrModify:start
    // @anchor:preCreateOrModify:end
    // anchor:custom-preCreateOrModify:start
    // anchor:custom-preCreateOrModify:end

    // @anchor:preCreateOrModify-validation:start
    // @anchor:preCreateOrModify-validation:end

    CrudsResult<DataRef> result = workflowCrudsLocal.createOrModify(projectionParameter);
    if (result.isError()) {
      return result;
    }

    // @anchor:postCreateOrModify:start
    // @anchor:postCreateOrModify:end
    // anchor:custom-postCreateOrModify:start
    CACHE_PROVIDER.expireCaches();
    // anchor:custom-postCreateOrModify:end

    return result;
  }

  // @anchor:delete-annotations:start
  // @anchor:delete-annotations:end
  // anchor:custom-delete-annotations:start
  // anchor:custom-delete-annotations:end
  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }

    net.democritus.sys.Context context = idParameter.getContext();
    Long id = idParameter.getValue();

    // @anchor:preDelete:start
    // @anchor:preDelete:end

    // anchor:custom-preDelete:start
    // anchor:custom-preDelete:end

    try {
      // @anchor:preDelete-tryBlock:start
      // @anchor:preDelete-tryBlock:end

      DataRef dataRef = idToDataRef(idParameter.getValue());
      CrudsResult<Void> result = workflowCrudsLocal.delete(context.withParameter(dataRef));

      if (result.isError()) {
          return result;
      }

      // @anchor:postDelete:start
      // @anchor:postDelete:end

      // anchor:custom-postDelete:start
      CACHE_PROVIDER.expireCaches();
      // anchor:custom-postDelete:end

      return CrudsResult.success(null);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Delete failed for ID = " + id, e
        );
      }
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }
  }

  // @anchor:deleteByDataRef-annotations:start
  // @anchor:deleteByDataRef-annotations:end
  // anchor:custom-deleteByDataRef-annotations:start
  // anchor:custom-deleteByDataRef-annotations:end
  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }
    CrudsResult<DataRef> dataRefResult = resolveDataRef(dataRefParameter);
    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return delete(dataRefParameter.construct(dataRefResult.getValue().getId()));
  }
  // anchor:crudsMethods:end

  /*========== Business logic methods ==========*/

  // anchor:businessLogic:start
  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    return workflowCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = workflowCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return workflowCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public WorkflowInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public WorkflowDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public WorkflowDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return workflowCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return workflowCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<WorkflowInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<WorkflowDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<WorkflowDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<WorkflowDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    ProjectionRef projectionRef = new ProjectionRef("details", dataRef);
    return getProjection(dataRefParameter.construct(projectionRef));
  }

  // anchor:businessLogic:end

  /*========== findBy methods ==========*/

  // anchor:findBy:start
  // anchor:custom-projections:start
  // anchor:custom-projections:end

  private Map<String, IDataElementProjector> elementProjectorMap = new HashMap<>();
  {
    /* no longer used */
    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  // anchor:custom-getProjection-annotations:start
  // anchor:custom-getProjection-annotations:end
  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    CrudsResult<T> result;

    ProjectionRef projectionRef = projectionRefParameter.getValue();
    DataRef dataRef = projectionRef.getDataRef();

    // @anchor:before-getProjection:start
    // @anchor:before-getProjection:end

    // anchor:custom-before-getProjection:start
    // anchor:custom-before-getProjection:end

    result = workflowCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends WorkflowFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    result = workflowCrudsLocal.find(searchParameter);

    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return result;
  }

  // anchor:findBy:end

  /*========== Command implementations ==========*/

  // anchor:command-implementations:start
  // anchor:command-implementations:end

  /*========== Import/Export ==========*/

  // anchor:import-export:start
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public ImportResult importFile(ParameterContext<ImportFile> parameter) {
    String type = parameter.getValue().getType();
    ImportResult importResult;
    if (false) {
    // @anchor:import-implementations:start
    } else if ("csv".equals(type)) {
      importResult = new WorkflowCsvImporterImpl().importFile(parameter);
    // @anchor:import-implementations:end
    // anchor:custom-import-implementations:start
    // anchor:custom-import-implementations:end
    } else {
      // @anchor:import-unsupported:start
      // @anchor:import-unsupported:end
      // anchor:custom-import-unsupported:start
      // anchor:custom-import-unsupported:end
      return ImportResult.globalError("Unsupported import type (" + type + ")");
    }
    // @anchor:post-import:start
    // @anchor:post-import:end
    // anchor:custom-post-import:start
    // anchor:custom-post-import:end
    return importResult;
  }
  // anchor:import-export:end

  /*========== utility ==========*/

  private DiagnosticHelper getDiagnosticHelper() {
    return diagnosticHelper;
  }

  private DiagnosticFactory getDiagnosticFactory() {
    return diagnosticFactory;
  }

  private DataRef idToDataRef(Long id) {
    return new DataRef(
        id,
        "[no name]",
        "workflow",
        "net.democritus.workflow",
        "Workflow"
    );
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public FlowResult<Void> runWorkflowWithEngineService(ParameterContext<DataRef> engineServiceParameter) {
    net.democritus.sys.Context baseContext = engineServiceParameter.getContext();

    ProjectionRef projectionRef = new ProjectionRef("details", engineServiceParameter.getValue());

    CrudsResult<EngineServiceDetails> crudsResult = engineServiceLocal.getProjection(engineServiceParameter.construct(projectionRef));
    if (crudsResult.isError()) {
      return FlowResult.error();
    }

    EngineServiceDetails engineServiceDetails = crudsResult.getValue();
    WorkflowDetails workflowDetails = getDetails(engineServiceDetails.getWorkflow());

    EngineNodeContext engineNodeContext = new EngineNodeContextRetriever(baseContext).retrieveEngineNodeContext();
    UserContext userContext = getResponsibleUserContext(baseContext.withParameter(workflowDetails));

    net.democritus.sys.Context context = baseContext
        .extend(engineNodeContext)
        .extend(userContext)
        .extend(new EngineServiceProcessingContext(engineServiceDetails.getDataRef()))
        .extend(new BasicProcessingContext("<no implementation>", engineServiceDetails.getName()));

    FlowOrchestrator<?, WorkflowDetails> flowOrchestrator = FlowOrchestratorLookup.getFlowOrchestrator(workflowDetails);
    engineServiceLocal.setLastRunAt(context
        .extend(new FunctionTargetContext(engineServiceDetails.getDataRef()))
        .withParameter(new Date()));
    FlowResult<?> flowResult = flowOrchestrator.orchestrate(context.withParameter(workflowDetails));
    if (flowResult.isError()) {
      logger.warn("Workflow '" + workflowDetails.getName() + "' failed when executing for engineService '" + engineServiceDetails.getName() + "'");
    }

    return (FlowResult<Void>) flowResult;
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public FlowResult<Void> runWorkflowWithTargets(ParameterContext<DataRef> workflowParameter, List<DataRef> targetInstances) {
    net.democritus.sys.Context baseContext = workflowParameter.getContext();

    ProjectionRef projectionRef = new ProjectionRef("details", workflowParameter.getValue());

    CrudsResult<WorkflowDetails> crudsResult = getProjection(workflowParameter.construct(projectionRef));
    if (crudsResult.isError()) {
      return FlowResult.error();
    }

    WorkflowDetails workflowDetails = crudsResult.getValue();

    EngineNodeContext engineNodeContext = new EngineNodeContextRetriever(baseContext).retrieveEngineNodeContext();
    UserContext userContext = getResponsibleUserContext(baseContext.withParameter(workflowDetails));

    net.democritus.sys.Context context = baseContext
        .extend(engineNodeContext)
        .extend(userContext)
        .extend(new BatchProcessingContext(targetInstances))
        .extend(new BasicProcessingContext("<no implementation>", "<no engineService>"));

    FlowOrchestrator<?, WorkflowDetails> flowOrchestrator = FlowOrchestratorLookup.getFlowOrchestrator(workflowDetails);
    FlowResult<?> flowResult = flowOrchestrator.orchestrate(context.withParameter(workflowDetails));
    if (flowResult.isError()) {
      logger.warn("Workflow '" + workflowDetails.getName() + "' failed when executing for targets " + targetInstances);
    }

    return (FlowResult<Void>) flowResult;
  }

  @Override
  public TaskResult<Void> recoverWorkflow(ParameterContext<DataRef> workflowParameter) {
    net.democritus.sys.Context context = workflowParameter.getContext();
    ProjectionRef projectionRef = new ProjectionRef("details", workflowParameter.getValue());
    CrudsResult<WorkflowDetails> crudsResult = getProjection(context.withParameter(projectionRef));
    if (crudsResult.isError()) {
      return TaskResult.error(crudsResult.getDiagnostics());
    }

    WorkflowDetails details = crudsResult.getValue();
    WorkflowParameterContextFactory contextFactory = new WorkflowParameterContextFactory(context.withParameter(details), Collections.emptyList(), new RunId());
    contextFactory.init();
    net.democritus.sys.Context workflowContext = contextFactory.createContext(null).getContext();

    WorkflowInterruptRecoverer workflowInterruptRecoverer = new WorkflowInterruptRecoverer(workflowContext);
    workflowInterruptRecoverer.recover(details);
    return TaskResult.success();
  }

  @Override
  public CrudsResult<WorkflowDetails> getWorkflowById(ParameterContext<Long> workflowIdParameter) {
    return CACHE_PROVIDER.getCache(this, workflowIdParameter.getContext())
        .getInstance(w -> w.getId().equals(workflowIdParameter.getValue()));
  }

  @Override
  public UserContext getResponsibleUserContext(ParameterContext<WorkflowDetails> workflowDetailsParameter) {
    WorkflowDetails workflowDetails = workflowDetailsParameter.getValue();
    return responsibleUserContextCacheProvider.getCache(this, workflowDetailsParameter.getContext())
        .getInstance(e -> e.getId().equals(workflowDetails.getResponsible().getId()))
        .getValueOrElse(UserContext.NO_USER_CONTEXT);
  }
  // anchor:custom-methods:end

}
