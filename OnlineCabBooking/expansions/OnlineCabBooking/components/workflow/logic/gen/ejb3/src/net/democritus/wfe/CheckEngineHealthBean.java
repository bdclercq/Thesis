package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.democritus.wfe.EngineNodeDetails;
import net.democritus.wfe.EngineNodeLocalAgent;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.ProcessingContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ProjectionRef;
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
import net.democritus.sys.Context;
import net.democritus.workflow.EngineNodeExpiredNodeCleaner;
import net.democritus.workflow.ExpiredClaimCleaner;
import net.democritus.workflow.WorkflowInterruptRecoverer;
// anchor:custom-imports:end

/**
 * Stateless session bean encapsulating the implementation of the task CheckEngineHealth.
 */

@Stateless
@Remote(CheckEngineHealthRemote.class)
@Local(CheckEngineHealthLocal.class)
// @anchor:class-annotations:start
// @anchor:class-annotations:end
// anchor:custom-class-annotations:start
// anchor:custom-class-annotations:end
public class CheckEngineHealthBean implements CheckEngineHealthRemote, CheckEngineHealthLocal {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(CheckEngineHealthBean.class);
  // @anchor:variables:end

  @javax.annotation.Resource private SessionContext sessionContext = null;

  // anchor:link-variables:start
  // anchor:link-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business methods implementation ==========*/

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> performOnTarget(ParameterContext<DataRef> targetParameter) {
    EngineNodeLocalAgent engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(targetParameter.getContext());
    // anchor:get-instance:start
    CrudsResult<EngineNodeDetails> result = engineNodeAgent.getProjection(new ProjectionRef("details", targetParameter.getValue()));
    // anchor:get-instance:end

    if (result.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to retrieve target instance for 'EngineNode'"
        );
      }
      return TaskResult.error();
    }
    return perform(targetParameter.construct(result.getValue()));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaskResult<Void> perform(ParameterContext<EngineNodeDetails> targetParameter) {

    TaskResult<Void> taskResult = null;

    // @anchor:prePerform:start
    // @anchor:prePerform:end
    // anchor:custom-prePerform:start
    EngineNodeDetails engineNodeDetails = targetParameter.getValue();
    if (engineNodeDetails.getMaster() == null || !engineNodeDetails.getMaster()) {
      logger.error("Engine node must be master to execute health checks");
      return TaskResult.error();
    }

    logger.debug("Starting health checks...");
    Context context = targetParameter.getContext();

    EngineNodeExpiredNodeCleaner engineNodeExpiredNodeCleaner = new EngineNodeExpiredNodeCleaner(context);
    ExpiredClaimCleaner expiredClaimCleaner = new ExpiredClaimCleaner(context);

    WorkflowInterruptRecoverer workflowInterruptRecoverer = new WorkflowInterruptRecoverer(context);
    workflowInterruptRecoverer.setRecoverClaimingFlows(true);
    workflowInterruptRecoverer.setRecoverNonClaimingFlows(false);
    // anchor:custom-prePerform:end

    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
      // anchor:custom-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform CheckEngineHealth on EngineNodeDetails", e
        );
      }
    }

    // @anchor:postPerform:start
    // @anchor:postPerform:end
    // anchor:custom-postPerform:start
    try {
      expiredClaimCleaner.cleanExpiredClaims();
      engineNodeExpiredNodeCleaner.cleanExpiredNodes();
      workflowInterruptRecoverer.recoverInterruptedInstances();
      taskResult = TaskResult.success();
    } catch (Exception e) {
      logger.error("Workflow recovery failed", e);
      taskResult = TaskResult.error();
    }
    // anchor:custom-postPerform:end

    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

