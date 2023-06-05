package net.democritus.basic.jquery;

import static net.democritus.valuetype.basic.jquery.JQueryDateFormat.*;
import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class JQueryDateFormatTest {

  private static final Locale LOCALE_NL = new Locale("NL", "nl");

  @Test
  public void test_javaToJQuery() {
    assertEquals("dd-mm-yy", javaToJQuery("dd-MM-yyyy"));
    assertEquals("dd-mm-yy", javaToJQuery("dddddd-MMMMM-yyyyyyyy"));
    assertEquals("yy-mm-dd", javaToJQuery("yyyy-MM-dd"));
    assertEquals("Piet yy-mm-dd", javaToJQuery("Piet yyyy-MM-dd"));
  }

  @Test
  public void test_jQueryToJava() {
    assertEquals("dd-MM-yyyy", jQueryToJava("dd-mm-yy"));
    assertEquals("yyyy-MM-dd", jQueryToJava("yy-mm-dd"));
  }

  @Test
  public void test_localeToJavaDateFormat() {
    assertEquals("dd-MM-yyyy", localeToJavaDateFormat(LOCALE_NL));
    assertEquals("dd/MM/yyyy", localeToJavaDateFormat(Locale.FRANCE));
  }

  @Test
  public void test_localeToJQueryDateFormat() {
    assertEquals("dd-mm-yy", localeToJQueryDateFormat(LOCALE_NL));
    assertEquals("dd/mm/yy", localeToJQueryDateFormat(Locale.FRANCE));
  }

}
