package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface MenuItemFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<MenuItemData> findAllMenuItems(ParameterContext<SearchDetails<MenuItemFindAllMenuItemsDetails>> searchParameter);
  SearchResult<MenuItemData> findByNameEq(ParameterContext<SearchDetails<MenuItemFindByNameEqDetails>> searchParameter);
  SearchResult<MenuItemData> findByMenuEq(ParameterContext<SearchDetails<MenuItemFindByMenuEqDetails>> searchParameter);
  SearchResult<MenuItemData> findByMenuEq_ScreenEq(ParameterContext<SearchDetails<MenuItemFindByMenuEq_ScreenEqDetails>> searchParameter);
  SearchResult<MenuItemData> findByMenuItemEq(ParameterContext<SearchDetails<MenuItemFindByMenuItemEqDetails>> searchParameter);
  SearchResult<MenuItemData> findByScreenEq(ParameterContext<SearchDetails<MenuItemFindByScreenEqDetails>> searchParameter);
  SearchResult<MenuItemData> findBySortOrderGt(ParameterContext<SearchDetails<MenuItemFindBySortOrderGtDetails>> searchParameter);
  SearchResult<MenuItemData> findBySortOrderLt(ParameterContext<SearchDetails<MenuItemFindBySortOrderLtDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends MenuItemFindDetails> SearchResult<MenuItemData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
