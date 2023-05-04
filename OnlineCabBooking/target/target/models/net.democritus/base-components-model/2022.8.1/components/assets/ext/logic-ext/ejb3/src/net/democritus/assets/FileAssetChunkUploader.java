package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ParameterContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class FileAssetChunkUploader {

  private static final Logger LOGGER = LoggerFactory.getLogger(net.democritus.assets.FileAssetChunkUploader.class);

  CrudsResult<Void> upload(ParameterContext<AssetChunk> chunkParam, FileAssetDetails fileAsset) {
    AssetChunk assetChunk = chunkParam.getValue();

    File uploadDir = FileAssetDirectoryManager.getUploadDir(chunkParam.getUserContext());
    File file = new File(uploadDir, fileAsset.getUploadUri());

    return appendToFile(assetChunk.getContent(), file);
  }

  private CrudsResult<Void> appendToFile(byte[] content, File toFile) {
    // The second parameter defines that the content should be appended to the current file
    try (FileOutputStream fos = new FileOutputStream(toFile, true)) {
      fos.write(content);
      return CrudsResult.success();
    } catch (IOException e) {
      LOGGER.error("Could not write file", e);
      return CrudsResult.error(Diagnostic.error("assets", "fileAsset", "assets.fileAsset.couldnotwritefile"));
    }
  }

}
