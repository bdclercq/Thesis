package net.democritus.wfe;

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
 * Transport detailed class for the entity bean EngineNode,
 */

public class EngineNodeDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mStatus;
  private String mHostname;
  private Boolean mMaster;
  private Date mLastActive;
  private Date mActiveSince;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineNodeDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mStatus = "";
    this.mHostname = "";
    this.mMaster = Boolean.FALSE;
    this.mLastActive = new Date();
    this.mActiveSince = new Date();
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineNodeDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String status
      , String hostname
      , Boolean master
      , Date lastActive
      , Date activeSince
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mStatus = status;
    this.mHostname = hostname;
    this.mMaster = master;
    this.mLastActive = lastActive;
    this.mActiveSince = activeSince;
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
    return "EngineNode";
  }

  public String getElementPackage() {
    return "net.democritus.wfe";
  }
  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getStatus() {
    return this.mStatus;
  }

  public void setStatus(String status) {
    this.mStatus = status;
  }

  public EngineNodeState getStatusAsEnum() {
    return EngineNodeState.getEngineNodeState(this.mStatus);
  }

  public void setStatusEnum(EngineNodeState status) {
    if (status != EngineNodeState.NOT_MAPPED) {
      this.mStatus = status.getStatus();
    } else {
      throw new IllegalArgumentException("Cannot set unmapped status");
    }
  }

  public String getHostname() {
    return this.mHostname;
  }

  public void setHostname(String hostname) {
    this.mHostname = hostname;
  }

  public Boolean getMaster() {
    return this.mMaster;
  }

  public void setMaster(Boolean master) {
    this.mMaster = master;
  }

  public Date getLastActive() {
    return this.mLastActive;
  }

  public void setLastActive(Date lastActive) {
    this.mLastActive = lastActive;
  }

  public Date getActiveSince() {
    return this.mActiveSince;
  }

  public void setActiveSince(Date activeSince) {
    this.mActiveSince = activeSince;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.wfe", "EngineNode");
  }
}
