package net.democritus.usr;

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
 * Persistency pojo class for the entity UserGroup,
 */

@Entity(name=UserGroupData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="UserGroup", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=UserGroupData.QUERY_FINDALL, query="select o FROM " + UserGroupData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class UserGroupData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.UserGroup";
  public static final String QUERY_FINDALL = "net.democritus.usr.UserGroup.findAll";
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
  private Date mLastModifiedAt;
  private Date mEnteredAt;
  private String mDisabled;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public UserGroupData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public UserGroupData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String type
      , Date lastModifiedAt
      , Date enteredAt
      , String disabled
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mType = type;
    this.mLastModifiedAt = lastModifiedAt;
    this.mEnteredAt = enteredAt;
    this.mDisabled = disabled;
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

  // @anchor:annotations-getter-lastModifiedAt:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-lastModifiedAt:end
  // anchor:custom-annotations-getter-lastModifiedAt:start
  // anchor:custom-annotations-getter-lastModifiedAt:end
  public Date getLastModifiedAt() {
    return this.mLastModifiedAt;
  }

  // @anchor:annotations-setter-lastModifiedAt:start
  // @anchor:annotations-setter-lastModifiedAt:end
  // anchor:custom-annotations-setter-lastModifiedAt:start
  // anchor:custom-annotations-setter-lastModifiedAt:end
  public void setLastModifiedAt(Date lastModifiedAt) {
    this.mLastModifiedAt = lastModifiedAt;
  }

  // @anchor:annotations-getter-enteredAt:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-enteredAt:end
  // anchor:custom-annotations-getter-enteredAt:start
  // anchor:custom-annotations-getter-enteredAt:end
  public Date getEnteredAt() {
    return this.mEnteredAt;
  }

  // @anchor:annotations-setter-enteredAt:start
  // @anchor:annotations-setter-enteredAt:end
  // anchor:custom-annotations-setter-enteredAt:start
  // anchor:custom-annotations-setter-enteredAt:end
  public void setEnteredAt(Date enteredAt) {
    this.mEnteredAt = enteredAt;
  }

  // @anchor:annotations-getter-disabled:start
  // @anchor:annotations-getter-disabled:end
  // anchor:custom-annotations-getter-disabled:start
  // anchor:custom-annotations-getter-disabled:end
  public String getDisabled() {
    return this.mDisabled;
  }

  // @anchor:annotations-setter-disabled:start
  // @anchor:annotations-setter-disabled:end
  // anchor:custom-annotations-setter-disabled:start
  // anchor:custom-annotations-setter-disabled:end
  public void setDisabled(String disabled) {
    this.mDisabled = disabled;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
