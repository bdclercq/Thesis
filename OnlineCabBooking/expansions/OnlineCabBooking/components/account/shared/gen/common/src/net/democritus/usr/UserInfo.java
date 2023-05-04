package net.democritus.usr;

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
 * Transport detailed class for the entity bean User,
 */

public class UserInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mFullName;
  private String mEmail;
  private String mLanguage;
  private DataRef mAccount;
  private net.democritus.usr.AccountDetails mAccountDetails;
  private DataRef mProfile;
  private net.democritus.usr.ProfileDetails mProfileDetails;
  private List<DataRef> mUserGroups;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public UserInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mFullName = "";
    this.mEmail = "";
    this.mLanguage = "";
    this.mAccount = DataRef.withId(0L);
    this.mAccountDetails = new net.democritus.usr.AccountDetails();
    this.mProfile = DataRef.withId(0L);
    this.mProfileDetails = new net.democritus.usr.ProfileDetails();
    this.mUserGroups = new ArrayList<DataRef>();
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public UserInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String fullName
      , String email
      , String language
      , DataRef account
      , net.democritus.usr.AccountDetails accountDetails
      , DataRef profile
      , net.democritus.usr.ProfileDetails profileDetails
      , List<DataRef> userGroups
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mFullName = fullName;
    this.mEmail = email;
    this.mLanguage = language;
    this.mAccount = account;
    this.mAccountDetails = accountDetails;
    this.mProfile = profile;
    this.mProfileDetails = profileDetails;
    this.mUserGroups = userGroups;
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

  public String getEmail() {
    return this.mEmail;
  }

  public void setEmail(String email) {
    this.mEmail = email;
  }

  public String getLanguage() {
    return this.mLanguage;
  }

  public void setLanguage(String language) {
    this.mLanguage = language;
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
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("Name");
    fieldOrder.add("FullName");
    fieldOrder.add("Email");
    fieldOrder.add("Language");
    fieldOrder.add("Account");
    fieldOrder.add("Profile");
    fieldOrder.add("UserGroups");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.usr", "User");
  }
}
