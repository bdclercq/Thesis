package net.democritus.acl.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.acl.AccessQueryBuilder;
import net.democritus.acl.AuthorizationManagerAgent;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.TaskAccessRights;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.json.JsonResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.UserContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import net.palver.util.Options.Option;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

import static net.palver.util.Options.none;
import static net.palver.util.Options.some;

// @feature:authorization
public class GetTaskAccessRightsAction extends ActionSupport {

  private static final Logger logger = LoggerFactory.getLogger(GetTaskAccessRightsAction.class);

  private String taskElement;
  private String component;
  private transient Option<TaskAccessRights> taskAccessRights = none();

  public String getElement() {
    return taskElement;
  }

  public void setElement(String element) {
    this.taskElement = element;
  }

  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }

  @Override
  public String execute() {
    if (!ServletActionContext.getRequest().getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }

    return getTaskAccessRights();
  }

  public String getTaskAccessRights() {
    DataAccessQuery dataAccessQuery = new AccessQueryBuilder()
        .setComponent(component)
        .setElement(taskElement)
        .createAccessQuery();

    AuthorizationManagerAgent authorizationAgent = AuthorizationManagerAgent.getAuthorizationManagerAgent(getUserContext());
    TaskResult<TaskAccessRights> taskResult = authorizationAgent.getTaskAccessRights(dataAccessQuery);
    if (taskResult.isSuccess()) {
      TaskAccessRights rights = taskResult.getValue();
      taskAccessRights = some(rights);
    }
    return SUCCESS;
  }

  public JsonResult<TaskAccessRights> getJsonResult() {
    if (taskAccessRights.isDefined()) {
      return JsonResult.createValue(taskAccessRights.getValue());
    } else {
      String errorMessage = "Profile access rights not found: " + component + "#" + taskElement;
      logger.error(errorMessage);
      return JsonResult.createError(errorMessage);
    }
  }

  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }

}
