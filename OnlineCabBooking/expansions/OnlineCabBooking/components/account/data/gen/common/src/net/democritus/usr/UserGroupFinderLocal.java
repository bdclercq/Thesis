package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface UserGroupFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<UserGroupData> findAllUserGroups(ParameterContext<SearchDetails<UserGroupFindAllUserGroupsDetails>> searchParameter);
  SearchResult<UserGroupData> findByNameEq(ParameterContext<SearchDetails<UserGroupFindByNameEqDetails>> searchParameter);
  SearchResult<UserGroupData> findByTypeEq(ParameterContext<SearchDetails<UserGroupFindByTypeEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends UserGroupFindDetails> SearchResult<UserGroupData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
