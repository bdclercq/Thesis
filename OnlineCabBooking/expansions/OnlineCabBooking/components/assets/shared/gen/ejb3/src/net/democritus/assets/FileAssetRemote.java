package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.ParameterContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import java.util.Date;
import java.util.List;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Remote interface for the entity bean FileAsset,
 * representing a known FileAsset
 */
public interface FileAssetRemote {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<FileAssetDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<FileAssetDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<FileAssetInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<FileAssetDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<FileAssetDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends FileAssetFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:start
  CrudsResult<Void> uploadAssetChunk(ParameterContext<AssetChunk> parameter);
  CrudsResult<AssetChunk> getAssetChunk(ParameterContext<AssetChunkRef> refParam);
  // anchor:custom-methods:end

}

