package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import java.io.Serializable;

// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class Email implements Serializable {

  private static final long serialVersionUID = 1L;

  private String email;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Email() {
    // anchor:custom-default-constructor:start
    this("");
    // anchor:custom-default-constructor:end
  }

  public Email(String email) {
    this.email = email;

    // anchor:custom-value-constructor:start
    if (email == null) {
      this.email = "";
    }
    // anchor:custom-value-constructor:end
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Email)) {
      return false;
    }

    Email other = (Email) obj;

    boolean isEqual = super.equals(other);
    // anchor:custom-equality:start
    // anchor:custom-equality:end
    return isEqual;
  }

  public String getValue() {
    // anchor:custom-getValue:start
    // anchor:custom-getValue:end

    return email;
  }

  public void setValue(String value) {
    // anchor:custom-setValue:start
    // anchor:custom-setValue:end

    email = value;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
