package net.democritus.usr.action;

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
import net.democritus.usr.AccountAgent;
import net.democritus.usr.AccountDetails;

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

public class AccountEnterer extends ActionSupport implements Preparable {

  private String accountOid = "";
  private String accountName = null;
  private AccountDetails accountDetails = new AccountDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AccountEnterer.class);
  private AccountAgent accountAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public AccountAgent getAccountAgent() {
    return accountAgent;
  }

  public AccountDetails getAccountDetails() {
    return accountDetails;
  }

  // convenience method, that skips the 'Details' part
  public AccountDetails getAccount() {
    return getAccountDetails();
  }

  public String getAccountOid() {
    return accountOid;
  }

  public void setAccountOid(String accountOid) {
    this.accountOid = accountOid;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    accountAgent = createAccountAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (accountName != null) {
      accountDetails.setName(accountName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(accountDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      accountName = dataRef.getName();
      accountOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      accountOid = "";

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

  public AccountDetails getJsonRoot() {
    return accountDetails;
  }

  private boolean hasaccountOid() {
    return !(accountOid.equals("") || accountOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(AccountDetails accountDetails) {
    boolean isNew;

    if (hasaccountOid()) {
      accountDetails.setId(new Long(accountOid));
    }

    Long id = accountDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return accountAgent.create(accountDetails);
    } else {
      return accountAgent.modify(accountDetails);
    }
  }

  // @anchor:methods:start
  private static AccountAgent createAccountAgent() {
    return AccountAgent.getAccountAgent(getContext());
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
