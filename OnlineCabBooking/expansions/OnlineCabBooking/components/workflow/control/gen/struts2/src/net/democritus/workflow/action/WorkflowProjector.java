package net.democritus.workflow.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

import net.democritus.sys.ProjectionRef;
import workflow.context.ContextRetriever;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import net.democritus.workflow.WorkflowAgent;
import net.democritus.workflow.WorkflowDetails;

import net.democritus.sys.DataRef;
import net.democritus.sys.PageRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchDataRef;
import net.democritus.sys.SearchResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import net.democritus.acl.struts2.UserContextRetriever;

import net.democritus.sys.UserContext;
import net.democritus.json.JsonResult;
import net.democritus.json.DiagnosticsToStrutsMapper;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class WorkflowProjector extends ActionSupport {

  private CrudsResult<?> crudsResult;

  private String projection;
  private Long id;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(WorkflowProjector.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setProjection(String projection) {
    this.projection = projection;
  }

  public String getProjection() {
    return projection;
  }

  public <T> CrudsResult<T> getCrudsResult() {
    return (CrudsResult<T>) crudsResult;
  }

  public <T> JsonResult<T> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return (JsonResult<T>) JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    if (id == 0L) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Workflow.execute() failed: no id specified"
        );
      }
      return Action.ERROR;
    }

    projection = projection.trim();
    if (projection.length() == 0) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Workflow.execute() failed: no projection specified"
        );
      }
      return Action.ERROR;
    }

    DataRef dataRef = new DataRef(id, null, "workflow", "net.democritus.workflow", "Workflow");
    ProjectionRef projectionRef = new ProjectionRef(projection, dataRef);

    WorkflowAgent workflowAgent = createWorkflowAgent();
    crudsResult = workflowAgent.getProjection(projectionRef);

    return Action.SUCCESS;
  }

  private static WorkflowAgent createWorkflowAgent() {
    return WorkflowAgent.getWorkflowAgent(
        ContextRetriever.getContext());
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
