package net.democritus.assets;

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

class FileAssetChunkRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileAssetChunkRetriever.class);
  private static final Diagnostic FILE_ERROR = Diagnostic.error("assets", "fileAsset", "assets.fileAsset.fileError");

  public CrudsResult<AssetChunk> retrieve(ParameterContext<AssetChunkRef> refParam, FileAssetDetails fileAssetDetails) {
    File file = getFile(refParam.construct(fileAssetDetails));
    AssetChunkRef chunkRef = refParam.getValue();
    int chunkSize = getChunkSize(refParam.getUserContext());

    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      InputStreamToChunkReader inputStreamToChunkReader = new InputStreamToChunkReader(fileInputStream);
      return inputStreamToChunkReader.readChunk(chunkRef, chunkSize);
    } catch (IOException e) {
      LOGGER.error("Could not read inputstream", e);
      return CrudsResult.error(FILE_ERROR);
    }
  }

  private File getFile(ParameterContext<FileAssetDetails> parameter) {
    FileAssetDetails fileDetails = parameter.getValue();
    String uploadUri = fileDetails.getUploadUri();
    File uploadDir = getUploadDir(parameter.getUserContext());
    return new File(uploadDir, uploadUri);
  }

  private File getUploadDir(UserContext userContext) {
    return FileAssetDirectoryManager.getUploadDir(userContext);
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
