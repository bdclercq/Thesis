package net.democritus.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.democritus.validation.Result;
import net.democritus.valuetype.basic.Time;
import net.democritus.valuetype.basic.TimeConverter;
import org.junit.Test;

public class TimeConverterTest {

  @Test
  public void test_fromString_success() {
    TimeConverter timeConverter = new TimeConverter();

    assertEquals(new Time(0,0), timeConverter.fromString("").getValue());
    assertEquals(new Time(10,20), timeConverter.fromString("10:20").getValue());
  }

  @Test
  public void test_fromString_error() {
    TimeConverter timeConverter = new TimeConverter();

    assertTrue(timeConverter.fromString("100:200").isError());
    assertTrue(timeConverter.fromString("-100:0").isError());

    assertEquals("hours must be positive", timeConverter.fromString("-100:0").getMessage());
  }

  @Test
  public void test_fromString_null() {
    TimeConverter timeConverter = new TimeConverter();
    assertEquals(new Time(0,0), timeConverter.fromString(null).getValue());
  }

  @Test
  public void test_asString_null() {
    TimeConverter timeConverter = new TimeConverter();
    Result<String> result = timeConverter.asString(null);
    assertEquals(true, result.isSuccess());
    assertEquals("", result.getValue());
  }
}
