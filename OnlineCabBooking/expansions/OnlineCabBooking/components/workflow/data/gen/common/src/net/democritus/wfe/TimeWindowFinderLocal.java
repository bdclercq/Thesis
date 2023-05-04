package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TimeWindowFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<TimeWindowData> findAllTimeWindows(ParameterContext<SearchDetails<TimeWindowFindAllTimeWindowsDetails>> searchParameter);
  SearchResult<TimeWindowData> findByNameEq(ParameterContext<SearchDetails<TimeWindowFindByNameEqDetails>> searchParameter);
  SearchResult<TimeWindowData> findByStartTimeEq(ParameterContext<SearchDetails<TimeWindowFindByStartTimeEqDetails>> searchParameter);
  SearchResult<TimeWindowData> findByStopTimeEq(ParameterContext<SearchDetails<TimeWindowFindByStopTimeEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends TimeWindowFindDetails> SearchResult<TimeWindowData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
