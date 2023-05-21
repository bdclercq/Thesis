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
import cabBookingCore.TripBookingTaskStatusAgent;
import cabBookingCore.TripBookingTaskStatusDetails;

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

public class TripBookingTaskStatusEnterer extends ActionSupport implements Preparable {

  private String tripBookingTaskStatusOid = "";
  private String tripBookingTaskStatusName = null;
  private TripBookingTaskStatusDetails tripBookingTaskStatusDetails = new TripBookingTaskStatusDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingTaskStatusEnterer.class);
  private TripBookingTaskStatusAgent tripBookingTaskStatusAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TripBookingTaskStatusAgent getTripBookingTaskStatusAgent() {
    return tripBookingTaskStatusAgent;
  }

  public TripBookingTaskStatusDetails getTripBookingTaskStatusDetails() {
    return tripBookingTaskStatusDetails;
  }

  // convenience method, that skips the 'Details' part
  public TripBookingTaskStatusDetails getTripBookingTaskStatus() {
    return getTripBookingTaskStatusDetails();
  }

  public String getTripBookingTaskStatusOid() {
    return tripBookingTaskStatusOid;
  }

  public void setTripBookingTaskStatusOid(String tripBookingTaskStatusOid) {
    this.tripBookingTaskStatusOid = tripBookingTaskStatusOid;
  }

  public String getTripBookingTaskStatusName() {
    return tripBookingTaskStatusName;
  }

  public void setTripBookingTaskStatusName(String tripBookingTaskStatusName) {
    this.tripBookingTaskStatusName = tripBookingTaskStatusName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    tripBookingTaskStatusAgent = createTripBookingTaskStatusAgent();
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

    if (tripBookingTaskStatusName != null) {
      tripBookingTaskStatusDetails.setName(tripBookingTaskStatusName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(tripBookingTaskStatusDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      tripBookingTaskStatusName = dataRef.getName();
      tripBookingTaskStatusOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      tripBookingTaskStatusOid = "";

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

  public TripBookingTaskStatusDetails getJsonRoot() {
    return tripBookingTaskStatusDetails;
  }

  private boolean hastripBookingTaskStatusOid() {
    return !(tripBookingTaskStatusOid.equals("") || tripBookingTaskStatusOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(TripBookingTaskStatusDetails tripBookingTaskStatusDetails) {
    boolean isNew;

    if (hastripBookingTaskStatusOid()) {
      tripBookingTaskStatusDetails.setId(new Long(tripBookingTaskStatusOid));
    }

    Long id = tripBookingTaskStatusDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return tripBookingTaskStatusAgent.create(tripBookingTaskStatusDetails);
    } else {
      return tripBookingTaskStatusAgent.modify(tripBookingTaskStatusDetails);
    }
  }

  // @anchor:methods:start
  private static TripBookingTaskStatusAgent createTripBookingTaskStatusAgent() {
    return TripBookingTaskStatusAgent.getTripBookingTaskStatusAgent(getContext());
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
