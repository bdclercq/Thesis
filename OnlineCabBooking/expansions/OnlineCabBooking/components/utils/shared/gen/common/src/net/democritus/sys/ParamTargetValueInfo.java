package net.democritus.sys;

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
 * Transport detailed class for the entity bean ParamTargetValue,
 */

public class ParamTargetValueInfo
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  // anchor:instance-variables:start
  private String mParam;
  private String mTarget;
  private String mValue;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public ParamTargetValueInfo() {
    this.mId = 0L;
    // anchor:default-constructor-initialization:start
    this.mParam = "";
    this.mTarget = "";
    this.mValue = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public ParamTargetValueInfo(Long id
      // anchor:detailed-constructor-parameters:start
      , String param
      , String target
      , String value
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    // anchor:detailed-constructor-initialization:start
    this.mParam = param;
    this.mTarget = target;
    this.mValue = value;
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
  public String getParam() {
    return this.mParam;
  }

  public void setParam(String param) {
    this.mParam = param;
  }

  public String getTarget() {
    return this.mTarget;
  }

  public void setTarget(String target) {
    this.mTarget = target;
  }

  public String getValue() {
    return this.mValue;
  }

  public void setValue(String value) {
    this.mValue = value;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public List<String> getFieldOrder() {
    List<String> fieldOrder = new ArrayList<String>();
    // anchor:field-order:start
    fieldOrder.add("Param");
    fieldOrder.add("Target");
    fieldOrder.add("Value");
    // anchor:field-order:end
    return fieldOrder;
  }

  public DataRef getDataRef() {
    return new DataRef(mId, "", "utils", "net.democritus.sys", "ParamTargetValue");
  }
}
