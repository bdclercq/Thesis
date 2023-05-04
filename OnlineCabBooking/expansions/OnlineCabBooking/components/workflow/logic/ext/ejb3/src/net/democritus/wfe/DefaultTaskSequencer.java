package net.democritus.wfe;

import net.democritus.sys.DataRef;
import net.democritus.sys.FlowResult;
import net.democritus.sys.TaskExecutor;
import net.democritus.sys.TaskResult;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.FlowProcessingContext;
import net.democritus.sys.workflow.TaskParameterContext;
import net.democritus.workflow.StateTaskDetails;

/**
 * The default sequencing strategy will execute the first stateTask of the taskChain.
 * This will result in each stateTask being executed on each valid target before going to the next stateTask
 *
 * @param <RESULT> not used
 */
// @feature:workflow-sequencing
public class DefaultTaskSequencer<RESULT> implements TaskSequencer<RESULT> {

  @Override
  public FlowResult<RESULT> sequence(FlowParameterContext<DataRef, StateTaskDetails> flowParameters) {
    FlowProcessingContext<StateTaskDetails> processingContext = flowParameters.getProcessingContext();
    TaskExecutorFactory taskExecutorFactory = new TaskExecutorFactory();

    DataRef target = flowParameters.getValue();
    StateTaskDetails stateTaskDetails = processingContext.getTaskChain().get(0);

    TaskParameterContext<DataRef, StateTaskDetails> taskParameters = makeTaskParameterContext(flowParameters, target, stateTaskDetails);
    TaskExecutor<RESULT, StateTaskDetails> taskExecutor = taskExecutorFactory.makeTaskExecutor(taskParameters);
    TaskResult<RESULT> taskResult = taskExecutor.executeTask(taskParameters);

    FlowResult<RESULT> flowResult = FlowResult.success(null);
    flowResult.addTaskResult(taskResult);
    return flowResult;
  }

  private TaskParameterContext<DataRef, StateTaskDetails> makeTaskParameterContext(FlowParameterContext<DataRef, StateTaskDetails> flowParameters, DataRef target, StateTaskDetails stateTaskDetails) {
    StateTaskParameterContextFactory contextFactory = new StateTaskParameterContextFactory(flowParameters.construct(stateTaskDetails));
    contextFactory.init();
    return contextFactory.createContext(target);
  }

}
