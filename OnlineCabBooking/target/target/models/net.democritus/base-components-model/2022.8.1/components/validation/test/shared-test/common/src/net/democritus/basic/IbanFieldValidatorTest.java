package net.democritus.basic;

import net.democritus.validation.ValidationResult;
import net.democritus.valuetype.basic.Iban;
import net.democritus.valuetype.basic.IbanValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class IbanFieldValidatorTest {
  private static final String VALID_IBAN = "NL44RABO0383580816";
  private static final String INVALID_IBAN = "NL44RABO0383580817";

  private IbanValidator validator = new IbanValidator();

  @Test
  public void validate() throws Exception {
    ValidationResult result = validator.validate(new Iban(VALID_IBAN));
    assertEquals(true, result.isValid());
  }

  @Test
  public void validate_invalid() throws Exception {
    ValidationResult result = validator.validate(new Iban(INVALID_IBAN));
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_null() throws Exception {
    ValidationResult result = validator.validate(null);
    assertEquals(true, result.isError());
  }
  @Test
  public void validate_empty() throws Exception {
    ValidationResult result = validator.validate(new Iban(null));
    assertEquals(true, result.isValid());
  }


}