package net.democritus.acl.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
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
import net.democritus.acl.DataAccessAgent;
import net.democritus.acl.DataAccessDetails;

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

public class DataAccessDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<DataAccessDetails> crudsResult;

  // @anchor:variables:start
  private Long dataAccessId = null;
  private String dataAccessOid = "";
  private String dataAccessName = "";
  private DataAccessDetails dataAccessDetails = new DataAccessDetails();
  private DataAccessAgent dataAccessAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<DataAccessDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<DataAccessDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    dataAccessAgent = createDataAccessAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (dataAccessOid.length() > 0) {
      if (shouldClone(dataAccessOid)) {
        dataAccessId = new Long(dataAccessOid.substring(1));
        dataAccessOid = "";
      } else {
        dataAccessId = new Long(dataAccessOid);
      }

      crudsResult = dataAccessAgent.getDetails(dataAccessId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        dataAccessDetails = crudsResult.getValue();
        dataAccessName = dataAccessDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      dataAccessName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(dataAccessDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("account", "DataAccess", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String dataAccessOid) {
    return dataAccessOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = DataAccessAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(dataAccessAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = DataAccessDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(dataAccessDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public DataAccessAgent getDataAccessAgent() {
    return dataAccessAgent;
  }

  public DataAccessDetails getDataAccessDetails() {
    return dataAccessDetails;
  }

  public String getDataAccessOid() {
    return dataAccessOid;
  }

  public void setDataAccessOid(String dataAccessOid) {
    this.dataAccessOid = dataAccessOid;
  }

  public String getDataAccessName() {
    return dataAccessName;
  }

  public void setDataAccessName(String dataAccessName) {
    this.dataAccessName = dataAccessName;
  }
  private static DataAccessAgent createDataAccessAgent() {
    return DataAccessAgent.getDataAccessAgent(getContext());
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
