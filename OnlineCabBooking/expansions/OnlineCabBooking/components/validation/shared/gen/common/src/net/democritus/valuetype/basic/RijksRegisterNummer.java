package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import java.io.Serializable;

// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class RijksRegisterNummer implements Serializable {

  private static final long serialVersionUID = 1L;

  private String rijksRegisterNummer;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public RijksRegisterNummer() {
    // anchor:custom-default-constructor:start
    this("");
    // anchor:custom-default-constructor:end
  }

  public RijksRegisterNummer(String rijksRegisterNummer) {
    this.rijksRegisterNummer = rijksRegisterNummer;

    // anchor:custom-value-constructor:start
    if (rijksRegisterNummer == null) {
      this.rijksRegisterNummer = "";
    }
    // anchor:custom-value-constructor:end
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof RijksRegisterNummer)) {
      return false;
    }

    RijksRegisterNummer other = (RijksRegisterNummer) obj;

    boolean isEqual = super.equals(other);
    // anchor:custom-equality:start
    if (rijksRegisterNummer == null) {
      isEqual = other.rijksRegisterNummer == null;
    } else {
      isEqual = rijksRegisterNummer.equals(other.rijksRegisterNummer);
    }
    // anchor:custom-equality:end
    return isEqual;
  }

  public String getValue() {
    // anchor:custom-getValue:start
    // anchor:custom-getValue:end

    return rijksRegisterNummer;
  }

  public void setValue(String value) {
    // anchor:custom-setValue:start
    // anchor:custom-setValue:end

    rijksRegisterNummer = value;
  }

  // anchor:custom-methods:start
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rijksRegisterNummer == null) ? 0 : rijksRegisterNummer.hashCode());
    return result;
  }
  // anchor:custom-methods:end

}
