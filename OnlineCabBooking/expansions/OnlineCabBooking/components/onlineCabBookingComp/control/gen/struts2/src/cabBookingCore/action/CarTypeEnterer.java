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
import cabBookingCore.CarTypeAgent;
import cabBookingCore.CarTypeDetails;

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

public class CarTypeEnterer extends ActionSupport implements Preparable {

  private String carTypeOid = "";
  private String carTypeName = null;
  private CarTypeDetails carTypeDetails = new CarTypeDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(CarTypeEnterer.class);
  private CarTypeAgent carTypeAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public CarTypeAgent getCarTypeAgent() {
    return carTypeAgent;
  }

  public CarTypeDetails getCarTypeDetails() {
    return carTypeDetails;
  }

  // convenience method, that skips the 'Details' part
  public CarTypeDetails getCarType() {
    return getCarTypeDetails();
  }

  public String getCarTypeOid() {
    return carTypeOid;
  }

  public void setCarTypeOid(String carTypeOid) {
    this.carTypeOid = carTypeOid;
  }

  public String getCarTypeName() {
    return carTypeName;
  }

  public void setCarTypeName(String carTypeName) {
    this.carTypeName = carTypeName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    carTypeAgent = createCarTypeAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (carTypeName != null) {
      carTypeDetails.setName(carTypeName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(carTypeDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      carTypeName = dataRef.getName();
      carTypeOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      carTypeOid = "";

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

  public CarTypeDetails getJsonRoot() {
    return carTypeDetails;
  }

  private boolean hascarTypeOid() {
    return !(carTypeOid.equals("") || carTypeOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(CarTypeDetails carTypeDetails) {
    boolean isNew;

    if (hascarTypeOid()) {
      carTypeDetails.setId(new Long(carTypeOid));
    }

    Long id = carTypeDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return carTypeAgent.create(carTypeDetails);
    } else {
      return carTypeAgent.modify(carTypeDetails);
    }
  }

  // @anchor:methods:start
  private static CarTypeAgent createCarTypeAgent() {
    return CarTypeAgent.getCarTypeAgent(getContext());
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
