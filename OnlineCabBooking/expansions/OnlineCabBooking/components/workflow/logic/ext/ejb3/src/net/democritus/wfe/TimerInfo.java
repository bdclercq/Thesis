package net.democritus.wfe;

import net.democritus.workflow.EngineServiceUpdate;

import javax.ejb.Timer;

// @feature:engine-service
class TimerInfo {
  private final EngineServiceStatus engineServiceStatus;
  private final Timer timer;

  TimerInfo(EngineServiceStatus engineServiceStatus, Timer timer) {
    this.engineServiceStatus = engineServiceStatus;
    this.timer = timer;
  }

  public EngineServiceStatus getEngineServiceStatus() {
    return engineServiceStatus;
  }

  public String getEngineName() {
    return engineServiceStatus.getEngineName();
  }

  public Timer getTimer() {
    return timer;
  }


  public EngineServiceUpdate getUpdate() {
    EngineServiceUpdate update = new EngineServiceUpdate();
    update.setEngineService(engineServiceStatus.getEngineService().getDataRef());
    try {
      update.setNextRun(timer.getNextTimeout());
    } catch (Exception e) {
      update.setNextRun(null);
    }
    return update;
  }
}
