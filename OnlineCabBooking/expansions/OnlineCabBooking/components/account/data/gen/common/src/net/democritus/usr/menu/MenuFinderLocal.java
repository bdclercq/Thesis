package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface MenuFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<MenuData> findAllMenus(ParameterContext<SearchDetails<MenuFindAllMenusDetails>> searchParameter);
  SearchResult<MenuData> findByNameEq(ParameterContext<SearchDetails<MenuFindByNameEqDetails>> searchParameter);
  SearchResult<MenuData> findByPortalEq(ParameterContext<SearchDetails<MenuFindByPortalEqDetails>> searchParameter);
  SearchResult<MenuData> findByPortalEq_ProfileEq(ParameterContext<SearchDetails<MenuFindByPortalEq_ProfileEqDetails>> searchParameter);
  SearchResult<MenuData> findByProfileEq(ParameterContext<SearchDetails<MenuFindByProfileEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends MenuFindDetails> SearchResult<MenuData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
