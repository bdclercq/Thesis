package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface PersonFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<PersonData> findAllPersons(ParameterContext<SearchDetails<PersonFindAllPersonsDetails>> searchParameter);
  SearchResult<PersonData> findByNameEq(ParameterContext<SearchDetails<PersonFindByNameEqDetails>> searchParameter);
  SearchResult<PersonData> findByEmailEq(ParameterContext<SearchDetails<PersonFindByEmailEqDetails>> searchParameter);
  SearchResult<PersonData> findByAddressEq(ParameterContext<SearchDetails<PersonFindByAddressEqDetails>> searchParameter);
  SearchResult<PersonData> findByMobileEq(ParameterContext<SearchDetails<PersonFindByMobileEqDetails>> searchParameter);
  SearchResult<PersonData> findByUsernameEq(ParameterContext<SearchDetails<PersonFindByUsernameEqDetails>> searchParameter);
  SearchResult<PersonData> findAllPerson(ParameterContext<SearchDetails<PersonFindAllPersonDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends PersonFindDetails> SearchResult<PersonData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
