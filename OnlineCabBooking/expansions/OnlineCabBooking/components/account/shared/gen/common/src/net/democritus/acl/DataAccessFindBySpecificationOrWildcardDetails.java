package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.List;
import java.util.ArrayList;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class DataAccessFindBySpecificationOrWildcardDetails implements DataAccessFindDetails {

  private static final long serialVersionUID = 1L;

  /*========== member fields ==========*/

  // anchor:instance-variables:start
  private DataRef mForProfile;
  private DataRef mForUserGroup;
  private DataRef mForUser;
  private String mElement;
  private String mTarget;
  private String mFunctionality;
  // anchor:instance-variables:end

  // anchor:custom-variables:start
  private String mComponent;
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DataAccessFindBySpecificationOrWildcardDetails() {
    // anchor:default-constructor-initialization:start
    // anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Getters and Setters ==========*/

  // anchor:setters:start
  public void setForProfile(DataRef forProfile) {
    this.mForProfile = forProfile;
  }

  public void setForUserGroup(DataRef forUserGroup) {
    this.mForUserGroup = forUserGroup;
  }

  public void setForUser(DataRef forUser) {
    this.mForUser = forUser;
  }

  public void setElement(String element) {
    this.mElement = element;
  }

  public void setTarget(String target) {
    this.mTarget = target;
  }

  public void setFunctionality(String functionality) {
    this.mFunctionality = functionality;
  }
  // anchor:setters:end

  // anchor:getters:start
  public DataRef getForProfile() {
    return this.mForProfile;
  }

  public DataRef getForUserGroup() {
    return this.mForUserGroup;
  }

  public DataRef getForUser() {
    return this.mForUser;
  }

  public String getElement() {
    return this.mElement;
  }

  public String getTarget() {
    return this.mTarget;
  }

  public String getFunctionality() {
    return this.mFunctionality;
  }
  // anchor:getters:end

  // anchor:custom-methods:start
  public String getComponent() {
    return mComponent;
  }

  public void setComponent(String component) {
    this.mComponent = component;
  }
  // anchor:custom-methods:end
}
