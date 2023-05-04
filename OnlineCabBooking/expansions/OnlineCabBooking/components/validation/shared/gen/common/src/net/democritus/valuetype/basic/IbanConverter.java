package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.Result.error;
import static net.democritus.validation.Result.success;
import net.democritus.validation.Result;

import net.democritus.validation.IConverter;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class IbanConverter implements IConverter<Iban> {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public Result<Iban> fromString(String value) {
    Result<Iban> result = error("not implemented");

    // anchor:custom-fromString:start
    result = success(new Iban(value));
    // anchor:custom-fromString:end

    return result;
  }

  @Override
  public Result<String> asString(Iban iban) {
    Result<String> result = error("not implemented");

    // anchor:custom-asString:start
    if (iban == null) {
      result = success("");
    } else {
      result = success(iban.getValue());
    }
    // anchor:custom-asString:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
