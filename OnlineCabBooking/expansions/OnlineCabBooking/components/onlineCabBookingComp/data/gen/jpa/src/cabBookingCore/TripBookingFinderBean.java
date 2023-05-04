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

public class TripBookingFinderBean implements TripBookingFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends TripBookingFindDetails>,
      BiFunction<TripBookingFinderBean, ParameterContext, SearchResult<TripBookingData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(TripBookingFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TripBookingFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends TripBookingFindDetails>, BiFunction<TripBookingFinderBean, ParameterContext, SearchResult<TripBookingData>>> generateFinderMapping() {
    Map<Class<? extends TripBookingFindDetails>, BiFunction<TripBookingFinderBean, ParameterContext, SearchResult<TripBookingData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(TripBookingFindAllTripBookingsDetails.class, TripBookingFinderBean::findAllTripBookings);
    finderMapping.put(TripBookingFindByNameEqDetails.class, TripBookingFinderBean::findByNameEq);
    finderMapping.put(TripBookingFindByCustomerEqDetails.class, TripBookingFinderBean::findByCustomerEq);
    finderMapping.put(TripBookingFindByDriverEqDetails.class, TripBookingFinderBean::findByDriverEq);
    finderMapping.put(TripBookingFindAllTripBookingDetails.class, TripBookingFinderBean::findAllTripBooking);
    finderMapping.put(TripBookingFindByFromDateTimeEqDetails.class, TripBookingFinderBean::findByFromDateTimeEq);
    finderMapping.put(TripBookingFindByToDateTimeEqDetails.class, TripBookingFinderBean::findByToDateTimeEq);
    finderMapping.put(TripBookingFindByCustomerEq_FromDateTimeEqDetails.class, TripBookingFinderBean::findByCustomerEq_FromDateTimeEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findAllTripBookings(ParameterContext<SearchDetails<TripBookingFindAllTripBookingsDetails>> searchParameter) {
    SearchDetails<TripBookingFindAllTripBookingsDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findByNameEq(ParameterContext<SearchDetails<TripBookingFindByNameEqDetails>> searchParameter) {
    SearchDetails<TripBookingFindByNameEqDetails> searchDetails = searchParameter.getValue();

    TripBookingFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findByCustomerEq(ParameterContext<SearchDetails<TripBookingFindByCustomerEqDetails>> searchParameter) {
    SearchDetails<TripBookingFindByCustomerEqDetails> searchDetails = searchParameter.getValue();

    TripBookingFindByCustomerEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("customer", "=", "Customer", details.getCustomer()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findByDriverEq(ParameterContext<SearchDetails<TripBookingFindByDriverEqDetails>> searchParameter) {
    SearchDetails<TripBookingFindByDriverEqDetails> searchDetails = searchParameter.getValue();

    TripBookingFindByDriverEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("driver", "=", "Driver", details.getDriver()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findAllTripBooking(ParameterContext<SearchDetails<TripBookingFindAllTripBookingDetails>> searchParameter) {
    SearchDetails<TripBookingFindAllTripBookingDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findByFromDateTimeEq(ParameterContext<SearchDetails<TripBookingFindByFromDateTimeEqDetails>> searchParameter) {
    SearchDetails<TripBookingFindByFromDateTimeEqDetails> searchDetails = searchParameter.getValue();

    TripBookingFindByFromDateTimeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createTimestampParameter("fromDateTime", ">=", "FromDateTime_1", DateNormalizer.stripTime(details.getFromDateTime())))
        .addParameter(QueryParameter.createTimestampParameter("fromDateTime", "<", "FromDateTime_2", DateNormalizer.nextDay(DateNormalizer.stripTime(details.getFromDateTime()))))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findByToDateTimeEq(ParameterContext<SearchDetails<TripBookingFindByToDateTimeEqDetails>> searchParameter) {
    SearchDetails<TripBookingFindByToDateTimeEqDetails> searchDetails = searchParameter.getValue();

    TripBookingFindByToDateTimeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createTimestampParameter("toDateTime", ">=", "ToDateTime_1", DateNormalizer.stripTime(details.getToDateTime())))
        .addParameter(QueryParameter.createTimestampParameter("toDateTime", "<", "ToDateTime_2", DateNormalizer.nextDay(DateNormalizer.stripTime(details.getToDateTime()))))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<TripBookingData> findByCustomerEq_FromDateTimeEq(ParameterContext<SearchDetails<TripBookingFindByCustomerEq_FromDateTimeEqDetails>> searchParameter) {
    SearchDetails<TripBookingFindByCustomerEq_FromDateTimeEqDetails> searchDetails = searchParameter.getValue();

    TripBookingFindByCustomerEq_FromDateTimeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("customer", "=", "Customer", details.getCustomer()))
        .addParameter(QueryParameter.createTimestampParameter("fromDateTime", ">=", "FromDateTime_1", DateNormalizer.stripTime(details.getFromDateTime())))
        .addParameter(QueryParameter.createTimestampParameter("fromDateTime", "<", "FromDateTime_2", DateNormalizer.nextDay(DateNormalizer.stripTime(details.getFromDateTime()))))
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
  private SearchResult<TripBookingData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<TripBookingData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<TripBookingData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<TripBookingData> resultDatas = (List<TripBookingData>) dataQuery.getResultList();

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
  public <S extends TripBookingFindDetails> SearchResult<TripBookingData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<TripBookingData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends TripBookingFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in TripBookingFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("onlineCabBookingComp", "tripBooking", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in TripBookingFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("onlineCabBookingComp", "tripBooking", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(TripBookingData.ENTITY_NAME, "id");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
