package net.democritus.basic;

import net.democritus.validation.ValidationResult;
import net.democritus.valuetype.basic.KBO;
import net.democritus.valuetype.basic.KBOValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class KBOValidatorTest {

  private KBOValidator validator = new KBOValidator();

  @Test
  public void validate() throws Exception {
    ValidationResult result = validator.validate(new KBO("1987.654.321"));
    assertEquals(true, result.isValid());
  }

  @Test
  public void validate_invalid() throws Exception {
    ValidationResult result = validator.validate(new KBO("7987.654.321"));
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_null() throws Exception {
    ValidationResult result = validator.validate(null);
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_empty() throws Exception {
    ValidationResult result = validator.validate(new KBO(null));
    assertEquals(true, result.isValid());
  }

}