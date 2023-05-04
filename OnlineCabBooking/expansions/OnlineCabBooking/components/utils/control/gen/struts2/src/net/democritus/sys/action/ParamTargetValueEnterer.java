package net.democritus.sys.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import utils.context.ContextRetriever;
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
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParamTargetValueDetails;

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

public class ParamTargetValueEnterer extends ActionSupport implements Preparable {

  private String paramTargetValueOid = "";
  private ParamTargetValueDetails paramTargetValueDetails = new ParamTargetValueDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ParamTargetValueEnterer.class);
  private ParamTargetValueAgent paramTargetValueAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ParamTargetValueAgent getParamTargetValueAgent() {
    return paramTargetValueAgent;
  }

  public ParamTargetValueDetails getParamTargetValueDetails() {
    return paramTargetValueDetails;
  }

  // convenience method, that skips the 'Details' part
  public ParamTargetValueDetails getParamTargetValue() {
    return getParamTargetValueDetails();
  }

  public String getParamTargetValueOid() {
    return paramTargetValueOid;
  }

  public void setParamTargetValueOid(String paramTargetValueOid) {
    this.paramTargetValueOid = paramTargetValueOid;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    paramTargetValueAgent = createParamTargetValueAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(paramTargetValueDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      paramTargetValueOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      paramTargetValueOid = "";

      actionResult = Action.INPUT;
    }

    // @anchor:execute-fileUploadOnly-after:start
    // @anchor:execute-fileUploadOnly-after:end

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  public CrudsResult<DataRef> getCrudsResult() {
    return crudsResult;
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

  public ParamTargetValueDetails getJsonRoot() {
    return paramTargetValueDetails;
  }

  private boolean hasparamTargetValueOid() {
    return !(paramTargetValueOid.equals("") || paramTargetValueOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ParamTargetValueDetails paramTargetValueDetails) {
    boolean isNew;

    if (hasparamTargetValueOid()) {
      paramTargetValueDetails.setId(new Long(paramTargetValueOid));
    }

    Long id = paramTargetValueDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return paramTargetValueAgent.create(paramTargetValueDetails);
    } else {
      return paramTargetValueAgent.modify(paramTargetValueDetails);
    }
  }

  // @anchor:methods:start
  private static ParamTargetValueAgent createParamTargetValueAgent() {
    return ParamTargetValueAgent.getParamTargetValueAgent(getContext());
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