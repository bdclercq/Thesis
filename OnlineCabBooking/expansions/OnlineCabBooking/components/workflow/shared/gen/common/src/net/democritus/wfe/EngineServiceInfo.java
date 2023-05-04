package net.democritus.wfe;

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
 * Transport detailed class for the entity bean EngineService,
 */

public class EngineServiceInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mStatus;
  private Integer mWaitTime;
  private DataRef mWorkflow;
  private DataRef mTimeWindowGroup;
  private Integer mBatchSize;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineServiceInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mStatus = "";
    this.mWaitTime = null;
    this.mWorkflow = DataRef.withId(0L);
    this.mTimeWindowGroup = DataRef.withId(0L);
    this.mBatchSize = null;
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineServiceInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String status
      , Integer waitTime
      , DataRef workflow
      , DataRef timeWindowGroup
      , Integer batchSize
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mStatus = status;
    this.mWaitTime = waitTime;
    this.mWorkflow = workflow;
    this.mTimeWindowGroup = timeWindowGroup;
    this.mBatchSize = batchSize;
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

  public String getStatus() {
    return this.mStatus;
  }

  public void setStatus(String status) {
    this.mStatus = status;
  }

  public EngineServiceState getStatusAsEnum() {
    return EngineServiceState.getEngineServiceState(this.mStatus);
  }

  public void setStatusEnum(EngineServiceState status) {
    if (status != EngineServiceState.NOT_MAPPED) {
      this.mStatus = status.getStatus();
    } else {
      throw new IllegalArgumentException("Cannot set unmapped status");
    }
  }

  public Integer getWaitTime() {
    return this.mWaitTime;
  }

  public void setWaitTime(Integer waitTime) {
    this.mWaitTime = waitTime;
  }

  public DataRef getWorkflow() {
    return this.mWorkflow;
  }

  public void setWorkflow(DataRef workflow) {
    this.mWorkflow = workflow;
  }

  public DataRef getTimeWindowGroup() {
    return this.mTimeWindowGroup;
  }

  public void setTimeWindowGroup(DataRef timeWindowGroup) {
    this.mTimeWindowGroup = timeWindowGroup;
  }

  public Integer getBatchSize() {
    return this.mBatchSize;
  }

  public void setBatchSize(Integer batchSize) {
    this.mBatchSize = batchSize;
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
    fieldOrder.add("Status");
    fieldOrder.add("WaitTime");
    fieldOrder.add("Workflow");
    fieldOrder.add("TimeWindowGroup");
    fieldOrder.add("BatchSize");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.wfe", "EngineService");
  }
}
