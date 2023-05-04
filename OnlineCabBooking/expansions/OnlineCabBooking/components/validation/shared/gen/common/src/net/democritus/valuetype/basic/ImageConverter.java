package net.democritus.valuetype.basic;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:field-type-expanders:5.12.1

import static net.democritus.validation.Result.error;
import static net.democritus.validation.Result.success;
import net.democritus.validation.Result;

import net.democritus.validation.IConverter;

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ImageConverter implements IConverter<Image> {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public Result<Image> fromString(String value) {
    Result<Image> result = error("not implemented");

    // anchor:custom-fromString:start
    if (value == null) {
      result = success(new Image(""));
    } else {
      result = success(new Image(value));
    }
    // anchor:custom-fromString:end

    return result;
  }

  @Override
  public Result<String> asString(Image image) {
    Result<String> result = error("not implemented");

    // anchor:custom-asString:start
    if (image == null) {
      result = success("");
    } else {
      result = success(image.getValue());
    }
    // anchor:custom-asString:end

    return result;
  }

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
