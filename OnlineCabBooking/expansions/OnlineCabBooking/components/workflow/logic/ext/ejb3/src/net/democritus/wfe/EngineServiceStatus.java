package net.democritus.wfe;

import net.democritus.sys.DataRef;

import java.io.Serializable;

// @feature:engine-service
class EngineServiceStatus implements Serializable {
  private EngineServiceDetailsWithoutRefs engineService;
  private EngineNodeServiceDetails engineNodeService;
  private boolean busy;
  private boolean isWaitTimeChanged = false;

  EngineServiceStatus(EngineServiceDetailsWithoutRefs engineService) {
    this.engineService = engineService;
    busy = false;
  }

  EngineServiceStatus updateEngineServiceDetails(EngineServiceDetailsWithoutRefs engineServiceDetails) {
    validateChange(engineServiceDetails);
    engineService = engineServiceDetails;
    return this;
  }

  EngineServiceStatus updateEngineNodeServiceDetails(EngineNodeServiceDetails engineNodeServiceDetails) {
    engineNodeService = engineNodeServiceDetails;
    return this;
  }

  void validateChange(EngineServiceDetailsWithoutRefs engineServiceDetails) {
    if (getWaitTime().intValue() != engineServiceDetails.getWaitTime().intValue()) {
      this.isWaitTimeChanged = true;
    }
  }

  EngineServiceDetailsWithoutRefs getEngineService() {
    return engineService;
  }

  boolean isServiceStarted() {
    return engineService.getStatus().equals("start");
  }

  boolean isServiceBusy() {
    return busy;
  }

  boolean isAvailable() {
    return isServiceStarted()
        && !isServiceBusy()
        && (engineNodeService == null ||
        (engineNodeService.getStatusAsEnum() != EngineNodeServiceState.READY_FOR_SHUTDOWN
            && engineNodeService.getStatusAsEnum() != EngineNodeServiceState.SHUTTING_DOWN));
  }

  boolean isInTimewindow() throws Exception {
    return new TimeWindowValidator().validateTimeWindowGroup(DataRef.withId(engineService.getTimeWindowGroupId()));
  }

  Integer getWaitTime() {
    return engineService.getWaitTime();
  }

  long getEngineId() {
    return engineService.getId();
  }

  String getEngineName() {
    return engineService.getName();
  }

  String getEngineStatus() {
    return engineService.getStatus();
  }

  boolean isEnginePresent() {
    return engineService != null;
  }

  Long getWorkflowId() {
    return engineService.getWorkflowId();
  }

  void setBusy(boolean busy) {
    this.busy = busy;
  }

  public boolean isWaitTimeChanged() {
    return isWaitTimeChanged;
  }

  void resetChanged() {
    this.isWaitTimeChanged = false;
  }

  @Override
  public String toString() {
    return getEngineName();
  }

}
