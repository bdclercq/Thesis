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
import cabBookingCore.CustomerAgent;
import cabBookingCore.CustomerDetails;

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

public class CustomerEnterer extends ActionSupport implements Preparable {

  private String customerOid = "";
  private String customerName = null;
  private CustomerDetails customerDetails = new CustomerDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(CustomerEnterer.class);
  private CustomerAgent customerAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public CustomerAgent getCustomerAgent() {
    return customerAgent;
  }

  public CustomerDetails getCustomerDetails() {
    return customerDetails;
  }

  // convenience method, that skips the 'Details' part
  public CustomerDetails getCustomer() {
    return getCustomerDetails();
  }

  public String getCustomerOid() {
    return customerOid;
  }

  public void setCustomerOid(String customerOid) {
    this.customerOid = customerOid;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    customerAgent = createCustomerAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (customerName != null) {
      customerDetails.setName(customerName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(customerDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      customerName = dataRef.getName();
      customerOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      customerOid = "";

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

  public CustomerDetails getJsonRoot() {
    return customerDetails;
  }

  private boolean hascustomerOid() {
    return !(customerOid.equals("") || customerOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(CustomerDetails customerDetails) {
    boolean isNew;

    if (hascustomerOid()) {
      customerDetails.setId(new Long(customerOid));
    }

    Long id = customerDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return customerAgent.create(customerDetails);
    } else {
      return customerAgent.modify(customerDetails);
    }
  }

  // @anchor:methods:start
  private static CustomerAgent createCustomerAgent() {
    return CustomerAgent.getCustomerAgent(getContext());
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
