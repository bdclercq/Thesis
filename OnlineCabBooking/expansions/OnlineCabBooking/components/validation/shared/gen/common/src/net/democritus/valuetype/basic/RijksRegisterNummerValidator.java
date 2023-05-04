package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.ValidationResult.success;
import static net.democritus.validation.ValidationResult.error;
import net.democritus.validation.IFieldValidator;
import net.democritus.validation.ValidationResult;

// anchor:custom-imports:start
/**
 * <p>
 * Validation for the {@link RijksRegisterNummer}, the social security number used by the Belgian government.
 * </p>
 *
 * <p>
 * We favor simplicity vs absolute correctness. There are two algorithms to validate the RRN based on whether the citizen is born before the year 2000 or after. Since the RRN only uses 2 digit precision for the year of birth, we cannot make the
 * distinction. The odds to encounter false positives are low enough to check for both and validate success if either is valid.
 * </p>
 *
 * <p>
 * Example for born before 2000<br>
 * RRN = 72020290081<br>
 * check digit = 81<br>
 * modulo 97 of 720202900 is 16<br>
 * 97 - 16 = 81
 * </p>
 *
 * <p>
 * Example for born after 2000<br>
 * RRN = 00012556777<br>
 * check digit = 77<br>
 * modulo 97 of 2000125567 is 20<br>
 * 97 - 20 = 77
 * </p>
 * <p>
 * Official reference (in Dutch) available on <a href=https://www.ksz-bcss.fgov.be/nl/bcss/page/content/websites/belgium/ services/docutheque/technical_faq/faq_5.html>KSZ Website</a> <i>checked 5 Dec 2014</i>.
 *
 * @author dtrog
 */
// anchor:custom-imports:end

public class RijksRegisterNummerValidator implements IFieldValidator {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public ValidationResult validate(Object value) {
    if (value != null && !(value instanceof RijksRegisterNummer)) {
      return error("Expected a RijksRegisterNummer object, found: " + value.getClass());
    }

    ValidationResult result = success();

    RijksRegisterNummer rijksRegisterNummer = (RijksRegisterNummer) value;

    if (rijksRegisterNummer != null && rijksRegisterNummer.getValue() != null) {
      String _value = rijksRegisterNummer.getValue();

      // anchor:basic-validation:start
      // anchor:basic-validation:end
    }

    // anchor:custom-validation:start
    if (value == null) {
      return ValidationResult.error("RijksRegisterNummer object is null");
    }

    String strRijksRegisterNummer = rijksRegisterNummer.getValue();

    if (strRijksRegisterNummer.isEmpty()) {
      return ValidationResult.success();
    }

    // should be numerical
    try {
      Long.parseLong(strRijksRegisterNummer);
    } catch (NumberFormatException e) {
      System.out.println("problem with this RijksRegisterNummer");
      return ValidationResult.error("should be numerical");
    }

    // should be 11 digits
    if (strRijksRegisterNummer.length() != 11) {
      System.out.println("problem with this RijksRegisterNummer");
      return ValidationResult.error("should be 11 digits");
    }

    System.out.println("validating RijksRegisterNummer " + strRijksRegisterNummer);

    // we have no information about year of birth
    // thus we check both and return success if either is OK
    if (isValidForBornBefore2000(strRijksRegisterNummer) || isValidForBornAfter2000(strRijksRegisterNummer)) {
      System.out.println("RijksRegisterNummer validation OK");
      result = ValidationResult.success();
    } else {
      System.out.println("problem with this RijksRegisterNummer");
      result = ValidationResult.error("should be a valid RijksRegisterNummer");
    }
    // anchor:custom-validation:end

    return result;
  }

  // anchor:custom-methods:start
  /**
   * @param rrNummer {@link RijksRegisterNummer} for citizen born before year 2000
   * @return <code>true</code> if valid
   */
  private boolean isValidForBornBefore2000(String rrNummer) {
    long first9Digits = Long.parseLong(rrNummer.substring(0, 9));
    int checkDigit = Integer.parseInt(rrNummer.substring(9, 11));
    int modulus = (int) (first9Digits % 97);

    return (97 - modulus) == checkDigit;
  }

  /**
   * @param rrNummer {@link RijksRegisterNummer} for citizen born after year 2000
   * @return <code>true</code> if valid
   */
  private boolean isValidForBornAfter2000(String rrNummer) {
    // differs only from other validation by prefixing the first 9 digits with extra digit '2'
    long first10Digits = Long.parseLong("2" + rrNummer.substring(0, 9));
    int checkDigit = Integer.parseInt(rrNummer.substring(9, 11));
    int modulus = (int) (first10Digits % 97);

    return (97 - modulus) == checkDigit;
  }
  // anchor:custom-methods:end

}
