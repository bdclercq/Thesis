package net.democritus.sys;

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

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the entity bean IdCounter,
 * representing a known IdCounter
 */
public interface IdCounterLocal {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  DataRef getDataRef(Long id);

  IdCounterInfo getInfo(Long id);
  IdCounterDetails getDetails(DataRef dataRef);
  IdCounterDetails getDetails(Long id);

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<IdCounterDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<IdCounterDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<IdCounterInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<IdCounterDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<IdCounterDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends IdCounterFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
    public Long getNextCount(String aName);
  // anchor:custom-methods:end

}

