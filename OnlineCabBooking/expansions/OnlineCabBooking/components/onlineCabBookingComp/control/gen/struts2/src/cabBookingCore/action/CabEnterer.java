package cabBookingCore.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.Context;
import onlineCabBookingComp.context.ContextRetriever;
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
import cabBookingCore.CabAgent;
import cabBookingCore.CabDetails;

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

public class CabEnterer extends ActionSupport implements Preparable {

  private String cabOid = "";
  private String cabName = null;
  private CabDetails cabDetails = new CabDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(CabEnterer.class);
  private CabAgent cabAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public CabAgent getCabAgent() {
    return cabAgent;
  }

  public CabDetails getCabDetails() {
    return cabDetails;
  }

  // convenience method, that skips the 'Details' part
  public CabDetails getCab() {
    return getCabDetails();
  }

  public String getCabOid() {
    return cabOid;
  }

  public void setCabOid(String cabOid) {
    this.cabOid = cabOid;
  }

  public String getCabName() {
    return cabName;
  }

  public void setCabName(String cabName) {
    this.cabName = cabName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    cabAgent = createCabAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (cabName != null) {
      cabDetails.setName(cabName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(cabDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      cabName = dataRef.getName();
      cabOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      cabOid = "";

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

  public CabDetails getJsonRoot() {
    return cabDetails;
  }

  private boolean hascabOid() {
    return !(cabOid.equals("") || cabOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(CabDetails cabDetails) {
    boolean isNew;

    if (hascabOid()) {
      cabDetails.setId(new Long(cabOid));
    }

    Long id = cabDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return cabAgent.create(cabDetails);
    } else {
      return cabAgent.modify(cabDetails);
    }
  }

  // @anchor:methods:start
  private static CabAgent createCabAgent() {
    return CabAgent.getCabAgent(getContext());
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
