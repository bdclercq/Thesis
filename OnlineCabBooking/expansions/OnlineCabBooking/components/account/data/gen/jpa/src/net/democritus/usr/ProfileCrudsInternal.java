package net.democritus.usr;

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

import net.democritus.usr.UserGroupData;
import net.democritus.usr.UserGroupDetails;
import net.democritus.usr.UserGroupCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ProfileCrudsInternal extends ProfileCrudsLocal {

  /* get the underlying data object */
  CrudsResult<ProfileData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<ProfileData> profileDataParameter);

  /** find the data objects */
  <S extends ProfileFindDetails> SearchResult<ProfileData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<ProfileDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<ProfileDetails>> getDetailsListFromData(ParameterContext<Collection<ProfileData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<ProfileData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<ProfileData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<List<DataRef>> getScreens(ParameterContext<ProfileData> profileDataParameter);
  CrudsResult<DataRef> getUserGroup(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<List<ScreenDetails>> getScreensDetails(ParameterContext<ProfileData> dataParameter);
  CrudsResult<UserGroupDetails> getUserGroupDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setScreens(ProfileData profileData, ParameterContext<List<DataRef>> dataRefListParameter);
  void setUserGroup(ProfileData profileData, ParameterContext<DataRef> dataRefParameter);
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
