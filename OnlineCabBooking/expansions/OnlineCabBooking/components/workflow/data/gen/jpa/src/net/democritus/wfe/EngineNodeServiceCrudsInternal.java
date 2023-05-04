package net.democritus.wfe;

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
import net.democritus.wfe.EngineNodeData;
import net.democritus.wfe.EngineNodeDetails;
import net.democritus.wfe.EngineNodeCrudsInternal;

import net.democritus.wfe.EngineServiceData;
import net.democritus.wfe.EngineServiceDetails;
import net.democritus.wfe.EngineServiceCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface EngineNodeServiceCrudsInternal extends EngineNodeServiceCrudsLocal {

  /* get the underlying data object */
  CrudsResult<EngineNodeServiceData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<EngineNodeServiceData> engineNodeServiceDataParameter);

  /** find the data objects */
  <S extends EngineNodeServiceFindDetails> SearchResult<EngineNodeServiceData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<EngineNodeServiceDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<EngineNodeServiceDetails>> getDetailsListFromData(ParameterContext<Collection<EngineNodeServiceData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<EngineNodeServiceData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<EngineNodeServiceData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getEngineNode(ParameterContext<EngineNodeData> engineNodeDataParameter);
  CrudsResult<DataRef> getEngineService(ParameterContext<EngineServiceData> engineServiceDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setEngineNode(EngineNodeServiceData aEngineNodeServiceData, ParameterContext<DataRef> dataRefParameter);
  void setEngineService(EngineNodeServiceData aEngineNodeServiceData, ParameterContext<DataRef> dataRefParameter);
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
