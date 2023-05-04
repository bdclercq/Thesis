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
import cabBookingCore.DriverAgent;
import cabBookingCore.DriverDetails;

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

public class DriverEnterer extends ActionSupport implements Preparable {

  private String driverOid = "";
  private String driverName = null;
  private DriverDetails driverDetails = new DriverDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DriverEnterer.class);
  private DriverAgent driverAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DriverAgent getDriverAgent() {
    return driverAgent;
  }

  public DriverDetails getDriverDetails() {
    return driverDetails;
  }

  // convenience method, that skips the 'Details' part
  public DriverDetails getDriver() {
    return getDriverDetails();
  }

  public String getDriverOid() {
    return driverOid;
  }

  public void setDriverOid(String driverOid) {
    this.driverOid = driverOid;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    driverAgent = createDriverAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (driverName != null) {
      driverDetails.setName(driverName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(driverDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      driverName = dataRef.getName();
      driverOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      driverOid = "";

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

  public DriverDetails getJsonRoot() {
    return driverDetails;
  }

  private boolean hasdriverOid() {
    return !(driverOid.equals("") || driverOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(DriverDetails driverDetails) {
    boolean isNew;

    if (hasdriverOid()) {
      driverDetails.setId(new Long(driverOid));
    }

    Long id = driverDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return driverAgent.create(driverDetails);
    } else {
      return driverAgent.modify(driverDetails);
    }
  }

  // @anchor:methods:start
  private static DriverAgent createDriverAgent() {
    return DriverAgent.getDriverAgent(getContext());
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
