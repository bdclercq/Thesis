package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * interface for the entity bean ExternalAssetBean,
 * representing a known ExternalAsset
 */
public interface ExternalAssetAgentIf {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business logic methods ==========*/

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ExternalAssetDetails externalAssetDetails);
  CrudsResult<DataRef> modify(ExternalAssetDetails externalAssetDetails);
  <P> CrudsResult<DataRef> createOrModify(P projection);
  CrudsResult<Void> delete(Long id);
  CrudsResult<Void> delete(DataRef dataRef);

  <T> CrudsResult<T> getProjection(ProjectionRef projectionRef);
  CrudsResult<DataRef> resolveDataRef(DataRef dataRef);
  CrudsResult<ExternalAssetDetails> getDetails(DataRef dataRef);
  // anchor:crud-methods:end

  // anchor:exposed-fields:start
  // anchor:exposed-fields:end

  // anchor:search-methods:start
  <S extends ExternalAssetFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails);
  // anchor:search-methods:end

  // anchor:data-only-methods:start
  CrudsResult<ExternalAssetDetails> getDetails(Long externalAssetId);
  CrudsResult<DataRef> getDataRef(String name);

  SearchResult<DataRef> findAllDataRefs();
  SearchResult<ExternalAssetInfo> findAllInfos();
  // anchor:data-only-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
