package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.democritus.workflow.WorkflowDetails;
import net.democritus.workflow.WorkflowLocalAgent;
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
import net.democritus.wfe.EngineNodeContext;

import net.democritus.wfe.EngineServiceLocal;
import net.democritus.wfe.TimerHandlerLocal;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.democritus.properties.RuntimeProperties;
import net.democritus.wfe.EngineServiceFindAllEngineServicesDetails;
import net.democritus.wfe.TimerOptions;
import net.democritus.wfe.EngineNodeServiceFindByEngineServiceEq_StatusNeDetails;
import net.democritus.wfe.EngineNodeServiceLocal;
import net.democritus.wfe.EngineNodeServiceState;
import net.democritus.wfe.EngineServiceDetails;
import net.palver.util.Options;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task StartAllEnginesTask.
 */

@Stateless
@Remote(StartAllEnginesTaskRemote.class)
@Local(StartAllEnginesTaskLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class StartAllEnginesTaskBean implements StartAllEnginesTaskRemote, StartAllEnginesTaskLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StartAllEnginesTaskBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  @EJB
  private EngineServiceLocal engineServiceLocal;
  @EJB
  private TimerHandlerLocal timerHandlerLocal;
  @EJB
  private EngineNodeServiceLocal engineNodeServiceLocal;
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> performOnTarget(ParameterContext<DataRef> targetParameter) {
    WorkflowLocalAgent workflowAgent = WorkflowLocalAgent.getWorkflowAgent(targetParameter.getContext());
    // anchor:get-instance:start
    CrudsResult<WorkflowDetails> result = workflowAgent.getProjection(new ProjectionRef("details", targetParameter.getValue()));
    // anchor:get-instance:end

    if (result.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to retrieve target instance for 'Workflow'"
        );
      }
      return TaskResult.error();
    }
    return perform(targetParameter.construct(result.getValue()));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> perform(ParameterContext<WorkflowDetails> targetParameter) {

    TaskResult<Void> taskResult = null;

    // @anchor:prePerform:start
    // @anchor:prePerform:end
    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
    SearchResult<EngineServiceDetails> searchResult = findEngines(targetParameter);
    if (searchResult.isError()) {
      return TaskResult.error();
    }
    if (searchResult.getTotalNumberOfItems() == 0) {
      return TaskResult.success();
    }

    taskResult = startEngines(searchResult, targetParameter.getContext());
      // anchor:custom-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform StartAllEnginesTask on WorkflowDetails", e
        );
      }
    }

    // @anchor:postPerform:start
    // @anchor:postPerform:end
    // anchor:custom-postPerform:start
    // anchor:custom-postPerform:end

    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  private SearchResult<EngineServiceDetails> findEngines(ParameterContext<WorkflowDetails> targetParameter) {
    EngineServiceFindAllEngineServicesDetails finder = new EngineServiceFindAllEngineServicesDetails();
    SearchDetails<EngineServiceFindAllEngineServicesDetails> searchDetails = SearchDetails.fetchAll(finder);
    searchDetails.setProjection("details");
    return engineServiceLocal.find(ParameterContext.create(targetParameter.getUserContext(), searchDetails));
  }

  private TaskResult<Void> startEngines(SearchResult<EngineServiceDetails> searchResult, Context context) {
    TaskResult<Void> taskResult = TaskResult.success();

    double delayTime = 10L;
    for (EngineServiceDetails engine : searchResult.getResults()) {
      int numberOfEngineSlotsLeft = getNumberOfEngineSlotsLeft(engine, context);
      if (numberOfEngineSlotsLeft > 0) {
        delayTime += Math.PI / 2;
        TaskResult<Void> result = startEngine(engine, delayTime);
        if (result.isError()) {
          taskResult = result;
        }
      } else if (numberOfEngineSlotsLeft < 0) {
        TaskResult<Void> result = stopEngine(engine);
        if (result.isError()) {
          logger.warn("Failed to stop engineService name=" + engine.getName());
        }
      }
    }

    return taskResult;
  }

  private int getNumberOfEngineSlotsLeft(EngineServiceDetails engine, Context context) {
    Integer maximumNumberOfNodes = engine.getMaximumNumberOfNodes();
    if (maximumNumberOfNodes == null || maximumNumberOfNodes < 0 || isSingleNodeApplication()) {
      return Integer.MAX_VALUE;
    }
    SearchResult<Object> searchResult = findActiveEngineNodesForService(engine, context);
    if (searchResult.isError()) {
      return 0;
    }
    return maximumNumberOfNodes - searchResult.getTotalNumberOfItems();
  }

  private SearchResult<Object> findActiveEngineNodesForService(EngineServiceDetails engine, Context context) {
    EngineNodeServiceFindByEngineServiceEq_StatusNeDetails finder = new EngineNodeServiceFindByEngineServiceEq_StatusNeDetails();
    finder.setEngineService(engine.getDataRef());
    finder.setStatus(EngineNodeServiceState.NOT_RESPONDING.getStatus());

    SearchDetails<EngineNodeServiceFindByEngineServiceEq_StatusNeDetails> searchDetails = SearchDetails.fetchAll(finder);
    return engineNodeServiceLocal.find(context.withParameter(searchDetails));
  }

  private TaskResult<Void> startEngine(EngineServiceDetails engine, double delayTime) {
    TimerOptions timerOptions = new TimerOptions();
    timerOptions.setDelayTime(delayTime);
    Integer result = timerHandlerLocal.startTimer(engine.getName(), timerOptions);
    return result == -1 ?
        TaskResult.<Void>error() :
        TaskResult.success();
  }

  private TaskResult<Void> stopEngine(EngineServiceDetails engine) {
    Integer result = timerHandlerLocal.stopTimer(engine.getName());
    return result == -1 ?
        TaskResult.<Void>error() :
        TaskResult.success();
  }

  private boolean isSingleNodeApplication() {
    return RuntimeProperties.getRuntimeProperties().getEjbVersion().equals("ejb3.0");
  }
  // anchor:custom-methods:end

}

