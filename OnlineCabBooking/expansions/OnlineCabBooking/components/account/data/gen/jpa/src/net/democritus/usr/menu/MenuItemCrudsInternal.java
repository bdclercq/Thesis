package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Collection;
import java.util.List;

import net.democritus.sys.DataRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.search.SearchDetails;

import java.util.Date;
import java.util.List;

// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.menu.MenuData;
import net.democritus.usr.menu.MenuDetails;
import net.democritus.usr.menu.MenuCrudsInternal;

import net.democritus.usr.menu.ScreenData;
import net.democritus.usr.menu.ScreenDetails;
import net.democritus.usr.menu.ScreenCrudsInternal;

import net.democritus.usr.menu.MenuItemData;
import net.democritus.usr.menu.MenuItemDetails;
import net.democritus.usr.menu.MenuItemCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface MenuItemCrudsInternal extends MenuItemCrudsLocal {

  /* get the underlying data object */
  CrudsResult<MenuItemData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<MenuItemData> menuItemDataParameter);

  /** find the data objects */
  <S extends MenuItemFindDetails> SearchResult<MenuItemData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<MenuItemDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<MenuItemDetails>> getDetailsListFromData(ParameterContext<Collection<MenuItemData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<MenuItemData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<MenuItemData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getMenu(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getScreen(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getMenuItem(ParameterContext<Long> idParameter);
  CrudsResult<List<DataRef>> getSubmenuItems(ParameterContext<MenuItemData> menuItemDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<MenuDetails> getMenuDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<ScreenDetails> getScreenDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<MenuItemDetails> getMenuItemDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<MenuItemDetails>> getSubmenuItemsDetails(ParameterContext<MenuItemData> dataParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setMenu(MenuItemData menuItemData, ParameterContext<DataRef> dataRefParameter);
  void setScreen(MenuItemData menuItemData, ParameterContext<DataRef> dataRefParameter);
  void setMenuItem(MenuItemData menuItemData, ParameterContext<DataRef> dataRefParameter);

  // anchor:projection-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-wrapper-methods:start
  // anchor:calculation-wrapper-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
