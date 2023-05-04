package net.democritus.basic;

import net.democritus.validation.ValidationResult;
import net.democritus.valuetype.basic.Image;
import net.democritus.valuetype.basic.ImageValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageFieldValidatorTest {

  private ImageValidator validator = new ImageValidator();

  @Test
  public void validate() throws Exception {
    ValidationResult result = validator.validate(new Image("http://stuff"));
    System.out.println(result.getMessage());
    assertEquals(true, result.isValid());
  }

  @Test
  public void validate_invalid() throws Exception {
    ValidationResult result = validator.validate(new Image("something"));
    assertEquals(true, result.isError());
  }

  @Test
  public void validate_null() throws Exception {
    ValidationResult result = validator.validate(null);
    assertEquals(true, result.isError());
  }


  @Test
  public void validate_empty() throws Exception {
    ValidationResult result = validator.validate(new Image(null));
    assertEquals(true, result.isValid());
  }
}