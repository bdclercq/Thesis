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
 * Persistency pojo class for the entity ExternalAsset,
 */

@Entity(name=ExternalAssetData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="ExternalAsset", schema="ASSETS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=ExternalAssetData.QUERY_FINDALL, query="select o FROM " + ExternalAssetData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class ExternalAssetData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.assets.ExternalAsset";
  public static final String QUERY_FINDALL = "net.democritus.assets.ExternalAsset.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mUri;
  private Long mByteSize;
  private String mContentType;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ExternalAssetData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ExternalAssetData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String uri
      , Long byteSize
      , String contentType
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mUri = uri;
    this.mByteSize = byteSize;
    this.mContentType = contentType;
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
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
