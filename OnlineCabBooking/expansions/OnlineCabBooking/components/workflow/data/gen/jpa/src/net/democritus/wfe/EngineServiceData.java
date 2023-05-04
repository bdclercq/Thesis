package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.democritus.sys.DataRef;

// anchor:imports:start
import net.democritus.wfe.EngineNodeServiceData;
// anchor:imports:end

// @anchor:imports:start
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Persistency pojo class for the entity EngineService,
 */

@Entity(name=EngineServiceData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="EngineService", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=EngineServiceData.QUERY_FINDALL, query="select o FROM " + EngineServiceData.ENTITY_NAME + " o")
  // @anchor:queries:start
  , @NamedQuery(name = EngineServiceData.QUERY_COMPARE_SET_STATUS, query = "UPDATE " + EngineServiceData.ENTITY_NAME + " o SET o.status = :targetStatus WHERE o.id = :id AND o.status = :expectedStatus")
  // @anchor:queries:end
  // anchor:custom-queries:start
  , @NamedQuery(name = EngineServiceData.QUERY_UPDATE_LASTRUNAT, query = "UPDATE " + EngineServiceData.ENTITY_NAME + " o SET o.lastRunAt = CURRENT_TIMESTAMP WHERE o.id = :id")
  // anchor:custom-queries:end
})
public class EngineServiceData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String QUERY_COMPARE_SET_STATUS = "net.democritus.wfe.EngineService.compareAndSetStatus";
  public static final String ENTITY_NAME = "net.democritus.wfe.EngineService";
  public static final String QUERY_FINDALL = "net.democritus.wfe.EngineService.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  public static final String QUERY_UPDATE_LASTRUNAT = "net.democritus.wfe.EngineService.updateLastRunAt";
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mStatus;
  private String mChanged;
  private String mBusy;
  private Integer mWaitTime;
  private Long mCollector;
  private Long mWorkflow;
  private Long mTimeWindowGroup;
  private Date mLastRunAt;
  private Integer mBatchSize;
  private Integer mMaximumNumberOfNodes;
  private Collection<EngineNodeServiceData> mEngineNodeServices;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineServiceData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineServiceData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String status
      , String changed
      , String busy
      , Integer waitTime
      , Long collector
      , Long workflow
      , Long timeWindowGroup
      , Date lastRunAt
      , Integer batchSize
      , Integer maximumNumberOfNodes
      , Collection<EngineNodeServiceData> engineNodeServices
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
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
    // anchor:constructor-assign:end
  }

  /*========== Getters and Setters ==========*/

  @Id
  // @anchor:generateId:start
  @GeneratedValue(strategy=GenerationType.AUTO)
  // @anchor:generateId:end
  // anchor:custom-annotations-getter-id:start
  // anchor:custom-annotations-getter-id:end
  public Long getId() {
    return this.mId;
  }

  // anchor:custom-annotations-setter-id:start
  // anchor:custom-annotations-setter-id:end
  public void setId(Long id) {
    this.mId = id;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-name:start
  // @anchor:annotations-getter-name:end
  // anchor:custom-annotations-getter-name:start
  // anchor:custom-annotations-getter-name:end
  public String getName() {
    return this.mName;
  }

  // @anchor:annotations-setter-name:start
  // @anchor:annotations-setter-name:end
  // anchor:custom-annotations-setter-name:start
  // anchor:custom-annotations-setter-name:end
  public void setName(String name) {
    this.mName = name;
  }

  // @anchor:annotations-getter-status:start
  // @anchor:annotations-getter-status:end
  // anchor:custom-annotations-getter-status:start
  // anchor:custom-annotations-getter-status:end
  public String getStatus() {
    return this.mStatus;
  }

  // @anchor:annotations-setter-status:start
  // @anchor:annotations-setter-status:end
  // anchor:custom-annotations-setter-status:start
  // anchor:custom-annotations-setter-status:end
  public void setStatus(String status) {
    this.mStatus = status;
  }

  // @anchor:annotations-getter-changed:start
  // @anchor:annotations-getter-changed:end
  // anchor:custom-annotations-getter-changed:start
  // anchor:custom-annotations-getter-changed:end
  public String getChanged() {
    return this.mChanged;
  }

  // @anchor:annotations-setter-changed:start
  // @anchor:annotations-setter-changed:end
  // anchor:custom-annotations-setter-changed:start
  // anchor:custom-annotations-setter-changed:end
  public void setChanged(String changed) {
    this.mChanged = changed;
  }

  // @anchor:annotations-getter-busy:start
  // @anchor:annotations-getter-busy:end
  // anchor:custom-annotations-getter-busy:start
  // anchor:custom-annotations-getter-busy:end
  public String getBusy() {
    return this.mBusy;
  }

  // @anchor:annotations-setter-busy:start
  // @anchor:annotations-setter-busy:end
  // anchor:custom-annotations-setter-busy:start
  // anchor:custom-annotations-setter-busy:end
  public void setBusy(String busy) {
    this.mBusy = busy;
  }

  // @anchor:annotations-getter-waitTime:start
  // @anchor:annotations-getter-waitTime:end
  // anchor:custom-annotations-getter-waitTime:start
  // anchor:custom-annotations-getter-waitTime:end
  public Integer getWaitTime() {
    return this.mWaitTime;
  }

  // @anchor:annotations-setter-waitTime:start
  // @anchor:annotations-setter-waitTime:end
  // anchor:custom-annotations-setter-waitTime:start
  // anchor:custom-annotations-setter-waitTime:end
  public void setWaitTime(Integer waitTime) {
    this.mWaitTime = waitTime;
  }

  // @anchor:annotations-getter-collector:start
  // @anchor:annotations-getter-collector:end
  // anchor:custom-annotations-getter-collector:start
  // anchor:custom-annotations-getter-collector:end
  public Long getCollector() {
    return this.mCollector;
  }

  // @anchor:annotations-setter-collector:start
  // @anchor:annotations-setter-collector:end
  // anchor:custom-annotations-setter-collector:start
  // anchor:custom-annotations-setter-collector:end
  public void setCollector(Long collector) {
    this.mCollector = collector;
  }

  // @anchor:annotations-getter-workflow:start
  @Column(name="workflow_id")
  // @anchor:annotations-getter-workflow:end
  // anchor:custom-annotations-getter-workflow:start
  // anchor:custom-annotations-getter-workflow:end
  public Long getWorkflow() {
    return this.mWorkflow;
  }

  // @anchor:annotations-setter-workflow:start
  // @anchor:annotations-setter-workflow:end
  // anchor:custom-annotations-setter-workflow:start
  // anchor:custom-annotations-setter-workflow:end
  public void setWorkflow(Long workflow) {
    this.mWorkflow = workflow;
  }

  // @anchor:annotations-getter-timeWindowGroup:start
  @Column(name="timeWindowGroup_id")
  // @anchor:annotations-getter-timeWindowGroup:end
  // anchor:custom-annotations-getter-timeWindowGroup:start
  // anchor:custom-annotations-getter-timeWindowGroup:end
  public Long getTimeWindowGroup() {
    return this.mTimeWindowGroup;
  }

  // @anchor:annotations-setter-timeWindowGroup:start
  // @anchor:annotations-setter-timeWindowGroup:end
  // anchor:custom-annotations-setter-timeWindowGroup:start
  // anchor:custom-annotations-setter-timeWindowGroup:end
  public void setTimeWindowGroup(Long timeWindowGroup) {
    this.mTimeWindowGroup = timeWindowGroup;
  }

  // @anchor:annotations-getter-lastRunAt:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-lastRunAt:end
  // anchor:custom-annotations-getter-lastRunAt:start
  // anchor:custom-annotations-getter-lastRunAt:end
  public Date getLastRunAt() {
    return this.mLastRunAt;
  }

  // @anchor:annotations-setter-lastRunAt:start
  // @anchor:annotations-setter-lastRunAt:end
  // anchor:custom-annotations-setter-lastRunAt:start
  // anchor:custom-annotations-setter-lastRunAt:end
  public void setLastRunAt(Date lastRunAt) {
    this.mLastRunAt = lastRunAt;
  }

  // @anchor:annotations-getter-batchSize:start
  // @anchor:annotations-getter-batchSize:end
  // anchor:custom-annotations-getter-batchSize:start
  // anchor:custom-annotations-getter-batchSize:end
  public Integer getBatchSize() {
    return this.mBatchSize;
  }

  // @anchor:annotations-setter-batchSize:start
  // @anchor:annotations-setter-batchSize:end
  // anchor:custom-annotations-setter-batchSize:start
  // anchor:custom-annotations-setter-batchSize:end
  public void setBatchSize(Integer batchSize) {
    this.mBatchSize = batchSize;
  }

  // @anchor:annotations-getter-maximumNumberOfNodes:start
  // @anchor:annotations-getter-maximumNumberOfNodes:end
  // anchor:custom-annotations-getter-maximumNumberOfNodes:start
  // anchor:custom-annotations-getter-maximumNumberOfNodes:end
  public Integer getMaximumNumberOfNodes() {
    return this.mMaximumNumberOfNodes;
  }

  // @anchor:annotations-setter-maximumNumberOfNodes:start
  // @anchor:annotations-setter-maximumNumberOfNodes:end
  // anchor:custom-annotations-setter-maximumNumberOfNodes:start
  // anchor:custom-annotations-setter-maximumNumberOfNodes:end
  public void setMaximumNumberOfNodes(Integer maximumNumberOfNodes) {
    this.mMaximumNumberOfNodes = maximumNumberOfNodes;
  }

  // @anchor:annotations-getter-engineNodeServices:start
  @OneToMany(fetch=FetchType.LAZY, mappedBy="engineService")
  // @anchor:annotations-getter-engineNodeServices:end
  // anchor:custom-annotations-getter-engineNodeServices:start
  // anchor:custom-annotations-getter-engineNodeServices:end
  public Collection<EngineNodeServiceData> getEngineNodeServices() {
    return this.mEngineNodeServices;
  }

  // @anchor:annotations-setter-engineNodeServices:start
  // @anchor:annotations-setter-engineNodeServices:end
  // anchor:custom-annotations-setter-engineNodeServices:start
  // anchor:custom-annotations-setter-engineNodeServices:end
  public void setEngineNodeServices(final Collection<EngineNodeServiceData> engineNodeServices) {
    this.mEngineNodeServices = engineNodeServices;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
