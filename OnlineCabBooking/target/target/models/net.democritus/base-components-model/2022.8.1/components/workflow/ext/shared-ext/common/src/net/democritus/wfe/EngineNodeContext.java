package net.democritus.wfe;

import net.democritus.sys.ContextGroup;
import net.democritus.sys.DataRef;

// @feature:multi-node-workflow
public class EngineNodeContext implements ContextGroup {

  private final DataRef engineNode;
  private final EngineNodeState status;

  EngineNodeContext(DataRef engineNode, EngineNodeState status) {
    this.engineNode = engineNode;
    this.status = status;
  }

  public DataRef getEngineNode() {
    return engineNode;
  }

  public EngineNodeState getStatus() {
    return status;
  }
}
