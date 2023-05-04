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
import cabBookingCore.AddressAgent;
import cabBookingCore.AddressDetails;

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

public class AddressEnterer extends ActionSupport implements Preparable {

  private String addressOid = "";
  private String addressName = null;
  private AddressDetails addressDetails = new AddressDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AddressEnterer.class);
  private AddressAgent addressAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public AddressAgent getAddressAgent() {
    return addressAgent;
  }

  public AddressDetails getAddressDetails() {
    return addressDetails;
  }

  // convenience method, that skips the 'Details' part
  public AddressDetails getAddress() {
    return getAddressDetails();
  }

  public String getAddressOid() {
    return addressOid;
  }

  public void setAddressOid(String addressOid) {
    this.addressOid = addressOid;
  }

  public String getAddressName() {
    return addressName;
  }

  public void setAddressName(String addressName) {
    this.addressName = addressName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    addressAgent = createAddressAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (addressName != null) {
      addressDetails.setName(addressName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(addressDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      addressName = dataRef.getName();
      addressOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      addressOid = "";

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

  public AddressDetails getJsonRoot() {
    return addressDetails;
  }

  private boolean hasaddressOid() {
    return !(addressOid.equals("") || addressOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(AddressDetails addressDetails) {
    boolean isNew;

    if (hasaddressOid()) {
      addressDetails.setId(new Long(addressOid));
    }

    Long id = addressDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return addressAgent.create(addressDetails);
    } else {
      return addressAgent.modify(addressDetails);
    }
  }

  // @anchor:methods:start
  private static AddressAgent createAddressAgent() {
    return AddressAgent.getAddressAgent(getContext());
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
