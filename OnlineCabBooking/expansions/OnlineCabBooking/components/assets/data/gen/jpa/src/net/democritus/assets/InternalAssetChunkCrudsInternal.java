package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Collection;
import java.util.List;

import net.democritus.sys.DataRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.search.SearchDetails;

import java.util.Date;
import java.util.List;

// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import net.democritus.assets.InternalAssetData;
import net.democritus.assets.InternalAssetDetails;
import net.democritus.assets.InternalAssetCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface InternalAssetChunkCrudsInternal extends InternalAssetChunkCrudsLocal {

  /* get the underlying data object */
  CrudsResult<InternalAssetChunkData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<InternalAssetChunkData> internalAssetChunkDataParameter);

  /** find the data objects */
  <S extends InternalAssetChunkFindDetails> SearchResult<InternalAssetChunkData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<InternalAssetChunkDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<InternalAssetChunkDetails>> getDetailsListFromData(ParameterContext<Collection<InternalAssetChunkData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<InternalAssetChunkData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<InternalAssetChunkData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getInternalAsset(ParameterContext<InternalAssetData> internalAssetDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setInternalAsset(InternalAssetChunkData aInternalAssetChunkData, ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-wrapper-methods:start
  // anchor:calculation-wrapper-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
