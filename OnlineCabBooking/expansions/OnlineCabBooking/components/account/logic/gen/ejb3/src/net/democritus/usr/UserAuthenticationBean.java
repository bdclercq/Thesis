// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.usr;

import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.SearchResult;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.democritus.sys.diagnostics.DiagnosticHelper;
import net.democritus.sys.search.SearchDetails;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// @anchor:imports:start
import javax.ejb.Local;
import net.democritus.acl.AuthenticationParams;
// @anchor:imports:end

@Stateless()
// @anchor:annotations:start
@Local(UserAuthenticationLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class UserAuthenticationBean /*@anchor:interfaces:start@*/implements UserAuthenticationLocal/*@anchor:interfaces:end@*/{

  private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationBean.class);

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("account", "User");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  private DiagnosticHelper getDiagnosticHelper() {
    return diagnosticHelper;
  }

  private DiagnosticFactory getDiagnosticFactory() {
    return diagnosticFactory;
  }

  @Override
  public <V> AuthenticationResult authenticate(ParameterContext<AuthenticationDetails<V>> authenticationDetailsParameter) {
    AuthenticationDetails<V> authenticationDetails = authenticationDetailsParameter.getValue();
    if (authenticationDetails == null) {
      return AuthenticationResult.error(getDiagnosticFactory().error("authenticationFailed"));
    }

    V authentication = authenticationDetails.getDetails();

    // @anchor:authenticate:start
    if (authentication instanceof UserInput) {
      AuthenticationDetails<UserInput> basicAuthenticationDetails =
        authenticationDetails.construct((UserInput) authentication);
      return authenticateBasic(authenticationDetailsParameter.construct(basicAuthenticationDetails));
    }
    // @anchor:authenticate:end

    return AuthenticationResult.error(getDiagnosticFactory().error("authenticationFailed"));
  }

  private UserContext createLoginUserContext(UserDetails userDetails) {
    return new ExtendedUserContext(userDetails, getProfileFromUser(userDetails), getUserGroupsFromUser(userDetails));
  }

  private Context createLoginContext(UserDetails userDetails) {
    return Context.from(createLoginUserContext(userDetails));
  }

  /**
   * Collects the names of all user groups linked to a given {@link UserDetails} object.
   *
   * @param userDetails The given user object.
   * @return A set containing all unique names of user groups the user belongs to.
   */
  private Set<String> getUserGroupsFromUser(UserDetails userDetails) {
    return Optional.ofNullable(userDetails.getUserGroups())
        .map(this::getNames)
        .orElse(Collections.emptySet());
  }

  /**
   * Maps a collection of {@link DataRef} objects to a collection of their names.
   *
   * @param dataRefs The given collection of references.
   * @return A set containing all unique names of the given references.
   */
  private Set<String> getNames(Collection<DataRef> dataRefs) {
    return dataRefs.stream()
        .filter(Objects::nonNull)
        .map(DataRef::getName)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

  /**
   * Gets the name of the profile set for a given {@link UserDetails} object.
   *
   * @param userDetails The user object.
   * @return The name of the profile linked to the user or {@code null} if no profile was linked to the object.
   */
  private String getProfileFromUser(UserDetails userDetails) {
    final DataRef profile = userDetails.getProfile();
    if (profile == null || profile.getId().equals(0L)) {
      return null;
    } else {
      return profile.getName();
    }
  }

  private boolean hasNoPasswordLogin(ParameterContext<UserDetails> userParameter) {
    final UserDetails user = userParameter.getValue();
    return ParamTargetValueAgent.getParamTargetValueAgent(userParameter.getContext())
        .isParamTargetValueActive("noPasswordLogin", user.getName(), false);
  }

  // @anchor:methods:start
  @Override
  public AuthenticationResult authenticateBasic(ParameterContext<AuthenticationDetails<UserInput>> authenticationDetailsParameter) {
    Context context = authenticationDetailsParameter.getContext();
    AuthenticationDetails<UserInput> authenticationDetails = authenticationDetailsParameter.getValue();
    UserInput details = authenticationDetails.getDetails();

    UserFindByNameEqDetails userFinder = new UserFindByNameEqDetails();
    userFinder.setName(details.getName());
    SearchDetails<UserFindByNameEqDetails> searchDetails = SearchDetails.fetchAllDetails(userFinder);
    UserLocalAgent agent = UserLocalAgent.getUserAgent(context);
    SearchResult<UserDetails> searchResult = agent.find(searchDetails);
    if (searchResult.isError()) {
      return AuthenticationResult.error(searchResult.getDiagnostics());
    }
    if (searchResult.getTotalNumberOfItems() != 1) {
      return AuthenticationResult.error(getDiagnosticFactory().error("authenticationFailed"));
    } else {
      UserDetails dbUser = searchResult.getFirst().getValue();
      if (hasNoPasswordLogin(context.withParameter(dbUser))) {
        logger.info("Can't log in with basic authentication for a user without password");
        return AuthenticationResult.error(getDiagnosticFactory().error("authenticationFailed"));
      } else {
        AuthenticationParams authenticationParams = new AuthenticationParams();
        boolean isPasswordEncrypted = authenticationParams.useEncryption();
        UserLocalAgent userAgent = UserLocalAgent.getUserAgent(context);
        CrudsResult<Boolean> crudsResult;
        if (isPasswordEncrypted) {
          crudsResult = userAgent.checkEncryptedPassword(context.withParameter(details));
        } else {
          crudsResult = userAgent.checkPassword(context.withParameter(details));
        }
        if (crudsResult.isError()) {
          return AuthenticationResult.error(crudsResult.getDiagnostics());
        }
        if (!Boolean.TRUE.equals(crudsResult.getValue())) {
          if (isPasswordEncrypted) {
            if (logger.isDebugEnabled()) {
              logger.debug(
                  "Input password doesn't match stored encrypted password"
              );
            }
          } else {
            if (logger.isInfoEnabled()) {
              logger.info(
                  "Input password doesn't match stored password"
              );
            }
          }
          return AuthenticationResult.error(getDiagnosticFactory().error("authenticationFailed"));
        }
        return AuthenticationResult.success(createLoginContext(dbUser));
      }
    }
  }
  // @anchor:methods:end

}
