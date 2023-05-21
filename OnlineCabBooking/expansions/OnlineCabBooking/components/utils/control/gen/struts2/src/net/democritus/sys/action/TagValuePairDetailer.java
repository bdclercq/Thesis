package net.democritus.sys.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.democritus.sys.Context;
import utils.context.ContextRetriever;
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
import net.democritus.sys.TagValuePairAgent;
import net.democritus.sys.TagValuePairDetails;

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

public class TagValuePairDetailer extends ActionSupport implements Preparable {

  private String parentOid = "";
  private String parentField = "";
  private CrudsResult<TagValuePairDetails> crudsResult;

  // @anchor:variables:start
  private Long tagValuePairId = null;
  private String tagValuePairOid = "";
  private String tagValuePairName = "";
  private TagValuePairDetails tagValuePairDetails = new TagValuePairDetails();
  private TagValuePairAgent tagValuePairAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public void setParentField(String parentField) {
    this.parentField = parentField;
  }

  public void setParentOid(String parentOid) {
      this.parentOid = parentOid;
  }

  public CrudsResult<TagValuePairDetails> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<TagValuePairDetails> getJsonResult() {
    if (crudsResult.isSuccess()) {
        return JsonResult.createValue(crudsResult.getValue());
    } else {
        return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    tagValuePairAgent = createTagValuePairAgent();
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

    if (tagValuePairOid.length() > 0) {
      if (shouldClone(tagValuePairOid)) {
        tagValuePairId = new Long(tagValuePairOid.substring(1));
        tagValuePairOid = "";
      } else {
        tagValuePairId = new Long(tagValuePairOid);
      }

      crudsResult = tagValuePairAgent.getDetails(tagValuePairId);

      if (crudsResult.isError()) {
        actionResult = Action.INPUT;
      } else {
        tagValuePairDetails = crudsResult.getValue();
        tagValuePairName = tagValuePairDetails.getName();

        actionResult = Action.SUCCESS;
      }

    } else {
      tagValuePairName = "";

      actionResult = Action.SUCCESS;
      if (parentField.length() > 0) {
        actionResult = setParent(new Long(parentOid));
      } else {
        actionResult = Action.SUCCESS;
      }

      if (actionResult.equals(Action.SUCCESS)) {
        crudsResult = CrudsResult.success(tagValuePairDetails);
      } else {
          Diagnostic diagnostic = Diagnostic.error("utils", "TagValuePair", parentField, "cannotSetParentField");
          diagnostic.addReasons(new DiagnosticReason("parentOid", parentOid));

          crudsResult = CrudsResult.error(diagnostic);
      }

    }

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  private boolean shouldClone(String tagValuePairOid) {
    return tagValuePairOid.startsWith("c");
  }

  private String setParent(Long parentOid) {
    try {

        String getParentDataRefMethodName = "get"+parentField+"DataRef";
        Method getParentDataRefMethod = TagValuePairAgent.class.getMethod(getParentDataRefMethodName, new Class[] {Long.class});

        DataRef parentRef = (DataRef) getParentDataRefMethod.invoke(tagValuePairAgent, new Object[] {parentOid});

        String setParentMethodName = "set"+parentField;
        Method setParentMethod = TagValuePairDetails.class.getMethod(setParentMethodName, new Class[] {DataRef.class});

        setParentMethod.invoke(tagValuePairDetails, new Object[] {parentRef});

        return Action.SUCCESS;
     } catch (Exception e) {
        addActionError(e.getMessage());
        return Action.INPUT;
     }
  }

  // @anchor:methods:start
  public TagValuePairAgent getTagValuePairAgent() {
    return tagValuePairAgent;
  }

  public TagValuePairDetails getTagValuePairDetails() {
    return tagValuePairDetails;
  }

  public String getTagValuePairOid() {
    return tagValuePairOid;
  }

  public void setTagValuePairOid(String tagValuePairOid) {
    this.tagValuePairOid = tagValuePairOid;
  }

  public String getTagValuePairName() {
    return tagValuePairName;
  }

  public void setTagValuePairName(String tagValuePairName) {
    this.tagValuePairName = tagValuePairName;
  }
  private static TagValuePairAgent createTagValuePairAgent() {
    return TagValuePairAgent.getTagValuePairAgent(getContext());
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
