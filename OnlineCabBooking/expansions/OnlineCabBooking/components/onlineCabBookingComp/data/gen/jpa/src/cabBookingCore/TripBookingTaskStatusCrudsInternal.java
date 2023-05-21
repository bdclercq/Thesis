package cabBookingCore;

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
import net.democritus.workflow.StateTaskData;
import net.democritus.workflow.StateTaskDetails;
import net.democritus.workflow.StateTaskCrudsInternal;

import cabBookingCore.TripBookingData;
import cabBookingCore.TripBookingDetails;
import cabBookingCore.TripBookingCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TripBookingTaskStatusCrudsInternal extends TripBookingTaskStatusCrudsLocal {

  /* get the underlying data object */
  CrudsResult<TripBookingTaskStatusData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<TripBookingTaskStatusData> tripBookingTaskStatusDataParameter);

  /** find the data objects */
  <S extends TripBookingTaskStatusFindDetails> SearchResult<TripBookingTaskStatusData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<TripBookingTaskStatusDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<TripBookingTaskStatusDetails>> getDetailsListFromData(ParameterContext<Collection<TripBookingTaskStatusData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<TripBookingTaskStatusData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<TripBookingTaskStatusData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getStateTask(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getTripBooking(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<StateTaskDetails> getStateTaskDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<TripBookingDetails> getTripBookingDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setStateTask(TripBookingTaskStatusData tripBookingTaskStatusData, ParameterContext<DataRef> dataRefParameter);
  void setTripBooking(TripBookingTaskStatusData tripBookingTaskStatusData, ParameterContext<DataRef> dataRefParameter);
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
