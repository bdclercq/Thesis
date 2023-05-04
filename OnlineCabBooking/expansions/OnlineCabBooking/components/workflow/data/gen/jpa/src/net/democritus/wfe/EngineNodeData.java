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
 * Persistency pojo class for the entity EngineNode,
 */

@Entity(name=EngineNodeData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="EngineNode", schema="WORKFLOW")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=EngineNodeData.QUERY_FINDALL, query="select o FROM " + EngineNodeData.ENTITY_NAME + " o")
  // @anchor:queries:start
  , @NamedQuery(name = EngineNodeData.QUERY_COMPARE_SET_STATUS, query = "UPDATE " + EngineNodeData.ENTITY_NAME + " o SET o.status = :targetStatus WHERE o.id = :id AND o.status = :expectedStatus")
  // @anchor:queries:end
  // anchor:custom-queries:start
    , @NamedQuery(name = EngineNodeData.QUERY_PROMOTE_MASTER, query = "UPDATE " + EngineNodeData.ENTITY_NAME + " o set o.master=true where o.id=:id and NOT exists (select x from " + EngineNodeData.ENTITY_NAME + " x where x.master=true)")
  // anchor:custom-queries:end
})
public class EngineNodeData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String QUERY_COMPARE_SET_STATUS = "net.democritus.wfe.EngineNode.compareAndSetStatus";
  public static final String ENTITY_NAME = "net.democritus.wfe.EngineNode";
  public static final String QUERY_FINDALL = "net.democritus.wfe.EngineNode.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  public static final String QUERY_PROMOTE_MASTER = ENTITY_NAME + "_promoteToMaster";
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mStatus;
  private String mHostname;
  private Boolean mMaster;
  private Date mLastActive;
  private Date mActiveSince;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public EngineNodeData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public EngineNodeData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String status
      , String hostname
      , Boolean master
      , Date lastActive
      , Date activeSince
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mStatus = status;
    this.mHostname = hostname;
    this.mMaster = master;
    this.mLastActive = lastActive;
    this.mActiveSince = activeSince;
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

  // @anchor:annotations-getter-hostname:start
  // @anchor:annotations-getter-hostname:end
  // anchor:custom-annotations-getter-hostname:start
  // anchor:custom-annotations-getter-hostname:end
  public String getHostname() {
    return this.mHostname;
  }

  // @anchor:annotations-setter-hostname:start
  // @anchor:annotations-setter-hostname:end
  // anchor:custom-annotations-setter-hostname:start
  // anchor:custom-annotations-setter-hostname:end
  public void setHostname(String hostname) {
    this.mHostname = hostname;
  }

  // @anchor:annotations-getter-master:start
  // @anchor:annotations-getter-master:end
  // anchor:custom-annotations-getter-master:start
  // anchor:custom-annotations-getter-master:end
  public Boolean getMaster() {
    return this.mMaster;
  }

  // @anchor:annotations-setter-master:start
  // @anchor:annotations-setter-master:end
  // anchor:custom-annotations-setter-master:start
  // anchor:custom-annotations-setter-master:end
  public void setMaster(Boolean master) {
    this.mMaster = master;
  }

  // @anchor:annotations-getter-lastActive:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-lastActive:end
  // anchor:custom-annotations-getter-lastActive:start
  // anchor:custom-annotations-getter-lastActive:end
  public Date getLastActive() {
    return this.mLastActive;
  }

  // @anchor:annotations-setter-lastActive:start
  // @anchor:annotations-setter-lastActive:end
  // anchor:custom-annotations-setter-lastActive:start
  // anchor:custom-annotations-setter-lastActive:end
  public void setLastActive(Date lastActive) {
    this.mLastActive = lastActive;
  }

  // @anchor:annotations-getter-activeSince:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-activeSince:end
  // anchor:custom-annotations-getter-activeSince:start
  // anchor:custom-annotations-getter-activeSince:end
  public Date getActiveSince() {
    return this.mActiveSince;
  }

  // @anchor:annotations-setter-activeSince:start
  // @anchor:annotations-setter-activeSince:end
  // anchor:custom-annotations-setter-activeSince:start
  // anchor:custom-annotations-setter-activeSince:end
  public void setActiveSince(Date activeSince) {
    this.mActiveSince = activeSince;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
