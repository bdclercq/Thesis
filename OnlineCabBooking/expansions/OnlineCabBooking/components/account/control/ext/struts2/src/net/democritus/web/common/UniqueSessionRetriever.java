package net.democritus.web.common;

import com.opensymphony.xwork2.ActionContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Map;

// @feature:unique-session
public class UniqueSessionRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.web.common.UniqueSessionRetriever");
  private UniqueSessionKeeper uniqueSessionKeeper = new UniqueSessionKeeper();

  public HttpSession getSession(String username) {
    Map sessionMap = uniqueSessionKeeper.getSessionMap(ActionContext.getContext());
    Object userSession = sessionMap.get(username);
    if (userSession == null) {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("No session found for " + username);
      }
    }
    return (HttpSession) userSession;
  }

}
