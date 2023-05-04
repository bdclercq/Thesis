package net.democritus.wfe;

import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.TaskResult;
import net.democritus.workflow.EngineNodeConfig;
import net.democritus.workflow.EngineNodeMasterStatusViewer;
import net.democritus.workflow.EngineNodeUpdater;
import net.democritus.workflow.EngineNodeMasterFailOverChecker;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;

// @feature:workflow-recovery

/**
 * This bean regularly performs the following 3 actions:
 * <p>
 * 1. It updates the lastActive field of the EngineNode instance linked to the current node
 * 2. It makes sure that there is always 1 master node
 * 3. If this node is the master node, it starts health checks to restore hanging workflow state
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class EngineHealthBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineHealthBean.class);

  @Resource
  private TimerService timerService;
  @EJB
  private RestoreEnginesLocal restoreEngines;
  @EJB
  private CheckEngineHealthLocal checkEngineHealth;
  @EJB
  private ShutdownEnginesTaskLocal shutdownEngines;

  private Timer recoveryTimer;

  @PostConstruct
  void postConstruct() {
    LOGGER.info("Initializing Engine Recovery service");
    new EngineNodeUpdater(Context.emptyContext()).registerEngineNode();

    long offset = EngineNodeConfig.getHealthCheckOffset();
    recoveryTimer = timerService.createIntervalTimer(offset, EngineNodeConfig.getHealthCheckInterval(), new TimerConfig(null, false));
  }

  @Timeout
  @SuppressWarnings("unused")
  private void startChecks() {
    Context context = getContext();

    EngineNodeMasterFailOverChecker.Result masterCheck = new EngineNodeMasterFailOverChecker(context).checkIfThisNodeCanBeMaster();

    EngineNodeContext engineNodeContext = context
        .getContext(EngineNodeContext.class).getValue();

    switch (engineNodeContext.getStatus()) {
      case RECOVERING:
        if (masterCheck.isMaster()) {
          recoverEngines(context);
        } else {
          EngineNodeMasterStatusViewer.Result masterStatus = new EngineNodeMasterStatusViewer(context).getMasterStatus();
          if (masterStatus.isFound() && !masterStatus.isRecovering()) {
            updateEngineNode(context);
          }
        }
        return;
      case READY:
      case ACTIVE:
      case NOT_RESPONDING:
        updateEngineNode(context);
        if (masterCheck.isMaster()) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Performing Engine Health Check, node='" + engineNodeContext.getEngineNode().getName() + "'");
          }
          checkEngineHealth(context);
        }
        return;
      case UNEXPECTED_SHUTDOWN:
      case NOT_MAPPED:
        // Do nothing
    }
  }

  private void recoverEngines(Context context) {
    EngineNodeContext engineNodeContext = context.getContext(EngineNodeContext.class).getValue();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Performing Engine Recovery, node='" + engineNodeContext.getEngineNode().getName() + "'");
    }
    DataRef engineNode = engineNodeContext.getEngineNode();
    TaskResult<Void> taskResult = restoreEngines.performOnTarget(context.withParameter(engineNode));
    if (taskResult.isSuccess()) {
      new EngineNodeUpdater(context).activateEngineNodes();
    }
  }

  private void checkEngineHealth(Context context) {
    EngineNodeContext engineNodeContext = context.getContext(EngineNodeContext.class).getValue();
    DataRef engineNode = engineNodeContext.getEngineNode();
    TaskResult<Void> result = checkEngineHealth.performOnTarget(context.withParameter(engineNode));
    if (result.isError()) {
      LOGGER.warn("Check Engine Health task was not successful");
    }
  }

  private void updateEngineNode(Context context) {
    EngineNodeContext engineNodeContext = context.getContext(EngineNodeContext.class).getValue();
    LOGGER.debug("Master has already recovered, skipping Recovery step, node='" + engineNodeContext.getEngineNode().getName() + "'");
    new EngineNodeUpdater(context).updateLastActive();
  }


  @PreDestroy
  void preDestroy() {
    Context context = getContext();
    EngineNodeContext engineNodeContext = context.getContext(EngineNodeContext.class).getValue();
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Stopping Engine Recovery service, node='" + engineNodeContext.getEngineNode().getName() + "'");
    }
    if (recoveryTimer != null) {
      try {
        recoveryTimer.cancel();
      } catch (Exception e) {
        LOGGER.debug("Error when shutting down timer", e);
      }
    }

    TaskResult<Void> result = shutdownEngines.performOnTarget(context.withParameter(engineNodeContext.getEngineNode()));
    if (result.isError()) {
      LOGGER.warn("Shut down Engines task was not successful");
    }
    new EngineNodeUpdater(context).deregisterNode();
  }

  private Context getContext() {
    Context context = Context.emptyContext();
    EngineNodeContext engineNodeContext = new EngineNodeContextRetriever(context).retrieveEngineNodeContext();
    return context.extend(engineNodeContext);
  }
}
