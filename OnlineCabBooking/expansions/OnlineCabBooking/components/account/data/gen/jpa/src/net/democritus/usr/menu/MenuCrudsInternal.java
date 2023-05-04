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
import net.democritus.usr.PortalData;
import net.democritus.usr.PortalDetails;
import net.democritus.usr.PortalCrudsInternal;

import net.democritus.usr.ProfileData;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.ProfileCrudsInternal;

import net.democritus.usr.menu.MenuItemData;
import net.democritus.usr.menu.MenuItemDetails;
import net.democritus.usr.menu.MenuItemCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface MenuCrudsInternal extends MenuCrudsLocal {

  /* get the underlying data object */
  CrudsResult<MenuData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<MenuData> menuDataParameter);

  /** find the data objects */
  <S extends MenuFindDetails> SearchResult<MenuData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<MenuDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<MenuDetails>> getDetailsListFromData(ParameterContext<Collection<MenuData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<MenuData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<MenuData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getPortal(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getProfile(ParameterContext<Long> idParameter);
  CrudsResult<List<DataRef>> getMenuItems(ParameterContext<MenuData> menuDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<PortalDetails> getPortalDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<ProfileDetails> getProfileDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<MenuItemDetails>> getMenuItemsDetails(ParameterContext<MenuData> dataParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setPortal(MenuData menuData, ParameterContext<DataRef> dataRefParameter);
  void setProfile(MenuData menuData, ParameterContext<DataRef> dataRefParameter);

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
