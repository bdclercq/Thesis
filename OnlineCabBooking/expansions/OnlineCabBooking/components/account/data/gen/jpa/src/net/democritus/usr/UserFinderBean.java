package net.democritus.usr;

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

public class UserFinderBean implements UserFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends UserFindDetails>,
      BiFunction<UserFinderBean, ParameterContext, SearchResult<UserData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(UserFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public UserFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends UserFindDetails>, BiFunction<UserFinderBean, ParameterContext, SearchResult<UserData>>> generateFinderMapping() {
    Map<Class<? extends UserFindDetails>, BiFunction<UserFinderBean, ParameterContext, SearchResult<UserData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(UserFindAllUsersDetails.class, UserFinderBean::findAllUsers);
    finderMapping.put(UserFindByNameEqDetails.class, UserFinderBean::findByNameEq);
    finderMapping.put(UserFindByAccountEqDetails.class, UserFinderBean::findByAccountEq);
    finderMapping.put(UserFindByAccountEq_ProfileEqDetails.class, UserFinderBean::findByAccountEq_ProfileEq);
    finderMapping.put(UserFindByFullNameEqDetails.class, UserFinderBean::findByFullNameEq);
    finderMapping.put(UserFindByPersNrEqDetails.class, UserFinderBean::findByPersNrEq);
    finderMapping.put(UserFindByProfileEqDetails.class, UserFinderBean::findByProfileEq);
    finderMapping.put(UserFindByEmailEqDetails.class, UserFinderBean::findByEmailEq);
    finderMapping.put(UserFindByNameEq_ProfileEqDetails.class, UserFinderBean::findByNameEq_ProfileEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findAllUsers(ParameterContext<SearchDetails<UserFindAllUsersDetails>> searchParameter) {
    SearchDetails<UserFindAllUsersDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByNameEq(ParameterContext<SearchDetails<UserFindByNameEqDetails>> searchParameter) {
    SearchDetails<UserFindByNameEqDetails> searchDetails = searchParameter.getValue();

    UserFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByAccountEq(ParameterContext<SearchDetails<UserFindByAccountEqDetails>> searchParameter) {
    SearchDetails<UserFindByAccountEqDetails> searchDetails = searchParameter.getValue();

    UserFindByAccountEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("account", "=", "Account", details.getAccount()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByAccountEq_ProfileEq(ParameterContext<SearchDetails<UserFindByAccountEq_ProfileEqDetails>> searchParameter) {
    SearchDetails<UserFindByAccountEq_ProfileEqDetails> searchDetails = searchParameter.getValue();

    UserFindByAccountEq_ProfileEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("account", "=", "Account", details.getAccount()))
        .addParameter(QueryParameter.createDataRefParameter("profile", "=", "Profile", details.getProfile()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByFullNameEq(ParameterContext<SearchDetails<UserFindByFullNameEqDetails>> searchParameter) {
    SearchDetails<UserFindByFullNameEqDetails> searchDetails = searchParameter.getValue();

    UserFindByFullNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("fullName", "LIKE", "FullName", details.getFullName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByPersNrEq(ParameterContext<SearchDetails<UserFindByPersNrEqDetails>> searchParameter) {
    SearchDetails<UserFindByPersNrEqDetails> searchDetails = searchParameter.getValue();

    UserFindByPersNrEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("persNr", "LIKE", "PersNr", details.getPersNr()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByProfileEq(ParameterContext<SearchDetails<UserFindByProfileEqDetails>> searchParameter) {
    SearchDetails<UserFindByProfileEqDetails> searchDetails = searchParameter.getValue();

    UserFindByProfileEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("profile", "=", "Profile", details.getProfile()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByEmailEq(ParameterContext<SearchDetails<UserFindByEmailEqDetails>> searchParameter) {
    SearchDetails<UserFindByEmailEqDetails> searchDetails = searchParameter.getValue();

    UserFindByEmailEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("email", "LIKE", "Email", details.getEmail()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<UserData> findByNameEq_ProfileEq(ParameterContext<SearchDetails<UserFindByNameEq_ProfileEqDetails>> searchParameter) {
    SearchDetails<UserFindByNameEq_ProfileEqDetails> searchDetails = searchParameter.getValue();

    UserFindByNameEq_ProfileEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addParameter(QueryParameter.createDataRefParameter("profile", "=", "Profile", details.getProfile()))
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
  private SearchResult<UserData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<UserData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<UserData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<UserData> resultDatas = (List<UserData>) dataQuery.getResultList();

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
  public <S extends UserFindDetails> SearchResult<UserData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<UserData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends UserFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in UserFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("account", "user", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in UserFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("account", "user", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(UserData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
