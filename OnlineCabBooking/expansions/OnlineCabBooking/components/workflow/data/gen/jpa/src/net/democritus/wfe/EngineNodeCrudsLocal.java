package net.democritus.wfe;

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
import java.util.Date;
// @anchor:imports:end

// anchor:imports:start
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface EngineNodeCrudsLocal {

  CrudsResult<DataRef> create(ParameterContext<EngineNodeDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<EngineNodeDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);

  CrudsResult<Void> delete(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getDataRefFromName(ParameterContext<String> nameParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  /* searching */
  /** find objects corresponding to the projection defined in the searchParameter */
  <S extends EngineNodeFindDetails, P> SearchResult<P> find(ParameterContext<SearchDetails<S>> searchParameter);

  <P> CrudsResult<P> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);

  /* calculations that can be used for validation */
  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:compare-set-methods:start
  CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter);
  // anchor:compare-set-methods:end

  // @anchor:methods:start

  /*========== Exposed Field Methods ==========*/

  /* exposed fields, that can be directly be gotten or set */
  // anchor:exposed-fields:start
  CrudsResult<Boolean> getMaster(ParameterContext<DataRef> idParameter);
  CrudsResult<Void> setMaster(ParameterContext<Boolean> idParameter);

  CrudsResult<Date> getLastActive(ParameterContext<DataRef> idParameter);
  CrudsResult<Void> setLastActive(ParameterContext<Date> idParameter);

  // anchor:exposed-fields:end
  // @anchor:methods:end

  // anchor:custom-methods:start
  CrudsResult<Void> promoteToMaster(ParameterContext<DataRef> parameter);
  // anchor:custom-methods:end
}
