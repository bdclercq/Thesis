package net.democritus.basic;

import net.democritus.validation.ValidationResult;
import net.democritus.valuetype.basic.Email;
import net.democritus.valuetype.basic.EmailValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailFieldValidatorTest {

  private EmailValidator validator = new EmailValidator();

  @Test
  public void validate() throws Exception {
    ValidationResult result = validator.validate(new Email("alpha@test.com"));
    assertEquals(true, result.isValid());
  }

  @Test
  public void validate_invalid() throws Exception {
    ValidationResult result = validator.validate(new Email("alpha"));
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_null() throws Exception {
    ValidationResult result = validator.validate(null);
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_empty() throws Exception {
    ValidationResult result = validator.validate(new Email(null));
    assertEquals(true, result.isValid());
  }

}