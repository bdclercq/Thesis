package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.Result.error;
import static net.democritus.validation.Result.success;
import net.democritus.validation.Result;

import net.democritus.validation.IDateConverter;

import java.util.Date;
import java.util.Locale;
import net.democritus.validation.DateType;
// anchor:custom-imports:start
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.democritus.valuetype.basic.jquery.JQueryDateFormat;
// anchor:custom-imports:end

public class DateTimeConverter implements IDateConverter<DateTime> {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private Locale locale = Locale.getDefault();

  @Override
  public Result<DateTime> fromString(String value) {
    Result<DateTime> result = error("not implemented");

    // anchor:custom-fromString:start
    if (value == null || value.trim().length() == 0) {
      return Result.success(new DateTime(null));
    }

    try {
      Date date = this.dateFormatter().parse(value);
      result = Result.success(new DateTime(date));
    } catch (ParseException var3) {
      result = Result.error("net.democritus.valuetype.basic.datetime.not.valid");
    }
    // anchor:custom-fromString:end

    return result;
  }

  @Override
  public Result<String> asString(DateTime dateTime) {
    Result<String> result = error("not implemented");

    // anchor:custom-asString:start
    if (dateTime == null || dateTime.getValue() == null) {
      return Result.success("");
    }

    result = Result.success(this.dateFormatter().format(dateTime.getValue()));
    // anchor:custom-asString:end

    return result;
  }

  @Override
  public Result<DateTime> fromDate(Date value) {
    Result<DateTime> result = error("not implemented");

    // anchor:custom-fromStorageType:start
    result = Result.success(new DateTime(value));
    // anchor:custom-fromStorageType:end

    return result;
  }

  @Override
  public Result<Date> asDate(DateTime dateTime) {
    Result<Date> result = error("not implemented");

    // anchor:custom-toStorageType:start
    if (dateTime == null) {
      result = Result.success(null);
    } else {
      result = Result.success(dateTime.getValue());
    }
    // anchor:custom-toStorageType:end

    return result;
  }

  @Override
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  @Override
  public DateType getDatetype() {
    return null;
  }

  // anchor:custom-methods:start
  private DateFormat dateFormatter() {
    String dateFormatString = JQueryDateFormat.localeToJavaDateFormat(this.locale);
    return new SimpleDateFormat(dateFormatString + " hh:mm");
  }
  // anchor:custom-methods:end

}
