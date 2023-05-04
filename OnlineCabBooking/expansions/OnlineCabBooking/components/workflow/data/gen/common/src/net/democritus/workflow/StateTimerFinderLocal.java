package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface StateTimerFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<StateTimerData> findAllStateTimers(ParameterContext<SearchDetails<StateTimerFindAllStateTimersDetails>> searchParameter);
  SearchResult<StateTimerData> findByNameEq(ParameterContext<SearchDetails<StateTimerFindByNameEqDetails>> searchParameter);
  SearchResult<StateTimerData> findByAlteredStateEq(ParameterContext<SearchDetails<StateTimerFindByAlteredStateEqDetails>> searchParameter);
  SearchResult<StateTimerData> findByBeginStateEq(ParameterContext<SearchDetails<StateTimerFindByBeginStateEqDetails>> searchParameter);
  SearchResult<StateTimerData> findByTargetStateEq(ParameterContext<SearchDetails<StateTimerFindByTargetStateEqDetails>> searchParameter);
  SearchResult<StateTimerData> findByWorkflowEq(ParameterContext<SearchDetails<StateTimerFindByWorkflowEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends StateTimerFindDetails> SearchResult<StateTimerData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
