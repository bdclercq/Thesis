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
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the entity bean EngineService,
 * representing a known EngineService
 */
public interface EngineServiceLocal {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  DataRef getDataRef(Long id);

  EngineServiceInfo getInfo(Long id);
  EngineServiceDetails getDetails(DataRef dataRef);
  EngineServiceDetails getDetails(Long id);

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<EngineServiceDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<EngineServiceDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<EngineServiceInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<EngineServiceDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<EngineServiceDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends EngineServiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // anchor:compare-set-methods:start
  CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter);
  // anchor:compare-set-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter);
  <T extends ICommand> CommandResult dispatchCommand(ParameterContext<T> commandParameter);

  // anchor:command-methods:start
  public CommandResult startEngine(ParameterContext<EngineServiceCommand.StartEngine> commandParameter);
  public CommandResult stopEngine(ParameterContext<EngineServiceCommand.StopEngine> commandParameter);
  public CommandResult disableAllEngines(ParameterContext<EngineServiceCommand.DisableAllEngines> commandParameter);
  public CommandResult enableAllEngines(ParameterContext<EngineServiceCommand.EnableAllEngines> commandParameter);
  public CommandResult refreshEngine(ParameterContext<EngineServiceCommand.RefreshEngine> commandParameter);
  // anchor:command-methods:end

  /*========== Exposed Field Methods ==========*/

  // anchor:exposed-fields:start
  CrudsResult<String> getBusy(ParameterContext<DataRef> idParameter);
  CrudsResult<Void> setBusy(ParameterContext<String> idParameter);

  CrudsResult<Date> getLastRunAt(ParameterContext<DataRef> idParameter);
  CrudsResult<Void> setLastRunAt(ParameterContext<Date> idParameter);

  // anchor:exposed-fields:end
  ImportResult importFile(ParameterContext<ImportFile> parameter);
  // @anchor:methods:end

  // anchor:custom-methods:start
  CrudsResult<Void> updateLastRunAt(ParameterContext<DataRef> dataRefParameter);
  // anchor:custom-methods:end

}

