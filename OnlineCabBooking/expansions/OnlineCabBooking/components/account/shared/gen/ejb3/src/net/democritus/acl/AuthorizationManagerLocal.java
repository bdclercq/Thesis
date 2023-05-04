package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.acl.DataAccessQuery;
// anchor:custom-imports:start
import net.democritus.sys.ParameterContext;
import net.democritus.sys.TaskResult;
// anchor:custom-imports:end

/**
 * Local interface for the session bean AuthorizationManager.
 */

public interface AuthorizationManagerLocal {

  // anchor:custom-methods:start
  TaskResult<DataAccessRights> getDataAccessRights(ParameterContext<DataAccessQuery> accessParameter);

  TaskResult<TaskAccessRights> getTaskAccessRights(ParameterContext<DataAccessQuery> accessParameter);
  // anchor:custom-methods:end
}

