package net.democritus.usr;

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
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserGroupMapper implements IDataElementMapper<UserGroupDetails> {
  private static final Logger logger = LoggerFactory.getLogger(UserGroupMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<UserGroupDetails> parameter) {
    UserGroupDetails userGroupDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (userGroupDetails.getName() != null) {
        map.put("name", userGroupDetails.getName());
      }

      if (userGroupDetails.getType() != null) {
        map.put("type", userGroupDetails.getType());
      }

      if (userGroupDetails.getLastModifiedAt() != null) {
        Result<String> lastModifiedAt = DateStringMapper.formatDate(userGroupDetails.getLastModifiedAt());
        if (lastModifiedAt.isError()) {
          return Result.error("lastModifiedAt: " + lastModifiedAt.getMessage());
        }
        map.put("lastModifiedAt", lastModifiedAt.getValue());
      }

      if (userGroupDetails.getEnteredAt() != null) {
        Result<String> enteredAt = DateStringMapper.formatDate(userGroupDetails.getEnteredAt());
        if (enteredAt.isError()) {
          return Result.error("enteredAt: " + enteredAt.getMessage());
        }
        map.put("enteredAt", enteredAt.getValue());
      }

      if (userGroupDetails.getDisabled() != null) {
        map.put("disabled", userGroupDetails.getDisabled());
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
          "Unexpected error while mapping instance " + userGroupDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<UserGroupDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      UserGroupDetails userGroupDetails = new UserGroupDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        userGroupDetails.setName(nameValueFromMap);
      }

      String typeValueFromMap = map.get("type");
      if (typeValueFromMap != null && !typeValueFromMap.isEmpty()) {
        userGroupDetails.setType(typeValueFromMap);
      }

      String lastModifiedAtValueFromMap = map.get("lastModifiedAt");
      if (lastModifiedAtValueFromMap != null && !lastModifiedAtValueFromMap.isEmpty()) {
        Result<Date> lastModifiedAt = DateStringMapper.parseDate(lastModifiedAtValueFromMap);
        if (lastModifiedAt.isError()) {
          return Result.error("lastModifiedAt: " + lastModifiedAt.getMessage());
        }
        userGroupDetails.setLastModifiedAt(lastModifiedAt.getValue());
      }

      String enteredAtValueFromMap = map.get("enteredAt");
      if (enteredAtValueFromMap != null && !enteredAtValueFromMap.isEmpty()) {
        Result<Date> enteredAt = DateStringMapper.parseDate(enteredAtValueFromMap);
        if (enteredAt.isError()) {
          return Result.error("enteredAt: " + enteredAt.getMessage());
        }
        userGroupDetails.setEnteredAt(enteredAt.getValue());
      }

      String disabledValueFromMap = map.get("disabled");
      if (disabledValueFromMap != null && !disabledValueFromMap.isEmpty()) {
        userGroupDetails.setDisabled(disabledValueFromMap);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(userGroupDetails);
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
