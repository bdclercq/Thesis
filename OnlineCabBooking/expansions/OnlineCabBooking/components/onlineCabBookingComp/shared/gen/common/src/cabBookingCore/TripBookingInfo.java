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
 * Transport detailed class for the entity bean TripBooking,
 */

public class TripBookingInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private DataRef mCustomer;
  private DataRef mDriver;
  private DataRef mFromLocation;
  private DataRef mToLocation;
  private Date mFromDateTime;
  private Date mToDateTime;
  private Double mKm;
  private Double mTotalAmount;
  private DataRef mPayment;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TripBookingInfo() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mCustomer = DataRef.withId(0L);
    this.mDriver = DataRef.withId(0L);
    this.mFromLocation = DataRef.withId(0L);
    this.mToLocation = DataRef.withId(0L);
    this.mFromDateTime = new Date();
    this.mToDateTime = new Date();
    this.mKm = null;
    this.mTotalAmount = null;
    this.mPayment = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TripBookingInfo(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , DataRef customer
      , DataRef driver
      , DataRef fromLocation
      , DataRef toLocation
      , Date fromDateTime
      , Date toDateTime
      , Double km
      , Double totalAmount
      , DataRef payment
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mCustomer = customer;
    this.mDriver = driver;
    this.mFromLocation = fromLocation;
    this.mToLocation = toLocation;
    this.mFromDateTime = fromDateTime;
    this.mToDateTime = toDateTime;
    this.mKm = km;
    this.mTotalAmount = totalAmount;
    this.mPayment = payment;
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
  public DataRef getCustomer() {
    return this.mCustomer;
  }

  public void setCustomer(DataRef customer) {
    this.mCustomer = customer;
  }

  public DataRef getDriver() {
    return this.mDriver;
  }

  public void setDriver(DataRef driver) {
    this.mDriver = driver;
  }

  public DataRef getFromLocation() {
    return this.mFromLocation;
  }

  public void setFromLocation(DataRef fromLocation) {
    this.mFromLocation = fromLocation;
  }

  public DataRef getToLocation() {
    return this.mToLocation;
  }

  public void setToLocation(DataRef toLocation) {
    this.mToLocation = toLocation;
  }

  public Date getFromDateTime() {
    return this.mFromDateTime;
  }

  public void setFromDateTime(Date fromDateTime) {
    this.mFromDateTime = fromDateTime;
  }

  public Date getToDateTime() {
    return this.mToDateTime;
  }

  public void setToDateTime(Date toDateTime) {
    this.mToDateTime = toDateTime;
  }

  public Double getKm() {
    return this.mKm;
  }

  public void setKm(Double km) {
    this.mKm = km;
  }

  public Double getTotalAmount() {
    return this.mTotalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.mTotalAmount = totalAmount;
  }

  public DataRef getPayment() {
    return this.mPayment;
  }

  public void setPayment(DataRef payment) {
    this.mPayment = payment;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("Customer");
    fieldOrder.add("Driver");
    fieldOrder.add("FromLocation");
    fieldOrder.add("ToLocation");
    fieldOrder.add("FromDateTime");
    fieldOrder.add("ToDateTime");
    fieldOrder.add("Km");
    fieldOrder.add("TotalAmount");
    fieldOrder.add("Payment");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "onlineCabBookingComp", "cabBookingCore", "TripBooking");
  }
}
