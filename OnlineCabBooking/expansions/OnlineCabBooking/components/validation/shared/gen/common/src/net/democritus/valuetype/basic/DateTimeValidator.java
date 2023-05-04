package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

import java.util.Date;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class DateTimeValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof DateTime)) {
      return error("Expected a DateTime object, found: " + value.getClass());
    }

    ValidationResult result = success();

    DateTime dateTime = (DateTime) value;

    if (dateTime != null && dateTime.getValue() != null) {
      Date _value = dateTime.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
