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
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the entity bean EngineNodeService,
 * representing a known EngineNodeService
 */
public interface EngineNodeServiceLocal {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  DataRef getDataRef(Long id);

  EngineNodeServiceInfo getInfo(Long id);
  EngineNodeServiceDetails getDetails(DataRef dataRef);
  EngineNodeServiceDetails getDetails(Long id);

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<EngineNodeServiceDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<EngineNodeServiceDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  <P> CrudsResult<DataRef> modifyWithProjection(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<EngineNodeServiceInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<EngineNodeServiceDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<EngineNodeServiceDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends EngineNodeServiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // anchor:compare-set-methods:start
  CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter);
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
    CrudsResult<Void> setNotResponding(ParameterContext<DataRef> parameter);
    CrudsResult<Void> setReadyForShutdown(ParameterContext<DataRef> parameter);
    CrudsResult<EngineNodeServiceDetails> getEngineNodeServiceForEngineService(ParameterContext<DataRef> parameter);
  // anchor:custom-methods:end

}

