package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.IStateDef;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public enum EngineNodeServiceState implements IStateDef {
  NOT_RUNNING("Not running"),
  NOT_RESPONDING("Not responding"),
  WAITING("Waiting"),
  WORKING("Working"),
  SHUTTING_DOWN("Shutting down"),
  READY_FOR_SHUTDOWN("Ready for shutdown"),
  NOT_MAPPED("<not mapped>");

  private String status;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  EngineNodeServiceState(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean isSameAs(String status) {
    return this.status.equals(status);
  }

  public static EngineNodeServiceState getEngineNodeServiceState(String status) {
    for (EngineNodeServiceState engineNodeServiceState : values()) {
      if (engineNodeServiceState.isSameAs(status)) {
        return engineNodeServiceState;
      }
    }
    return NOT_MAPPED;
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
