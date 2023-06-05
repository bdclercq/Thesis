package net.democritus.web.action;

import com.opensymphony.xwork2.Action;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// @feature:authentication
public class SessionTerminatorAction {

  private static final Logger logger = LoggerFactory.getLogger(SessionTerminatorAction.class);

  public String execute() throws Exception {

    final HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
    final HttpSession httpSession = httpServletRequest.getSession();

    httpSession.invalidate();

    return Action.LOGIN;
  }

}
