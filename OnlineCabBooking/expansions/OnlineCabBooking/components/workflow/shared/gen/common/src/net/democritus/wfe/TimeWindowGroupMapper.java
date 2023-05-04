package net.democritus.wfe;

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
import net.democritus.wfe.TimeWindowDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class TimeWindowGroupMapper implements IDataElementMapper<TimeWindowGroupDetails> {
  private static final Logger logger = LoggerFactory.getLogger(TimeWindowGroupMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private TimeWindowDataRefConverter timeWindowDataRefConverter = new TimeWindowDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<TimeWindowGroupDetails> parameter) {
    TimeWindowGroupDetails timeWindowGroupDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (timeWindowGroupDetails.getName() != null) {
        map.put("name", timeWindowGroupDetails.getName());
      }

      if (timeWindowGroupDetails.getVisible() != null) {
        map.put("visible", timeWindowGroupDetails.getVisible());
      }
      List<String> timeWindowsList = new ArrayList<String>();
      for (DataRef timeWindowsRef : timeWindowGroupDetails.getTimeWindows()) {
        Result<String> timeWindowsKey = timeWindowDataRefConverter.asString(timeWindowsRef);
        if (timeWindowsKey.isError()) {
          return Result.error("timeWindows: " + timeWindowsKey.getMessage());
        }
        timeWindowsList.add(timeWindowsKey.getValue());
      }
      map.put("timeWindows", StringUtil.join(listSeparator, timeWindowsList));
      // anchor:convert-fields-to-map:end

      // @anchor:fields-to-map:start
      // @anchor:fields-to-map:end
      // anchor:custom-fields-to-map:start
      // anchor:custom-fields-to-map:end

      return Result.success(map);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance " + timeWindowGroupDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<TimeWindowGroupDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      TimeWindowGroupDetails timeWindowGroupDetails = new TimeWindowGroupDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        timeWindowGroupDetails.setName(nameValueFromMap);
      }

      String visibleValueFromMap = map.get("visible");
      if (visibleValueFromMap != null && !visibleValueFromMap.isEmpty()) {
        timeWindowGroupDetails.setVisible(visibleValueFromMap);
      }
      String timeWindowsValueFromMap = map.get("timeWindows");
      if (timeWindowsValueFromMap != null && !timeWindowsValueFromMap.isEmpty()) {
        List<DataRef> timeWindowsList = new ArrayList<DataRef>();
        for (String key : timeWindowsValueFromMap.split(listSeparator)) {
          Result<DataRef> result = timeWindowDataRefConverter.fromString(key);
          if (result.isError()) {
            return Result.error("timeWindows: " + result.getMessage());
          }
          timeWindowsList.add(result.getValue());
        }
        timeWindowGroupDetails.setTimeWindows(timeWindowsList);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(timeWindowGroupDetails);
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
