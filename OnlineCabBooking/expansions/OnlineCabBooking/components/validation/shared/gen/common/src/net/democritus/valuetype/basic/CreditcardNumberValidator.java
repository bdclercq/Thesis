package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class CreditcardNumberValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof CreditcardNumber)) {
      return error("Expected a CreditcardNumber object, found: " + value.getClass());
    }

    ValidationResult result = success();

    CreditcardNumber creditcardNumber = (CreditcardNumber) value;

    if (creditcardNumber != null && creditcardNumber.getValue() != null) {
      String _value = creditcardNumber.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return ValidationResult.error("CreditcardNumber is null");
    }

    String strCreditcardNumber = creditcardNumber.getValue();
    if (strCreditcardNumber == null || strCreditcardNumber.isEmpty()) {
      return ValidationResult.success();
    }

    System.out.println("validating CreditcardNumber " + strCreditcardNumber);
    if (this.isValid(strCreditcardNumber)) {
      System.out.println("CreditcardNumber validation OK");
      result = ValidationResult.success();
    } else {
      System.out.println("problem with this CreditcardNumber");
      result = ValidationResult.error("should be a valid CreditcardNumber");
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  public boolean isValid(String cardNumber) {
    String digitsOnly = this.getDigitsOnly(cardNumber);
    int sum = 0;
    boolean timesTwo = false;

    int modulus;
    for(modulus = digitsOnly.length() - 1; modulus >= 0; --modulus) {
      int digit = Integer.parseInt(digitsOnly.substring(modulus, modulus + 1));
      int addend;
      if (timesTwo) {
        addend = digit * 2;
        if (addend > 9) {
          addend -= 9;
        }
      } else {
        addend = digit;
      }

      sum += addend;
      timesTwo = !timesTwo;
    }

    modulus = sum % 10;
    return modulus == 0;
  }

  private String getDigitsOnly(String s) {
    StringBuffer digitsOnly = new StringBuffer();

    for(int i = 0; i < s.length(); ++i) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        digitsOnly.append(c);
      }
    }

    return digitsOnly.toString();
  }
  // anchor:custom-methods:end

}
