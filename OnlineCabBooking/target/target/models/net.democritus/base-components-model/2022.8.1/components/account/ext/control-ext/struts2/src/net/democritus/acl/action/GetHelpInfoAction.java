package net.democritus.acl.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.acl.HelpInfoAgent;
import net.democritus.acl.HelpInfoDetails;
import net.democritus.json.JsonResult;
import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.UserContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// @feature:help-messages
public class GetHelpInfoAction extends ActionSupport {

  private static final Logger logger = LoggerFactory.getLogger(GetHelpInfoAction.class);

  private String target;
  private String description;

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public String execute() {
    if (!ServletActionContext.getRequest().getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }

    HelpInfoAgent helpInfoAgent = HelpInfoAgent.getHelpInfoAgent(Context.from(getUserContext()));
    CrudsResult<HelpInfoDetails> result = helpInfoAgent.getDetails(helpInfoAgent.getId(target));
    if (result.isSuccess()) {
      description = result.getValue().getDescription();
      return SUCCESS;
    } else {
      return ERROR;
    }
  }

  public JsonResult<String> getJsonResult() {
    if (description != null) {
      return JsonResult.createValue(description);
    } else {
      String errorMessage = "Help info not found: " + target;
      logger.error(errorMessage);
      return JsonResult.createError(errorMessage);
    }
  }

  private static UserContext getUserContext() {
    Map<String, Object> session = ActionContext.getContext().getSession();

    return (UserContext) session.get("userContext");
  }

}
