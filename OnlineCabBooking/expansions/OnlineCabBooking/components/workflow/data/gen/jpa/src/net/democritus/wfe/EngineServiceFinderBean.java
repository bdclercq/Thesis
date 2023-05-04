package net.democritus.wfe;

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

public class EngineServiceFinderBean implements EngineServiceFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends EngineServiceFindDetails>,
      BiFunction<EngineServiceFinderBean, ParameterContext, SearchResult<EngineServiceData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineServiceFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineServiceFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends EngineServiceFindDetails>, BiFunction<EngineServiceFinderBean, ParameterContext, SearchResult<EngineServiceData>>> generateFinderMapping() {
    Map<Class<? extends EngineServiceFindDetails>, BiFunction<EngineServiceFinderBean, ParameterContext, SearchResult<EngineServiceData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(EngineServiceFindAllEngineServicesDetails.class, EngineServiceFinderBean::findAllEngineServices);
    finderMapping.put(EngineServiceFindByNameEqDetails.class, EngineServiceFinderBean::findByNameEq);
    finderMapping.put(EngineServiceFindByBusyEqDetails.class, EngineServiceFinderBean::findByBusyEq);
    finderMapping.put(EngineServiceFindByChangedEqDetails.class, EngineServiceFinderBean::findByChangedEq);
    finderMapping.put(EngineServiceFindByCollectorEqDetails.class, EngineServiceFinderBean::findByCollectorEq);
    finderMapping.put(EngineServiceFindByLastRunAtGtDetails.class, EngineServiceFinderBean::findByLastRunAtGt);
    finderMapping.put(EngineServiceFindByLastRunAtLtDetails.class, EngineServiceFinderBean::findByLastRunAtLt);
    finderMapping.put(EngineServiceFindByNameEq_CollectorEqDetails.class, EngineServiceFinderBean::findByNameEq_CollectorEq);
    finderMapping.put(EngineServiceFindByStatusEqDetails.class, EngineServiceFinderBean::findByStatusEq);
    finderMapping.put(EngineServiceFindByWorkflowEqDetails.class, EngineServiceFinderBean::findByWorkflowEq);
    finderMapping.put(EngineServiceFindByTimeWindowGroupEqDetails.class, EngineServiceFinderBean::findByTimeWindowGroupEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findAllEngineServices(ParameterContext<SearchDetails<EngineServiceFindAllEngineServicesDetails>> searchParameter) {
    SearchDetails<EngineServiceFindAllEngineServicesDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByNameEq(ParameterContext<SearchDetails<EngineServiceFindByNameEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByNameEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByBusyEq(ParameterContext<SearchDetails<EngineServiceFindByBusyEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByBusyEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByBusyEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("busy", "LIKE", "Busy", details.getBusy()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByChangedEq(ParameterContext<SearchDetails<EngineServiceFindByChangedEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByChangedEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByChangedEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("changed", "LIKE", "Changed", details.getChanged()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByCollectorEq(ParameterContext<SearchDetails<EngineServiceFindByCollectorEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByCollectorEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByCollectorEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("collector", "=", "Collector", details.getCollector()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByLastRunAtGt(ParameterContext<SearchDetails<EngineServiceFindByLastRunAtGtDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByLastRunAtGtDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByLastRunAtGtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createTimestampParameter("lastRunAt", ">", "LastRunAt", details.getLastRunAt() != null ? details.getLastRunAt().getValue() : null))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByLastRunAtLt(ParameterContext<SearchDetails<EngineServiceFindByLastRunAtLtDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByLastRunAtLtDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByLastRunAtLtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createTimestampParameter("lastRunAt", "<", "LastRunAt", details.getLastRunAt() != null ? details.getLastRunAt().getValue() : null))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByNameEq_CollectorEq(ParameterContext<SearchDetails<EngineServiceFindByNameEq_CollectorEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByNameEq_CollectorEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByNameEq_CollectorEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("collector", "=", "Collector", details.getCollector()))
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByStatusEq(ParameterContext<SearchDetails<EngineServiceFindByStatusEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByStatusEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByStatusEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("status", "LIKE", "Status", details.getStatus()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByWorkflowEq(ParameterContext<SearchDetails<EngineServiceFindByWorkflowEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByWorkflowEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByWorkflowEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("workflow", "=", "Workflow", details.getWorkflow()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineServiceData> findByTimeWindowGroupEq(ParameterContext<SearchDetails<EngineServiceFindByTimeWindowGroupEqDetails>> searchParameter) {
    SearchDetails<EngineServiceFindByTimeWindowGroupEqDetails> searchDetails = searchParameter.getValue();

    EngineServiceFindByTimeWindowGroupEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("timeWindowGroup", "=", "TimeWindowGroup", details.getTimeWindowGroup()))
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
  private SearchResult<EngineServiceData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<EngineServiceData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<EngineServiceData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<EngineServiceData> resultDatas = (List<EngineServiceData>) dataQuery.getResultList();

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
  public <S extends EngineServiceFindDetails> SearchResult<EngineServiceData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<EngineServiceData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends EngineServiceFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in EngineServiceFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("workflow", "engineService", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in EngineServiceFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("workflow", "engineService", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(EngineServiceData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
