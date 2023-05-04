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
import cabBookingCore.CustomerData;
import cabBookingCore.CustomerDetails;
import cabBookingCore.CustomerCrudsInternal;

import cabBookingCore.DriverData;
import cabBookingCore.DriverDetails;
import cabBookingCore.DriverCrudsInternal;

import cabBookingCore.AddressData;
import cabBookingCore.AddressDetails;
import cabBookingCore.AddressCrudsInternal;

import cabBookingCore.PaymentData;
import cabBookingCore.PaymentDetails;
import cabBookingCore.PaymentCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TripBookingCrudsInternal extends TripBookingCrudsLocal {

  /* get the underlying data object */
  CrudsResult<TripBookingData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<TripBookingData> tripBookingDataParameter);

  /** find the data objects */
  <S extends TripBookingFindDetails> SearchResult<TripBookingData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<TripBookingDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<TripBookingDetails>> getDetailsListFromData(ParameterContext<Collection<TripBookingData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<TripBookingData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<TripBookingData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getCustomer(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getDriver(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getFromLocation(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getToLocation(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getPayment(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<CustomerDetails> getCustomerDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<DriverDetails> getDriverDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<AddressDetails> getFromLocationDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<AddressDetails> getToLocationDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<PaymentDetails> getPaymentDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setCustomer(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter);
  void setDriver(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter);
  void setFromLocation(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter);
  void setToLocation(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter);
  void setPayment(TripBookingData tripBookingData, ParameterContext<DataRef> dataRefParameter);
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
