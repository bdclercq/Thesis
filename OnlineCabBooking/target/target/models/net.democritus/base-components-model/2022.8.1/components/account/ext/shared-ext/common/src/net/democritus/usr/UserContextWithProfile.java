package net.democritus.usr;

import net.democritus.sys.UserContext;

// @feature:authentication
public interface UserContextWithProfile extends UserContext {

  String getProfileName();

}
