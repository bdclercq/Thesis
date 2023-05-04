package net.democritus.workflow;

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

import net.democritus.workflow.WorkflowDetails;
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton proxy to connect client sessions from a client or web tier,
 * to the actual implementation of the task StartAllEnginesTask in the logic tier
 */
public class StartAllEnginesTaskProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("workflow", "StartAllEnginesTask", "unknownError");

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StartAllEnginesTaskProxy.class);
  // @anchor:variables:end

  private StartAllEnginesTaskRemote startAllEnginesTaskRemote = null;
  private static StartAllEnginesTaskProxy startAllEnginesTaskProxy = null;
  private List startAllEnginesTaskIdVector = null;
  private Date lastRefreshIdVector = new Date();

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private StartAllEnginesTaskProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      String remoteName = componentJNDI.getTaskRemoteName("StartAllEnginesTask");
      startAllEnginesTaskRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static StartAllEnginesTaskProxy getStartAllEnginesTaskProxy() {
    if (startAllEnginesTaskProxy == null) {
      startAllEnginesTaskProxy = new StartAllEnginesTaskProxy();
    }
    return startAllEnginesTaskProxy;
  }

  /*========== Business Methods ==========*/

  public TaskResult<Void> perform(ParameterContext<WorkflowDetails> detailsParameter) {

    TaskResult<Void> taskResult = null;

    // anchor:custom-prePerform:start
    // anchor:custom-prePerform:end

    try {
      taskResult = startAllEnginesTaskRemote.perform(detailsParameter);
    } catch (Exception e) {
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Failed to perform the StartAllEnginesTask task", e
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
