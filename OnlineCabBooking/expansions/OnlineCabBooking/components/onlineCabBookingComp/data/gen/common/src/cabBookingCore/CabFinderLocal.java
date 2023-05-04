package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface CabFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<CabData> findAllCabs(ParameterContext<SearchDetails<CabFindAllCabsDetails>> searchParameter);
  SearchResult<CabData> findByNameEq(ParameterContext<SearchDetails<CabFindByNameEqDetails>> searchParameter);
  SearchResult<CabData> findByDriverEq(ParameterContext<SearchDetails<CabFindByDriverEqDetails>> searchParameter);
  SearchResult<CabData> findByCarTypeEq(ParameterContext<SearchDetails<CabFindByCarTypeEqDetails>> searchParameter);
  SearchResult<CabData> findAllCab(ParameterContext<SearchDetails<CabFindAllCabDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends CabFindDetails> SearchResult<CabData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
