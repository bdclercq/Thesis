package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.IStateDef;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public enum AccountState implements IStateDef {
  NOT_MAPPED("<not mapped>");

  private String status;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  AccountState(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean isSameAs(String status) {
    return this.status.equals(status);
  }

  public static AccountState getAccountState(String status) {
    for (AccountState accountState : values()) {
      if (accountState.isSameAs(status)) {
        return accountState;
      }
    }
    return NOT_MAPPED;
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
