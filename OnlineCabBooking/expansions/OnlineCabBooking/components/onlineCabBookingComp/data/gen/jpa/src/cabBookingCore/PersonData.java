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
 * Persistency pojo class for the entity Person,
 */

@Entity(name=PersonData.ENTITY_NAME)
// @anchor:annotations:start
@Table(name="Person", schema="ONLINECABBOOKINGCOMP")
// @anchor:annotations:end
@NamedQueries({
  @NamedQuery(name=PersonData.QUERY_FINDALL, query="select o FROM " + PersonData.ENTITY_NAME + " o")
  // @anchor:queries:start
  // @anchor:queries:end
  // anchor:custom-queries:start
  // anchor:custom-queries:end
})
public class PersonData implements java.io.Serializable {

  // @anchor:constants:start
  public static final String ENTITY_NAME = "cabBookingCore.Person";
  public static final String QUERY_FINDALL = "cabBookingCore.Person.findAll";
  // @anchor:constants:end
  // anchor:custom-constants:start
  // anchor:custom-constants:end

  /*========== Bean member fields ==========*/

  private Long mId;
  // @anchor:variables:start
  private String mName;
  // @anchor:variables:end
  // anchor:member-fields:start
  private String mUsername;
  private String mPassword;
  private String mEmail;
  private String mMobile;
  private Long mAddress;
  // anchor:member-fields:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public PersonData() {
    // @anchor:default-constructor:start
    this.mName = "";
    // @anchor:default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public PersonData(Long id
      // @anchor:constructor-parameters:start
      , String name
      // @anchor:constructor-parameters:end
      // anchor:constructor-parameters:start
      , String username
      , String password
      , String email
      , String mobile
      , Long address
      // anchor:constructor-parameters:end
    ) {
    this.mId = id;
    // @anchor:constructor-assign:start
    this.mName = name;
    // @anchor:constructor-assign:end
    // anchor:constructor-assign:start
    this.mUsername = username;
    this.mPassword = password;
    this.mEmail = email;
    this.mMobile = mobile;
    this.mAddress = address;
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
  // @anchor:annotations-getter-username:start
  // @anchor:annotations-getter-username:end
  // anchor:custom-annotations-getter-username:start
  // anchor:custom-annotations-getter-username:end
  public String getUsername() {
    return this.mUsername;
  }

  // @anchor:annotations-setter-username:start
  // @anchor:annotations-setter-username:end
  // anchor:custom-annotations-setter-username:start
  // anchor:custom-annotations-setter-username:end
  public void setUsername(String username) {
    this.mUsername = username;
  }

  // @anchor:annotations-getter-password:start
  // @anchor:annotations-getter-password:end
  // anchor:custom-annotations-getter-password:start
  // anchor:custom-annotations-getter-password:end
  public String getPassword() {
    return this.mPassword;
  }

  // @anchor:annotations-setter-password:start
  // @anchor:annotations-setter-password:end
  // anchor:custom-annotations-setter-password:start
  // anchor:custom-annotations-setter-password:end
  public void setPassword(String password) {
    this.mPassword = password;
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

  // @anchor:annotations-getter-mobile:start
  // @anchor:annotations-getter-mobile:end
  // anchor:custom-annotations-getter-mobile:start
  // anchor:custom-annotations-getter-mobile:end
  public String getMobile() {
    return this.mMobile;
  }

  // @anchor:annotations-setter-mobile:start
  // @anchor:annotations-setter-mobile:end
  // anchor:custom-annotations-setter-mobile:start
  // anchor:custom-annotations-setter-mobile:end
  public void setMobile(String mobile) {
    this.mMobile = mobile;
  }

  // @anchor:annotations-getter-address:start
  @Column(name="address_id")
  // @anchor:annotations-getter-address:end
  // anchor:custom-annotations-getter-address:start
  // anchor:custom-annotations-getter-address:end
  public Long getAddress() {
    return this.mAddress;
  }

  // @anchor:annotations-setter-address:start
  // @anchor:annotations-setter-address:end
  // anchor:custom-annotations-setter-address:start
  // anchor:custom-annotations-setter-address:end
  public void setAddress(Long address) {
    this.mAddress = address;
  }
  // anchor:getters-and-setters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
