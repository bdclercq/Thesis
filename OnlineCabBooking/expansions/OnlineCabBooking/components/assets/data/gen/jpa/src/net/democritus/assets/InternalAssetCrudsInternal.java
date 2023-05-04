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
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface InternalAssetCrudsInternal extends InternalAssetCrudsLocal {

  /* get the underlying data object */
  CrudsResult<InternalAssetData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<InternalAssetData> internalAssetDataParameter);

  /** find the data objects */
  <S extends InternalAssetFindDetails> SearchResult<InternalAssetData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<InternalAssetDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<InternalAssetDetails>> getDetailsListFromData(ParameterContext<Collection<InternalAssetData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<InternalAssetData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<InternalAssetData> dataParameter);

  // anchor:projection-getters:start
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
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
