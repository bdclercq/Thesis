package net.democritus.web.common;

import com.opensymphony.xwork2.ActionContext;
import net.democritus.sys.UserContext;
import org.apache.struts2.ServletActionContext;

import java.util.Map;

// @feature:unique-session
public class UniqueSessionRegistrar {

  private UniqueSessionKeeper uniqueSessionKeeper = new UniqueSessionKeeper();

  public void registerSession(UserContext userContext) throws DuplicateUserSessionException {
    Map sessionMap = getSessionMap(ActionContext.getContext());
    String username = userContext.getUserName();
    if (sessionMap.containsKey(username)) {
      throw new DuplicateUserSessionException();
    } else {
      sessionMap.put(username, ServletActionContext.getRequest().getSession());
    }
  }

  private Map getSessionMap(ActionContext actionContext) {
    return uniqueSessionKeeper.getOrInitSessionMap(actionContext);
  }

}
