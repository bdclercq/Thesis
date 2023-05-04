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

public class AccountFinderBean implements AccountFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends AccountFindDetails>,
      BiFunction<AccountFinderBean, ParameterContext, SearchResult<AccountData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AccountFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public AccountFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends AccountFindDetails>, BiFunction<AccountFinderBean, ParameterContext, SearchResult<AccountData>>> generateFinderMapping() {
    Map<Class<? extends AccountFindDetails>, BiFunction<AccountFinderBean, ParameterContext, SearchResult<AccountData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(AccountFindAllAccountsDetails.class, AccountFinderBean::findAllAccounts);
    finderMapping.put(AccountFindByNameEqDetails.class, AccountFinderBean::findByNameEq);
    finderMapping.put(AccountFindByAddressEqDetails.class, AccountFinderBean::findByAddressEq);
    finderMapping.put(AccountFindByCityEqDetails.class, AccountFinderBean::findByCityEq);
    finderMapping.put(AccountFindByCountryEqDetails.class, AccountFinderBean::findByCountryEq);
    finderMapping.put(AccountFindByEmailEqDetails.class, AccountFinderBean::findByEmailEq);
    finderMapping.put(AccountFindByFullNameEqDetails.class, AccountFinderBean::findByFullNameEq);
    finderMapping.put(AccountFindByRefIdEqDetails.class, AccountFinderBean::findByRefIdEq);
    finderMapping.put(AccountFindByStatusEqDetails.class, AccountFinderBean::findByStatusEq);
    finderMapping.put(AccountFindByZipCodeEqDetails.class, AccountFinderBean::findByZipCodeEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findAllAccounts(ParameterContext<SearchDetails<AccountFindAllAccountsDetails>> searchParameter) {
    SearchDetails<AccountFindAllAccountsDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByNameEq(ParameterContext<SearchDetails<AccountFindByNameEqDetails>> searchParameter) {
    SearchDetails<AccountFindByNameEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByAddressEq(ParameterContext<SearchDetails<AccountFindByAddressEqDetails>> searchParameter) {
    SearchDetails<AccountFindByAddressEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByAddressEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("address", "LIKE", "Address", details.getAddress()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByCityEq(ParameterContext<SearchDetails<AccountFindByCityEqDetails>> searchParameter) {
    SearchDetails<AccountFindByCityEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByCityEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("city", "LIKE", "City", details.getCity()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByCountryEq(ParameterContext<SearchDetails<AccountFindByCountryEqDetails>> searchParameter) {
    SearchDetails<AccountFindByCountryEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByCountryEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("country", "LIKE", "Country", details.getCountry()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByEmailEq(ParameterContext<SearchDetails<AccountFindByEmailEqDetails>> searchParameter) {
    SearchDetails<AccountFindByEmailEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByEmailEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("email", "LIKE", "Email", details.getEmail()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByFullNameEq(ParameterContext<SearchDetails<AccountFindByFullNameEqDetails>> searchParameter) {
    SearchDetails<AccountFindByFullNameEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByFullNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("fullName", "LIKE", "FullName", details.getFullName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByRefIdEq(ParameterContext<SearchDetails<AccountFindByRefIdEqDetails>> searchParameter) {
    SearchDetails<AccountFindByRefIdEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByRefIdEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("refId", "LIKE", "RefId", details.getRefId()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByStatusEq(ParameterContext<SearchDetails<AccountFindByStatusEqDetails>> searchParameter) {
    SearchDetails<AccountFindByStatusEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByStatusEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("status", "LIKE", "Status", details.getStatus()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<AccountData> findByZipCodeEq(ParameterContext<SearchDetails<AccountFindByZipCodeEqDetails>> searchParameter) {
    SearchDetails<AccountFindByZipCodeEqDetails> searchDetails = searchParameter.getValue();

    AccountFindByZipCodeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("zipCode", "LIKE", "ZipCode", details.getZipCode()))
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
  private SearchResult<AccountData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<AccountData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<AccountData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<AccountData> resultDatas = (List<AccountData>) dataQuery.getResultList();

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
  public <S extends AccountFindDetails> SearchResult<AccountData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<AccountData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends AccountFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in AccountFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("account", "account", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in AccountFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("account", "account", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(AccountData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
