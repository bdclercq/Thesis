package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface DriverFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<DriverData> findAllDrivers(ParameterContext<SearchDetails<DriverFindAllDriversDetails>> searchParameter);
  SearchResult<DriverData> findByNameEq(ParameterContext<SearchDetails<DriverFindByNameEqDetails>> searchParameter);
  SearchResult<DriverData> findByLicenseNoEq(ParameterContext<SearchDetails<DriverFindByLicenseNoEqDetails>> searchParameter);
  SearchResult<DriverData> findByRatingEq(ParameterContext<SearchDetails<DriverFindByRatingEqDetails>> searchParameter);
  SearchResult<DriverData> findByIsAvailableEq(ParameterContext<SearchDetails<DriverFindByIsAvailableEqDetails>> searchParameter);
  SearchResult<DriverData> findByTripBookingEq(ParameterContext<SearchDetails<DriverFindByTripBookingEqDetails>> searchParameter);
  SearchResult<DriverData> findByCabEq(ParameterContext<SearchDetails<DriverFindByCabEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends DriverFindDetails> SearchResult<DriverData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
