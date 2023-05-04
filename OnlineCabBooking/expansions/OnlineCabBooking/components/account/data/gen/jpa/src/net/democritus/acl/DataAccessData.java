package net.democritus.acl;

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
import net.democritus.usr.UserGroupData;
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
 * Persistency pojo class for the entity DataAccess,
 */

@Entity(name=DataAccessData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="DataAccess", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=DataAccessData.QUERY_FINDALL, query="select o FROM " + DataAccessData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class DataAccessData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.acl.DataAccess";
  public static final String QUERY_FINDALL = "net.democritus.acl.DataAccess.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Long mForProfile;
  private Long mForUser;
  private Collection<UserGroupData> mUserGroups;
  private String mElement;
  private String mTarget;
  private String mFunctionality;
  private String mAuthorized;
  private Date mLastModifiedAt;
  private Date mEnteredAt;
  private String mDisabled;
  private Long mForUserGroup;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DataAccessData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public DataAccessData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Long forProfile
      , Long forUser
      , Collection<UserGroupData> userGroups
      , String element
      , String target
      , String functionality
      , String authorized
      , Date lastModifiedAt
      , Date enteredAt
      , String disabled
      , Long forUserGroup
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mForProfile = forProfile;
    this.mForUser = forUser;
    this.mUserGroups = userGroups;
    this.mElement = element;
    this.mTarget = target;
    this.mFunctionality = functionality;
    this.mAuthorized = authorized;
    this.mLastModifiedAt = lastModifiedAt;
    this.mEnteredAt = enteredAt;
    this.mDisabled = disabled;
    this.mForUserGroup = forUserGroup;
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
  // @anchor:annotations-getter-forProfile:start
  @Column(name="forProfile_id")
  // @anchor:annotations-getter-forProfile:end
  // anchor:custom-annotations-getter-forProfile:start
  // anchor:custom-annotations-getter-forProfile:end
  public Long getForProfile() {
    return this.mForProfile;
  }

  // @anchor:annotations-setter-forProfile:start
  // @anchor:annotations-setter-forProfile:end
  // anchor:custom-annotations-setter-forProfile:start
  // anchor:custom-annotations-setter-forProfile:end
  public void setForProfile(Long forProfile) {
    this.mForProfile = forProfile;
  }

  // @anchor:annotations-getter-forUser:start
  @Column(name="forUser_id")
  // @anchor:annotations-getter-forUser:end
  // anchor:custom-annotations-getter-forUser:start
  // anchor:custom-annotations-getter-forUser:end
  public Long getForUser() {
    return this.mForUser;
  }

  // @anchor:annotations-setter-forUser:start
  // @anchor:annotations-setter-forUser:end
  // anchor:custom-annotations-setter-forUser:start
  // anchor:custom-annotations-setter-forUser:end
  public void setForUser(Long forUser) {
    this.mForUser = forUser;
  }

  // @anchor:annotations-getter-userGroups:start
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="dataAccess_userGroups",
    schema="ACCOUNT",
    joinColumns=@JoinColumn(name="dataAccess_id"),
    inverseJoinColumns=@JoinColumn(name="userGroup_id"))
  // @anchor:annotations-getter-userGroups:end
  // anchor:custom-annotations-getter-userGroups:start
  // anchor:custom-annotations-getter-userGroups:end
  public Collection<UserGroupData> getUserGroups() {
    return this.mUserGroups;
  }

  // @anchor:annotations-setter-userGroups:start
  // @anchor:annotations-setter-userGroups:end
  // anchor:custom-annotations-setter-userGroups:start
  // anchor:custom-annotations-setter-userGroups:end
  public void setUserGroups(final Collection<UserGroupData> userGroups) {
    this.mUserGroups = userGroups;
  }

  // @anchor:annotations-getter-element:start
  // @anchor:annotations-getter-element:end
  // anchor:custom-annotations-getter-element:start
  // anchor:custom-annotations-getter-element:end
  public String getElement() {
    return this.mElement;
  }

  // @anchor:annotations-setter-element:start
  // @anchor:annotations-setter-element:end
  // anchor:custom-annotations-setter-element:start
  // anchor:custom-annotations-setter-element:end
  public void setElement(String element) {
    this.mElement = element;
  }

  // @anchor:annotations-getter-target:start
  // @anchor:annotations-getter-target:end
  // anchor:custom-annotations-getter-target:start
  // anchor:custom-annotations-getter-target:end
  public String getTarget() {
    return this.mTarget;
  }

  // @anchor:annotations-setter-target:start
  // @anchor:annotations-setter-target:end
  // anchor:custom-annotations-setter-target:start
  // anchor:custom-annotations-setter-target:end
  public void setTarget(String target) {
    this.mTarget = target;
  }

  // @anchor:annotations-getter-functionality:start
  // @anchor:annotations-getter-functionality:end
  // anchor:custom-annotations-getter-functionality:start
  // anchor:custom-annotations-getter-functionality:end
  public String getFunctionality() {
    return this.mFunctionality;
  }

  // @anchor:annotations-setter-functionality:start
  // @anchor:annotations-setter-functionality:end
  // anchor:custom-annotations-setter-functionality:start
  // anchor:custom-annotations-setter-functionality:end
  public void setFunctionality(String functionality) {
    this.mFunctionality = functionality;
  }

  // @anchor:annotations-getter-authorized:start
  // @anchor:annotations-getter-authorized:end
  // anchor:custom-annotations-getter-authorized:start
  // anchor:custom-annotations-getter-authorized:end
  public String getAuthorized() {
    return this.mAuthorized;
  }

  // @anchor:annotations-setter-authorized:start
  // @anchor:annotations-setter-authorized:end
  // anchor:custom-annotations-setter-authorized:start
  // anchor:custom-annotations-setter-authorized:end
  public void setAuthorized(String authorized) {
    this.mAuthorized = authorized;
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

  // @anchor:annotations-getter-forUserGroup:start
  @Column(name="forUserGroup_id")
  // @anchor:annotations-getter-forUserGroup:end
  // anchor:custom-annotations-getter-forUserGroup:start
  // anchor:custom-annotations-getter-forUserGroup:end
  public Long getForUserGroup() {
    return this.mForUserGroup;
  }

  // @anchor:annotations-setter-forUserGroup:start
  // @anchor:annotations-setter-forUserGroup:end
  // anchor:custom-annotations-setter-forUserGroup:start
  // anchor:custom-annotations-setter-forUserGroup:end
  public void setForUserGroup(Long forUserGroup) {
    this.mForUserGroup = forUserGroup;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
