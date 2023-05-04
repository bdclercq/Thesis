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
 * Transport detailed class for the entity bean EngineNodeService,
 */

public class EngineNodeServiceInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private String mStatus;
  private DataRef mEngineNode;
  private DataRef mEngineService;
  private Date mLastRunAt;
  private Date mNextRun;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineNodeServiceInfo() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mStatus = "";
    this.mEngineNode = DataRef.withId(0L);
    this.mEngineService = DataRef.withId(0L);
    this.mLastRunAt = new Date();
    this.mNextRun = new Date();
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineNodeServiceInfo(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , String status
      , DataRef engineNode
      , DataRef engineService
      , Date lastRunAt
      , Date nextRun
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mStatus = status;
    this.mEngineNode = engineNode;
    this.mEngineService = engineService;
    this.mLastRunAt = lastRunAt;
    this.mNextRun = nextRun;
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
  public String getStatus() {
    return this.mStatus;
  }

  public void setStatus(String status) {
    this.mStatus = status;
  }

  public EngineNodeServiceState getStatusAsEnum() {
    return EngineNodeServiceState.getEngineNodeServiceState(this.mStatus);
  }

  public void setStatusEnum(EngineNodeServiceState status) {
    if (status != EngineNodeServiceState.NOT_MAPPED) {
      this.mStatus = status.getStatus();
    } else {
      throw new IllegalArgumentException("Cannot set unmapped status");
    }
  }

  public DataRef getEngineNode() {
    return this.mEngineNode;
  }

  public void setEngineNode(DataRef engineNode) {
    this.mEngineNode = engineNode;
  }

  public DataRef getEngineService() {
    return this.mEngineService;
  }

  public void setEngineService(DataRef engineService) {
    this.mEngineService = engineService;
  }

  public Date getLastRunAt() {
    return this.mLastRunAt;
  }

  public void setLastRunAt(Date lastRunAt) {
    this.mLastRunAt = lastRunAt;
  }

  public Date getNextRun() {
    return this.mNextRun;
  }

  public void setNextRun(Date nextRun) {
    this.mNextRun = nextRun;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("Status");
    fieldOrder.add("EngineNode");
    fieldOrder.add("EngineService");
    fieldOrder.add("LastRunAt");
    fieldOrder.add("NextRun");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.wfe", "EngineNodeService");
  }
}
