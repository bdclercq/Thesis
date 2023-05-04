package net.democritus.web.common;

import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import java.util.Map;

// @feature:unique-session
public class UniqueSessionUnregistrar {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.web.common.UniqueSessionUnregistrar");
  private UniqueSessionKeeper uniqueSessionKeeper = new UniqueSessionKeeper();

  public void unregisterSession(HttpSessionEvent httpSessionEvent) {
    Map sessionMap = uniqueSessionKeeper.getSessionMap(httpSessionEvent.getSession());
    Object username = httpSessionEvent.getSession().getAttribute(SessionAttribute.USERNAME);
    if (sessionMap.containsKey(username)) {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Removing session from session list for " + username);
      }
      sessionMap.remove(username);
    }
  }

}