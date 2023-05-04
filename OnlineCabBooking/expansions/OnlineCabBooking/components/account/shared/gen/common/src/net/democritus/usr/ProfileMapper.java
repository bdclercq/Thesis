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
// anchor:valueType-imports:end

// anchor:dataRef-imports:start
import net.democritus.usr.menu.ScreenDataRefConverter;
import net.democritus.usr.UserGroupDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class ProfileMapper implements IDataElementMapper<ProfileDetails> {
  private static final Logger logger = LoggerFactory.getLogger(ProfileMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private ScreenDataRefConverter screenDataRefConverter = new ScreenDataRefConverter();
  private UserGroupDataRefConverter userGroupDataRefConverter = new UserGroupDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<ProfileDetails> parameter) {
    ProfileDetails profileDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (profileDetails.getName() != null) {
        map.put("name", profileDetails.getName());
      }

      if (profileDetails.getWeight() != null) {
        map.put("weight", String.valueOf(profileDetails.getWeight()));
      }
      if (DataRefValidation.isDataRefDefined(profileDetails.getUserGroup())) {
        Result<String> userGroup = userGroupDataRefConverter.asString(profileDetails.getUserGroup());
        if (userGroup.isError()) {
          return Result.error("userGroup: " + userGroup.getMessage());
        }
        map.put("userGroup", userGroup.getValue());
      }
      List<String> screensList = new ArrayList<String>();
      for (DataRef screensRef : profileDetails.getScreens()) {
        Result<String> screensKey = screenDataRefConverter.asString(screensRef);
        if (screensKey.isError()) {
          return Result.error("screens: " + screensKey.getMessage());
        }
        screensList.add(screensKey.getValue());
      }
      map.put("screens", StringUtil.join(listSeparator, screensList));
      // anchor:convert-fields-to-map:end

      // @anchor:fields-to-map:start
      // @anchor:fields-to-map:end
      // anchor:custom-fields-to-map:start
      // anchor:custom-fields-to-map:end

      return Result.success(map);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
          logger.error(
          "Unexpected error while mapping instance " + profileDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<ProfileDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      ProfileDetails profileDetails = new ProfileDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        profileDetails.setName(nameValueFromMap);
      }

      String weightValueFromMap = map.get("weight");
      if (weightValueFromMap != null && !weightValueFromMap.isEmpty()) {
        try {
          Integer weight = Integer.valueOf(weightValueFromMap);
          profileDetails.setWeight(weight);
        } catch (NumberFormatException e) {
          return Result.error("weight: Expected number, but got '" + weightValueFromMap + "'");
        }
      }
      String userGroupValueFromMap = map.get("userGroup");
      Result<DataRef> userGroup = userGroupDataRefConverter.fromString(userGroupValueFromMap);
      if (userGroup.isError()) {
        return Result.error("userGroup: " + userGroup.getMessage());
      }
      profileDetails.setUserGroup(userGroup.getValue());
      String screensValueFromMap = map.get("screens");
      if (screensValueFromMap != null && !screensValueFromMap.isEmpty()) {
        List<DataRef> screensList = new ArrayList<DataRef>();
        for (String key : screensValueFromMap.split(listSeparator)) {
          Result<DataRef> result = screenDataRefConverter.fromString(key);
          if (result.isError()) {
            return Result.error("screens: " + result.getMessage());
          }
          screensList.add(result.getValue());
        }
        profileDetails.setScreens(screensList);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(profileDetails);
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
