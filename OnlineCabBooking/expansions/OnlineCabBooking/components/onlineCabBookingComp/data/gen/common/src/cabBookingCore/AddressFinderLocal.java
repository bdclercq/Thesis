package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AddressFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<AddressData> findAllAddresss(ParameterContext<SearchDetails<AddressFindAllAddresssDetails>> searchParameter);
  SearchResult<AddressData> findByNameEq(ParameterContext<SearchDetails<AddressFindByNameEqDetails>> searchParameter);
  SearchResult<AddressData> findByPincodeEq(ParameterContext<SearchDetails<AddressFindByPincodeEqDetails>> searchParameter);
  SearchResult<AddressData> findByCityEq(ParameterContext<SearchDetails<AddressFindByCityEqDetails>> searchParameter);
  SearchResult<AddressData> findByStateEq(ParameterContext<SearchDetails<AddressFindByStateEqDetails>> searchParameter);
  SearchResult<AddressData> findByStreetEq(ParameterContext<SearchDetails<AddressFindByStreetEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends AddressFindDetails> SearchResult<AddressData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
