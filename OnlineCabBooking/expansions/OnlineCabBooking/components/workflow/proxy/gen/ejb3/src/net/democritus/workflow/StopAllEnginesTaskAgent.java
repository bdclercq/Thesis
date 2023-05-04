package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.BasicProcessingContext;

import net.democritus.sys.TaskResult;
import net.democritus.workflow.WorkflowDetails;
// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Client agent for a session in a client or web tier, connecting through a proxy
 * to the actual implementation of the task StopAllEnginesTask in the logic tier
 */
public class StopAllEnginesTaskAgent {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StopAllEnginesTaskAgent.class);
  // @anchor:variables:end

  private static final StopAllEnginesTaskProxy stopAllEnginesTaskProxy = StopAllEnginesTaskProxy.getStopAllEnginesTaskProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Agent constructor and retrieval ==========*/

  private StopAllEnginesTaskAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static StopAllEnginesTaskAgent getStopAllEnginesTaskAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new StopAllEnginesTaskAgent(context, userContext);
  }

  public static StopAllEnginesTaskAgent getStopAllEnginesTaskAgent(UserContext userContext) {
    return new StopAllEnginesTaskAgent(Context.from(userContext), userContext);
  }

  /*========== Handling the context ==========*/

  private <T> ParameterContext<T> createParameter(T value, String paramString) {
    return mContext.extend(getProcessingContext(paramString)).withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private BasicProcessingContext getProcessingContext(String paramString) {
    return new BasicProcessingContext("net.democritus.workflow.StopAllEnginesTaskImpl", paramString);
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========== Business Methods ==========*/

  public TaskResult<Void> perform(WorkflowDetails workflowDetails) {
    return perform(workflowDetails, "");
  }

  public TaskResult<Void> perform(WorkflowDetails workflowDetails, String paramString) {
    TaskResult<Void> taskResult = null;
    ParameterContext<WorkflowDetails> parameterContext = createParameter(workflowDetails, paramString);

    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      taskResult = stopAllEnginesTaskProxy.perform(parameterContext);
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the StopAllEnginesTask task", e
        );
      }
    }

    // anchor:custom-postPerform:start
    // anchor:custom-postPerform:end

    return taskResult;
  }
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

