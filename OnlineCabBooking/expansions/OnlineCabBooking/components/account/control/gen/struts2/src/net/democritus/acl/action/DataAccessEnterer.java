package net.democritus.acl.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import account.context.ContextRetriever;
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
import net.democritus.acl.DataAccessAgent;
import net.democritus.acl.DataAccessDetails;

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

public class DataAccessEnterer extends ActionSupport implements Preparable {

  private String dataAccessOid = "";
  private String dataAccessName = null;
  private DataAccessDetails dataAccessDetails = new DataAccessDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DataAccessEnterer.class);
  private DataAccessAgent dataAccessAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DataAccessAgent getDataAccessAgent() {
    return dataAccessAgent;
  }

  public DataAccessDetails getDataAccessDetails() {
    return dataAccessDetails;
  }

  // convenience method, that skips the 'Details' part
  public DataAccessDetails getDataAccess() {
    return getDataAccessDetails();
  }

  public String getDataAccessOid() {
    return dataAccessOid;
  }

  public void setDataAccessOid(String dataAccessOid) {
    this.dataAccessOid = dataAccessOid;
  }

  public String getDataAccessName() {
    return dataAccessName;
  }

  public void setDataAccessName(String dataAccessName) {
    this.dataAccessName = dataAccessName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    dataAccessAgent = createDataAccessAgent();
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

    if (dataAccessName != null) {
      dataAccessDetails.setName(dataAccessName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(dataAccessDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      dataAccessName = dataRef.getName();
      dataAccessOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      dataAccessOid = "";

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

  public DataAccessDetails getJsonRoot() {
    return dataAccessDetails;
  }

  private boolean hasdataAccessOid() {
    return !(dataAccessOid.equals("") || dataAccessOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(DataAccessDetails dataAccessDetails) {
    boolean isNew;

    if (hasdataAccessOid()) {
      dataAccessDetails.setId(new Long(dataAccessOid));
    }

    Long id = dataAccessDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return dataAccessAgent.create(dataAccessDetails);
    } else {
      return dataAccessAgent.modify(dataAccessDetails);
    }
  }

  // @anchor:methods:start
  private static DataAccessAgent createDataAccessAgent() {
    return DataAccessAgent.getDataAccessAgent(getContext());
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
