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
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class AddressMapper implements IDataElementMapper<AddressDetails> {
  private static final Logger logger = LoggerFactory.getLogger(AddressMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<AddressDetails> parameter) {
    AddressDetails addressDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (addressDetails.getState() != null) {
        map.put("state", addressDetails.getState());
      }

      if (addressDetails.getCity() != null) {
        map.put("city", addressDetails.getCity());
      }

      if (addressDetails.getPincode() != null) {
        map.put("pincode", addressDetails.getPincode());
      }

      if (addressDetails.getStreet() != null) {
        map.put("street", addressDetails.getStreet());
      }

      if (addressDetails.getHouseNumber() != null) {
        map.put("houseNumber", String.valueOf(addressDetails.getHouseNumber()));
      }

      if (addressDetails.getName() != null) {
        map.put("name", addressDetails.getName());
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
          "Unexpected error while mapping instance " + addressDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<AddressDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      AddressDetails addressDetails = new AddressDetails();

      // anchor:convert-map-to-fields:start
      String stateValueFromMap = map.get("state");
      if (stateValueFromMap != null && !stateValueFromMap.isEmpty()) {
        addressDetails.setState(stateValueFromMap);
      }

      String cityValueFromMap = map.get("city");
      if (cityValueFromMap != null && !cityValueFromMap.isEmpty()) {
        addressDetails.setCity(cityValueFromMap);
      }

      String pincodeValueFromMap = map.get("pincode");
      if (pincodeValueFromMap != null && !pincodeValueFromMap.isEmpty()) {
        addressDetails.setPincode(pincodeValueFromMap);
      }

      String streetValueFromMap = map.get("street");
      if (streetValueFromMap != null && !streetValueFromMap.isEmpty()) {
        addressDetails.setStreet(streetValueFromMap);
      }

      String houseNumberValueFromMap = map.get("houseNumber");
      if (houseNumberValueFromMap != null && !houseNumberValueFromMap.isEmpty()) {
        try {
          Integer houseNumber = Integer.valueOf(houseNumberValueFromMap);
          addressDetails.setHouseNumber(houseNumber);
        } catch (NumberFormatException e) {
          return Result.error("houseNumber: Expected number, but got '" + houseNumberValueFromMap + "'");
        }
      }

      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        addressDetails.setName(nameValueFromMap);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(addressDetails);
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
