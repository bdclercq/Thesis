package net.democritus.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import net.democritus.sys.UserContext;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

// @feature:struts-navigation
public class ScreenInfoRetrieverAction implements SessionAware {

  private Map<String, Object> session;
  private String actionName;

  public String execute() throws Exception {
    return Action.SUCCESS;
  }

  @Override
  public void setSession(Map<String, Object> session) {
    this.session = session;
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
  }

  protected Map<String, Object> getSession() {
    if (session == null || session.size() == 0) {
      try {
        session = ActionContext.getContext().getSession();
      } catch (Exception e) {
        // ignore
      }
    }
    return session;
  }

  protected String getActionName() {
    if (this.actionName == null || this.actionName.length() == 0) {
      actionName = ActionContext.getContext().getName();
    }
    return actionName;
  }

  protected UserContext getUserContext() {
    UserContext userContext = (UserContext) getSession().get("userContext");
    if (userContext != null) {
      return userContext;
    } else {
      return UserContext.NO_USER_CONTEXT;
    }
  }

}
