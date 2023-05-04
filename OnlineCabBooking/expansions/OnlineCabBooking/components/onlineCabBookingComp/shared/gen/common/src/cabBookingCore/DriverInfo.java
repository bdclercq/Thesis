package cabBookingCore;

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
 * Transport detailed class for the entity bean Driver,
 */

public class DriverInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private Integer mLicenseNo;
  private Double mRating;
  private Boolean mIsAvailable;
  private DataRef mCab;
  private DataRef mTripBooking;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DriverInfo() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mLicenseNo = null;
    this.mRating = null;
    this.mIsAvailable = Boolean.FALSE;
    this.mCab = DataRef.withId(0L);
    this.mTripBooking = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public DriverInfo(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , Integer licenseNo
      , Double rating
      , Boolean isAvailable
      , DataRef cab
      , DataRef tripBooking
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mLicenseNo = licenseNo;
    this.mRating = rating;
    this.mIsAvailable = isAvailable;
    this.mCab = cab;
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

  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  // anchor:getters-setters:start
  public Integer getLicenseNo() {
    return this.mLicenseNo;
  }

  public void setLicenseNo(Integer licenseNo) {
    this.mLicenseNo = licenseNo;
  }

  public Double getRating() {
    return this.mRating;
  }

  public void setRating(Double rating) {
    this.mRating = rating;
  }

  public Boolean getIsAvailable() {
    return this.mIsAvailable;
  }

  public void setIsAvailable(Boolean isAvailable) {
    this.mIsAvailable = isAvailable;
  }

  public DataRef getCab() {
    return this.mCab;
  }

  public void setCab(DataRef cab) {
    this.mCab = cab;
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

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("LicenseNo");
    fieldOrder.add("Rating");
    fieldOrder.add("IsAvailable");
    fieldOrder.add("Cab");
    fieldOrder.add("TripBooking");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "onlineCabBookingComp", "cabBookingCore", "Driver");
  }
}
