package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
import static net.democritus.valuetype.basic.IBANValidation.validateIBAN;
// anchor:custom-imports:end

public class IbanValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof Iban)) {
      return error("Expected a Iban object, found: " + value.getClass());
    }

    ValidationResult result = success();

    Iban iban = (Iban) value;

    if (iban != null && iban.getValue() != null) {
      String _value = iban.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return ValidationResult.error("Iban Object is null.");
    }

    String strIban = iban.getValue();

    if (strIban.isEmpty()) {
      return ValidationResult.success();
    }

    System.out.println("validating IBAN " + strIban);

    IBANValidation.IBANResponse ibanResponse = validateIBAN(strIban);
    if (ibanResponse.isValid()) {
      System.out.println("IBAN validation OK");
      result = ValidationResult.success();
    }
    else {
      System.out.println("problem with IBAN: " + ibanResponse.getErrorMessage());
      result = ValidationResult.error("should be a valid IBAN");
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
