package net.democritus.wfe;

import net.democritus.sys.Context;
import net.democritus.sys.ElementRef;
import net.democritus.sys.workflow.FlowParameterContext;
import net.democritus.sys.workflow.TaskParameterContext;
import net.democritus.sys.workflow.FlowProcessingContext;
import net.democritus.sys.workflow.TaskProcessingContext;
import net.democritus.workflow.StateTaskDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.regex.Pattern;

// @feature:workflow
public class StateTaskParameterContextFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(StateTaskParameterContextFactory.class);
  private static final Pattern JAVA_CLASS = Pattern.compile("(\\w+\\.)+([A-Z][\\w$]+)");

  private static final boolean USE_CONTEXT_CONSTRUCTOR = taskParameterContextHasContextConstructor();

  private final StateTaskDetails stateTask;
  private final FlowParameterContext<StateTaskDetails, StateTaskDetails> flowParameterContext;

  private TaskProcessingContext<StateTaskDetails> processingContext;

  public StateTaskParameterContextFactory(FlowParameterContext<StateTaskDetails, StateTaskDetails> parameter) {
    flowParameterContext = parameter;
    stateTask = parameter.getValue();
  }

  public void init() {
    FlowProcessingContext<StateTaskDetails> flowProcessingContext = flowParameterContext.getProcessingContext();

    StateTaskParser stateTaskParser = new StateTaskParser(this.stateTask);
    stateTaskParser.parse();

    ElementRef flowElementRef = flowProcessingContext.getWorkflow();
    ElementRef targetElementRef = flowProcessingContext.getTargetDataElement();
    ElementRef taskElementRef = stateTaskParser.getTaskElementRef();

    String taskImplementation = stateTask.getImplementation();
    String taskParams = stateTask.getParams();
    String beginState = stateTask.getBeginState();
    String interimState = stateTask.getInterimState();
    String endState = stateTask.getEndState();
    String failedState = stateTask.getFailedState();

    List<StateTaskDetails> taskChain = flowProcessingContext.getTaskChain();

    checkIsValidString(taskImplementation, "implementation");
    checkIsValidString(taskParams, "params");
    checkIsValidString(beginState, "beginState");
    checkIsValidString(interimState, "interimState");
    checkIsValidString(endState, "endState");
    checkIsValidString(failedState, "failedState");

    taskImplementation = cleanString(taskImplementation, "implementation");
    taskParams = cleanString(taskParams, "params");
    beginState = cleanString(beginState, "beginState");
    interimState = cleanString(interimState, "interimState");
    endState = cleanString(endState, "endState");
    failedState = cleanString(failedState, "failedState");

    if (!"none".equalsIgnoreCase(taskImplementation)) {
      checkIsValidJavaClass(taskImplementation, "implementation");
    }

    this.processingContext = new TaskProcessingContext<>(
        flowProcessingContext.getRunId(),
        taskImplementation,
        taskParams,
        flowElementRef,
        taskElementRef,
        targetElementRef,
        beginState,
        interimState,
        endState,
        failedState,
        taskChain,
        stateTask,
        flowProcessingContext.getSequencingStrategy()
    );
  }

  private String cleanString(String string, String fieldName) {
    String cleanString = string.trim();
    if (!cleanString.equals(string)) {
      LOGGER.warn(stateTask.getName() + ": field '" + fieldName + "' has leading or trailing spaces (value='" + string + "')");
    }
    return cleanString;
  }

  private void checkIsValidString(String string, String fieldName) {
    if (string == null || string.isEmpty()) {
      throw new IllegalStateException(stateTask.getName() + ": field '" + fieldName + "' should not be empty");
    }
  }

  private void checkIsValidJavaClass(String string, String fieldName) {
    if (!JAVA_CLASS.matcher(string).matches()) {
      throw new IllegalStateException(stateTask.getName() + ": field '" + fieldName + "' should be a valid java class");
    }
  }

  public <T> TaskParameterContext<T, StateTaskDetails> createContext(T value) {
    if (processingContext == null) {
      init();
    }
    if (USE_CONTEXT_CONSTRUCTOR) {
      return new TaskParameterContext<>(
          value,
          flowParameterContext.getContext(),
          processingContext);
    }
    return new TaskParameterContext<>(
        value,
        flowParameterContext.getUserContext(),
        processingContext);
  }

  // Constructor with Context is provided by nsx-shared-common:2019.7.1
  private static boolean taskParameterContextHasContextConstructor() {
    Constructor<?>[] declaredConstructors = TaskParameterContext.class.getDeclaredConstructors();
    for (Constructor<?> constructor : declaredConstructors) {
      Class<?>[] parameterTypes = constructor.getParameterTypes();
      if (parameterTypes.length == 3
          && parameterTypes[1].equals(Context.class)
          && parameterTypes[2].equals(TaskProcessingContext.class)) {
        return true;
      }
    }
    return false;
  }
}
