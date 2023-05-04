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

import net.democritus.wfe.EngineServiceDetails;
// anchor:link-imports:start
// anchor:link-imports:end

// anchor:custom-imports:start
import net.democritus.sys.SearchResult;

import net.democritus.sys.DataRef;
import net.palver.util.Options;
import java.util.Date;
// anchor:custom-imports:end

/**
 * Client agent for a session in a client or web tier, connecting through a proxy
 * to the actual implementation of the task TimerHandler in the logic tier
 */
public class TimerHandlerAgent {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TimerHandlerAgent.class);
  // @anchor:variables:end

  private static final TimerHandlerProxy timerHandlerProxy = TimerHandlerProxy.getTimerHandlerProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Agent constructor and retrieval ==========*/

  private TimerHandlerAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static TimerHandlerAgent getTimerHandlerAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new TimerHandlerAgent(context, userContext);
  }

  public static TimerHandlerAgent getTimerHandlerAgent(UserContext userContext) {
    return new TimerHandlerAgent(Context.from(userContext), userContext);
  }

  /*========== Handling the context ==========*/

  private <T> ParameterContext<T> createParameter(T value, String paramString) {
    return mContext.extend(getProcessingContext(paramString)).withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private BasicProcessingContext getProcessingContext(String paramString) {
    return new BasicProcessingContext("net.democritus.wfe.TimerHandlerImpl", paramString);
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  /*========== Business Methods ==========*/

  // anchor:custom-methods:start
    public Integer startTimer(String name) {
        return timerHandlerProxy.startTimer(name);
    }

    public Integer stopTimer(String name) {
        return timerHandlerProxy.stopTimer(name);
    }

    public SearchResult<Date> getNextRun(DataRef parameter) {
        return timerHandlerProxy.getNextRun(createParameter(parameter, ""));
    }

  // anchor:custom-methods:end

}

