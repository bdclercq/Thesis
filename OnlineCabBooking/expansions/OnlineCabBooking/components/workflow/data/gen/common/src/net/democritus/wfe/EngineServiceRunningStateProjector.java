package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import net.democritus.projection.IDataElementProjector;
import net.democritus.projection.IDataProjectorRequired;

import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.diagnostics.DiagnosticHelper;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.DataRefValidation;

// anchor:valueType-imports:start
import net.democritus.valuetype.basic.DateTime;
import net.democritus.valuetype.basic.DateTimeConverter;

import java.util.Date;
// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
import java.util.Date;
// anchor:custom-imports:end

public class EngineServiceRunningStateProjector implements IDataElementProjector<EngineServiceData, EngineServiceRunningState>, IDataProjectorRequired<EngineServiceRunningState> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final EngineServiceCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineServiceRunningStateProjector(EngineServiceCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public EngineServiceRunningState project(ParameterContext<EngineServiceData> dataParameter) {

    EngineServiceRunningState projection = new EngineServiceRunningState();
    EngineServiceData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setStatus(data.getStatus());
      projection.setBusy(data.getBusy());
      projection.setWaitTime(data.getWaitTime());
      projection.setLastRunAt(new DateTimeConverter().fromDate(data.getLastRunAt()).getValue());
      projection.setBatchSize(data.getBatchSize());
      projection.setMaximumNumberOfNodes(data.getMaximumNumberOfNodes());
      projection.setTimeWindowGroup(crudsInternal.getTimeWindowGroup(dataParameter.construct(data.getTimeWindowGroup())).getValueOrElse(EMPTY_DATA_REF));
      projection.setRunning(calculateRunning(dataParameter, projection));
      projection.setNextRun(calculateNextRun(dataParameter, projection));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(EngineServiceData data, ParameterContext<EngineServiceRunningState> projectionParameter) {
    // don't set the data id
    EngineServiceRunningState projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setStatus(projection.getStatus());
    data.setBusy(projection.getBusy());
    data.setWaitTime(projection.getWaitTime());
    data.setLastRunAt(new DateTimeConverter().asDate(projection.getLastRunAt()).getValue());
    data.setBatchSize(projection.getBatchSize());
    data.setMaximumNumberOfNodes(projection.getMaximumNumberOfNodes());
    crudsInternal.setTimeWindowGroup(data, projectionParameter.construct(projection.getTimeWindowGroup()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(EngineServiceRunningState projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "runningState";
  }

  public Class<EngineServiceRunningState> getProjectionClass() {
    return EngineServiceRunningState.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<EngineServiceRunningState> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  public String calculateRunning(ParameterContext<EngineServiceData> dataParameter, EngineServiceRunningState projection) {
    String result = null;
    // anchor:custom-calculation-running:start
    EngineServiceData engineServiceData = dataParameter.getValue();
    if (!hasRunningNode(engineServiceData)) {
      result = "not running";
    } else if ("start".equals(engineServiceData.getStatus())) {
      result = "running [enabled]";
    } else {
      result = "running [disabled]";
    }
    // anchor:custom-calculation-running:end
    return result;
  }

  public Date calculateNextRun(ParameterContext<EngineServiceData> dataParameter, EngineServiceRunningState projection) {
    Date result = null;
    // anchor:custom-calculation-nextRun:start
    EngineServiceData engineServiceData = dataParameter.getValue();
    Date earliestRun = null;
    for (EngineNodeServiceData engineNodeServiceData : engineServiceData.getEngineNodeServices()) {
      if (EngineNodeServiceState.NOT_RESPONDING.getStatus().equals(engineNodeServiceData.getStatus())) {
        continue;
      }
      Date nextNodeRun = engineNodeServiceData.getNextRun();
      if (earliestRun == null || (nextNodeRun != null && nextNodeRun.before(earliestRun))) {
        earliestRun = nextNodeRun;
      }
    }
    result = earliestRun;
    // anchor:custom-calculation-nextRun:end
    return result;
  }
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  private boolean hasRunningNode(EngineServiceData engineServiceData) {
    for (EngineNodeServiceData engineNodeServiceData : engineServiceData.getEngineNodeServices()) {
      if (!EngineNodeServiceState.NOT_RESPONDING.getStatus().equals(engineNodeServiceData.getStatus())) {
        return true;
      }
    }
    return false;
  }
  // anchor:custom-methods:end
}
