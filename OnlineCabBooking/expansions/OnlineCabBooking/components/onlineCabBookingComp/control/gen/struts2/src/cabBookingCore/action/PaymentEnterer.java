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
import cabBookingCore.PaymentAgent;
import cabBookingCore.PaymentDetails;

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

public class PaymentEnterer extends ActionSupport implements Preparable {

  private String paymentOid = "";
  private String paymentName = null;
  private PaymentDetails paymentDetails = new PaymentDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(PaymentEnterer.class);
  private PaymentAgent paymentAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public PaymentAgent getPaymentAgent() {
    return paymentAgent;
  }

  public PaymentDetails getPaymentDetails() {
    return paymentDetails;
  }

  // convenience method, that skips the 'Details' part
  public PaymentDetails getPayment() {
    return getPaymentDetails();
  }

  public String getPaymentOid() {
    return paymentOid;
  }

  public void setPaymentOid(String paymentOid) {
    this.paymentOid = paymentOid;
  }

  public String getPaymentName() {
    return paymentName;
  }

  public void setPaymentName(String paymentName) {
    this.paymentName = paymentName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    paymentAgent = createPaymentAgent();
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

    if (paymentName != null) {
      paymentDetails.setName(paymentName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(paymentDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      paymentName = dataRef.getName();
      paymentOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      paymentOid = "";

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

  public PaymentDetails getJsonRoot() {
    return paymentDetails;
  }

  private boolean haspaymentOid() {
    return !(paymentOid.equals("") || paymentOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(PaymentDetails paymentDetails) {
    boolean isNew;

    if (haspaymentOid()) {
      paymentDetails.setId(new Long(paymentOid));
    }

    Long id = paymentDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return paymentAgent.create(paymentDetails);
    } else {
      return paymentAgent.modify(paymentDetails);
    }
  }

  // @anchor:methods:start
  private static PaymentAgent createPaymentAgent() {
    return PaymentAgent.getPaymentAgent(getContext());
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
