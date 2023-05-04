package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.usr.UserInput;
import net.democritus.sys.UserContext;

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the session bean Authentication.
 */

public interface AuthenticationLocal extends TaskPerformer<UserContext,UserInput>, TaskPerformerByDataRef<UserContext> {

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

