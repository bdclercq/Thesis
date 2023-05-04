// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.usr;

import net.democritus.sys.Diagnostic;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class AuthenticationResult implements Serializable {

  private final List<Diagnostic> diagnostics = new ArrayList<>();

  public abstract boolean isSuccess();

  public boolean isError() {
    return !this.isSuccess();
  }

  /**
   * @deprecated Use {@link AuthenticationResult#getContext()} instead
   */
  @Deprecated
  public UserContext getUserContext() {
    throw new RuntimeException("getUserContext() not applicable.");
  }

  public Context getContext() {
    throw new RuntimeException("getContext() not applicable.");
  }

  public void addDiagnostics(Collection<Diagnostic> diagnostics) {
    this.diagnostics.addAll(diagnostics);
  }

  public List<Diagnostic> getDiagnostics() {
    return this.diagnostics;
  }

  public static AuthenticationResult success(UserContext userContext) {
    return new AuthenticationSuccessResult(userContext);
  }

  public static AuthenticationResult success(Context context) {
    return new AuthenticationSuccessResult(context);
  }

  public static AuthenticationResult error(Collection<Diagnostic> diagnostics) {
    AuthenticationResult authenticationResult = new AuthenticationErrorResult();
    authenticationResult.addDiagnostics(diagnostics);
    return authenticationResult;
  }

  public static AuthenticationResult error(Diagnostic... diagnostics) {
    return error(Arrays.asList(diagnostics));
  }

  public static class AuthenticationSuccessResult extends AuthenticationResult {

    private final Context context;

    AuthenticationSuccessResult(UserContext userContext) {
      this(Context.from(userContext));
    }

    AuthenticationSuccessResult(Context context) {
      this.context = context;
    }

    @Override
    public boolean isSuccess() {
      return true;
    }

    @Deprecated
    @Override
    public UserContext getUserContext() {
      return context
          .getContext(UserContext.class)
          .orElse((UserContext) null);
    }

    @Override
    public Context getContext() {
      return context;
    }

  }

  public static class AuthenticationErrorResult extends AuthenticationResult {

    @Override
    public boolean isSuccess() {
      return false;
    }

  }

}
