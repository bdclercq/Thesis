package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface DataAccessFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<DataAccessData> findAllDataAccesss(ParameterContext<SearchDetails<DataAccessFindAllDataAccesssDetails>> searchParameter);
  SearchResult<DataAccessData> findByNameEq(ParameterContext<SearchDetails<DataAccessFindByNameEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByElementEq(ParameterContext<SearchDetails<DataAccessFindByElementEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByElementEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByElementEq_FunctionalityEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByElementEq_TargetEq(ParameterContext<SearchDetails<DataAccessFindByElementEq_TargetEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForProfileEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForProfileEq_ElementEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForProfileEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEq_FunctionalityEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserEq(ParameterContext<SearchDetails<DataAccessFindByForUserEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserEq_ElementEq(ParameterContext<SearchDetails<DataAccessFindByForUserEq_ElementEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForUserEq_FunctionalityEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByTargetEq(ParameterContext<SearchDetails<DataAccessFindByTargetEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserGroupEq_ElementEq(ParameterContext<SearchDetails<DataAccessFindByForUserGroupEq_ElementEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserEq_ElementEq_TargetEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForUserEq_ElementEq_TargetEq_FunctionalityEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForProfileEq_ElementEq_TargetEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForProfileEq_ElementEq_TargetEq_FunctionalityEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq(ParameterContext<SearchDetails<DataAccessFindByForUserGroupEq_ElementEq_TargetEq_FunctionalityEqDetails>> searchParameter);
  SearchResult<DataAccessData> findByForUserGroupEq(ParameterContext<SearchDetails<DataAccessFindByForUserGroupEqDetails>> searchParameter);
  SearchResult<DataAccessData> findBySpecificationOrWildcard(ParameterContext<SearchDetails<DataAccessFindBySpecificationOrWildcardDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends DataAccessFindDetails> SearchResult<DataAccessData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
