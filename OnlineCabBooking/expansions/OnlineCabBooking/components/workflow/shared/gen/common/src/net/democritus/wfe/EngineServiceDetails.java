package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.io.Serializable;
import net.democritus.sys.DataRef;
import net.democritus.sys.IndirectRef;
import java.util.ArrayList;
import java.util.List;

// anchor:valuetype-imports:start
import net.democritus.valuetype.basic.DateTime;
// anchor:valuetype-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean EngineService,
 */

public class EngineServiceDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private String mStatus;
  private String mChanged;
  private String mBusy;
  private Integer mWaitTime;
  private Long mCollector;
  private DataRef mWorkflow;
  private DataRef mTimeWindowGroup;
  private DateTime mLastRunAt;
  private Integer mBatchSize;
  private Integer mMaximumNumberOfNodes;
  private List<DataRef> mEngineNodeServices;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineServiceDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mStatus = "";
    this.mChanged = "";
    this.mBusy = "";
    this.mWaitTime = null;
    this.mCollector = null;
    this.mWorkflow = DataRef.withId(0L);
    this.mTimeWindowGroup = DataRef.withId(0L);
    this.mLastRunAt = new DateTime();
    this.mBatchSize = null;
    this.mMaximumNumberOfNodes = null;
    this.mEngineNodeServices = new ArrayList<DataRef>();
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineServiceDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , String status
      , String changed
      , String busy
      , Integer waitTime
      , Long collector
      , DataRef workflow
      , DataRef timeWindowGroup
      , DateTime lastRunAt
      , Integer batchSize
      , Integer maximumNumberOfNodes
      , List<DataRef> engineNodeServices
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mStatus = status;
    this.mChanged = changed;
    this.mBusy = busy;
    this.mWaitTime = waitTime;
    this.mCollector = collector;
    this.mWorkflow = workflow;
    this.mTimeWindowGroup = timeWindowGroup;
    this.mLastRunAt = lastRunAt;
    this.mBatchSize = batchSize;
    this.mMaximumNumberOfNodes = maximumNumberOfNodes;
    this.mEngineNodeServices = engineNodeServices;
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
    return "EngineService";
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

  public String getChanged() {
    return this.mChanged;
  }

  public void setChanged(String changed) {
    this.mChanged = changed;
  }

  public String getBusy() {
    return this.mBusy;
  }

  public void setBusy(String busy) {
    this.mBusy = busy;
  }

  public Integer getWaitTime() {
    return this.mWaitTime;
  }

  public void setWaitTime(Integer waitTime) {
    this.mWaitTime = waitTime;
  }

  public Long getCollector() {
    return this.mCollector;
  }

  public void setCollector(Long collector) {
    this.mCollector = collector;
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

  public DateTime getLastRunAt() {
    return this.mLastRunAt;
  }

  public void setLastRunAt(DateTime lastRunAt) {
    this.mLastRunAt = lastRunAt;
  }

  public Integer getBatchSize() {
    return this.mBatchSize;
  }

  public void setBatchSize(Integer batchSize) {
    this.mBatchSize = batchSize;
  }

  public Integer getMaximumNumberOfNodes() {
    return this.mMaximumNumberOfNodes;
  }

  public void setMaximumNumberOfNodes(Integer maximumNumberOfNodes) {
    this.mMaximumNumberOfNodes = maximumNumberOfNodes;
  }

  public List<DataRef> getEngineNodeServices() {
    return this.mEngineNodeServices;
  }

  public void setEngineNodeServices(List<DataRef> engineNodeServices) {
    this.mEngineNodeServices = engineNodeServices;
  }

  public List<Long> getEngineNodeServicesIds() {
    final List<Long> engineNodeServicesIds = new ArrayList<Long>();
    for (DataRef dataRef : mEngineNodeServices) {
      engineNodeServicesIds.add(dataRef.getId());
    }
    return engineNodeServicesIds;
  }

  public void setEngineNodeServicesIds(String[] engineNodeServicesIds) {
    this.mEngineNodeServices = new ArrayList<DataRef>();
    for (final String string : engineNodeServicesIds) {
      mEngineNodeServices.add(new DataRef(new Long(string), "", "", "", ""));
    }
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
    @Override
    public String toString() {
        return "EngineService{" +
            "name='" + mName + '\'' +
            ", status='" + mStatus + '\'' +
            ", busy='" + mBusy + '\'' +
            ", batchSize=" + mBatchSize +
            '}';
    }
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "workflow", "net.democritus.wfe", "EngineService");
  }
}
