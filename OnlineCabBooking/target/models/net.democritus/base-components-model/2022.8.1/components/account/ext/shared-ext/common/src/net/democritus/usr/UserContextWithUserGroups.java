package net.democritus.usr;

import net.democritus.sys.UserContext;

import java.util.Set;

// @feature:authentication
public interface UserContextWithUserGroups extends UserContext {

  Set<String> getUserGroups();

}
