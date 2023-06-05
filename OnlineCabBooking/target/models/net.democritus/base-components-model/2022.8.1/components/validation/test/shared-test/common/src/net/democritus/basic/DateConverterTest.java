package net.democritus.basic;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import net.democritus.validation.Result;


import net.democritus.valuetype.basic.DateConverter;
import org.junit.Test;

public class DateConverterTest {

  private static final String SAMPLE_DATE = "28/06/2012";
  private static final Locale TEST_LOCALE = Locale.UK;

  @Test
  public void test_fromString() {
    Date converted = createConverter().fromString(SAMPLE_DATE).getValue();
    assertEquals(sampleDate(), converted);
  }

  @Test
  public void test_fromString_null() throws Exception {
    Result<Date> result = createConverter().fromString(null);
    assertEquals(true, result.isSuccess());
    assertEquals(null, result.getValue());
  }

  @Test
  public void test_fromString_empty() throws Exception {
    Result<Date> result = createConverter().fromString("");
    assertEquals(true, result.isSuccess());
    assertEquals(null, result.getValue());
  }

  @Test
  public void test_asString() {
    Result<String> converted = createConverter().asString(sampleDate());
    assertEquals(SAMPLE_DATE, converted.getValue());
  }

  @Test
  public void test_asString_null() throws Exception {
    Result<String> result = createConverter().asString(null);
    assertEquals(true, result.isSuccess());
    assertEquals("", result.getValue());
  }

  @Test
  public void test_fromDate_null() throws Exception {
    Result<Date> result = createConverter().fromDate(null);
    assertEquals(true, result.isSuccess());
    assertEquals(null, result.getValue());
  }

  @Test
  public void test_asDate_null() throws Exception {
    Result<Date> result = createConverter().asDate(null);
    assertEquals(true, result.isSuccess());
    assertEquals(null, result.getValue());
  }

  private DateConverter createConverter() {
    DateConverter converter = new DateConverter();
    converter.setLocale(TEST_LOCALE);
    return converter;
  }

  private Date sampleDate() {
    Calendar calendar = new GregorianCalendar(TEST_LOCALE);
    calendar.set(2012, 5, 28, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
}
