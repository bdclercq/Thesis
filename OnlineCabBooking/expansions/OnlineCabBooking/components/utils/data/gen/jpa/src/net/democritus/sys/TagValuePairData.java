package net.democritus.sys;

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
 * Persistency pojo class for the entity TagValuePair,
 */

@Entity(name=TagValuePairData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="TagValuePair", schema="UTILS")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=TagValuePairData.QUERY_FINDALL, query="select o FROM " + TagValuePairData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class TagValuePairData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.sys.TagValuePair";
  public static final String QUERY_FINDALL = "net.democritus.sys.TagValuePair.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mTag;
  private String mValue;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TagValuePairData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TagValuePairData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String tag
      , String value
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mTag = tag;
    this.mValue = value;
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
  @Transient
  public String getName() {
    return this.getId() == null ? "" : this.getId().toString();
  }
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-tag:start
  // @anchor:annotations-getter-tag:end
  // anchor:custom-annotations-getter-tag:start
  // anchor:custom-annotations-getter-tag:end
  public String getTag() {
    return this.mTag;
  }

  // @anchor:annotations-setter-tag:start
  // @anchor:annotations-setter-tag:end
  // anchor:custom-annotations-setter-tag:start
  // anchor:custom-annotations-setter-tag:end
  public void setTag(String tag) {
    this.mTag = tag;
  }

  // @anchor:annotations-getter-value:start
  // @anchor:annotations-getter-value:end
  // anchor:custom-annotations-getter-value:start
  // anchor:custom-annotations-getter-value:end
  public String getValue() {
    return this.mValue;
  }

  // @anchor:annotations-setter-value:start
  // @anchor:annotations-setter-value:end
  // anchor:custom-annotations-setter-value:start
  // anchor:custom-annotations-setter-value:end
  public void setValue(String value) {
    this.mValue = value;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
