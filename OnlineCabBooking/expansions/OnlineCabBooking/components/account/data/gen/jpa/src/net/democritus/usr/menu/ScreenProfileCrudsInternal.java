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
import net.democritus.usr.menu.ScreenData;
import net.democritus.usr.menu.ScreenDetails;
import net.democritus.usr.menu.ScreenCrudsInternal;

import net.democritus.usr.ProfileData;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.ProfileCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ScreenProfileCrudsInternal extends ScreenProfileCrudsLocal {

  /* get the underlying data object */
  CrudsResult<ScreenProfileData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<ScreenProfileData> screenProfileDataParameter);

  /** find the data objects */
  <S extends ScreenProfileFindDetails> SearchResult<ScreenProfileData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<ScreenProfileDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<ScreenProfileDetails>> getDetailsListFromData(ParameterContext<Collection<ScreenProfileData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<ScreenProfileData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<ScreenProfileData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getScreen(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getProfile(ParameterContext<Long> idParameter);
  CrudsResult<List<DataRef>> getScreens(ParameterContext<ScreenProfileData> screenProfileDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<ScreenDetails> getScreenDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<ProfileDetails> getProfileDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<ScreenDetails>> getScreensDetails(ParameterContext<ScreenProfileData> dataParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setScreen(ScreenProfileData screenProfileData, ParameterContext<DataRef> dataRefParameter);
  void setProfile(ScreenProfileData screenProfileData, ParameterContext<DataRef> dataRefParameter);
  void setScreens(ScreenProfileData screenProfileData, ParameterContext<List<DataRef>> dataRefListParameter);
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
