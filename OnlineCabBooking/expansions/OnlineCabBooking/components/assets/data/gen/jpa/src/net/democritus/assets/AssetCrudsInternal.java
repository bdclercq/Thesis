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
import net.democritus.assets.FileAssetData;
import net.democritus.assets.FileAssetDetails;
import net.democritus.assets.FileAssetCrudsInternal;

import net.democritus.assets.InternalAssetData;
import net.democritus.assets.InternalAssetDetails;
import net.democritus.assets.InternalAssetCrudsInternal;

import net.democritus.assets.ExternalAssetData;
import net.democritus.assets.ExternalAssetDetails;
import net.democritus.assets.ExternalAssetCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AssetCrudsInternal extends AssetCrudsLocal {

  /* get the underlying data object */
  CrudsResult<AssetData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<AssetData> assetDataParameter);

  /** find the data objects */
  <S extends AssetFindDetails> SearchResult<AssetData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<AssetDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<AssetDetails>> getDetailsListFromData(ParameterContext<Collection<AssetData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<AssetData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<AssetData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getFileAsset(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getInternalAsset(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getExternalAsset(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<FileAssetDetails> getFileAssetDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<InternalAssetDetails> getInternalAssetDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<ExternalAssetDetails> getExternalAssetDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setFileAsset(AssetData assetData, ParameterContext<DataRef> dataRefParameter);
  void setInternalAsset(AssetData assetData, ParameterContext<DataRef> dataRefParameter);
  void setExternalAsset(AssetData assetData, ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-setters:end

  // anchor:calculation-methods:start
  CrudsResult<String> performCalculateDownloadLink(ParameterContext<AssetData> dataParameter);
  CrudsResult<String> performCalculateFileSize(ParameterContext<AssetData> dataParameter);
  // anchor:calculation-methods:end

  // anchor:calculation-wrapper-methods:start
  CrudsResult<String> calculateDownloadLink(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<String> calculateFileSize(ParameterContext<DataRef> dataRefParameter);
  // anchor:calculation-wrapper-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
