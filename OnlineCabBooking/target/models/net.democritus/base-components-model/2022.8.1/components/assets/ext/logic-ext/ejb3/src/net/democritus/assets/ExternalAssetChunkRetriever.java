package net.democritus.assets;

import net.democritus.io.SimpleUriFormat;
import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.UserContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class ExternalAssetChunkRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExternalAssetChunkRetriever.class);
  private static final Diagnostic FILE_ERROR = Diagnostic.error("assets", "externalAsset", "assets.externalAsset.fileError");
  private SimpleUriFormat uriFormat = new SimpleUriFormat();

  public CrudsResult<AssetChunk> retrieve(ParameterContext<AssetChunkRef> refParam, ExternalAssetDetails externalAssetDetails) {
    AssetChunkRef chunkRef = refParam.getValue();
    int chunkSize = getChunkSize(refParam.getUserContext());

    CrudsResult<File> fileResult = getFile(refParam.construct(externalAssetDetails));
    if (fileResult.isError()) {
      return fileResult.convertError();
    }

    try (FileInputStream fileInputStream = new FileInputStream(fileResult.getValue())) {
      InputStreamToChunkReader inputStreamToChunkReader = new InputStreamToChunkReader(fileInputStream);
      return inputStreamToChunkReader.readChunk(chunkRef, chunkSize);
    } catch (IOException e) {
      LOGGER.error("Could not read inputstream", e);
      return CrudsResult.error(FILE_ERROR);
    }
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

  private int getChunkSize(UserContext userContext) {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(Context.from(userContext));
    String chunkSize = paramTargetValueAgent.getParamTargetValue("assetChunkSize", "default");
    if (chunkSize == null || chunkSize.isEmpty()) {
      return 1024 * 128;
    } else {
      return Integer.parseInt(chunkSize);
    }
  }

}
