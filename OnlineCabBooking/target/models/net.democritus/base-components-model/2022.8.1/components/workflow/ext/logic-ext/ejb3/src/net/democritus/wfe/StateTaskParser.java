package net.democritus.wfe;

import net.democritus.sys.ElementRef;
import net.democritus.workflow.StateTaskDetails;

import java.util.regex.Pattern;

// @feature:workflow
public class StateTaskParser {
  private static final Pattern PROCESSOR_PATTERN = Pattern.compile("\\w+:\\w+");

  private final StateTaskDetails stateTaskDetails;

  private String taskComponentName;
  private String taskName;

  public StateTaskParser(StateTaskDetails stateTaskDetails) {
    this.stateTaskDetails = stateTaskDetails;
  }

  public void parse() throws IllegalStateException {
    parseProcessorString();
  }

  private void parseProcessorString() {
    String processor = stateTaskDetails.getProcessor();

    if (processor == null || !PROCESSOR_PATTERN.matcher(processor).matches()) {
      throw new IllegalStateException("Processor should be of form '<componentName>:<taskName>', but got '" + processor + "'");
    }

    String[] processorChunks = processor.split(":");
    taskComponentName = processorChunks[0];
    taskName = processorChunks[1];
  }

  public ElementRef getTaskElementRef() {
    return new ElementRef(
        taskComponentName,
        taskName
    );
  }

}
