package net.democritus.assets;

import net.democritus.sys.ParameterContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.File;
import java.util.UUID;

public class FileAssetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.assets.FileAssetCreator");

  public void initUploadFile(ParameterContext<FileAssetDetails> parameter) {
    FileAssetDetails assetDetails = parameter.getValue();

    File uploadDir = FileAssetDirectoryManager.getUploadDir(parameter.getUserContext());
    String subFolderName = UUID.randomUUID().toString();
    String fileName = assetDetails.getName();

    File subFolder = new File(uploadDir, subFolderName);
    subFolder.mkdirs();

    String uploadUri = subFolderName + "/" + fileName;

    assetDetails.setUploadUri(uploadUri);
  }

}
