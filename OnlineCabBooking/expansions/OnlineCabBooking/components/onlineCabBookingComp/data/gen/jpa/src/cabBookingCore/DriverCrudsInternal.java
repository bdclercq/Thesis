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
import cabBookingCore.CabData;
import cabBookingCore.CabDetails;
import cabBookingCore.CabCrudsInternal;

import cabBookingCore.TripBookingData;
import cabBookingCore.TripBookingDetails;
import cabBookingCore.TripBookingCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface DriverCrudsInternal extends DriverCrudsLocal {

  /* get the underlying data object */
  CrudsResult<DriverData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<DriverData> driverDataParameter);

  /** find the data objects */
  <S extends DriverFindDetails> SearchResult<DriverData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<DriverDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<DriverDetails>> getDetailsListFromData(ParameterContext<Collection<DriverData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<DriverData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<DriverData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getCab(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getTripBooking(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<CabDetails> getCabDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<TripBookingDetails> getTripBookingDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setCab(DriverData driverData, ParameterContext<DataRef> dataRefParameter);
  void setTripBooking(DriverData driverData, ParameterContext<DataRef> dataRefParameter);
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
