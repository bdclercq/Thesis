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

public class EngineNodeServiceFinderBean implements EngineNodeServiceFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends EngineNodeServiceFindDetails>,
      BiFunction<EngineNodeServiceFinderBean, ParameterContext, SearchResult<EngineNodeServiceData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeServiceFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineNodeServiceFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends EngineNodeServiceFindDetails>, BiFunction<EngineNodeServiceFinderBean, ParameterContext, SearchResult<EngineNodeServiceData>>> generateFinderMapping() {
    Map<Class<? extends EngineNodeServiceFindDetails>, BiFunction<EngineNodeServiceFinderBean, ParameterContext, SearchResult<EngineNodeServiceData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(EngineNodeServiceFindAllEngineNodeServicesDetails.class, EngineNodeServiceFinderBean::findAllEngineNodeServices);
    finderMapping.put(EngineNodeServiceFindByNameEqDetails.class, EngineNodeServiceFinderBean::findByNameEq);
    finderMapping.put(EngineNodeServiceFindByStatusEqDetails.class, EngineNodeServiceFinderBean::findByStatusEq);
    finderMapping.put(EngineNodeServiceFindByEngineNodeEqDetails.class, EngineNodeServiceFinderBean::findByEngineNodeEq);
    finderMapping.put(EngineNodeServiceFindByEngineServiceEq_StatusNeDetails.class, EngineNodeServiceFinderBean::findByEngineServiceEq_StatusNe);
    finderMapping.put(EngineNodeServiceFindByEngineServiceEqDetails.class, EngineNodeServiceFinderBean::findByEngineServiceEq);
    finderMapping.put(EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails.class, EngineNodeServiceFinderBean::findByEngineNodeEq_EngineServiceEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findAllEngineNodeServices(ParameterContext<SearchDetails<EngineNodeServiceFindAllEngineNodeServicesDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindAllEngineNodeServicesDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findByNameEq(ParameterContext<SearchDetails<EngineNodeServiceFindByNameEqDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindByNameEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeServiceFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findByStatusEq(ParameterContext<SearchDetails<EngineNodeServiceFindByStatusEqDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindByStatusEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeServiceFindByStatusEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("status", "LIKE", "Status", details.getStatus()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findByEngineNodeEq(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineNodeEqDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindByEngineNodeEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeServiceFindByEngineNodeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("engineNode.id", "=", "EngineNode", details.getEngineNode()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findByEngineServiceEq_StatusNe(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineServiceEq_StatusNeDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindByEngineServiceEq_StatusNeDetails> searchDetails = searchParameter.getValue();

    EngineNodeServiceFindByEngineServiceEq_StatusNeDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("engineService.id", "=", "EngineService", details.getEngineService()))
        .addParameter(QueryParameter.createStringParameter("status", "NOT LIKE", "Status", details.getStatus()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findByEngineServiceEq(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineServiceEqDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindByEngineServiceEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeServiceFindByEngineServiceEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("engineService.id", "=", "EngineService", details.getEngineService()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeServiceData> findByEngineNodeEq_EngineServiceEq(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails>> searchParameter) {
    SearchDetails<EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("engineNode.id", "=", "EngineNode", details.getEngineNode()))
        .addParameter(QueryParameter.createDataRefParameter("engineService.id", "=", "EngineService", details.getEngineService()))
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
  private SearchResult<EngineNodeServiceData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<EngineNodeServiceData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<EngineNodeServiceData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<EngineNodeServiceData> resultDatas = (List<EngineNodeServiceData>) dataQuery.getResultList();

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
  public <S extends EngineNodeServiceFindDetails> SearchResult<EngineNodeServiceData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<EngineNodeServiceData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends EngineNodeServiceFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in EngineNodeServiceFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("workflow", "engineNodeService", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in EngineNodeServiceFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("workflow", "engineNodeService", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(EngineNodeServiceData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
