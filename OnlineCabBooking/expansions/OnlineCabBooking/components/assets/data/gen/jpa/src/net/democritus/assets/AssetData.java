package net.democritus.assets;

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
 * Persistency pojo class for the entity Asset,
 */

@Entity(name=AssetData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Asset", schema="ASSETS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=AssetData.QUERY_FINDALL, query="select o FROM " + AssetData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class AssetData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.assets.Asset";
  public static final String QUERY_FINDALL = "net.democritus.assets.Asset.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mType;
  private Long mFileAsset;
  private Long mInternalAsset;
  private String mContentType;
  private Long mByteSize;
  private String mFileId;
  private Boolean mComplete;
  private Long mExternalAsset;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public AssetData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public AssetData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String type
      , Long fileAsset
      , Long internalAsset
      , String contentType
      , Long byteSize
      , String fileId
      , Boolean complete
      , Long externalAsset
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mType = type;
    this.mFileAsset = fileAsset;
    this.mInternalAsset = internalAsset;
    this.mContentType = contentType;
    this.mByteSize = byteSize;
    this.mFileId = fileId;
    this.mComplete = complete;
    this.mExternalAsset = externalAsset;
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

  // @anchor:annotations-getter-type:start
  // @anchor:annotations-getter-type:end
  // anchor:custom-annotations-getter-type:start
  // anchor:custom-annotations-getter-type:end
  public String getType() {
    return this.mType;
  }

  // @anchor:annotations-setter-type:start
  // @anchor:annotations-setter-type:end
  // anchor:custom-annotations-setter-type:start
  // anchor:custom-annotations-setter-type:end
  public void setType(String type) {
    this.mType = type;
  }

  // @anchor:annotations-getter-fileAsset:start
  @Column(name="fileAsset_id")
  // @anchor:annotations-getter-fileAsset:end
  // anchor:custom-annotations-getter-fileAsset:start
  // anchor:custom-annotations-getter-fileAsset:end
  public Long getFileAsset() {
    return this.mFileAsset;
  }

  // @anchor:annotations-setter-fileAsset:start
  // @anchor:annotations-setter-fileAsset:end
  // anchor:custom-annotations-setter-fileAsset:start
  // anchor:custom-annotations-setter-fileAsset:end
  public void setFileAsset(Long fileAsset) {
    this.mFileAsset = fileAsset;
  }

  // @anchor:annotations-getter-internalAsset:start
  @Column(name="internalAsset_id")
  // @anchor:annotations-getter-internalAsset:end
  // anchor:custom-annotations-getter-internalAsset:start
  // anchor:custom-annotations-getter-internalAsset:end
  public Long getInternalAsset() {
    return this.mInternalAsset;
  }

  // @anchor:annotations-setter-internalAsset:start
  // @anchor:annotations-setter-internalAsset:end
  // anchor:custom-annotations-setter-internalAsset:start
  // anchor:custom-annotations-setter-internalAsset:end
  public void setInternalAsset(Long internalAsset) {
    this.mInternalAsset = internalAsset;
  }

  // @anchor:annotations-getter-contentType:start
  // @anchor:annotations-getter-contentType:end
  // anchor:custom-annotations-getter-contentType:start
  // anchor:custom-annotations-getter-contentType:end
  public String getContentType() {
    return this.mContentType;
  }

  // @anchor:annotations-setter-contentType:start
  // @anchor:annotations-setter-contentType:end
  // anchor:custom-annotations-setter-contentType:start
  // anchor:custom-annotations-setter-contentType:end
  public void setContentType(String contentType) {
    this.mContentType = contentType;
  }

  // @anchor:annotations-getter-byteSize:start
  // @anchor:annotations-getter-byteSize:end
  // anchor:custom-annotations-getter-byteSize:start
  // anchor:custom-annotations-getter-byteSize:end
  public Long getByteSize() {
    return this.mByteSize;
  }

  // @anchor:annotations-setter-byteSize:start
  // @anchor:annotations-setter-byteSize:end
  // anchor:custom-annotations-setter-byteSize:start
  // anchor:custom-annotations-setter-byteSize:end
  public void setByteSize(Long byteSize) {
    this.mByteSize = byteSize;
  }

  // @anchor:annotations-getter-fileId:start
  // @anchor:annotations-getter-fileId:end
  // anchor:custom-annotations-getter-fileId:start
  // anchor:custom-annotations-getter-fileId:end
  public String getFileId() {
    return this.mFileId;
  }

  // @anchor:annotations-setter-fileId:start
  // @anchor:annotations-setter-fileId:end
  // anchor:custom-annotations-setter-fileId:start
  // anchor:custom-annotations-setter-fileId:end
  public void setFileId(String fileId) {
    this.mFileId = fileId;
  }

  // @anchor:annotations-getter-complete:start
  // @anchor:annotations-getter-complete:end
  // anchor:custom-annotations-getter-complete:start
  // anchor:custom-annotations-getter-complete:end
  public Boolean getComplete() {
    return this.mComplete;
  }

  // @anchor:annotations-setter-complete:start
  // @anchor:annotations-setter-complete:end
  // anchor:custom-annotations-setter-complete:start
  // anchor:custom-annotations-setter-complete:end
  public void setComplete(Boolean complete) {
    this.mComplete = complete;
  }

  // @anchor:annotations-getter-externalAsset:start
  @Column(name="externalAsset_id")
  // @anchor:annotations-getter-externalAsset:end
  // anchor:custom-annotations-getter-externalAsset:start
  // anchor:custom-annotations-getter-externalAsset:end
  public Long getExternalAsset() {
    return this.mExternalAsset;
  }

  // @anchor:annotations-setter-externalAsset:start
  // @anchor:annotations-setter-externalAsset:end
  // anchor:custom-annotations-setter-externalAsset:start
  // anchor:custom-annotations-setter-externalAsset:end
  public void setExternalAsset(Long externalAsset) {
    this.mExternalAsset = externalAsset;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
