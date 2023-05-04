package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import java.io.Serializable;

// anchor:imports:start
import java.util.Date;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class DateTime implements Serializable {

  private static final long serialVersionUID = 1L;

  private Date dateTime;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DateTime() {
    // anchor:custom-default-constructor:start
    this(new Date());
    // anchor:custom-default-constructor:end
  }

  public DateTime(Date dateTime) {
    this.dateTime = dateTime;

    // anchor:custom-value-constructor:start
    // anchor:custom-value-constructor:end
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof DateTime)) {
      return false;
    }

    DateTime other = (DateTime) obj;

    boolean isEqual = super.equals(other);
    // anchor:custom-equality:start
    // anchor:custom-equality:end
    return isEqual;
  }

  public Date getValue() {
    // anchor:custom-getValue:start
    // anchor:custom-getValue:end

    return dateTime;
  }

  public void setValue(Date value) {
    // anchor:custom-setValue:start
    // anchor:custom-setValue:end

    dateTime = value;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
