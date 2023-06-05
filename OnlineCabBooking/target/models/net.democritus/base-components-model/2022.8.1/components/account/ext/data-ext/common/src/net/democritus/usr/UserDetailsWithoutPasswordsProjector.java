package net.democritus.usr;

import net.democritus.sys.ParameterContext;

// extension on version 3.1.4

// @feature:hide-sensitive-information
public class UserDetailsWithoutPasswordsProjector extends UserDetailsProjector {

  public UserDetailsWithoutPasswordsProjector(UserCrudsInternal crudsInternal) {
    super(crudsInternal);
  }

  @Override
  public UserDetails project(ParameterContext<UserData> dataParameter) {
    UserDetails projection = super.project(dataParameter);
    projection.setEncryptedPassword("");
    projection.setPassword("");
    return projection;
  }

}
