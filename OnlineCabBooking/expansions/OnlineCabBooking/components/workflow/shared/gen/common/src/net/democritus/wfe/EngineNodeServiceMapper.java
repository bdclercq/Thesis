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
import net.democritus.wfe.EngineNodeDataRefConverter;
import net.democritus.wfe.EngineServiceDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineNodeServiceMapper implements IDataElementMapper<EngineNodeServiceDetails> {
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeServiceMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private EngineNodeDataRefConverter engineNodeDataRefConverter = new EngineNodeDataRefConverter();
  private EngineServiceDataRefConverter engineServiceDataRefConverter = new EngineServiceDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<EngineNodeServiceDetails> parameter) {
    EngineNodeServiceDetails engineNodeServiceDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (engineNodeServiceDetails.getName() != null) {
        map.put("name", engineNodeServiceDetails.getName());
      }

      if (engineNodeServiceDetails.getStatus() != null) {
        map.put("status", engineNodeServiceDetails.getStatus());
      }

      if (engineNodeServiceDetails.getLastRunAt() != null) {
        Result<String> lastRunAt = DateStringMapper.formatDateTime(engineNodeServiceDetails.getLastRunAt());
        if (lastRunAt.isError()) {
          return Result.error("lastRunAt: " + lastRunAt.getMessage());
        }
        map.put("lastRunAt", lastRunAt.getValue());
      }

      if (engineNodeServiceDetails.getNextRun() != null) {
        Result<String> nextRun = DateStringMapper.formatDateTime(engineNodeServiceDetails.getNextRun());
        if (nextRun.isError()) {
          return Result.error("nextRun: " + nextRun.getMessage());
        }
        map.put("nextRun", nextRun.getValue());
      }
      if (DataRefValidation.isDataRefDefined(engineNodeServiceDetails.getEngineNode())) {
        Result<String> engineNode = engineNodeDataRefConverter.asString(engineNodeServiceDetails.getEngineNode());
        if (engineNode.isError()) {
          return Result.error("engineNode: " + engineNode.getMessage());
        }
        map.put("engineNode", engineNode.getValue());
      }

      if (DataRefValidation.isDataRefDefined(engineNodeServiceDetails.getEngineService())) {
        Result<String> engineService = engineServiceDataRefConverter.asString(engineNodeServiceDetails.getEngineService());
        if (engineService.isError()) {
          return Result.error("engineService: " + engineService.getMessage());
        }
        map.put("engineService", engineService.getValue());
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
          "Unexpected error while mapping instance " + engineNodeServiceDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<EngineNodeServiceDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      EngineNodeServiceDetails engineNodeServiceDetails = new EngineNodeServiceDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        engineNodeServiceDetails.setName(nameValueFromMap);
      }

      String statusValueFromMap = map.get("status");
      if (statusValueFromMap != null && !statusValueFromMap.isEmpty()) {
        engineNodeServiceDetails.setStatus(statusValueFromMap);
      }

      String lastRunAtValueFromMap = map.get("lastRunAt");
      if (lastRunAtValueFromMap != null && !lastRunAtValueFromMap.isEmpty()) {
        Result<Date> lastRunAt = DateStringMapper.parseDateTime(lastRunAtValueFromMap);
        if (lastRunAt.isError()) {
          return Result.error("lastRunAt: " + lastRunAt.getMessage());
        }
        engineNodeServiceDetails.setLastRunAt(lastRunAt.getValue());
      }

      String nextRunValueFromMap = map.get("nextRun");
      if (nextRunValueFromMap != null && !nextRunValueFromMap.isEmpty()) {
        Result<Date> nextRun = DateStringMapper.parseDateTime(nextRunValueFromMap);
        if (nextRun.isError()) {
          return Result.error("nextRun: " + nextRun.getMessage());
        }
        engineNodeServiceDetails.setNextRun(nextRun.getValue());
      }
      String engineNodeValueFromMap = map.get("engineNode");
      Result<DataRef> engineNode = engineNodeDataRefConverter.fromString(engineNodeValueFromMap);
      if (engineNode.isError()) {
        return Result.error("engineNode: " + engineNode.getMessage());
      }
      engineNodeServiceDetails.setEngineNode(engineNode.getValue());

      String engineServiceValueFromMap = map.get("engineService");
      Result<DataRef> engineService = engineServiceDataRefConverter.fromString(engineServiceValueFromMap);
      if (engineService.isError()) {
        return Result.error("engineService: " + engineService.getMessage());
      }
      engineNodeServiceDetails.setEngineService(engineService.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(engineNodeServiceDetails);
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
