package net.democritus.acl;

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
 * Transport detailed class for the entity bean DataAccess,
 */

public class DataAccessInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private DataRef mForProfile;
  private String mElement;
  private String mTarget;
  private String mFunctionality;
  private String mAuthorized;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DataAccessInfo() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mForProfile = DataRef.withId(0L);
    this.mElement = "";
    this.mTarget = "";
    this.mFunctionality = "";
    this.mAuthorized = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public DataAccessInfo(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , DataRef forProfile
      , String element
      , String target
      , String functionality
      , String authorized
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mForProfile = forProfile;
    this.mElement = element;
    this.mTarget = target;
    this.mFunctionality = functionality;
    this.mAuthorized = authorized;
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
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("ForProfile");
    fieldOrder.add("Element");
    fieldOrder.add("Target");
    fieldOrder.add("Functionality");
    fieldOrder.add("Authorized");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.acl", "DataAccess");
  }
}
