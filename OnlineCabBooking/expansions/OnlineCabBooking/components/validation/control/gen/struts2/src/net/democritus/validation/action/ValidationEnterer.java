package net.democritus.validation.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import validation.context.ContextRetriever;
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
import net.democritus.validation.ValidationAgent;
import net.democritus.validation.ValidationDetails;

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

public class ValidationEnterer extends ActionSupport implements Preparable {

  private String validationOid = "";
  private String validationName = null;
  private ValidationDetails validationDetails = new ValidationDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ValidationEnterer.class);
  private ValidationAgent validationAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ValidationAgent getValidationAgent() {
    return validationAgent;
  }

  public ValidationDetails getValidationDetails() {
    return validationDetails;
  }

  // convenience method, that skips the 'Details' part
  public ValidationDetails getValidation() {
    return getValidationDetails();
  }

  public String getValidationOid() {
    return validationOid;
  }

  public void setValidationOid(String validationOid) {
    this.validationOid = validationOid;
  }

  public String getValidationName() {
    return validationName;
  }

  public void setValidationName(String validationName) {
    this.validationName = validationName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    validationAgent = createValidationAgent();
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

    String actionResult;

    if (validationName != null) {
      validationDetails.setName(validationName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(validationDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      validationName = dataRef.getName();
      validationOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      validationOid = "";

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

  public ValidationDetails getJsonRoot() {
    return validationDetails;
  }

  private boolean hasvalidationOid() {
    return !(validationOid.equals("") || validationOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ValidationDetails validationDetails) {
    boolean isNew;

    if (hasvalidationOid()) {
      validationDetails.setId(new Long(validationOid));
    }

    Long id = validationDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return validationAgent.create(validationDetails);
    } else {
      return validationAgent.modify(validationDetails);
    }
  }

  // @anchor:methods:start
  private static ValidationAgent createValidationAgent() {
    return ValidationAgent.getValidationAgent(getContext());
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
