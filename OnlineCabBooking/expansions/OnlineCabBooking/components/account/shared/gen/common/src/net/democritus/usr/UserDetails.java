package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.io.Serializable;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;
import java.util.ArrayList;
import java.util.List;

// anchor:valuetype-imports:start
import java.util.Date;
// anchor:valuetype-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean User,
 */

public class UserDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
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
  private DataRef mAccount;
  private net.democritus.usr.AccountDetails mAccountDetails;
  private DataRef mProfile;
  private net.democritus.usr.ProfileDetails mProfileDetails;
  private List<DataRef> mUserGroups;
  private String mEncryptedPassword;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public UserDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mPassword = "";
    this.mFullName = "";
    this.mEmail = "";
    this.mMobile = "";
    this.mLanguage = "";
    this.mFirstName = "";
    this.mLastName = "";
    this.mPersNr = "";
    this.mLastModifiedAt = new Date();
    this.mEnteredAt = new Date();
    this.mDisabled = "";
    this.mTimeout = null;
    this.mAccount = DataRef.withId(0L);
    this.mAccountDetails = new net.democritus.usr.AccountDetails();
    this.mProfile = DataRef.withId(0L);
    this.mProfileDetails = new net.democritus.usr.ProfileDetails();
    this.mUserGroups = new ArrayList<DataRef>();
    this.mEncryptedPassword = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public UserDetails(Long id
      // anchor:detailed-constructor-parameters:start
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
      , DataRef account
      , net.democritus.usr.AccountDetails accountDetails
      , DataRef profile
      , net.democritus.usr.ProfileDetails profileDetails
      , List<DataRef> userGroups
      , String encryptedPassword
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
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
    this.mAccountDetails = accountDetails;
    this.mProfile = profile;
    this.mProfileDetails = profileDetails;
    this.mUserGroups = userGroups;
    this.mEncryptedPassword = encryptedPassword;
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
    return "User";
  }

  public String getElementPackage() {
    return "net.democritus.usr";
  }
  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getPassword() {
    return this.mPassword;
  }

  public void setPassword(String password) {
    this.mPassword = password;
  }

  public String getFullName() {
    return this.mFullName;
  }

  public void setFullName(String fullName) {
    this.mFullName = fullName;
  }

  public String getEmail() {
    return this.mEmail;
  }

  public void setEmail(String email) {
    this.mEmail = email;
  }

  public String getMobile() {
    return this.mMobile;
  }

  public void setMobile(String mobile) {
    this.mMobile = mobile;
  }

  public String getLanguage() {
    return this.mLanguage;
  }

  public void setLanguage(String language) {
    this.mLanguage = language;
  }

  public String getFirstName() {
    return this.mFirstName;
  }

  public void setFirstName(String firstName) {
    this.mFirstName = firstName;
  }

  public String getLastName() {
    return this.mLastName;
  }

  public void setLastName(String lastName) {
    this.mLastName = lastName;
  }

  public String getPersNr() {
    return this.mPersNr;
  }

  public void setPersNr(String persNr) {
    this.mPersNr = persNr;
  }

  public Date getLastModifiedAt() {
    return this.mLastModifiedAt;
  }

  public void setLastModifiedAt(Date lastModifiedAt) {
    this.mLastModifiedAt = lastModifiedAt;
  }

  public Date getEnteredAt() {
    return this.mEnteredAt;
  }

  public void setEnteredAt(Date enteredAt) {
    this.mEnteredAt = enteredAt;
  }

  public String getDisabled() {
    return this.mDisabled;
  }

  public void setDisabled(String disabled) {
    this.mDisabled = disabled;
  }

  public Integer getTimeout() {
    return this.mTimeout;
  }

  public void setTimeout(Integer timeout) {
    this.mTimeout = timeout;
  }

  public DataRef getAccount() {
    return this.mAccount;
  }

  public void setAccount(DataRef account) {
    this.mAccount = account;
  }

  public void setAccountDetails(net.democritus.usr.AccountDetails accountDetails) {
    this.mAccountDetails = accountDetails;
  }

  public net.democritus.usr.AccountDetails getAccountDetails() {
    return this.mAccountDetails;
  }

  public DataRef getProfile() {
    return this.mProfile;
  }

  public void setProfile(DataRef profile) {
    this.mProfile = profile;
  }

  public void setProfileDetails(net.democritus.usr.ProfileDetails profileDetails) {
    this.mProfileDetails = profileDetails;
  }

  public net.democritus.usr.ProfileDetails getProfileDetails() {
    return this.mProfileDetails;
  }

  public List<DataRef> getUserGroups() {
    return this.mUserGroups;
  }

  public void setUserGroups(List<DataRef> userGroups) {
    this.mUserGroups = userGroups;
  }

  public List<Long> getUserGroupsIds() {
    final List<Long> userGroupsIds = new ArrayList<Long>();
    for (DataRef dataRef : mUserGroups) {
      userGroupsIds.add(dataRef.getId());
    }
    return userGroupsIds;
  }

  public void setUserGroupsIds(String[] userGroupsIds) {
    this.mUserGroups = new ArrayList<DataRef>();
    for (final String string : userGroupsIds) {
      mUserGroups.add(new DataRef(new Long(string), "", "", "", ""));
    }
  }

  public String getEncryptedPassword() {
    return this.mEncryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.mEncryptedPassword = encryptedPassword;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.usr", "User");
  }
}
