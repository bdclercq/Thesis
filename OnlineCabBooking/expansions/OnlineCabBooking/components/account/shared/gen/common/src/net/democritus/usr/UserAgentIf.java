package net.democritus.usr;

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
 * interface for the entity bean UserBean,
 * representing a known User
 */
public interface UserAgentIf {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Business logic methods ==========*/

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(UserDetails userDetails);
  CrudsResult<DataRef> modify(UserDetails userDetails);
  <P> CrudsResult<DataRef> createOrModify(P projection);
  CrudsResult<Void> delete(Long id);
  CrudsResult<Void> delete(DataRef dataRef);

  <T> CrudsResult<T> getProjection(ProjectionRef projectionRef);
  CrudsResult<DataRef> resolveDataRef(DataRef dataRef);
  CrudsResult<UserDetails> getDetails(DataRef dataRef);
  // anchor:crud-methods:end

  // anchor:exposed-fields:start
  // anchor:exposed-fields:end

  // anchor:search-methods:start
  <S extends UserFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails);
  // anchor:search-methods:end

  // anchor:data-only-methods:start
  CrudsResult<UserDetails> getDetails(Long userId);
  CrudsResult<DataRef> getDataRef(String name);

  SearchResult<DataRef> findAllDataRefs();
  SearchResult<UserInfo> findAllInfos();
  // anchor:data-only-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  <T extends ICommand> CommandResult perform(T command);
  <V> AuthenticationResult authenticate(AuthenticationDetails<V> authenticationDetails);
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
