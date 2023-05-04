package net.democritus.valuetype.basic.jquery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class JQueryDateFormat {
  private static final String JQ_DAY = "dd";
  private static final String JQ_MONTH = "mm";
  private static final String JQ_YEAR = "yy";
  
  private static final String JAVA_DAY = "dd";
  private static final String JAVA_MONTH = "MM";
  private static final String JAVA_YEAR = "yyyy";

  public static String javaToJQuery(String javaFormat) {
    StringBuilder converted = new StringBuilder();
    Stream stream = new Stream(javaFormat);

    while (stream.hasNext()) {
      switch (stream.peek()) {
      case 'd':
        stream.eatChars('d');
        converted.append(JQ_DAY);
        break;
      case 'M':
        stream.eatChars('M');
        converted.append(JQ_MONTH);
        break;
      case 'y':
        stream.eatChars('y');
        converted.append(JQ_YEAR);
        break;
      default:
        converted.append(stream.peek());
        stream.next();
      }
    }
    
    return converted.toString();
  }
  
  public static String jQueryToJava(String jQueryFormat) {
    StringBuilder converted = new StringBuilder();
    Stream stream = new Stream(jQueryFormat);

    while (stream.hasNext()) {
      switch (stream.peek()) {
      case 'd':
        stream.eatChars('d');
        converted.append(JAVA_DAY);
        break;
      case 'm':
        stream.eatChars('m');
        converted.append(JAVA_MONTH);
        break;
      case 'y':
        stream.eatChars('y');
        converted.append(JAVA_YEAR);
        break;
      default:
        converted.append(stream.peek());
        stream.next();
      }
    }
    
    return converted.toString();
  }
  
  public static String localeToJavaDateFormat(Locale locale) {
    return jQueryToJava(localeToJQueryDateFormat(locale));
  }

  public static String localeToJQueryDateFormat(Locale locale) {
    SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale);
    return javaToJQuery(dateFormat.toPattern());
  }
}

class Stream {
  private final String content;
  private int pos = 0;

  public Stream(String content) {
    this.content = content;
  }

  public boolean hasNext() {
    return pos < content.length();
  }

  public char peek() {
    return content.charAt(pos);
  }

  public char next() {
    char ch = content.charAt(pos);
    pos++;
    return ch;
  }

  public void eatChars(char ch) {
    while (hasNext() && peek() == ch) {
      next();
    }
  }
}
