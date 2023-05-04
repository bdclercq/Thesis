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
import java.util.UUID;
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean Asset,
 */

public class AssetDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mType;
  private DataRef mFileAsset;
  private DataRef mInternalAsset;
  private String mContentType;
  private Long mByteSize;
  private String mFileId;
  private Boolean mComplete;
  private DataRef mExternalAsset;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public AssetDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mType = "";
    this.mFileAsset = DataRef.withId(0L);
    this.mInternalAsset = DataRef.withId(0L);
    this.mContentType = "";
    this.mByteSize = null;
    this.mFileId = "";
    this.mComplete = Boolean.FALSE;
    this.mExternalAsset = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    this.mFileId = UUID.randomUUID().toString();
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public AssetDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String type
      , DataRef fileAsset
      , DataRef internalAsset
      , String contentType
      , Long byteSize
      , String fileId
      , Boolean complete
      , DataRef externalAsset
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mType = type;
    this.mFileAsset = fileAsset;
    this.mInternalAsset = internalAsset;
    this.mContentType = contentType;
    this.mByteSize = byteSize;
    this.mFileId = fileId;
    this.mComplete = complete;
    this.mExternalAsset = externalAsset;
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

  public String getElementName() {
    return "Asset";
  }

  public String getElementPackage() {
    return "net.democritus.assets";
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

  public DataRef getFileAsset() {
    return this.mFileAsset;
  }

  public void setFileAsset(DataRef fileAsset) {
    this.mFileAsset = fileAsset;
  }

  public DataRef getInternalAsset() {
    return this.mInternalAsset;
  }

  public void setInternalAsset(DataRef internalAsset) {
    this.mInternalAsset = internalAsset;
  }

  public String getContentType() {
    return this.mContentType;
  }

  public void setContentType(String contentType) {
    this.mContentType = contentType;
  }

  public Long getByteSize() {
    return this.mByteSize;
  }

  public void setByteSize(Long byteSize) {
    this.mByteSize = byteSize;
  }

  public String getFileId() {
    return this.mFileId;
  }

  public void setFileId(String fileId) {
    this.mFileId = fileId;
  }

  public Boolean getComplete() {
    return this.mComplete;
  }

  public void setComplete(Boolean complete) {
    this.mComplete = complete;
  }

  public DataRef getExternalAsset() {
    return this.mExternalAsset;
  }

  public void setExternalAsset(DataRef externalAsset) {
    this.mExternalAsset = externalAsset;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  public void setType(AssetType assetType) {
    this.mType = assetType.name();
  }
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "assets", "net.democritus.assets", "Asset");
  }
}
