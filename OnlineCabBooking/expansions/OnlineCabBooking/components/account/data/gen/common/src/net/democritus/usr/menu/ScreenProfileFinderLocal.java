package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ScreenProfileFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<ScreenProfileData> findAllScreenProfiles(ParameterContext<SearchDetails<ScreenProfileFindAllScreenProfilesDetails>> searchParameter);
  SearchResult<ScreenProfileData> findByProfileEq(ParameterContext<SearchDetails<ScreenProfileFindByProfileEqDetails>> searchParameter);
  SearchResult<ScreenProfileData> findByScreenEq(ParameterContext<SearchDetails<ScreenProfileFindByScreenEqDetails>> searchParameter);
  SearchResult<ScreenProfileData> findByScreenEq_ProfileEq(ParameterContext<SearchDetails<ScreenProfileFindByScreenEq_ProfileEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends ScreenProfileFindDetails> SearchResult<ScreenProfileData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
