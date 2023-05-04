package net.democritus.wfe;

import net.democritus.state.StateViewer;
import net.democritus.state.StateViewerFactory;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.TaskExecutor;
import net.democritus.sys.TaskResult;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.FlowProcessingContext;
import net.democritus.sys.workflow.TaskParameterContext;
import net.democritus.workflow.StateTaskDetails;

/**
 * The greedy sequencing strategy will attempt to execute each stateTask in the taskChain if the current status is equal
 * to the beginState of that stateTask. This will result in each instance being run through the entire workflow as far
 * as possible before continuing with the next instance.
 * The algorithm will iterate over the taskChain only once, so that loops are avoided.
 *
 * @param <RESULT> not used
 */
// @feature:workflow-sequencing
public class GreedyTaskSequencer<RESULT> implements TaskSequencer<RESULT> {

  @Override
  public FlowResult<RESULT> sequence(FlowParameterContext<DataRef, StateTaskDetails> flowParameters) {
    FlowProcessingContext<StateTaskDetails> processingContext = flowParameters.getProcessingContext();
    TaskExecutorFactory taskExecutorFactory = new TaskExecutorFactory();

    StateViewer<?> stateViewer = new StateViewerFactory().makeStateViewer(processingContext.getWorkflow());

    DataRef target = flowParameters.getValue();


    FlowResult<RESULT> flowResult = FlowResult.success(null);
    for (StateTaskDetails stateTaskDetails : processingContext.getTaskChain()) {
      CrudsResult<String> status = stateViewer.getStatus(flowParameters);

      if (status.isError()) {
        return flowResult;
      }

      if (status.getValue().equals(stateTaskDetails.getBeginState())) {
        TaskParameterContext<DataRef, StateTaskDetails> taskParameters = makeTaskParameterContext(flowParameters, target, stateTaskDetails);
        TaskExecutor<RESULT, StateTaskDetails> taskExecutor = taskExecutorFactory.makeTaskExecutor(taskParameters);
        TaskResult<RESULT> taskResult = taskExecutor.executeTask(taskParameters);
        flowResult.addTaskResult(taskResult);
      }
    }

    return flowResult;
  }

  private TaskParameterContext<DataRef, StateTaskDetails> makeTaskParameterContext(FlowParameterContext<DataRef, StateTaskDetails> flowParameters, DataRef target, StateTaskDetails stateTaskDetails) {
    StateTaskParameterContextFactory contextFactory = new StateTaskParameterContextFactory(flowParameters.construct(stateTaskDetails));
    contextFactory.init();
    return contextFactory.createContext(target);
  }

}
