package net.democritus.acl;

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
import net.palver.util.StringUtil;
// anchor:custom-imports:end

/**
 * Transport detailed class for the entity bean DataAccess,
 */

public class DataAccessQuery
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /*========== Bean member fields ==========*/

  private Long mId;
  private String mName;
  // anchor:instance-variables:start
  private String mElement;
  private String mFunctionality;
  private String mTarget;
  // anchor:instance-variables:end
  // @anchor:instance-variables:start
  // @anchor:instance-variables:end

  // anchor:custom-variables:start
  private String mComponent;
  // anchor:custom-variables:end

  /*========== Default constructor ==========*/

  public DataAccessQuery() {
    this.mId = 0L;
    this.mName = "";
    // anchor:default-constructor-initialization:start
    this.mElement = "";
    this.mFunctionality = "";
    this.mTarget = "";
    // anchor:default-constructor-initialization:end
    // @anchor:default-constructor-initialization:start
    // @anchor:default-constructor-initialization:end

    // anchor:custom-default-constructor:start
    this.mComponent = "";
    // anchor:custom-default-constructor:end
  }

  /*========== Detailed constructor ==========*/

  public DataAccessQuery(Long id
      , String name
      // anchor:detailed-constructor-parameters:start
      , String element
      , String functionality
      , String target
      // anchor:detailed-constructor-parameters:end
      // @anchor:detailed-constructor-parameters:start
      // @anchor:detailed-constructor-parameters:end
      ) {
    this.mId = id;
    this.mName = name;
    // anchor:detailed-constructor-initialization:start
    this.mElement = element;
    this.mFunctionality = functionality;
    this.mTarget = target;
    // anchor:detailed-constructor-initialization:end
    // @anchor:detailed-constructor-initialization:start
    // @anchor:detailed-constructor-initialization:end

    // anchor:custom-detail-constructor:start
    this.mComponent = "";
    // anchor:custom-detail-constructor:end
  }

  /*========== Getters and Setters ==========*/

  public Long getId() {
    return this.mId;
  }

  public void setId(Long id) {
    this.mId = id;
  }

  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  // anchor:getters-setters:start
  public String getElement() {
    return this.mElement;
  }

  public void setElement(String element) {
    this.mElement = element;
  }

  public String getFunctionality() {
    return this.mFunctionality;
  }

  public void setFunctionality(String functionality) {
    this.mFunctionality = functionality;
  }

  public String getTarget() {
    return this.mTarget;
  }

  public void setTarget(String target) {
    this.mTarget = target;
  }
  // anchor:getters-setters:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  @Override
  public boolean equals(Object otherObject) {
    boolean isSame = false;
    if (otherObject instanceof DataAccessQuery) {
      isSame = areFieldsSame((DataAccessQuery) otherObject);
    }
    return isSame;
  }

  private boolean areFieldsSame(DataAccessQuery otherObject) {
    return notNull(getElement()).equals(notNull(otherObject.getElement())) &&
        notNull(getFunctionality()).equals(notNull(otherObject.getFunctionality())) &&
        notNull(getTarget()).equals(notNull(otherObject.getTarget())) &&
        this.hashCode() == (otherObject.hashCode());
  }

  private String notNull(String str) {
    if (str == null) {
      return "";
    }
    return str;
  }

  @Override
  public int hashCode() {
    return notNull(this.getElement()).hashCode() + notNull(this.getFunctionality()).hashCode() + notNull(this.getTarget()).hashCode();
  }

  public String getComponent() {
    return mComponent;
  }

  public void setComponent(String component) {
    this.mComponent = component;
  }

  @Override
  public String toString() {
    return StringUtil.joinSkip(".",
        mComponent,
        mElement,
        mFunctionality
    );
  }
  // anchor:custom-methods:end

  public DataRef getDataRef() {
    return new DataRef(mId, mName, "account", "net.democritus.acl", "DataAccess");
  }
}
