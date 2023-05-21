package cabBookingCore;

import java.util.Date;
import java.io.Serializable;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.workflow.TaskParameterContext;
import net.democritus.sys.workflow.TaskProcessingContext;
import net.democritus.sys.TaskResult;
import net.democritus.sys.IStateDef;
import net.democritus.state.StateUpdate;
import net.democritus.state.TaskUpdate;
import net.democritus.state.StateTransitioner;
import net.democritus.workflow.StateTaskDetails;
import net.palver.util.Options;

import static net.palver.util.Options.notNull;
import static net.palver.util.Options.none;

import cabBookingCore.TripBookingLocalAgent;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import cabBookingCore.TripBookingTaskStatusLocalAgent;
import cabBookingCore.TripBookingTaskStatusDetails;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

/**
 * Provides a number of methods to set the status field on a(n) TripBooking instance, based on a StateTask instance.
 */
@SuppressWarnings("unused")
public class TripBookingFlowStateTransitioner implements StateTransitioner<StateTaskDetails>, Serializable {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingFlowStateTransitioner.class);
  private DataRef taskStatusRef;
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /**
   * Sets the status of the target TripBooking instance to the interim state.
   *
   * @param parameter a parameterContext containing a dataRef to the target order instance
   *
   * @return  CrudsSucces if setting the status was successful
   *          CrudsError otherwise
   */
  @Override
  public CrudsResult<Void> startTransition(TaskParameterContext<DataRef, StateTaskDetails> parameter) {
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(parameter.getContext());
    DataRef tripBooking = parameter.getValue();
    TaskProcessingContext<StateTaskDetails> processingContext = parameter.getProcessingContext();

    StateUpdate stateUpdate = new StateUpdate();
    stateUpdate.setTarget(tripBooking);
    stateUpdate.setExpectedStatus(processingContext.getBeginState());
    stateUpdate.setTargetStatus(processingContext.getInterimState());

    // @anchor:interim-before:start
    // @anchor:interim-before:end
    // anchor:custom-interim-before:start
    // anchor:custom-interim-before:end

    if (logger.isDebugEnabled()) {
      logger.debug(
          "Setting interim state " + processingContext.getInterimState() + " on TripBooking with id " + tripBooking.getId()
      );
    }
    CrudsResult<Void> crudsResult = tripBookingAgent.compareAndSetStatus(stateUpdate);

    if (crudsResult.isError()) {
      // @anchor:interim-error:start
      // @anchor:interim-error:end
      // anchor:custom-interim-error:start
      // anchor:custom-interim-error:end
      return crudsResult.convertError();
    }

    // @anchor:interim-after:start
    if (logger.isDebugEnabled()) {
      logger.debug(
          "Creating TripBookingTaskStatus for TripBooking with id " + tripBooking.getId()
      );
    }
    CrudsResult<DataRef> taskStatusResult = createTaskStatus(parameter, processingContext.getInterimState());

    if (taskStatusResult.isError()) {
      return taskStatusResult.convertError();
    }
    taskStatusRef = taskStatusResult.getValue();
    // @anchor:interim-after:end
    // anchor:custom-interim-after:start
    // anchor:custom-interim-after:end

    return CrudsResult.success();
  }

  /**
   * Sets the status of the target TripBooking instance to the failed state.
   *
   * @param parameter a parameterContext containing a TaskUpdate
   *
   * @return  CrudsSucces if setting the status was successful
   *          CrudsError otherwise
   */
  @Override
  public CrudsResult<Void> failTransition(TaskParameterContext<TaskUpdate, StateTaskDetails> parameter) {
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(parameter.getContext());

    TaskUpdate taskUpdate = parameter.getValue();
    DataRef tripBooking = taskUpdate.getTarget();
    TaskProcessingContext<StateTaskDetails> processingContext = parameter.getProcessingContext();

    StateUpdate stateUpdate = new StateUpdate();
    stateUpdate.setTarget(tripBooking);
    stateUpdate.setExpectedStatus(processingContext.getInterimState());
    stateUpdate.setTargetStatus(processingContext.getFailedState());

    // @anchor:failed-before:start
    // @anchor:failed-before:end
    // anchor:custom-failed-before:start
    // anchor:custom-failed-before:end

    if (logger.isDebugEnabled()) {
      logger.debug(
          "Setting failed state " + processingContext.getFailedState() + " on TripBooking with id " + tripBooking.getId()
      );
    }
    CrudsResult<Void> crudsResult = tripBookingAgent.compareAndSetStatus(stateUpdate);

    if (crudsResult.isError()) {
      // @anchor:failed-error:start
      // @anchor:failed-error:end
      // anchor:custom-failed-error:start
      // anchor:custom-failed-error:end
      return crudsResult.convertError();
    }

    // @anchor:failed-after:start
    if (taskStatusRef != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Updating TripBookingTaskStatus for TripBooking with id " + tripBooking.getId()
        );
      }
      CrudsResult<DataRef> taskStatusCrudsResult = updateTaskStatus(parameter, processingContext.getFailedState());
      if (taskStatusCrudsResult.isError()) {
        return taskStatusCrudsResult.convertError();
      }
    }
    // @anchor:failed-after:end
    // anchor:custom-failed-after:start
    // anchor:custom-failed-after:end

    return CrudsResult.success();
  }

  /**
   * Sets the status of the target TripBooking instance to the end state.
   *
   * @param parameter a parameterContext containing a TaskUpdate
   *
   * @return  CrudsSucces if setting the status was successful
   *          CrudsError otherwise
   */
  @Override
  public CrudsResult<Void> endTransition(TaskParameterContext<TaskUpdate, StateTaskDetails> parameter) {
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(parameter.getContext());

    TaskUpdate taskUpdate = parameter.getValue();
    TaskResult taskResult = taskUpdate.getTaskResult();
    DataRef tripBooking = taskUpdate.getTarget();
    TaskProcessingContext<StateTaskDetails> processingContext = parameter.getProcessingContext();

    Options.Option<String> taskEndState = getEndState(parameter.construct(taskResult));
    if (taskEndState.isEmpty()) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "No endState defined for " + processingContext.getStateTask().getName()
        );
      }
      return CrudsResult.success();
    }

    StateUpdate stateUpdate = new StateUpdate();
    stateUpdate.setTarget(tripBooking);
    stateUpdate.setExpectedStatus(processingContext.getInterimState());
    stateUpdate.setTargetStatus(taskEndState.getValue());

    // @anchor:end-before:start
    // @anchor:end-before:end
    // anchor:custom-end-before:start
    // anchor:custom-end-before:end

    if (logger.isDebugEnabled()) {
      logger.debug(
          "Setting end state " + taskEndState.getValue() + " on TripBooking with id " + tripBooking.getId()
      );
    }
    CrudsResult<Void> crudsResult = tripBookingAgent.compareAndSetStatus(stateUpdate);

    if (crudsResult.isError()) {
      // @anchor:end-error:start
      // @anchor:end-error:end
      // anchor:custom-end-error:start
      // anchor:custom-end-error:end
      return crudsResult.convertError();
    }

    // @anchor:end-after:start
    if (taskStatusRef != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Updating TripBookingTaskStatus for TripBooking with id " + tripBooking.getId()
        );
      }
      CrudsResult<DataRef> updateResult = updateTaskStatus(parameter, taskEndState.getValue());
      if (updateResult.isError()) {
        return updateResult.convertError();
      }
    }
    // @anchor:end-after:end
    // anchor:custom-end-after:start
    // anchor:custom-end-after:end

    return CrudsResult.success();
  }

  private Options.Option<String> getEndState(TaskParameterContext<TaskResult, StateTaskDetails> parameter) {
    TaskResult taskResult = parameter.getValue();
    // @anchor:get-end-state:start
    // @anchor:get-end-state:end
    // anchor:custom-get-end-state:start
    // anchor:custom-get-end-state:end
    if (taskResult.isNonBranchingTask()) {
      return notNull(parameter.getProcessingContext().getEndState());
    } else {
      return getBranchingTaskEndState(taskResult);
    }
  }

  private Options.Option<String> getBranchingTaskEndState(TaskResult taskResult) {
    Object branchResult = taskResult.getValue();
    if (branchResult instanceof IStateDef) {
      return notNull(((IStateDef) branchResult).getStatus());
    } else {
      return none();
    }
  }

  /**
   * Returns the status of the target TripBooking instance to the begin state.
   *
   * @param parameter a parameterContext containing a TaskUpdate
   *
   * @return  CrudsSucces if setting the status was successful
   *          CrudsError otherwise
   */
  @Override
  public CrudsResult<Void> revertTransition(TaskParameterContext<TaskUpdate, StateTaskDetails> parameter) {
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(parameter.getContext());

    TaskUpdate taskUpdate = parameter.getValue();
    DataRef tripBooking = taskUpdate.getTarget();
    TaskProcessingContext<StateTaskDetails> processingContext = parameter.getProcessingContext();

    StateUpdate stateUpdate = new StateUpdate();
    stateUpdate.setTarget(tripBooking);
    stateUpdate.setExpectedStatus(processingContext.getInterimState());
    stateUpdate.setTargetStatus(processingContext.getBeginState());

    // @anchor:revert-before:start
    // @anchor:revert-before:end
    // anchor:custom-revert-before:start
    // anchor:custom-revert-before:end

    if (logger.isDebugEnabled()) {
      logger.debug(
          "Returning to begin state " + processingContext.getBeginState() + " on TripBooking with id " + tripBooking.getId()
      );
    }
    CrudsResult<Void> crudsResult = tripBookingAgent.compareAndSetStatus(stateUpdate);

    if (crudsResult.isError()) {
      // @anchor:revert-error:start
      // @anchor:revert-error:end
      // anchor:custom-revert-error:start
      // anchor:custom-revert-error:end
      return crudsResult.convertError();
    }

    // @anchor:revert-after:start
    if (taskStatusRef != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Updating TripBookingTaskStatus for TripBooking with id " + tripBooking.getId()
        );
      }
      CrudsResult<DataRef> taskStatusCrudsResult = updateTaskStatus(parameter, processingContext.getBeginState());
      if (taskStatusCrudsResult.isError()) {
        return taskStatusCrudsResult.convertError();
      }
    }
    // @anchor:revert-after:end
    // anchor:custom-revert-after:start
    // anchor:custom-revert-after:end

    return CrudsResult.success();
  }

  // @anchor:methods:start
  private CrudsResult<DataRef> createTaskStatus(TaskParameterContext<DataRef, StateTaskDetails> parameter, String status) {
    TripBookingTaskStatusLocalAgent tripBookingTaskStatusAgent = TripBookingTaskStatusLocalAgent.getTripBookingTaskStatusAgent(parameter.getUserContext());
    DataRef tripBooking = parameter.getValue();

    DataRef stateTask = parameter.getProcessingContext().getStateTask().getDataRef();
    TripBookingTaskStatusDetails taskStatus = new TripBookingTaskStatusDetails();
    taskStatus.setName(tripBooking.getName() + ":" + stateTask.getName());
    taskStatus.setStatus(status);
    taskStatus.setStartedAt(new Date());
    taskStatus.setFinishedAt(null);
    taskStatus.setTripBooking(tripBooking);
    taskStatus.setStateTask(stateTask);

    return tripBookingTaskStatusAgent.create(taskStatus);
  }

  private CrudsResult<DataRef> updateTaskStatus(TaskParameterContext<TaskUpdate, StateTaskDetails> parameter, String status) {
    TripBookingTaskStatusLocalAgent tripBookingTaskStatusAgent = TripBookingTaskStatusLocalAgent.getTripBookingTaskStatusAgent(parameter.getUserContext());

    CrudsResult<TripBookingTaskStatusDetails> crudsResult = tripBookingTaskStatusAgent.getDetails(taskStatusRef);
    if (crudsResult.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not find taskStatus instance"
        );
      }
      return crudsResult.convertError();
    }

    TripBookingTaskStatusDetails details = crudsResult.getValue();
    details.setFinishedAt(new Date());
    details.setStatus(status);

    return tripBookingTaskStatusAgent.modify(details);
  }
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
