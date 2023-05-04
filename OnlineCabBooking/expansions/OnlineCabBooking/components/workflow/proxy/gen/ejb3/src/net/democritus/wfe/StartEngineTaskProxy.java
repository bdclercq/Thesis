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
// anchor:custom-imports:end

/**
 * Singleton proxy to connect client sessions from a client or web tier,
 * to the actual implementation of the task StartEngineTask in the logic tier
 */
public class StartEngineTaskProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("workflow", "StartEngineTask", "unknownError");

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StartEngineTaskProxy.class);
  // @anchor:variables:end

  private StartEngineTaskRemote startEngineTaskRemote = null;
  private static StartEngineTaskProxy startEngineTaskProxy = null;
  private List startEngineTaskIdVector = null;
  private Date lastRefreshIdVector = new Date();

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private StartEngineTaskProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      String remoteName = componentJNDI.getTaskRemoteName("StartEngineTask");
      startEngineTaskRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static StartEngineTaskProxy getStartEngineTaskProxy() {
    if (startEngineTaskProxy == null) {
      startEngineTaskProxy = new StartEngineTaskProxy();
    }
    return startEngineTaskProxy;
  }

  /*========== Business Methods ==========*/

  public TaskResult<Void> perform(ParameterContext<EngineServiceDetails> detailsParameter) {

    TaskResult<Void> taskResult = null;

    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      taskResult = startEngineTaskRemote.perform(detailsParameter);
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the StartEngineTask task", e
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
