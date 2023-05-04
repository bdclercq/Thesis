package net.democritus.usr.menu.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.ServletActionContext;

import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.json.DiagnosticsToStrutsMapper;
import net.democritus.json.JsonResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.sys.search.Paging;
import net.democritus.sys.search.SearchDetails;
import net.democritus.sys.search.SortField;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import static net.palver.util.Options.Option;
import static net.palver.util.Options.some;
import static net.palver.util.Options.none;
import net.palver.util.StringUtil;

import net.democritus.usr.menu.ScreenProfileAgent;
import account.context.ContextRetriever;
import net.democritus.usr.menu.ScreenProfileFindDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ScreenProfileFindAction extends ActionSupport implements Preparable {

  private static final long serialVersionUID = 1L;

  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger("net.democritus.usr.menu.action.ScreenProfileFindAction");

  private SearchDetails<? extends ScreenProfileFindDetails> searchDetails;
  private Object details;

  private SearchResult<?> searchResult;
  private Paging paging = new Paging();

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:accessors:start
  public Object getDetails() {
    return details;
  }

  public void setDetails(Object details) {
    this.details = details;
  }

  public int getPage() {
    return paging.getPage();
  }

  public void setPage(int page) {
    paging.setPage(page);
  }

  public int getRowsPerPage() {
    return paging.getRowsPerPage();
  }

  public void setRowsPerPage(int rowsPerPage) {
    paging.setRowsPerPage(rowsPerPage);
  }

  public List<SortField> getSortFields() {
    return searchDetails.getSortFields();
  }

  public void setSortFields(List<SortField> sortFields) {
    searchDetails.setSortFields(sortFields);
  }

  public boolean isFetchAll() {
    return paging.isFetchAll();
  }

  public void setFetchAll(boolean fetchAll) {
    paging.setFetchAll(fetchAll);
  }

  public String getProjection() {
    return searchDetails.getProjection();
  }

  public void setProjection(String projection) {
    searchDetails.setProjection(projection);
  }

  public void setSearchMethod(String searchMethod) {
    // searchMethod is accessed directly from request in prepare()
  }
  // anchor:accessors:end

  @Override
  public void prepare() throws Exception {
    HttpServletRequest request = ServletActionContext.getRequest();
    String searchMethod = request.getParameter("searchMethod");

    if (searchMethod == null) {
      throw new RuntimeException("Parameter 'searchMethod' not specified");
    }

    Option<Class> optClazz = getFinderClass("net.democritus.usr.menu", "ScreenProfile", searchMethod);
    if (optClazz.isEmpty()) {
      throw new RuntimeException("searchMethod not found: " + searchMethod);
    }

    details = optClazz.getValue().newInstance();
    searchDetails = new SearchDetails(details);
    searchDetails.setPaging(paging);

    // anchor:custom-preparation:start
    // anchor:custom-preparation:end
  }

  private static Option<Class> getFinderClass(String packageName, String elementName, String searchMethod) {
    if (searchMethod.equals("findAll")) {
      searchMethod = "findAllScreenProfiles";
    }
    String className = elementName + StringUtil.firstToUpperCase(searchMethod) + "Details";
    String qualifiedName = packageName + "." + className;
    try {
      Class clazz = Class.forName(qualifiedName);
      return some(clazz);
    } catch (ClassNotFoundException e) {
      logger.error("Class " + qualifiedName + " not found for searchMethod: " + searchMethod);
      return none();
    }
  }

  public String execute() throws Exception {
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end
    // anchor:custom-execute-validation:start
    // anchor:custom-execute-validation:end

    ScreenProfileAgent screenProfileAgent = createScreenProfileAgent();

    // @anchor:execute-before:start
    // @anchor:execute-before:end
    // anchor:custom-execute-before:start
    // anchor:custom-execute-before:end

    searchResult = screenProfileAgent.find(searchDetails);
    DiagnosticsToStrutsMapper.mapDiagnostics(this, searchResult);
    return Action.SUCCESS;
  }

  public SearchResult<?> getSearchResult() {
    return searchResult;
  }

  public JsonResult<?> getJsonResult() {
    if (searchResult.isSuccess()) {
      return JsonResult.createPagedListWithTotals(
          searchResult.getResults(),
          getPage(),
          getRowsPerPage(),
          searchResult.getTotalNumberOfItems()
      );
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  private ScreenProfileAgent createScreenProfileAgent() {
    return ScreenProfileAgent.getScreenProfileAgent(getContext());
  }

  private Context getContext() {
    Context context = ContextRetriever.getContext();
    // @anchor:context:start
    // @anchor:context:end
    // anchor:custom-context:start
    // anchor:custom-context:end
    return context;
  }

  /**
   * @deprecated Use {@link ContextRetriever} instead
   */
  @Deprecated
  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
