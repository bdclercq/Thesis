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

public class DataAccessFindByTargetEqDetails implements DataAccessFindDetails {

  private static final long serialVersionUID = 1L;

  /*========== member fields ==========*/

  // anchor:instance-variables:start
  private String mTarget;
  // anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DataAccessFindByTargetEqDetails() {
    // anchor:default-constructor-initialization:start
    // anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Getters and Setters ==========*/

  // anchor:setters:start
  public void setTarget(String target) {
    this.mTarget = target;
  }
  // anchor:setters:end

  // anchor:getters:start
  public String getTarget() {
    return this.mTarget;
  }
  // anchor:getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
