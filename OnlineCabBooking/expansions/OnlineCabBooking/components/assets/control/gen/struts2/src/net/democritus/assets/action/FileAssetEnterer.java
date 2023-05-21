package net.democritus.assets.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.democritus.io.SimpleUriFormat;
import net.democritus.io.NioFileCopier;
import net.democritus.sys.ParamTargetValueAgent;
import java.util.UUID;
import net.democritus.sys.Context;
import assets.context.ContextRetriever;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import net.democritus.assets.FileAssetAgent;
import net.democritus.assets.FileAssetDetails;

import net.democritus.sys.DataRef;
import net.democritus.sys.PageRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchDataRef;
import net.democritus.sys.SearchResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import net.democritus.acl.struts2.UserContextRetriever;

import net.democritus.sys.UserContext;
import net.democritus.json.JsonResult;
import net.democritus.json.DiagnosticsToStrutsMapper;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class FileAssetEnterer extends ActionSupport implements Preparable {

  private String fileAssetOid = "";
  private String fileAssetName = null;
  private FileAssetDetails fileAssetDetails = new FileAssetDetails();

  private Map session = ActionContext.getContext().getSession();

  private CrudsResult<DataRef> crudsResult;

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(FileAssetEnterer.class);
  private File uploadData; // the actual file
  private String uploadDataContentType; // the content type of the file
  private String uploadDataFileName; // the uploaded file name
  private FileAssetAgent fileAssetAgent;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public FileAssetAgent getFileAssetAgent() {
    return fileAssetAgent;
  }

  public FileAssetDetails getFileAssetDetails() {
    return fileAssetDetails;
  }

  // convenience method, that skips the 'Details' part
  public FileAssetDetails getFileAsset() {
    return getFileAssetDetails();
  }

  public String getFileAssetOid() {
    return fileAssetOid;
  }

  public void setFileAssetOid(String fileAssetOid) {
    this.fileAssetOid = fileAssetOid;
  }

  public String getFileAssetName() {
    return fileAssetName;
  }

  public void setFileAssetName(String fileAssetName) {
    this.fileAssetName = fileAssetName;
  }

  public void prepare() throws Exception {
    // @anchor:prepare:start
    fileAssetAgent = createFileAssetAgent();
    // @anchor:prepare:end
    // anchor:custom-prepare:start
    // anchor:custom-prepare:end
  }

  public String execute() throws Exception {

    // @anchor:execute-validation:start
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    if (!httpServletRequest.getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    String actionResult;

    if (fileAssetName != null) {
      fileAssetDetails.setName(fileAssetName);
    }

    // @anchor:execute-fileUploadOnly-before:start
    if(handleFileUpload(fileAssetDetails)) {
    // @anchor:execute-fileUploadOnly-before:end

    crudsResult = saveDetails(fileAssetDetails);
    if (crudsResult.isSuccess()) {
      DataRef dataRef = crudsResult.getValue();

      fileAssetName = dataRef.getName();
      fileAssetOid = dataRef.getId().toString();

      actionResult = Action.SUCCESS;
    } else {
      fileAssetOid = "";

      actionResult = Action.INPUT;
    }

    // @anchor:execute-fileUploadOnly-after:start
    } else {
      actionResult = Action.INPUT;
    }
    // @anchor:execute-fileUploadOnly-after:end

    DiagnosticsToStrutsMapper.mapDiagnostics(this, crudsResult);

    return actionResult;
  }

  public CrudsResult<DataRef> getCrudsResult() {
    return crudsResult;
  }

  public JsonResult<DataRef> getJsonResult() {

    if (crudsResult == null) {
      // there were validation or conversion errors
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }

    if (crudsResult.isSuccess()) {
      return JsonResult.createValue(crudsResult.getValue(), getActionMessages());
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public FileAssetDetails getJsonRoot() {
    return fileAssetDetails;
  }

  private boolean hasfileAssetOid() {
    return !(fileAssetOid.equals("") || fileAssetOid.equals("0"));
  }

  private CrudsResult<DataRef> saveDetails(FileAssetDetails fileAssetDetails) {
    boolean isNew;

    if (hasfileAssetOid()) {
      fileAssetDetails.setId(new Long(fileAssetOid));
    }

    Long id = fileAssetDetails.getId();
    isNew = id == null || id == 0L;

    if (isNew) {
      return fileAssetAgent.create(fileAssetDetails);
    } else {
      return fileAssetAgent.modify(fileAssetDetails);
    }
  }

  // @anchor:methods:start

  /*========== File Upload ==========*/

  private boolean handleFileUpload(FileAssetDetails fileAssetDetails) {
    if (uploadData == null) {
      return true; // nothing to upload is a valid use case
    }
    String subdirectoryName = "dir_" + UUID.randomUUID().toString();
    String relativeUploadName = subdirectoryName + "/" + uploadDataFileName;
    boolean isSuccess = uploadFile(uploadData, subdirectoryName, uploadDataFileName);
    if (isSuccess) {
      fileAssetDetails.setUploadUri(relativeUploadName);
    }
    return isSuccess;
  }

  private boolean uploadFile(File fileToUpload, String subdirectoryName, String targetFileName) {
    if (logger.isInfoEnabled()) {
      logger.info(
          "uploading {file: \"" + fileToUpload + "\", contentType: \"" + uploadDataContentType + "\", fileName: \"" + targetFileName + "\"}"
      );
    }
    if (fileToUpload == null) {
      return true; // nothing to upload is a valid use case
    }

    try {
      Path subdirectory = getUploadDirectory().toPath().resolve(subdirectoryName);
      Files.createDirectory(subdirectory);
      Path uploadedFile = subdirectory.resolve(targetFileName);
      Files.copy(fileToUpload.toPath(), uploadedFile);
      if (logger.isInfoEnabled()) {
        logger.info(
            "File uploaded to \"" + uploadedFile + "\""
        );
      }
      return true;
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "File upload failed", e
        );
      }
      return false;
    }
  }

  private File getUploadDirectory() {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(getContext());

    String uploadFolderUriString = paramTargetValueAgent.getParamTargetValue("fileUploadDirUri", "default");
    // URI stored in paramTargetValue is user-supplied and potentially invalid
    URI uploadFolderUri;
    try {
      uploadFolderUri = new URI(uploadFolderUriString);
    } catch (URISyntaxException e) {
      // this is backwards-compatibility hack matching SimpleUriFormat space replacement,
      // so existing projects using broken configuration don't fail immediately
      try {
        uploadFolderUri = new URI(uploadFolderUriString.replace(" ", "%20"));
        // the replacement fix worked, but issue a warning anyway
        if (logger.isWarnEnabled()) {
          logger.warn(
              "URI in <ParamTargetValue fileUploadDirUri::default> is invalid: spaces (' ') must be URI-encoded ('%20'). This may break in the future", e
          );
        }
      } catch (URISyntaxException ignoredInnerException) {
        // the URI is invalid even after space replacement, so we throw the more appropriate original exception
        throw new RuntimeException("URI in <ParamTargetValue fileUploadDirUri::default> is invalid", e);
      }
    }
    return new File(uploadFolderUri);
  }

  /*========== File Upload Accessors ==========*/

  public void setUploadData(File file) {
    this.uploadData = file;
  }

  public File getUploadData() {
    return uploadData;
  }

  public void setUploadDataContentType(String contentType) {
    this.uploadDataContentType = contentType;
  }

  public String getUploadDataContentType() {
    return uploadDataContentType;
  }

  public void setUploadDataFileName(String filename) {
    this.uploadDataFileName = filename;
  }

  public String getUploadDataFileName() {
    return uploadDataFileName;
  }
  private static FileAssetAgent createFileAssetAgent() {
    return FileAssetAgent.getFileAssetAgent(getContext());
  }

  private static Context getContext() {
    return ContextRetriever.getContext();
  }

  /**
   * @deprecated Use {@link ContextRetriever} instead
   */
  @Deprecated
  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
