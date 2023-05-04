package net.democritus.assets;

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

public class AssetUniquenessValidation<P> {
  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AssetUniquenessValidation.class);
  // @anchor:variables:end

  private final AssetLocal assetLocal;
  private final ParameterContext<P> projectionParameter;

  DiagnosticFactory diagnosticFactory = new DiagnosticHelper("assets", "Asset").getDiagnosticFactory();

  public AssetUniquenessValidation(AssetLocal assetLocal, ParameterContext<P> projectionParameter) {
    this.assetLocal = assetLocal;
    this.projectionParameter = projectionParameter;
  }

  public CrudsResult<Void> checkUnique() {
    P projection = projectionParameter.getValue();

    // anchor:projection-dispatch:start
    if (projection instanceof AssetDetails) {
      return checkUnique((AssetDetails) projection);
    }

    if (projection instanceof AssetInfo) {
      return checkUnique((AssetInfo) projection);
    }

    if (projection instanceof AssetDownloadLink) {
      return checkUnique((AssetDownloadLink) projection);
    }
    // anchor:projection-dispatch:end

    return CrudsResult.error(diagnosticFactory.error("projection not supported"));
  }

  // anchor:projection-constraints:start
  private CrudsResult<Void> checkUnique(AssetDetails projection) {
    AssetFindByFileIdEqDetails finder = new AssetFindByFileIdEqDetails();
    finder.setFileId(projection.getFileId());
    return verifyUnique(finder, projection.getId());
  }

  private CrudsResult<Void> checkUnique(AssetInfo projection) {
    CrudsResult<AssetDetails> detailsResult = assetLocal.getDetails(projectionParameter.construct(projection.getId()));
    if (detailsResult.isError()) {
        return detailsResult.convertError();
    }
    AssetFindByFileIdEqDetails finder = fill_AssetFindByFileIdEqDetails_fromDetails(detailsResult.getValue());
    return verifyUnique(finder, projection.getId());
  }

  private CrudsResult<Void> checkUnique(AssetDownloadLink projection) {
    CrudsResult<AssetDetails> detailsResult = assetLocal.getDetails(projectionParameter.construct(projection.getId()));
    if (detailsResult.isError()) {
        return detailsResult.convertError();
    }
    AssetFindByFileIdEqDetails finder = fill_AssetFindByFileIdEqDetails_fromDetails(detailsResult.getValue());
    return verifyUnique(finder, projection.getId());
  }
  // anchor:projection-constraints:end

  private <F extends AssetFindDetails> CrudsResult<Void> verifyUnique(F finder, Long id) {
    SearchDetails<F> searchDetails = new SearchDetails<F>(finder);

    searchDetails.setProjection("dataRef");

    SearchResult<DataRef> searchResult = assetLocal.find(projectionParameter.construct(searchDetails));
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
    diagnostics.add(diagnosticFactory.error("assets.asset.uniqueKey.error"));
    return CrudsResult.error(diagnostics);
  }

  // anchor:fillConstraintFromDetails:start
  private AssetFindByFileIdEqDetails fill_AssetFindByFileIdEqDetails_fromDetails(AssetDetails projection) {
    AssetFindByFileIdEqDetails finder = new AssetFindByFileIdEqDetails();
    finder.setFileId(projection.getFileId());
    return finder;
  }
  // anchor:fillConstraintFromDetails:end
}
