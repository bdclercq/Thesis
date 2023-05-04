package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.democritus.wfe.EngineServiceDetails;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProcessingContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ProjectionRef;
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
import net.democritus.jndi.ComponentJNDI;
import net.democritus.properties.RuntimeProperties;
import net.democritus.sys.BasicProcessingContext;
import net.democritus.sys.Context;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.FlowOrchestrator;
import net.democritus.sys.FlowResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.democritus.workflow.EngineNodeServiceUpdater;
import net.democritus.workflow.EngineServiceUpdate;
import net.democritus.workflow.WorkflowDetails;
import net.democritus.workflow.WorkflowLocalAgent;
import net.palver.util.Options.Option;

import javax.ejb.EJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static net.palver.util.Options.none;
import static net.palver.util.Options.some;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task TimerHandler.
 */

@Stateless
@Remote(TimerHandlerRemote.class)
@Local(TimerHandlerLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
@SuppressWarnings("unused")
// anchor:custom-class-annotations:end
public class TimerHandlerBean implements TimerHandlerRemote, TimerHandlerLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TimerHandlerBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  private static final DiagnosticFactory diagnosticFactory = new DiagnosticFactory("workflow", "timerHandler");

  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  private Option<TimerInfo> findEngineServiceTimer(String name) {
    Collection<Timer> timers = getTimers();

    if (logger.isDebugEnabled()) {
      logger.debug(
          "timerHandler nrOfTimers = " + timers.size()
      );
    }

    for (Timer timer : timers) {
      try {
        EngineServiceStatus engineServiceStatus = (EngineServiceStatus) timer.getInfo();

        if (logger.isDebugEnabled()) {
          logger.debug(
              "  > timerHandler timer for " + engineServiceStatus.getEngineName()
          );
        }

        if (engineServiceStatus.getEngineName().equals(name)) {
          return some(new TimerInfo(engineServiceStatus, timer));
        }
      } catch (NoSuchObjectLocalException e) {
        logger.debug(
            "cannot query timer because of exception", e
        );
      }
    }
    return none();
  }

  private EngineServiceStatus updateEngineServiceStatus(EngineServiceStatus engineServiceStatus, Context context) {
    String engineName = engineServiceStatus.getEngineName();
    Option<EngineServiceDetailsWithoutRefs> engineService = findEngineService(engineName, context);
    EngineServiceDetailsWithoutRefs engineServiceDetails = engineService.orElse(engineServiceStatus.getEngineService());
    if (hasEngineNodeState()) {
      engineServiceStatus.updateEngineNodeServiceDetails(
          EngineNodeServiceLocalAgent
              .getEngineNodeServiceAgent(context)
              .getEngineNodeServiceForEngineService(context.withParameter(engineServiceDetails.getDataRef()))
              .getValue());
    }
    return engineServiceStatus.updateEngineServiceDetails(engineServiceDetails);
  }

  private Option<EngineServiceDetailsWithoutRefs> findEngineService(String engineName, Context context) {
    EngineServiceLocalAgent engineServiceAgent = EngineServiceLocalAgent.getEngineServiceAgent(context);

    try {
      EngineServiceFindByNameEqDetails finder = new EngineServiceFindByNameEqDetails();
      finder.setName(engineName);

      SearchDetails<EngineServiceFindByNameEqDetails> searchDetails = SearchDetails.fetchN(finder, 2);
      searchDetails.setProjection("detailsWithoutRefs");
      searchDetails.setSkipCount(true);

      SearchResult<EngineServiceDetailsWithoutRefs> searchResult = engineServiceAgent.find(searchDetails);

      if (searchResult.getResults().isEmpty()) {
        if (logger.isErrorEnabled()) {
          logger.error(
              "Could not find engineService with name " + engineName + ", please create one"
          );
        }
        // engineService not found, reinitialize
        return none();
      }

      if (searchResult.getResults().size() > 1) {
        if (logger.isErrorEnabled()) {
          logger.error(
              "Found more than one engineService with name " + engineName + ", please fix this"
          );
        }
        // multiple engineServices found, reinitialize
        return none();
      }

      return some(searchResult.getResults().get(0));
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not find engineService with name " + engineName + ": ", e
        );
      }
      // engineService not found, reinitialize
      return none();
    }
  }

  /* ========== Start and stop timer methods ========== */

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Integer startTimer(String name) {
    return startTimer(name, new TimerOptions());
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Integer startTimer(String name, TimerOptions timerOptions) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "start timer for " + name
      );
    }
    // lookup 'name' engineService
    Option<TimerInfo> timer = findEngineServiceTimer(name);
    if (timer.isDefined()) {
      try {
        resetTimerIfChanged(timer.getValue(), timerOptions);
        return 0;
      } catch (Exception e) {
        logger.error("Cannot modify timer object", e);
        return -1;
      }
    }

    Option<EngineServiceDetailsWithoutRefs> engineService = findEngineService(name, getEngineNodeContext());
    if (engineService.isEmpty()) {
      logger.error("Cannot find engineService");
      return -1;
    }
    EngineServiceStatus engineServiceStatus = new EngineServiceStatus(engineService.getValue());

    try {
      startEngineTimer(engineServiceStatus, timerOptions);
      return 0;
    } catch (Exception e) {
      logger.error("Cannot create timer object", e);
      return -1;
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Integer stopTimer(String name) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "stop timer for " + name
      );
    }
    // lookup 'name' engineService
    Option<TimerInfo> timerInfo = findEngineServiceTimer(name);

    if (timerInfo.isEmpty()) {
      if (logger.isWarnEnabled()) {
        logger.warn("Timer for " + name + " not found");
      }
      return -1;
    }

    timerInfo.getValue().getUpdate();
    try {
      stopEngineTimer(timerInfo.getValue());
      return 0;
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot cancel timer object", e
        );
      }
      return -1;
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Integer refreshTimer(String name) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "refresh timer for " + name
      );
    }
    // lookup 'name' engineService
    Option<TimerInfo> timerInfoOption = findEngineServiceTimer(name);

    if (timerInfoOption.isEmpty()) {
      if (logger.isWarnEnabled()) {
        logger.warn("Timer for " + name + " not found");
      }
      return -1;
    }

    try {
      validateTimer(timerInfoOption.getValue().getTimer());
      return 0;
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot reset timer object", e
        );
      }
      return -1;
    }
  }

  @Timeout
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void timeoutHandler(Timer timer) {
    String engineName = ((EngineServiceStatus) timer.getInfo()).getEngineName();
    if (logger.isDebugEnabled()) {
      logger.debug("*** Received Timer event for " + engineName + " at " + (new Date()));
    }

    TimerTriggerInput timerTriggerInput = validateTimer(timer);
    if (!timerTriggerInput.shouldRun()) {
      return;
    }

    runEngine(timerTriggerInput);
  }

  private TimerTriggerInput validateTimer(Timer timer) {
    EngineServiceStatus engineServiceStatus = (EngineServiceStatus) timer.getInfo();

    Context engineNodeContext = getEngineNodeContext();

    TimerInfo timerInfo = null;

    try {
      engineServiceStatus = updateEngineServiceStatus(engineServiceStatus, engineNodeContext);
      timerInfo = new TimerInfo(engineServiceStatus, timer);
      resetTimerIfChanged(timerInfo, new TimerOptions());
      if (isBusyOrInvalidTime(engineServiceStatus)) {
        return new TimerTriggerInput(false, timerInfo, engineNodeContext);
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error("Cannot handle timeout", e);
      }
      return new TimerTriggerInput(false, timerInfo, engineNodeContext);
    }

    return new TimerTriggerInput(true, timerInfo, engineNodeContext);
  }

  private void runEngine(TimerTriggerInput input) {
    TimerInfo timerInfo = input.getTimerInfo();
    EngineServiceStatus engineServiceStatus = timerInfo.getEngineServiceStatus();
    Context engineNodeContext = input.getEngineNodeContext();
    WorkflowLocalAgent workflowAgent = WorkflowLocalAgent.getWorkflowAgent(engineNodeContext);

    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Executing " + engineServiceStatus.getEngineService().getName());
      }

      CrudsResult<WorkflowDetails> workflowDetailsResult = workflowAgent.getWorkflowById(engineServiceStatus.getWorkflowId());
      if (workflowDetailsResult.isError()) {
        logger.error("Error finding workflow details for engineService " + engineServiceStatus.getEngineService().getName());
        return;
      }
      WorkflowDetails workflow = workflowDetailsResult.getValue();

      UserContext userContext = workflowAgent.getResponsibleUserContext(workflow);

      Context context = getWorkflowContext(engineNodeContext, userContext, engineServiceStatus);
      FlowOrchestrator<?, WorkflowDetails> engine = getFlowOrchestrator(workflow);

      if (engine == null) {
        logger.warn("No engine found for workflow " + workflow.getClassName());
        return;
      }

      if (logger.isDebugEnabled()) {
        logger.debug("orchestrate() for flowOrchestrator " + workflow.getClassName() + "Engine");
      }
      updateLastRunAt(engineServiceStatus, engineNodeContext);
      engineServiceStatus.setBusy(true);

      if (hasEngineNodeState()) {
        new EngineNodeServiceUpdater(context).setStateToWorking(timerInfo.getUpdate());
      }

      FlowResult<?> flowResult = engine.orchestrate(context.withParameter(workflow));
      if (flowResult.isError()) {
        logger.warn("FlowResult for workflow '" + engineServiceStatus.getEngineName() + "' is error");
      }
      if (hasEngineNodeState()) {
        new EngineNodeServiceUpdater(context).setStateToWaiting(timerInfo.getUpdate());
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error("Cannot orchestrate workflow", e);
      }
    } finally {
      engineServiceStatus.setBusy(false);
    }
  }

  private boolean isBusyOrInvalidTime(EngineServiceStatus engineServiceStatus) throws Exception {
    if (!engineServiceStatus.isAvailable()) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "do nothing, engineService " + engineServiceStatus.getEngineName() + " stopped or busy!"
        );
      }
      return true;
    }

    if (!engineServiceStatus.isInTimewindow()) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "do nothing, current time is not in given timeWindows"
        );
      }
      return true;
    }
    return false;
  }

  private void updateLastRunAt(EngineServiceStatus engineServiceStatus, Context context) {
    CrudsResult<Void> updateResult = EngineServiceLocalAgent.getEngineServiceAgent(context)
        .updateLastRunAt(DataRef.withId(engineServiceStatus.getEngineId()));
    if (updateResult.isError()) {
      logger.error("Failed to update engineService lastRunAt");
    }
  }

  /**
   * @param workflow details object of the target workflow
   * @return returns FlowOrchestrator (workflow engine), null if not found
   */
  private FlowOrchestrator<?, WorkflowDetails> getFlowOrchestrator(WorkflowDetails workflow) {
    ComponentJNDI workflowComponent = ComponentJNDI.getComponentJNDI(workflow.getComponentName());

    String workflowEngineName = workflow.getClassName() + "Engine";
    try {
      String engineLocalName = workflowComponent.getFlowLocalName(workflowEngineName);
      return workflowComponent.lookupLocal(engineLocalName);
    } catch (NamingException e) {
      // local engine not found, try remote engine
    }

    try {
      String engineRemoteName = workflowComponent.getFlowRemoteName(workflowEngineName);
      return workflowComponent.lookupRemote(engineRemoteName);
    } catch (NamingException e) {
      // remote engine not found
      return null;
    } catch (Exception e) {
      throw new IllegalStateException("Failed to find EngineBean for workflow='" + workflow.getName() + "' with flowElementName='" + workflow.getClassName() + "'. Expected ComponentMetaData to contain a flowElementDef with name '" + workflowEngineName + "'", e);
    }
  }

  private void resetTimerIfChanged(TimerInfo timerInfo, TimerOptions timerOptions) {
    if (timerInfo.getEngineServiceStatus().isWaitTimeChanged()) {
      stopEngineTimer(timerInfo);
      startEngineTimer(timerInfo.getEngineServiceStatus(), timerOptions);

      if (hasEngineNodeState()) {
        new EngineNodeServiceUpdater(getEngineNodeContext()).updateEngineNodeService(timerInfo.getUpdate());
      }
      timerInfo.getEngineServiceStatus().resetChanged();
      logger.info(String.format("timerHandler timer for %s : restart timer", timerInfo.getEngineName()));
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(String.format(
            "timerHandler timer for %s : continue timer", timerInfo.getEngineName())
        );
      }
    }
  }

  private void stopEngineTimer(TimerInfo timerInfo) {
    if (logger.isInfoEnabled()) {
      logger.info(
          "cancel timer " + timerInfo.getEngineName()
      );
    }
    timerInfo.getTimer().cancel();
    if (hasEngineNodeState()) {
      new EngineNodeServiceUpdater(getEngineNodeContext()).removeEngineNodeService(timerInfo.getUpdate());
    }
  }

  private void startEngineTimer(EngineServiceStatus engineServiceStatus, TimerOptions timerOptions) {
    if (!engineServiceStatus.isEnginePresent()) {
      throw new IllegalStateException("Start timer not possible due to non-existing engineService entry");
    }

    Integer waitTime = engineServiceStatus.getWaitTime();
    if (waitTime == null) {
      waitTime = 30;
      if (logger.isWarnEnabled()) {
        logger.warn(
            "No wait time defined for " + engineServiceStatus.getEngineName() + ", defaulting to " + waitTime + " sec"
        );
      }
    }

    if (logger.isInfoEnabled()) {
      logger.info(
          "create timer (within 10 sec) with interval = " + waitTime + " sec"
      );
    }

    Timer timer = new TimerFactory(getTimerService())
        .createTimer(
            waitTime,
            engineServiceStatus,
            timerOptions);

    if (logger.isInfoEnabled()) {
      logger.info(
          "started timer " + timer.getInfo()
      );
    }

    TimerInfo timerInfo = new TimerInfo(engineServiceStatus, timer);
    if (hasEngineNodeState()) {
      new EngineNodeServiceUpdater(getEngineNodeContext()).registerNewEngineNodeService(timerInfo.getUpdate());
    }
  }

  private TimerService getTimerService() {
    return sessionContext.getTimerService();
  }

  private Collection<Timer> getTimers() {
    return getTimerService().getTimers();
  }

  private boolean hasEngineNodeState() {
    String ejbVersion = RuntimeProperties.getRuntimeProperties().getEjbVersion();
    return !(ejbVersion == null || ejbVersion.isEmpty() || ejbVersion.equals("ejb3.0"));
  }

  @Override
  public SearchResult<Date> getNextRun(ParameterContext<DataRef> parameter) {
    DataRef engineService = parameter.getValue();
    String engineServiceName = engineService.getName();

    Option<TimerInfo> timerInfo = findEngineServiceTimer(engineServiceName);
    if (timerInfo.isDefined()) {
      try {
        Timer timer = timerInfo.getValue().getTimer();
        Date nextTimeout = timer.getNextTimeout();
        return SearchResult.success(Collections.singletonList(nextTimeout));
      } catch (IllegalStateException | EJBException e) {
        logger.error("Could not retrieve next run time", e);
        return SearchResult.error(diagnosticFactory.error("error"));
      }
    } else {
      return SearchResult.success(Collections.emptyList());
    }
  }

  private Context getWorkflowContext(Context engineNodeContext, UserContext userContext, EngineServiceStatus engineServiceStatus) {
    return engineNodeContext
        .extend(userContext)
        .extend(new EngineServiceProcessingContext(engineServiceStatus.getEngineService().getDataRef()))
        .extend(new BasicProcessingContext("<no implementation>", engineServiceStatus.getEngineName()));
  }

  private Context getEngineNodeContext() {
    EngineNodeContextRetriever contextRetriever = new EngineNodeContextRetriever(Context.emptyContext());
    return Context.from(contextRetriever.retrieveEngineNodeContext());
  }

  private static class TimerTriggerInput {

    private final boolean shouldRun;
    private final TimerInfo timerInfo;
    private final Context engineNodeContext;

    public TimerTriggerInput(boolean shouldRun, TimerInfo timerInfo, Context engineNodeContext) {
      this.shouldRun = shouldRun;
      this.timerInfo = timerInfo;
      this.engineNodeContext = engineNodeContext;
    }

    public boolean shouldRun() {
      return shouldRun;
    }

    public TimerInfo getTimerInfo() {
      return timerInfo;
    }

    public Context getEngineNodeContext() {
      return engineNodeContext;
    }

  }
  // anchor:custom-methods:end

}

