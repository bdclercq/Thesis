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
import net.democritus.usr.UserData;
import net.democritus.usr.UserDetails;
import net.democritus.usr.UserCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface WorkflowCrudsInternal extends WorkflowCrudsLocal {

  /* get the underlying data object */
  CrudsResult<WorkflowData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<WorkflowData> workflowDataParameter);

  /** find the data objects */
  <S extends WorkflowFindDetails> SearchResult<WorkflowData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<WorkflowDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<WorkflowDetails>> getDetailsListFromData(ParameterContext<Collection<WorkflowData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<WorkflowData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<WorkflowData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getResponsible(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<UserDetails> getResponsibleDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setResponsible(WorkflowData workflowData, ParameterContext<DataRef> dataRefParameter);
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
