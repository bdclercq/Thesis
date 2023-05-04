package net.democritus.workflow.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.io.ByteArrayConverter;
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import workflow.context.ContextRetriever;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import net.democritus.workflow.StateTaskAgent;

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class StateTaskImporterAction extends ActionSupport {
  private static final Logger logger = LoggerFactory.getLogger(StateTaskImporterAction.class);

  private String type;
  private File file;
  private String uploadDataContentType;
  private String uploadDataFileName;
  private String downloadDirectory;

  private Map<String, String> parameters = new HashMap<String, String>();

  private ImportResult result;

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public String execute() throws Exception {
    ImportFile importFile = new ImportFile();

    if (file == null) {
      result = ImportResult.globalError("File is missing");
      return Action.SUCCESS;
    }
    if (type == null || type.isEmpty()) {
      result = ImportResult.globalError("Type is missing");
      return Action.SUCCESS;
    }

    // @anchor:validate:start
    // @anchor:validate:end
    // anchor:custom-validate:start
    // anchor:custom-validate:end

    byte[] content;
    try {
      content = Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      logger.error("Invalid file content of '" + file.toPath() + "'", e);
      result = ImportResult.globalError("Invalid file content");
      return Action.SUCCESS;
    }

    importFile.setType(type.toLowerCase());
    importFile.setContent(content);
    importFile.setParameters(parameters);

    // @anchor:import-before:start
    // @anchor:import-before:end
    // anchor:custom-import-before:start
    // anchor:custom-import-before:end
    StateTaskAgent stateTaskAgent = StateTaskAgent.getStateTaskAgent(ContextRetriever.getContext());
    result = stateTaskAgent.importFile(importFile);
    // @anchor:import-after:start
    // @anchor:import-after:end
    // anchor:custom-import-after:start
    // anchor:custom-import-after:end

    return Action.SUCCESS;
  }

  public ImportResult getJsonResult() {
    return result;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
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

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
