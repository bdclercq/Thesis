package net.democritus.acl;

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
import net.democritus.usr.ProfileData;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.ProfileCrudsInternal;

import net.democritus.usr.UserData;
import net.democritus.usr.UserDetails;
import net.democritus.usr.UserCrudsInternal;

import net.democritus.usr.UserGroupData;
import net.democritus.usr.UserGroupDetails;
import net.democritus.usr.UserGroupCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface DataAccessCrudsInternal extends DataAccessCrudsLocal {

  /* get the underlying data object */
  CrudsResult<DataAccessData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<DataAccessData> dataAccessDataParameter);

  /** find the data objects */
  <S extends DataAccessFindDetails> SearchResult<DataAccessData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<DataAccessDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<DataAccessDetails>> getDetailsListFromData(ParameterContext<Collection<DataAccessData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<DataAccessData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<DataAccessData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getForProfile(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getForUser(ParameterContext<Long> idParameter);
  CrudsResult<List<DataRef>> getUserGroups(ParameterContext<DataAccessData> dataAccessDataParameter);
  CrudsResult<DataRef> getForUserGroup(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<ProfileDetails> getForProfileDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<UserDetails> getForUserDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<UserGroupDetails>> getUserGroupsDetails(ParameterContext<DataAccessData> dataParameter);
  CrudsResult<UserGroupDetails> getForUserGroupDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setForProfile(DataAccessData dataAccessData, ParameterContext<DataRef> dataRefParameter);
  void setForUser(DataAccessData dataAccessData, ParameterContext<DataRef> dataRefParameter);
  void setUserGroups(DataAccessData dataAccessData, ParameterContext<List<DataRef>> dataRefListParameter);
  void setForUserGroup(DataAccessData dataAccessData, ParameterContext<DataRef> dataRefParameter);
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
