package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.Result.error;
import static net.democritus.validation.Result.success;
import net.democritus.validation.Result;

import net.democritus.validation.IConverter;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class TimeConverter implements IConverter<Time> {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public Result<Time> fromString(String value) {
    Result<Time> result = error("not implemented");

    // anchor:custom-fromString:start
    if (value == null) {
      return success(new Time(0,0));
    }

    value = value.trim();

    if (value.isEmpty()) {
      return success(new Time(0, 0));
    }

    int pos = value.indexOf(":");
    if (pos < 0) {
      return error("time format is HH:mm");
    }

    int hours;
    int minutes;

    String[] parts = value.split(":");

    try {
      hours = Integer.parseInt(parts[0]);
    } catch (NumberFormatException e) {
      return error("hours must be a valid integer");
    }

    try {

      if (parts.length > 0) {
        minutes = Integer.parseInt(parts[1]);
      } else {
        minutes = 0;
      }
    } catch (NumberFormatException e) {
      return error("hours must be a valid integer");
    }

    if (minutes <0 || minutes > 59) {
      return error("minutes must be 0-59");
    }

    if (hours < 0) {
      return error("hours must be positive");
    }

    result = success(new Time(hours, minutes));
    // anchor:custom-fromString:end

    return result;
  }

  @Override
  public Result<String> asString(Time time) {
    Result<String> result = error("not implemented");

    // anchor:custom-asString:start
    if (time == null) {
      result = success("");
    } else {
      result = success(String.format("%02d:%02d", time.getHours(), time.getMinutes()));
    }
    // anchor:custom-asString:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
