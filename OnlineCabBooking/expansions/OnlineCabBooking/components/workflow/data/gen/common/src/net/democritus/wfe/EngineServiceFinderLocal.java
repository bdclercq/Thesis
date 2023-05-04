package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface EngineServiceFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<EngineServiceData> findAllEngineServices(ParameterContext<SearchDetails<EngineServiceFindAllEngineServicesDetails>> searchParameter);
  SearchResult<EngineServiceData> findByNameEq(ParameterContext<SearchDetails<EngineServiceFindByNameEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByBusyEq(ParameterContext<SearchDetails<EngineServiceFindByBusyEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByChangedEq(ParameterContext<SearchDetails<EngineServiceFindByChangedEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByCollectorEq(ParameterContext<SearchDetails<EngineServiceFindByCollectorEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByLastRunAtGt(ParameterContext<SearchDetails<EngineServiceFindByLastRunAtGtDetails>> searchParameter);
  SearchResult<EngineServiceData> findByLastRunAtLt(ParameterContext<SearchDetails<EngineServiceFindByLastRunAtLtDetails>> searchParameter);
  SearchResult<EngineServiceData> findByNameEq_CollectorEq(ParameterContext<SearchDetails<EngineServiceFindByNameEq_CollectorEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByStatusEq(ParameterContext<SearchDetails<EngineServiceFindByStatusEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByWorkflowEq(ParameterContext<SearchDetails<EngineServiceFindByWorkflowEqDetails>> searchParameter);
  SearchResult<EngineServiceData> findByTimeWindowGroupEq(ParameterContext<SearchDetails<EngineServiceFindByTimeWindowGroupEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends EngineServiceFindDetails> SearchResult<EngineServiceData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
