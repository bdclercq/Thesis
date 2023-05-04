package net.democritus.usr.menu;

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

public class ScreenProfileFinderBean implements ScreenProfileFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends ScreenProfileFindDetails>,
      BiFunction<ScreenProfileFinderBean, ParameterContext, SearchResult<ScreenProfileData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ScreenProfileFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ScreenProfileFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends ScreenProfileFindDetails>, BiFunction<ScreenProfileFinderBean, ParameterContext, SearchResult<ScreenProfileData>>> generateFinderMapping() {
    Map<Class<? extends ScreenProfileFindDetails>, BiFunction<ScreenProfileFinderBean, ParameterContext, SearchResult<ScreenProfileData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(ScreenProfileFindAllScreenProfilesDetails.class, ScreenProfileFinderBean::findAllScreenProfiles);
    finderMapping.put(ScreenProfileFindByProfileEqDetails.class, ScreenProfileFinderBean::findByProfileEq);
    finderMapping.put(ScreenProfileFindByScreenEqDetails.class, ScreenProfileFinderBean::findByScreenEq);
    finderMapping.put(ScreenProfileFindByScreenEq_ProfileEqDetails.class, ScreenProfileFinderBean::findByScreenEq_ProfileEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenProfileData> findAllScreenProfiles(ParameterContext<SearchDetails<ScreenProfileFindAllScreenProfilesDetails>> searchParameter) {
    SearchDetails<ScreenProfileFindAllScreenProfilesDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenProfileData> findByProfileEq(ParameterContext<SearchDetails<ScreenProfileFindByProfileEqDetails>> searchParameter) {
    SearchDetails<ScreenProfileFindByProfileEqDetails> searchDetails = searchParameter.getValue();

    ScreenProfileFindByProfileEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("profile", "=", "Profile", details.getProfile()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenProfileData> findByScreenEq(ParameterContext<SearchDetails<ScreenProfileFindByScreenEqDetails>> searchParameter) {
    SearchDetails<ScreenProfileFindByScreenEqDetails> searchDetails = searchParameter.getValue();

    ScreenProfileFindByScreenEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("screen", "=", "Screen", details.getScreen()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ScreenProfileData> findByScreenEq_ProfileEq(ParameterContext<SearchDetails<ScreenProfileFindByScreenEq_ProfileEqDetails>> searchParameter) {
    SearchDetails<ScreenProfileFindByScreenEq_ProfileEqDetails> searchDetails = searchParameter.getValue();

    ScreenProfileFindByScreenEq_ProfileEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createDataRefParameter("profile", "=", "Profile", details.getProfile()))
        .addParameter(QueryParameter.createDataRefParameter("screen", "=", "Screen", details.getScreen()))
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
  private SearchResult<ScreenProfileData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<ScreenProfileData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<ScreenProfileData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<ScreenProfileData> resultDatas = (List<ScreenProfileData>) dataQuery.getResultList();

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
  public <S extends ScreenProfileFindDetails> SearchResult<ScreenProfileData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<ScreenProfileData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends ScreenProfileFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in ScreenProfileFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("account", "screenProfile", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in ScreenProfileFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("account", "screenProfile", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(ScreenProfileData.ENTITY_NAME, "id");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
