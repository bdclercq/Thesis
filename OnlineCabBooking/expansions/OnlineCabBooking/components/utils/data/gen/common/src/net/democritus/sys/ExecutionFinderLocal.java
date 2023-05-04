package net.democritus.sys;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ExecutionFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<ExecutionData> findAllExecutions(ParameterContext<SearchDetails<ExecutionFindAllExecutionsDetails>> searchParameter);
  SearchResult<ExecutionData> findByNameEq(ParameterContext<SearchDetails<ExecutionFindByNameEqDetails>> searchParameter);
  SearchResult<ExecutionData> findByElementEq(ParameterContext<SearchDetails<ExecutionFindByElementEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends ExecutionFindDetails> SearchResult<ExecutionData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
