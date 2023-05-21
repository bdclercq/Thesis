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
 * Persistency pojo class for the entity Address,
 */

@Entity(name=AddressData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Address", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=AddressData.QUERY_FINDALL, query="select o FROM " + AddressData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class AddressData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Address";
  public static final String QUERY_FINDALL = "cabBookingCore.Address.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mState;
  private String mCity;
  private String mPincode;
  private String mStreet;
  private Integer mHouseNumber;
  private String mName;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public AddressData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public AddressData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String state
      , String city
      , String pincode
      , String street
      , Integer houseNumber
      , String name
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mState = state;
    this.mCity = city;
    this.mPincode = pincode;
    this.mStreet = street;
    this.mHouseNumber = houseNumber;
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
  // @anchor:annotations-getter-state:start
  // @anchor:annotations-getter-state:end
  // anchor:custom-annotations-getter-state:start
  // anchor:custom-annotations-getter-state:end
  public String getState() {
    return this.mState;
  }

  // @anchor:annotations-setter-state:start
  // @anchor:annotations-setter-state:end
  // anchor:custom-annotations-setter-state:start
  // anchor:custom-annotations-setter-state:end
  public void setState(String state) {
    this.mState = state;
  }

  // @anchor:annotations-getter-city:start
  // @anchor:annotations-getter-city:end
  // anchor:custom-annotations-getter-city:start
  // anchor:custom-annotations-getter-city:end
  public String getCity() {
    return this.mCity;
  }

  // @anchor:annotations-setter-city:start
  // @anchor:annotations-setter-city:end
  // anchor:custom-annotations-setter-city:start
  // anchor:custom-annotations-setter-city:end
  public void setCity(String city) {
    this.mCity = city;
  }

  // @anchor:annotations-getter-pincode:start
  // @anchor:annotations-getter-pincode:end
  // anchor:custom-annotations-getter-pincode:start
  // anchor:custom-annotations-getter-pincode:end
  public String getPincode() {
    return this.mPincode;
  }

  // @anchor:annotations-setter-pincode:start
  // @anchor:annotations-setter-pincode:end
  // anchor:custom-annotations-setter-pincode:start
  // anchor:custom-annotations-setter-pincode:end
  public void setPincode(String pincode) {
    this.mPincode = pincode;
  }

  // @anchor:annotations-getter-street:start
  // @anchor:annotations-getter-street:end
  // anchor:custom-annotations-getter-street:start
  // anchor:custom-annotations-getter-street:end
  public String getStreet() {
    return this.mStreet;
  }

  // @anchor:annotations-setter-street:start
  // @anchor:annotations-setter-street:end
  // anchor:custom-annotations-setter-street:start
  // anchor:custom-annotations-setter-street:end
  public void setStreet(String street) {
    this.mStreet = street;
  }

  // @anchor:annotations-getter-houseNumber:start
  // @anchor:annotations-getter-houseNumber:end
  // anchor:custom-annotations-getter-houseNumber:start
  // anchor:custom-annotations-getter-houseNumber:end
  public Integer getHouseNumber() {
    return this.mHouseNumber;
  }

  // @anchor:annotations-setter-houseNumber:start
  // @anchor:annotations-setter-houseNumber:end
  // anchor:custom-annotations-setter-houseNumber:start
  // anchor:custom-annotations-setter-houseNumber:end
  public void setHouseNumber(Integer houseNumber) {
    this.mHouseNumber = houseNumber;
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
