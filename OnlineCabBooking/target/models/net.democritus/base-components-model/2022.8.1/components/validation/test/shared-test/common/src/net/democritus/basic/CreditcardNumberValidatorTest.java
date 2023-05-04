package net.democritus.basic;

import net.democritus.validation.ValidationResult;
import net.democritus.valuetype.basic.CreditcardNumber;
import net.democritus.valuetype.basic.CreditcardNumberValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditcardNumberValidatorTest {
  
  public static final String VALID_CCN = "4556244290049497";
  public static final String INVALID_CCN = "4556244290049499";
  private CreditcardNumberValidator validator = new CreditcardNumberValidator();

  @Test
  public void validate() throws Exception {
    CreditcardNumberValidator validator = new CreditcardNumberValidator();
    ValidationResult result = validator.validate(new CreditcardNumber(VALID_CCN));
    assertEquals(true, result.isValid());
  }

  @Test
  public void validate_invalid() throws Exception {
    ValidationResult result = validator.validate(new CreditcardNumber(INVALID_CCN));
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_null() throws Exception {
    ValidationResult result = validator.validate(null);
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_empty() throws Exception {
    ValidationResult result = validator.validate(new CreditcardNumber(null));
    assertEquals(true, result.isValid());
  }

}