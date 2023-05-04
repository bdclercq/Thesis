package net.democritus.acl;

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
 * Transport detailed class for the entity bean DataAccess,
 */

public class DataAccessDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private DataRef mForProfile;
  private DataRef mForUser;
  private List<DataRef> mUserGroups;
  private String mElement;
  private String mTarget;
  private String mFunctionality;
  private String mAuthorized;
  private Date mLastModifiedAt;
  private Date mEnteredAt;
  private String mDisabled;
  private DataRef mForUserGroup;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DataAccessDetails() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mForProfile = DataRef.withId(0L);
    this.mForUser = DataRef.withId(0L);
    this.mUserGroups = new ArrayList<DataRef>();
    this.mElement = "";
    this.mTarget = "";
    this.mFunctionality = "";
    this.mAuthorized = "";
    this.mLastModifiedAt = new Date();
    this.mEnteredAt = new Date();
    this.mDisabled = "";
    this.mForUserGroup = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public DataAccessDetails(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , DataRef forProfile
      , DataRef forUser
      , List<DataRef> userGroups
      , String element
      , String target
      , String functionality
      , String authorized
      , Date lastModifiedAt
      , Date enteredAt
      , String disabled
      , DataRef forUserGroup
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
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
    return "DataAccess";
  }

  public String getElementPackage() {
    return "net.democritus.acl";
  }
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  // anchor:getters-setters:start
  public DataRef getForProfile() {
    return this.mForProfile;
  }

  public void setForProfile(DataRef forProfile) {
    this.mForProfile = forProfile;
  }

  public DataRef getForUser() {
    return this.mForUser;
  }

  public void setForUser(DataRef forUser) {
    this.mForUser = forUser;
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

  public String getElement() {
    return this.mElement;
  }

  public void setElement(String element) {
    this.mElement = element;
  }

  public String getTarget() {
    return this.mTarget;
  }

  public void setTarget(String target) {
    this.mTarget = target;
  }

  public String getFunctionality() {
    return this.mFunctionality;
  }

  public void setFunctionality(String functionality) {
    this.mFunctionality = functionality;
  }

  public String getAuthorized() {
    return this.mAuthorized;
  }

  public void setAuthorized(String authorized) {
    this.mAuthorized = authorized;
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

  public DataRef getForUserGroup() {
    return this.mForUserGroup;
  }

  public void setForUserGroup(DataRef forUserGroup) {
    this.mForUserGroup = forUserGroup;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.acl", "DataAccess");
  }
}
