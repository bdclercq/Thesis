package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.List;
import java.util.ArrayList;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserFindByNameEq_ProfileEqDetails implements UserFindDetails {

  private static final long serialVersionUID = 1L;

  /*========== member fields ==========*/

  // anchor:instance-variables:start
  private String mName;
  private DataRef mProfile;
  // anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public UserFindByNameEq_ProfileEqDetails() {
    // anchor:default-constructor-initialization:start
    // anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Getters and Setters ==========*/

  // anchor:setters:start
  public void setName(String name) {
    this.mName = name;
  }

  public void setProfile(DataRef profile) {
    this.mProfile = profile;
  }
  // anchor:setters:end

  // anchor:getters:start
  public String getName() {
    return this.mName;
  }

  public DataRef getProfile() {
    return this.mProfile;
  }
  // anchor:getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}