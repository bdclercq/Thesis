package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface EngineNodeServiceFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<EngineNodeServiceData> findAllEngineNodeServices(ParameterContext<SearchDetails<EngineNodeServiceFindAllEngineNodeServicesDetails>> searchParameter);
  SearchResult<EngineNodeServiceData> findByNameEq(ParameterContext<SearchDetails<EngineNodeServiceFindByNameEqDetails>> searchParameter);
  SearchResult<EngineNodeServiceData> findByStatusEq(ParameterContext<SearchDetails<EngineNodeServiceFindByStatusEqDetails>> searchParameter);
  SearchResult<EngineNodeServiceData> findByEngineNodeEq(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineNodeEqDetails>> searchParameter);
  SearchResult<EngineNodeServiceData> findByEngineServiceEq_StatusNe(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineServiceEq_StatusNeDetails>> searchParameter);
  SearchResult<EngineNodeServiceData> findByEngineServiceEq(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineServiceEqDetails>> searchParameter);
  SearchResult<EngineNodeServiceData> findByEngineNodeEq_EngineServiceEq(ParameterContext<SearchDetails<EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends EngineNodeServiceFindDetails> SearchResult<EngineNodeServiceData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
