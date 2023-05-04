package net.democritus.acl;

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
import net.democritus.usr.ProfileDataRefConverter;
import net.democritus.usr.UserDataRefConverter;
import net.democritus.usr.UserGroupDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class DataAccessMapper implements IDataElementMapper<DataAccessDetails> {
  private static final Logger logger = LoggerFactory.getLogger(DataAccessMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private ProfileDataRefConverter profileDataRefConverter = new ProfileDataRefConverter();
  private UserDataRefConverter userDataRefConverter = new UserDataRefConverter();
  private UserGroupDataRefConverter userGroupDataRefConverter = new UserGroupDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<DataAccessDetails> parameter) {
    DataAccessDetails dataAccessDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (dataAccessDetails.getElement() != null) {
        map.put("element", dataAccessDetails.getElement());
      }

      if (dataAccessDetails.getTarget() != null) {
        map.put("target", dataAccessDetails.getTarget());
      }

      if (dataAccessDetails.getFunctionality() != null) {
        map.put("functionality", dataAccessDetails.getFunctionality());
      }

      if (dataAccessDetails.getAuthorized() != null) {
        map.put("authorized", dataAccessDetails.getAuthorized());
      }

      if (dataAccessDetails.getLastModifiedAt() != null) {
        Result<String> lastModifiedAt = DateStringMapper.formatDate(dataAccessDetails.getLastModifiedAt());
        if (lastModifiedAt.isError()) {
          return Result.error("lastModifiedAt: " + lastModifiedAt.getMessage());
        }
        map.put("lastModifiedAt", lastModifiedAt.getValue());
      }

      if (dataAccessDetails.getEnteredAt() != null) {
        Result<String> enteredAt = DateStringMapper.formatDate(dataAccessDetails.getEnteredAt());
        if (enteredAt.isError()) {
          return Result.error("enteredAt: " + enteredAt.getMessage());
        }
        map.put("enteredAt", enteredAt.getValue());
      }

      if (dataAccessDetails.getDisabled() != null) {
        map.put("disabled", dataAccessDetails.getDisabled());
      }
      if (DataRefValidation.isDataRefDefined(dataAccessDetails.getForProfile())) {
        Result<String> forProfile = profileDataRefConverter.asString(dataAccessDetails.getForProfile());
        if (forProfile.isError()) {
          return Result.error("forProfile: " + forProfile.getMessage());
        }
        map.put("forProfile", forProfile.getValue());
      }

      if (DataRefValidation.isDataRefDefined(dataAccessDetails.getForUser())) {
        Result<String> forUser = userDataRefConverter.asString(dataAccessDetails.getForUser());
        if (forUser.isError()) {
          return Result.error("forUser: " + forUser.getMessage());
        }
        map.put("forUser", forUser.getValue());
      }

      if (DataRefValidation.isDataRefDefined(dataAccessDetails.getForUserGroup())) {
        Result<String> forUserGroup = userGroupDataRefConverter.asString(dataAccessDetails.getForUserGroup());
        if (forUserGroup.isError()) {
          return Result.error("forUserGroup: " + forUserGroup.getMessage());
        }
        map.put("forUserGroup", forUserGroup.getValue());
      }
      List<String> userGroupsList = new ArrayList<String>();
      for (DataRef userGroupsRef : dataAccessDetails.getUserGroups()) {
        Result<String> userGroupsKey = userGroupDataRefConverter.asString(userGroupsRef);
        if (userGroupsKey.isError()) {
          return Result.error("userGroups: " + userGroupsKey.getMessage());
        }
        userGroupsList.add(userGroupsKey.getValue());
      }
      map.put("userGroups", StringUtil.join(listSeparator, userGroupsList));
      // anchor:convert-fields-to-map:end

      // @anchor:fields-to-map:start
      // @anchor:fields-to-map:end
      // anchor:custom-fields-to-map:start
      // anchor:custom-fields-to-map:end

      return Result.success(map);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance " + dataAccessDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<DataAccessDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      DataAccessDetails dataAccessDetails = new DataAccessDetails();

      // anchor:convert-map-to-fields:start
      String elementValueFromMap = map.get("element");
      if (elementValueFromMap != null && !elementValueFromMap.isEmpty()) {
        dataAccessDetails.setElement(elementValueFromMap);
      }

      String targetValueFromMap = map.get("target");
      if (targetValueFromMap != null && !targetValueFromMap.isEmpty()) {
        dataAccessDetails.setTarget(targetValueFromMap);
      }

      String functionalityValueFromMap = map.get("functionality");
      if (functionalityValueFromMap != null && !functionalityValueFromMap.isEmpty()) {
        dataAccessDetails.setFunctionality(functionalityValueFromMap);
      }

      String authorizedValueFromMap = map.get("authorized");
      if (authorizedValueFromMap != null && !authorizedValueFromMap.isEmpty()) {
        dataAccessDetails.setAuthorized(authorizedValueFromMap);
      }

      String lastModifiedAtValueFromMap = map.get("lastModifiedAt");
      if (lastModifiedAtValueFromMap != null && !lastModifiedAtValueFromMap.isEmpty()) {
        Result<Date> lastModifiedAt = DateStringMapper.parseDate(lastModifiedAtValueFromMap);
        if (lastModifiedAt.isError()) {
          return Result.error("lastModifiedAt: " + lastModifiedAt.getMessage());
        }
        dataAccessDetails.setLastModifiedAt(lastModifiedAt.getValue());
      }

      String enteredAtValueFromMap = map.get("enteredAt");
      if (enteredAtValueFromMap != null && !enteredAtValueFromMap.isEmpty()) {
        Result<Date> enteredAt = DateStringMapper.parseDate(enteredAtValueFromMap);
        if (enteredAt.isError()) {
          return Result.error("enteredAt: " + enteredAt.getMessage());
        }
        dataAccessDetails.setEnteredAt(enteredAt.getValue());
      }

      String disabledValueFromMap = map.get("disabled");
      if (disabledValueFromMap != null && !disabledValueFromMap.isEmpty()) {
        dataAccessDetails.setDisabled(disabledValueFromMap);
      }
      String forProfileValueFromMap = map.get("forProfile");
      Result<DataRef> forProfile = profileDataRefConverter.fromString(forProfileValueFromMap);
      if (forProfile.isError()) {
        return Result.error("forProfile: " + forProfile.getMessage());
      }
      dataAccessDetails.setForProfile(forProfile.getValue());

      String forUserValueFromMap = map.get("forUser");
      Result<DataRef> forUser = userDataRefConverter.fromString(forUserValueFromMap);
      if (forUser.isError()) {
        return Result.error("forUser: " + forUser.getMessage());
      }
      dataAccessDetails.setForUser(forUser.getValue());

      String forUserGroupValueFromMap = map.get("forUserGroup");
      Result<DataRef> forUserGroup = userGroupDataRefConverter.fromString(forUserGroupValueFromMap);
      if (forUserGroup.isError()) {
        return Result.error("forUserGroup: " + forUserGroup.getMessage());
      }
      dataAccessDetails.setForUserGroup(forUserGroup.getValue());
      String userGroupsValueFromMap = map.get("userGroups");
      if (userGroupsValueFromMap != null && !userGroupsValueFromMap.isEmpty()) {
        List<DataRef> userGroupsList = new ArrayList<DataRef>();
        for (String key : userGroupsValueFromMap.split(listSeparator)) {
          Result<DataRef> result = userGroupDataRefConverter.fromString(key);
          if (result.isError()) {
            return Result.error("userGroups: " + result.getMessage());
          }
          userGroupsList.add(result.getValue());
        }
        dataAccessDetails.setUserGroups(userGroupsList);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(dataAccessDetails);
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
