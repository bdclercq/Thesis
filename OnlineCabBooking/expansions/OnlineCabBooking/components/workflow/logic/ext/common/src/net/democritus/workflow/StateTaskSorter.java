package net.democritus.workflow;

import net.democritus.collections.AcyclicSubGraphFinder;
import net.democritus.collections.DirectedGraph;
import net.democritus.collections.GraphToListSorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// @feature:workflow
public class StateTaskSorter {

  public List<StateTaskDetails> sort(Collection<StateTaskDetails> stateTasks) {
    DirectedGraph<String, StateTaskTransition> directedGraph = new DirectedGraph<String, StateTaskTransition>();

    for (StateTaskDetails stateTask : stateTasks) {
      directedGraph.addEdge(stateTask.getBeginState(), stateTask.getEndState(), new StateTaskTransition(stateTask, "success"));
      directedGraph.addEdge(stateTask.getBeginState(), stateTask.getFailedState(), new StateTaskTransition(stateTask, "failure"));
    }

    DirectedGraph<String, StateTaskTransition> directedAcyclicGraph = new AcyclicSubGraphFinder<String, StateTaskTransition>(directedGraph).findDirectedAcyclicGraph();
    List<String> sortedStates = new GraphToListSorter<String, StateTaskTransition>(directedAcyclicGraph).sort();

    return mapStatesToStateTasks(directedGraph, sortedStates);
  }

  private ArrayList<StateTaskDetails> mapStatesToStateTasks(DirectedGraph<String, StateTaskTransition> directedGraph, List<String> sortedStates) {
    ArrayList<StateTaskDetails> sortedStateTasks = new ArrayList<StateTaskDetails>();
    for (String state : sortedStates) {
      Collection<StateTaskTransition> outgoingEdges = directedGraph.getOutgoingEdges(state);
      for (StateTaskTransition outgoingEdge : outgoingEdges) {
        if (!sortedStateTasks.contains(outgoingEdge.stateTaskDetails)) {
          sortedStateTasks.add(outgoingEdge.stateTaskDetails);
        }
      }
    }
    return sortedStateTasks;
  }

  private static class StateTaskTransition {

    private final StateTaskDetails stateTaskDetails;
    private final String postFix;

    StateTaskTransition(StateTaskDetails stateTaskDetails, String postFix) {
      this.stateTaskDetails = stateTaskDetails;
      this.postFix = postFix;
    }

    @Override
    public String toString() {
      return stateTaskDetails.getName() + "_" + postFix;
    }
  }


}
