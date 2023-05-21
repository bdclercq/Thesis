package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.palver.util.StringUtil;
import net.democritus.mapping.DateStringMapper;
import net.democritus.mapping.Base64;
import net.democritus.mapping.IDataElementMapper;
import net.democritus.sys.DataRef;
import net.democritus.sys.DataRefWithFunctionalKey;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.ParameterContext;
import net.democritus.validation.Result;
import net.democritus.validation.ValidationResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:dataRef-imports:start
import cabBookingCore.CarTypeDataRefConverter;
import cabBookingCore.DriverDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class CabMapper implements IDataElementMapper<CabDetails> {
  private static final Logger logger = LoggerFactory.getLogger(CabMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private CarTypeDataRefConverter carTypeDataRefConverter = new CarTypeDataRefConverter();
  private DriverDataRefConverter driverDataRefConverter = new DriverDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<CabDetails> parameter) {
    CabDetails cabDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (cabDetails.getRatePerKm() != null) {
        map.put("ratePerKm", String.valueOf(cabDetails.getRatePerKm()));
      }

      if (cabDetails.getName() != null) {
        map.put("name", cabDetails.getName());
      }
      if (DataRefValidation.isDataRefDefined(cabDetails.getCarType())) {
        Result<String> carType = carTypeDataRefConverter.asString(cabDetails.getCarType());
        if (carType.isError()) {
          return Result.error("carType: " + carType.getMessage());
        }
        map.put("carType", carType.getValue());
      }

      if (DataRefValidation.isDataRefDefined(cabDetails.getDriver())) {
        Result<String> driver = driverDataRefConverter.asString(cabDetails.getDriver());
        if (driver.isError()) {
          return Result.error("driver: " + driver.getMessage());
        }
        map.put("driver", driver.getValue());
      }
      // anchor:convert-fields-to-map:end

      // @anchor:fields-to-map:start
      // @anchor:fields-to-map:end
      // anchor:custom-fields-to-map:start
      // anchor:custom-fields-to-map:end

      return Result.success(map);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance " + cabDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<CabDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      CabDetails cabDetails = new CabDetails();

      // anchor:convert-map-to-fields:start
      String ratePerKmValueFromMap = map.get("ratePerKm");
      if (ratePerKmValueFromMap != null && !ratePerKmValueFromMap.isEmpty()) {
        try {
          Integer ratePerKm = Integer.valueOf(ratePerKmValueFromMap);
          cabDetails.setRatePerKm(ratePerKm);
        } catch (NumberFormatException e) {
          return Result.error("ratePerKm: Expected number, but got '" + ratePerKmValueFromMap + "'");
        }
      }

      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        cabDetails.setName(nameValueFromMap);
      }
      String carTypeValueFromMap = map.get("carType");
      Result<DataRef> carType = carTypeDataRefConverter.fromString(carTypeValueFromMap);
      if (carType.isError()) {
        return Result.error("carType: " + carType.getMessage());
      }
      cabDetails.setCarType(carType.getValue());

      String driverValueFromMap = map.get("driver");
      Result<DataRef> driver = driverDataRefConverter.fromString(driverValueFromMap);
      if (driver.isError()) {
        return Result.error("driver: " + driver.getMessage());
      }
      cabDetails.setDriver(driver.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(cabDetails);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance to details", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

  public void setListSeparator(String listSeparator) {
    this.listSeparator = listSeparator;
  }

}
