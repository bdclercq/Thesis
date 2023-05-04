package net.democritus.acl.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.acl.AccessQueryBuilder;
import net.democritus.acl.AuthorizationManagerAgent;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.DataAccessRights;
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
public class GetAccessRightsAction extends ActionSupport {

  private static final Logger logger = LoggerFactory.getLogger(GetAccessRightsAction.class);

  private String element;
  private String component;
  private transient Option<DataAccessRights> dataAccessRights = none();

  public String getElement() {
    return element;
  }

  public void setElement(String element) {
    this.element = element;
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

    return getDataAccessRights();
  }

  public String getDataAccessRights() {
    DataAccessQuery dataAccessQuery = new AccessQueryBuilder()
        .setComponent(component)
        .setElement(element)
        .createAccessQuery();

    AuthorizationManagerAgent authorizationAgent = AuthorizationManagerAgent.getAuthorizationManagerAgent(getUserContext());
    TaskResult<DataAccessRights> taskResult = authorizationAgent.getDataAccessRights(dataAccessQuery);
    if (taskResult.isSuccess()) {
      DataAccessRights rights = taskResult.getValue();
      dataAccessRights = some(rights);
    }
    return SUCCESS;
  }

  public JsonResult<DataAccessRights> getJsonResult() {
    if (dataAccessRights.isDefined()) {
      return JsonResult.createValue(dataAccessRights.getValue());
    } else {
      String errorMessage = "Profile access rights not found: " + component + "#" + element;
      logger.error(errorMessage);
      return JsonResult.createError(errorMessage);
    }
  }

  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }

}