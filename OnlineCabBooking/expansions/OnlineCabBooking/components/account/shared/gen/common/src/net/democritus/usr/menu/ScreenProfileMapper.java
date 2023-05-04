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
import net.democritus.usr.menu.ScreenDataRefConverter;
import net.democritus.usr.ProfileDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class ScreenProfileMapper implements IDataElementMapper<ScreenProfileDetails> {
  private static final Logger logger = LoggerFactory.getLogger(ScreenProfileMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private ScreenDataRefConverter screenDataRefConverter = new ScreenDataRefConverter();
  private ProfileDataRefConverter profileDataRefConverter = new ProfileDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<ScreenProfileDetails> parameter) {
    ScreenProfileDetails screenProfileDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (DataRefValidation.isDataRefDefined(screenProfileDetails.getScreen())) {
        Result<String> screen = screenDataRefConverter.asString(screenProfileDetails.getScreen());
        if (screen.isError()) {
          return Result.error("screen: " + screen.getMessage());
        }
        map.put("screen", screen.getValue());
      }

      if (DataRefValidation.isDataRefDefined(screenProfileDetails.getProfile())) {
        Result<String> profile = profileDataRefConverter.asString(screenProfileDetails.getProfile());
        if (profile.isError()) {
          return Result.error("profile: " + profile.getMessage());
        }
        map.put("profile", profile.getValue());
      }
      List<String> screensList = new ArrayList<String>();
      for (DataRef screensRef : screenProfileDetails.getScreens()) {
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
          "Unexpected error while mapping instance " + screenProfileDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<ScreenProfileDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      ScreenProfileDetails screenProfileDetails = new ScreenProfileDetails();

      // anchor:convert-map-to-fields:start
      String screenValueFromMap = map.get("screen");
      Result<DataRef> screen = screenDataRefConverter.fromString(screenValueFromMap);
      if (screen.isError()) {
        return Result.error("screen: " + screen.getMessage());
      }
      screenProfileDetails.setScreen(screen.getValue());

      String profileValueFromMap = map.get("profile");
      Result<DataRef> profile = profileDataRefConverter.fromString(profileValueFromMap);
      if (profile.isError()) {
        return Result.error("profile: " + profile.getMessage());
      }
      screenProfileDetails.setProfile(profile.getValue());
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
        screenProfileDetails.setScreens(screensList);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(screenProfileDetails);
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
