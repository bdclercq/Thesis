package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface InternalAssetChunkFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<InternalAssetChunkData> findAllInternalAssetChunks(ParameterContext<SearchDetails<InternalAssetChunkFindAllInternalAssetChunksDetails>> searchParameter);
  SearchResult<InternalAssetChunkData> findByNameEq(ParameterContext<SearchDetails<InternalAssetChunkFindByNameEqDetails>> searchParameter);
  SearchResult<InternalAssetChunkData> findByInternalAssetEq(ParameterContext<SearchDetails<InternalAssetChunkFindByInternalAssetEqDetails>> searchParameter);
  SearchResult<InternalAssetChunkData> findByInternalAssetEq_PositionEq(ParameterContext<SearchDetails<InternalAssetChunkFindByInternalAssetEq_PositionEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends InternalAssetChunkFindDetails> SearchResult<InternalAssetChunkData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
