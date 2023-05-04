package net.democritus.sys;

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

public class ParamTargetValueMapper implements IDataElementMapper<ParamTargetValueDetails> {
  private static final Logger logger = LoggerFactory.getLogger(ParamTargetValueMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<ParamTargetValueDetails> parameter) {
    ParamTargetValueDetails paramTargetValueDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (paramTargetValueDetails.getParam() != null) {
        map.put("param", paramTargetValueDetails.getParam());
      }

      if (paramTargetValueDetails.getTarget() != null) {
        map.put("target", paramTargetValueDetails.getTarget());
      }

      if (paramTargetValueDetails.getValue() != null) {
        map.put("value", paramTargetValueDetails.getValue());
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
          "Unexpected error while mapping instance " + paramTargetValueDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<ParamTargetValueDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      ParamTargetValueDetails paramTargetValueDetails = new ParamTargetValueDetails();

      // anchor:convert-map-to-fields:start
      String paramValueFromMap = map.get("param");
      if (paramValueFromMap != null && !paramValueFromMap.isEmpty()) {
        paramTargetValueDetails.setParam(paramValueFromMap);
      }

      String targetValueFromMap = map.get("target");
      if (targetValueFromMap != null && !targetValueFromMap.isEmpty()) {
        paramTargetValueDetails.setTarget(targetValueFromMap);
      }

      String valueValueFromMap = map.get("value");
      if (valueValueFromMap != null && !valueValueFromMap.isEmpty()) {
        paramTargetValueDetails.setValue(valueValueFromMap);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(paramTargetValueDetails);
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
