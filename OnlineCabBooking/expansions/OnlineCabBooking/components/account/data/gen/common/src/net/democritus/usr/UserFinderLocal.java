package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface UserFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<UserData> findAllUsers(ParameterContext<SearchDetails<UserFindAllUsersDetails>> searchParameter);
  SearchResult<UserData> findByNameEq(ParameterContext<SearchDetails<UserFindByNameEqDetails>> searchParameter);
  SearchResult<UserData> findByAccountEq(ParameterContext<SearchDetails<UserFindByAccountEqDetails>> searchParameter);
  SearchResult<UserData> findByAccountEq_ProfileEq(ParameterContext<SearchDetails<UserFindByAccountEq_ProfileEqDetails>> searchParameter);
  SearchResult<UserData> findByFullNameEq(ParameterContext<SearchDetails<UserFindByFullNameEqDetails>> searchParameter);
  SearchResult<UserData> findByPersNrEq(ParameterContext<SearchDetails<UserFindByPersNrEqDetails>> searchParameter);
  SearchResult<UserData> findByProfileEq(ParameterContext<SearchDetails<UserFindByProfileEqDetails>> searchParameter);
  SearchResult<UserData> findByEmailEq(ParameterContext<SearchDetails<UserFindByEmailEqDetails>> searchParameter);
  SearchResult<UserData> findByNameEq_ProfileEq(ParameterContext<SearchDetails<UserFindByNameEq_ProfileEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends UserFindDetails> SearchResult<UserData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
