package net.democritus.wfe;

import javax.ejb.Timer;
import javax.ejb.TimerService;
import java.io.Serializable;

// @feature:engine-service
public class DefaultTimerFactoryImpl implements TimerFactoryImpl {

  @Override
  public Timer createTimer(TimerService timerService, long waitTime, Serializable timerInfo, TimerOptions timerOptions) {
    long timeInterval = (long) (timerOptions.getDelayTime() * 1000);

    return timerService.createTimer(
        timeInterval,
        waitTime * 1000L,
        timerInfo);
  }

}
