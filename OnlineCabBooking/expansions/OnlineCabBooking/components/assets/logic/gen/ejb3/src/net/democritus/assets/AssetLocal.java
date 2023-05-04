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
 * Local interface for the entity bean Asset,
 * representing a known Asset
 */
public interface AssetLocal {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  DataRef getDataRef(Long id);

  AssetInfo getInfo(Long id);
  AssetDetails getDetails(DataRef dataRef);
  AssetDetails getDetails(Long id);

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<AssetDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<AssetDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<AssetInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<AssetDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<AssetDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends AssetFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter);
  <T extends ICommand> CommandResult dispatchCommand(ParameterContext<T> commandParameter);

  // anchor:command-methods:start
  public CommandResult registerExternalAsset(ParameterContext<AssetCommand.RegisterExternalAsset> commandParameter);
  // anchor:command-methods:end
  // @anchor:methods:end

  // anchor:custom-methods:start
  CrudsResult<AssetChunk> getAssetChunk(ParameterContext<AssetChunkRef> refParam);
  CrudsResult<Void> uploadChunk(ParameterContext<AssetChunk> parameter);
  // anchor:custom-methods:end

}

