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
import net.democritus.wfe.EngineServiceLocalAgent;
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
import net.democritus.sys.Context;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.democritus.workflow.EngineNodeServiceUpdater;
import net.democritus.workflow.EngineServiceUpdate;

import java.util.List;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task ShutdownEnginesTask.
 */

@Stateless
@Remote(ShutdownEnginesTaskRemote.class)
@Local(ShutdownEnginesTaskLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class ShutdownEnginesTaskBean implements ShutdownEnginesTaskRemote, ShutdownEnginesTaskLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ShutdownEnginesTaskBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  @EJB
  private TimerHandlerLocal timerHandlerLocal;
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> performOnTarget(ParameterContext<DataRef> targetParameter) {
    EngineServiceLocalAgent engineServiceAgent = EngineServiceLocalAgent.getEngineServiceAgent(targetParameter.getContext());
    // anchor:get-instance:start
    CrudsResult<EngineServiceDetails> result = engineServiceAgent.getProjection(new ProjectionRef("details", targetParameter.getValue()));
    // anchor:get-instance:end

    if (result.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to retrieve target instance for 'EngineService'"
        );
      }
      return TaskResult.error();
    }
    return perform(targetParameter.construct(result.getValue()));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> perform(ParameterContext<EngineServiceDetails> targetParameter) {

    TaskResult<Void> taskResult = null;

    // @anchor:prePerform:start
    // @anchor:prePerform:end
    // anchor:custom-prePerform:start
    Context context = targetParameter.getContext();
    // anchor:custom-prePerform:end

    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
      // anchor:custom-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform ShutdownEnginesTask on EngineServiceDetails", e
        );
      }
    }

    // @anchor:postPerform:start
    // @anchor:postPerform:end
    // anchor:custom-postPerform:start
    shutdownEngineServices(context);
    do {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    } while (isBusy(context));
    logger.info("All engineServices are done, ready to shut down");
    // anchor:custom-postPerform:end

    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  private boolean isBusy(Context context) {
    List<EngineNodeServiceDetails> engineNodeServices = getEngineNodeServices(context);
    for (EngineNodeServiceDetails service : engineNodeServices) {
      if (service.getStatusAsEnum() == EngineNodeServiceState.SHUTTING_DOWN) {
        logger.info("Waiting for engineService '" + service.getName() + "'");
        return true;
      }
    }
    return false;
  }

  private void shutdownEngineServices(Context context) {
    List<EngineNodeServiceDetails> services = getEngineNodeServices(context);
    for (EngineNodeServiceDetails service : services) {
      shutdownEngineService(context, service);
    }
  }

  private void shutdownEngineService(Context context, EngineNodeServiceDetails service) {
    if (service.getStatusAsEnum() == EngineNodeServiceState.WAITING) {
      try {
        timerHandlerLocal.stopTimer(service.getName());
      } catch (Exception ignored) {
      }
    } else if (service.getStatusAsEnum() == EngineNodeServiceState.WORKING) {
      logger.debug("EngineService '" + service.getName() + "' is still running, settings state to 'Shutting down'");
      EngineServiceUpdate update = new EngineServiceUpdate();
      update.setExpectedStatus(EngineNodeServiceState.WORKING);
      update.setStatus(EngineNodeServiceState.SHUTTING_DOWN);
      update.setNextRun(null);
      update.setEngineService(service.getEngineService());
      new EngineNodeServiceUpdater(context).updateEngineNodeService(update);
    }
  }

  private List<EngineNodeServiceDetails> getEngineNodeServices(Context context) {
    EngineNodeContext engineNodeContext = context.getContext(EngineNodeContext.class)
        .orElse(new EngineNodeContextRetriever(context).retrieveEngineNodeContext());

    EngineNodeServiceLocalAgent engineNodeServiceAgent = EngineNodeServiceLocalAgent.getEngineNodeServiceAgent(context);
    EngineNodeServiceFindByEngineNodeEqDetails finder = new EngineNodeServiceFindByEngineNodeEqDetails();
    finder.setEngineNode(engineNodeContext.getEngineNode());

    SearchDetails<EngineNodeServiceFindByEngineNodeEqDetails> searchDetails = SearchDetails.fetchAll(finder);
    searchDetails.setProjection("details");
    SearchResult<EngineNodeServiceDetails> searchResult = engineNodeServiceAgent.find(searchDetails);

    return searchResult.getResults();
  }
  // anchor:custom-methods:end

}

