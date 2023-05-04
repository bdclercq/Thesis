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

public class StateTimerFinderBean implements StateTimerFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends StateTimerFindDetails>,
      BiFunction<StateTimerFinderBean, ParameterContext, SearchResult<StateTimerData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(StateTimerFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public StateTimerFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends StateTimerFindDetails>, BiFunction<StateTimerFinderBean, ParameterContext, SearchResult<StateTimerData>>> generateFinderMapping() {
    Map<Class<? extends StateTimerFindDetails>, BiFunction<StateTimerFinderBean, ParameterContext, SearchResult<StateTimerData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(StateTimerFindAllStateTimersDetails.class, StateTimerFinderBean::findAllStateTimers);
    finderMapping.put(StateTimerFindByNameEqDetails.class, StateTimerFinderBean::findByNameEq);
    finderMapping.put(StateTimerFindByAlteredStateEqDetails.class, StateTimerFinderBean::findByAlteredStateEq);
    finderMapping.put(StateTimerFindByBeginStateEqDetails.class, StateTimerFinderBean::findByBeginStateEq);
    finderMapping.put(StateTimerFindByTargetStateEqDetails.class, StateTimerFinderBean::findByTargetStateEq);
    finderMapping.put(StateTimerFindByWorkflowEqDetails.class, StateTimerFinderBean::findByWorkflowEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTimerData> findAllStateTimers(ParameterContext<SearchDetails<StateTimerFindAllStateTimersDetails>> searchParameter) {
    SearchDetails<StateTimerFindAllStateTimersDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTimerData> findByNameEq(ParameterContext<SearchDetails<StateTimerFindByNameEqDetails>> searchParameter) {
    SearchDetails<StateTimerFindByNameEqDetails> searchDetails = searchParameter.getValue();

    StateTimerFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTimerData> findByAlteredStateEq(ParameterContext<SearchDetails<StateTimerFindByAlteredStateEqDetails>> searchParameter) {
    SearchDetails<StateTimerFindByAlteredStateEqDetails> searchDetails = searchParameter.getValue();

    StateTimerFindByAlteredStateEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("alteredState", "LIKE", "AlteredState", details.getAlteredState()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTimerData> findByBeginStateEq(ParameterContext<SearchDetails<StateTimerFindByBeginStateEqDetails>> searchParameter) {
    SearchDetails<StateTimerFindByBeginStateEqDetails> searchDetails = searchParameter.getValue();

    StateTimerFindByBeginStateEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("beginState", "LIKE", "BeginState", details.getBeginState()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTimerData> findByTargetStateEq(ParameterContext<SearchDetails<StateTimerFindByTargetStateEqDetails>> searchParameter) {
    SearchDetails<StateTimerFindByTargetStateEqDetails> searchDetails = searchParameter.getValue();

    StateTimerFindByTargetStateEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("targetState", "LIKE", "TargetState", details.getTargetState()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<StateTimerData> findByWorkflowEq(ParameterContext<SearchDetails<StateTimerFindByWorkflowEqDetails>> searchParameter) {
    SearchDetails<StateTimerFindByWorkflowEqDetails> searchDetails = searchParameter.getValue();

    StateTimerFindByWorkflowEqDetails details = searchDetails.getDetails();

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
  private SearchResult<StateTimerData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<StateTimerData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<StateTimerData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<StateTimerData> resultDatas = (List<StateTimerData>) dataQuery.getResultList();

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
  public <S extends StateTimerFindDetails> SearchResult<StateTimerData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<StateTimerData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends StateTimerFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in StateTimerFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("workflow", "stateTimer", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in StateTimerFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("workflow", "stateTimer", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(StateTimerData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
