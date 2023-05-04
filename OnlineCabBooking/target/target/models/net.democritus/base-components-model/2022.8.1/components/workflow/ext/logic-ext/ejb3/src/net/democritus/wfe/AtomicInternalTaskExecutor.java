package net.democritus.wfe;

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ElementRef;
import net.democritus.sys.TaskExecutor;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.sys.TaskResult;
import net.democritus.sys.workflow.TaskParameterContext;
import net.democritus.state.StateTransitioner;
import net.democritus.state.StateTransitionerFactory;
import net.democritus.state.TaskUpdate;
import net.democritus.workflow.StateTaskDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

// @feature:task-transactions
public class AtomicInternalTaskExecutor<RESULT> implements TaskExecutor<RESULT, StateTaskDetails> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AtomicInternalTaskExecutor.class);

  private StateTransitioner stateTransitioner;
  private UserTransaction userTransaction;

  public TaskResult<RESULT> executeTask(TaskParameterContext<DataRef, StateTaskDetails> taskParameters) {
    lookupUserTransaction();

    try {
      return doExecuteTask(taskParameters);
    } catch (Exception e) {
      LOGGER.error("Error when executing task", e);
      return TaskResult.notExecuted();
    }
  }

  private TaskResult<RESULT> doExecuteTask(TaskParameterContext<DataRef, StateTaskDetails> parameter) throws TaskExecutionException {
    stateTransitioner = getStateTransitioner(parameter);
    DataRef target = parameter.getValue();

    TaskResult<Void> prepareResult = prepareTask(parameter);
    if (prepareResult.isError()) {
      return TaskResult.notExecuted();
    }

    TaskResult<RESULT> taskResult = performTask(parameter);
    if (taskResult.isError()) {
      finalizeFailedTask(parameter.construct(new TaskUpdate(target, taskResult)));
    }
    return taskResult;
  }

  private void lookupUserTransaction() {
    if (userTransaction == null) {
      try {
        InitialContext ic = new InitialContext();
        String jndiName = "java:comp/UserTransaction";
        userTransaction = (UserTransaction) ic.lookup(jndiName);
      } catch (Exception e) {
        throw new TaskExecutionException("Failed to lookup UserTransaction", e);
      }
    }
  }

  private TaskResult<Void> prepareTask(TaskParameterContext<DataRef, StateTaskDetails> parameter) {
    long start = System.currentTimeMillis();
    try {
      userTransaction.begin();
    } catch (Exception e) {
      throw new TaskExecutionException("Could not start transaction", e);
    }

    try {
      CrudsResult<Void> preparationResult = stateTransitioner.startTransition(parameter);

      if (preparationResult.isSuccess()) {
        userTransaction.commit();
        LOGGER.debug("Task preparation complete, duration=" + (System.currentTimeMillis() - start));
        return TaskResult.success();
      }
    } catch (Exception e) {
      LOGGER.error("Task perform failed due to exception", e);
    }

    try {
      if (userTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
        LOGGER.debug("Rolling back task prepare");
        userTransaction.rollback();
      }
    } catch (Exception e) {
      LOGGER.error("Failed to rollback transaction", e);
    }
    return TaskResult.error();
  }

  private TaskResult<RESULT> performTask(TaskParameterContext<DataRef, StateTaskDetails> parameter) {
    TaskResult<RESULT> taskResult;

    long start = System.currentTimeMillis();

    TaskPerformerByDataRef<RESULT> taskPerformer = getTaskPerformer(parameter);

    try {
      userTransaction.begin();
    } catch (Exception e) {
      LOGGER.error("Could not start transaction", e);
      return TaskResult.notExecuted();
    }

    try {
      taskResult = taskPerformer.performOnTarget(parameter);

      LOGGER.debug("Task perform implementation complete, duration=" + (System.currentTimeMillis() - start));

      if (taskResult.isSuccess()) {
        long startEndState = System.currentTimeMillis();
        TaskUpdate taskUpdate = new TaskUpdate(parameter.getValue(), taskResult);
        setEndState(parameter.construct(taskUpdate));
        LOGGER.debug("Task set end state complete, duration=" + (System.currentTimeMillis() - startEndState));
      } else {
        LOGGER.debug("Task failed");
      }

      if (taskResult.isSuccess()) {
        userTransaction.commit();
        LOGGER.debug("Task perform + set end state complete, duration=" + (System.currentTimeMillis() - start));
        return taskResult;
      } else {
        LOGGER.debug("Task completion failed");
      }
    } catch (Exception e) {
      taskResult = TaskResult.error();
      LOGGER.error("Task failed due to exception", e);
    }

    try {
      if (userTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
        LOGGER.debug("Rolling back task");
        userTransaction.rollback();
      }
    } catch (Exception e) {
      LOGGER.error("Failed to rollback transaction", e);
    }
    return taskResult;
  }

  private void setEndState(TaskParameterContext<TaskUpdate, StateTaskDetails> parameter) {
    CrudsResult<Void> result = stateTransitioner.endTransition(parameter);
    if (result.isError()) {
      throw new TaskExecutionException("Set end state failed");
    }
  }

  private void finalizeFailedTask(TaskParameterContext<TaskUpdate, StateTaskDetails> parameter) {
    long start = System.currentTimeMillis();

    try {
      userTransaction.begin();
    } catch (Exception e) {
      LOGGER.error("Could not start transaction", e);
      return;
    }

    try {
      CrudsResult<Void> finalisationResult = stateTransitioner.failTransition(parameter);

      if (finalisationResult.isSuccess()) {
        userTransaction.commit();
        LOGGER.debug("Task finalize failed complete, duration=" + (System.currentTimeMillis() - start));
        return;
      } else {
        LOGGER.error("failed to set error state");
      }
    } catch (Exception e) {
      LOGGER.error("failed to set error state due to exception", e);
    }

    DataRef target = parameter.getValue().getTarget();
    LOGGER.error("Failed to set state after task failure for target with id " + target.getId() + ". Workflow may hang on interim state.");
    try {
      if (userTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
        LOGGER.debug("Rolling back transaction");
        userTransaction.rollback();
      }
    } catch (Exception e) {
      LOGGER.error("Failed to rollback transaction", e);
    }
  }

  private TaskPerformerByDataRef<RESULT> getTaskPerformer(TaskParameterContext<?, StateTaskDetails> taskParameter) {
    ElementRef taskElement = taskParameter.getProcessingContext().getTaskElement();

    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI(taskElement.getComponentName());

      String localBean = componentJNDI.getTaskLocalName(taskElement.getElementName());
      return componentJNDI.lookupLocal(localBean);
    } catch (Exception e) {
      throw new TaskExecutionException("Could not find task performer for task element [" + taskElement.getQualifiedName() + "]", e);
    }
  }


  private StateTransitioner getStateTransitioner(TaskParameterContext<?, StateTaskDetails> taskParameter) {
    ElementRef workflowRef = taskParameter.getProcessingContext().getWorkflow();
    ElementRef taskElement = taskParameter.getProcessingContext().getTaskElement();

    try {
      StateTransitionerFactory factory = new StateTransitionerFactory();
      return factory.makeStateTransitioner(workflowRef);
    } catch (Exception e) {
      throw new TaskExecutionException("Could not load StateTransitioner for task element '" + taskElement.getQualifiedName() + "'", e);
    }
  }
}
