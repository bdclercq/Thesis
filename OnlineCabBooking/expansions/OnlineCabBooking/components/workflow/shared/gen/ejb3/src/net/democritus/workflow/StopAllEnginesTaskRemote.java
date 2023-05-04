package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.workflow.WorkflowDetails;
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Remote interface for the session bean StopAllEnginesTask.
 */

public interface StopAllEnginesTaskRemote extends TaskPerformer<Void,WorkflowDetails>, TaskPerformerByDataRef<Void> {

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

