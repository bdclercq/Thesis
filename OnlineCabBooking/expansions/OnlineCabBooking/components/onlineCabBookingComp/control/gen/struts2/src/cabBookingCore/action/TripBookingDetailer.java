package cabBookingCore.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
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
import cabBookingCore.TripBookingAgent;
import cabBookingCore.TripBookingDetails;

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

import java.lang.reflect.Method;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;

public class TripBookingDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<TripBookingDetails> crudsResult;

  // @anchor:variables:start
  private Long tripBookingId = null;
  private String tripBookingOid = "";
  private String tripBookingName = "";
  private TripBookingDetails tripBookingDetails = new TripBookingDetails();
  private TripBookingAgent tripBookingAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<TripBookingDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<TripBookingDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    tripBookingAgent = createTripBookingAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    if (!httpServletRequest.getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    String actionResult;

    if (tripBookingOid.length() > 0) {
      if (shouldClone(tripBookingOid)) {
        tripBookingId = new Long(tripBookingOid.substring(1));
        tripBookingOid = "";
      } else {
        tripBookingId = new Long(tripBookingOid);
      }

      crudsResult = tripBookingAgent.getDetails(tripBookingId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        tripBookingDetails = crudsResult.getValue();
        tripBookingName = tripBookingDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      tripBookingName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(tripBookingDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("onlineCabBookingComp", "TripBooking", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String tripBookingOid) {
    return tripBookingOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = TripBookingAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(tripBookingAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = TripBookingDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(tripBookingDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public TripBookingAgent getTripBookingAgent() {
    return tripBookingAgent;
  }

  public TripBookingDetails getTripBookingDetails() {
    return tripBookingDetails;
  }

  public String getTripBookingOid() {
    return tripBookingOid;
  }

  public void setTripBookingOid(String tripBookingOid) {
    this.tripBookingOid = tripBookingOid;
  }

  public String getTripBookingName() {
    return tripBookingName;
  }

  public void setTripBookingName(String tripBookingName) {
    this.tripBookingName = tripBookingName;
  }
  private static TripBookingAgent createTripBookingAgent() {
    return TripBookingAgent.getTripBookingAgent(getContext());
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
