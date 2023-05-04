package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ExternalAssetFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<ExternalAssetData> findAllExternalAssets(ParameterContext<SearchDetails<ExternalAssetFindAllExternalAssetsDetails>> searchParameter);
  SearchResult<ExternalAssetData> findByNameEq(ParameterContext<SearchDetails<ExternalAssetFindByNameEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends ExternalAssetFindDetails> SearchResult<ExternalAssetData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
