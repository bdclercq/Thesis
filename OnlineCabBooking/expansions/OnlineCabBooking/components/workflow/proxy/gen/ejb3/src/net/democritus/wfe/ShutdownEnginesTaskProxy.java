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
 * to the actual implementation of the task ShutdownEnginesTask in the logic tier
 */
public class ShutdownEnginesTaskProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("workflow", "ShutdownEnginesTask", "unknownError");

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ShutdownEnginesTaskProxy.class);
  // @anchor:variables:end

  private ShutdownEnginesTaskRemote shutdownEnginesTaskRemote = null;
  private static ShutdownEnginesTaskProxy shutdownEnginesTaskProxy = null;
  private List shutdownEnginesTaskIdVector = null;
  private Date lastRefreshIdVector = new Date();

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private ShutdownEnginesTaskProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      String remoteName = componentJNDI.getTaskRemoteName("ShutdownEnginesTask");
      shutdownEnginesTaskRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static ShutdownEnginesTaskProxy getShutdownEnginesTaskProxy() {
    if (shutdownEnginesTaskProxy == null) {
      shutdownEnginesTaskProxy = new ShutdownEnginesTaskProxy();
    }
    return shutdownEnginesTaskProxy;
  }

  /*========== Business Methods ==========*/

  public TaskResult<Void> perform(ParameterContext<EngineServiceDetails> detailsParameter) {

    TaskResult<Void> taskResult = null;

    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      taskResult = shutdownEnginesTaskRemote.perform(detailsParameter);
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
