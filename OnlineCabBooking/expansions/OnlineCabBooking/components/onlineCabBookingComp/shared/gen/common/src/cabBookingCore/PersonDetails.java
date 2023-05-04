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
 * Transport detailed class for the entity bean Person,
 */

public class PersonDetails
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private String mUsername;
  private String mPassword;
  private String mEmail;
  private String mMobile;
  private DataRef mAddress;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public PersonDetails() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mUsername = "";
    this.mPassword = "";
    this.mEmail = "";
    this.mMobile = "";
    this.mAddress = DataRef.withId(0L);
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public PersonDetails(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , String username
      , String password
      , String email
      , String mobile
      , DataRef address
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mUsername = username;
    this.mPassword = password;
    this.mEmail = email;
    this.mMobile = mobile;
    this.mAddress = address;
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
    return "Person";
  }

  public String getElementPackage() {
    return "cabBookingCore";
  }
  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  // anchor:getters-setters:start
  public String getUsername() {
    return this.mUsername;
  }

  public void setUsername(String username) {
    this.mUsername = username;
  }

  public String getPassword() {
    return this.mPassword;
  }

  public void setPassword(String password) {
    this.mPassword = password;
  }

  public String getEmail() {
    return this.mEmail;
  }

  public void setEmail(String email) {
    this.mEmail = email;
  }

  public String getMobile() {
    return this.mMobile;
  }

  public void setMobile(String mobile) {
    this.mMobile = mobile;
  }

  public DataRef getAddress() {
    return this.mAddress;
  }

  public void setAddress(DataRef address) {
    this.mAddress = address;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "onlineCabBookingComp", "cabBookingCore", "Person");
  }
}
