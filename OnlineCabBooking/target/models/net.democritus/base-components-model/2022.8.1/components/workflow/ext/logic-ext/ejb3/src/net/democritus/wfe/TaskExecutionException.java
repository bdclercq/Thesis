package net.democritus.wfe;

// @feature:workflow
public class TaskExecutionException extends RuntimeException {
  public TaskExecutionException(String s) {
    super(s);
  }

  public TaskExecutionException(String s, Throwable throwable) {
    super(s, throwable);
  }
}
