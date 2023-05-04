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
import net.democritus.sys.IdCounterAgent;
import net.democritus.sys.IdCounterDetails;

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

public class IdCounterEnterer extends ActionSupport implements Preparable {

  private String idCounterOid = "";
  private String idCounterName = null;
  private IdCounterDetails idCounterDetails = new IdCounterDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(IdCounterEnterer.class);
  private IdCounterAgent idCounterAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public IdCounterAgent getIdCounterAgent() {
    return idCounterAgent;
  }

  public IdCounterDetails getIdCounterDetails() {
    return idCounterDetails;
  }

  // convenience method, that skips the 'Details' part
  public IdCounterDetails getIdCounter() {
    return getIdCounterDetails();
  }

  public String getIdCounterOid() {
    return idCounterOid;
  }

  public void setIdCounterOid(String idCounterOid) {
    this.idCounterOid = idCounterOid;
  }

  public String getIdCounterName() {
    return idCounterName;
  }

  public void setIdCounterName(String idCounterName) {
    this.idCounterName = idCounterName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    idCounterAgent = createIdCounterAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (idCounterName != null) {
      idCounterDetails.setName(idCounterName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(idCounterDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      idCounterName = dataRef.getName();
      idCounterOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      idCounterOid = "";

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

  public IdCounterDetails getJsonRoot() {
    return idCounterDetails;
  }

  private boolean hasidCounterOid() {
    return !(idCounterOid.equals("") || idCounterOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(IdCounterDetails idCounterDetails) {
    boolean isNew;

    if (hasidCounterOid()) {
      idCounterDetails.setId(new Long(idCounterOid));
    }

    Long id = idCounterDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return idCounterAgent.create(idCounterDetails);
    } else {
      return idCounterAgent.modify(idCounterDetails);
    }
  }

  // @anchor:methods:start
  private static IdCounterAgent createIdCounterAgent() {
    return IdCounterAgent.getIdCounterAgent(getContext());
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
