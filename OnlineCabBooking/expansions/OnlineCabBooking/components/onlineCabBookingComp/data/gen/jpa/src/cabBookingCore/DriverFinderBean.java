package cabBookingCore;

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

public class DriverFinderBean implements DriverFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends DriverFindDetails>,
      BiFunction<DriverFinderBean, ParameterContext, SearchResult<DriverData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DriverFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DriverFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends DriverFindDetails>, BiFunction<DriverFinderBean, ParameterContext, SearchResult<DriverData>>> generateFinderMapping() {
    Map<Class<? extends DriverFindDetails>, BiFunction<DriverFinderBean, ParameterContext, SearchResult<DriverData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(DriverFindAllDriversDetails.class, DriverFinderBean::findAllDrivers);
    finderMapping.put(DriverFindByNameEqDetails.class, DriverFinderBean::findByNameEq);
    finderMapping.put(DriverFindByLicenseNoEqDetails.class, DriverFinderBean::findByLicenseNoEq);
    finderMapping.put(DriverFindByRatingEqDetails.class, DriverFinderBean::findByRatingEq);
    finderMapping.put(DriverFindByIsAvailableEqDetails.class, DriverFinderBean::findByIsAvailableEq);
    finderMapping.put(DriverFindByTripBookingEqDetails.class, DriverFinderBean::findByTripBookingEq);
    finderMapping.put(DriverFindByCabEqDetails.class, DriverFinderBean::findByCabEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findAllDrivers(ParameterContext<SearchDetails<DriverFindAllDriversDetails>> searchParameter) {
    SearchDetails<DriverFindAllDriversDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findByNameEq(ParameterContext<SearchDetails<DriverFindByNameEqDetails>> searchParameter) {
    SearchDetails<DriverFindByNameEqDetails> searchDetails = searchParameter.getValue();

    DriverFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findByLicenseNoEq(ParameterContext<SearchDetails<DriverFindByLicenseNoEqDetails>> searchParameter) {
    SearchDetails<DriverFindByLicenseNoEqDetails> searchDetails = searchParameter.getValue();

    DriverFindByLicenseNoEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("licenseNo", "=", "LicenseNo", details.getLicenseNo()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findByRatingEq(ParameterContext<SearchDetails<DriverFindByRatingEqDetails>> searchParameter) {
    SearchDetails<DriverFindByRatingEqDetails> searchDetails = searchParameter.getValue();

    DriverFindByRatingEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("rating", "=", "Rating", details.getRating()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findByIsAvailableEq(ParameterContext<SearchDetails<DriverFindByIsAvailableEqDetails>> searchParameter) {
    SearchDetails<DriverFindByIsAvailableEqDetails> searchDetails = searchParameter.getValue();

    DriverFindByIsAvailableEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("isAvailable", "=", "IsAvailable", details.getIsAvailable()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findByTripBookingEq(ParameterContext<SearchDetails<DriverFindByTripBookingEqDetails>> searchParameter) {
    SearchDetails<DriverFindByTripBookingEqDetails> searchDetails = searchParameter.getValue();

    DriverFindByTripBookingEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("tripBooking", "=", "TripBooking", details.getTripBooking()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<DriverData> findByCabEq(ParameterContext<SearchDetails<DriverFindByCabEqDetails>> searchParameter) {
    SearchDetails<DriverFindByCabEqDetails> searchDetails = searchParameter.getValue();

    DriverFindByCabEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("cab", "=", "Cab", details.getCab()))
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
  private SearchResult<DriverData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<DriverData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<DriverData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<DriverData> resultDatas = (List<DriverData>) dataQuery.getResultList();

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
  public <S extends DriverFindDetails> SearchResult<DriverData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<DriverData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends DriverFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in DriverFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("onlineCabBookingComp", "driver", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in DriverFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("onlineCabBookingComp", "driver", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(DriverData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
