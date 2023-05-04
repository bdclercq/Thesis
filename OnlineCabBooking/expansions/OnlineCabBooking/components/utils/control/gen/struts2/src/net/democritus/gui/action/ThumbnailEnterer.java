package net.democritus.gui.action;

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
import net.democritus.gui.ThumbnailAgent;
import net.democritus.gui.ThumbnailDetails;

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

public class ThumbnailEnterer extends ActionSupport implements Preparable {

  private String thumbnailOid = "";
  private String thumbnailName = null;
  private ThumbnailDetails thumbnailDetails = new ThumbnailDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ThumbnailEnterer.class);
  private ThumbnailAgent thumbnailAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ThumbnailAgent getThumbnailAgent() {
    return thumbnailAgent;
  }

  public ThumbnailDetails getThumbnailDetails() {
    return thumbnailDetails;
  }

  // convenience method, that skips the 'Details' part
  public ThumbnailDetails getThumbnail() {
    return getThumbnailDetails();
  }

  public String getThumbnailOid() {
    return thumbnailOid;
  }

  public void setThumbnailOid(String thumbnailOid) {
    this.thumbnailOid = thumbnailOid;
  }

  public String getThumbnailName() {
    return thumbnailName;
  }

  public void setThumbnailName(String thumbnailName) {
    this.thumbnailName = thumbnailName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    thumbnailAgent = createThumbnailAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    String actionResult;

    if (thumbnailName != null) {
      thumbnailDetails.setName(thumbnailName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(thumbnailDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      thumbnailName = dataRef.getName();
      thumbnailOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      thumbnailOid = "";

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

  public ThumbnailDetails getJsonRoot() {
    return thumbnailDetails;
  }

  private boolean hasthumbnailOid() {
    return !(thumbnailOid.equals("") || thumbnailOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(ThumbnailDetails thumbnailDetails) {
    boolean isNew;

    if (hasthumbnailOid()) {
      thumbnailDetails.setId(new Long(thumbnailOid));
    }

    Long id = thumbnailDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return thumbnailAgent.create(thumbnailDetails);
    } else {
      return thumbnailAgent.modify(thumbnailDetails);
    }
  }

  // @anchor:methods:start
  private static ThumbnailAgent createThumbnailAgent() {
    return ThumbnailAgent.getThumbnailAgent(getContext());
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
