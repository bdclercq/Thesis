package net.democritus.assets;

import net.democritus.io.SimpleUriFormat;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ParameterContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

class ExternalAssetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.assets.ExternalAssetCreator");
  private static final Diagnostic FILE_ERROR = Diagnostic.error("assets", "externalAsset", "assets.externalAsset.fileError");

  private SimpleUriFormat uriFormat = new SimpleUriFormat();

  public CrudsResult<Void> initExternalAsset(ParameterContext<ExternalAssetDetails> param) {
    ExternalAssetDetails externalAssetDetails = param.getValue();

    CrudsResult<File> fileResult = getFile(param);
    if (fileResult.isError()) {
      return fileResult.convertError();
    }

    File file = fileResult.getValue();
    externalAssetDetails.setName(file.getName());
    externalAssetDetails.setByteSize(file.length());
    externalAssetDetails.setContentType(getContentType(file));
    return CrudsResult.success();
  }

  private String getContentType(File file) {
    return URLConnection.guessContentTypeFromName(file.getName());
  }

  private CrudsResult<File> getFile(ParameterContext<ExternalAssetDetails> parameter) {
    try {
      ExternalAssetDetails fileDetails = parameter.getValue();
      String uriString = uriFormat.format(fileDetails.getUri());
      URI uri = new URI(uriString);
      return CrudsResult.success(new File(uri));
    } catch (URISyntaxException e) {
      LOGGER.error("Could not retrieve external asset details");
      return CrudsResult.error(FILE_ERROR);
    }
  }

}
