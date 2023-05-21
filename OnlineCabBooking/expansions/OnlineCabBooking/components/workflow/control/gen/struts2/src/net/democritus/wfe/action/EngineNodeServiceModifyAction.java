package net.democritus.wfe.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.json.DiagnosticsToStrutsMapper;
import net.democritus.json.JsonResult;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.search.Paging;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import net.palver.util.StringUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import workflow.context.ContextRetriever;

import static net.palver.util.Options.Option;
import static net.palver.util.Options.none;
import static net.palver.util.Options.some;

import net.democritus.wfe.EngineNodeServiceAgent;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineNodeServiceModifyAction extends ActionSupport implements Preparable {

  private static final long serialVersionUID = 1L;

  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger("net.democritus.wfe.action.EngineNodeServiceModifyAction");

  private Object details;

  private CrudsResult<DataRef> crudsResult;
  private Paging paging = new Paging();

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Object getDetails() {
    return details;
  }

  public void setDetails(Object details) {
    this.details = details;
  }

  /* end transport data */

  @Override
  public void prepare() throws Exception {
    HttpServletRequest request = ServletActionContext.getRequest();
    String projectionName = request.getParameter("projection");

    if (projectionName == null) {
      throw new RuntimeException("Parameter 'projection' not specified");
    }

    Option<Class> optClazz = getProjectionClass("net.democritus.wfe", "EngineNodeService", projectionName);
    if (optClazz.isEmpty()) {
      throw new RuntimeException("projection not found: " + projectionName);
    }

    details = optClazz.getValue().newInstance();

    // anchor:custom-preparation:start
    // anchor:custom-preparation:end
  }

  private static Option<Class> getProjectionClass(String packageName, String elementName, String projectionName) {
    String className = elementName + StringUtil.firstToUpperCase(projectionName);
    String qualifiedName = packageName + "." + className;
    try {
      Class clazz = Class.forName(qualifiedName);
      return some(clazz);
    } catch (ClassNotFoundException e) {
      logger.error("Class " + qualifiedName + " not found for projection: " + projectionName);
      return none();
    }
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    if (!httpServletRequest.getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    String actionResult;

    DataRef dataRef = null;

    // anchor:special-fields:start
    // anchor:special-fields:end

    crudsResult = createEngineNodeServiceAgent().modifyWithProjection(details);

    if (crudsResult.isSuccess()) {
      dataRef = crudsResult.getValue();

      actionResult = Action.SUCCESS;
    } else {
      actionResult = Action.INPUT;
    }
    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;

  }

  public JsonResult<DataRef> getJsonResult() {

    if (crudsResult == null) {
      // there were validation or conversion errors
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }

    if (crudsResult.isSuccess()) {
      return JsonResult.createValue(crudsResult.getValue(), getActionMessages());
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  private static EngineNodeServiceAgent createEngineNodeServiceAgent() {
    return EngineNodeServiceAgent.getEngineNodeServiceAgent(ContextRetriever.getContext());
  }

  /**
   * @deprecated Use {@link ContextRetriever} instead
   */
  @Deprecated
  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
