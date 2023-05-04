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
 * Transport detailed class for the entity bean InternalAssetChunk,
 */

public class InternalAssetChunkDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private byte[] mContent;
  private DataRef mInternalAsset;
  private Integer mByteSize;
  private Integer mPosition;
  private Boolean mIsLast;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public InternalAssetChunkDetails() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mContent = new byte[1];
    this.mInternalAsset = DataRef.withId(0L);
    this.mByteSize = null;
    this.mPosition = null;
    this.mIsLast = Boolean.FALSE;
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public InternalAssetChunkDetails(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , byte[] content
      , DataRef internalAsset
      , Integer byteSize
      , Integer position
      , Boolean isLast
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mContent = content;
    this.mInternalAsset = internalAsset;
    this.mByteSize = byteSize;
    this.mPosition = position;
    this.mIsLast = isLast;
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
    return "InternalAssetChunk";
  }

  public String getElementPackage() {
    return "net.democritus.assets";
  }
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  // anchor:getters-setters:start
  public byte[] getContent() {
    return this.mContent;
  }

  public void setContent(byte[] content) {
    this.mContent = content;
  }

  public DataRef getInternalAsset() {
    return this.mInternalAsset;
  }

  public void setInternalAsset(DataRef internalAsset) {
    this.mInternalAsset = internalAsset;
  }

  public Integer getByteSize() {
    return this.mByteSize;
  }

  public void setByteSize(Integer byteSize) {
    this.mByteSize = byteSize;
  }

  public Integer getPosition() {
    return this.mPosition;
  }

  public void setPosition(Integer position) {
    this.mPosition = position;
  }

  public Boolean getIsLast() {
    return this.mIsLast;
  }

  public void setIsLast(Boolean isLast) {
    this.mIsLast = isLast;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "assets", "net.democritus.assets", "InternalAssetChunk");
  }
}
