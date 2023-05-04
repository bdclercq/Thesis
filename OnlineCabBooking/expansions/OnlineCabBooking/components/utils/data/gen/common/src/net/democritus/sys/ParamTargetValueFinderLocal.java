package net.democritus.sys;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ParamTargetValueFinderLocal {

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:findBys-methods:start
  SearchResult<ParamTargetValueData> findAllParamTargetValues(ParameterContext<SearchDetails<ParamTargetValueFindAllParamTargetValuesDetails>> searchParameter);
  SearchResult<ParamTargetValueData> findByParamEq(ParameterContext<SearchDetails<ParamTargetValueFindByParamEqDetails>> searchParameter);
  SearchResult<ParamTargetValueData> findByParamEq_TargetEq(ParameterContext<SearchDetails<ParamTargetValueFindByParamEq_TargetEqDetails>> searchParameter);
  SearchResult<ParamTargetValueData> findByParamEq_ValueEq(ParameterContext<SearchDetails<ParamTargetValueFindByParamEq_ValueEqDetails>> searchParameter);
  SearchResult<ParamTargetValueData> findByTargetEq(ParameterContext<SearchDetails<ParamTargetValueFindByTargetEqDetails>> searchParameter);
  SearchResult<ParamTargetValueData> findByTargetEq_ValueEq(ParameterContext<SearchDetails<ParamTargetValueFindByTargetEq_ValueEqDetails>> searchParameter);
  SearchResult<ParamTargetValueData> findByValueEq(ParameterContext<SearchDetails<ParamTargetValueFindByValueEqDetails>> searchParameter);
  // anchor:findBys-methods:end

  <S extends ParamTargetValueFindDetails> SearchResult<ParamTargetValueData> find(ParameterContext<SearchDetails<S>> searchParameter);

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
