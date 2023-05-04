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
 * Transport detailed class for the entity bean UserGroup,
 */

public class UserGroupDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mType;
  private Date mLastModifiedAt;
  private Date mEnteredAt;
  private String mDisabled;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public UserGroupDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mType = "";
    this.mLastModifiedAt = new Date();
    this.mEnteredAt = new Date();
    this.mDisabled = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public UserGroupDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String type
      , Date lastModifiedAt
      , Date enteredAt
      , String disabled
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mType = type;
    this.mLastModifiedAt = lastModifiedAt;
    this.mEnteredAt = enteredAt;
    this.mDisabled = disabled;
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
    return "UserGroup";
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

  public String getType() {
    return this.mType;
  }

  public void setType(String type) {
    this.mType = type;
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
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.usr", "UserGroup");
  }
}
