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
 * Persistency pojo class for the entity Cab,
 */

@Entity(name=CabData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Cab", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=CabData.QUERY_FINDALL, query="select o FROM " + CabData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class CabData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Cab";
  public static final String QUERY_FINDALL = "cabBookingCore.Cab.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private Integer mRatePerKm;
  private Long mCarType;
  private Long mDriver;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public CabData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public CabData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , Integer ratePerKm
      , Long carType
      , Long driver
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mRatePerKm = ratePerKm;
    this.mCarType = carType;
    this.mDriver = driver;
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
  // @anchor:annotations-getter-ratePerKm:start
  // @anchor:annotations-getter-ratePerKm:end
  // anchor:custom-annotations-getter-ratePerKm:start
  // anchor:custom-annotations-getter-ratePerKm:end
  public Integer getRatePerKm() {
    return this.mRatePerKm;
  }

  // @anchor:annotations-setter-ratePerKm:start
  // @anchor:annotations-setter-ratePerKm:end
  // anchor:custom-annotations-setter-ratePerKm:start
  // anchor:custom-annotations-setter-ratePerKm:end
  public void setRatePerKm(Integer ratePerKm) {
    this.mRatePerKm = ratePerKm;
  }

  // @anchor:annotations-getter-carType:start
  @Column(name="carType_id")
  // @anchor:annotations-getter-carType:end
  // anchor:custom-annotations-getter-carType:start
  // anchor:custom-annotations-getter-carType:end
  public Long getCarType() {
    return this.mCarType;
  }

  // @anchor:annotations-setter-carType:start
  // @anchor:annotations-setter-carType:end
  // anchor:custom-annotations-setter-carType:start
  // anchor:custom-annotations-setter-carType:end
  public void setCarType(Long carType) {
    this.mCarType = carType;
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
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
