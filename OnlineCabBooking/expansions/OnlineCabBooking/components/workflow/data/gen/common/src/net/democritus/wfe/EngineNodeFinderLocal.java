package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface EngineNodeFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<EngineNodeData> findAllEngineNodes(ParameterContext<SearchDetails<EngineNodeFindAllEngineNodesDetails>> searchParameter);
  SearchResult<EngineNodeData> findByNameEq(ParameterContext<SearchDetails<EngineNodeFindByNameEqDetails>> searchParameter);
  SearchResult<EngineNodeData> findByMasterEq(ParameterContext<SearchDetails<EngineNodeFindByMasterEqDetails>> searchParameter);
  SearchResult<EngineNodeData> findByMasterEq_LastActiveLt(ParameterContext<SearchDetails<EngineNodeFindByMasterEq_LastActiveLtDetails>> searchParameter);
  SearchResult<EngineNodeData> findByLastActiveLt(ParameterContext<SearchDetails<EngineNodeFindByLastActiveLtDetails>> searchParameter);
  SearchResult<EngineNodeData> findByStatusEq(ParameterContext<SearchDetails<EngineNodeFindByStatusEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends EngineNodeFindDetails> SearchResult<EngineNodeData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
