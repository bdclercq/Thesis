package net.democritus.valuetype.basic;

import static net.democritus.validation.Result.error;
import static net.democritus.validation.Result.success;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.democritus.validation.DateType;
import net.democritus.validation.IDateConverter;
import net.democritus.validation.Result;
import net.democritus.valuetype.basic.jquery.JQueryDateFormat;

public class DateConverter implements IDateConverter<Date> {
  
  private Locale locale = Locale.getDefault();

  @Override
  public Result<Date> fromString(String value) {
    
    if (value == null || value.trim().length() == 0) {
      return success(null);
    }
        
    try {
      return success(dateFormatter().parse(value));
    } catch (ParseException e) {
      return error("net.democritus.valuetype.basic.date.not.valid");
    }
    
  }

  @Override
  public Result<String> asString(Date date) {
    if (date == null) {
      return success("");
    }
    return success(dateFormatter().format(date));
  }

  @Override
  public Result<Date> fromDate(Date date) {
    if (date != null) {
      return success(date);
    } else {
      return success(null);
    }
  }

  @Override
  public Result<Date> asDate(Date date) {
    return success(date);
  }

  @Override
  public DateType getDatetype() {
    return null;
  }

  private DateFormat dateFormatter() {
    String dateFormatString = JQueryDateFormat.localeToJavaDateFormat(locale);
    return new SimpleDateFormat(dateFormatString);
  }

  @Override
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

}
