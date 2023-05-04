package net.democritus.wfe;

import net.democritus.sys.*;
import net.democritus.workflow.EngineNodeConfig;
import net.democritus.workflow.EngineNodeUpdater;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

// @feature:multi-node-workflow
public class EngineNodeContextRetriever {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineNodeContextRetriever.class);

  private final Context context;
  private final EngineNodeLocalAgent engineNodeAgent;

  public EngineNodeContextRetriever(Context context) {
    this.context = context;
    this.engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(context);
  }

  public EngineNodeContext retrieveEngineNodeContext() {
    DataRef engineNode = getEngineNode();
    EngineNodeState status;
    if (DataRefValidation.isDataRefDefined(engineNode)) {
      status = getEngineNodeStatus(engineNode);
    } else {
      status = EngineNodeState.RECOVERING;
    }
    return new EngineNodeContext(engineNode, status);
  }

  private DataRef getEngineNode() {
    synchronized (EngineNodeContextRetriever.class) {
      String name = EngineNodeConfig.getEngineNodeName();

      CrudsResult<EngineNodeDetails> result = engineNodeAgent.getEngineNodeByName(name);
      if (result.isSuccess()) {
        return result.getValue().getDataRef();
      } else {
        return new EngineNodeUpdater(context).registerEngineNode();
      }
    }
  }

  private EngineNodeState getEngineNodeStatus(DataRef engineNode) {
    CrudsResult<EngineNodeDetails> details = engineNodeAgent.getEngineNodeById(engineNode.getId());
    if (details.isError()) {
      LOGGER.error("Could not retrieve status of Engine Node '" + engineNode.getName() + "'");
      return EngineNodeState.NOT_RESPONDING;
    }

    return details.getValue().getStatusAsEnum();
  }
}