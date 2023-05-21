package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.FlowOrchestrator;
import net.democritus.workflow.WorkflowDetails;

/**
 * Local interface for the engine session bean of the flow TripBookingFlow.
 */

public interface TripBookingFlowEngineLocal extends FlowOrchestrator<Void,WorkflowDetails> {

    // anchor:custom-methods:start
    // anchor:custom-methods:end

}

