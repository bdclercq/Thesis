package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import java.io.Serializable;

// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class Time implements Serializable {

  private static final long serialVersionUID = 1L;

  private String time;

  // anchor:custom-variables:start
  private int hours;
  private int minutes;

  public Time(int hours, int minutes) {
    this.hours = hours;
    this.minutes = minutes;
  }
  // anchor:custom-variables:end

  public Time() {
    // anchor:custom-default-constructor:start
    this(0, 0);
    // anchor:custom-default-constructor:end
  }

  public Time(String time) {
    this.time = time;

    // anchor:custom-value-constructor:start
    this.hours = 0;
    this.minutes = 0;
    // anchor:custom-value-constructor:end
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Time)) {
      return false;
    }

    Time other = (Time) obj;

    boolean isEqual = super.equals(other);
    // anchor:custom-equality:start
    isEqual = getHours() == other.getHours() && getMinutes() == other.getMinutes();
    // anchor:custom-equality:end
    return isEqual;
  }

  public String getValue() {
    // anchor:custom-getValue:start
    time = String.format("%d:%02d", hours, minutes);
    // anchor:custom-getValue:end

    return time;
  }

  public void setValue(String value) {
    // anchor:custom-setValue:start
    String[] parts = value.split(":");

    hours = 0;
    minutes = 0;

    try {
      if (parts.length >= 1) {
        String strHours = parts[0].trim();
        if (!strHours.isEmpty()) {
          hours = Integer.parseInt(strHours);
        }
      }

      if (parts.length >= 2) {

        String strMinutes = parts[1].trim();
        if (!strMinutes.isEmpty()) {
          minutes = Integer.parseInt(strMinutes);
        }
      }
    } catch (NumberFormatException e) {
      // don't care
    }
    // anchor:custom-setValue:end

    time = value;
  }

  // anchor:custom-methods:start
  public int getHours() {
    return hours;
  }

  public int getMinutes() {
    return minutes;
  }
  // anchor:custom-methods:end

}
