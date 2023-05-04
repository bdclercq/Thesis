package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface StateTaskFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<StateTaskData> findAllStateTasks(ParameterContext<SearchDetails<StateTaskFindAllStateTasksDetails>> searchParameter);
  SearchResult<StateTaskData> findByNameEq(ParameterContext<SearchDetails<StateTaskFindByNameEqDetails>> searchParameter);
  SearchResult<StateTaskData> findByBeginStateEq(ParameterContext<SearchDetails<StateTaskFindByBeginStateEqDetails>> searchParameter);
  SearchResult<StateTaskData> findByEndStateEq(ParameterContext<SearchDetails<StateTaskFindByEndStateEqDetails>> searchParameter);
  SearchResult<StateTaskData> findByFailedStateEq(ParameterContext<SearchDetails<StateTaskFindByFailedStateEqDetails>> searchParameter);
  SearchResult<StateTaskData> findByWorkflowEq(ParameterContext<SearchDetails<StateTaskFindByWorkflowEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends StateTaskFindDetails> SearchResult<StateTaskData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
