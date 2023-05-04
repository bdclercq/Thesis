package net.democritus.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import net.democritus.validation.Result;

import net.democritus.valuetype.basic.DateTime;
import net.democritus.valuetype.basic.DateTimeConverter;
import org.junit.Test;

public class DateTimeConverterTest {

  private static final String SAMPLE_DATE = "28/06/2012 10:11";
  private static final Locale TEST_LOKALE = Locale.UK;

  @Test
  public void test_fromString() {
    DateTime converted = createConverter().fromString(SAMPLE_DATE).getValue();
    
    assertEquals(sampleDate(), converted.getValue());
  }

  private DateTimeConverter createConverter() {
    
    DateTimeConverter converter = new DateTimeConverter();
    
    converter.setLocale(TEST_LOKALE);
    return converter;
  }

  @Test
  public void test_asString() {
    DateTime dateTime = new DateTime(sampleDate());
    Result<String> converted = createConverter().asString(dateTime);
    
    assertEquals(SAMPLE_DATE, converted.getValue());
  }

  private Date sampleDate() {
    Calendar calendar = new GregorianCalendar(TEST_LOKALE);
    calendar.set(2012, 5, 28, 10, 11, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  @Test
  public void test_fromDate_null() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<DateTime> result = converter.fromDate(null);
    assertEquals(true, result.isSuccess());
    assertNotNull(result.getValue());
    assertNull(result.getValue().getValue());
  }

  @Test
  public void test_fromString_null() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<DateTime> result = converter.fromString(null);
    assertEquals(true, result.isSuccess());
    assertNotNull(result.getValue());
    assertNull(result.getValue().getValue());
  }

  @Test
  public void test_fromString_emptyString() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<DateTime> result = converter.fromString("");
    assertEquals(true, result.isSuccess());
    assertNotNull(result.getValue());
    assertNull(result.getValue().getValue());
  }

  @Test
  public void test_asDate_null() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<Date> result = converter.asDate(null);
    assertEquals(true, result.isSuccess());
    assertNull(result.getValue());
  }

  @Test
  public void test_asDate_empty() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<Date> result = converter.asDate(new DateTime(null));
    assertEquals(true, result.isSuccess());
    assertNull(result.getValue());
  }

  @Test
  public void test_asString_null() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<String> result = converter.asString(null);
    assertEquals(true, result.isSuccess());
    assertEquals("", result.getValue());
  }

  @Test
  public void test_asString_empty() throws Exception {
    DateTimeConverter converter = createConverter();

    Result<String> result = converter.asString(new DateTime(null));
    assertEquals(true, result.isSuccess());
    assertEquals("", result.getValue());
  }
}
