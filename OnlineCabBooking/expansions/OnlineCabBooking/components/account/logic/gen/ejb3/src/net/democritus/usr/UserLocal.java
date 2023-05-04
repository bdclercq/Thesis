package net.democritus.usr;

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
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Local interface for the entity bean User,
 * representing a known User
 */
public interface UserLocal {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  DataRef getDataRef(Long id);

  UserInfo getInfo(Long id);
  UserDetails getDetails(DataRef dataRef);
  UserDetails getDetails(Long id);

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<UserDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<UserDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<UserInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<UserDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<UserDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends UserFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter);
  <T extends ICommand> CommandResult dispatchCommand(ParameterContext<T> commandParameter);

  // anchor:command-methods:start
  public CommandResult changePassword(ParameterContext<UserCommand.ChangePassword> commandParameter);
  // anchor:command-methods:end
  ImportResult importFile(ParameterContext<ImportFile> parameter);
  <V> AuthenticationResult authenticate(ParameterContext<AuthenticationDetails<V>> authenticationDetailsParameter);
  // @anchor:methods:end

  // anchor:custom-methods:start
  CrudsResult<Boolean> checkEncryptedPassword(ParameterContext<UserInput> userInputParameterContext);
  CrudsResult<Boolean> checkPassword(ParameterContext<UserInput> userInputParameterContext);
  // anchor:custom-methods:end

}

