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

public class StateTaskMapper implements IDataElementMapper<StateTaskDetails> {
  private static final Logger logger = LoggerFactory.getLogger(StateTaskMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private WorkflowDataRefConverter workflowDataRefConverter = new WorkflowDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<StateTaskDetails> parameter) {
    StateTaskDetails stateTaskDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (stateTaskDetails.getName() != null) {
        map.put("name", stateTaskDetails.getName());
      }

      if (stateTaskDetails.getProcessor() != null) {
        map.put("processor", stateTaskDetails.getProcessor());
      }

      if (stateTaskDetails.getImplementation() != null) {
        map.put("implementation", stateTaskDetails.getImplementation());
      }

      if (stateTaskDetails.getParams() != null) {
        map.put("params", stateTaskDetails.getParams());
      }

      if (stateTaskDetails.getBeginState() != null) {
        map.put("beginState", stateTaskDetails.getBeginState());
      }

      if (stateTaskDetails.getInterimState() != null) {
        map.put("interimState", stateTaskDetails.getInterimState());
      }

      if (stateTaskDetails.getFailedState() != null) {
        map.put("failedState", stateTaskDetails.getFailedState());
      }

      if (stateTaskDetails.getEndState() != null) {
        map.put("endState", stateTaskDetails.getEndState());
      }

      if (stateTaskDetails.getMaxConcurrentTasks() != null) {
        map.put("maxConcurrentTasks", String.valueOf(stateTaskDetails.getMaxConcurrentTasks()));
      }

      if (stateTaskDetails.getTimeout() != null) {
        map.put("timeout", String.valueOf(stateTaskDetails.getTimeout()));
      }
      if (DataRefValidation.isDataRefDefined(stateTaskDetails.getWorkflow())) {
        Result<String> workflow = workflowDataRefConverter.asString(stateTaskDetails.getWorkflow());
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
          "Unexpected error while mapping instance " + stateTaskDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<StateTaskDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      StateTaskDetails stateTaskDetails = new StateTaskDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        stateTaskDetails.setName(nameValueFromMap);
      }

      String processorValueFromMap = map.get("processor");
      if (processorValueFromMap != null && !processorValueFromMap.isEmpty()) {
        stateTaskDetails.setProcessor(processorValueFromMap);
      }

      String implementationValueFromMap = map.get("implementation");
      if (implementationValueFromMap != null && !implementationValueFromMap.isEmpty()) {
        stateTaskDetails.setImplementation(implementationValueFromMap);
      }

      String paramsValueFromMap = map.get("params");
      if (paramsValueFromMap != null && !paramsValueFromMap.isEmpty()) {
        stateTaskDetails.setParams(paramsValueFromMap);
      }

      String beginStateValueFromMap = map.get("beginState");
      if (beginStateValueFromMap != null && !beginStateValueFromMap.isEmpty()) {
        stateTaskDetails.setBeginState(beginStateValueFromMap);
      }

      String interimStateValueFromMap = map.get("interimState");
      if (interimStateValueFromMap != null && !interimStateValueFromMap.isEmpty()) {
        stateTaskDetails.setInterimState(interimStateValueFromMap);
      }

      String failedStateValueFromMap = map.get("failedState");
      if (failedStateValueFromMap != null && !failedStateValueFromMap.isEmpty()) {
        stateTaskDetails.setFailedState(failedStateValueFromMap);
      }

      String endStateValueFromMap = map.get("endState");
      if (endStateValueFromMap != null && !endStateValueFromMap.isEmpty()) {
        stateTaskDetails.setEndState(endStateValueFromMap);
      }

      String maxConcurrentTasksValueFromMap = map.get("maxConcurrentTasks");
      if (maxConcurrentTasksValueFromMap != null && !maxConcurrentTasksValueFromMap.isEmpty()) {
        try {
          Integer maxConcurrentTasks = Integer.valueOf(maxConcurrentTasksValueFromMap);
          stateTaskDetails.setMaxConcurrentTasks(maxConcurrentTasks);
        } catch (NumberFormatException e) {
          return Result.error("maxConcurrentTasks: Expected number, but got '" + maxConcurrentTasksValueFromMap + "'");
        }
      }

      String timeoutValueFromMap = map.get("timeout");
      if (timeoutValueFromMap != null && !timeoutValueFromMap.isEmpty()) {
        try {
          Long timeout = Long.valueOf(timeoutValueFromMap);
          stateTaskDetails.setTimeout(timeout);
        } catch (NumberFormatException e) {
          return Result.error("timeout: Expected number, but got '" + timeoutValueFromMap + "'");
        }
      }
      String workflowValueFromMap = map.get("workflow");
      Result<DataRef> workflow = workflowDataRefConverter.fromString(workflowValueFromMap);
      if (workflow.isError()) {
        return Result.error("workflow: " + workflow.getMessage());
      }
      stateTaskDetails.setWorkflow(workflow.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(stateTaskDetails);
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
