package net.democritus.workflow;

import net.democritus.sys.Context;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.search.SearchDetails;
import net.democritus.wfe.EngineNodeDetails;
import net.democritus.wfe.EngineNodeFindByMasterEqDetails;
import net.democritus.wfe.EngineNodeLocalAgent;
import net.democritus.wfe.EngineNodeState;

// @feature:multi-node-workflow
public class EngineNodeMasterStatusViewer {
  private final Context context;
  private final EngineNodeLocalAgent engineNodeAgent;

  public EngineNodeMasterStatusViewer(Context context) {
    this.context = context;
    engineNodeAgent = EngineNodeLocalAgent.getEngineNodeAgent(context.getContext(UserContext.class).orElse(UserContext.NO_USER_CONTEXT));
  }

  public Result getMasterStatus() {
    SearchResult<EngineNodeDetails> masterResult = findMaster();
    if (masterResult.isError() || masterResult.getFirst().isEmpty()) {
      return new Result(null);
    }

    String status = masterResult.getFirst().getValue().getStatus();
    for (EngineNodeState engineNodeState : EngineNodeState.values()) {
      if (engineNodeState.getStatus().equals(status)) {
        return new Result(engineNodeState);
      }
    }
    return new Result(null);
  }

  private SearchResult<EngineNodeDetails> findMaster() {
    EngineNodeFindByMasterEqDetails finder = new EngineNodeFindByMasterEqDetails();
    finder.setMaster(true);

    SearchDetails<EngineNodeFindByMasterEqDetails> searchDetails = SearchDetails.fetchNDetails(finder, 1);
    return engineNodeAgent.find(searchDetails);
  }

  public static class Result {
    private EngineNodeState state;

    public Result(EngineNodeState state) {
      this.state = state;
    }

    public boolean isFound() {
      return state != null;
    }

    public boolean isRecovering() {
      return state == EngineNodeState.RECOVERING;
    }
  }
}
