package net.democritus.workflow;

import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.democritus.wfe.*;

/**
 * Implements the failover check for the master engine node.
 * The first node to do the check is promoted to master. However, if the master node fails, another engine node will take over.
 */
// @feature:multi-node-workflow
public class EngineNodeMasterFailOverChecker {
  private final Context context;
  private final EngineNodeLocalAgent engineNodeAgent;

  public EngineNodeMasterFailOverChecker(Context context) {
    this.context = context;
    engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(context.getContext(UserContext.class).orElse(UserContext.NO_USER_CONTEXT));
  }

  /**
   * Checks whether this node is the master node.
   * <p>
   * If this node already was the master node, the method returns with a positive result.
   * If it is not the master and there is currently no master node, it sets this node as the master node and returns a positive result.
   * If it is not the master and the current master has been inactive for a certain duration, it 'steals' the master flag and returns a positive result.
   * If it is not the master and the current master is still valid, it returns a negative result.
   *
   * @return A Result instance which defines if the current engine node is the master node
   */
  public Result checkIfThisNodeCanBeMaster() {
    EngineNodeContext engineNodeContext = getEngineNodeContext();
    CrudsResult<EngineNodeDetails> result = engineNodeAgent.getDetails(engineNodeContext.getEngineNode());
    if (result.isError()) {
      return new Result(false);
    }
    EngineNodeDetails thisNode = result.getValue();

    if (isAlreadyMaster(thisNode)) {
      return new Result(true);
    }

    return tryToReplaceCurrentMaster(thisNode);

  }

  private Result tryToReplaceCurrentMaster(EngineNodeDetails thisNode) {
    SearchResult<EngineNodeDetails> searchResult = findMaster();
    if (searchResult.isError()) {
      return new Result(false);
    }

    if (searchResult.getFirst().isEmpty()) {
      CrudsResult<Void> promotionResult = promote(thisNode);
      return new Result(promotionResult.isSuccess());
    } else {
      EngineNodeDetails masterNode = searchResult.getFirst().getValue();
      if (isExpired(masterNode)) {
        demote(masterNode);
        CrudsResult<Void> promotionResult = promote(thisNode);
        return new Result(promotionResult.isSuccess());
      } else {
        return new Result(false);
      }
    }
  }

  private SearchResult<EngineNodeDetails> findMaster() {
    EngineNodeFindByMasterEqDetails finder = new EngineNodeFindByMasterEqDetails();
    finder.setMaster(true);

    SearchDetails<EngineNodeFindByMasterEqDetails> searchDetails = SearchDetails.fetchNDetails(finder, 1);
    searchDetails.setSkipCount(true);
    return engineNodeAgent.find(searchDetails);
  }

  private boolean isAlreadyMaster(EngineNodeDetails thisNode) {
    return thisNode.getMaster() != null
        && thisNode.getMaster();
  }

  private void demote(EngineNodeDetails masterNode) {
    masterNode.setMaster(false);
    masterNode.setStatus(EngineNodeState.NOT_RESPONDING.getStatus());
    engineNodeAgent.modify(masterNode);
  }

  private CrudsResult<Void> promote(EngineNodeDetails thisNode) {
    return engineNodeAgent.promoteToMaster(thisNode.getDataRef());
  }

  private boolean isExpired(EngineNodeDetails masterNode) {
    return masterNode.getLastActive() == null
        || masterNode.getLastActive().before(EngineNodeConfig.getExpirationCutOffTime());
  }

  private EngineNodeContext getEngineNodeContext() {
    if (context.getContext(EngineNodeContext.class).isEmpty()) {
      throw new IllegalStateException("Missing Engine Node context");
    }
    return context.getContext(EngineNodeContext.class).getValue();
  }

  public static class Result {
    private final boolean isMaster;

    Result(boolean isMaster) {
      this.isMaster = isMaster;
    }
    public boolean isMaster() {
      return isMaster;
    }

  }
}
