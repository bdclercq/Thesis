package net.democritus.usr;

import net.democritus.acl.AccessRightCache;
import net.democritus.sys.UserContext;

// @feature:authentication
public interface UserContextWithRights extends UserContext {

  AccessRightCache getAccessRights();

}
