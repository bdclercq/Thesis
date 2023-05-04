package net.democritus.wfe;

import net.democritus.sys.Context;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.TaskResult;
import net.democritus.sys.UserContext;
import net.democritus.workflow.EngineNodeConfig;
import net.democritus.workflow.StartAllEnginesTaskLocal;
import net.democritus.workflow.WorkflowDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Random;

import static net.democritus.wfe.EngineNodeState.ACTIVE;
import static net.democritus.wfe.EngineNodeState.READY;

// @feature:engine-service-automatic-startup

/**
 * This bean regularly checks for engines to start or stop.
 * If this bean is included in the deployed artifact, the application will automatically manage its own engines.
 */
@Startup
@Singleton
public class EngineStarterBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineStarterBean.class);

  @EJB
  private StartAllEnginesTaskLocal startAllEnginesTaskLocal;
  @Resource
  private TimerService timerService;

  @PostConstruct
  void postConstruct() {
    LOGGER.info("Initialising Engine Starter service");

    long offset = EngineNodeConfig.getEngineStarterOffset();
    timerService.createIntervalTimer(offset, EngineNodeConfig.getEngineStarterInterval(), new TimerConfig(null, false));
  }


  @Timeout
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  @SuppressWarnings("unused")
  private void startAllEngines() {
    Context context = getContext();
    EngineNodeContext engineNodeContext = context
        .getContext(EngineNodeContext.class).getValue();

    if (engineNodeContext.getStatus() == READY || engineNodeContext.getStatus() == ACTIVE) {
      LOGGER.debug("Running engine starter, node='" + engineNodeContext.getEngineNode().getName()
          + "', status='" + engineNodeContext.getStatus().getStatus() + "'");
      TaskResult<Void> taskResult = startAllEnginesTaskLocal.perform(context.withParameter(new WorkflowDetails()));
      if (taskResult.isError()) {
        LOGGER.error("Failure occurred when starting/stopping engines");
      }
    }
  }

  private Context getContext() {
    Context context = Context.emptyContext();
    EngineNodeContext engineNodeContext = new EngineNodeContextRetriever(context).retrieveEngineNodeContext();
    return context.extend(engineNodeContext);
  }
}
