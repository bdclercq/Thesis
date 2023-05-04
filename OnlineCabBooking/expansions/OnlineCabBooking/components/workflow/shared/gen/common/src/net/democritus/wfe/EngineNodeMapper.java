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
import java.util.Date;

// anchor:valueType-imports:end

// anchor:dataRef-imports:start
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineNodeMapper implements IDataElementMapper<EngineNodeDetails> {
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<EngineNodeDetails> parameter) {
    EngineNodeDetails engineNodeDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (engineNodeDetails.getName() != null) {
        map.put("name", engineNodeDetails.getName());
      }

      if (engineNodeDetails.getStatus() != null) {
        map.put("status", engineNodeDetails.getStatus());
      }

      if (engineNodeDetails.getHostname() != null) {
        map.put("hostname", engineNodeDetails.getHostname());
      }

      if (engineNodeDetails.getMaster() != null) {
        map.put("master", String.valueOf(engineNodeDetails.getMaster()));
      }

      if (engineNodeDetails.getLastActive() != null) {
        Result<String> lastActive = DateStringMapper.formatDateTime(engineNodeDetails.getLastActive());
        if (lastActive.isError()) {
          return Result.error("lastActive: " + lastActive.getMessage());
        }
        map.put("lastActive", lastActive.getValue());
      }

      if (engineNodeDetails.getActiveSince() != null) {
        Result<String> activeSince = DateStringMapper.formatDateTime(engineNodeDetails.getActiveSince());
        if (activeSince.isError()) {
          return Result.error("activeSince: " + activeSince.getMessage());
        }
        map.put("activeSince", activeSince.getValue());
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
          "Unexpected error while mapping instance " + engineNodeDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<EngineNodeDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      EngineNodeDetails engineNodeDetails = new EngineNodeDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        engineNodeDetails.setName(nameValueFromMap);
      }

      String statusValueFromMap = map.get("status");
      if (statusValueFromMap != null && !statusValueFromMap.isEmpty()) {
        engineNodeDetails.setStatus(statusValueFromMap);
      }

      String hostnameValueFromMap = map.get("hostname");
      if (hostnameValueFromMap != null && !hostnameValueFromMap.isEmpty()) {
        engineNodeDetails.setHostname(hostnameValueFromMap);
      }

      String masterValueFromMap = map.get("master");
      if (masterValueFromMap != null && !masterValueFromMap.isEmpty()) {
        Boolean master = Boolean.valueOf(masterValueFromMap);
        engineNodeDetails.setMaster(master);
      }

      String lastActiveValueFromMap = map.get("lastActive");
      if (lastActiveValueFromMap != null && !lastActiveValueFromMap.isEmpty()) {
        Result<Date> lastActive = DateStringMapper.parseDateTime(lastActiveValueFromMap);
        if (lastActive.isError()) {
          return Result.error("lastActive: " + lastActive.getMessage());
        }
        engineNodeDetails.setLastActive(lastActive.getValue());
      }

      String activeSinceValueFromMap = map.get("activeSince");
      if (activeSinceValueFromMap != null && !activeSinceValueFromMap.isEmpty()) {
        Result<Date> activeSince = DateStringMapper.parseDateTime(activeSinceValueFromMap);
        if (activeSince.isError()) {
          return Result.error("activeSince: " + activeSince.getMessage());
        }
        engineNodeDetails.setActiveSince(activeSince.getValue());
      }
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(engineNodeDetails);
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
