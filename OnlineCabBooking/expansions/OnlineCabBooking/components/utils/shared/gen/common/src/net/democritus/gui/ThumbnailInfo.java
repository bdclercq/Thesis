package net.democritus.gui;

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
 * Transport detailed class for the entity bean Thumbnail,
 */

public class ThumbnailInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mFullName;
  private String mUri;
  private Integer mDepth;
  private String mThumbType;
  private String mTargetType;
  private Integer mLeftX;
  private Integer mTopY;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ThumbnailInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mFullName = "";
    this.mUri = "";
    this.mDepth = null;
    this.mThumbType = "";
    this.mTargetType = "";
    this.mLeftX = null;
    this.mTopY = null;
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ThumbnailInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String fullName
      , String uri
      , Integer depth
      , String thumbType
      , String targetType
      , Integer leftX
      , Integer topY
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mFullName = fullName;
    this.mUri = uri;
    this.mDepth = depth;
    this.mThumbType = thumbType;
    this.mTargetType = targetType;
    this.mLeftX = leftX;
    this.mTopY = topY;
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

  public String getFullName() {
    return this.mFullName;
  }

  public void setFullName(String fullName) {
    this.mFullName = fullName;
  }

  public String getUri() {
    return this.mUri;
  }

  public void setUri(String uri) {
    this.mUri = uri;
  }

  public Integer getDepth() {
    return this.mDepth;
  }

  public void setDepth(Integer depth) {
    this.mDepth = depth;
  }

  public String getThumbType() {
    return this.mThumbType;
  }

  public void setThumbType(String thumbType) {
    this.mThumbType = thumbType;
  }

  public String getTargetType() {
    return this.mTargetType;
  }

  public void setTargetType(String targetType) {
    this.mTargetType = targetType;
  }

  public Integer getLeftX() {
    return this.mLeftX;
  }

  public void setLeftX(Integer leftX) {
    this.mLeftX = leftX;
  }

  public Integer getTopY() {
    return this.mTopY;
  }

  public void setTopY(Integer topY) {
    this.mTopY = topY;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("Name");
    fieldOrder.add("FullName");
    fieldOrder.add("Uri");
    fieldOrder.add("Depth");
    fieldOrder.add("ThumbType");
    fieldOrder.add("TargetType");
    fieldOrder.add("LeftX");
    fieldOrder.add("TopY");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "utils", "net.democritus.gui", "Thumbnail");
  }
}
