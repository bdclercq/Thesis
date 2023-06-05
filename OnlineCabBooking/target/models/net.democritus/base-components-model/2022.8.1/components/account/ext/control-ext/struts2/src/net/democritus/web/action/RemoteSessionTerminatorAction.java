package net.democritus.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import net.democritus.web.common.SessionAttribute;
import net.democritus.web.common.UniqueSessionRetriever;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Map;

// @feature:unique-session
public class RemoteSessionTerminatorAction {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteSessionTerminatorAction.class);

  public String execute() throws Exception {
    try {
      String username = getUsername();
      final HttpSession httpSession = new UniqueSessionRetriever().getSession(username);
      httpSession.invalidate();
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Forcing session removal for " + username);
      }
      return Action.LOGIN;
    } catch (Exception e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Could not remove session", e);
      }
      return Action.ERROR;
    }
  }

  private String getUsername() {
    Map<String, Object> session = ActionContext.getContext().getSession();
    Object username = session.get(SessionAttribute.USERNAME);
    return (String) username;
  }

}
