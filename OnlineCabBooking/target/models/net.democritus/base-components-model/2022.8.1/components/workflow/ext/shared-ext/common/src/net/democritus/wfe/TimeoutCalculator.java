package net.democritus.wfe;

import net.democritus.workflow.StateTaskDetails;

import java.util.Date;

/**
 * Calculates the time until which claims is valid
 */
// @feature:claims
public class TimeoutCalculator {

  // default is 5 minutes
  long defaultTimeout = 5 * 60 * 1000L;

  public Date calculateTimeout(StateTaskDetails stateTaskDetails) {
    return calculateTimeout(stateTaskDetails, new Date());
  }

  public Date calculateTimeout(StateTaskDetails stateTaskDetails, Date startTime) {
    long time = startTime.getTime();
    time += getTimeout(stateTaskDetails);
    return new Date(time);
  }

  private long getTimeout(StateTaskDetails stateTaskDetails) {
    if (stateTaskDetails.getTimeout() != null) {
      return stateTaskDetails.getTimeout();
    } else {
      return defaultTimeout;
    }
  }

}
