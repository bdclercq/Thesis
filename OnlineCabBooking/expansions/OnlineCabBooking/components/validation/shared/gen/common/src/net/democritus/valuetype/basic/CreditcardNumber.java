package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import java.io.Serializable;

// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class CreditcardNumber implements Serializable {

  private static final long serialVersionUID = 1L;

  private String creditcardNumber;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public CreditcardNumber() {
    // anchor:custom-default-constructor:start
    // anchor:custom-default-constructor:end
  }

  public CreditcardNumber(String creditcardNumber) {
    this.creditcardNumber = creditcardNumber;

    // anchor:custom-value-constructor:start
    if (creditcardNumber == null) {
      this.creditcardNumber = "";
    }
    // anchor:custom-value-constructor:end
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof CreditcardNumber)) {
      return false;
    }

    CreditcardNumber other = (CreditcardNumber) obj;

    boolean isEqual = super.equals(other);
    // anchor:custom-equality:start
    // anchor:custom-equality:end
    return isEqual;
  }

  public String getValue() {
    // anchor:custom-getValue:start
    // anchor:custom-getValue:end

    return creditcardNumber;
  }

  public void setValue(String value) {
    // anchor:custom-setValue:start
    // anchor:custom-setValue:end

    creditcardNumber = value;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
