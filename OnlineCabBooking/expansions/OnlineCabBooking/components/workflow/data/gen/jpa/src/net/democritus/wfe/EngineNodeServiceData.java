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
import net.democritus.wfe.EngineNodeData;import net.democritus.wfe.EngineServiceData;
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
 * Persistency pojo class for the entity EngineNodeService,
 */

@Entity(name=EngineNodeServiceData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="EngineNodeService", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=EngineNodeServiceData.QUERY_FINDALL, query="select o FROM " + EngineNodeServiceData.ENTITY_NAME + " o")
  // @anchor:queries:start
  , @NamedQuery(name = EngineNodeServiceData.QUERY_COMPARE_SET_STATUS, query = "UPDATE " + EngineNodeServiceData.ENTITY_NAME + " o SET o.status = :targetStatus WHERE o.id = :id AND o.status = :expectedStatus")
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class EngineNodeServiceData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String QUERY_COMPARE_SET_STATUS = "net.democritus.wfe.EngineNodeService.compareAndSetStatus";
  public static final String ENTITY_NAME = "net.democritus.wfe.EngineNodeService";
  public static final String QUERY_FINDALL = "net.democritus.wfe.EngineNodeService.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mStatus;
  private EngineNodeData mEngineNode;
  private EngineServiceData mEngineService;
  private Date mLastRunAt;
  private Date mNextRun;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineNodeServiceData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineNodeServiceData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String status
      , EngineNodeData engineNode
      , EngineServiceData engineService
      , Date lastRunAt
      , Date nextRun
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mStatus = status;
    this.mEngineNode = engineNode;
    this.mEngineService = engineService;
    this.mLastRunAt = lastRunAt;
    this.mNextRun = nextRun;
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

  // @anchor:annotations-getter-engineNode:start
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="engineNode_id")
  // @anchor:annotations-getter-engineNode:end
  // anchor:custom-annotations-getter-engineNode:start
  // anchor:custom-annotations-getter-engineNode:end
  public EngineNodeData getEngineNode() {
    return this.mEngineNode;
  }

  // @anchor:annotations-setter-engineNode:start
  // @anchor:annotations-setter-engineNode:end
  // anchor:custom-annotations-setter-engineNode:start
  // anchor:custom-annotations-setter-engineNode:end
  public void setEngineNode(final EngineNodeData engineNode) {
    this.mEngineNode = engineNode;
  }

  // @anchor:annotations-getter-engineService:start
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="engineService_id")
  // @anchor:annotations-getter-engineService:end
  // anchor:custom-annotations-getter-engineService:start
  // anchor:custom-annotations-getter-engineService:end
  public EngineServiceData getEngineService() {
    return this.mEngineService;
  }

  // @anchor:annotations-setter-engineService:start
  // @anchor:annotations-setter-engineService:end
  // anchor:custom-annotations-setter-engineService:start
  // anchor:custom-annotations-setter-engineService:end
  public void setEngineService(final EngineServiceData engineService) {
    this.mEngineService = engineService;
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

  // @anchor:annotations-getter-nextRun:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-nextRun:end
  // anchor:custom-annotations-getter-nextRun:start
  // anchor:custom-annotations-getter-nextRun:end
  public Date getNextRun() {
    return this.mNextRun;
  }

  // @anchor:annotations-setter-nextRun:start
  // @anchor:annotations-setter-nextRun:end
  // anchor:custom-annotations-setter-nextRun:start
  // anchor:custom-annotations-setter-nextRun:end
  public void setNextRun(Date nextRun) {
    this.mNextRun = nextRun;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
