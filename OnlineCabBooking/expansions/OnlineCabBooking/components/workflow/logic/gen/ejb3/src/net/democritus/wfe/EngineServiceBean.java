package net.democritus.wfe;

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

import net.democritus.state.StateUpdate;


// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.wfe.EngineServiceCsvImporterImpl;
import java.util.Date;
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.workflow.WorkflowLocal;
import net.democritus.workflow.WorkflowDetails;
import net.democritus.wfe.TimeWindowGroupLocal;
import net.democritus.wfe.TimeWindowGroupDetails;
import net.democritus.wfe.EngineNodeServiceLocal;
import net.democritus.wfe.EngineNodeServiceDetails;
// anchor:imports:end

// anchor:valuetype-imports:start
import net.democritus.valuetype.basic.DateTime;
import net.democritus.valuetype.basic.DateTimeConverter;

// anchor:valuetype-imports:end

// anchor:custom-imports:start
import net.democritus.properties.RuntimeProperties;
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(EngineServiceRemote.class)
@Local(EngineServiceLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class EngineServiceBean /*@anchor:interfaces:start@*/implements EngineServiceRemote, EngineServiceLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("workflow", "EngineService");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  @EJB private WorkflowLocal workflowLocal;

  @EJB private TimeWindowGroupLocal timeWindowGroupLocal;

  @EJB private EngineNodeServiceLocal engineNodeServiceLocal;

  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineServiceBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_workflow")
  private EntityManager entityManager;

  @EJB
  EngineServiceCrudsLocal engineServiceCrudsLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
  @EJB
  private TimerHandlerLocal timerHandler;
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<EngineServiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    EngineServiceDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = engineServiceCrudsLocal.create(context.withParameter(details));

    if (result.isError()) {
      return result;
    }

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // @anchor:modify-annotations:start
  // @anchor:modify-annotations:end
  // anchor:custom-modify-annotations:start
  // anchor:custom-modify-annotations:end
  public CrudsResult<DataRef> modify(ParameterContext<EngineServiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    EngineServiceDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = engineServiceCrudsLocal.modify(context.withParameter(details));
    if (result.isError()) {
      return result;
    }

    // @anchor:postModify:start
    // @anchor:postModify:end

    // anchor:custom-postModify:start
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

    CrudsResult<DataRef> result = engineServiceCrudsLocal.createOrModify(projectionParameter);
    if (result.isError()) {
      return result;
    }

    // @anchor:postCreateOrModify:start
    // @anchor:postCreateOrModify:end
    // anchor:custom-postCreateOrModify:start
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
      CrudsResult<Void> result = engineServiceCrudsLocal.delete(context.withParameter(dataRef));

      if (result.isError()) {
          return result;
      }

      // @anchor:postDelete:start
      // @anchor:postDelete:end

      // anchor:custom-postDelete:start
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
    return engineServiceCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = engineServiceCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return engineServiceCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public EngineServiceInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public EngineServiceDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public EngineServiceDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return engineServiceCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return engineServiceCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<EngineServiceInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<EngineServiceDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<EngineServiceDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<EngineServiceDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
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

    result = engineServiceCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    if (result.isSuccess()
        && result.getValue() instanceof EngineServiceRunningState
        && isSingleNodeApplication()) {
      updateRunningStateProjection(projectionRefParameter.construct((EngineServiceRunningState) result.getValue()));
    }
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends EngineServiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    result = engineServiceCrudsLocal.find(searchParameter);

    // anchor:custom-after-find:start
    if (result.isSuccess() && isSingleNodeApplication()) {
      List<T> results = result.getResults();
      for (T engine : results) {
        if (engine instanceof EngineServiceRunningState) {
          updateRunningStateProjection(searchParameter.construct((EngineServiceRunningState) engine));
        }
      }
    }
    // anchor:custom-after-find:end

    return result;
  }

  // anchor:findBy:end

  /*========== Command implementations ==========*/

  // anchor:command-implementations:start
  // @anchor:command-startEngine-annotations:start
  // @anchor:command-startEngine-annotations:end
  // anchor:custom-command-startEngine-annotations:start
  // anchor:custom-command-startEngine-annotations:end
  public CommandResult startEngine(ParameterContext<EngineServiceCommand.StartEngine> commandParameter) {
    EngineServiceCommand.StartEngine startEngineCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(startEngineCommand);

    // @anchor:command-startEngine-before:start
    // @anchor:command-startEngine-before:end
    // anchor:custom-command-startEngine-before:start
    // anchor:custom-command-startEngine-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    CrudsResult<EngineServiceDetails> targetResult = getDetailsFromDataRef(parameterFactory.construct(startEngineCommand.getTarget()));

    EngineServiceDetails engineServiceDetails = targetResult.getValue();

    // @anchor:command-startEngine:start
    // @anchor:command-startEngine:end
    // anchor:custom-command-startEngine:start
    engineServiceDetails.setStatus("start");
    modify(parameterFactory.construct(engineServiceDetails));
    // anchor:custom-command-startEngine:end

    // @anchor:command-startEngine-after:start
    // @anchor:command-startEngine-after:end
    // anchor:custom-command-startEngine-after:start
    // anchor:custom-command-startEngine-after:end

    return commandResult;
  }
  // @anchor:command-stopEngine-annotations:start
  // @anchor:command-stopEngine-annotations:end
  // anchor:custom-command-stopEngine-annotations:start
  // anchor:custom-command-stopEngine-annotations:end
  public CommandResult stopEngine(ParameterContext<EngineServiceCommand.StopEngine> commandParameter) {
    EngineServiceCommand.StopEngine stopEngineCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(stopEngineCommand);

    // @anchor:command-stopEngine-before:start
    // @anchor:command-stopEngine-before:end
    // anchor:custom-command-stopEngine-before:start
    // anchor:custom-command-stopEngine-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    CrudsResult<EngineServiceDetails> targetResult = getDetailsFromDataRef(parameterFactory.construct(stopEngineCommand.getTarget()));

    EngineServiceDetails engineServiceDetails = targetResult.getValue();

    // @anchor:command-stopEngine:start
    // @anchor:command-stopEngine:end
    // anchor:custom-command-stopEngine:start
    engineServiceDetails.setStatus("stop");
    modify(parameterFactory.construct(engineServiceDetails));
    // anchor:custom-command-stopEngine:end

    // @anchor:command-stopEngine-after:start
    // @anchor:command-stopEngine-after:end
    // anchor:custom-command-stopEngine-after:start
    // anchor:custom-command-stopEngine-after:end

    return commandResult;
  }
  // @anchor:command-disableAllEngines-annotations:start
  // @anchor:command-disableAllEngines-annotations:end
  // anchor:custom-command-disableAllEngines-annotations:start
  // anchor:custom-command-disableAllEngines-annotations:end
  public CommandResult disableAllEngines(ParameterContext<EngineServiceCommand.DisableAllEngines> commandParameter) {
    EngineServiceCommand.DisableAllEngines disableAllEnginesCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(disableAllEnginesCommand);

    // @anchor:command-disableAllEngines-before:start
    // @anchor:command-disableAllEngines-before:end
    // anchor:custom-command-disableAllEngines-before:start
    // anchor:custom-command-disableAllEngines-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    // @anchor:command-disableAllEngines:start
    // @anchor:command-disableAllEngines:end
    // anchor:custom-command-disableAllEngines:start
    SearchDetails<EngineServiceFindAllEngineServicesDetails> searchDetails = SearchDetails.fetchAllDetails(new EngineServiceFindAllEngineServicesDetails());
    searchDetails.setSkipCount(true);
    SearchResult<EngineServiceDetails> searchResult = find(commandParameter.construct(searchDetails));
    if (searchResult.isError()) {
      return CommandResult.error(commandParameter.getValue(), "Failed to find engineServices");
    }

    for (EngineServiceDetails engineServiceDetails : searchResult.getResults()) {
      engineServiceDetails.setStatus("stop");
      CrudsResult<DataRef> result = modify(commandParameter.construct(engineServiceDetails));
      if (result.isError()) {
        return CommandResult.error(commandParameter.getValue(), "Failed to disable engineService '" + engineServiceDetails.getName() + "'");
      }
    }
    // anchor:custom-command-disableAllEngines:end

    // @anchor:command-disableAllEngines-after:start
    // @anchor:command-disableAllEngines-after:end
    // anchor:custom-command-disableAllEngines-after:start
    // anchor:custom-command-disableAllEngines-after:end

    return commandResult;
  }
  // @anchor:command-enableAllEngines-annotations:start
  // @anchor:command-enableAllEngines-annotations:end
  // anchor:custom-command-enableAllEngines-annotations:start
  // anchor:custom-command-enableAllEngines-annotations:end
  public CommandResult enableAllEngines(ParameterContext<EngineServiceCommand.EnableAllEngines> commandParameter) {
    EngineServiceCommand.EnableAllEngines enableAllEnginesCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(enableAllEnginesCommand);

    // @anchor:command-enableAllEngines-before:start
    // @anchor:command-enableAllEngines-before:end
    // anchor:custom-command-enableAllEngines-before:start
    // anchor:custom-command-enableAllEngines-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    // @anchor:command-enableAllEngines:start
    // @anchor:command-enableAllEngines:end
    // anchor:custom-command-enableAllEngines:start
    SearchDetails<EngineServiceFindAllEngineServicesDetails> searchDetails = SearchDetails.fetchAllDetails(new EngineServiceFindAllEngineServicesDetails());
    searchDetails.setSkipCount(true);
    SearchResult<EngineServiceDetails> searchResult = find(commandParameter.construct(searchDetails));
    if (searchResult.isError()) {
      return CommandResult.error(commandParameter.getValue(), "Failed to find engineServices");
    }

    for (EngineServiceDetails engineServiceDetails : searchResult.getResults()) {
      engineServiceDetails.setStatus("start");
      CrudsResult<DataRef> result = modify(commandParameter.construct(engineServiceDetails));
      if (result.isError()) {
        return CommandResult.error(commandParameter.getValue(), "Failed to enable engineService '" + engineServiceDetails.getName() + "'");
      }
    }
    // anchor:custom-command-enableAllEngines:end

    // @anchor:command-enableAllEngines-after:start
    // @anchor:command-enableAllEngines-after:end
    // anchor:custom-command-enableAllEngines-after:start
    // anchor:custom-command-enableAllEngines-after:end

    return commandResult;
  }
  // @anchor:command-refreshEngine-annotations:start
  // @anchor:command-refreshEngine-annotations:end
  // anchor:custom-command-refreshEngine-annotations:start
  // anchor:custom-command-refreshEngine-annotations:end
  public CommandResult refreshEngine(ParameterContext<EngineServiceCommand.RefreshEngine> commandParameter) {
    EngineServiceCommand.RefreshEngine refreshEngineCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(refreshEngineCommand);

    // @anchor:command-refreshEngine-before:start
    // @anchor:command-refreshEngine-before:end
    // anchor:custom-command-refreshEngine-before:start
    // anchor:custom-command-refreshEngine-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    CrudsResult<EngineServiceDetails> targetResult = getDetailsFromDataRef(parameterFactory.construct(refreshEngineCommand.getTarget()));

    EngineServiceDetails engineServiceDetails = targetResult.getValue();

    // @anchor:command-refreshEngine:start
    // @anchor:command-refreshEngine:end
    // anchor:custom-command-refreshEngine:start
    Integer refreshResult = timerHandler.refreshTimer(engineServiceDetails.getName());
    commandResult = refreshResult >= 0 ?
        CommandResult.success(refreshEngineCommand) :
        CommandResult.error(refreshEngineCommand, "Refresh command failed");
    // anchor:custom-command-refreshEngine:end

    // @anchor:command-refreshEngine-after:start
    // @anchor:command-refreshEngine-after:end
    // anchor:custom-command-refreshEngine-after:start
    // anchor:custom-command-refreshEngine-after:end

    return commandResult;
  }
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
      importResult = new EngineServiceCsvImporterImpl().importFile(parameter);
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
        "net.democritus.wfe",
        "EngineService"
    );
  }

  // anchor:compare-set-methods:start
  @Override
  public CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter) {
    return engineServiceCrudsLocal.compareAndSetStatus(parameter);
  }
  // anchor:compare-set-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  public <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter) {
    return executeCommand(commandParameter);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <T extends ICommand> CommandResult dispatchCommand(ParameterContext<T> commandParameter) {

    // anchor:custom-before-dispatch:start
    // anchor:custom-before-dispatch:end

    CommandResult commandResult = executeCommand(commandParameter);

    // anchor:custom-after-dispatch:start
    // anchor:custom-after-dispatch:end

    if (commandResult.isError()) {
        sessionContext.setRollbackOnly();
    }

    return commandResult;
  }

  @SuppressWarnings("unchecked")
  private <T extends ICommand> CommandResult executeCommand(ParameterContext<T> commandParameter) {
    ICommand command = commandParameter.getValue();

    // anchor:command-dispatch:start
    if (command instanceof EngineServiceCommand.StartEngine) {
      return startEngine((ParameterContext<EngineServiceCommand.StartEngine>) commandParameter);
    }

    if (command instanceof EngineServiceCommand.StopEngine) {
      return stopEngine((ParameterContext<EngineServiceCommand.StopEngine>) commandParameter);
    }

    if (command instanceof EngineServiceCommand.DisableAllEngines) {
      return disableAllEngines((ParameterContext<EngineServiceCommand.DisableAllEngines>) commandParameter);
    }

    if (command instanceof EngineServiceCommand.EnableAllEngines) {
      return enableAllEngines((ParameterContext<EngineServiceCommand.EnableAllEngines>) commandParameter);
    }

    if (command instanceof EngineServiceCommand.RefreshEngine) {
      return refreshEngine((ParameterContext<EngineServiceCommand.RefreshEngine>) commandParameter);
    }
    // anchor:command-dispatch:end

    return CommandResult.error(command, "not dispatched");
  }

  /*========== Exposed Field Methods ==========*/

  // anchor:exposed-fields:start
  public CrudsResult<String> getBusy(ParameterContext<DataRef> idParameter) {
    return engineServiceCrudsLocal.getBusy(idParameter);
  }

  public CrudsResult<Date> getLastRunAt(ParameterContext<DataRef> idParameter) {
    return engineServiceCrudsLocal.getLastRunAt(idParameter);
  }

  public CrudsResult<Void> setBusy(ParameterContext<String> idParameter) {
    return engineServiceCrudsLocal.setBusy(idParameter);
  }

  public CrudsResult<Void> setLastRunAt(ParameterContext<Date> idParameter) {
    return engineServiceCrudsLocal.setLastRunAt(idParameter);
  }

  // anchor:exposed-fields:end
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  private boolean isSingleNodeApplication() {
    return RuntimeProperties.getRuntimeProperties().getEjbVersion().equals("ejb3.0");
  }

  private void updateRunningStateProjection(ParameterContext<EngineServiceRunningState> parameter) {
    EngineServiceRunningState projection = parameter.getValue();
    SearchResult<Date> nextRun = timerHandler.getNextRun(parameter.construct(projection.getDataRef()));

    if (nextRun.isError()) {
      projection.setRunning("error");
    } else if (!nextRun.getFirst().isDefined()) {
      projection.setRunning("not running");
    } else if (!projection.getStatus().equals("start")) {
      projection.setRunning("running [disabled]");
    } else {
      projection.setNextRun(nextRun.getFirst().getValue());
      projection.setRunning("running [enabled]");
    }
  }

  public CrudsResult<Void> updateLastRunAt(ParameterContext<DataRef> dataRefParameter) {
    return engineServiceCrudsLocal.updateLastRunAt(dataRefParameter);
  }
  // anchor:custom-methods:end

}
