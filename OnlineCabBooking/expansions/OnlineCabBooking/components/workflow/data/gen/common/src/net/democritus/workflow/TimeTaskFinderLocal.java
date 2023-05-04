package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface TimeTaskFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<TimeTaskData> findAllTimeTasks(ParameterContext<SearchDetails<TimeTaskFindAllTimeTasksDetails>> searchParameter);
  SearchResult<TimeTaskData> findByNameEq(ParameterContext<SearchDetails<TimeTaskFindByNameEqDetails>> searchParameter);
  SearchResult<TimeTaskData> findByIntervalPeriodEq(ParameterContext<SearchDetails<TimeTaskFindByIntervalPeriodEqDetails>> searchParameter);
  SearchResult<TimeTaskData> findByTriggerStateEq(ParameterContext<SearchDetails<TimeTaskFindByTriggerStateEqDetails>> searchParameter);
  SearchResult<TimeTaskData> findByWorkflowEq(ParameterContext<SearchDetails<TimeTaskFindByWorkflowEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends TimeTaskFindDetails> SearchResult<TimeTaskData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
