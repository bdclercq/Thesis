package net.democritus.wfe;

import javax.ejb.Timer;
import javax.ejb.TimerService;
import java.io.Serializable;

// @feature:engine-service
public interface TimerFactoryImpl {
  Timer createTimer(TimerService timerService, long waitTime, Serializable timerInfo, TimerOptions timerOptions);
}
