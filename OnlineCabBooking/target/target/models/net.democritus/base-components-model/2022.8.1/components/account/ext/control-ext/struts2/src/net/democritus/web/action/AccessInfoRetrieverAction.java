package net.democritus.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import net.democritus.acl.AccessQueryBuilder;
import net.democritus.acl.AuthorizationManagerAgent;
import net.democritus.acl.DataAccessFunctionality;
import net.democritus.acl.DataAccessQuery;
import net.democritus.acl.DataAccessRights;
import net.democritus.acl.TaskAccessRights;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.sys.TaskResult;
import net.democritus.sys.UserContext;
import net.democritus.utils.action.StrutsParameterRetriever;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import net.palver.util.Options;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * TODO the SearchDataRefContext should receive a valid UserContext instead of null.
 */
// @feature:authorization
public class AccessInfoRetrieverAction implements SessionAware {

  private static final Logger logger = LoggerFactory.getLogger(AccessInfoRetrieverAction.class);

  private static final String NOACCESS = Action.LOGIN;

  private Map<String, Object> session;
  private String actionName;
  private Options.Option<String> optCommandName;
  private Options.Option<String> optTaskName;

  public AccessInfoRetrieverAction() {
    session = ActionContext.getContext().getSession();
  }

  public String execute() throws Exception {
    ActionContext context = ActionContext.getContext();
    actionName = context.getName();

    optCommandName = StrutsParameterRetriever.findParameterValue(context, "commandName");
    optTaskName = StrutsParameterRetriever.findParameterValue(context, "taskName");

    if (isResourceAction(actionName)) {
      // not an <element>-<operation> action
      return Action.SUCCESS;
    }

    int operationIndex = actionName.indexOf("-");
    String elementName = actionName.substring(0, operationIndex);
    String operationName = actionName.substring(operationIndex + 1);
    String componentName = getComponentName(context);

    UserContext userContext = UserContextRetriever.getUserContext();
    AuthorizationManagerAgent authorizationManagerAgent = AuthorizationManagerAgent.getAuthorizationManagerAgent(userContext);
    if (isTaskOperation(operationName)) {
      return validateTaskAction(componentName, optTaskName.getValue(), authorizationManagerAgent);
    } else {
      return validateDataAction(componentName, elementName, operationName, authorizationManagerAgent);
    }
  }

  private String validateTaskAction(String componentName, String elementName, AuthorizationManagerAgent authorizationAgent) {
    DataAccessQuery dataAccessQuery = new AccessQueryBuilder()
        .setComponent(componentName)
        .setElement(elementName)
        .createAccessQuery();

    TaskResult<TaskAccessRights> taskAccessRights = authorizationAgent.getTaskAccessRights(dataAccessQuery);
    if (taskAccessRights.isError()) {
      logger.error("Could not retrieve task access rights for '" + dataAccessQuery + "'");
      return NOACCESS;
    }

    return taskAccessRights.getValue().isCanExecute() ? Action.SUCCESS : NOACCESS;
  }

  private String validateDataAction(String componentName, String elementName, String operationName, AuthorizationManagerAgent authorizationAgent) {
    DataAccessQuery dataAccessQuery = new AccessQueryBuilder()
        .setComponent(componentName)
        .setElement(elementName)
        .createAccessQuery();

    TaskResult<DataAccessRights> dataAccessRights = authorizationAgent.getDataAccessRights(dataAccessQuery);
    if (dataAccessRights.isError()) {
      logger.error("Could not retrieve data access rights for '" + dataAccessQuery + "'");
      return NOACCESS;
    }

    return hasAccess(dataAccessRights.getValue(), operationName) ? Action.SUCCESS : NOACCESS;
  }

  private String getComponentName(ActionContext context) {
    String namespace = context.getActionInvocation().getProxy().getNamespace();
    return namespace.substring(namespace.lastIndexOf("/") + 1);
  }

  private boolean isResourceAction(String actionName) {
    return !actionName.contains("-");
  }

  private boolean hasAccess(DataAccessRights accessRights, String operationName) {
    if (isDeleteOperation(operationName)) {
      return accessRights.isAllowed(DataAccessFunctionality.DELETE);
    }
    if (operationName.startsWith("saveDetails")) {
      return accessRights.isAllowed(DataAccessFunctionality.MODIFY) || accessRights.isAllowed(DataAccessFunctionality.CREATE);
    }
    if (isModifyOperation(operationName)) {
      return accessRights.isAllowed(DataAccessFunctionality.MODIFY);
    }
    if (isCreateOperation(operationName)) {
      return accessRights.isAllowed(DataAccessFunctionality.CREATE);
    }
    if (isViewOperation(operationName)) {
      return accessRights.isAllowed(DataAccessFunctionality.VIEW);
    }
    if (optCommandName.isDefined() && isCommandOperation(operationName)) {
      return accessRights.isAllowed(optCommandName.getValue());
    }
    return true;
  }

  private boolean isViewOperation(String operation) {
    return isOperationInSet(operation,
        "detail",
        "status",
        "retrieve",
        "view",
        "report",
        "find",
        "search",
        "getProjection",
        "idretrieve");
  }

  private boolean isCreateOperation(String operation) {
    return isOperationInSet(operation,
        "entry",
        "indirectForm",
        "detailForm");
  }

  private boolean isModifyOperation(String operation) {
    return isOperationInSet(operation,
        "modify");
  }

  private boolean isTaskOperation(String operation) {
    return (isOperationInSet(operation,
        "perform") && !operation.contains("-command-"));
  }

  private boolean isCommandOperation(String operation) {
    return isOperationInSet(operation,
        "perform-command");
  }

  private boolean isDeleteOperation(String operation) {
    return isOperationInSet(operation, "delete");
  }

  private boolean isOperationInSet(String operation, String... operations) {
    for (String candidate : operations) {
      if (operation.startsWith(candidate)) {
        return true;
      }
    }

    return false;
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

}
