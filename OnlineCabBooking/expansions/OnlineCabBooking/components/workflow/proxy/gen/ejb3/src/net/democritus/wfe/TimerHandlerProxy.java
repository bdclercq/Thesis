package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import javax.naming.Context;
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
import net.democritus.sys.DataRef;
import net.democritus.sys.PageRef;
import net.democritus.sys.SearchDataRef;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.Diagnostic;

import net.democritus.sys.TaskResult;
import net.democritus.sys.TaskPerformer;
import net.democritus.sys.ParameterContext;

import net.democritus.jndi.ComponentJNDI;

import net.democritus.wfe.EngineServiceDetails;
// anchor:custom-imports:start
import net.palver.util.Options;
// anchor:custom-imports:end

/**
 * Singleton proxy to connect client sessions from a client or web tier,
 * to the actual implementation of the task TimerHandler in the logic tier
 */
public class TimerHandlerProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("workflow", "TimerHandler", "unknownError");

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TimerHandlerProxy.class);
  // @anchor:variables:end

  private TimerHandlerRemote timerHandlerRemote = null;
  private static TimerHandlerProxy timerHandlerProxy = null;
  private List timerHandlerIdVector = null;
  private Date lastRefreshIdVector = new Date();

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private TimerHandlerProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      String remoteName = componentJNDI.getTaskRemoteName("TimerHandler");
      timerHandlerRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static TimerHandlerProxy getTimerHandlerProxy() {
    if (timerHandlerProxy == null) {
      timerHandlerProxy = new TimerHandlerProxy();
    }
    return timerHandlerProxy;
  }

  /*========== Business Methods ==========*/

  // anchor:custom-methods:start
  public Integer startTimer(String name) {
    return timerHandlerRemote.startTimer(name);
  }

  public Integer stopTimer(String name) {
    return timerHandlerRemote.stopTimer(name);
  }

  public SearchResult<Date> getNextRun(ParameterContext<DataRef> parameter) {
    return timerHandlerRemote.getNextRun(parameter);
  }
  // anchor:custom-methods:end

}
