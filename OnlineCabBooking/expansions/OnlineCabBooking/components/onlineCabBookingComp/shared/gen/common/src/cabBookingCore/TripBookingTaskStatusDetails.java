package cabBookingCore;

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
 * Transport detailed class for the entity bean TripBookingTaskStatus,
 */

public class TripBookingTaskStatusDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mName;
  private Date mStartedAt;
  private Date mFinishedAt;
  private String mStatus;
  private DataRef mStateTask;
  private DataRef mTripBooking;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TripBookingTaskStatusDetails() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mName = "";
    this.mStartedAt = new Date();
    this.mFinishedAt = new Date();
    this.mStatus = "";
    this.mStateTask = DataRef.withId(0L);
    this.mTripBooking = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TripBookingTaskStatusDetails(Long id
      // anchor:detailed-constructor-parameters:start
      , String name
      , Date startedAt
      , Date finishedAt
      , String status
      , DataRef stateTask
      , DataRef tripBooking
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mName = name;
    this.mStartedAt = startedAt;
    this.mFinishedAt = finishedAt;
    this.mStatus = status;
    this.mStateTask = stateTask;
    this.mTripBooking = tripBooking;
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
    return "TripBookingTaskStatus";
  }

  public String getElementPackage() {
    return "cabBookingCore";
  }
  // anchor:getters-setters:start
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public Date getStartedAt() {
    return this.mStartedAt;
  }

  public void setStartedAt(Date startedAt) {
    this.mStartedAt = startedAt;
  }

  public Date getFinishedAt() {
    return this.mFinishedAt;
  }

  public void setFinishedAt(Date finishedAt) {
    this.mFinishedAt = finishedAt;
  }

  public String getStatus() {
    return this.mStatus;
  }

  public void setStatus(String status) {
    this.mStatus = status;
  }

  public TripBookingTaskStatusState getStatusAsEnum() {
    return TripBookingTaskStatusState.getTripBookingTaskStatusState(this.mStatus);
  }

  public void setStatusEnum(TripBookingTaskStatusState status) {
    if (status != TripBookingTaskStatusState.NOT_MAPPED) {
      this.mStatus = status.getStatus();
    } else {
      throw new IllegalArgumentException("Cannot set unmapped status");
    }
  }

  public DataRef getStateTask() {
    return this.mStateTask;
  }

  public void setStateTask(DataRef stateTask) {
    this.mStateTask = stateTask;
  }

  public DataRef getTripBooking() {
    return this.mTripBooking;
  }

  public void setTripBooking(DataRef tripBooking) {
    this.mTripBooking = tripBooking;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "onlineCabBookingComp", "cabBookingCore", "TripBookingTaskStatus");
  }
}
