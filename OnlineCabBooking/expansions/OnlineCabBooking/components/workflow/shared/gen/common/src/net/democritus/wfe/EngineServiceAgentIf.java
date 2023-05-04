package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

// @anchor:imports:start
import net.democritus.sys.command.CommandResult;
import net.democritus.sys.command.ICommand;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * interface for the entity bean EngineServiceBean,
 * representing a known EngineService
 */
public interface EngineServiceAgentIf {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business logic methods ==========*/

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(EngineServiceDetails engineServiceDetails);
  CrudsResult<DataRef> modify(EngineServiceDetails engineServiceDetails);
  <P> CrudsResult<DataRef> createOrModify(P projection);
  CrudsResult<Void> delete(Long id);
  CrudsResult<Void> delete(DataRef dataRef);

  <T> CrudsResult<T> getProjection(ProjectionRef projectionRef);
  CrudsResult<DataRef> resolveDataRef(DataRef dataRef);
  CrudsResult<EngineServiceDetails> getDetails(DataRef dataRef);
  // anchor:crud-methods:end

  // anchor:exposed-fields:start
  // anchor:exposed-fields:end

  // anchor:search-methods:start
  <S extends EngineServiceFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails);
  // anchor:search-methods:end

  // anchor:data-only-methods:start
  CrudsResult<EngineServiceDetails> getDetails(Long engineServiceId);
  CrudsResult<DataRef> getDataRef(String name);

  SearchResult<DataRef> findAllDataRefs();
  SearchResult<EngineServiceInfo> findAllInfos();
  // anchor:data-only-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  <T extends ICommand> CommandResult perform(T command);
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
