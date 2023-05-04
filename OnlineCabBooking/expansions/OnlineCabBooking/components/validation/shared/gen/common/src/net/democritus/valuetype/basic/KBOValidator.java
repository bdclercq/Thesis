package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class KBOValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof KBO)) {
      return error("Expected a KBO object, found: " + value.getClass());
    }

    ValidationResult result = success();

    KBO kBO = (KBO) value;

    if (kBO != null && kBO.getValue() != null) {
      String _value = kBO.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return ValidationResult.error("KBO Object is null");
    }

    String strKBO = kBO.getValue();

    if (strKBO.isEmpty()) {
      return ValidationResult.success();
    }

    if (strKBO.matches("[0-1]?[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}")) {
      result = ValidationResult.success();
    } else {
      result = ValidationResult.error("should be of the form ZNNN.NNN.NNN, with Z a 1 or optional 0, and N any digit");
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
