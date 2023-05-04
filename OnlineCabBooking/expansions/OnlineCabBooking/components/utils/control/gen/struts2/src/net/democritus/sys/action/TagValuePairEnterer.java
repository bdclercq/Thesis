package net.democritus.sys.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
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

public class TagValuePairEnterer extends ActionSupport implements Preparable {

  private String tagValuePairOid = "";
  private String tagValuePairName = null;
  private TagValuePairDetails tagValuePairDetails = new TagValuePairDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TagValuePairEnterer.class);
  private TagValuePairAgent tagValuePairAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TagValuePairAgent getTagValuePairAgent() {
    return tagValuePairAgent;
  }

  public TagValuePairDetails getTagValuePairDetails() {
    return tagValuePairDetails;
  }

  // convenience method, that skips the 'Details' part
  public TagValuePairDetails getTagValuePair() {
    return getTagValuePairDetails();
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

  public void prepare() throws Exception {
    // @anchor:prepare:start
    tagValuePairAgent = createTagValuePairAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (tagValuePairName != null) {
      tagValuePairDetails.setName(tagValuePairName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(tagValuePairDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      tagValuePairName = dataRef.getName();
      tagValuePairOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      tagValuePairOid = "";

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

  public TagValuePairDetails getJsonRoot() {
    return tagValuePairDetails;
  }

  private boolean hastagValuePairOid() {
    return !(tagValuePairOid.equals("") || tagValuePairOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(TagValuePairDetails tagValuePairDetails) {
    boolean isNew;

    if (hastagValuePairOid()) {
      tagValuePairDetails.setId(new Long(tagValuePairOid));
    }

    Long id = tagValuePairDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return tagValuePairAgent.create(tagValuePairDetails);
    } else {
      return tagValuePairAgent.modify(tagValuePairDetails);
    }
  }

  // @anchor:methods:start
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
