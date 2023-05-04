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
import cabBookingCore.AddressDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class PersonMapper implements IDataElementMapper<PersonDetails> {
  private static final Logger logger = LoggerFactory.getLogger(PersonMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private AddressDataRefConverter addressDataRefConverter = new AddressDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<PersonDetails> parameter) {
    PersonDetails personDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (personDetails.getUsername() != null) {
        map.put("username", personDetails.getUsername());
      }

      if (personDetails.getPassword() != null) {
        map.put("password", personDetails.getPassword());
      }

      if (personDetails.getEmail() != null) {
        map.put("email", personDetails.getEmail());
      }

      if (personDetails.getMobile() != null) {
        map.put("mobile", personDetails.getMobile());
      }
      if (DataRefValidation.isDataRefDefined(personDetails.getAddress())) {
        Result<String> address = addressDataRefConverter.asString(personDetails.getAddress());
        if (address.isError()) {
          return Result.error("address: " + address.getMessage());
        }
        map.put("address", address.getValue());
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
          "Unexpected error while mapping instance " + personDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<PersonDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      PersonDetails personDetails = new PersonDetails();

      // anchor:convert-map-to-fields:start
      String usernameValueFromMap = map.get("username");
      if (usernameValueFromMap != null && !usernameValueFromMap.isEmpty()) {
        personDetails.setUsername(usernameValueFromMap);
      }

      String passwordValueFromMap = map.get("password");
      if (passwordValueFromMap != null && !passwordValueFromMap.isEmpty()) {
        personDetails.setPassword(passwordValueFromMap);
      }

      String emailValueFromMap = map.get("email");
      if (emailValueFromMap != null && !emailValueFromMap.isEmpty()) {
        personDetails.setEmail(emailValueFromMap);
      }

      String mobileValueFromMap = map.get("mobile");
      if (mobileValueFromMap != null && !mobileValueFromMap.isEmpty()) {
        personDetails.setMobile(mobileValueFromMap);
      }
      String addressValueFromMap = map.get("address");
      Result<DataRef> address = addressDataRefConverter.fromString(addressValueFromMap);
      if (address.isError()) {
        return Result.error("address: " + address.getMessage());
      }
      personDetails.setAddress(address.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(personDetails);
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
