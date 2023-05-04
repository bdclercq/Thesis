package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
import net.democritus.sys.command.CommandResult;
import net.democritus.sys.command.ICommand;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * interface for the entity bean AssetBean,
 * representing a known Asset
 */
public interface AssetAgentIf {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business logic methods ==========*/

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(AssetDetails assetDetails);
  CrudsResult<DataRef> modify(AssetDetails assetDetails);
  <P> CrudsResult<DataRef> createOrModify(P projection);
  CrudsResult<Void> delete(Long id);
  CrudsResult<Void> delete(DataRef dataRef);

  <T> CrudsResult<T> getProjection(ProjectionRef projectionRef);
  CrudsResult<DataRef> resolveDataRef(DataRef dataRef);
  CrudsResult<AssetDetails> getDetails(DataRef dataRef);
  // anchor:crud-methods:end

  // anchor:exposed-fields:start
  // anchor:exposed-fields:end

  // anchor:search-methods:start
  <S extends AssetFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails);
  // anchor:search-methods:end

  // anchor:data-only-methods:start
  CrudsResult<AssetDetails> getDetails(Long assetId);
  CrudsResult<DataRef> getDataRef(String name);

  SearchResult<DataRef> findAllDataRefs();
  SearchResult<AssetInfo> findAllInfos();
  // anchor:data-only-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  <T extends ICommand> CommandResult perform(T command);
  // @anchor:methods:end

  // anchor:custom-methods:start
    CrudsResult<AssetChunk> getAssetChunk(AssetChunkRef reference);
  // anchor:custom-methods:end
}
