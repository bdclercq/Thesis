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
import net.democritus.workflow.WorkflowData;
import net.democritus.workflow.WorkflowDetails;
import net.democritus.workflow.WorkflowCrudsInternal;

import net.democritus.wfe.TimeWindowGroupData;
import net.democritus.wfe.TimeWindowGroupDetails;
import net.democritus.wfe.TimeWindowGroupCrudsInternal;

import net.democritus.wfe.EngineNodeServiceData;
import net.democritus.wfe.EngineNodeServiceDetails;
import net.democritus.wfe.EngineNodeServiceCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import net.democritus.valuetype.basic.DateTime;
import net.democritus.valuetype.basic.DateTimeConverter;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface EngineServiceCrudsInternal extends EngineServiceCrudsLocal {

  /* get the underlying data object */
  CrudsResult<EngineServiceData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<EngineServiceData> engineServiceDataParameter);

  /** find the data objects */
  <S extends EngineServiceFindDetails> SearchResult<EngineServiceData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<EngineServiceDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<EngineServiceDetails>> getDetailsListFromData(ParameterContext<Collection<EngineServiceData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<EngineServiceData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<EngineServiceData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getWorkflow(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getTimeWindowGroup(ParameterContext<Long> idParameter);
  CrudsResult<List<DataRef>> getEngineNodeServices(ParameterContext<EngineServiceData> engineServiceDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<WorkflowDetails> getWorkflowDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<TimeWindowGroupDetails> getTimeWindowGroupDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<EngineNodeServiceDetails>> getEngineNodeServicesDetails(ParameterContext<EngineServiceData> dataParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setWorkflow(EngineServiceData engineServiceData, ParameterContext<DataRef> dataRefParameter);
  void setTimeWindowGroup(EngineServiceData engineServiceData, ParameterContext<DataRef> dataRefParameter);

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
