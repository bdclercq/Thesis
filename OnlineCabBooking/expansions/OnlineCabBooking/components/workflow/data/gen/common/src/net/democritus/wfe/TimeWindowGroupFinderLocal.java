package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TimeWindowGroupFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<TimeWindowGroupData> findAllTimeWindowGroups(ParameterContext<SearchDetails<TimeWindowGroupFindAllTimeWindowGroupsDetails>> searchParameter);
  SearchResult<TimeWindowGroupData> findByNameEq(ParameterContext<SearchDetails<TimeWindowGroupFindByNameEqDetails>> searchParameter);
  SearchResult<TimeWindowGroupData> findByVisibleEq(ParameterContext<SearchDetails<TimeWindowGroupFindByVisibleEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends TimeWindowGroupFindDetails> SearchResult<TimeWindowGroupData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
