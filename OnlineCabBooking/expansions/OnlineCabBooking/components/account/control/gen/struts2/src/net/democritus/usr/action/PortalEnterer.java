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
import net.democritus.usr.PortalAgent;
import net.democritus.usr.PortalDetails;

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

public class PortalEnterer extends ActionSupport implements Preparable {

  private String portalOid = "";
  private String portalName = null;
  private PortalDetails portalDetails = new PortalDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(PortalEnterer.class);
  private PortalAgent portalAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public PortalAgent getPortalAgent() {
    return portalAgent;
  }

  public PortalDetails getPortalDetails() {
    return portalDetails;
  }

  // convenience method, that skips the 'Details' part
  public PortalDetails getPortal() {
    return getPortalDetails();
  }

  public String getPortalOid() {
    return portalOid;
  }

  public void setPortalOid(String portalOid) {
    this.portalOid = portalOid;
  }

  public String getPortalName() {
    return portalName;
  }

  public void setPortalName(String portalName) {
    this.portalName = portalName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    portalAgent = createPortalAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (portalName != null) {
      portalDetails.setName(portalName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(portalDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      portalName = dataRef.getName();
      portalOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      portalOid = "";

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

  public PortalDetails getJsonRoot() {
    return portalDetails;
  }

  private boolean hasportalOid() {
    return !(portalOid.equals("") || portalOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(PortalDetails portalDetails) {
    boolean isNew;

    if (hasportalOid()) {
      portalDetails.setId(new Long(portalOid));
    }

    Long id = portalDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return portalAgent.create(portalDetails);
    } else {
      return portalAgent.modify(portalDetails);
    }
  }

  // @anchor:methods:start
  private static PortalAgent createPortalAgent() {
    return PortalAgent.getPortalAgent(getContext());
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
