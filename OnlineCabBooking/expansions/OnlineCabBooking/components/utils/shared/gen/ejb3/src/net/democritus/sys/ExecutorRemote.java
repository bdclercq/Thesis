package net.democritus.sys;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.sys.ExecutionDetails;
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Remote interface for the session bean Executor.
 */

public interface ExecutorRemote extends TaskPerformer<Void,ExecutionDetails>, TaskPerformerByDataRef<Void> {

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

