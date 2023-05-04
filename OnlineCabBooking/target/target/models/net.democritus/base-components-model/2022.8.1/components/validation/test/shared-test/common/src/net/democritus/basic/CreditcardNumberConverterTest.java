package net.democritus.basic;

import net.democritus.validation.Result;
import net.democritus.valuetype.basic.CreditcardNumber;
import net.democritus.valuetype.basic.CreditcardNumberConverter;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditcardNumberConverterTest {

  private CreditcardNumberConverter converter = new CreditcardNumberConverter();

  @Test
  public void fromString() throws Exception {
    String str = "test";
    Result<CreditcardNumber> result = converter.fromString(str);
    assertEquals(true, result.isSuccess());
    assertNotNull(result.getValue());
    assertEquals(str, result.getValue().getValue());
  }

  @Test
  public void fromString_null() throws Exception {
    Result<CreditcardNumber> result = converter.fromString(null);
    assertEquals(true, result.isSuccess());
    assertNotNull(result.getValue());
    assertEquals("", result.getValue().getValue());
  }

  @Test
  public void fromString_empty() throws Exception {
    Result<CreditcardNumber> result = converter.fromString("");
    assertEquals(true, result.isSuccess());
    assertNotNull(result.getValue());
    assertEquals("", result.getValue().getValue());
  }

  @Test
  public void asString() throws Exception {
    String str = "test";
    CreditcardNumber creditcardNumber = new CreditcardNumber(str);
    Result<String> result = converter.asString(creditcardNumber);
    assertEquals(true, result.isSuccess());
    assertEquals(str, result.getValue());
  }

  @Test
  public void asString_null() throws Exception {
    Result<String> result = converter.asString(null);
    assertEquals(true, result.isSuccess());
    assertEquals("", result.getValue());
  }

  @Test
  public void asString_empty() throws Exception {
    CreditcardNumber creditcardNumber = new CreditcardNumber(null);
    Result<String> result = converter.asString(creditcardNumber);
    assertEquals(true, result.isSuccess());
    assertEquals("", result.getValue());
  }
}