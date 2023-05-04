package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TripBookingFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<TripBookingData> findAllTripBookings(ParameterContext<SearchDetails<TripBookingFindAllTripBookingsDetails>> searchParameter);
  SearchResult<TripBookingData> findByNameEq(ParameterContext<SearchDetails<TripBookingFindByNameEqDetails>> searchParameter);
  SearchResult<TripBookingData> findByCustomerEq(ParameterContext<SearchDetails<TripBookingFindByCustomerEqDetails>> searchParameter);
  SearchResult<TripBookingData> findByDriverEq(ParameterContext<SearchDetails<TripBookingFindByDriverEqDetails>> searchParameter);
  SearchResult<TripBookingData> findAllTripBooking(ParameterContext<SearchDetails<TripBookingFindAllTripBookingDetails>> searchParameter);
  SearchResult<TripBookingData> findByFromDateTimeEq(ParameterContext<SearchDetails<TripBookingFindByFromDateTimeEqDetails>> searchParameter);
  SearchResult<TripBookingData> findByToDateTimeEq(ParameterContext<SearchDetails<TripBookingFindByToDateTimeEqDetails>> searchParameter);
  SearchResult<TripBookingData> findByCustomerEq_FromDateTimeEq(ParameterContext<SearchDetails<TripBookingFindByCustomerEq_FromDateTimeEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends TripBookingFindDetails> SearchResult<TripBookingData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
