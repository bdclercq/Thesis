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
import net.democritus.wfe.TimeWindowData;
import net.democritus.wfe.TimeWindowDetails;
import net.democritus.wfe.TimeWindowCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TimeWindowGroupCrudsInternal extends TimeWindowGroupCrudsLocal {

  /* get the underlying data object */
  CrudsResult<TimeWindowGroupData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<TimeWindowGroupData> timeWindowGroupDataParameter);

  /** find the data objects */
  <S extends TimeWindowGroupFindDetails> SearchResult<TimeWindowGroupData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<TimeWindowGroupDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<TimeWindowGroupDetails>> getDetailsListFromData(ParameterContext<Collection<TimeWindowGroupData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<TimeWindowGroupData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<TimeWindowGroupData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<List<DataRef>> getTimeWindows(ParameterContext<TimeWindowGroupData> timeWindowGroupDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<List<TimeWindowDetails>> getTimeWindowsDetails(ParameterContext<TimeWindowGroupData> dataParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setTimeWindows(TimeWindowGroupData timeWindowGroupData, ParameterContext<List<DataRef>> dataRefListParameter);
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
