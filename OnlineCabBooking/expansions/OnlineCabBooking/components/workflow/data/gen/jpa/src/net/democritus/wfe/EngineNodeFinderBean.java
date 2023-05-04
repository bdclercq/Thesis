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

public class EngineNodeFinderBean implements EngineNodeFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends EngineNodeFindDetails>,
      BiFunction<EngineNodeFinderBean, ParameterContext, SearchResult<EngineNodeData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineNodeFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends EngineNodeFindDetails>, BiFunction<EngineNodeFinderBean, ParameterContext, SearchResult<EngineNodeData>>> generateFinderMapping() {
    Map<Class<? extends EngineNodeFindDetails>, BiFunction<EngineNodeFinderBean, ParameterContext, SearchResult<EngineNodeData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(EngineNodeFindAllEngineNodesDetails.class, EngineNodeFinderBean::findAllEngineNodes);
    finderMapping.put(EngineNodeFindByNameEqDetails.class, EngineNodeFinderBean::findByNameEq);
    finderMapping.put(EngineNodeFindByMasterEqDetails.class, EngineNodeFinderBean::findByMasterEq);
    finderMapping.put(EngineNodeFindByMasterEq_LastActiveLtDetails.class, EngineNodeFinderBean::findByMasterEq_LastActiveLt);
    finderMapping.put(EngineNodeFindByLastActiveLtDetails.class, EngineNodeFinderBean::findByLastActiveLt);
    finderMapping.put(EngineNodeFindByStatusEqDetails.class, EngineNodeFinderBean::findByStatusEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeData> findAllEngineNodes(ParameterContext<SearchDetails<EngineNodeFindAllEngineNodesDetails>> searchParameter) {
    SearchDetails<EngineNodeFindAllEngineNodesDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeData> findByNameEq(ParameterContext<SearchDetails<EngineNodeFindByNameEqDetails>> searchParameter) {
    SearchDetails<EngineNodeFindByNameEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeData> findByMasterEq(ParameterContext<SearchDetails<EngineNodeFindByMasterEqDetails>> searchParameter) {
    SearchDetails<EngineNodeFindByMasterEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeFindByMasterEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("master", "=", "Master", details.getMaster()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeData> findByMasterEq_LastActiveLt(ParameterContext<SearchDetails<EngineNodeFindByMasterEq_LastActiveLtDetails>> searchParameter) {
    SearchDetails<EngineNodeFindByMasterEq_LastActiveLtDetails> searchDetails = searchParameter.getValue();

    EngineNodeFindByMasterEq_LastActiveLtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("master", "=", "Master", details.getMaster()))
        .addParameter(QueryParameter.createTimestampParameter("lastActive", "<", "LastActive", details.getLastActive()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeData> findByLastActiveLt(ParameterContext<SearchDetails<EngineNodeFindByLastActiveLtDetails>> searchParameter) {
    SearchDetails<EngineNodeFindByLastActiveLtDetails> searchDetails = searchParameter.getValue();

    EngineNodeFindByLastActiveLtDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createTimestampParameter("lastActive", "<", "LastActive", details.getLastActive()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<EngineNodeData> findByStatusEq(ParameterContext<SearchDetails<EngineNodeFindByStatusEqDetails>> searchParameter) {
    SearchDetails<EngineNodeFindByStatusEqDetails> searchDetails = searchParameter.getValue();

    EngineNodeFindByStatusEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("status", "LIKE", "Status", details.getStatus()))
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
  private SearchResult<EngineNodeData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<EngineNodeData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<EngineNodeData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<EngineNodeData> resultDatas = (List<EngineNodeData>) dataQuery.getResultList();

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
  public <S extends EngineNodeFindDetails> SearchResult<EngineNodeData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<EngineNodeData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends EngineNodeFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in EngineNodeFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("workflow", "engineNode", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in EngineNodeFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("workflow", "engineNode", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(EngineNodeData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
