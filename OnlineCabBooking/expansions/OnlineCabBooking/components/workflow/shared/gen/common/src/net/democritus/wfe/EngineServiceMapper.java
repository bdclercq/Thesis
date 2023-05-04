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
import net.democritus.valuetype.basic.DateTime;
import net.democritus.valuetype.basic.DateTimeConverter;
import net.democritus.valuetype.basic.DateTimeValidator;
// anchor:valueType-imports:end

// anchor:dataRef-imports:start
import net.democritus.workflow.WorkflowDataRefConverter;
import net.democritus.wfe.TimeWindowGroupDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineServiceMapper implements IDataElementMapper<EngineServiceDetails> {
  private static final Logger logger = LoggerFactory.getLogger(EngineServiceMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private DateTimeConverter dateTimeConverter = new DateTimeConverter();
  private DateTimeValidator dateTimeValidator = new DateTimeValidator();
  private WorkflowDataRefConverter workflowDataRefConverter = new WorkflowDataRefConverter();
  private TimeWindowGroupDataRefConverter timeWindowGroupDataRefConverter = new TimeWindowGroupDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<EngineServiceDetails> parameter) {
    EngineServiceDetails engineServiceDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (engineServiceDetails.getName() != null) {
        map.put("name", engineServiceDetails.getName());
      }

      if (engineServiceDetails.getStatus() != null) {
        map.put("status", engineServiceDetails.getStatus());
      }

      if (engineServiceDetails.getChanged() != null) {
        map.put("changed", engineServiceDetails.getChanged());
      }

      if (engineServiceDetails.getBusy() != null) {
        map.put("busy", engineServiceDetails.getBusy());
      }

      if (engineServiceDetails.getWaitTime() != null) {
        map.put("waitTime", String.valueOf(engineServiceDetails.getWaitTime()));
      }

      if (engineServiceDetails.getCollector() != null) {
        map.put("collector", String.valueOf(engineServiceDetails.getCollector()));
      }

      if (engineServiceDetails.getLastRunAt() != null) {
        Result<String> lastRunAt = dateTimeConverter.asString(engineServiceDetails.getLastRunAt());
        if (lastRunAt.isError()) {
          return Result.error("lastRunAt: " + lastRunAt.getMessage());
        }
        map.put("lastRunAt", lastRunAt.getValue());
      }

      if (engineServiceDetails.getBatchSize() != null) {
        map.put("batchSize", String.valueOf(engineServiceDetails.getBatchSize()));
      }

      if (engineServiceDetails.getMaximumNumberOfNodes() != null) {
        map.put("maximumNumberOfNodes", String.valueOf(engineServiceDetails.getMaximumNumberOfNodes()));
      }
      if (DataRefValidation.isDataRefDefined(engineServiceDetails.getWorkflow())) {
        Result<String> workflow = workflowDataRefConverter.asString(engineServiceDetails.getWorkflow());
        if (workflow.isError()) {
          return Result.error("workflow: " + workflow.getMessage());
        }
        map.put("workflow", workflow.getValue());
      }

      if (DataRefValidation.isDataRefDefined(engineServiceDetails.getTimeWindowGroup())) {
        Result<String> timeWindowGroup = timeWindowGroupDataRefConverter.asString(engineServiceDetails.getTimeWindowGroup());
        if (timeWindowGroup.isError()) {
          return Result.error("timeWindowGroup: " + timeWindowGroup.getMessage());
        }
        map.put("timeWindowGroup", timeWindowGroup.getValue());
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
          "Unexpected error while mapping instance " + engineServiceDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<EngineServiceDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      EngineServiceDetails engineServiceDetails = new EngineServiceDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        engineServiceDetails.setName(nameValueFromMap);
      }

      String statusValueFromMap = map.get("status");
      if (statusValueFromMap != null && !statusValueFromMap.isEmpty()) {
        engineServiceDetails.setStatus(statusValueFromMap);
      }

      String changedValueFromMap = map.get("changed");
      if (changedValueFromMap != null && !changedValueFromMap.isEmpty()) {
        engineServiceDetails.setChanged(changedValueFromMap);
      }

      String busyValueFromMap = map.get("busy");
      if (busyValueFromMap != null && !busyValueFromMap.isEmpty()) {
        engineServiceDetails.setBusy(busyValueFromMap);
      }

      String waitTimeValueFromMap = map.get("waitTime");
      if (waitTimeValueFromMap != null && !waitTimeValueFromMap.isEmpty()) {
        try {
          Integer waitTime = Integer.valueOf(waitTimeValueFromMap);
          engineServiceDetails.setWaitTime(waitTime);
        } catch (NumberFormatException e) {
          return Result.error("waitTime: Expected number, but got '" + waitTimeValueFromMap + "'");
        }
      }

      String collectorValueFromMap = map.get("collector");
      if (collectorValueFromMap != null && !collectorValueFromMap.isEmpty()) {
        try {
          Long collector = Long.valueOf(collectorValueFromMap);
          engineServiceDetails.setCollector(collector);
        } catch (NumberFormatException e) {
          return Result.error("collector: Expected number, but got '" + collectorValueFromMap + "'");
        }
      }

      String lastRunAtValueFromMap = map.get("lastRunAt");
      if (lastRunAtValueFromMap != null && !lastRunAtValueFromMap.isEmpty()) {
        Result<DateTime> lastRunAt = dateTimeConverter.fromString(lastRunAtValueFromMap);
        if (lastRunAt.isError()) {
          return Result.error("lastRunAt: " + lastRunAt.getMessage());
        }
        ValidationResult lastRunAtValidation = dateTimeValidator.validate(lastRunAt.getValue());
        if (lastRunAtValidation.isError()) {
          return Result.error("lastRunAt: " + lastRunAtValidation.getMessage());
        }
        engineServiceDetails.setLastRunAt(lastRunAt.getValue());
      }

      String batchSizeValueFromMap = map.get("batchSize");
      if (batchSizeValueFromMap != null && !batchSizeValueFromMap.isEmpty()) {
        try {
          Integer batchSize = Integer.valueOf(batchSizeValueFromMap);
          engineServiceDetails.setBatchSize(batchSize);
        } catch (NumberFormatException e) {
          return Result.error("batchSize: Expected number, but got '" + batchSizeValueFromMap + "'");
        }
      }

      String maximumNumberOfNodesValueFromMap = map.get("maximumNumberOfNodes");
      if (maximumNumberOfNodesValueFromMap != null && !maximumNumberOfNodesValueFromMap.isEmpty()) {
        try {
          Integer maximumNumberOfNodes = Integer.valueOf(maximumNumberOfNodesValueFromMap);
          engineServiceDetails.setMaximumNumberOfNodes(maximumNumberOfNodes);
        } catch (NumberFormatException e) {
          return Result.error("maximumNumberOfNodes: Expected number, but got '" + maximumNumberOfNodesValueFromMap + "'");
        }
      }
      String workflowValueFromMap = map.get("workflow");
      Result<DataRef> workflow = workflowDataRefConverter.fromString(workflowValueFromMap);
      if (workflow.isError()) {
        return Result.error("workflow: " + workflow.getMessage());
      }
      engineServiceDetails.setWorkflow(workflow.getValue());

      String timeWindowGroupValueFromMap = map.get("timeWindowGroup");
      Result<DataRef> timeWindowGroup = timeWindowGroupDataRefConverter.fromString(timeWindowGroupValueFromMap);
      if (timeWindowGroup.isError()) {
        return Result.error("timeWindowGroup: " + timeWindowGroup.getMessage());
      }
      engineServiceDetails.setTimeWindowGroup(timeWindowGroup.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(engineServiceDetails);
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
