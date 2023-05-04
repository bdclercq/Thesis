package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.search.SearchDetails;
import net.democritus.state.StateUpdate;

import java.util.Date;
import java.util.List;

// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AccountCrudsLocal {

  CrudsResult<DataRef> create(ParameterContext<AccountDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<AccountDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);

  CrudsResult<Void> delete(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getDataRefFromName(ParameterContext<String> nameParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  /* searching */
  /** find objects corresponding to the projection defined in the searchParameter */
  <S extends AccountFindDetails, P> SearchResult<P> find(ParameterContext<SearchDetails<S>> searchParameter);

  <P> CrudsResult<P> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);

  /* calculations that can be used for validation */
  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:compare-set-methods:start
  CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter);
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}