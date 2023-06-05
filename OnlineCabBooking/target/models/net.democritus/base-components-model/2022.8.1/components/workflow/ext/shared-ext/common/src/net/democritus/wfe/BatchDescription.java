package net.democritus.wfe;

import net.democritus.sys.workflow.RunId;
import net.democritus.workflow.StateTaskDetails;
import net.democritus.workflow.WorkflowDetails;

import java.util.ArrayList;
import java.util.List;

// @feature:workflow
public class BatchDescription {

  private WorkflowDetails workflow;
  private EngineServiceDetailsWithoutRefs engine;
  private List<StateTaskDetails> taskChain;

  private RunId runId;
  private int batchSize;

  public WorkflowDetails getWorkflow() {
    return workflow;
  }

  public void setWorkflow(WorkflowDetails workflow) {
    this.workflow = workflow;
  }

  public EngineServiceDetailsWithoutRefs getEngine() {
    return engine;
  }

  public void setEngine(EngineServiceDetailsWithoutRefs engine) {
    this.engine = engine;
  }

  public List<StateTaskDetails> getTaskChain() {
    return taskChain;
  }

  public void setTaskChain(List<StateTaskDetails> taskChain) {
    this.taskChain = new ArrayList<StateTaskDetails>(taskChain);
  }

  public RunId getRunId() {
    return runId;
  }

  public void setRunId(RunId runId) {
    this.runId = runId;
  }

  public int getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(int batchSize) {
    this.batchSize = batchSize;
  }
}
