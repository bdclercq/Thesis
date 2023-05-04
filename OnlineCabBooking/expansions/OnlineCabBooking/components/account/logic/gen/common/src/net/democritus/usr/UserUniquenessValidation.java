package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.diagnostics.DiagnosticHelper;
import net.democritus.sys.search.SearchDetails;
import net.palver.util.Options.Option;

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

import java.util.LinkedList;
import java.util.List;

public class UserUniquenessValidation<P> {
  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(UserUniquenessValidation.class);
  // @anchor:variables:end

  private final UserLocal userLocal;
  private final ParameterContext<P> projectionParameter;

  DiagnosticFactory diagnosticFactory = new DiagnosticHelper("account", "User").getDiagnosticFactory();

  public UserUniquenessValidation(UserLocal userLocal, ParameterContext<P> projectionParameter) {
    this.userLocal = userLocal;
    this.projectionParameter = projectionParameter;
  }

  public CrudsResult<Void> checkUnique() {
    P projection = projectionParameter.getValue();

    // anchor:projection-dispatch:start
    if (projection instanceof UserDetails) {
      return checkUnique((UserDetails) projection);
    }

    if (projection instanceof UserInfo) {
      return checkUnique((UserInfo) projection);
    }

    if (projection instanceof UserInput) {
      return checkUnique((UserInput) projection);
    }

    if (projection instanceof UserDetailsWithoutRefs) {
      return checkUnique((UserDetailsWithoutRefs) projection);
    }
    // anchor:projection-dispatch:end

    return CrudsResult.error(diagnosticFactory.error("projection not supported"));
  }

  // anchor:projection-constraints:start
  private CrudsResult<Void> checkUnique(UserDetails projection) {
    UserFindByNameEqDetails finder = new UserFindByNameEqDetails();
    finder.setName(projection.getName());
    return verifyUnique(finder, projection.getId());
  }

  private CrudsResult<Void> checkUnique(UserInfo projection) {
    UserFindByNameEqDetails finder = new UserFindByNameEqDetails();
    finder.setName(projection.getName());
    return verifyUnique(finder, projection.getId());
  }

  private CrudsResult<Void> checkUnique(UserInput projection) {
    UserFindByNameEqDetails finder = new UserFindByNameEqDetails();
    finder.setName(projection.getName());
    return verifyUnique(finder, projection.getId());
  }

  private CrudsResult<Void> checkUnique(UserDetailsWithoutRefs projection) {
    UserFindByNameEqDetails finder = new UserFindByNameEqDetails();
    finder.setName(projection.getName());
    return verifyUnique(finder, projection.getId());
  }
  // anchor:projection-constraints:end

  private <F extends UserFindDetails> CrudsResult<Void> verifyUnique(F finder, Long id) {
    SearchDetails<F> searchDetails = new SearchDetails<F>(finder);

    searchDetails.setProjection("dataRef");

    SearchResult<DataRef> searchResult = userLocal.find(projectionParameter.construct(searchDetails));
    if (searchResult.isError()) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot verify uniqueness because of failed search"
        );
      }
      return CrudsResult.error(diagnosticFactory.error("search error"));
    }

    Option<DataRef> optOther = searchResult.getFirst();
    if (optOther.isEmpty()) {
      return CrudsResult.success();
    }

    DataRef other = optOther.getValue();
    if (other.getId().equals(id)) {
      return CrudsResult.success();
    }

    List<Diagnostic> diagnostics = new LinkedList<Diagnostic>();
    diagnostics.add(diagnosticFactory.error("account.user.uniqueKey.error"));
    return CrudsResult.error(diagnostics);
  }

  // anchor:fillConstraintFromDetails:start
  private UserFindByNameEqDetails fill_UserFindByNameEqDetails_fromDetails(UserDetails projection) {
    UserFindByNameEqDetails finder = new UserFindByNameEqDetails();
    finder.setName(projection.getName());
    return finder;
  }
  // anchor:fillConstraintFromDetails:end
}
