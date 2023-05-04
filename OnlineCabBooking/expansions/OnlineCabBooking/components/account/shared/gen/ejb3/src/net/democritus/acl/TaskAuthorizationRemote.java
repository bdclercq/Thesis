package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.TaskAccessRights;

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Remote interface for the session bean TaskAuthorization.
 */

public interface TaskAuthorizationRemote extends TaskPerformer<TaskAccessRights,DataAccessQuery>, TaskPerformerByDataRef<TaskAccessRights> {

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

