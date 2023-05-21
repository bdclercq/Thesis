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

public class CabFinderBean implements CabFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends CabFindDetails>,
      BiFunction<CabFinderBean, ParameterContext, SearchResult<CabData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(CabFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public CabFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends CabFindDetails>, BiFunction<CabFinderBean, ParameterContext, SearchResult<CabData>>> generateFinderMapping() {
    Map<Class<? extends CabFindDetails>, BiFunction<CabFinderBean, ParameterContext, SearchResult<CabData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(CabFindAllCabsDetails.class, CabFinderBean::findAllCabs);
    finderMapping.put(CabFindByNameEqDetails.class, CabFinderBean::findByNameEq);
    finderMapping.put(CabFindByDriverEqDetails.class, CabFinderBean::findByDriverEq);
    finderMapping.put(CabFindByCarTypeEqDetails.class, CabFinderBean::findByCarTypeEq);
    finderMapping.put(CabFindAllCabDetails.class, CabFinderBean::findAllCab);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<CabData> findAllCabs(ParameterContext<SearchDetails<CabFindAllCabsDetails>> searchParameter) {
    SearchDetails<CabFindAllCabsDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<CabData> findByNameEq(ParameterContext<SearchDetails<CabFindByNameEqDetails>> searchParameter) {
    SearchDetails<CabFindByNameEqDetails> searchDetails = searchParameter.getValue();

    CabFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<CabData> findByDriverEq(ParameterContext<SearchDetails<CabFindByDriverEqDetails>> searchParameter) {
    SearchDetails<CabFindByDriverEqDetails> searchDetails = searchParameter.getValue();

    CabFindByDriverEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("driver", "=", "Driver", details.getDriver()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<CabData> findByCarTypeEq(ParameterContext<SearchDetails<CabFindByCarTypeEqDetails>> searchParameter) {
    SearchDetails<CabFindByCarTypeEqDetails> searchDetails = searchParameter.getValue();

    CabFindByCarTypeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("carType", "=", "CarType", details.getCarType()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<CabData> findAllCab(ParameterContext<SearchDetails<CabFindAllCabDetails>> searchParameter) {
    SearchDetails<CabFindAllCabDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
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
  private SearchResult<CabData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<CabData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<CabData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<CabData> resultDatas = (List<CabData>) dataQuery.getResultList();

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
  public <S extends CabFindDetails> SearchResult<CabData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<CabData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends CabFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in CabFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("onlineCabBookingComp", "cab", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in CabFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("onlineCabBookingComp", "cab", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(CabData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
