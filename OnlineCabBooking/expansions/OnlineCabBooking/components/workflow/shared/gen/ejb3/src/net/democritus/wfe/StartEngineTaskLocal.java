package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.wfe.EngineServiceDetails;
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the session bean StartEngineTask.
 */

public interface StartEngineTaskLocal extends TaskPerformer<Void,EngineServiceDetails>, TaskPerformerByDataRef<Void> {

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

