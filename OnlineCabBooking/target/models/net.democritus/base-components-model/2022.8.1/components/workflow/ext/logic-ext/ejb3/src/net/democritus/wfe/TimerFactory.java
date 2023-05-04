package net.democritus.wfe;

import net.democritus.properties.RuntimeProperties;

import javax.ejb.Timer;
import javax.ejb.TimerService;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

// @feature:engine-service
public class TimerFactory {
  private static final String EJB_RUNTIME_KEY = "technologies.ejb.version";

  private static final Class<DefaultTimerFactoryImpl> DEFAULT_IMPL = DefaultTimerFactoryImpl.class;
  private static final HashMap<String, String> implementations = new HashMap<String, String>();
  static {
    implementations.put("ejb3.0", "net.democritus.wfe.DefaultTimerFactoryImpl");
    implementations.put("ejb3.1", "net.democritus.wfe.Ejb3_1TimerFactoryImpl");
  }

  private TimerService timerService;

  public TimerFactory(TimerService timerService) {
    this.timerService = timerService;
  }

  public Timer createTimer(int waitTime, Serializable timerInfo, TimerOptions timerOptions) {
    TimerFactoryImpl timerFactoryImpl = getTimerFactoryImplementation();
    return timerFactoryImpl.createTimer(timerService, waitTime, timerInfo, timerOptions);
  }

  private TimerFactoryImpl getTimerFactoryImplementation() {
    Class<? extends TimerFactoryImpl> implClass = getImplementationClass();

    try {
      return implClass.newInstance();
    } catch (Exception e) {
      throw new IllegalStateException("Could not initialize TimerFactory implementation from class '" + implClass.getCanonicalName() + "'", e);
    }
  }

  private Class<? extends TimerFactoryImpl> getImplementationClass() {
    String ejbVersion = RuntimeProperties.getRuntimeProperties().getEjbVersion();

    if (ejbVersion == null) {
      return DEFAULT_IMPL;
    }

    try {
      return (Class<? extends TimerFactoryImpl>) Class.forName(implementations.get(ejbVersion));
    } catch (Exception e) {
      throw new IllegalStateException("Could not find TimerFactory implementation class", e);
    }
  }


}
