package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.List;
import java.util.ArrayList;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;

// anchor:valuetype-imports:start
import java.util.Date;
// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineNodeFindByMasterEq_LastActiveLtDetails implements EngineNodeFindDetails {

  private static final long serialVersionUID = 1L;

  /*========== member fields ==========*/

  // anchor:instance-variables:start
  private Boolean mMaster;
  private Date mLastActive;
  // anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineNodeFindByMasterEq_LastActiveLtDetails() {
    // anchor:default-constructor-initialization:start
    // anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Getters and Setters ==========*/

  // anchor:setters:start
  public void setMaster(Boolean master) {
    this.mMaster = master;
  }

  public void setLastActive(Date lastActive) {
    this.mLastActive = lastActive;
  }
  // anchor:setters:end

  // anchor:getters:start
  public Boolean getMaster() {
    return this.mMaster;
  }

  public Date getLastActive() {
    return this.mLastActive;
  }
  // anchor:getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
