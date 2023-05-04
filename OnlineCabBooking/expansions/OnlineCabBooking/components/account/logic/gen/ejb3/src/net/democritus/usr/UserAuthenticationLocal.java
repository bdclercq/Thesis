// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.usr;

import net.democritus.sys.ParameterContext;

// @anchor:imports:start
// @anchor:imports:end

public interface UserAuthenticationLocal {

  <V> AuthenticationResult authenticate(ParameterContext<AuthenticationDetails<V>> authenticationDetailsParameter);

  // @anchor:methods:start
  AuthenticationResult authenticateBasic(ParameterContext<AuthenticationDetails<UserInput>> authenticationDetailsParameter);
  // @anchor:methods:end

}
