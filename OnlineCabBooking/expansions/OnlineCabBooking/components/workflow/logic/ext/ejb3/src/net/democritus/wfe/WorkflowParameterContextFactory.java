package net.democritus.wfe;

import net.democritus.sys.Context;
import net.democritus.sys.ElementRef;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.workflow.*;
import net.democritus.workflow.StateTaskDetails;
import net.democritus.workflow.WorkflowDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

// @feature:workflow
public class WorkflowParameterContextFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowParameterContextFactory.class);

  private static final boolean USE_CONTEXT_CONSTRUCTOR = flowParameterContextHasContextConstructor();

  private final ParameterContext<?> parameterContext;
  private final List<StateTaskDetails> taskChain;
  private final WorkflowDetails workflow;
  private final RunId runId;

  private FlowProcessingContext<StateTaskDetails> processingContext;

  public WorkflowParameterContextFactory(ParameterContext<WorkflowDetails> workflowParameter, List<StateTaskDetails> taskChain, RunId runId) {
    this.parameterContext = workflowParameter;
    this.taskChain = taskChain;
    workflow = workflowParameter.getValue();
    this.runId = runId;
  }

  public void init() {
    String componentName = workflow.getComponentName();
    String workflowName = workflow.getClassName();

    ElementRef flowElementRef = new ElementRef(componentName, workflowName);
    ElementRef targetElementRef = new ElementRef(componentName, workflow.getTarget());

    String taskImplementation = null;
    String taskParams = null;

    this.processingContext = new FlowProcessingContext<StateTaskDetails>(
        runId,
        taskImplementation,
        taskParams,
        flowElementRef,
        targetElementRef,
        workflow.getSequencingStrategy(),
        taskChain
    );
  }

  public <T> FlowParameterContext<T, StateTaskDetails> createContext(T value) {
    if (processingContext == null) {
      init();
    }
    if (USE_CONTEXT_CONSTRUCTOR) {
      return new FlowParameterContext<T, StateTaskDetails>(
          value,
          parameterContext.getContext(),
          processingContext);
    }
    return new FlowParameterContext<T, StateTaskDetails>(
        value,
        parameterContext.getUserContext(),
        processingContext);
  }

  // Constructor with Context is provided by nsx-shared-common:2019.7.1
  private static boolean flowParameterContextHasContextConstructor() {
    Constructor<?>[] declaredConstructors = FlowParameterContext.class.getDeclaredConstructors();
    for (Constructor<?> constructor : declaredConstructors) {
      Class<?>[] parameterTypes = constructor.getParameterTypes();
      if (parameterTypes.length == 3
          && parameterTypes[1].equals(Context.class)
          && parameterTypes[2].equals(FlowProcessingContext.class)) {
        return true;
      }
    }
    return false;
  }
}
