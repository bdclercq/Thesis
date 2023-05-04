package net.democritus.assets;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.io.Serializable;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;
import java.util.ArrayList;
import java.util.List;

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean Asset,
 */

public class AssetDownloadLink
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mType;
  private String mDownloadLink;
  private String mContentType;
  private String mFileSize;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public AssetDownloadLink() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mType = "";
    this.mDownloadLink = "";
    this.mContentType = "";
    this.mFileSize = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public AssetDownloadLink(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String type
      , String downloadLink
      , String contentType
      , String fileSize
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mType = type;
    this.mDownloadLink = downloadLink;
    this.mContentType = contentType;
    this.mFileSize = fileSize;
    // anchor:detailed-constructor-initialization:end
    // @anchor:detailed-constructor-initialization:start
    // @anchor:detailed-constructor-initialization:end

    // anchor:custom-detail-constructor:start
    // anchor:custom-detail-constructor:end
  }

  /*========== Getters and Setters ==========*/

  public Long getId() {
    return this.mId;
  }

  public void setId(Long id) {
    this.mId = id;
  }

  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getType() {
    return this.mType;
  }

  public void setType(String type) {
    this.mType = type;
  }

  public String getDownloadLink() {
    return this.mDownloadLink;
  }

  public void setDownloadLink(String downloadLink) {
    this.mDownloadLink = downloadLink;
  }

  public String getContentType() {
    return this.mContentType;
  }

  public void setContentType(String contentType) {
    this.mContentType = contentType;
  }

  public String getFileSize() {
    return this.mFileSize;
  }

  public void setFileSize(String fileSize) {
    this.mFileSize = fileSize;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "assets", "net.democritus.assets", "Asset");
  }
}
