package net.democritus.workflow;

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
import net.democritus.workflow.WorkflowDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class TimeTaskMapper implements IDataElementMapper<TimeTaskDetails> {
  private static final Logger logger = LoggerFactory.getLogger(TimeTaskMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private WorkflowDataRefConverter workflowDataRefConverter = new WorkflowDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<TimeTaskDetails> parameter) {
    TimeTaskDetails timeTaskDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (timeTaskDetails.getName() != null) {
        map.put("name", timeTaskDetails.getName());
      }

      if (timeTaskDetails.getProcessor() != null) {
        map.put("processor", timeTaskDetails.getProcessor());
      }

      if (timeTaskDetails.getImplementation() != null) {
        map.put("implementation", timeTaskDetails.getImplementation());
      }

      if (timeTaskDetails.getParams() != null) {
        map.put("params", timeTaskDetails.getParams());
      }

      if (timeTaskDetails.getTriggerState() != null) {
        map.put("triggerState", timeTaskDetails.getTriggerState());
      }

      if (timeTaskDetails.getIntervalPeriod() != null) {
        map.put("intervalPeriod", String.valueOf(timeTaskDetails.getIntervalPeriod()));
      }

      if (timeTaskDetails.getRequiredAction() != null) {
        map.put("requiredAction", timeTaskDetails.getRequiredAction());
      }
      if (DataRefValidation.isDataRefDefined(timeTaskDetails.getWorkflow())) {
        Result<String> workflow = workflowDataRefConverter.asString(timeTaskDetails.getWorkflow());
        if (workflow.isError()) {
          return Result.error("workflow: " + workflow.getMessage());
        }
        map.put("workflow", workflow.getValue());
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
          "Unexpected error while mapping instance " + timeTaskDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<TimeTaskDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      TimeTaskDetails timeTaskDetails = new TimeTaskDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        timeTaskDetails.setName(nameValueFromMap);
      }

      String processorValueFromMap = map.get("processor");
      if (processorValueFromMap != null && !processorValueFromMap.isEmpty()) {
        timeTaskDetails.setProcessor(processorValueFromMap);
      }

      String implementationValueFromMap = map.get("implementation");
      if (implementationValueFromMap != null && !implementationValueFromMap.isEmpty()) {
        timeTaskDetails.setImplementation(implementationValueFromMap);
      }

      String paramsValueFromMap = map.get("params");
      if (paramsValueFromMap != null && !paramsValueFromMap.isEmpty()) {
        timeTaskDetails.setParams(paramsValueFromMap);
      }

      String triggerStateValueFromMap = map.get("triggerState");
      if (triggerStateValueFromMap != null && !triggerStateValueFromMap.isEmpty()) {
        timeTaskDetails.setTriggerState(triggerStateValueFromMap);
      }

      String intervalPeriodValueFromMap = map.get("intervalPeriod");
      if (intervalPeriodValueFromMap != null && !intervalPeriodValueFromMap.isEmpty()) {
        try {
          Long intervalPeriod = Long.valueOf(intervalPeriodValueFromMap);
          timeTaskDetails.setIntervalPeriod(intervalPeriod);
        } catch (NumberFormatException e) {
          return Result.error("intervalPeriod: Expected number, but got '" + intervalPeriodValueFromMap + "'");
        }
      }

      String requiredActionValueFromMap = map.get("requiredAction");
      if (requiredActionValueFromMap != null && !requiredActionValueFromMap.isEmpty()) {
        timeTaskDetails.setRequiredAction(requiredActionValueFromMap);
      }
      String workflowValueFromMap = map.get("workflow");
      Result<DataRef> workflow = workflowDataRefConverter.fromString(workflowValueFromMap);
      if (workflow.isError()) {
        return Result.error("workflow: " + workflow.getMessage());
      }
      timeTaskDetails.setWorkflow(workflow.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(timeTaskDetails);
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
