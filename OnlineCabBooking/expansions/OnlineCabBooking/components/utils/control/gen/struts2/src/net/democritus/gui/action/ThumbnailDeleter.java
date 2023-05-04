package net.democritus.gui.action;

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

public class ThumbnailDeleter extends ActionSupport implements Preparable {

  private CrudsResult<Void> crudsResult;

  // @anchor:variables:start
  private Long thumbnailId = null;
  private String thumbnailOid = "";
  private String thumbnailName = "";
  private ThumbnailDetails thumbnailDetails = new ThumbnailDetails();
  private ThumbnailAgent thumbnailAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

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

    if (!thumbnailOid.equals("")) {
      thumbnailId = new Long(thumbnailOid);
    } else {
      thumbnailId = 0L;
    }

    crudsResult = thumbnailAgent.delete(thumbnailId);

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return Action.SUCCESS;
  }

  public JsonResult<String> getJsonResult() {
    if (crudsResult.isSuccess()) {
      return JsonResult.createValue("item deleted");
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  // @anchor:methods:start
  public ThumbnailAgent getThumbnailAgent() {
    return thumbnailAgent;
  }

  public ThumbnailDetails getThumbnailDetails() {
    return thumbnailDetails;
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

