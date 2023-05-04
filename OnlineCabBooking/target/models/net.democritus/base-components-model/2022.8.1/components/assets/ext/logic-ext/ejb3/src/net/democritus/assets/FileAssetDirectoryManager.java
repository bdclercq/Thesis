package net.democritus.assets;

import net.democritus.io.SimpleUriFormat;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.UserContext;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FileAssetDirectoryManager {

  private static SimpleUriFormat uriFormat = new SimpleUriFormat();

  public static File getUploadDir(UserContext userContext) {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(userContext);
    String fileUploadDirUri = paramTargetValueAgent.getParamTargetValue("fileUploadDirUri", "default");

    try {
      File uploadDir = new File(new URI(uriFormat.format(fileUploadDirUri)));
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      return uploadDir;
    } catch (URISyntaxException e) {
      throw new RuntimeException("Could not get upload dir", e);
    }
  }

}
