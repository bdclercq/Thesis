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

public class ThumbnailDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mFullName;
  private String mUri;
  private Integer mDepth;
  private Integer mBorder;
  private String mThumbType;
  private String mThumbName;
  private String mTargetType;
  private String mTargetName;
  private Long mTargetId;
  private Integer mLeftX;
  private Integer mTopY;
  private Integer mWidth;
  private Integer mHeight;
  private String mClickAction;
  private String mHooverAction;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ThumbnailDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mFullName = "";
    this.mUri = "";
    this.mDepth = null;
    this.mBorder = null;
    this.mThumbType = "";
    this.mThumbName = "";
    this.mTargetType = "";
    this.mTargetName = "";
    this.mTargetId = null;
    this.mLeftX = null;
    this.mTopY = null;
    this.mWidth = null;
    this.mHeight = null;
    this.mClickAction = "";
    this.mHooverAction = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ThumbnailDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String fullName
      , String uri
      , Integer depth
      , Integer border
      , String thumbType
      , String thumbName
      , String targetType
      , String targetName
      , Long targetId
      , Integer leftX
      , Integer topY
      , Integer width
      , Integer height
      , String clickAction
      , String hooverAction
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
    this.mBorder = border;
    this.mThumbType = thumbType;
    this.mThumbName = thumbName;
    this.mTargetType = targetType;
    this.mTargetName = targetName;
    this.mTargetId = targetId;
    this.mLeftX = leftX;
    this.mTopY = topY;
    this.mWidth = width;
    this.mHeight = height;
    this.mClickAction = clickAction;
    this.mHooverAction = hooverAction;
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
    return "Thumbnail";
  }

  public String getElementPackage() {
    return "net.democritus.gui";
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

  public Integer getBorder() {
    return this.mBorder;
  }

  public void setBorder(Integer border) {
    this.mBorder = border;
  }

  public String getThumbType() {
    return this.mThumbType;
  }

  public void setThumbType(String thumbType) {
    this.mThumbType = thumbType;
  }

  public String getThumbName() {
    return this.mThumbName;
  }

  public void setThumbName(String thumbName) {
    this.mThumbName = thumbName;
  }

  public String getTargetType() {
    return this.mTargetType;
  }

  public void setTargetType(String targetType) {
    this.mTargetType = targetType;
  }

  public String getTargetName() {
    return this.mTargetName;
  }

  public void setTargetName(String targetName) {
    this.mTargetName = targetName;
  }

  public Long getTargetId() {
    return this.mTargetId;
  }

  public void setTargetId(Long targetId) {
    this.mTargetId = targetId;
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

  public Integer getWidth() {
    return this.mWidth;
  }

  public void setWidth(Integer width) {
    this.mWidth = width;
  }

  public Integer getHeight() {
    return this.mHeight;
  }

  public void setHeight(Integer height) {
    this.mHeight = height;
  }

  public String getClickAction() {
    return this.mClickAction;
  }

  public void setClickAction(String clickAction) {
    this.mClickAction = clickAction;
  }

  public String getHooverAction() {
    return this.mHooverAction;
  }

  public void setHooverAction(String hooverAction) {
    this.mHooverAction = hooverAction;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "utils", "net.democritus.gui", "Thumbnail");
  }
}
