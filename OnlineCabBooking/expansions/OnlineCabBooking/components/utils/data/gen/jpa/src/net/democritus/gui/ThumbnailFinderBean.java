package net.democritus.gui;

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

public class ThumbnailFinderBean implements ThumbnailFinderLocal {

  private EntityManager entityManager;

  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends ThumbnailFindDetails>,
      BiFunction<ThumbnailFinderBean, ParameterContext, SearchResult<ThumbnailData>>> finderMapping = generateFinderMapping();

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ThumbnailFinderBean.class);
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ThumbnailFinderBean(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("rawtypes")
  private static Map<Class<? extends ThumbnailFindDetails>, BiFunction<ThumbnailFinderBean, ParameterContext, SearchResult<ThumbnailData>>> generateFinderMapping() {
    Map<Class<? extends ThumbnailFindDetails>, BiFunction<ThumbnailFinderBean, ParameterContext, SearchResult<ThumbnailData>>> finderMapping = new HashMap<>();
    // anchor:register-finders:start
    finderMapping.put(ThumbnailFindAllThumbnailsDetails.class, ThumbnailFinderBean::findAllThumbnails);
    finderMapping.put(ThumbnailFindByNameEqDetails.class, ThumbnailFinderBean::findByNameEq);
    finderMapping.put(ThumbnailFindByDepthEqDetails.class, ThumbnailFinderBean::findByDepthEq);
    finderMapping.put(ThumbnailFindByTargetNameEqDetails.class, ThumbnailFinderBean::findByTargetNameEq);
    finderMapping.put(ThumbnailFindByTargetTypeEqDetails.class, ThumbnailFinderBean::findByTargetTypeEq);
    finderMapping.put(ThumbnailFindByThumbTypeEqDetails.class, ThumbnailFinderBean::findByThumbTypeEq);
    finderMapping.put(ThumbnailFindByUriEqDetails.class, ThumbnailFinderBean::findByUriEq);
    finderMapping.put(ThumbnailFindByUriEq_DepthEqDetails.class, ThumbnailFinderBean::findByUriEq_DepthEq);
    finderMapping.put(ThumbnailFindByUriEq_TargetTypeEqDetails.class, ThumbnailFinderBean::findByUriEq_TargetTypeEq);
    finderMapping.put(ThumbnailFindByUriEq_ThumbTypeEqDetails.class, ThumbnailFinderBean::findByUriEq_ThumbTypeEq);
    // anchor:register-finders:end
    // anchor:register-customFinders:start
    // anchor:register-customFinders:end
    return Collections.unmodifiableMap(finderMapping);
  }

  // anchor:findBys-methods:start
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findAllThumbnails(ParameterContext<SearchDetails<ThumbnailFindAllThumbnailsDetails>> searchParameter) {
    SearchDetails<ThumbnailFindAllThumbnailsDetails> searchDetails = searchParameter.getValue();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByNameEq(ParameterContext<SearchDetails<ThumbnailFindByNameEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByNameEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("name", "LIKE", "Name", details.getName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByDepthEq(ParameterContext<SearchDetails<ThumbnailFindByDepthEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByDepthEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByDepthEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("depth", "=", "Depth", details.getDepth()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByTargetNameEq(ParameterContext<SearchDetails<ThumbnailFindByTargetNameEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByTargetNameEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByTargetNameEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("targetName", "LIKE", "TargetName", details.getTargetName()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByTargetTypeEq(ParameterContext<SearchDetails<ThumbnailFindByTargetTypeEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByTargetTypeEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByTargetTypeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("targetType", "LIKE", "TargetType", details.getTargetType()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByThumbTypeEq(ParameterContext<SearchDetails<ThumbnailFindByThumbTypeEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByThumbTypeEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByThumbTypeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("thumbType", "LIKE", "ThumbType", details.getThumbType()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByUriEq(ParameterContext<SearchDetails<ThumbnailFindByUriEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByUriEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByUriEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("uri", "LIKE", "Uri", details.getUri()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByUriEq_DepthEq(ParameterContext<SearchDetails<ThumbnailFindByUriEq_DepthEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByUriEq_DepthEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByUriEq_DepthEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createValueParameter("depth", "=", "Depth", details.getDepth()))
        .addParameter(QueryParameter.createStringParameter("uri", "LIKE", "Uri", details.getUri()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByUriEq_TargetTypeEq(ParameterContext<SearchDetails<ThumbnailFindByUriEq_TargetTypeEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByUriEq_TargetTypeEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByUriEq_TargetTypeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("targetType", "LIKE", "TargetType", details.getTargetType()))
        .addParameter(QueryParameter.createStringParameter("uri", "LIKE", "Uri", details.getUri()))
        .addSortFields(searchDetails.getSortFields())
        ;

    return fetchData((ParameterContext) searchParameter, queryBuilder);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SearchResult<ThumbnailData> findByUriEq_ThumbTypeEq(ParameterContext<SearchDetails<ThumbnailFindByUriEq_ThumbTypeEqDetails>> searchParameter) {
    SearchDetails<ThumbnailFindByUriEq_ThumbTypeEqDetails> searchDetails = searchParameter.getValue();

    ThumbnailFindByUriEq_ThumbTypeEqDetails details = searchDetails.getDetails();

    JPAQueryBuilder queryBuilder = createQueryBuilder()
        .addParameter(QueryParameter.createStringParameter("thumbType", "LIKE", "ThumbType", details.getThumbType()))
        .addParameter(QueryParameter.createStringParameter("uri", "LIKE", "Uri", details.getUri()))
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
  private SearchResult<ThumbnailData> fetchData(ParameterContext<SearchDetails<?>> searchParameter, QueryBuilder queryBuilder) {
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

  private SearchResult<ThumbnailData> fetchData(SearchDetails<?> searchDetails, Query countQuery, Query dataQuery) {
    Long total = 0L;
    if (!searchDetails.getSkipCount()) {
      List<Long> countList = countQuery.getResultList();
      total = countList.get(0);

      // @anchor:fetch-after-count:start
      if (total == 0 || searchDetails.getPaging().getRowsPerPage() == 0) {
        return SearchResult.success(Collections.<ThumbnailData>emptyList(), total.intValue());
      }
      // @anchor:fetch-after-count:end
      // anchor:custom-fetch-after-count:start
      // anchor:custom-fetch-after-count:end
    }

    setPagingRestrictions(searchDetails.getPaging(), dataQuery);

    List<ThumbnailData> resultDatas = (List<ThumbnailData>) dataQuery.getResultList();

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
  public <S extends ThumbnailFindDetails> SearchResult<ThumbnailData> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<ThumbnailData> searchResult;

    // @anchor:before-find:start
    // @anchor:before-find:end
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    Class<? extends ThumbnailFindDetails> findDetailsClass = searchParameter.getValue().getDetails().getClass();
    try {
      if (finderMapping.containsKey(findDetailsClass)) {
        searchResult = finderMapping.get(findDetailsClass).apply(this, searchParameter);
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              findDetailsClass.getName() + " does not match a finder in ThumbnailFinder.find()"
          );
        }

        searchResult = SearchResult.error(Diagnostic.error("utils", "thumbnail", "invalidFinderException"));
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Exception when processing " + findDetailsClass.getName() + " in ThumbnailFinder.find() ", e
        );
      }

      searchResult = SearchResult.error(Diagnostic.error("utils", "thumbnail", "finderException"));
    }

    // @anchor:after-find:start
    // @anchor:after-find:end
    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return searchResult;
  }

  private JPAQueryBuilder createQueryBuilder() {
    return new JPAQueryBuilder(ThumbnailData.ENTITY_NAME, "name");
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
