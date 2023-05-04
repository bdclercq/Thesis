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

public class ScreenFinderBean implements ScreenFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends ScreenFindDetails>,
      BiFunction<ScreenFinderBean, ParameterContext, SearchResult<ScreenData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ScreenFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ScreenFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends ScreenFindDetails>, BiFunction<ScreenFinderBean, ParameterContext, SearchResult<ScreenData>>> generateFinderMapping() {
    Map<Class<? extends ScreenFindDetails>, BiFunction<ScreenFinderBean, ParameterContext, SearchResult<ScreenData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(ScreenFindAllScreensDetails.class, ScreenFinderBean::findAllScreens);
    finderMapping.put(ScreenFindByNameEqDetails.class, ScreenFinderBean::findByNameEq);
    finderMapping.put(ScreenFindByComponentEqDetails.class, ScreenFinderBean::findByComponentEq);
    finderMapping.put(ScreenFindByLinkEqDetails.class, ScreenFinderBean::findByLinkEq);
    finderMapping.put(ScreenFindByNameEq_ComponentEqDetails.class, ScreenFinderBean::findByNameEq_ComponentEq);
    finderMapping.put(ScreenFindBySortOrderGtDetails.class, ScreenFinderBean::findBySortOrderGt);
    finderMapping.put(ScreenFindBySortOrderLtDetails.class, ScreenFinderBean::findBySortOrderLt);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findAllScreens(ParameterContext<SearchDetails<ScreenFindAllScreensDetails>> searchParameter) {
    SearchDetails<ScreenFindAllScreensDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findByNameEq(ParameterContext<SearchDetails<ScreenFindByNameEqDetails>> searchParameter) {
    SearchDetails<ScreenFindByNameEqDetails> searchDetails = searchParameter.getValue();

    ScreenFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findByComponentEq(ParameterContext<SearchDetails<ScreenFindByComponentEqDetails>> searchParameter) {
    SearchDetails<ScreenFindByComponentEqDetails> searchDetails = searchParameter.getValue();

    ScreenFindByComponentEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("component", "=", "Component", details.getComponent()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findByLinkEq(ParameterContext<SearchDetails<ScreenFindByLinkEqDetails>> searchParameter) {
    SearchDetails<ScreenFindByLinkEqDetails> searchDetails = searchParameter.getValue();

    ScreenFindByLinkEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("link", "LIKE", "Link", details.getLink()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findByNameEq_ComponentEq(ParameterContext<SearchDetails<ScreenFindByNameEq_ComponentEqDetails>> searchParameter) {
    SearchDetails<ScreenFindByNameEq_ComponentEqDetails> searchDetails = searchParameter.getValue();

    ScreenFindByNameEq_ComponentEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("component", "=", "Component", details.getComponent()))
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findBySortOrderGt(ParameterContext<SearchDetails<ScreenFindBySortOrderGtDetails>> searchParameter) {
    SearchDetails<ScreenFindBySortOrderGtDetails> searchDetails = searchParameter.getValue();

    ScreenFindBySortOrderGtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("sortOrder", ">", "SortOrder", details.getSortOrder()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenData> findBySortOrderLt(ParameterContext<SearchDetails<ScreenFindBySortOrderLtDetails>> searchParameter) {
    SearchDetails<ScreenFindBySortOrderLtDetails> searchDetails = searchParameter.getValue();

    ScreenFindBySortOrderLtDetails details = searchDetails.getDetails();

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
  private SearchResult<ScreenData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<ScreenData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<ScreenData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<ScreenData> resultDatas = (List<ScreenData>) dataQuery.getResultList();

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
  public <S extends ScreenFindDetails> SearchResult<ScreenData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<ScreenData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends ScreenFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in ScreenFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("account", "screen", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in ScreenFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("account", "screen", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(ScreenData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
