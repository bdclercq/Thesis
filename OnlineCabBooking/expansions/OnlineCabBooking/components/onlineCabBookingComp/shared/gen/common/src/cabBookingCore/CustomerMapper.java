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
import cabBookingCore.PersonDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class CustomerMapper implements IDataElementMapper<CustomerDetails> {
  private static final Logger logger = LoggerFactory.getLogger(CustomerMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private PersonDataRefConverter personDataRefConverter = new PersonDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<CustomerDetails> parameter) {
    CustomerDetails customerDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (customerDetails.getJourneyStatus() != null) {
        map.put("journeyStatus", String.valueOf(customerDetails.getJourneyStatus()));
      }
      if (DataRefValidation.isDataRefDefined(customerDetails.getPerson())) {
        Result<String> person = personDataRefConverter.asString(customerDetails.getPerson());
        if (person.isError()) {
          return Result.error("person: " + person.getMessage());
        }
        map.put("person", person.getValue());
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
          "Unexpected error while mapping instance " + customerDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<CustomerDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      CustomerDetails customerDetails = new CustomerDetails();

      // anchor:convert-map-to-fields:start
      String journeyStatusValueFromMap = map.get("journeyStatus");
      if (journeyStatusValueFromMap != null && !journeyStatusValueFromMap.isEmpty()) {
        Boolean journeyStatus = Boolean.valueOf(journeyStatusValueFromMap);
        customerDetails.setJourneyStatus(journeyStatus);
      }
      String personValueFromMap = map.get("person");
      Result<DataRef> person = personDataRefConverter.fromString(personValueFromMap);
      if (person.isError()) {
        return Result.error("person: " + person.getMessage());
      }
      customerDetails.setPerson(person.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(customerDetails);
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
