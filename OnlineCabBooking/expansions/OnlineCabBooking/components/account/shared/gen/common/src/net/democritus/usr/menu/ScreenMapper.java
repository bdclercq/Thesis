package net.democritus.usr.menu;

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
import net.democritus.component.ComponentDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class ScreenMapper implements IDataElementMapper<ScreenDetails> {
  private static final Logger logger = LoggerFactory.getLogger(ScreenMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private ComponentDataRefConverter componentDataRefConverter = new ComponentDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<ScreenDetails> parameter) {
    ScreenDetails screenDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (screenDetails.getName() != null) {
        map.put("name", screenDetails.getName());
      }

      if (screenDetails.getLink() != null) {
        map.put("link", screenDetails.getLink());
      }

      if (screenDetails.getSortOrder() != null) {
        map.put("sortOrder", String.valueOf(screenDetails.getSortOrder()));
      }
      if (DataRefValidation.isDataRefDefined(screenDetails.getComponent())) {
        Result<String> component = componentDataRefConverter.asString(screenDetails.getComponent());
        if (component.isError()) {
          return Result.error("component: " + component.getMessage());
        }
        map.put("component", component.getValue());
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
          "Unexpected error while mapping instance " + screenDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<ScreenDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      ScreenDetails screenDetails = new ScreenDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        screenDetails.setName(nameValueFromMap);
      }

      String linkValueFromMap = map.get("link");
      if (linkValueFromMap != null && !linkValueFromMap.isEmpty()) {
        screenDetails.setLink(linkValueFromMap);
      }

      String sortOrderValueFromMap = map.get("sortOrder");
      if (sortOrderValueFromMap != null && !sortOrderValueFromMap.isEmpty()) {
        try {
          Integer sortOrder = Integer.valueOf(sortOrderValueFromMap);
          screenDetails.setSortOrder(sortOrder);
        } catch (NumberFormatException e) {
          return Result.error("sortOrder: Expected number, but got '" + sortOrderValueFromMap + "'");
        }
      }
      String componentValueFromMap = map.get("component");
      Result<DataRef> component = componentDataRefConverter.fromString(componentValueFromMap);
      if (component.isError()) {
        return Result.error("component: " + component.getMessage());
      }
      screenDetails.setComponent(component.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(screenDetails);
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
