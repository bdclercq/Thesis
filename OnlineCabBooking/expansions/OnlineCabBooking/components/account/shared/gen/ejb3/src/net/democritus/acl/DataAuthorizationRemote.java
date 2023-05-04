package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.DataAccessRights;

// anchor:custom-imports:start
import net.democritus.sys.CrudsResult;
import net.democritus.sys.ParameterContext;
// anchor:custom-imports:end

/**
 * Remote interface for the session bean DataAuthorization.
 */

public interface DataAuthorizationRemote extends TaskPerformer<DataAccessRights,DataAccessQuery>, TaskPerformerByDataRef<DataAccessRights> {

  // anchor:custom-methods:start
  CrudsResult<ProfileAccessRights> getProfileAccessRights(ParameterContext<ProfileAccessQuery> queryParameter);
  // anchor:custom-methods:end

}

