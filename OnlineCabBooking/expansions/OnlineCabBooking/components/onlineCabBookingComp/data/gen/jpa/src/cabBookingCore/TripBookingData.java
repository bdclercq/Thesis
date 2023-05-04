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
 * Persistency pojo class for the entity TripBooking,
 */

@Entity(name=TripBookingData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="TripBooking", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=TripBookingData.QUERY_FINDALL, query="select o FROM " + TripBookingData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class TripBookingData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.TripBooking";
  public static final String QUERY_FINDALL = "cabBookingCore.TripBooking.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Long mCustomer;
  private Long mDriver;
  private Long mFromLocation;
  private Long mToLocation;
  private Date mFromDateTime;
  private Date mToDateTime;
  private Double mKm;
  private Double mTotalAmount;
  private Long mPayment;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public TripBookingData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public TripBookingData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Long customer
      , Long driver
      , Long fromLocation
      , Long toLocation
      , Date fromDateTime
      , Date toDateTime
      , Double km
      , Double totalAmount
      , Long payment
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mCustomer = customer;
    this.mDriver = driver;
    this.mFromLocation = fromLocation;
    this.mToLocation = toLocation;
    this.mFromDateTime = fromDateTime;
    this.mToDateTime = toDateTime;
    this.mKm = km;
    this.mTotalAmount = totalAmount;
    this.mPayment = payment;
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
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }
  // @anchor:methods:end
  // anchor:getters-and-setters:start
  // @anchor:annotations-getter-customer:start
  @Column(name="customer_id")
  // @anchor:annotations-getter-customer:end
  // anchor:custom-annotations-getter-customer:start
  // anchor:custom-annotations-getter-customer:end
  public Long getCustomer() {
    return this.mCustomer;
  }

  // @anchor:annotations-setter-customer:start
  // @anchor:annotations-setter-customer:end
  // anchor:custom-annotations-setter-customer:start
  // anchor:custom-annotations-setter-customer:end
  public void setCustomer(Long customer) {
    this.mCustomer = customer;
  }

  // @anchor:annotations-getter-driver:start
  @Column(name="driver_id")
  // @anchor:annotations-getter-driver:end
  // anchor:custom-annotations-getter-driver:start
  // anchor:custom-annotations-getter-driver:end
  public Long getDriver() {
    return this.mDriver;
  }

  // @anchor:annotations-setter-driver:start
  // @anchor:annotations-setter-driver:end
  // anchor:custom-annotations-setter-driver:start
  // anchor:custom-annotations-setter-driver:end
  public void setDriver(Long driver) {
    this.mDriver = driver;
  }

  // @anchor:annotations-getter-fromLocation:start
  @Column(name="fromLocation_id")
  // @anchor:annotations-getter-fromLocation:end
  // anchor:custom-annotations-getter-fromLocation:start
  // anchor:custom-annotations-getter-fromLocation:end
  public Long getFromLocation() {
    return this.mFromLocation;
  }

  // @anchor:annotations-setter-fromLocation:start
  // @anchor:annotations-setter-fromLocation:end
  // anchor:custom-annotations-setter-fromLocation:start
  // anchor:custom-annotations-setter-fromLocation:end
  public void setFromLocation(Long fromLocation) {
    this.mFromLocation = fromLocation;
  }

  // @anchor:annotations-getter-toLocation:start
  @Column(name="toLocation_id")
  // @anchor:annotations-getter-toLocation:end
  // anchor:custom-annotations-getter-toLocation:start
  // anchor:custom-annotations-getter-toLocation:end
  public Long getToLocation() {
    return this.mToLocation;
  }

  // @anchor:annotations-setter-toLocation:start
  // @anchor:annotations-setter-toLocation:end
  // anchor:custom-annotations-setter-toLocation:start
  // anchor:custom-annotations-setter-toLocation:end
  public void setToLocation(Long toLocation) {
    this.mToLocation = toLocation;
  }

  // @anchor:annotations-getter-fromDateTime:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-fromDateTime:end
  // anchor:custom-annotations-getter-fromDateTime:start
  // anchor:custom-annotations-getter-fromDateTime:end
  public Date getFromDateTime() {
    return this.mFromDateTime;
  }

  // @anchor:annotations-setter-fromDateTime:start
  // @anchor:annotations-setter-fromDateTime:end
  // anchor:custom-annotations-setter-fromDateTime:start
  // anchor:custom-annotations-setter-fromDateTime:end
  public void setFromDateTime(Date fromDateTime) {
    this.mFromDateTime = fromDateTime;
  }

  // @anchor:annotations-getter-toDateTime:start
  @Temporal(TemporalType.TIMESTAMP)
  // @anchor:annotations-getter-toDateTime:end
  // anchor:custom-annotations-getter-toDateTime:start
  // anchor:custom-annotations-getter-toDateTime:end
  public Date getToDateTime() {
    return this.mToDateTime;
  }

  // @anchor:annotations-setter-toDateTime:start
  // @anchor:annotations-setter-toDateTime:end
  // anchor:custom-annotations-setter-toDateTime:start
  // anchor:custom-annotations-setter-toDateTime:end
  public void setToDateTime(Date toDateTime) {
    this.mToDateTime = toDateTime;
  }

  // @anchor:annotations-getter-km:start
  // @anchor:annotations-getter-km:end
  // anchor:custom-annotations-getter-km:start
  // anchor:custom-annotations-getter-km:end
  public Double getKm() {
    return this.mKm;
  }

  // @anchor:annotations-setter-km:start
  // @anchor:annotations-setter-km:end
  // anchor:custom-annotations-setter-km:start
  // anchor:custom-annotations-setter-km:end
  public void setKm(Double km) {
    this.mKm = km;
  }

  // @anchor:annotations-getter-totalAmount:start
  // @anchor:annotations-getter-totalAmount:end
  // anchor:custom-annotations-getter-totalAmount:start
  // anchor:custom-annotations-getter-totalAmount:end
  public Double getTotalAmount() {
    return this.mTotalAmount;
  }

  // @anchor:annotations-setter-totalAmount:start
  // @anchor:annotations-setter-totalAmount:end
  // anchor:custom-annotations-setter-totalAmount:start
  // anchor:custom-annotations-setter-totalAmount:end
  public void setTotalAmount(Double totalAmount) {
    this.mTotalAmount = totalAmount;
  }

  // @anchor:annotations-getter-payment:start
  @Column(name="payment_id")
  // @anchor:annotations-getter-payment:end
  // anchor:custom-annotations-getter-payment:start
  // anchor:custom-annotations-getter-payment:end
  public Long getPayment() {
    return this.mPayment;
  }

  // @anchor:annotations-setter-payment:start
  // @anchor:annotations-setter-payment:end
  // anchor:custom-annotations-setter-payment:start
  // anchor:custom-annotations-setter-payment:end
  public void setPayment(Long payment) {
    this.mPayment = payment;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
