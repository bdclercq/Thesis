package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ScreenFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<ScreenData> findAllScreens(ParameterContext<SearchDetails<ScreenFindAllScreensDetails>> searchParameter);
  SearchResult<ScreenData> findByNameEq(ParameterContext<SearchDetails<ScreenFindByNameEqDetails>> searchParameter);
  SearchResult<ScreenData> findByComponentEq(ParameterContext<SearchDetails<ScreenFindByComponentEqDetails>> searchParameter);
  SearchResult<ScreenData> findByLinkEq(ParameterContext<SearchDetails<ScreenFindByLinkEqDetails>> searchParameter);
  SearchResult<ScreenData> findByNameEq_ComponentEq(ParameterContext<SearchDetails<ScreenFindByNameEq_ComponentEqDetails>> searchParameter);
  SearchResult<ScreenData> findBySortOrderGt(ParameterContext<SearchDetails<ScreenFindBySortOrderGtDetails>> searchParameter);
  SearchResult<ScreenData> findBySortOrderLt(ParameterContext<SearchDetails<ScreenFindBySortOrderLtDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends ScreenFindDetails> SearchResult<ScreenData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
