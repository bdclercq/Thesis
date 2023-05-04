package net.democritus.acl;

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
import java.util.ArrayList;
import java.util.Arrays;
// anchor:custom-imports:end

public class DataAccessFinderBean implements DataAccessFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends DataAccessFindDetails>,
      BiFunction<DataAccessFinderBean, ParameterContext, SearchResult<DataAccessData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DataAccessFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DataAccessFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends DataAccessFindDetails>, BiFunction<DataAccessFinderBean, ParameterContext, SearchResult<DataAccessData>>> generateFinderMapping() {
    Map<Class<? extends DataAccessFindDetails>, BiFunction<DataAccessFinderBean, ParameterContext, SearchResult<DataAccessData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(DataAccessFindAllDataAccesssDetails.class, DataAccessFinderBean::findAllDataAccesss);
    finderMapping.put(DataAccessFindByNameEqDetails.class, DataAccessFinderBean::findByNameEq);
    finderMapping.put(DataAccessFindByElementEqDetails.class, DataAccessFinderBean::findByElementEq);
    finderMapping.put(DataAccessFindByElementEq_FunctionalityEqDetails.class, DataAccessFinderBean::findByElementEq_FunctionalityEq);
    finderMapping.put(DataAccessFindByElementEq_TargetEqDetails.class, DataAccessFinderBean::findByElementEq_TargetEq);
    finderMapping.put(DataAccessFindByForProfileEqDetails.class, DataAccessFinderBean::findByForProfileEq);
    finderMapping.put(DataAccessFindByForProfileEq_ElementEqDetails.class, DataAccessFinderBean::findByForProfileEq_ElementEq);
    finderMapping.put(DataAccessFindByForProfileEq_FunctionalityEqDetails.class, DataAccessFinderBean::findByForProfileEq_FunctionalityEq);
    finderMapping.put(DataAccessFindByForUserEqDetails.class, DataAccessFinderBean::findByForUserEq);
    finderMapping.put(DataAccessFindByForUserEq_ElementEqDetails.class, DataAccessFinderBean::findByForUserEq_ElementEq);
    finderMapping.put(DataAccessFindByForUserEq_FunctionalityEqDetails.class, DataAccessFinderBean::findByForUserEq_FunctionalityEq);
    finderMapping.put(DataAccessFindByTargetEqDetails.class, DataAccessFinderBean::findByTargetEq);
    finderMapping.put(DataAccessFindByForUserGroupEq_ElementEqDetails.class, DataAccessFinderBean::findByForUserGroupEq_ElementEq);
    finderMapping.put(DataAccessFindByForUserEq_ElementEq_TargetEq_FunctionalityEqDetails.class, DataAccessFinderBean::findByForUserEq_ElementEq_TargetEq_FunctionalityEq);
    finderMapping.put(DataAccessFindByForProfileEq_ElementEq_TargetEq_FunctionalityEqDetails.class, DataAccessFinderBean::findByForProfileEq_ElementEq_TargetEq_FunctionalityEq);
    finderMapping.put(DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqDetails.class, DataAccessFinderBean::findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq);
    finderMapping.put(DataAccessFindByForUserGroupEqDetails.class, DataAccessFinderBean::findByForUserGroupEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    finderMapping.put(DataAccessFindBySpecificationOrWildcardDetails.class, DataAccessFinderBean::findBySpecificationOrWildcard);
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findAllDataAccesss(ParameterContext<SearchDetails<DataAccessFindAllDataAccesssDetails>> searchParameter) {
    SearchDetails<DataAccessFindAllDataAccesssDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByNameEq(ParameterContext<SearchDetails<DataAccessFindByNameEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByNameEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByElementEq(ParameterContext<SearchDetails<DataAccessFindByElementEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByElementEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByElementEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByElementEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByElementEq_FunctionalityEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByElementEq_FunctionalityEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByElementEq_FunctionalityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createStringParameter("functionality", "LIKE", "Functionality", details.getFunctionality()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByElementEq_TargetEq(ParameterContext<SearchDetails<DataAccessFindByElementEq_TargetEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByElementEq_TargetEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByElementEq_TargetEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForProfileEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForProfileEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForProfileEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forProfile", "=", "ForProfile", details.getForProfile()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForProfileEq_ElementEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForProfileEq_ElementEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createDataRefParameter("forProfile", "=", "ForProfile", details.getForProfile()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForProfileEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEq_FunctionalityEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForProfileEq_FunctionalityEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForProfileEq_FunctionalityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forProfile", "=", "ForProfile", details.getForProfile()))
        .addParameter(QueryParameter.createStringParameter("functionality", "LIKE", "Functionality", details.getFunctionality()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserEq(ParameterContext<SearchDetails<DataAccessFindByForUserEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forUser", "=", "ForUser", details.getForUser()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserEq_ElementEq(ParameterContext<SearchDetails<DataAccessFindByForUserEq_ElementEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserEq_ElementEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserEq_ElementEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createDataRefParameter("forUser", "=", "ForUser", details.getForUser()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForUserEq_FunctionalityEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserEq_FunctionalityEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserEq_FunctionalityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forUser", "=", "ForUser", details.getForUser()))
        .addParameter(QueryParameter.createStringParameter("functionality", "LIKE", "Functionality", details.getFunctionality()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByTargetEq(ParameterContext<SearchDetails<DataAccessFindByTargetEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByTargetEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByTargetEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserGroupEq_ElementEq(ParameterContext<SearchDetails<DataAccessFindByForUserGroupEq_ElementEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserGroupEq_ElementEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserGroupEq_ElementEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createDataRefParameter("forUserGroup", "=", "ForUserGroup", details.getForUserGroup()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserEq_ElementEq_TargetEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForUserEq_ElementEq_TargetEq_FunctionalityEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserEq_ElementEq_TargetEq_FunctionalityEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserEq_ElementEq_TargetEq_FunctionalityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forUser", "=", "ForUser", details.getForUser()))
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addParameter(QueryParameter.createStringParameter("functionality", "LIKE", "Functionality", details.getFunctionality()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForProfileEq_ElementEq_TargetEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEq_ElementEq_TargetEq_FunctionalityEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForProfileEq_ElementEq_TargetEq_FunctionalityEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForProfileEq_ElementEq_TargetEq_FunctionalityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forProfile", "=", "ForProfile", details.getForProfile()))
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addParameter(QueryParameter.createStringParameter("functionality", "LIKE", "Functionality", details.getFunctionality()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forUserGroup", "=", "ForUserGroup", details.getForUserGroup()))
        .addParameter(QueryParameter.createStringParameter("element", "LIKE", "Element", details.getElement()))
        .addParameter(QueryParameter.createStringParameter("target", "LIKE", "Target", details.getTarget()))
        .addParameter(QueryParameter.createStringParameter("functionality", "LIKE", "Functionality", details.getFunctionality()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findByForUserGroupEq(ParameterContext<SearchDetails<DataAccessFindByForUserGroupEqDetails>> searchParameter) {
    SearchDetails<DataAccessFindByForUserGroupEqDetails> searchDetails = searchParameter.getValue();

    DataAccessFindByForUserGroupEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forUserGroup", "=", "ForUserGroup", details.getForUserGroup()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  // anchor:findBys-methods:end

  // anchor:findBys-customFinders:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DataAccessData> findBySpecificationOrWildcard(ParameterContext<SearchDetails<DataAccessFindBySpecificationOrWildcardDetails>> searchParameter) {
    SearchDetails<DataAccessFindBySpecificationOrWildcardDetails> searchDetails = searchParameter.getValue();
    SearchResult<DataAccessData> searchResult = new SearchResult.SearchError<DataAccessData>();
    //anchor:custom-findBySpecificationOrWildcard-finder:start
    DataAccessFindBySpecificationOrWildcardDetails details = searchDetails.getDetails();

    List<String> elementPatterns = new ArrayList<>();
    if (details.getElement() != null && !details.getElement().isEmpty()) {
      elementPatterns.add("*");
      elementPatterns.add(details.getElement());
      if (details.getComponent() != null && !details.getComponent().isEmpty()) {
        elementPatterns.add(details.getComponent() + "_*");
        elementPatterns.add(details.getComponent() + "_" + details.getElement());
      }
    }

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("forProfile", "=", "ForProfile", details.getForProfile()))
        .addParameter(QueryParameter.createDataRefParameter("forUserGroup", "=", "ForUserGroup", details.getForUserGroup()))
        .addParameter(QueryParameter.createDataRefParameter("forUser", "=", "ForUser", details.getForUser()))
        .addParameter(QueryParameter.createValueParameter("element", "IN", "Element", elementPatterns))
        .addParameter(QueryParameter.createValueParameter("functionality", "IN", "Functionality",
            details.getFunctionality() != null && !details.getFunctionality().isEmpty()
                ? Arrays.asList("all", details.getFunctionality())
                : null))
        .addSortFields(searchDetails.getSortFields());

    searchResult = fetchData((ParameterContext) searchParameter, queryBuilder);
    //anchor:custom-findBySpecificationOrWildcard-finder:end

    return searchResult;
  }

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
  private SearchResult<DataAccessData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
    SearchDetails<?> searchDetails = searchParameter.getValue();

    // @anchor:fetch-before-create-queries:start
    if (!queryBuilder.hasParameter("Disabled")) {
      queryBuilder.addParameter(QueryParameter.createStringParameter("disabled", "<>", "Disabled", "yes"));
    }
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

  private SearchResult<DataAccessData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<DataAccessData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<DataAccessData> resultDatas = (List<DataAccessData>) dataQuery.getResultList();

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
  public <S extends DataAccessFindDetails> SearchResult<DataAccessData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<DataAccessData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends DataAccessFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in DataAccessFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("account", "dataAccess", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in DataAccessFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("account", "dataAccess", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(DataAccessData.ENTITY_NAME, "id");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
