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
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class AccountMapper implements IDataElementMapper<AccountDetails> {
  private static final Logger logger = LoggerFactory.getLogger(AccountMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<AccountDetails> parameter) {
    AccountDetails accountDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (accountDetails.getName() != null) {
        map.put("name", accountDetails.getName());
      }

      if (accountDetails.getRefId() != null) {
        map.put("refId", accountDetails.getRefId());
      }

      if (accountDetails.getFullName() != null) {
        map.put("fullName", accountDetails.getFullName());
      }

      if (accountDetails.getAddress() != null) {
        map.put("address", accountDetails.getAddress());
      }

      if (accountDetails.getZipCode() != null) {
        map.put("zipCode", accountDetails.getZipCode());
      }

      if (accountDetails.getCity() != null) {
        map.put("city", accountDetails.getCity());
      }

      if (accountDetails.getCountry() != null) {
        map.put("country", accountDetails.getCountry());
      }

      if (accountDetails.getEmail() != null) {
        map.put("email", accountDetails.getEmail());
      }

      if (accountDetails.getPhone() != null) {
        map.put("phone", accountDetails.getPhone());
      }

      if (accountDetails.getStyle() != null) {
        map.put("style", accountDetails.getStyle());
      }

      if (accountDetails.getStatus() != null) {
        map.put("status", accountDetails.getStatus());
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
          "Unexpected error while mapping instance " + accountDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<AccountDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      AccountDetails accountDetails = new AccountDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        accountDetails.setName(nameValueFromMap);
      }

      String refIdValueFromMap = map.get("refId");
      if (refIdValueFromMap != null && !refIdValueFromMap.isEmpty()) {
        accountDetails.setRefId(refIdValueFromMap);
      }

      String fullNameValueFromMap = map.get("fullName");
      if (fullNameValueFromMap != null && !fullNameValueFromMap.isEmpty()) {
        accountDetails.setFullName(fullNameValueFromMap);
      }

      String addressValueFromMap = map.get("address");
      if (addressValueFromMap != null && !addressValueFromMap.isEmpty()) {
        accountDetails.setAddress(addressValueFromMap);
      }

      String zipCodeValueFromMap = map.get("zipCode");
      if (zipCodeValueFromMap != null && !zipCodeValueFromMap.isEmpty()) {
        accountDetails.setZipCode(zipCodeValueFromMap);
      }

      String cityValueFromMap = map.get("city");
      if (cityValueFromMap != null && !cityValueFromMap.isEmpty()) {
        accountDetails.setCity(cityValueFromMap);
      }

      String countryValueFromMap = map.get("country");
      if (countryValueFromMap != null && !countryValueFromMap.isEmpty()) {
        accountDetails.setCountry(countryValueFromMap);
      }

      String emailValueFromMap = map.get("email");
      if (emailValueFromMap != null && !emailValueFromMap.isEmpty()) {
        accountDetails.setEmail(emailValueFromMap);
      }

      String phoneValueFromMap = map.get("phone");
      if (phoneValueFromMap != null && !phoneValueFromMap.isEmpty()) {
        accountDetails.setPhone(phoneValueFromMap);
      }

      String styleValueFromMap = map.get("style");
      if (styleValueFromMap != null && !styleValueFromMap.isEmpty()) {
        accountDetails.setStyle(styleValueFromMap);
      }

      String statusValueFromMap = map.get("status");
      if (statusValueFromMap != null && !statusValueFromMap.isEmpty()) {
        accountDetails.setStatus(statusValueFromMap);
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(accountDetails);
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
