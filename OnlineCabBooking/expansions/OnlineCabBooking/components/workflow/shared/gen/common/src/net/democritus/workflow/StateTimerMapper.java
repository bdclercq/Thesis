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

public class StateTimerMapper implements IDataElementMapper<StateTimerDetails> {
  private static final Logger logger = LoggerFactory.getLogger(StateTimerMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private WorkflowDataRefConverter workflowDataRefConverter = new WorkflowDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<StateTimerDetails> parameter) {
    StateTimerDetails stateTimerDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (stateTimerDetails.getName() != null) {
        map.put("name", stateTimerDetails.getName());
      }

      if (stateTimerDetails.getProcessor() != null) {
        map.put("processor", stateTimerDetails.getProcessor());
      }

      if (stateTimerDetails.getImplementation() != null) {
        map.put("implementation", stateTimerDetails.getImplementation());
      }

      if (stateTimerDetails.getParams() != null) {
        map.put("params", stateTimerDetails.getParams());
      }

      if (stateTimerDetails.getBeginState() != null) {
        map.put("beginState", stateTimerDetails.getBeginState());
      }

      if (stateTimerDetails.getTargetState() != null) {
        map.put("targetState", stateTimerDetails.getTargetState());
      }

      if (stateTimerDetails.getAlteredState() != null) {
        map.put("alteredState", stateTimerDetails.getAlteredState());
      }

      if (stateTimerDetails.getAllowedPeriod() != null) {
        map.put("allowedPeriod", String.valueOf(stateTimerDetails.getAllowedPeriod()));
      }

      if (stateTimerDetails.getRequiredAction() != null) {
        map.put("requiredAction", stateTimerDetails.getRequiredAction());
      }
      if (DataRefValidation.isDataRefDefined(stateTimerDetails.getWorkflow())) {
        Result<String> workflow = workflowDataRefConverter.asString(stateTimerDetails.getWorkflow());
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
          "Unexpected error while mapping instance " + stateTimerDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<StateTimerDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      StateTimerDetails stateTimerDetails = new StateTimerDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        stateTimerDetails.setName(nameValueFromMap);
      }

      String processorValueFromMap = map.get("processor");
      if (processorValueFromMap != null && !processorValueFromMap.isEmpty()) {
        stateTimerDetails.setProcessor(processorValueFromMap);
      }

      String implementationValueFromMap = map.get("implementation");
      if (implementationValueFromMap != null && !implementationValueFromMap.isEmpty()) {
        stateTimerDetails.setImplementation(implementationValueFromMap);
      }

      String paramsValueFromMap = map.get("params");
      if (paramsValueFromMap != null && !paramsValueFromMap.isEmpty()) {
        stateTimerDetails.setParams(paramsValueFromMap);
      }

      String beginStateValueFromMap = map.get("beginState");
      if (beginStateValueFromMap != null && !beginStateValueFromMap.isEmpty()) {
        stateTimerDetails.setBeginState(beginStateValueFromMap);
      }

      String targetStateValueFromMap = map.get("targetState");
      if (targetStateValueFromMap != null && !targetStateValueFromMap.isEmpty()) {
        stateTimerDetails.setTargetState(targetStateValueFromMap);
      }

      String alteredStateValueFromMap = map.get("alteredState");
      if (alteredStateValueFromMap != null && !alteredStateValueFromMap.isEmpty()) {
        stateTimerDetails.setAlteredState(alteredStateValueFromMap);
      }

      String allowedPeriodValueFromMap = map.get("allowedPeriod");
      if (allowedPeriodValueFromMap != null && !allowedPeriodValueFromMap.isEmpty()) {
        try {
          Long allowedPeriod = Long.valueOf(allowedPeriodValueFromMap);
          stateTimerDetails.setAllowedPeriod(allowedPeriod);
        } catch (NumberFormatException e) {
          return Result.error("allowedPeriod: Expected number, but got '" + allowedPeriodValueFromMap + "'");
        }
      }

      String requiredActionValueFromMap = map.get("requiredAction");
      if (requiredActionValueFromMap != null && !requiredActionValueFromMap.isEmpty()) {
        stateTimerDetails.setRequiredAction(requiredActionValueFromMap);
      }
      String workflowValueFromMap = map.get("workflow");
      Result<DataRef> workflow = workflowDataRefConverter.fromString(workflowValueFromMap);
      if (workflow.isError()) {
        return Result.error("workflow: " + workflow.getMessage());
      }
      stateTimerDetails.setWorkflow(workflow.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(stateTimerDetails);
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
