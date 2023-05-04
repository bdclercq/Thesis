package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface PortalFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<PortalData> findAllPortals(ParameterContext<SearchDetails<PortalFindAllPortalsDetails>> searchParameter);
  SearchResult<PortalData> findByNameEq(ParameterContext<SearchDetails<PortalFindByNameEqDetails>> searchParameter);
  SearchResult<PortalData> findByNameEq_VersionEq(ParameterContext<SearchDetails<PortalFindByNameEq_VersionEqDetails>> searchParameter);
  SearchResult<PortalData> findByVersionEq(ParameterContext<SearchDetails<PortalFindByVersionEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends PortalFindDetails> SearchResult<PortalData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
