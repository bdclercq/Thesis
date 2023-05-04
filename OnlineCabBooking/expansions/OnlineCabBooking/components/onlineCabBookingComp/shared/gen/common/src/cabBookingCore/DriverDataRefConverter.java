package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:functional-key-expanders:5.12.1

import net.democritus.sys.DataRef;
import net.democritus.sys.NullDataRef;
import net.democritus.validation.IConverter;
import net.democritus.validation.Result;

// @anchor:imports:start
// @anchor:imports:end
// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end
public class DriverDataRefConverter implements IConverter<DataRef> {

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:from-string:start
  @Override
  public Result<DataRef> fromString(String value) {
    try {
      Result<DataRef> result;
      if (value == null || value.isEmpty()) {
        result = Result.success(NullDataRef.EMPTY_DATA_REF);
      } else {
        result = Result.success(new DataRef(0L, value, "onlineCabBookingComp", "cabBookingCore", "Driver"));
      }
      // @anchor:fromString:start
      // @anchor:fromString:end
      // anchor:custom-fromString:start
      // anchor:custom-fromString:end
      return result;
    } catch (Exception e) {
      return Result.error(e.getMessage());
    }
  }
  // anchor:from-string:end

  // anchor:as-string:start
  @Override
  public Result<String> asString(DataRef dataRef) {
    try {
      Result<String> result = Result.success(dataRef.getName());
      // @anchor:asString:start
      // @anchor:asString:end
      // anchor:custom-asString:start
      // anchor:custom-asString:end
      return result;
    } catch(Exception e) {
      return Result.error(e.getMessage());
    }
  }
  // anchor:as-string:end

  public String getDescription() {
    // @anchor:description:start
    // @anchor:description:end
    // anchor:custom-description:start
    // anchor:custom-description:end
    return "name";
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
