package net.democritus.usr;

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
 * Persistency pojo class for the entity Account,
 */

@Entity(name=AccountData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Account", schema="ACCOUNT")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=AccountData.QUERY_FINDALL, query="select o FROM " + AccountData.ENTITY_NAME + " o")
  // @anchor:queries:start
  , @NamedQuery(name = AccountData.QUERY_COMPARE_SET_STATUS, query = "UPDATE " + AccountData.ENTITY_NAME + " o SET o.status = :targetStatus WHERE o.id = :id AND o.status = :expectedStatus")
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class AccountData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String QUERY_COMPARE_SET_STATUS = "net.democritus.usr.Account.compareAndSetStatus";
  public static final String ENTITY_NAME = "net.democritus.usr.Account";
  public static final String QUERY_FINDALL = "net.democritus.usr.Account.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mName;
  private String mRefId;
  private String mFullName;
  private String mAddress;
  private String mZipCode;
  private String mCity;
  private String mCountry;
  private String mEmail;
  private String mPhone;
  private String mStyle;
  private String mStatus;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public AccountData() {
    // @anchor:default-constructor:start
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public AccountData(Long id
      // @anchor:constructor-parameters:start
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String name
      , String refId
      , String fullName
      , String address
      , String zipCode
      , String city
      , String country
      , String email
      , String phone
      , String style
      , String status
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mName = name;
    this.mRefId = refId;
    this.mFullName = fullName;
    this.mAddress = address;
    this.mZipCode = zipCode;
    this.mCity = city;
    this.mCountry = country;
    this.mEmail = email;
    this.mPhone = phone;
    this.mStyle = style;
    this.mStatus = status;
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

  // @anchor:annotations-getter-refId:start
  // @anchor:annotations-getter-refId:end
  // anchor:custom-annotations-getter-refId:start
  // anchor:custom-annotations-getter-refId:end
  public String getRefId() {
    return this.mRefId;
  }

  // @anchor:annotations-setter-refId:start
  // @anchor:annotations-setter-refId:end
  // anchor:custom-annotations-setter-refId:start
  // anchor:custom-annotations-setter-refId:end
  public void setRefId(String refId) {
    this.mRefId = refId;
  }

  // @anchor:annotations-getter-fullName:start
  // @anchor:annotations-getter-fullName:end
  // anchor:custom-annotations-getter-fullName:start
  // anchor:custom-annotations-getter-fullName:end
  public String getFullName() {
    return this.mFullName;
  }

  // @anchor:annotations-setter-fullName:start
  // @anchor:annotations-setter-fullName:end
  // anchor:custom-annotations-setter-fullName:start
  // anchor:custom-annotations-setter-fullName:end
  public void setFullName(String fullName) {
    this.mFullName = fullName;
  }

  // @anchor:annotations-getter-address:start
  // @anchor:annotations-getter-address:end
  // anchor:custom-annotations-getter-address:start
  // anchor:custom-annotations-getter-address:end
  public String getAddress() {
    return this.mAddress;
  }

  // @anchor:annotations-setter-address:start
  // @anchor:annotations-setter-address:end
  // anchor:custom-annotations-setter-address:start
  // anchor:custom-annotations-setter-address:end
  public void setAddress(String address) {
    this.mAddress = address;
  }

  // @anchor:annotations-getter-zipCode:start
  // @anchor:annotations-getter-zipCode:end
  // anchor:custom-annotations-getter-zipCode:start
  // anchor:custom-annotations-getter-zipCode:end
  public String getZipCode() {
    return this.mZipCode;
  }

  // @anchor:annotations-setter-zipCode:start
  // @anchor:annotations-setter-zipCode:end
  // anchor:custom-annotations-setter-zipCode:start
  // anchor:custom-annotations-setter-zipCode:end
  public void setZipCode(String zipCode) {
    this.mZipCode = zipCode;
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

  // @anchor:annotations-getter-country:start
  // @anchor:annotations-getter-country:end
  // anchor:custom-annotations-getter-country:start
  // anchor:custom-annotations-getter-country:end
  public String getCountry() {
    return this.mCountry;
  }

  // @anchor:annotations-setter-country:start
  // @anchor:annotations-setter-country:end
  // anchor:custom-annotations-setter-country:start
  // anchor:custom-annotations-setter-country:end
  public void setCountry(String country) {
    this.mCountry = country;
  }

  // @anchor:annotations-getter-email:start
  // @anchor:annotations-getter-email:end
  // anchor:custom-annotations-getter-email:start
  // anchor:custom-annotations-getter-email:end
  public String getEmail() {
    return this.mEmail;
  }

  // @anchor:annotations-setter-email:start
  // @anchor:annotations-setter-email:end
  // anchor:custom-annotations-setter-email:start
  // anchor:custom-annotations-setter-email:end
  public void setEmail(String email) {
    this.mEmail = email;
  }

  // @anchor:annotations-getter-phone:start
  // @anchor:annotations-getter-phone:end
  // anchor:custom-annotations-getter-phone:start
  // anchor:custom-annotations-getter-phone:end
  public String getPhone() {
    return this.mPhone;
  }

  // @anchor:annotations-setter-phone:start
  // @anchor:annotations-setter-phone:end
  // anchor:custom-annotations-setter-phone:start
  // anchor:custom-annotations-setter-phone:end
  public void setPhone(String phone) {
    this.mPhone = phone;
  }

  // @anchor:annotations-getter-style:start
  // @anchor:annotations-getter-style:end
  // anchor:custom-annotations-getter-style:start
  // anchor:custom-annotations-getter-style:end
  public String getStyle() {
    return this.mStyle;
  }

  // @anchor:annotations-setter-style:start
  // @anchor:annotations-setter-style:end
  // anchor:custom-annotations-setter-style:start
  // anchor:custom-annotations-setter-style:end
  public void setStyle(String style) {
    this.mStyle = style;
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
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
