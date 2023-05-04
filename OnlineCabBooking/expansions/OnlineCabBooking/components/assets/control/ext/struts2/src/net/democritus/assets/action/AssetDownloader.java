package net.democritus.assets.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.assets.AssetAgent;
import net.democritus.assets.AssetDetails;
import net.democritus.assets.AssetFindByFileIdEqDetails;
import net.democritus.assets.AssetStream;
import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class AssetDownloader extends ActionSupport {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.assets.action.AssetDownloader");

  private transient InputStream downloadStream;
  private String fileId = "";
  private String fileName = "";
  private String contentType = "application/octet-stream";
  private Long contentLength = 0L;

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getContentType() {
    return this.contentType;
  }

  public Long getContentLength() {
    return this.contentLength;
  }

  public String getFileName() {
    return fileName;
  }

  public InputStream getDownloadStream() {
    return this.downloadStream;
  }

  public String execute() throws Exception {
    if (!ServletActionContext.getRequest().getMethod().equals("GET")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using GET");
      return Action.SUCCESS;
    }

    if (fileId == null || fileId.isEmpty()) {
      LOGGER.info("Received download request without fileId. Returning error.");
      return Action.ERROR;
    }

    AssetAgent assetAgent = AssetAgent.getAssetAgent(getContext());

    AssetFindByFileIdEqDetails findr = new AssetFindByFileIdEqDetails();
    findr.setFileId(fileId);
    SearchDetails<AssetFindByFileIdEqDetails> searchDetails = new SearchDetails<AssetFindByFileIdEqDetails>(findr);
    searchDetails.setProjection("dataRef");

    SearchResult<DataRef> searchResult = assetAgent.find(searchDetails);

    if (searchResult.isError()) {
      LOGGER.error("Could not retrieve asset due to error");
      return Action.ERROR;
    }
    if (searchResult.getResults().size() != 1) {
      LOGGER.error("Could not find asset");
      return Action.ERROR;
    }

    DataRef asset = searchResult.getFirst().getValue();
    CrudsResult<AssetStream> streamResult = assetAgent.getAssetStream(asset);

    if (streamResult.isError()) {
      LOGGER.error("Could not retrieve stream due to error");
      return Action.ERROR;
    }

    AssetStream assetStream = streamResult.getValue();
    AssetDetails assetDetails = assetStream.getAssetDetails();

    this.contentLength = assetDetails.getByteSize();
    this.contentType = assetDetails.getContentType();
    this.fileName = assetDetails.getName();
    this.downloadStream = assetStream.getInputStream();
    return Action.SUCCESS;
  }

  private static Context getContext() {
    return Context.from(UserContextRetriever.getUserContext());
  }

}