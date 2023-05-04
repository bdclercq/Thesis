package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AccountFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<AccountData> findAllAccounts(ParameterContext<SearchDetails<AccountFindAllAccountsDetails>> searchParameter);
  SearchResult<AccountData> findByNameEq(ParameterContext<SearchDetails<AccountFindByNameEqDetails>> searchParameter);
  SearchResult<AccountData> findByAddressEq(ParameterContext<SearchDetails<AccountFindByAddressEqDetails>> searchParameter);
  SearchResult<AccountData> findByCityEq(ParameterContext<SearchDetails<AccountFindByCityEqDetails>> searchParameter);
  SearchResult<AccountData> findByCountryEq(ParameterContext<SearchDetails<AccountFindByCountryEqDetails>> searchParameter);
  SearchResult<AccountData> findByEmailEq(ParameterContext<SearchDetails<AccountFindByEmailEqDetails>> searchParameter);
  SearchResult<AccountData> findByFullNameEq(ParameterContext<SearchDetails<AccountFindByFullNameEqDetails>> searchParameter);
  SearchResult<AccountData> findByRefIdEq(ParameterContext<SearchDetails<AccountFindByRefIdEqDetails>> searchParameter);
  SearchResult<AccountData> findByStatusEq(ParameterContext<SearchDetails<AccountFindByStatusEqDetails>> searchParameter);
  SearchResult<AccountData> findByZipCodeEq(ParameterContext<SearchDetails<AccountFindByZipCodeEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends AccountFindDetails> SearchResult<AccountData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
