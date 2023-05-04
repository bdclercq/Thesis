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
 * Persistency pojo class for the entity User,
 */

@Entity(name=UserData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="User", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=UserData.QUERY_FINDALL, query="select o FROM " + UserData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class UserData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "net.democritus.usr.User";
  public static final String QUERY_FINDALL = "net.democritus.usr.User.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mPassword;
  private String mFullName;
  private String mEmail;
  private String mMobile;
  private String mLanguage;
  private String mFirstName;
  private String mLastName;
  private String mPersNr;
  private Date mLastModifiedAt;
  private Date mEnteredAt;
  private String mDisabled;
  private Integer mTimeout;
  private Long mAccount;
  private Long mProfile;
  private Collection<UserGroupData> mUserGroups;
  private String mEncryptedPassword;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public UserData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public UserData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String password
      , String fullName
      , String email
      , String mobile
      , String language
      , String firstName
      , String lastName
      , String persNr
      , Date lastModifiedAt
      , Date enteredAt
      , String disabled
      , Integer timeout
      , Long account
      , Long profile
      , Collection<UserGroupData> userGroups
      , String encryptedPassword
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mPassword = password;
    this.mFullName = fullName;
    this.mEmail = email;
    this.mMobile = mobile;
    this.mLanguage = language;
    this.mFirstName = firstName;
    this.mLastName = lastName;
    this.mPersNr = persNr;
    this.mLastModifiedAt = lastModifiedAt;
    this.mEnteredAt = enteredAt;
    this.mDisabled = disabled;
    this.mTimeout = timeout;
    this.mAccount = account;
    this.mProfile = profile;
    this.mUserGroups = userGroups;
    this.mEncryptedPassword = encryptedPassword;
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

  // @anchor:annotations-getter-password:start
  // @anchor:annotations-getter-password:end
  // anchor:custom-annotations-getter-password:start
  // anchor:custom-annotations-getter-password:end
  public String getPassword() {
    return this.mPassword;
  }

  // @anchor:annotations-setter-password:start
  // @anchor:annotations-setter-password:end
  // anchor:custom-annotations-setter-password:start
  // anchor:custom-annotations-setter-password:end
  public void setPassword(String password) {
    this.mPassword = password;
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

  // @anchor:annotations-getter-email:start
  // @anchor:annotations-getter-email:end
  // anchor:custom-annotations-getter-email:start
  // anchor:custom-annotations-getter-email:end
  public String getEmail() {
    return this.mEmail;
  }

  // @anchor:annotations-setter-email:start
  // @anchor:annotations-setter-email:end
  // anchor:custom-annotations-setter-email:start
  // anchor:custom-annotations-setter-email:end
  public void setEmail(String email) {
    this.mEmail = email;
  }

  // @anchor:annotations-getter-mobile:start
  // @anchor:annotations-getter-mobile:end
  // anchor:custom-annotations-getter-mobile:start
  // anchor:custom-annotations-getter-mobile:end
  public String getMobile() {
    return this.mMobile;
  }

  // @anchor:annotations-setter-mobile:start
  // @anchor:annotations-setter-mobile:end
  // anchor:custom-annotations-setter-mobile:start
  // anchor:custom-annotations-setter-mobile:end
  public void setMobile(String mobile) {
    this.mMobile = mobile;
  }

  // @anchor:annotations-getter-language:start
  // @anchor:annotations-getter-language:end
  // anchor:custom-annotations-getter-language:start
  // anchor:custom-annotations-getter-language:end
  public String getLanguage() {
    return this.mLanguage;
  }

  // @anchor:annotations-setter-language:start
  // @anchor:annotations-setter-language:end
  // anchor:custom-annotations-setter-language:start
  // anchor:custom-annotations-setter-language:end
  public void setLanguage(String language) {
    this.mLanguage = language;
  }

  // @anchor:annotations-getter-firstName:start
  // @anchor:annotations-getter-firstName:end
  // anchor:custom-annotations-getter-firstName:start
  // anchor:custom-annotations-getter-firstName:end
  public String getFirstName() {
    return this.mFirstName;
  }

  // @anchor:annotations-setter-firstName:start
  // @anchor:annotations-setter-firstName:end
  // anchor:custom-annotations-setter-firstName:start
  // anchor:custom-annotations-setter-firstName:end
  public void setFirstName(String firstName) {
    this.mFirstName = firstName;
  }

  // @anchor:annotations-getter-lastName:start
  // @anchor:annotations-getter-lastName:end
  // anchor:custom-annotations-getter-lastName:start
  // anchor:custom-annotations-getter-lastName:end
  public String getLastName() {
    return this.mLastName;
  }

  // @anchor:annotations-setter-lastName:start
  // @anchor:annotations-setter-lastName:end
  // anchor:custom-annotations-setter-lastName:start
  // anchor:custom-annotations-setter-lastName:end
  public void setLastName(String lastName) {
    this.mLastName = lastName;
  }

  // @anchor:annotations-getter-persNr:start
  // @anchor:annotations-getter-persNr:end
  // anchor:custom-annotations-getter-persNr:start
  // anchor:custom-annotations-getter-persNr:end
  public String getPersNr() {
    return this.mPersNr;
  }

  // @anchor:annotations-setter-persNr:start
  // @anchor:annotations-setter-persNr:end
  // anchor:custom-annotations-setter-persNr:start
  // anchor:custom-annotations-setter-persNr:end
  public void setPersNr(String persNr) {
    this.mPersNr = persNr;
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

  // @anchor:annotations-getter-timeout:start
  // @anchor:annotations-getter-timeout:end
  // anchor:custom-annotations-getter-timeout:start
  // anchor:custom-annotations-getter-timeout:end
  public Integer getTimeout() {
    return this.mTimeout;
  }

  // @anchor:annotations-setter-timeout:start
  // @anchor:annotations-setter-timeout:end
  // anchor:custom-annotations-setter-timeout:start
  // anchor:custom-annotations-setter-timeout:end
  public void setTimeout(Integer timeout) {
    this.mTimeout = timeout;
  }

  // @anchor:annotations-getter-account:start
  @Column(name="account_id")
  // @anchor:annotations-getter-account:end
  // anchor:custom-annotations-getter-account:start
  // anchor:custom-annotations-getter-account:end
  public Long getAccount() {
    return this.mAccount;
  }

  // @anchor:annotations-setter-account:start
  // @anchor:annotations-setter-account:end
  // anchor:custom-annotations-setter-account:start
  // anchor:custom-annotations-setter-account:end
  public void setAccount(Long account) {
    this.mAccount = account;
  }

  // @anchor:annotations-getter-profile:start
  @Column(name="profile_id")
  // @anchor:annotations-getter-profile:end
  // anchor:custom-annotations-getter-profile:start
  // anchor:custom-annotations-getter-profile:end
  public Long getProfile() {
    return this.mProfile;
  }

  // @anchor:annotations-setter-profile:start
  // @anchor:annotations-setter-profile:end
  // anchor:custom-annotations-setter-profile:start
  // anchor:custom-annotations-setter-profile:end
  public void setProfile(Long profile) {
    this.mProfile = profile;
  }

  // @anchor:annotations-getter-userGroups:start
  @ManyToMany(fetch=FetchType.LAZY)
  @JoinTable(name="user_userGroups",
    schema="ACCOUNT",
    joinColumns=@JoinColumn(name="user_id"),
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

  // @anchor:annotations-getter-encryptedPassword:start
  // @anchor:annotations-getter-encryptedPassword:end
  // anchor:custom-annotations-getter-encryptedPassword:start
  // anchor:custom-annotations-getter-encryptedPassword:end
  public String getEncryptedPassword() {
    return this.mEncryptedPassword;
  }

  // @anchor:annotations-setter-encryptedPassword:start
  // @anchor:annotations-setter-encryptedPassword:end
  // anchor:custom-annotations-setter-encryptedPassword:start
  // anchor:custom-annotations-setter-encryptedPassword:end
  public void setEncryptedPassword(String encryptedPassword) {
    this.mEncryptedPassword = encryptedPassword;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
