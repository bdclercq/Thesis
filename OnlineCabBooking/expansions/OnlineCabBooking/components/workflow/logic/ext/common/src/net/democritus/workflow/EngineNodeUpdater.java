package net.democritus.workflow;

import net.democritus.sys.*;
import net.democritus.wfe.EngineNodeContext;
import net.democritus.wfe.EngineNodeDetails;
import net.democritus.wfe.EngineNodeLocalAgent;
import net.democritus.wfe.EngineNodeState;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import java.util.Date;

// @feature:multi-node-workflow
public class EngineNodeUpdater {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineNodeUpdater.class);

  private final Context context;
  private final EngineNodeLocalAgent engineNodeAgent;

  public EngineNodeUpdater(Context context) {
    this.context = context;
    engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(context);
  }

  public DataRef registerEngineNode() {
    String name = EngineNodeConfig.getEngineNodeName();
    CrudsResult<DataRef> dataRefResult = engineNodeAgent.getDataRef(name);
    if (dataRefResult.isSuccess() && DataRefValidation.isDataRefDefined(dataRefResult.getValue())) {
      LOGGER.info("Found duplicate Engine Node for name='" + name + "', removing it");
      engineNodeAgent.delete(dataRefResult.getValue());
    }

    String hostName = HostNameRetriever.getHostName();
    Date timeStamp = new Date();

    EngineNodeDetails engineNode = new EngineNodeDetails();
    engineNode.setName(name);
    engineNode.setHostname(hostName);
    engineNode.setActiveSince(timeStamp);
    engineNode.setLastActive(timeStamp);
    engineNode.setStatus(EngineNodeState.RECOVERING.getStatus());
    engineNode.setMaster(false);

    CrudsResult<DataRef> result = engineNodeAgent.create(engineNode);
    if (result.isSuccess()) {
      LOGGER.info("Registered Engine Node, name='" + name + "'");
      return result.getValue();
    } else {
      LOGGER.error("Failed to register Engine Node, name='" + name + "'");
      return NullDataRef.EMPTY_DATA_REF;
    }
  }

  public synchronized void deregisterNode() {
    EngineNodeContext engineNodeContext = getEngineNodeContext();
    DataRef engineNode = engineNodeContext.getEngineNode();
    LOGGER.info("Unregistering Engine Node, name='" + engineNode.getName() + "'");
    engineNodeAgent.delete(engineNode);
  }

  public void updateLastActive() {
    EngineNodeContext engineNodeContext = getEngineNodeContext();
    DataRef engineNode = engineNodeContext.getEngineNode();
    if (engineNodeAgent.postHealthCheck(engineNode).isError()) {
      LOGGER.error("Failed to perform postHealthCheck for Engine Node");
    }
  }

  public void activateEngineNodes() {
    if (engineNodeAgent.activateEngineNodes().isError()) {
      LOGGER.error("Failed to activate Engine Nodes");
    }
  }

  private EngineNodeContext getEngineNodeContext() {
    if (context.getContext(EngineNodeContext.class).isEmpty()) {
      throw new IllegalStateException("Missing Engine Node context");
    }
    return context.getContext(EngineNodeContext.class).getValue();
  }
}
