package net.democritus.workflow;

import net.democritus.state.StateUpdate;
import net.democritus.sys.*;
import net.democritus.sys.search.SearchDetails;
import net.democritus.wfe.EngineNodeContext;
import net.democritus.wfe.EngineNodeServiceDetails;
import net.democritus.wfe.EngineNodeServiceUpdateDetails;
import net.democritus.wfe.EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails;
import net.democritus.wfe.EngineNodeServiceLocalAgent;
import net.democritus.wfe.EngineNodeServiceState;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.util.Date;

// @feature:multi-node-workflow
public class EngineNodeServiceUpdater {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineNodeServiceUpdater.class);

  private final Context context;
  private final EngineNodeServiceLocalAgent engineNodeServiceAgent;

  public EngineNodeServiceUpdater(Context context) {
    this.context = context;
    engineNodeServiceAgent = EngineNodeServiceLocalAgent.getEngineNodeServiceAgent(context.getContext(UserContext.class).orElse(UserContext.NO_USER_CONTEXT));
  }

  public void registerNewEngineNodeService(EngineServiceUpdate update) {
    DataRef engineNode = getEngineNodeContext().getEngineNode();
    DataRef engineService = update.getEngineService();

    EngineNodeServiceDetails details = new EngineNodeServiceDetails();
    details.setName(engineService.getName());
    details.setEngineService(engineService);
    details.setEngineNode(engineNode);
    details.setLastRunAt(null);
    details.setNextRun(update.getNextRun());
    details.setStatus(update.getStatus().getStatus());

    CrudsResult<DataRef> result = engineNodeServiceAgent.create(details);
    if (result.isSuccess()) {
      LOGGER.info("Registered Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "'");
    } else {
      LOGGER.error("Failed to register Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "'");
    }
  }

  public void updateEngineNodeService(EngineServiceUpdate update) {
    DataRef engineNode = getEngineNodeContext().getEngineNode();
    DataRef engineService = update.getEngineService();

    if (update.getExpectedStatus() != EngineNodeServiceState.NOT_MAPPED) {
      StateUpdate stateUpdate = new StateUpdate();
      stateUpdate.setTarget(engineService);
      stateUpdate.setExpectedStatus(update.getExpectedStatus().getStatus());
      stateUpdate.setTargetStatus(update.getStatus().getStatus());

      CrudsResult<Void> updateStatusResult = engineNodeServiceAgent.compareAndSetStatus(stateUpdate);
      if (updateStatusResult.isError()) {
        LOGGER.info("Failed to update Engine Node Service, node='" + engineNode.getName() + "', " +
            "engineService='" + engineService.getName() + "' to status='" + update.getStatus().getStatus() + "' because status " +
            "does not match expectedStatus='" + update.getExpectedStatus().getStatus() + "'");
        return;
      }
    }

    EngineNodeServiceUpdateDetails details = findEngineNodeService(engineService, engineNode);
    details.setNextRun(update.getNextRun());
    if (update.getExpectedStatus() == EngineNodeServiceState.NOT_MAPPED) {
      details.setStatus(update.getStatus().getStatus());
    }
    if (update.getLastRun() != null) {
      details.setLastRunAt(update.getLastRun());
    }

    CrudsResult<DataRef> result = engineNodeServiceAgent.modifyWithProjection(details);
    if (result.isSuccess()) {
      LOGGER.debug("Updated Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "', status='" + update.getStatus().getStatus() + "'");
    } else {
      LOGGER.error("Failed to update Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "'");
    }
  }

  public void setStateToWorking(EngineServiceUpdate update) {
    update.setLastRun(new Date());
    update.setStatus(EngineNodeServiceState.WORKING);
    updateEngineNodeService(update);
  }

  public void setStateToWaiting(EngineServiceUpdate update) {
    update.setStatus(EngineNodeServiceState.WAITING);
    updateEngineNodeService(update);
  }

  public void removeEngineNodeService(EngineServiceUpdate update) {
    DataRef engineNode = getEngineNodeContext().getEngineNode();
    DataRef engineService = update.getEngineService();

    EngineNodeServiceUpdateDetails details = findEngineNodeService(engineService, engineNode);

    CrudsResult<Void> result = engineNodeServiceAgent.delete(details.getDataRef());
    if (result.isSuccess()) {
      LOGGER.debug("Removed Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "', status='" + update.getStatus().getStatus() + "'");
    } else {
      LOGGER.error("Failed to remove Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "'");
    }
  }

  private EngineNodeServiceUpdateDetails findEngineNodeService(DataRef engineService, DataRef engineNode) {
    EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails finder = new EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails();
    finder.setEngineNode(engineNode);
    finder.setEngineService(engineService);

    SearchDetails<EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails> searchDetails = SearchDetails.fetchN(finder, 1);
    searchDetails.setProjection("updateDetails");
    searchDetails.setSkipCount(true);
    SearchResult<EngineNodeServiceUpdateDetails> searchResult = engineNodeServiceAgent.find(searchDetails);
    if (searchResult.isError() || searchResult.getFirst().isEmpty()) {
      throw new IllegalStateException("Cannot find Engine Node Service, node='" + engineNode.getName() + "', engineService='" + engineService.getName() + "'");
    }
    return searchResult.getFirst().getValue();
  }

  private EngineNodeContext getEngineNodeContext() {
    if (context.getContext(EngineNodeContext.class).isEmpty()) {
      throw new IllegalStateException("Missing Engine Node context");
    }
    return context.getContext(EngineNodeContext.class).getValue();
  }

}
