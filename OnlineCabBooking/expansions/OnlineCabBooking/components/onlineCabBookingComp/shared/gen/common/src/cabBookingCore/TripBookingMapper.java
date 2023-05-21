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
import java.util.Date;



// anchor:valueType-imports:end

// anchor:dataRef-imports:start
import cabBookingCore.CustomerDataRefConverter;
import cabBookingCore.DriverDataRefConverter;
import cabBookingCore.AddressDataRefConverter;
import cabBookingCore.PaymentDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class TripBookingMapper implements IDataElementMapper<TripBookingDetails> {
  private static final Logger logger = LoggerFactory.getLogger(TripBookingMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private CustomerDataRefConverter customerDataRefConverter = new CustomerDataRefConverter();
  private DriverDataRefConverter driverDataRefConverter = new DriverDataRefConverter();
  private AddressDataRefConverter addressDataRefConverter = new AddressDataRefConverter();
  private PaymentDataRefConverter paymentDataRefConverter = new PaymentDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<TripBookingDetails> parameter) {
    TripBookingDetails tripBookingDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (tripBookingDetails.getFromDateTime() != null) {
        Result<String> fromDateTime = DateStringMapper.formatDate(tripBookingDetails.getFromDateTime());
        if (fromDateTime.isError()) {
          return Result.error("fromDateTime: " + fromDateTime.getMessage());
        }
        map.put("fromDateTime", fromDateTime.getValue());
      }

      if (tripBookingDetails.getToDateTime() != null) {
        Result<String> toDateTime = DateStringMapper.formatDate(tripBookingDetails.getToDateTime());
        if (toDateTime.isError()) {
          return Result.error("toDateTime: " + toDateTime.getMessage());
        }
        map.put("toDateTime", toDateTime.getValue());
      }

      if (tripBookingDetails.getKm() != null) {
        map.put("km", String.valueOf(tripBookingDetails.getKm()));
      }

      if (tripBookingDetails.getStatus() != null) {
        map.put("status", tripBookingDetails.getStatus());
      }
      if (DataRefValidation.isDataRefDefined(tripBookingDetails.getCustomer())) {
        Result<String> customer = customerDataRefConverter.asString(tripBookingDetails.getCustomer());
        if (customer.isError()) {
          return Result.error("customer: " + customer.getMessage());
        }
        map.put("customer", customer.getValue());
      }

      if (DataRefValidation.isDataRefDefined(tripBookingDetails.getDriver())) {
        Result<String> driver = driverDataRefConverter.asString(tripBookingDetails.getDriver());
        if (driver.isError()) {
          return Result.error("driver: " + driver.getMessage());
        }
        map.put("driver", driver.getValue());
      }

      if (DataRefValidation.isDataRefDefined(tripBookingDetails.getFromLocation())) {
        Result<String> fromLocation = addressDataRefConverter.asString(tripBookingDetails.getFromLocation());
        if (fromLocation.isError()) {
          return Result.error("fromLocation: " + fromLocation.getMessage());
        }
        map.put("fromLocation", fromLocation.getValue());
      }

      if (DataRefValidation.isDataRefDefined(tripBookingDetails.getToLocation())) {
        Result<String> toLocation = addressDataRefConverter.asString(tripBookingDetails.getToLocation());
        if (toLocation.isError()) {
          return Result.error("toLocation: " + toLocation.getMessage());
        }
        map.put("toLocation", toLocation.getValue());
      }

      if (DataRefValidation.isDataRefDefined(tripBookingDetails.getPayment())) {
        Result<String> payment = paymentDataRefConverter.asString(tripBookingDetails.getPayment());
        if (payment.isError()) {
          return Result.error("payment: " + payment.getMessage());
        }
        map.put("payment", payment.getValue());
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
          "Unexpected error while mapping instance " + tripBookingDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<TripBookingDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      TripBookingDetails tripBookingDetails = new TripBookingDetails();

      // anchor:convert-map-to-fields:start
      String fromDateTimeValueFromMap = map.get("fromDateTime");
      if (fromDateTimeValueFromMap != null && !fromDateTimeValueFromMap.isEmpty()) {
        Result<Date> fromDateTime = DateStringMapper.parseDate(fromDateTimeValueFromMap);
        if (fromDateTime.isError()) {
          return Result.error("fromDateTime: " + fromDateTime.getMessage());
        }
        tripBookingDetails.setFromDateTime(fromDateTime.getValue());
      }

      String toDateTimeValueFromMap = map.get("toDateTime");
      if (toDateTimeValueFromMap != null && !toDateTimeValueFromMap.isEmpty()) {
        Result<Date> toDateTime = DateStringMapper.parseDate(toDateTimeValueFromMap);
        if (toDateTime.isError()) {
          return Result.error("toDateTime: " + toDateTime.getMessage());
        }
        tripBookingDetails.setToDateTime(toDateTime.getValue());
      }

      String kmValueFromMap = map.get("km");
      if (kmValueFromMap != null && !kmValueFromMap.isEmpty()) {
        try {
          Double km = Double.valueOf(kmValueFromMap);
          tripBookingDetails.setKm(km);
        } catch (NumberFormatException e) {
          return Result.error("km: Expected number, but got '" + kmValueFromMap + "'");
        }
      }

      String statusValueFromMap = map.get("status");
      if (statusValueFromMap != null && !statusValueFromMap.isEmpty()) {
        tripBookingDetails.setStatus(statusValueFromMap);
      }
      String customerValueFromMap = map.get("customer");
      Result<DataRef> customer = customerDataRefConverter.fromString(customerValueFromMap);
      if (customer.isError()) {
        return Result.error("customer: " + customer.getMessage());
      }
      tripBookingDetails.setCustomer(customer.getValue());

      String driverValueFromMap = map.get("driver");
      Result<DataRef> driver = driverDataRefConverter.fromString(driverValueFromMap);
      if (driver.isError()) {
        return Result.error("driver: " + driver.getMessage());
      }
      tripBookingDetails.setDriver(driver.getValue());

      String fromLocationValueFromMap = map.get("fromLocation");
      Result<DataRef> fromLocation = addressDataRefConverter.fromString(fromLocationValueFromMap);
      if (fromLocation.isError()) {
        return Result.error("fromLocation: " + fromLocation.getMessage());
      }
      tripBookingDetails.setFromLocation(fromLocation.getValue());

      String toLocationValueFromMap = map.get("toLocation");
      Result<DataRef> toLocation = addressDataRefConverter.fromString(toLocationValueFromMap);
      if (toLocation.isError()) {
        return Result.error("toLocation: " + toLocation.getMessage());
      }
      tripBookingDetails.setToLocation(toLocation.getValue());

      String paymentValueFromMap = map.get("payment");
      Result<DataRef> payment = paymentDataRefConverter.fromString(paymentValueFromMap);
      if (payment.isError()) {
        return Result.error("payment: " + payment.getMessage());
      }
      tripBookingDetails.setPayment(payment.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(tripBookingDetails);
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
