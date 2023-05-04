package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
import java.net.MalformedURLException;
import java.net.URL;
// anchor:custom-imports:end

public class ImageValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof Image)) {
      return error("Expected a Image object, found: " + value.getClass());
    }

    ValidationResult result = success();

    Image image = (Image) value;

    if (image != null && image.getValue() != null) {
      String _value = image.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return ValidationResult.error("Image Object is null");
    }

    if (image.getValue().isEmpty()) {
      return ValidationResult.success();
    }

    try {
      new URL(image.getValue());
    } catch (MalformedURLException e) {
      return error(e.getMessage());
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
