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
import net.democritus.usr.AccountDataRefConverter;
import net.democritus.usr.ProfileDataRefConverter;
import net.democritus.usr.UserGroupDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserMapper implements IDataElementMapper<UserDetails> {
  private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private AccountDataRefConverter accountDataRefConverter = new AccountDataRefConverter();
  private ProfileDataRefConverter profileDataRefConverter = new ProfileDataRefConverter();
  private UserGroupDataRefConverter userGroupDataRefConverter = new UserGroupDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<UserDetails> parameter) {
    UserDetails userDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (userDetails.getName() != null) {
        map.put("name", userDetails.getName());
      }

      if (userDetails.getPassword() != null) {
        map.put("password", userDetails.getPassword());
      }

      if (userDetails.getFullName() != null) {
        map.put("fullName", userDetails.getFullName());
      }

      if (userDetails.getEmail() != null) {
        map.put("email", userDetails.getEmail());
      }

      if (userDetails.getMobile() != null) {
        map.put("mobile", userDetails.getMobile());
      }

      if (userDetails.getLanguage() != null) {
        map.put("language", userDetails.getLanguage());
      }

      if (userDetails.getFirstName() != null) {
        map.put("firstName", userDetails.getFirstName());
      }

      if (userDetails.getLastName() != null) {
        map.put("lastName", userDetails.getLastName());
      }

      if (userDetails.getPersNr() != null) {
        map.put("persNr", userDetails.getPersNr());
      }

      if (userDetails.getLastModifiedAt() != null) {
        Result<String> lastModifiedAt = DateStringMapper.formatDate(userDetails.getLastModifiedAt());
        if (lastModifiedAt.isError()) {
          return Result.error("lastModifiedAt: " + lastModifiedAt.getMessage());
        }
        map.put("lastModifiedAt", lastModifiedAt.getValue());
      }

      if (userDetails.getEnteredAt() != null) {
        Result<String> enteredAt = DateStringMapper.formatDate(userDetails.getEnteredAt());
        if (enteredAt.isError()) {
          return Result.error("enteredAt: " + enteredAt.getMessage());
        }
        map.put("enteredAt", enteredAt.getValue());
      }

      if (userDetails.getDisabled() != null) {
        map.put("disabled", userDetails.getDisabled());
      }

      if (userDetails.getTimeout() != null) {
        map.put("timeout", String.valueOf(userDetails.getTimeout()));
      }

      if (userDetails.getEncryptedPassword() != null) {
        map.put("encryptedPassword", userDetails.getEncryptedPassword());
      }
      if (DataRefValidation.isDataRefDefined(userDetails.getAccount())) {
        Result<String> account = accountDataRefConverter.asString(userDetails.getAccount());
        if (account.isError()) {
          return Result.error("account: " + account.getMessage());
        }
        map.put("account", account.getValue());
      }

      if (DataRefValidation.isDataRefDefined(userDetails.getProfile())) {
        Result<String> profile = profileDataRefConverter.asString(userDetails.getProfile());
        if (profile.isError()) {
          return Result.error("profile: " + profile.getMessage());
        }
        map.put("profile", profile.getValue());
      }
      List<String> userGroupsList = new ArrayList<String>();
      for (DataRef userGroupsRef : userDetails.getUserGroups()) {
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
          "Unexpected error while mapping instance " + userDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<UserDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      UserDetails userDetails = new UserDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        userDetails.setName(nameValueFromMap);
      }

      String passwordValueFromMap = map.get("password");
      if (passwordValueFromMap != null && !passwordValueFromMap.isEmpty()) {
        userDetails.setPassword(passwordValueFromMap);
      }

      String fullNameValueFromMap = map.get("fullName");
      if (fullNameValueFromMap != null && !fullNameValueFromMap.isEmpty()) {
        userDetails.setFullName(fullNameValueFromMap);
      }

      String emailValueFromMap = map.get("email");
      if (emailValueFromMap != null && !emailValueFromMap.isEmpty()) {
        userDetails.setEmail(emailValueFromMap);
      }

      String mobileValueFromMap = map.get("mobile");
      if (mobileValueFromMap != null && !mobileValueFromMap.isEmpty()) {
        userDetails.setMobile(mobileValueFromMap);
      }

      String languageValueFromMap = map.get("language");
      if (languageValueFromMap != null && !languageValueFromMap.isEmpty()) {
        userDetails.setLanguage(languageValueFromMap);
      }

      String firstNameValueFromMap = map.get("firstName");
      if (firstNameValueFromMap != null && !firstNameValueFromMap.isEmpty()) {
        userDetails.setFirstName(firstNameValueFromMap);
      }

      String lastNameValueFromMap = map.get("lastName");
      if (lastNameValueFromMap != null && !lastNameValueFromMap.isEmpty()) {
        userDetails.setLastName(lastNameValueFromMap);
      }

      String persNrValueFromMap = map.get("persNr");
      if (persNrValueFromMap != null && !persNrValueFromMap.isEmpty()) {
        userDetails.setPersNr(persNrValueFromMap);
      }

      String lastModifiedAtValueFromMap = map.get("lastModifiedAt");
      if (lastModifiedAtValueFromMap != null && !lastModifiedAtValueFromMap.isEmpty()) {
        Result<Date> lastModifiedAt = DateStringMapper.parseDate(lastModifiedAtValueFromMap);
        if (lastModifiedAt.isError()) {
          return Result.error("lastModifiedAt: " + lastModifiedAt.getMessage());
        }
        userDetails.setLastModifiedAt(lastModifiedAt.getValue());
      }

      String enteredAtValueFromMap = map.get("enteredAt");
      if (enteredAtValueFromMap != null && !enteredAtValueFromMap.isEmpty()) {
        Result<Date> enteredAt = DateStringMapper.parseDate(enteredAtValueFromMap);
        if (enteredAt.isError()) {
          return Result.error("enteredAt: " + enteredAt.getMessage());
        }
        userDetails.setEnteredAt(enteredAt.getValue());
      }

      String disabledValueFromMap = map.get("disabled");
      if (disabledValueFromMap != null && !disabledValueFromMap.isEmpty()) {
        userDetails.setDisabled(disabledValueFromMap);
      }

      String timeoutValueFromMap = map.get("timeout");
      if (timeoutValueFromMap != null && !timeoutValueFromMap.isEmpty()) {
        try {
          Integer timeout = Integer.valueOf(timeoutValueFromMap);
          userDetails.setTimeout(timeout);
        } catch (NumberFormatException e) {
          return Result.error("timeout: Expected number, but got '" + timeoutValueFromMap + "'");
        }
      }

      String encryptedPasswordValueFromMap = map.get("encryptedPassword");
      if (encryptedPasswordValueFromMap != null && !encryptedPasswordValueFromMap.isEmpty()) {
        userDetails.setEncryptedPassword(encryptedPasswordValueFromMap);
      }
      String accountValueFromMap = map.get("account");
      Result<DataRef> account = accountDataRefConverter.fromString(accountValueFromMap);
      if (account.isError()) {
        return Result.error("account: " + account.getMessage());
      }
      userDetails.setAccount(account.getValue());

      String profileValueFromMap = map.get("profile");
      Result<DataRef> profile = profileDataRefConverter.fromString(profileValueFromMap);
      if (profile.isError()) {
        return Result.error("profile: " + profile.getMessage());
      }
      userDetails.setProfile(profile.getValue());
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
        userDetails.setUserGroups(userGroupsList);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(userDetails);
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
