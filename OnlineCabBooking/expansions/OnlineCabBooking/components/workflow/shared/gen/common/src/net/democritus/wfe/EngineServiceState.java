package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.IStateDef;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public enum EngineServiceState implements IStateDef {
  NOT_MAPPED("<not mapped>");

  private String status;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  EngineServiceState(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean isSameAs(String status) {
    return this.status.equals(status);
  }

  public static EngineServiceState getEngineServiceState(String status) {
    for (EngineServiceState engineServiceState : values()) {
      if (engineServiceState.isSameAs(status)) {
        return engineServiceState;
      }
    }
    return NOT_MAPPED;
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
