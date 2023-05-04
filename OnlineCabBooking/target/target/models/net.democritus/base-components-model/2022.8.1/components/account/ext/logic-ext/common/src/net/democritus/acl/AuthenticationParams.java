package net.democritus.acl;

import net.democritus.sys.Context;
import net.democritus.sys.ParamTargetValueAgent;

// @feature:authentication
public class AuthenticationParams {

  public static final String ENCRYPTED_PASSWORD = "useEncryptedPassword";
  public static final String UNIQUE_SESSION = "useUniqueSession";

  private final ParamTargetValueAgent paramTargetValueAgent =
      ParamTargetValueAgent.getParamTargetValueAgent(Context.emptyContext());

  public boolean useEncryption() {
    return paramTargetValueAgent.isParamTargetValueActive(ENCRYPTED_PASSWORD, "default", false);
  }

  public boolean useUniqueSession() {
    return paramTargetValueAgent.isParamTargetValueActive(UNIQUE_SESSION, "default", false);
  }

}
