package net.democritus.assets.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskResult;
import java.lang.reflect.Method;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import assets.context.ContextRetriever;
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
import net.democritus.assets.InternalAssetAgent;
import net.democritus.assets.InternalAssetDetails;

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

public class InternalAssetPerformer extends ActionSupport implements Preparable {

  private static final long serialVersionUID = 1L;

  private CrudsResult<InternalAssetDetails> crudsResult;
  private TaskResult<Void> taskResult;
  private String taskName = "";
  private String paramString = "";
  private String targetStatus = "";

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(InternalAssetPerformer.class);
  private Long internalAssetId = null;
  private String internalAssetOid = "";
  private String internalAssetName = "";
  private InternalAssetDetails internalAssetDetails = new InternalAssetDetails();
  private InternalAssetAgent internalAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setTaskName(String aTaskName) {
    taskName = aTaskName;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setParamString(String aParamString) {
    paramString = aParamString;
  }

  public String getParamString() {
    return paramString;
  }

  public void setTargetStatus(String aTargetStatus) {
    targetStatus = aTargetStatus;
  }

  public String getTargetStatus() {
    return targetStatus;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    internalAssetAgent = createInternalAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
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

    if (!internalAssetOid.equals("")) {
      internalAssetId = new Long(internalAssetOid);
    } else {
      internalAssetId = 0L;
    }

    crudsResult = internalAssetAgent.getDetails(internalAssetId);

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    try {
      InternalAssetDetails details = crudsResult.getValue();
      if (!taskName.equals("")) {
        performTask(details);
      }
      if (!targetStatus.equals("")) {
        setTaskStatus(details);
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "InternalAssetPerformer failed to perform task", e
        );
      }
      taskResult = TaskResult.error();
    }

    parseResult();
    return Action.SUCCESS;
  }

  @SuppressWarnings("unchecked")
  private void performTask(InternalAssetDetails details) throws Exception {
    Class<?> agentClass = Class.forName("net.democritus.assets." + taskName + "Agent");
    Method getAgentMethod = agentClass.getMethod("get" + taskName + "Agent", Context.class);
    Object agent = getAgentMethod.invoke(null, getContext());
    Method performMethod = agentClass.getMethod("perform", InternalAssetDetails.class, String.class);

    taskResult = (TaskResult<Void>) performMethod.invoke(agent, details, paramString);
  }

  public void setTaskStatus(InternalAssetDetails details) throws Exception {
    Method setStatusMethod = InternalAssetDetails.class.getMethod("setStatus", String.class);
    setStatusMethod.invoke(details, targetStatus);
    internalAssetAgent.modify(details);
  }

  private void parseResult() {
    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);
    if (taskResult != null) {
      DiagnosticsToStrutsMapper.mapDiagnostics(this, taskResult);
    }
  }

  public JsonResult<String> getJsonResult() {
    if (crudsResult.isSuccess() && (taskResult == null || taskResult.isSuccess())) {
        return JsonResult.createValue("Task performed");
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  // @anchor:methods:start
  public InternalAssetAgent getInternalAssetAgent() {
    return internalAssetAgent;
  }

  public InternalAssetDetails getInternalAssetDetails() {
    return internalAssetDetails;
  }

  public String getInternalAssetOid() {
    return internalAssetOid;
  }

  public void setInternalAssetOid(String internalAssetOid) {
    this.internalAssetOid = internalAssetOid;
  }

  public String getInternalAssetName() {
    return internalAssetName;
  }

  public void setInternalAssetName(String internalAssetName) {
    this.internalAssetName = internalAssetName;
  }
  private static InternalAssetAgent createInternalAssetAgent() {
    return InternalAssetAgent.getInternalAssetAgent(getContext());
  }

  private static Context getContext() {
    return ContextRetriever.getContext();
  }

  /**
   * @deprecated Use {@link ContextRetriever} instead
   */
  @Deprecated
  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

