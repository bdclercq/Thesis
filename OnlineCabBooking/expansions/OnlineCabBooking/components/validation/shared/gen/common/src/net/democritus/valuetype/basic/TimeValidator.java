package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class TimeValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof Time)) {
      return error("Expected a Time object, found: " + value.getClass());
    }

    ValidationResult result = success();

    Time time = (Time) value;

    if (time != null && time.getValue() != null) {
      String _value = time.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return error("Time Object is null");
    }

    if (time.getMinutes() < 0 || time.getMinutes() > 59) {
      return error("minutes must be 0-59");
    }

    if (time.getHours() < 0) {
      return error("hours must be positive");
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
