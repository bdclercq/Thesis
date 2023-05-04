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
import net.democritus.usr.AccountData;
import net.democritus.usr.AccountDetails;
import net.democritus.usr.AccountCrudsInternal;

import net.democritus.usr.ProfileData;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.ProfileCrudsInternal;

import net.democritus.usr.UserGroupData;
import net.democritus.usr.UserGroupDetails;
import net.democritus.usr.UserGroupCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface UserCrudsInternal extends UserCrudsLocal {

  /* get the underlying data object */
  CrudsResult<UserData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<UserData> userDataParameter);

  /** find the data objects */
  <S extends UserFindDetails> SearchResult<UserData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<UserDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<UserDetails>> getDetailsListFromData(ParameterContext<Collection<UserData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<UserData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<UserData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getAccount(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getProfile(ParameterContext<Long> idParameter);
  CrudsResult<List<DataRef>> getUserGroups(ParameterContext<UserData> userDataParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<AccountDetails> getAccountDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<ProfileDetails> getProfileDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<UserGroupDetails>> getUserGroupsDetails(ParameterContext<UserData> dataParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setAccount(UserData userData, ParameterContext<DataRef> dataRefParameter);
  void setProfile(UserData userData, ParameterContext<DataRef> dataRefParameter);
  void setUserGroups(UserData userData, ParameterContext<List<DataRef>> dataRefListParameter);
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
