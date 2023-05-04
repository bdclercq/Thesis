package net.democritus.workflow;

import net.democritus.sys.Context;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.democritus.wfe.EngineNodeDetails;
import net.democritus.wfe.EngineNodeFindByLastActiveLtDetails;
import net.democritus.wfe.EngineNodeLocalAgent;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

// @feature:multi-node-workflow
public class EngineNodeExpiredNodeCleaner {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineNodeExpiredNodeCleaner.class);

  private final EngineNodeLocalAgent engineNodeAgent;

  public EngineNodeExpiredNodeCleaner(Context context) {
    engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(context);
  }

  public void cleanExpiredNodes() {
    LOGGER.debug("Cleaning expired engine nodes");

    SearchResult<EngineNodeDetails> searchResult = findExpiredEngineNodes();
    if (searchResult.isSuccess() && !searchResult.getResults().isEmpty()) {
      for (EngineNodeDetails node : searchResult.getResults()) {
        updateInactiveNode(node);
      }
      LOGGER.debug("Found " + searchResult.getTotalNumberOfItems() + " inactive nodes");
    }
  }

  private SearchResult<EngineNodeDetails> findExpiredEngineNodes() {
    EngineNodeFindByLastActiveLtDetails finder = new EngineNodeFindByLastActiveLtDetails();
    finder.setLastActive(EngineNodeConfig.getExpirationCutOffTime());
    SearchDetails<EngineNodeFindByLastActiveLtDetails> searchDetails = SearchDetails.fetchAllDetails(finder);
    return engineNodeAgent.find(searchDetails);
  }

  private void updateInactiveNode(EngineNodeDetails node) {
    if (node.getLastActive().before(EngineNodeConfig.getCleanupCutOffTime())) {
      LOGGER.info("Removing inactive engine node '" + node.getName() + "'");
      if (engineNodeAgent.delete(node.getDataRef()).isError()) {
        LOGGER.error("Failed to remove inactive engine node '" + node.getName() + "'");
      }
    } else {
      if (engineNodeAgent.setNotResponding(node.getDataRef()).isError()) {
        LOGGER.error("Failed to update engine node '" + node.getName() + "' to not responding");
      }
    }
  }

}
