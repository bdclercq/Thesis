package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TripBookingTaskStatusFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<TripBookingTaskStatusData> findAllTripBookingTaskStatuss(ParameterContext<SearchDetails<TripBookingTaskStatusFindAllTripBookingTaskStatussDetails>> searchParameter);
  SearchResult<TripBookingTaskStatusData> findByNameEq(ParameterContext<SearchDetails<TripBookingTaskStatusFindByNameEqDetails>> searchParameter);
  SearchResult<TripBookingTaskStatusData> findByStatusEq(ParameterContext<SearchDetails<TripBookingTaskStatusFindByStatusEqDetails>> searchParameter);
  SearchResult<TripBookingTaskStatusData> findByTripBookingEq(ParameterContext<SearchDetails<TripBookingTaskStatusFindByTripBookingEqDetails>> searchParameter);
  SearchResult<TripBookingTaskStatusData> findByStateTaskEq(ParameterContext<SearchDetails<TripBookingTaskStatusFindByStateTaskEqDetails>> searchParameter);
  SearchResult<TripBookingTaskStatusData> findAllTripBookingTaskStatusses(ParameterContext<SearchDetails<TripBookingTaskStatusFindAllTripBookingTaskStatussesDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends TripBookingTaskStatusFindDetails> SearchResult<TripBookingTaskStatusData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
