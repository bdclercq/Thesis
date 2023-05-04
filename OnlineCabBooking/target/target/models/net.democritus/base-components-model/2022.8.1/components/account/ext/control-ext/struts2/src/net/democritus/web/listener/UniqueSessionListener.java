package net.democritus.web.listener;

import net.democritus.web.common.UniqueSessionUnregistrar;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// @feature:unique-session
public class UniqueSessionListener implements HttpSessionListener {

  private final UniqueSessionUnregistrar uniqueSessionUnregistrar = new UniqueSessionUnregistrar();

  @Override
  public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    // unused
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    uniqueSessionUnregistrar.unregisterSession(httpSessionEvent);
  }

}
