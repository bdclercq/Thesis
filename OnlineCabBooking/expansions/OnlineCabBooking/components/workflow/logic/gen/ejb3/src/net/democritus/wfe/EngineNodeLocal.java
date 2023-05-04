package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.ParameterContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import java.util.Date;
import java.util.List;

import net.democritus.state.StateUpdate;

// @anchor:imports:start
import java.util.Date;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the entity bean EngineNode,
 * representing a known EngineNode
 */
public interface EngineNodeLocal {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  DataRef getDataRef(Long id);

  EngineNodeInfo getInfo(Long id);
  EngineNodeDetails getDetails(DataRef dataRef);
  EngineNodeDetails getDetails(Long id);

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<EngineNodeDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<EngineNodeDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<EngineNodeInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<EngineNodeDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<EngineNodeDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends EngineNodeFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // anchor:compare-set-methods:start
  CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter);
  // anchor:compare-set-methods:end

  // @anchor:methods:start

  /*========== Exposed Field Methods ==========*/

  // anchor:exposed-fields:start
  CrudsResult<Boolean> getMaster(ParameterContext<DataRef> idParameter);
  CrudsResult<Void> setMaster(ParameterContext<Boolean> idParameter);

  CrudsResult<Date> getLastActive(ParameterContext<DataRef> idParameter);
  CrudsResult<Void> setLastActive(ParameterContext<Date> idParameter);

  // anchor:exposed-fields:end
  // @anchor:methods:end

  // anchor:custom-methods:start
  CrudsResult<Void> promoteToMaster(ParameterContext<DataRef> parameter);
  CrudsResult<Void> postHealthCheck(ParameterContext<DataRef> parameter);
  CrudsResult<Void> setNotResponding(ParameterContext<DataRef> parameter);
  CrudsResult<Void> activateEngineNodes(ParameterContext<Void> parameter);
  CrudsResult<EngineNodeDetails> getEngineNodeByName(ParameterContext<String> nameParameter);
  CrudsResult<EngineNodeDetails> getEngineNodeById(ParameterContext<Long> idParameter);
  // anchor:custom-methods:end

}

