package net.democritus.workflow;

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

public class StateTaskFinderBean implements StateTaskFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends StateTaskFindDetails>,
      BiFunction<StateTaskFinderBean, ParameterContext, SearchResult<StateTaskData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StateTaskFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public StateTaskFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends StateTaskFindDetails>, BiFunction<StateTaskFinderBean, ParameterContext, SearchResult<StateTaskData>>> generateFinderMapping() {
    Map<Class<? extends StateTaskFindDetails>, BiFunction<StateTaskFinderBean, ParameterContext, SearchResult<StateTaskData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(StateTaskFindAllStateTasksDetails.class, StateTaskFinderBean::findAllStateTasks);
    finderMapping.put(StateTaskFindByNameEqDetails.class, StateTaskFinderBean::findByNameEq);
    finderMapping.put(StateTaskFindByBeginStateEqDetails.class, StateTaskFinderBean::findByBeginStateEq);
    finderMapping.put(StateTaskFindByEndStateEqDetails.class, StateTaskFinderBean::findByEndStateEq);
    finderMapping.put(StateTaskFindByFailedStateEqDetails.class, StateTaskFinderBean::findByFailedStateEq);
    finderMapping.put(StateTaskFindByWorkflowEqDetails.class, StateTaskFinderBean::findByWorkflowEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTaskData> findAllStateTasks(ParameterContext<SearchDetails<StateTaskFindAllStateTasksDetails>> searchParameter) {
    SearchDetails<StateTaskFindAllStateTasksDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTaskData> findByNameEq(ParameterContext<SearchDetails<StateTaskFindByNameEqDetails>> searchParameter) {
    SearchDetails<StateTaskFindByNameEqDetails> searchDetails = searchParameter.getValue();

    StateTaskFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTaskData> findByBeginStateEq(ParameterContext<SearchDetails<StateTaskFindByBeginStateEqDetails>> searchParameter) {
    SearchDetails<StateTaskFindByBeginStateEqDetails> searchDetails = searchParameter.getValue();

    StateTaskFindByBeginStateEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("beginState", "LIKE", "BeginState", details.getBeginState()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTaskData> findByEndStateEq(ParameterContext<SearchDetails<StateTaskFindByEndStateEqDetails>> searchParameter) {
    SearchDetails<StateTaskFindByEndStateEqDetails> searchDetails = searchParameter.getValue();

    StateTaskFindByEndStateEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("endState", "LIKE", "EndState", details.getEndState()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTaskData> findByFailedStateEq(ParameterContext<SearchDetails<StateTaskFindByFailedStateEqDetails>> searchParameter) {
    SearchDetails<StateTaskFindByFailedStateEqDetails> searchDetails = searchParameter.getValue();

    StateTaskFindByFailedStateEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("failedState", "LIKE", "FailedState", details.getFailedState()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTaskData> findByWorkflowEq(ParameterContext<SearchDetails<StateTaskFindByWorkflowEqDetails>> searchParameter) {
    SearchDetails<StateTaskFindByWorkflowEqDetails> searchDetails = searchParameter.getValue();

    StateTaskFindByWorkflowEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("workflow", "=", "Workflow", details.getWorkflow()))
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
  private SearchResult<StateTaskData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<StateTaskData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<StateTaskData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<StateTaskData> resultDatas = (List<StateTaskData>) dataQuery.getResultList();

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
  public <S extends StateTaskFindDetails> SearchResult<StateTaskData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<StateTaskData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends StateTaskFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in StateTaskFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("workflow", "stateTask", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in StateTaskFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("workflow", "stateTask", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(StateTaskData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
