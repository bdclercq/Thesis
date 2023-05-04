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
import net.democritus.usr.UserDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class WorkflowMapper implements IDataElementMapper<WorkflowDetails> {
  private static final Logger logger = LoggerFactory.getLogger(WorkflowMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private UserDataRefConverter userDataRefConverter = new UserDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<WorkflowDetails> parameter) {
    WorkflowDetails workflowDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (workflowDetails.getName() != null) {
        map.put("name", workflowDetails.getName());
      }

      if (workflowDetails.getTarget() != null) {
        map.put("target", workflowDetails.getTarget());
      }

      if (workflowDetails.getComponentName() != null) {
        map.put("componentName", workflowDetails.getComponentName());
      }

      if (workflowDetails.getClassName() != null) {
        map.put("className", workflowDetails.getClassName());
      }

      if (workflowDetails.getSequencingStrategy() != null) {
        map.put("sequencingStrategy", workflowDetails.getSequencingStrategy());
      }
      if (DataRefValidation.isDataRefDefined(workflowDetails.getResponsible())) {
        Result<String> responsible = userDataRefConverter.asString(workflowDetails.getResponsible());
        if (responsible.isError()) {
          return Result.error("responsible: " + responsible.getMessage());
        }
        map.put("responsible", responsible.getValue());
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
          "Unexpected error while mapping instance " + workflowDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<WorkflowDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      WorkflowDetails workflowDetails = new WorkflowDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        workflowDetails.setName(nameValueFromMap);
      }

      String targetValueFromMap = map.get("target");
      if (targetValueFromMap != null && !targetValueFromMap.isEmpty()) {
        workflowDetails.setTarget(targetValueFromMap);
      }

      String componentNameValueFromMap = map.get("componentName");
      if (componentNameValueFromMap != null && !componentNameValueFromMap.isEmpty()) {
        workflowDetails.setComponentName(componentNameValueFromMap);
      }

      String classNameValueFromMap = map.get("className");
      if (classNameValueFromMap != null && !classNameValueFromMap.isEmpty()) {
        workflowDetails.setClassName(classNameValueFromMap);
      }

      String sequencingStrategyValueFromMap = map.get("sequencingStrategy");
      if (sequencingStrategyValueFromMap != null && !sequencingStrategyValueFromMap.isEmpty()) {
        workflowDetails.setSequencingStrategy(sequencingStrategyValueFromMap);
      }
      String responsibleValueFromMap = map.get("responsible");
      Result<DataRef> responsible = userDataRefConverter.fromString(responsibleValueFromMap);
      if (responsible.isError()) {
        return Result.error("responsible: " + responsible.getMessage());
      }
      workflowDetails.setResponsible(responsible.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(workflowDetails);
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
