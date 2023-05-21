package cabBookingCore;

import java.io.Serializable;

import net.democritus.state.StateViewer;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;

// anchor:imports:start
import cabBookingCore.TripBookingDetails;
import cabBookingCore.TripBookingState;
import cabBookingCore.TripBookingLocalAgent;
// anchor:imports:end

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

/**
 * Provides a number of methods to view the status field of a(n) TripBooking instance.
 */
public class TripBookingFlowStateViewer implements StateViewer<TripBookingState>, Serializable {
  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingFlowStateViewer.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /**
   * Get the current status of the target TripBooking instance as a String value.
   *
   * @param parameter a parameterContext containing a dataRef to the target order instance
   *
   * @return  CrudsSucces with the status value if getting the status was successful
   *          CrudsError otherwise
   */
  @Override
  public CrudsResult<String> getStatus(ParameterContext<DataRef> parameter) {
    CrudsResult<TripBookingDetails> detailsResult = getDetails(parameter);
    if (detailsResult.isError()) {
      return detailsResult.convertError();
    }
    String status = detailsResult.getValue().getStatus();
    return CrudsResult.success(status);
  }

  /**
   * Get the current status of the target TripBooking instance as an Enum value.
   *
   * @param parameter a parameterContext containing a dataRef to the target order instance
   *
   * @return  CrudsSucces with the status value if getting the status was successful
   *          CrudsError otherwise
   */
  @Override
  public CrudsResult<TripBookingState> getStatusAsEnum(ParameterContext<DataRef> parameter) {
    CrudsResult<TripBookingDetails> detailsResult = getDetails(parameter);
    if (detailsResult.isError()) {
      return detailsResult.convertError();
    }
    String status = detailsResult.getValue().getStatus();
    return CrudsResult.success(TripBookingState.getTripBookingState(status));
  }

  private CrudsResult<TripBookingDetails> getDetails(ParameterContext<DataRef> parameter) {
    TripBookingLocalAgent tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(parameter.getContext());
    return tripBookingAgent.getDetails(parameter.getValue());
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
