package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class EmailValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof Email)) {
      return error("Expected a Email object, found: " + value.getClass());
    }

    ValidationResult result = success();

    Email email = (Email) value;

    if (email != null && email.getValue() != null) {
      String _value = email.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return ValidationResult.error("Email object is null");
    }

    String strEmail = email.getValue();

    if (strEmail.isEmpty()) {
      return ValidationResult.success();
    }

    if (strEmail.contains("@")) {
      result = ValidationResult.success();
    } else {
      result = ValidationResult.error("should contain a '@'");
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
