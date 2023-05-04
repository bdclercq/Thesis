package net.democritus.wfe.action;

import net.democritus.sys.FlowResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FlowJsonResult {

  private boolean success;
  private int tasksSucceeded;
  private int tasksFailed;
  private int tasksNotExecuted;
  private int tasksProcessedInTotal;
  private List<String> errors = new ArrayList<>();

  public static FlowJsonResult from(FlowResult<?> flowResult, Collection<String> errors) {
    FlowJsonResult jsonResult = new FlowJsonResult();
    jsonResult.success = flowResult.isSuccess();
    jsonResult.tasksSucceeded = flowResult.getNumberOfSuccessfulTasks();
    jsonResult.tasksFailed = flowResult.getNumberOfFailedTasks();
    jsonResult.tasksNotExecuted = flowResult.getNumberOfTasksNotExecuted();
    jsonResult.tasksProcessedInTotal = flowResult.getNumberOfProcessedTasks();
    errors.addAll(errors);
    return jsonResult;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getTasksSucceeded() {
    return tasksSucceeded;
  }

  public void setTasksSucceeded(int tasksSucceeded) {
    this.tasksSucceeded = tasksSucceeded;
  }

  public int getTasksFailed() {
    return tasksFailed;
  }

  public void setTasksFailed(int tasksFailed) {
    this.tasksFailed = tasksFailed;
  }

  public int getTasksNotExecuted() {
    return tasksNotExecuted;
  }

  public void setTasksNotExecuted(int tasksNotExecuted) {
    this.tasksNotExecuted = tasksNotExecuted;
  }

  public int getTasksProcessedInTotal() {
    return tasksProcessedInTotal;
  }

  public void setTasksProcessedInTotal(int tasksProcessedInTotal) {
    this.tasksProcessedInTotal = tasksProcessedInTotal;
  }
}
