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
 * Persistency pojo class for the entity Driver,
 */

@Entity(name=DriverData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Driver", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=DriverData.QUERY_FINDALL, query="select o FROM " + DriverData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class DriverData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Driver";
  public static final String QUERY_FINDALL = "cabBookingCore.Driver.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private Integer mLicenseNo;
  private Double mRating;
  private Boolean mIsAvailable;
  private Long mCab;
  private Long mTripBooking;
  private String mName;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DriverData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public DriverData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Integer licenseNo
      , Double rating
      , Boolean isAvailable
      , Long cab
      , Long tripBooking
      , String name
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mLicenseNo = licenseNo;
    this.mRating = rating;
    this.mIsAvailable = isAvailable;
    this.mCab = cab;
    this.mTripBooking = tripBooking;
    this.mName = name;
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
  // @anchor:annotations-getter-licenseNo:start
  // @anchor:annotations-getter-licenseNo:end
  // anchor:custom-annotations-getter-licenseNo:start
  // anchor:custom-annotations-getter-licenseNo:end
  public Integer getLicenseNo() {
    return this.mLicenseNo;
  }

  // @anchor:annotations-setter-licenseNo:start
  // @anchor:annotations-setter-licenseNo:end
  // anchor:custom-annotations-setter-licenseNo:start
  // anchor:custom-annotations-setter-licenseNo:end
  public void setLicenseNo(Integer licenseNo) {
    this.mLicenseNo = licenseNo;
  }

  // @anchor:annotations-getter-rating:start
  // @anchor:annotations-getter-rating:end
  // anchor:custom-annotations-getter-rating:start
  // anchor:custom-annotations-getter-rating:end
  public Double getRating() {
    return this.mRating;
  }

  // @anchor:annotations-setter-rating:start
  // @anchor:annotations-setter-rating:end
  // anchor:custom-annotations-setter-rating:start
  // anchor:custom-annotations-setter-rating:end
  public void setRating(Double rating) {
    this.mRating = rating;
  }

  // @anchor:annotations-getter-isAvailable:start
  // @anchor:annotations-getter-isAvailable:end
  // anchor:custom-annotations-getter-isAvailable:start
  // anchor:custom-annotations-getter-isAvailable:end
  public Boolean getIsAvailable() {
    return this.mIsAvailable;
  }

  // @anchor:annotations-setter-isAvailable:start
  // @anchor:annotations-setter-isAvailable:end
  // anchor:custom-annotations-setter-isAvailable:start
  // anchor:custom-annotations-setter-isAvailable:end
  public void setIsAvailable(Boolean isAvailable) {
    this.mIsAvailable = isAvailable;
  }

  // @anchor:annotations-getter-cab:start
  @Column(name="cab_id")
  // @anchor:annotations-getter-cab:end
  // anchor:custom-annotations-getter-cab:start
  // anchor:custom-annotations-getter-cab:end
  public Long getCab() {
    return this.mCab;
  }

  // @anchor:annotations-setter-cab:start
  // @anchor:annotations-setter-cab:end
  // anchor:custom-annotations-setter-cab:start
  // anchor:custom-annotations-setter-cab:end
  public void setCab(Long cab) {
    this.mCab = cab;
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
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
