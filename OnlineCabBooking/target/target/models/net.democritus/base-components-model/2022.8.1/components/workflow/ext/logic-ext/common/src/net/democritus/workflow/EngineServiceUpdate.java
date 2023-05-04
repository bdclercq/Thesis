package net.democritus.workflow;

import net.democritus.sys.DataRef;
import net.democritus.wfe.EngineNodeServiceState;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
// @feature:multi-node-workflow
public class EngineServiceUpdate {

  private DataRef engineService;
  private Date lastRun;
  private Date nextRun;
  private EngineNodeServiceState expectedStatus = EngineNodeServiceState.NOT_MAPPED;
  private EngineNodeServiceState status = EngineNodeServiceState.WAITING;

  public DataRef getEngineService() {
    return engineService;
  }

  public void setEngineService(DataRef engineService) {
    this.engineService = engineService;
  }

  public Date getLastRun() {
    return lastRun;
  }

  public void setLastRun(Date lastRun) {
    this.lastRun = lastRun;
  }

  public Date getNextRun() {
    return nextRun;
  }

  public void setNextRun(Date nextRun) {
    this.nextRun = nextRun;
  }

  public EngineNodeServiceState getStatus() {
    return status;
  }

  public void setStatus(EngineNodeServiceState status) {
    this.status = status;
  }

  public EngineNodeServiceState getExpectedStatus() {
    return expectedStatus;
  }

  public void setExpectedStatus(EngineNodeServiceState expectedStatus) {
    this.expectedStatus = expectedStatus;
  }
}
