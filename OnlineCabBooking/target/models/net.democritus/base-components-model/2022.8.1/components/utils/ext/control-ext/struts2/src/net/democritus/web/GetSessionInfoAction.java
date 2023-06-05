package net.democritus.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.Action;
import net.democritus.json.JsonResult;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

//@feature:session-info
public class GetSessionInfoAction extends ActionSupport {

  private static final long serialVersionUID = 1L;

  private transient SessionInfo sessionInfo;

  @Override
  public String execute() {
    if (!ServletActionContext.getRequest().getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }

    HttpSession httpSession = ServletActionContext.getRequest().getSession();
    int timeOut = httpSession.getMaxInactiveInterval();

    sessionInfo = new SessionInfo().setTimeOut(timeOut);

    return SUCCESS;
  }

  public JsonResult<SessionInfo> getJsonResult() {
    return JsonResult.createValue(sessionInfo);
  }
}