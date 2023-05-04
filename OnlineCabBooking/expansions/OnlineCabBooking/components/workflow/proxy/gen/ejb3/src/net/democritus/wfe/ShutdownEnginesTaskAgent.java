package net.democritus.wfe;

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
import net.democritus.wfe.EngineServiceDetails;
// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Client agent for a session in a client or web tier, connecting through a proxy
 * to the actual implementation of the task ShutdownEnginesTask in the logic tier
 */
public class ShutdownEnginesTaskAgent {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ShutdownEnginesTaskAgent.class);
  // @anchor:variables:end

  private static final ShutdownEnginesTaskProxy shutdownEnginesTaskProxy = ShutdownEnginesTaskProxy.getShutdownEnginesTaskProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Agent constructor and retrieval ==========*/

  private ShutdownEnginesTaskAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static ShutdownEnginesTaskAgent getShutdownEnginesTaskAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new ShutdownEnginesTaskAgent(context, userContext);
  }

  public static ShutdownEnginesTaskAgent getShutdownEnginesTaskAgent(UserContext userContext) {
    return new ShutdownEnginesTaskAgent(Context.from(userContext), userContext);
  }

  /*========== Handling the context ==========*/

  private <T> ParameterContext<T> createParameter(T value, String paramString) {
    return mContext.extend(getProcessingContext(paramString)).withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private BasicProcessingContext getProcessingContext(String paramString) {
    return new BasicProcessingContext("net.democritus.wfe.ShutdownEnginesTaskImpl", paramString);
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========== Business Methods ==========*/

  public TaskResult<Void> perform(EngineServiceDetails engineServiceDetails) {
    return perform(engineServiceDetails, "");
  }

  public TaskResult<Void> perform(EngineServiceDetails engineServiceDetails, String paramString) {
    TaskResult<Void> taskResult = null;
    ParameterContext<EngineServiceDetails> parameterContext = createParameter(engineServiceDetails, paramString);

    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      taskResult = shutdownEnginesTaskProxy.perform(parameterContext);
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the ShutdownEnginesTask task", e
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

