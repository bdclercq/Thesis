package net.democritus.workflow;

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
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TimeTaskCrudsInternal extends TimeTaskCrudsLocal {

  /* get the underlying data object */
  CrudsResult<TimeTaskData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<TimeTaskData> timeTaskDataParameter);

  /** find the data objects */
  <S extends TimeTaskFindDetails> SearchResult<TimeTaskData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<TimeTaskDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<TimeTaskDetails>> getDetailsListFromData(ParameterContext<Collection<TimeTaskData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<TimeTaskData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<TimeTaskData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getWorkflow(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<WorkflowDetails> getWorkflowDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setWorkflow(TimeTaskData timeTaskData, ParameterContext<DataRef> dataRefParameter);
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
