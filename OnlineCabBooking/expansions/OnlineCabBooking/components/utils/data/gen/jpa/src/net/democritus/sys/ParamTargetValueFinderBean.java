package net.democritus.sys;

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

public class ParamTargetValueFinderBean implements ParamTargetValueFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends ParamTargetValueFindDetails>,
      BiFunction<ParamTargetValueFinderBean, ParameterContext, SearchResult<ParamTargetValueData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ParamTargetValueFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ParamTargetValueFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends ParamTargetValueFindDetails>, BiFunction<ParamTargetValueFinderBean, ParameterContext, SearchResult<ParamTargetValueData>>> generateFinderMapping() {
    Map<Class<? extends ParamTargetValueFindDetails>, BiFunction<ParamTargetValueFinderBean, ParameterContext, SearchResult<ParamTargetValueData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(ParamTargetValueFindAllParamTargetValuesDetails.class, ParamTargetValueFinderBean::findAllParamTargetValues);
    finderMapping.put(ParamTargetValueFindByParamEqDetails.class, ParamTargetValueFinderBean::findByParamEq);
    finderMapping.put(ParamTargetValueFindByParamEq_TargetEqDetails.class, ParamTargetValueFinderBean::findByParamEq_TargetEq);
    finderMapping.put(ParamTargetValueFindByParamEq_ValueEqDetails.class, ParamTargetValueFinderBean::findByParamEq_ValueEq);
    finderMapping.put(ParamTargetValueFindByTargetEqDetails.class, ParamTargetValueFinderBean::findByTargetEq);
    finderMapping.put(ParamTargetValueFindByTargetEq_ValueEqDetails.class, ParamTargetValueFinderBean::findByTargetEq_ValueEq);
    finderMapping.put(ParamTargetValueFindByValueEqDetails.class, ParamTargetValueFinderBean::findByValueEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findAllParamTargetValues(ParameterContext<SearchDetails<ParamTargetValueFindAllParamTargetValuesDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindAllParamTargetValuesDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findByParamEq(ParameterContext<SearchDetails<ParamTargetValueFindByParamEqDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindByParamEqDetails> searchDetails = searchParameter.getValue();

    ParamTargetValueFindByParamEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("param", "LIKE", "Param", details.getParam()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findByParamEq_TargetEq(ParameterContext<SearchDetails<ParamTargetValueFindByParamEq_TargetEqDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindByParamEq_TargetEqDetails> searchDetails = searchParameter.getValue();

    ParamTargetValueFindByParamEq_TargetEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("param", "LIKE", "Param", details.getParam()))
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findByParamEq_ValueEq(ParameterContext<SearchDetails<ParamTargetValueFindByParamEq_ValueEqDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindByParamEq_ValueEqDetails> searchDetails = searchParameter.getValue();

    ParamTargetValueFindByParamEq_ValueEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("param", "LIKE", "Param", details.getParam()))
        .addParameter(QueryParameter.createStringParameter("value", "LIKE", "Value", details.getValue()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findByTargetEq(ParameterContext<SearchDetails<ParamTargetValueFindByTargetEqDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindByTargetEqDetails> searchDetails = searchParameter.getValue();

    ParamTargetValueFindByTargetEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findByTargetEq_ValueEq(ParameterContext<SearchDetails<ParamTargetValueFindByTargetEq_ValueEqDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindByTargetEq_ValueEqDetails> searchDetails = searchParameter.getValue();

    ParamTargetValueFindByTargetEq_ValueEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addParameter(QueryParameter.createStringParameter("value", "LIKE", "Value", details.getValue()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ParamTargetValueData> findByValueEq(ParameterContext<SearchDetails<ParamTargetValueFindByValueEqDetails>> searchParameter) {
    SearchDetails<ParamTargetValueFindByValueEqDetails> searchDetails = searchParameter.getValue();

    ParamTargetValueFindByValueEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("value", "LIKE", "Value", details.getValue()))
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
  private SearchResult<ParamTargetValueData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<ParamTargetValueData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<ParamTargetValueData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<ParamTargetValueData> resultDatas = (List<ParamTargetValueData>) dataQuery.getResultList();

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
  public <S extends ParamTargetValueFindDetails> SearchResult<ParamTargetValueData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<ParamTargetValueData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends ParamTargetValueFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in ParamTargetValueFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("utils", "paramTargetValue", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in ParamTargetValueFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("utils", "paramTargetValue", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(ParamTargetValueData.ENTITY_NAME, "id");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
