package net.democritus.basic;

import net.democritus.validation.ValidationResult;
import net.democritus.valuetype.basic.Time;
import net.democritus.valuetype.basic.TimeValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeValidatorTest {

  private TimeValidator validator = new TimeValidator();

  @Test
  public void validate() throws Exception {
    ValidationResult result = validator.validate(new Time(15, 30));
    assertEquals(true, result.isValid());
  }

  @Test
  public void validate_unvalid() throws Exception {
    ValidationResult result = validator.validate(new Time(-15, 30));
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_null() throws Exception {
    ValidationResult result = validator.validate(null);
    assertEquals(true, result.isError());
  }

}