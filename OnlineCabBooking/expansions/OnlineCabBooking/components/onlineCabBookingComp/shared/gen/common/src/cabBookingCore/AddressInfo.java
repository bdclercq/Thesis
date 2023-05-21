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
 * Transport detailed class for the entity bean Address,
 */

public class AddressInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mState;
  private String mStreet;
  private Integer mHouseNumber;
  private String mName;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public AddressInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mState = "";
    this.mStreet = "";
    this.mHouseNumber = null;
    this.mName = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public AddressInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , String state
      , String street
      , Integer houseNumber
      , String name
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mState = state;
    this.mStreet = street;
    this.mHouseNumber = houseNumber;
    this.mName = name;
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

  // anchor:getters-setters:start
  public String getState() {
    return this.mState;
  }

  public void setState(String state) {
    this.mState = state;
  }

  public String getStreet() {
    return this.mStreet;
  }

  public void setStreet(String street) {
    this.mStreet = street;
  }

  public Integer getHouseNumber() {
    return this.mHouseNumber;
  }

  public void setHouseNumber(Integer houseNumber) {
    this.mHouseNumber = houseNumber;
  }

  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("State");
    fieldOrder.add("Street");
    fieldOrder.add("HouseNumber");
    fieldOrder.add("Name");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "onlineCabBookingComp", "cabBookingCore", "Address");
  }
}
