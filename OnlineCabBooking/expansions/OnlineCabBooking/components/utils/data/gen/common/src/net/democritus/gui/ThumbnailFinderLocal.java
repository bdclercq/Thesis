package net.democritus.gui;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ThumbnailFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<ThumbnailData> findAllThumbnails(ParameterContext<SearchDetails<ThumbnailFindAllThumbnailsDetails>> searchParameter);
  SearchResult<ThumbnailData> findByNameEq(ParameterContext<SearchDetails<ThumbnailFindByNameEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByDepthEq(ParameterContext<SearchDetails<ThumbnailFindByDepthEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByTargetNameEq(ParameterContext<SearchDetails<ThumbnailFindByTargetNameEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByTargetTypeEq(ParameterContext<SearchDetails<ThumbnailFindByTargetTypeEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByThumbTypeEq(ParameterContext<SearchDetails<ThumbnailFindByThumbTypeEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByUriEq(ParameterContext<SearchDetails<ThumbnailFindByUriEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByUriEq_DepthEq(ParameterContext<SearchDetails<ThumbnailFindByUriEq_DepthEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByUriEq_TargetTypeEq(ParameterContext<SearchDetails<ThumbnailFindByUriEq_TargetTypeEqDetails>> searchParameter);
  SearchResult<ThumbnailData> findByUriEq_ThumbTypeEq(ParameterContext<SearchDetails<ThumbnailFindByUriEq_ThumbTypeEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends ThumbnailFindDetails> SearchResult<ThumbnailData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
