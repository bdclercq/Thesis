package net.democritus.gui;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.democritus.sys.DataRef;

// anchor:imports:start
// anchor:imports:end

// @anchor:imports:start
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Persistency pojo class for the entity Thumbnail,
 */

@Entity(name=ThumbnailData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Thumbnail", schema="UTILS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ThumbnailData.QUERY_FINDALL, query="select o FROM " + ThumbnailData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class ThumbnailData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.gui.Thumbnail";
  public static final String QUERY_FINDALL = "net.democritus.gui.Thumbnail.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
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
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ThumbnailData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ThumbnailData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
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
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
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
    // anchor:constructor-assign:end
  }

  /*========== Getters and Setters ==========*/

  @Id
  // @anchor:generateId:start
  @GeneratedValue(strategy=GenerationType.AUTO)
  // @anchor:generateId:end
  // anchor:custom-annotations-getter-id:start
  // anchor:custom-annotations-getter-id:end
  public Long getId() {
    return this.mId;
  }

  // anchor:custom-annotations-setter-id:start
  // anchor:custom-annotations-setter-id:end
  public void setId(Long id) {
    this.mId = id;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-name:start
  // @anchor:annotations-getter-name:end
  // anchor:custom-annotations-getter-name:start
  // anchor:custom-annotations-getter-name:end
  public String getName() {
    return this.mName;
  }

  // @anchor:annotations-setter-name:start
  // @anchor:annotations-setter-name:end
  // anchor:custom-annotations-setter-name:start
  // anchor:custom-annotations-setter-name:end
  public void setName(String name) {
    this.mName = name;
  }

  // @anchor:annotations-getter-fullName:start
  // @anchor:annotations-getter-fullName:end
  // anchor:custom-annotations-getter-fullName:start
  // anchor:custom-annotations-getter-fullName:end
  public String getFullName() {
    return this.mFullName;
  }

  // @anchor:annotations-setter-fullName:start
  // @anchor:annotations-setter-fullName:end
  // anchor:custom-annotations-setter-fullName:start
  // anchor:custom-annotations-setter-fullName:end
  public void setFullName(String fullName) {
    this.mFullName = fullName;
  }

  // @anchor:annotations-getter-uri:start
  // @anchor:annotations-getter-uri:end
  // anchor:custom-annotations-getter-uri:start
  // anchor:custom-annotations-getter-uri:end
  public String getUri() {
    return this.mUri;
  }

  // @anchor:annotations-setter-uri:start
  // @anchor:annotations-setter-uri:end
  // anchor:custom-annotations-setter-uri:start
  // anchor:custom-annotations-setter-uri:end
  public void setUri(String uri) {
    this.mUri = uri;
  }

  // @anchor:annotations-getter-depth:start
  // @anchor:annotations-getter-depth:end
  // anchor:custom-annotations-getter-depth:start
  // anchor:custom-annotations-getter-depth:end
  public Integer getDepth() {
    return this.mDepth;
  }

  // @anchor:annotations-setter-depth:start
  // @anchor:annotations-setter-depth:end
  // anchor:custom-annotations-setter-depth:start
  // anchor:custom-annotations-setter-depth:end
  public void setDepth(Integer depth) {
    this.mDepth = depth;
  }

  // @anchor:annotations-getter-border:start
  // @anchor:annotations-getter-border:end
  // anchor:custom-annotations-getter-border:start
  // anchor:custom-annotations-getter-border:end
  public Integer getBorder() {
    return this.mBorder;
  }

  // @anchor:annotations-setter-border:start
  // @anchor:annotations-setter-border:end
  // anchor:custom-annotations-setter-border:start
  // anchor:custom-annotations-setter-border:end
  public void setBorder(Integer border) {
    this.mBorder = border;
  }

  // @anchor:annotations-getter-thumbType:start
  // @anchor:annotations-getter-thumbType:end
  // anchor:custom-annotations-getter-thumbType:start
  // anchor:custom-annotations-getter-thumbType:end
  public String getThumbType() {
    return this.mThumbType;
  }

  // @anchor:annotations-setter-thumbType:start
  // @anchor:annotations-setter-thumbType:end
  // anchor:custom-annotations-setter-thumbType:start
  // anchor:custom-annotations-setter-thumbType:end
  public void setThumbType(String thumbType) {
    this.mThumbType = thumbType;
  }

  // @anchor:annotations-getter-thumbName:start
  // @anchor:annotations-getter-thumbName:end
  // anchor:custom-annotations-getter-thumbName:start
  // anchor:custom-annotations-getter-thumbName:end
  public String getThumbName() {
    return this.mThumbName;
  }

  // @anchor:annotations-setter-thumbName:start
  // @anchor:annotations-setter-thumbName:end
  // anchor:custom-annotations-setter-thumbName:start
  // anchor:custom-annotations-setter-thumbName:end
  public void setThumbName(String thumbName) {
    this.mThumbName = thumbName;
  }

  // @anchor:annotations-getter-targetType:start
  // @anchor:annotations-getter-targetType:end
  // anchor:custom-annotations-getter-targetType:start
  // anchor:custom-annotations-getter-targetType:end
  public String getTargetType() {
    return this.mTargetType;
  }

  // @anchor:annotations-setter-targetType:start
  // @anchor:annotations-setter-targetType:end
  // anchor:custom-annotations-setter-targetType:start
  // anchor:custom-annotations-setter-targetType:end
  public void setTargetType(String targetType) {
    this.mTargetType = targetType;
  }

  // @anchor:annotations-getter-targetName:start
  // @anchor:annotations-getter-targetName:end
  // anchor:custom-annotations-getter-targetName:start
  // anchor:custom-annotations-getter-targetName:end
  public String getTargetName() {
    return this.mTargetName;
  }

  // @anchor:annotations-setter-targetName:start
  // @anchor:annotations-setter-targetName:end
  // anchor:custom-annotations-setter-targetName:start
  // anchor:custom-annotations-setter-targetName:end
  public void setTargetName(String targetName) {
    this.mTargetName = targetName;
  }

  // @anchor:annotations-getter-targetId:start
  // @anchor:annotations-getter-targetId:end
  // anchor:custom-annotations-getter-targetId:start
  // anchor:custom-annotations-getter-targetId:end
  public Long getTargetId() {
    return this.mTargetId;
  }

  // @anchor:annotations-setter-targetId:start
  // @anchor:annotations-setter-targetId:end
  // anchor:custom-annotations-setter-targetId:start
  // anchor:custom-annotations-setter-targetId:end
  public void setTargetId(Long targetId) {
    this.mTargetId = targetId;
  }

  // @anchor:annotations-getter-leftX:start
  // @anchor:annotations-getter-leftX:end
  // anchor:custom-annotations-getter-leftX:start
  // anchor:custom-annotations-getter-leftX:end
  public Integer getLeftX() {
    return this.mLeftX;
  }

  // @anchor:annotations-setter-leftX:start
  // @anchor:annotations-setter-leftX:end
  // anchor:custom-annotations-setter-leftX:start
  // anchor:custom-annotations-setter-leftX:end
  public void setLeftX(Integer leftX) {
    this.mLeftX = leftX;
  }

  // @anchor:annotations-getter-topY:start
  // @anchor:annotations-getter-topY:end
  // anchor:custom-annotations-getter-topY:start
  // anchor:custom-annotations-getter-topY:end
  public Integer getTopY() {
    return this.mTopY;
  }

  // @anchor:annotations-setter-topY:start
  // @anchor:annotations-setter-topY:end
  // anchor:custom-annotations-setter-topY:start
  // anchor:custom-annotations-setter-topY:end
  public void setTopY(Integer topY) {
    this.mTopY = topY;
  }

  // @anchor:annotations-getter-width:start
  // @anchor:annotations-getter-width:end
  // anchor:custom-annotations-getter-width:start
  // anchor:custom-annotations-getter-width:end
  public Integer getWidth() {
    return this.mWidth;
  }

  // @anchor:annotations-setter-width:start
  // @anchor:annotations-setter-width:end
  // anchor:custom-annotations-setter-width:start
  // anchor:custom-annotations-setter-width:end
  public void setWidth(Integer width) {
    this.mWidth = width;
  }

  // @anchor:annotations-getter-height:start
  // @anchor:annotations-getter-height:end
  // anchor:custom-annotations-getter-height:start
  // anchor:custom-annotations-getter-height:end
  public Integer getHeight() {
    return this.mHeight;
  }

  // @anchor:annotations-setter-height:start
  // @anchor:annotations-setter-height:end
  // anchor:custom-annotations-setter-height:start
  // anchor:custom-annotations-setter-height:end
  public void setHeight(Integer height) {
    this.mHeight = height;
  }

  // @anchor:annotations-getter-clickAction:start
  // @anchor:annotations-getter-clickAction:end
  // anchor:custom-annotations-getter-clickAction:start
  // anchor:custom-annotations-getter-clickAction:end
  public String getClickAction() {
    return this.mClickAction;
  }

  // @anchor:annotations-setter-clickAction:start
  // @anchor:annotations-setter-clickAction:end
  // anchor:custom-annotations-setter-clickAction:start
  // anchor:custom-annotations-setter-clickAction:end
  public void setClickAction(String clickAction) {
    this.mClickAction = clickAction;
  }

  // @anchor:annotations-getter-hooverAction:start
  // @anchor:annotations-getter-hooverAction:end
  // anchor:custom-annotations-getter-hooverAction:start
  // anchor:custom-annotations-getter-hooverAction:end
  public String getHooverAction() {
    return this.mHooverAction;
  }

  // @anchor:annotations-setter-hooverAction:start
  // @anchor:annotations-setter-hooverAction:end
  // anchor:custom-annotations-setter-hooverAction:start
  // anchor:custom-annotations-setter-hooverAction:end
  public void setHooverAction(String hooverAction) {
    this.mHooverAction = hooverAction;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
