package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.Paging;
import net.democritus.sys.search.SearchDetails;
import net.democritus.sys.search.SortField;
import net.democritus.sys.search.JPAQueryBuilder;
import net.democritus.sys.search.QueryBuilder;
import net.democritus.sys.search.QueryParameter;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ElementRef;

import net.palver.util.StringUtil;
import net.democritus.palver.date.DateNormalizer;

// anchor:imports:start
// anchor:imports:end
// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import java.util.Collections;
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class MenuItemFinderBean implements MenuItemFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends MenuItemFindDetails>,
      BiFunction<MenuItemFinderBean, ParameterContext, SearchResult<MenuItemData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(MenuItemFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public MenuItemFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends MenuItemFindDetails>, BiFunction<MenuItemFinderBean, ParameterContext, SearchResult<MenuItemData>>> generateFinderMapping() {
    Map<Class<? extends MenuItemFindDetails>, BiFunction<MenuItemFinderBean, ParameterContext, SearchResult<MenuItemData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(MenuItemFindAllMenuItemsDetails.class, MenuItemFinderBean::findAllMenuItems);
    finderMapping.put(MenuItemFindByNameEqDetails.class, MenuItemFinderBean::findByNameEq);
    finderMapping.put(MenuItemFindByMenuEqDetails.class, MenuItemFinderBean::findByMenuEq);
    finderMapping.put(MenuItemFindByMenuEq_ScreenEqDetails.class, MenuItemFinderBean::findByMenuEq_ScreenEq);
    finderMapping.put(MenuItemFindByMenuItemEqDetails.class, MenuItemFinderBean::findByMenuItemEq);
    finderMapping.put(MenuItemFindByScreenEqDetails.class, MenuItemFinderBean::findByScreenEq);
    finderMapping.put(MenuItemFindBySortOrderGtDetails.class, MenuItemFinderBean::findBySortOrderGt);
    finderMapping.put(MenuItemFindBySortOrderLtDetails.class, MenuItemFinderBean::findBySortOrderLt);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findAllMenuItems(ParameterContext<SearchDetails<MenuItemFindAllMenuItemsDetails>> searchParameter) {
    SearchDetails<MenuItemFindAllMenuItemsDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findByNameEq(ParameterContext<SearchDetails<MenuItemFindByNameEqDetails>> searchParameter) {
    SearchDetails<MenuItemFindByNameEqDetails> searchDetails = searchParameter.getValue();

    MenuItemFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findByMenuEq(ParameterContext<SearchDetails<MenuItemFindByMenuEqDetails>> searchParameter) {
    SearchDetails<MenuItemFindByMenuEqDetails> searchDetails = searchParameter.getValue();

    MenuItemFindByMenuEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("menu", "=", "Menu", details.getMenu()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findByMenuEq_ScreenEq(ParameterContext<SearchDetails<MenuItemFindByMenuEq_ScreenEqDetails>> searchParameter) {
    SearchDetails<MenuItemFindByMenuEq_ScreenEqDetails> searchDetails = searchParameter.getValue();

    MenuItemFindByMenuEq_ScreenEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("menu", "=", "Menu", details.getMenu()))
        .addParameter(QueryParameter.createDataRefParameter("screen", "=", "Screen", details.getScreen()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findByMenuItemEq(ParameterContext<SearchDetails<MenuItemFindByMenuItemEqDetails>> searchParameter) {
    SearchDetails<MenuItemFindByMenuItemEqDetails> searchDetails = searchParameter.getValue();

    MenuItemFindByMenuItemEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("menuItem", "=", "MenuItem", details.getMenuItem()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findByScreenEq(ParameterContext<SearchDetails<MenuItemFindByScreenEqDetails>> searchParameter) {
    SearchDetails<MenuItemFindByScreenEqDetails> searchDetails = searchParameter.getValue();

    MenuItemFindByScreenEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("screen", "=", "Screen", details.getScreen()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findBySortOrderGt(ParameterContext<SearchDetails<MenuItemFindBySortOrderGtDetails>> searchParameter) {
    SearchDetails<MenuItemFindBySortOrderGtDetails> searchDetails = searchParameter.getValue();

    MenuItemFindBySortOrderGtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("sortOrder", ">", "SortOrder", details.getSortOrder()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<MenuItemData> findBySortOrderLt(ParameterContext<SearchDetails<MenuItemFindBySortOrderLtDetails>> searchParameter) {
    SearchDetails<MenuItemFindBySortOrderLtDetails> searchDetails = searchParameter.getValue();

    MenuItemFindBySortOrderLtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("sortOrder", "<", "SortOrder", details.getSortOrder()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  // anchor:findBys-methods:end

  // anchor:findBys-customFinders:start
  // anchor:findBys-customFinders:end

  // anchor:fetch-data:start
  protected void setPagingRestrictions(Paging paging, Query query) {
    int rowsPerPage = paging.getRowsPerPage();
    if (rowsPerPage <= 0) {
        return;
    }

    int page = paging.getPage();
    int offset = rowsPerPage * page; // 0-based page

    query.setFirstResult(offset);
    query.setMaxResults(rowsPerPage);
  }

  @SuppressWarnings("unchecked")
  private SearchResult<MenuItemData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
    SearchDetails<?> searchDetails = searchParameter.getValue();

    // @anchor:fetch-before-create-queries:start
    // @anchor:fetch-before-create-queries:end
    // anchor:custom-fetch-before-create-queries:start
    // anchor:custom-fetch-before-create-queries:end

    Query countQuery = entityManager.createQuery(queryBuilder.getCountQueryString());
    queryBuilder.populateQuery(countQuery);

    Query dataQuery = entityManager.createQuery(queryBuilder.getDataQueryString());
    queryBuilder.populateQuery(dataQuery);

    // @anchor:fetch-after-create-queries:start
    // @anchor:fetch-after-create-queries:end
    // anchor:custom-fetch-after-create-queries:start
    // anchor:custom-fetch-after-create-queries:end

    return fetchData(searchDetails, countQuery, dataQuery);
  }

  private SearchResult<MenuItemData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<MenuItemData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<MenuItemData> resultDatas = (List<MenuItemData>) dataQuery.getResultList();

    // @anchor:fetch-after-queries:start
    // @anchor:fetch-after-queries:end
    // anchor:custom-fetch-after-queries:start
    // anchor:custom-fetch-after-queries:end

    if (searchDetails.getSkipCount()) {
      total = (long) resultDatas.size();
    }

    return SearchResult.success(resultDatas, total.intValue());
  }
  // anchor:fetch-data:end

  @Override
  public <S extends MenuItemFindDetails> SearchResult<MenuItemData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<MenuItemData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends MenuItemFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in MenuItemFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("account", "menuItem", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in MenuItemFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("account", "menuItem", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(MenuItemData.ENTITY_NAME, "id");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
