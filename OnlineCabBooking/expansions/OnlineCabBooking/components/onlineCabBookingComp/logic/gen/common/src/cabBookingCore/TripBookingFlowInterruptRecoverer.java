package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.TaskResult;
import net.democritus.state.TaskUpdate;
import net.democritus.state.TaskInterruptStateRecoverer;
import net.democritus.sys.search.SearchDetails;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.RunId;
import net.democritus.wfe.StateTaskParameterContextFactory;
import net.democritus.wfe.WorkflowParameterContextFactory;
import net.democritus.workflow.StateTaskDetails;
import net.democritus.workflow.StateTaskLocalAgent;
import net.democritus.workflow.WorkflowDetails;

import cabBookingCore.TripBookingLocalAgent;
import cabBookingCore.TripBookingFindByStatusEqDetails;

// anchor:imports:start
// anchor:imports:end
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

public class TripBookingFlowInterruptRecoverer implements net.democritus.state.StateRecoverer<WorkflowDetails> {
  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingFlowInterruptRecoverer.class);
  // @anchor:variables:end

  public void recover(ParameterContext<WorkflowDetails> workflowContext) {
    List<StateTaskDetails> stateTasks = getStateTasks(workflowContext);
    findAndFixInterruptedInstances(stateTasks, workflowContext);
  }

  private List<StateTaskDetails> getStateTasks(ParameterContext<WorkflowDetails> workflowContext) {
    WorkflowDetails workflow = workflowContext.getValue();

    StateTaskLocalAgent stateTaskAgent = StateTaskLocalAgent.getStateTaskAgent(workflowContext.getContext());
    SearchResult<StateTaskDetails> stateTasksResult = stateTaskAgent.getStateTasksByWorkflowId(workflow.getId());
    if (stateTasksResult.isError()) {
      logger.error("Failed to find stateTasks for workflow " + workflow.getName());
      return Collections.emptyList();
    }

    return stateTasksResult.getResults();
  }

  private void findAndFixInterruptedInstances(List<StateTaskDetails> stateTasks, ParameterContext<WorkflowDetails> workflowContext) {
    WorkflowParameterContextFactory workflowContextFactory = new WorkflowParameterContextFactory(workflowContext, stateTasks, new RunId());
    workflowContextFactory.init();
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(workflowContext.getContext());

    int nbInstancesRecovered = 0;
    for (StateTaskDetails stateTask : stateTasks) {
      List<DataRef> tripBookingList = findInterruptedInstances(tripBookingAgent, stateTask);
      recoverInstances(tripBookingList, stateTask, workflowContextFactory);
      nbInstancesRecovered += tripBookingList.size();
    }

    if (nbInstancesRecovered > 0) {
      if (logger.isInfoEnabled()) {
        logger.info(
            "Recovered instances for workflow='" + workflowContext.getValue().getName() + "', amount=" + nbInstancesRecovered
        );
      }
    }
  }

  private List<DataRef> findInterruptedInstances(TripBookingLocalAgent tripBookingAgent, StateTaskDetails stateTask) {
    TripBookingFindByStatusEqDetails finder = new TripBookingFindByStatusEqDetails();
    finder.setStatus(stateTask.getInterimState());
    SearchDetails<TripBookingFindByStatusEqDetails> searchDetails = SearchDetails.fetchAllDataRef(finder);
    searchDetails.setSkipCount(true);

    SearchResult<DataRef> searchResult = tripBookingAgent.find(searchDetails);
    if (searchResult.isError()) {
      logger.error("Failed to find TripBookingFlow Interrupted instances with state " + stateTask.getInterimState());
      return Collections.emptyList();
    }

    return searchResult.getResults();
  }

  private void recoverInstances(List<DataRef> tripBookingList, StateTaskDetails stateTask, WorkflowParameterContextFactory workflowContextFactory) {
    FlowParameterContext<StateTaskDetails, StateTaskDetails> stateTaskContext = workflowContextFactory.createContext(stateTask);
    StateTaskParameterContextFactory contextFactory = new StateTaskParameterContextFactory(stateTaskContext);
    contextFactory.init();

    // anchor:recover-before:start
    // anchor:recover-before:end
    try {
      for (DataRef tripBooking : tripBookingList) {
        TaskUpdate taskUpdate = new TaskUpdate(tripBooking, TaskResult.error());
        TripBookingFlowStateTransitioner transition = new TripBookingFlowStateTransitioner();
        new TaskInterruptStateRecoverer<StateTaskDetails>().recover(contextFactory.createContext(taskUpdate), transition);
      }
    } catch (Exception e) {
      logger.error("recovery failed for " + stateTask.getName(), e);
    }
    // anchor:recover-after:start
    // anchor:recover-after:end
  }
}
