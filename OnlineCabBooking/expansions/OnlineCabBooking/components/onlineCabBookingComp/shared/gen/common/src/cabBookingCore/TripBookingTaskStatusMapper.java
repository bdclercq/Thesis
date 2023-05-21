package cabBookingCore;

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
import net.democritus.workflow.StateTaskDataRefConverter;
import cabBookingCore.TripBookingDataRefConverter;
// anchor:dataRef-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class TripBookingTaskStatusMapper implements IDataElementMapper<TripBookingTaskStatusDetails> {
  private static final Logger logger = LoggerFactory.getLogger(TripBookingTaskStatusMapper.class);

  private String listSeparator = "\\|";
  // anchor:variables:start
  private StateTaskDataRefConverter stateTaskDataRefConverter = new StateTaskDataRefConverter();
  private TripBookingDataRefConverter tripBookingDataRefConverter = new TripBookingDataRefConverter();
  // anchor:variables:end

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public Result<Map<String, String>> convertToMap(ParameterContext<TripBookingTaskStatusDetails> parameter) {
    TripBookingTaskStatusDetails tripBookingTaskStatusDetails = parameter.getValue();
    try {
      Map<String, String> map = new HashMap<String, String>();

      // anchor:convert-fields-to-map:start
      if (tripBookingTaskStatusDetails.getName() != null) {
        map.put("name", tripBookingTaskStatusDetails.getName());
      }

      if (tripBookingTaskStatusDetails.getStartedAt() != null) {
        Result<String> startedAt = DateStringMapper.formatDate(tripBookingTaskStatusDetails.getStartedAt());
        if (startedAt.isError()) {
          return Result.error("startedAt: " + startedAt.getMessage());
        }
        map.put("startedAt", startedAt.getValue());
      }

      if (tripBookingTaskStatusDetails.getFinishedAt() != null) {
        Result<String> finishedAt = DateStringMapper.formatDate(tripBookingTaskStatusDetails.getFinishedAt());
        if (finishedAt.isError()) {
          return Result.error("finishedAt: " + finishedAt.getMessage());
        }
        map.put("finishedAt", finishedAt.getValue());
      }

      if (tripBookingTaskStatusDetails.getStatus() != null) {
        map.put("status", tripBookingTaskStatusDetails.getStatus());
      }
      if (DataRefValidation.isDataRefDefined(tripBookingTaskStatusDetails.getStateTask())) {
        Result<String> stateTask = stateTaskDataRefConverter.asString(tripBookingTaskStatusDetails.getStateTask());
        if (stateTask.isError()) {
          return Result.error("stateTask: " + stateTask.getMessage());
        }
        map.put("stateTask", stateTask.getValue());
      }

      if (DataRefValidation.isDataRefDefined(tripBookingTaskStatusDetails.getTripBooking())) {
        Result<String> tripBooking = tripBookingDataRefConverter.asString(tripBookingTaskStatusDetails.getTripBooking());
        if (tripBooking.isError()) {
          return Result.error("tripBooking: " + tripBooking.getMessage());
        }
        map.put("tripBooking", tripBooking.getValue());
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
          "Unexpected error while mapping instance " + tripBookingTaskStatusDetails.getId() + " to map", e
          );
      }
      return Result.error("Unexpected error");
    }
  }

  public Result<TripBookingTaskStatusDetails> convertToDetails(ParameterContext<Map<String, String>> parameter) {
    try {
      Map<String, String> map = parameter.getValue();

      TripBookingTaskStatusDetails tripBookingTaskStatusDetails = new TripBookingTaskStatusDetails();

      // anchor:convert-map-to-fields:start
      String nameValueFromMap = map.get("name");
      if (nameValueFromMap != null && !nameValueFromMap.isEmpty()) {
        tripBookingTaskStatusDetails.setName(nameValueFromMap);
      }

      String startedAtValueFromMap = map.get("startedAt");
      if (startedAtValueFromMap != null && !startedAtValueFromMap.isEmpty()) {
        Result<Date> startedAt = DateStringMapper.parseDate(startedAtValueFromMap);
        if (startedAt.isError()) {
          return Result.error("startedAt: " + startedAt.getMessage());
        }
        tripBookingTaskStatusDetails.setStartedAt(startedAt.getValue());
      }

      String finishedAtValueFromMap = map.get("finishedAt");
      if (finishedAtValueFromMap != null && !finishedAtValueFromMap.isEmpty()) {
        Result<Date> finishedAt = DateStringMapper.parseDate(finishedAtValueFromMap);
        if (finishedAt.isError()) {
          return Result.error("finishedAt: " + finishedAt.getMessage());
        }
        tripBookingTaskStatusDetails.setFinishedAt(finishedAt.getValue());
      }

      String statusValueFromMap = map.get("status");
      if (statusValueFromMap != null && !statusValueFromMap.isEmpty()) {
        tripBookingTaskStatusDetails.setStatus(statusValueFromMap);
      }
      String stateTaskValueFromMap = map.get("stateTask");
      Result<DataRef> stateTask = stateTaskDataRefConverter.fromString(stateTaskValueFromMap);
      if (stateTask.isError()) {
        return Result.error("stateTask: " + stateTask.getMessage());
      }
      tripBookingTaskStatusDetails.setStateTask(stateTask.getValue());

      String tripBookingValueFromMap = map.get("tripBooking");
      Result<DataRef> tripBooking = tripBookingDataRefConverter.fromString(tripBookingValueFromMap);
      if (tripBooking.isError()) {
        return Result.error("tripBooking: " + tripBooking.getMessage());
      }
      tripBookingTaskStatusDetails.setTripBooking(tripBooking.getValue());
      // anchor:convert-map-to-fields:end

      // @anchor:map-to-fields:start
      // @anchor:map-to-fields:end
      // anchor:custom-map-to-fields:start
      // anchor:custom-map-to-fields:end

      return Result.success(tripBookingTaskStatusDetails);
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
