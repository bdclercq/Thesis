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
import cabBookingCore.CabDataRefConverter;
import cabBookingCore.TripBookingDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class DriverMapper implements IDataElementMapper<DriverDetails> {
  private static final Logger logger = LoggerFactory.getLogger(DriverMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private CabDataRefConverter cabDataRefConverter = new CabDataRefConverter();
  private TripBookingDataRefConverter tripBookingDataRefConverter = new TripBookingDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<DriverDetails> parameter) {
    DriverDetails driverDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (driverDetails.getLicenseNo() != null) {
        map.put("licenseNo", String.valueOf(driverDetails.getLicenseNo()));
      }

      if (driverDetails.getRating() != null) {
        map.put("rating", String.valueOf(driverDetails.getRating()));
      }

      if (driverDetails.getIsAvailable() != null) {
        map.put("isAvailable", String.valueOf(driverDetails.getIsAvailable()));
      }

      if (driverDetails.getName() != null) {
        map.put("name", driverDetails.getName());
      }
      if (DataRefValidation.isDataRefDefined(driverDetails.getCab())) {
        Result<String> cab = cabDataRefConverter.asString(driverDetails.getCab());
        if (cab.isError()) {
          return Result.error("cab: " + cab.getMessage());
        }
        map.put("cab", cab.getValue());
      }

      if (DataRefValidation.isDataRefDefined(driverDetails.getTripBooking())) {
        Result<String> tripBooking = tripBookingDataRefConverter.asString(driverDetails.getTripBooking());
        if (tripBooking.isError()) {
          return Result.error("tripBooking: " + tripBooking.getMessage());
        }
        map.put("tripBooking", tripBooking.getValue());
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
          "Unexpected error while mapping instance " + driverDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<DriverDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      DriverDetails driverDetails = new DriverDetails();

      // anchor:convert-map-to-fields:start
      String licenseNoValueFromMap = map.get("licenseNo");
      if (licenseNoValueFromMap != null && !licenseNoValueFromMap.isEmpty()) {
        try {
          Integer licenseNo = Integer.valueOf(licenseNoValueFromMap);
          driverDetails.setLicenseNo(licenseNo);
        } catch (NumberFormatException e) {
          return Result.error("licenseNo: Expected number, but got '" + licenseNoValueFromMap + "'");
        }
      }

      String ratingValueFromMap = map.get("rating");
      if (ratingValueFromMap != null && !ratingValueFromMap.isEmpty()) {
        try {
          Double rating = Double.valueOf(ratingValueFromMap);
          driverDetails.setRating(rating);
        } catch (NumberFormatException e) {
          return Result.error("rating: Expected number, but got '" + ratingValueFromMap + "'");
        }
      }

      String isAvailableValueFromMap = map.get("isAvailable");
      if (isAvailableValueFromMap != null && !isAvailableValueFromMap.isEmpty()) {
        Boolean isAvailable = Boolean.valueOf(isAvailableValueFromMap);
        driverDetails.setIsAvailable(isAvailable);
      }

      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        driverDetails.setName(nameValueFromMap);
      }
      String cabValueFromMap = map.get("cab");
      Result<DataRef> cab = cabDataRefConverter.fromString(cabValueFromMap);
      if (cab.isError()) {
        return Result.error("cab: " + cab.getMessage());
      }
      driverDetails.setCab(cab.getValue());

      String tripBookingValueFromMap = map.get("tripBooking");
      Result<DataRef> tripBooking = tripBookingDataRefConverter.fromString(tripBookingValueFromMap);
      if (tripBooking.isError()) {
        return Result.error("tripBooking: " + tripBooking.getMessage());
      }
      driverDetails.setTripBooking(tripBooking.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(driverDetails);
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
