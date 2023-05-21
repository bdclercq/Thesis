package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// anchor:standard-imports:start
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.naming.NamingException;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import net.democritus.sys.DataRef;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.SearchResult;
import net.democritus.sys.CrudsResult;

import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.democritus.sys.ProcessingContext;
import net.democritus.sys.BasicProcessingContext;

import net.democritus.sys.FlowResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.TaskPerformer;
import net.democritus.sys.ProjectionRef;

import net.democritus.jndi.ComponentJNDI;

import net.democritus.sys.Context;
import net.democritus.sys.search.Paging;
import net.democritus.sys.search.SearchDetails;
import net.democritus.sys.workflow.RunId;
import net.democritus.wfe.BatchProcessingContext;
import net.democritus.wfe.BatchDescription;
import net.democritus.wfe.EngineServiceFindByNameEqDetails;
import net.democritus.wfe.EngineServiceDetailsWithoutRefs;
import net.democritus.wfe.EngineServiceProcessingContext;
import net.democritus.wfe.EngineNodeServiceDetails;
import net.democritus.wfe.TaskProcessor;
import net.democritus.wfe.ParallelTaskProcessor;
import net.democritus.wfe.ProcessingException;
import net.democritus.wfe.TimeoutCalculator;

import net.democritus.workflow.StateTaskSorter;

import net.palver.util.Options.Option;
import static net.palver.util.Options.none;
import static net.palver.util.Options.some;

import net.democritus.workflow.WorkflowRemote;
import net.democritus.workflow.StateTaskRemote;
import net.democritus.wfe.EngineServiceRemote;
import net.democritus.wfe.EngineNodeServiceRemote;
import net.democritus.workflow.WorkflowDetails;
import net.democritus.workflow.StateTaskDetails;
// anchor:standard-imports:end

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
// anchor:imports:start
import cabBookingCore.TripBookingDetails;
import cabBookingCore.TripBookingFindByStatusEqDetails;
import cabBookingCore.TripBookingLocal;
import cabBookingCore.TripBookingTaskStatusLocal;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Stateless session bean serving as BeanEngine for the TripBookingFlow flow element.
 */

