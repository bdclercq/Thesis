package cabBookingCore;

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
 * Persistency pojo class for the entity TripBookingTaskStatus,
 */

@Entity(name=TripBookingTaskStatusData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="TripBookingTaskStatus", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=TripBookingTaskStatusData.QUERY_FINDALL, query="select o FROM " + TripBookingTaskStatusData.ENTITY_NAME + " o")
  // @anchor:queries:start
  , @NamedQuery(name = TripBookingTaskStatusData.QUERY_COMPARE_SET_STATUS, query = "UPDATE " + TripBookingTaskStatusData.ENTITY_NAME + " o SET o.status = :targetStatus WHERE o.id = :id AND o.status = :expectedStatus")
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class TripBookingTaskStatusData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String QUERY_COMPARE_SET_STATUS = "cabBookingCore.TripBookingTaskStatus.compareAndSetStatus";
  public static final String ENTITY_NAME = "cabBookingCore.TripBookingTaskStatus";
  public static final String QUERY_FINDALL = "cabBookingCore.TripBookingTaskStatus.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private Date mStartedAt;
  private Date mFinishedAt;
  private String mStatus;
  private Long mStateTask;
  private Long mTripBooking;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TripBookingTaskStatusData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TripBookingTaskStatusData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , Date startedAt
      , Date finishedAt
      , String status
      , Long stateTask
      , Long tripBooking
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mStartedAt = startedAt;
    this.mFinishedAt = finishedAt;
    this.mStatus = status;
    this.mStateTask = stateTask;
    this.mTripBooking = tripBooking;
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

  // @anchor:annotations-getter-startedAt:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-startedAt:end
  // anchor:custom-annotations-getter-startedAt:start
  // anchor:custom-annotations-getter-startedAt:end
  public Date getStartedAt() {
    return this.mStartedAt;
  }

  // @anchor:annotations-setter-startedAt:start
  // @anchor:annotations-setter-startedAt:end
  // anchor:custom-annotations-setter-startedAt:start
  // anchor:custom-annotations-setter-startedAt:end
  public void setStartedAt(Date startedAt) {
    this.mStartedAt = startedAt;
  }

  // @anchor:annotations-getter-finishedAt:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-finishedAt:end
  // anchor:custom-annotations-getter-finishedAt:start
  // anchor:custom-annotations-getter-finishedAt:end
  public Date getFinishedAt() {
    return this.mFinishedAt;
  }

  // @anchor:annotations-setter-finishedAt:start
  // @anchor:annotations-setter-finishedAt:end
  // anchor:custom-annotations-setter-finishedAt:start
  // anchor:custom-annotations-setter-finishedAt:end
  public void setFinishedAt(Date finishedAt) {
    this.mFinishedAt = finishedAt;
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

  // @anchor:annotations-getter-stateTask:start
  @Column(name="stateTask_id")
  // @anchor:annotations-getter-stateTask:end
  // anchor:custom-annotations-getter-stateTask:start
  // anchor:custom-annotations-getter-stateTask:end
  public Long getStateTask() {
    return this.mStateTask;
  }

  // @anchor:annotations-setter-stateTask:start
  // @anchor:annotations-setter-stateTask:end
  // anchor:custom-annotations-setter-stateTask:start
  // anchor:custom-annotations-setter-stateTask:end
  public void setStateTask(Long stateTask) {
    this.mStateTask = stateTask;
  }

  // @anchor:annotations-getter-tripBooking:start
  @Column(name="tripBooking_id")
  // @anchor:annotations-getter-tripBooking:end
  // anchor:custom-annotations-getter-tripBooking:start
  // anchor:custom-annotations-getter-tripBooking:end
  public Long getTripBooking() {
    return this.mTripBooking;
  }

  // @anchor:annotations-setter-tripBooking:start
  // @anchor:annotations-setter-tripBooking:end
  // anchor:custom-annotations-setter-tripBooking:start
  // anchor:custom-annotations-setter-tripBooking:end
  public void setTripBooking(Long tripBooking) {
    this.mTripBooking = tripBooking;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
