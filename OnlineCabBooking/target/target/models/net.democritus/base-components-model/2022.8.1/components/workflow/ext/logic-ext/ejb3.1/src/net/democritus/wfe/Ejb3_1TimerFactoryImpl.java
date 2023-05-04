package net.democritus.wfe;

import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import java.io.Serializable;

// @feature:engine-service
public class Ejb3_1TimerFactoryImpl implements TimerFactoryImpl {

  @Override
  public Timer createTimer(TimerService timerService, long waitTime, Serializable timerInfo, TimerOptions timerOptions) {
    long timeInterval = (long) (timerOptions.getDelayTime() * 1000);

    TimerConfig timerConfig = new TimerConfig(timerInfo, false);

    return timerService.createIntervalTimer(
        timeInterval,
        waitTime * 1000L,
        timerConfig);
  }

}
