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
 * Transport detailed class for the entity bean Cab,
 */

public class CabInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private Integer mRatePerKm;
  private DataRef mCarType;
  private DataRef mDriver;
  private String mName;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public CabInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mRatePerKm = null;
    this.mCarType = DataRef.withId(0L);
    this.mDriver = DataRef.withId(0L);
    this.mName = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public CabInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , Integer ratePerKm
      , DataRef carType
      , DataRef driver
      , String name
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mRatePerKm = ratePerKm;
    this.mCarType = carType;
    this.mDriver = driver;
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
  public Integer getRatePerKm() {
    return this.mRatePerKm;
  }

  public void setRatePerKm(Integer ratePerKm) {
    this.mRatePerKm = ratePerKm;
  }

  public DataRef getCarType() {
    return this.mCarType;
  }

  public void setCarType(DataRef carType) {
    this.mCarType = carType;
  }

  public DataRef getDriver() {
    return this.mDriver;
  }

  public void setDriver(DataRef driver) {
    this.mDriver = driver;
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
    fieldOrder.add("RatePerKm");
    fieldOrder.add("CarType");
    fieldOrder.add("Driver");
    fieldOrder.add("Name");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "onlineCabBookingComp", "cabBookingCore", "Cab");
  }
}