@Stateless
@Remote(TripBookingFlowEngineRemote.class)
@Local(TripBookingFlowEngineLocal.class)
public class TripBookingFlowEngineBean implements TripBookingFlowEngineRemote, TripBookingFlowEngineLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingFlowEngineBean.class);
  // @anchor:variables:end
  // anchor:variables:start
  private static final String ENGINE_START = "start";
  private static final int RESPONSE_TIME_IN_MS = 100;

  @EJB private StateTaskRemote stateTaskRemote;
  @EJB private WorkflowRemote workflowRemote;
  @EJB private EngineServiceRemote engineServiceRemote;
  @EJB private EngineNodeServiceRemote engineNodeServiceRemote;
  @EJB private TripBookingLocal tripBookingLocal;
  @EJB private TripBookingTaskStatusLocal tripBookingTaskStatusLocal;
  private TimeoutCalculator timeoutCalculator = new TimeoutCalculator();
  // anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  // anchor:orchestrate:start
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public FlowResult<Void> orchestrate(ParameterContext<WorkflowDetails> workflowContext) {
    WorkflowDetails workflow = workflowContext.getValue();
    UserContext userContext = workflowContext.getUserContext();

    Context context = workflowContext.getContext();
    String engineName = "-";
    EngineServiceDetailsWithoutRefs engineService = null;
    Option<EngineServiceDetailsWithoutRefs> engineServiceOpt = getEngineService(context);
    if (engineServiceOpt.isDefined()) {
      engineService = engineServiceOpt.getValue();
      engineName = engineService.getName();
    }

    // @anchor:orchestrate-before:start
    // @anchor:orchestrate-before:end
    // anchor:custom-orchestrate-before:start
    // anchor:custom-orchestrate-before:end

    int batchSize = getBatchSize(context);
    RunId runId = new RunId();
    long startTime = System.currentTimeMillis();

    if (logger.isDebugEnabled()) {
      logger.debug(
          runId + ": Processing workflow=" + workflow.getName()
      );
    }

    FlowResult<Void> flowResult = FlowResult.success(null);

    try {
       // @anchor:before-fetch-statetasks:start
       // @anchor:before-fetch-statetasks:end

      /*========== Perform all the appropriate tasks ==========*/

      SearchResult<StateTaskDetails> stateTaskResult = stateTaskRemote.getStateTasksByWorkflowId(workflowContext.construct(workflow.getId()));

      if (stateTaskResult.isError()) {
        return FlowResult.error();
      }

      List<StateTaskDetails> stateTasks = stateTaskResult.getResults();
      stateTasks = new StateTaskSorter().sort(stateTasks);

      for (int i = 0; i < stateTasks.size(); i++) {
        List<StateTaskDetails> taskChain = stateTasks.subList(i, stateTasks.size());

        BatchDescription batchDescription = new BatchDescription();
        batchDescription.setWorkflow(workflow);
        batchDescription.setEngine(engineService);
        batchDescription.setTaskChain(taskChain);
        batchDescription.setBatchSize(batchSize);
        batchDescription.setRunId(runId);

        // @anchor:batchDescription:start
        // @anchor:batchDescription:end
        // anchor:custom-batchDescription:start
        // anchor:custom-batchDescription:end

        FlowResult batchResult = processBatch(workflowContext.construct(batchDescription));
        flowResult.join(batchResult);
      }

      if (flowResult.isSuccess() && flowResult.getNumberOfProcessedTasks() > 0) {
        if (logger.isInfoEnabled()) {
          logger.info(
              runId + ": Completed workflow=" + workflow.getName()
                    + ", engineService=" + engineName
                    + ", batchSize=" + batchSize
                    + ", duration=" + (System.currentTimeMillis() - startTime) + "ms"
                    + ", tasksSucceeded=" + flowResult.getNumberOfSuccessfulTasks()
                    + ", tasksFailed=" + flowResult.getNumberOfFailedTasks()
                    + ", tasksNotExecuted=" + flowResult.getNumberOfTasksNotExecuted()
                    + ", tasksProcessedInTotal=" + flowResult.getNumberOfProcessedTasks()
          );
        }
      }
    } catch (Exception e) {
      flowResult = FlowResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the flow " + workflow.getName() + " on tripBooking set", e
        );
      }
    }

    // @anchor:orchestrate-after:start
    // @anchor:orchestrate-after:end
    // anchor:custom-orchestrate-after:start
    // anchor:custom-orchestrate-after:end

    return flowResult;
  }
  // anchor:orchestrate:end

  // anchor:process-batch:start
  private FlowResult processBatch(ParameterContext<BatchDescription> batchParameter) {
    UserContext userContext = batchParameter.getUserContext();
    Context context = batchParameter.getContext();
    BatchDescription batchDescription = batchParameter.getValue();
    RunId runId = batchDescription.getRunId();
    StateTaskDetails stateTask = batchDescription.getTaskChain().get(0);

    String engineName = "-";
    if (batchDescription.getEngine() != null) {
      engineName = batchDescription.getEngine().getName();
    }

    try {
      List<DataRef> targets = findBatch(stateTask, batchDescription.getBatchSize(), runId, context);

      TaskProcessor processor = new ParallelTaskProcessor();
      processor.init(batchParameter);

      // @anchor:processBatch-before:start
      // @anchor:processBatch-before:end
      // anchor:custom-processBatch-before:start
      // anchor:custom-processBatch-before:end

      long startTime = System.currentTimeMillis();
      long lastEngineCheck = System.currentTimeMillis();
      Queue<DataRef> queue = new LinkedList<DataRef>(targets);

      while (!queue.isEmpty()) {
        if (System.currentTimeMillis() - lastEngineCheck > RESPONSE_TIME_IN_MS) {
          if (logger.isDebugEnabled()) {
            logger.debug(
                runId + ": Checking engine=" + engineName
            );
          }
          if (!shouldEngineRun(batchParameter.getContext())) {
            if (logger.isInfoEnabled()) {
              logger.info(
                  runId + ": Engine stopped, cancelling all running tasks"
              );
            }
            processor.cancelRunningTasks();
            if (logger.isInfoEnabled()) {
              logger.info(
                  runId + ": Cancelling the rest of the queue, count=" + queue.size()
              );
            }
            finishTasks(queue, stateTask, runId, context);
            queue.clear();
            break;
          }
          lastEngineCheck = System.currentTimeMillis();
        }

        DataRef target = queue.remove();

        try {
          processor.processTask(target, stateTask.getTimeout());
          // @anchor:processTask-after:start
          // @anchor:processTask-after:end
          // anchor:custom-processTask-after:start
          // anchor:custom-processTask-after:end
        } catch (ProcessingException e) {
          if (logger.isWarnEnabled()) {
            logger.warn(
                runId + ": Could not start task for target=" + target.getName(), e
            );
          }
        }
      }

      List<DataRef> completedTargets = processor.takeCompletedTasks();
      finishTasks(completedTargets, stateTask, runId, context);

      while (processor.hasMoreTasks()) {
        if (!shouldEngineRun(batchParameter.getContext())) {
          if (logger.isInfoEnabled()) {
            logger.info(
                runId + ": Engine stopped, cancelling all running tasks"
            );
          }
          processor.cancelRunningTasks();
        }
        completedTargets = processor.takeNextCompletedTasks();
        finishTasks(completedTargets, stateTask, runId, context);
      }

      FlowResult flowResult = processor.getFlowResult();

      // In case a batch is being executed, list all instances that were not processed
      Option<BatchProcessingContext> batchProcessingContextOption = context.getContext(BatchProcessingContext.class);
      if (batchProcessingContextOption.isDefined()) {
        List<DataRef> notExecuted = new ArrayList<DataRef>(batchProcessingContextOption.getValue().getTargetInstances());
        notExecuted.removeAll(targets);
        for (DataRef target : notExecuted) {
          flowResult = flowResult.addTaskResult(TaskResult.notExecuted());
        }
      }

      // @anchor:processBatch-after:start
      checkShutdown(batchParameter.getContext());
      // @anchor:processBatch-after:end
      // anchor:custom-processBatch-after:start
      // anchor:custom-processBatch-after:end

      if (flowResult.isSuccess() && flowResult.getNumberOfProcessedTasks() > 0) {
        if (logger.isInfoEnabled()) {
          logger.info(
              runId + ": Batch finished"
                    + ", workflow=" + batchDescription.getWorkflow().getName()
                    + ", engineService=" + engineName
                    + ", stateTask=" + batchDescription.getTaskChain().get(0).getName()
                    + ", duration=" + (System.currentTimeMillis() - startTime) + "ms"
                    + ", batchSize=" + batchDescription.getBatchSize()
                    + ", beginState=" + batchDescription.getTaskChain().get(0).getBeginState()
                    + ", endState=" + batchDescription.getTaskChain().get(0).getEndState()
                    + ", tasksSucceeded=" + flowResult.getNumberOfSuccessfulTasks()
                    + ", tasksFailed=" + flowResult.getNumberOfFailedTasks()
                    + ", tasksNotExecuted=" + flowResult.getNumberOfTasksNotExecuted()
                    + ", tasksProcessedInTotal=" + flowResult.getNumberOfProcessedTasks()
          );
        }
      }
      return flowResult;
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        if (logger.isErrorEnabled()) {
          logger.error(
              runId + ": Failed to setup processing for stateTask=" + stateTask.getName(), e
          );
        }
      }
      return FlowResult.error();
    }
  }
  // anchor:process-batch:end

  // anchor:engine-service-methods:start
  private Option<EngineServiceDetailsWithoutRefs> getEngineService(Context context) {
    Option<EngineServiceProcessingContext> engineServiceProcessingContextOption = context.getContext(EngineServiceProcessingContext.class);
    if (engineServiceProcessingContextOption.isDefined()) {
      DataRef dataRef = engineServiceProcessingContextOption.getValue().getEngineService();
      return findEngineServiceDetails(dataRef, context);
    }

    Option<ProcessingContext> processingContextOption = context.getContext(ProcessingContext.class);
    if (processingContextOption.isEmpty()) {
      return none();
    }

    String engineName = processingContextOption.getValue().getParamsName();
    if (engineName == null) {
      return none();
    }

    EngineServiceFindByNameEqDetails engineFinder = new EngineServiceFindByNameEqDetails();
    engineFinder.setName(engineName);
    SearchDetails<EngineServiceFindByNameEqDetails> searchDetails = SearchDetails.fetchN(engineFinder, 1);
    searchDetails.setProjection("detailsWithoutRefs");
    searchDetails.setSkipCount(true);

    SearchResult<EngineServiceDetailsWithoutRefs> searchResult = engineServiceRemote.find(context.withParameter(searchDetails));

    if (searchResult.isSuccess()) {
      return searchResult.getFirst();
    } else {
      return none();
    }
  }

  private Option<EngineServiceDetailsWithoutRefs> findEngineServiceDetails(DataRef dataRef, Context context) {
    ProjectionRef projectionRef = new ProjectionRef("detailsWithoutRefs", dataRef);
    CrudsResult<EngineServiceDetailsWithoutRefs> result = engineServiceRemote.getProjection(context.withParameter(projectionRef));
    if (result.isError() || !DataRefValidation.isDataRefDefined(result.getValue().getDataRef())) {
      return none();
    } else {
      return some(result.getValue());
    }
  }

  private Integer getBatchSize(Context context) {
    Option<EngineServiceDetailsWithoutRefs> engineServiceDetailsOption = getEngineService(context);
    if (engineServiceDetailsOption.isDefined()) {
      EngineServiceDetailsWithoutRefs engineService = engineServiceDetailsOption.getValue();
      Integer batchSize = engineService.getBatchSize();
      return batchSize == null ? -1 : batchSize;
    }
    return -1;
  }

  private boolean shouldEngineRun(Context context) {
    Option<BatchProcessingContext> batchProcessingContextOpt = context.getContext(BatchProcessingContext.class);
    if (batchProcessingContextOpt.isDefined()) {
      return true;
    }

    Option<EngineServiceDetailsWithoutRefs> engineServiceDetailsOption = getEngineService(context);
    if (engineServiceDetailsOption.isEmpty()) {
      logger.warn("EngineService cannot be found, stopping engine");
      return false;
    }
    EngineServiceDetailsWithoutRefs engineServiceDetails = engineServiceDetailsOption.getValue();

    if (!ENGINE_START.equals(engineServiceDetails.getStatus())) {
      return false;
    }
    // @anchor:should-engine-run:start
    EngineNodeServiceDetails engineNodeService = engineNodeServiceRemote.getEngineNodeServiceForEngineService(context.withParameter(engineServiceDetails.getDataRef())).getValue();
    if ("Shutting down".equals(engineNodeService.getStatus())) {
      logger.info("EngineService '" + engineServiceDetails.getName() + "' is stopping for shutdown");
      return false;
    }
    // @anchor:should-engine-run:end
    // anchor:custom-should-engine-run:start
    // anchor:custom-should-engine-run:end
    return true;
  }
  // anchor:engine-service-methods:end
  // anchor:batch-methods:start
  private List<DataRef> findBatch(StateTaskDetails stateTask, int batchSize, RunId runId, Context context) {
    String beginState = stateTask.getBeginState();

    Option<BatchProcessingContext> batchProcessingContextOpt = context.getContext(BatchProcessingContext.class);
    if (batchProcessingContextOpt.isDefined()) {
      List<DataRef> targetInstances = batchProcessingContextOpt.getValue().getTargetInstances();
      return filterInstancesWithBeginState(targetInstances, beginState, context);
    }

    TripBookingFindByStatusEqDetails finder = new TripBookingFindByStatusEqDetails();
    finder.setStatus(beginState);
    SearchDetails<TripBookingFindByStatusEqDetails> searchDetails = SearchDetails.fetchAllDataRef(finder);
    searchDetails.setSkipCount(true);
    setBatchSize(searchDetails.getPaging(), batchSize);

    // @anchor:findBatch:start
    // @anchor:findBatch:end
    // anchor:custom-findBatch:start
    // anchor:custom-findBatch:end

    SearchResult<DataRef> searchResult = tripBookingLocal.find(context.withParameter(searchDetails));

    if (searchResult.isError()) {
      if (logger.isWarnEnabled()) {
        logger.warn(
            runId + ": finding batch failed"
        );
      }
      return Collections.emptyList();
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            runId + ": real batch size is " + searchResult.getResults().size()
        );
      }
      return searchResult.getResults();
    }
  }

  private void finishTasks(Collection<DataRef> targets, StateTaskDetails stateTask, RunId runId, Context context) {
    // @anchor:finish-tasks:start
    // @anchor:finish-tasks:end
    // anchor:custom-finish-tasks:start
    // anchor:custom-finish-tasks:end
    if (logger.isDebugEnabled()) {
      logger.debug(
          runId + ": tasks finished, count=" + targets.size() + ", stateTask=" + stateTask.getName()
      );
    }
  }

  private void setBatchSize(Paging paging, Integer batchSize) {
    if (batchSize == null || batchSize < 1) {
      paging.setFetchAll(true);
    } else {
      paging.setRowsPerPage(batchSize);
    }
  }
  // anchor:batch-methods:end

  private List<DataRef> filterInstancesWithBeginState(List<DataRef> targetInstances, String beginState, Context context) {
    List<DataRef> batch = new ArrayList<DataRef>();
    for (DataRef targetInstance : targetInstances) {
      ProjectionRef projectionRef = new ProjectionRef("details", targetInstance);
      CrudsResult<TripBookingDetails> result = tripBookingLocal.getProjection(context.withParameter(projectionRef));
      if (result.isError() || !DataRefValidation.isDataRefDefined(result.getValue().getDataRef())) {
        if (logger.isWarnEnabled()) {
          logger.warn("Target TripBooking '" + targetInstance.getName() + "' cannot be found, skipping instance");
        }
      } else if (beginState.equals(result.getValue().getStatus())) {
        batch.add(targetInstance);
      }
    }
    return batch;
  }

  // @anchor:methods:start
  private void checkShutdown(Context context) {
    Option<EngineServiceProcessingContext> engineServiceProcessingContextOption = context.getContext(EngineServiceProcessingContext.class);
    if (engineServiceProcessingContextOption.isEmpty()) {
      return;
    }
    DataRef engine = engineServiceProcessingContextOption.getValue().getEngineService();
    EngineNodeServiceDetails engineNodeService = engineNodeServiceRemote.getEngineNodeServiceForEngineService(context.withParameter(engine)).getValue();
    if ("Shutting down".equals(engineNodeService.getStatus())) {
      engineNodeServiceRemote.setReadyForShutdown(context.withParameter(engineNodeService.getDataRef()));
    }
  }
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
