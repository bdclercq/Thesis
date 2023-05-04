package net.democritus.wfe;

import net.democritus.metadata.ComponentMetaDataRegister;
import net.democritus.metadata.TaskElementDef;
import net.democritus.metadata.TransactionType;
import net.democritus.sys.ElementRef;
import net.democritus.sys.TaskExecutor;
import net.democritus.sys.workflow.TaskParameterContext;
import net.democritus.sys.workflow.TaskProcessingContext;
import net.democritus.workflow.StateTaskDetails;
import net.palver.util.Options;

// @feature:task-transactions
public class TaskExecutorFactory {

  public <T> TaskExecutor<T, StateTaskDetails> makeTaskExecutor(TaskParameterContext<?, StateTaskDetails> parameter) {
    TaskProcessingContext processingContext = parameter.getProcessingContext();
    ElementRef taskElement = processingContext.getTaskElement();

    ComponentMetaDataRegister componentMetaDataRegister = ComponentMetaDataRegister.getComponentMetaDataRegister();
    Options.Option<TaskElementDef> taskElementDef = componentMetaDataRegister.getTaskElementDef(taskElement.getComponentName(), taskElement.getElementName());
    if (taskElementDef.isEmpty()) {
      throw new ProcessingException("Could not find taskElementDef for '" + taskElement.getQualifiedName() + "'");
    }

    TransactionType transactionType = taskElementDef.getValue().getTransactionType();

    try {
      return findExecutorClassFor(transactionType);
    } catch (Exception e) {
      throw new ProcessingException("Failed to find task executor class for '" + transactionType + "'");
    }

  }

  private <T> TaskExecutor<T, StateTaskDetails> findExecutorClassFor(TransactionType transactionType) {
    switch (transactionType) {
      case ATOMIC_INTERNAL: return new AtomicInternalTaskExecutor<T>();
      default: return new NoTransactionTaskExecutor<T>();
    }
  }
}
