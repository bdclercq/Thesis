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
import net.democritus.wfe.EngineServiceLocal;
import net.democritus.wfe.TimerHandlerLocal;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.democritus.wfe.EngineServiceFindAllEngineServicesDetails;
import net.democritus.wfe.EngineServiceInfo;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task StopAllEnginesTask.
 */

@Stateless
@Remote(StopAllEnginesTaskRemote.class)
@Local(StopAllEnginesTaskLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class StopAllEnginesTaskBean implements StopAllEnginesTaskRemote, StopAllEnginesTaskLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StopAllEnginesTaskBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
    @EJB
    private EngineServiceLocal engineServiceLocal;
    @EJB private TimerHandlerLocal timerHandlerLocal;
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
        taskResult = TaskResult.success();
        EngineServiceFindAllEngineServicesDetails findAllEngines = new EngineServiceFindAllEngineServicesDetails();
        SearchDetails<EngineServiceFindAllEngineServicesDetails> findAllEngineServicesDetailsSearchDetails = SearchDetails.fetchAll(findAllEngines);
        SearchResult<EngineServiceInfo> searchResult = engineServiceLocal.find(ParameterContext.create(targetParameter.getUserContext(), findAllEngineServicesDetailsSearchDetails));
        if (searchResult.isError()||searchResult.getTotalNumberOfItems()==0){
            return TaskResult.error();
        }
        for(EngineServiceInfo engine:searchResult.getResults()){
            Integer result = timerHandlerLocal.stopTimer(engine.getName());
            if(result== -1){
                taskResult = TaskResult.error();
            }
        }
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
            "Failed to perform StopAllEnginesTask on WorkflowDetails", e
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
  // anchor:custom-methods:end

}

