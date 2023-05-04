package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AssetFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<AssetData> findAllAssets(ParameterContext<SearchDetails<AssetFindAllAssetsDetails>> searchParameter);
  SearchResult<AssetData> findByNameEq(ParameterContext<SearchDetails<AssetFindByNameEqDetails>> searchParameter);
  SearchResult<AssetData> findByFileIdEq(ParameterContext<SearchDetails<AssetFindByFileIdEqDetails>> searchParameter);
  SearchResult<AssetData> findByFileAssetEq(ParameterContext<SearchDetails<AssetFindByFileAssetEqDetails>> searchParameter);
  SearchResult<AssetData> findByInternalAssetEq(ParameterContext<SearchDetails<AssetFindByInternalAssetEqDetails>> searchParameter);
  SearchResult<AssetData> findByExternalAssetEq(ParameterContext<SearchDetails<AssetFindByExternalAssetEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends AssetFindDetails> SearchResult<AssetData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
