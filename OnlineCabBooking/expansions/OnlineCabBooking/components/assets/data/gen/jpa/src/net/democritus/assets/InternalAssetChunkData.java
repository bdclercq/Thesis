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
import net.democritus.assets.InternalAssetData;
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
 * Persistency pojo class for the entity InternalAssetChunk,
 */

@Entity(name=InternalAssetChunkData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="InternalAssetChunk", schema="ASSETS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=InternalAssetChunkData.QUERY_FINDALL, query="select o FROM " + InternalAssetChunkData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class InternalAssetChunkData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.assets.InternalAssetChunk";
  public static final String QUERY_FINDALL = "net.democritus.assets.InternalAssetChunk.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private byte[] mContent;
  private InternalAssetData mInternalAsset;
  private Integer mByteSize;
  private Integer mPosition;
  private Boolean mIsLast;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public InternalAssetChunkData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public InternalAssetChunkData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , byte[] content
      , InternalAssetData internalAsset
      , Integer byteSize
      , Integer position
      , Boolean isLast
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mContent = content;
    this.mInternalAsset = internalAsset;
    this.mByteSize = byteSize;
    this.mPosition = position;
    this.mIsLast = isLast;
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
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-content:start
  // @anchor:annotations-getter-content:end
  // anchor:custom-annotations-getter-content:start
  // anchor:custom-annotations-getter-content:end
  public byte[] getContent() {
    return this.mContent;
  }

  // @anchor:annotations-setter-content:start
  // @anchor:annotations-setter-content:end
  // anchor:custom-annotations-setter-content:start
  // anchor:custom-annotations-setter-content:end
  public void setContent(byte[] content) {
    this.mContent = content;
  }

  // @anchor:annotations-getter-internalAsset:start
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="internalAsset_id")
  // @anchor:annotations-getter-internalAsset:end
  // anchor:custom-annotations-getter-internalAsset:start
  // anchor:custom-annotations-getter-internalAsset:end
  public InternalAssetData getInternalAsset() {
    return this.mInternalAsset;
  }

  // @anchor:annotations-setter-internalAsset:start
  // @anchor:annotations-setter-internalAsset:end
  // anchor:custom-annotations-setter-internalAsset:start
  // anchor:custom-annotations-setter-internalAsset:end
  public void setInternalAsset(final InternalAssetData internalAsset) {
    this.mInternalAsset = internalAsset;
  }

  // @anchor:annotations-getter-byteSize:start
  // @anchor:annotations-getter-byteSize:end
  // anchor:custom-annotations-getter-byteSize:start
  // anchor:custom-annotations-getter-byteSize:end
  public Integer getByteSize() {
    return this.mByteSize;
  }

  // @anchor:annotations-setter-byteSize:start
  // @anchor:annotations-setter-byteSize:end
  // anchor:custom-annotations-setter-byteSize:start
  // anchor:custom-annotations-setter-byteSize:end
  public void setByteSize(Integer byteSize) {
    this.mByteSize = byteSize;
  }

  // @anchor:annotations-getter-position:start
  // @anchor:annotations-getter-position:end
  // anchor:custom-annotations-getter-position:start
  // anchor:custom-annotations-getter-position:end
  public Integer getPosition() {
    return this.mPosition;
  }

  // @anchor:annotations-setter-position:start
  // @anchor:annotations-setter-position:end
  // anchor:custom-annotations-setter-position:start
  // anchor:custom-annotations-setter-position:end
  public void setPosition(Integer position) {
    this.mPosition = position;
  }

  // @anchor:annotations-getter-isLast:start
  // @anchor:annotations-getter-isLast:end
  // anchor:custom-annotations-getter-isLast:start
  // anchor:custom-annotations-getter-isLast:end
  public Boolean getIsLast() {
    return this.mIsLast;
  }

  // @anchor:annotations-setter-isLast:start
  // @anchor:annotations-setter-isLast:end
  // anchor:custom-annotations-setter-isLast:start
  // anchor:custom-annotations-setter-isLast:end
  public void setIsLast(Boolean isLast) {
    this.mIsLast = isLast;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
