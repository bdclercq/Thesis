package net.democritus.sys;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.ExecutionDetails;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end
// anchor:custom-imports:start
import java.io.File;
// anchor:custom-imports:end

/**
 * Example implementation delegate for the task element Executor.
 */
public class ExecutorImpl implements Executor {

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ExecutorImpl.class);
  // @anchor:variables:end
  // anchor:custom-variables:start
    private DiagnosticFactory diagnosticFactory = new DiagnosticFactory("utils", "execution");
  // anchor:custom-variables:end

  public TaskResult<Void> perform(ParameterContext<ExecutionDetails> targetParameter) {
    UserContext userContext = targetParameter.getUserContext();
    Context context = targetParameter.getContext();
    TaskResult<Void> taskResult = TaskResult.success();
    if (logger.isDebugEnabled()) {
      logger.debug(
          "Performing the ExecutorImpl implementation"
      );
    }
    try {
      // @anchor:perform:start
      // @anchor:perform:end
      // anchor:custom-perform:start
        ExecutionDetails details = targetParameter.getValue();

        if (details.getName() == null || !details.getName().matches("^\\w+(\\\\\\w+)*(\\.\\w+)?$")) {
          return TaskResult.error(diagnosticFactory.error("execution name should be a file name"));
        }

        if (details.getComponent() == null || !details.getComponent().matches("^\\w+$")) {
          return TaskResult.error(diagnosticFactory.error("execution component should be not contain any strange characters"));
        }

        if (details.getElement() == null || !details.getElement().matches("^\\w+$")) {
          return TaskResult.error(diagnosticFactory.error("execution element should be not contain any strange characters"));
        }

        if (details.getPackageName() == null || !details.getPackageName().matches("^\\w+(\\.\\w+)*$")) {
          return TaskResult.error(diagnosticFactory.error("execution packageName should be not contain any strange characters"));
        }

        String scriptDir = System.getenv("JONAS_BASE") + "\\scripts";
        Process aProcess = 
          Runtime.getRuntime().exec("cmd /c start /wait "+details.getName()+" "+details.getComponent()+" "+details.getElement()+" "+details.getPackageName(), null, new File(scriptDir));
        aProcess.waitFor();
      // anchor:custom-perform:end
      // @anchor:post-perform:start
      // @anchor:post-perform:end
    } catch (Exception e) {
      // @anchor:perform-error:start
      // @anchor:perform-error:end
      // anchor:custom-perform-error:start
      // anchor:custom-perform-error:end
      taskResult = TaskResult.error();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception in ExecutorImpl implementation", e
        );
      }
    }
    return taskResult;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

