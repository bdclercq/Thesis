package net.democritus.assets.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.assets.AssetAgent;
import net.democritus.assets.AssetDetails;
import net.democritus.assets.AssetStream;
import net.democritus.json.DiagnosticsToStrutsMapper;
import net.democritus.json.JsonResult;
import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

// Custom file

public class AssetUploader extends ActionSupport {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.assets.action.AssetUploader");

  private final transient AssetAgent assetAgent = AssetAgent.getAssetAgent(getContext());
  private String type;

  // FILE UPLOAD: member variables
  private File uploadData; // the actual file
  private String uploadDataContentType; // the content type of the file
  private String uploadDataFileName; // the uploaded file name
  private String downloadDirectory;

  private CrudsResult<DataRef> result;

  @Override
  public String execute() {
    if (!ServletActionContext.getRequest().getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }

    AssetDetails assetDetails = getAssetDetails();

    FileInputStream inputStream;
    try {
      inputStream = new FileInputStream(getUploadData());
    } catch (FileNotFoundException e) {
      LOGGER.error("Uploaded file not found", e);
      addActionError("Error with file upload in control layer");
      return ERROR;
    }

    AssetStream assetStream = new AssetStream();
    assetStream.setAssetDetails(assetDetails);
    assetStream.setInputStream(inputStream);

    result = assetAgent.uploadAsset(assetStream);
    DiagnosticsToStrutsMapper.mapDiagnostics(this, result);

    return SUCCESS;
  }

  private AssetDetails getAssetDetails() {
    AssetDetails assetDetails = new AssetDetails();

    assetDetails.setName(uploadDataFileName);
    assetDetails.setType(type);
    assetDetails.setByteSize(uploadData.length());
    assetDetails.setContentType(uploadDataContentType);
    return assetDetails;
  }

  public JsonResult<DataRef> getJsonResult() {
    if (result.isSuccess()) {
      return JsonResult.createValue(result.getValue());
    } else {
      return JsonResult.createError(getActionErrors(), getFieldErrors());
    }
  }

  public JsonResult getStrutsError() {
    return JsonResult.createError("Error with file upload. File size may be too large.");
  }

  private static Context getContext() {
    return Context.from(UserContextRetriever.getUserContext());
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public File getUploadData() {
    return uploadData;
  }

  public void setUploadData(File uploadData) {
    this.uploadData = uploadData;
  }

  public String getUploadDataContentType() {
    return uploadDataContentType;
  }

  public void setUploadDataContentType(String uploadDataContentType) {
    this.uploadDataContentType = uploadDataContentType;
  }

  public String getUploadDataFileName() {
    return uploadDataFileName;
  }

  public void setUploadDataFileName(String uploadDataFileName) {
    this.uploadDataFileName = uploadDataFileName;
  }

  public String getDownloadDirectory() {
    return downloadDirectory;
  }

  public void setDownloadDirectory(String downloadDirectory) {
    this.downloadDirectory = downloadDirectory;
  }

}
