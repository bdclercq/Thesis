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
// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineServiceInfoProjector implements IDataElementProjector<EngineServiceData, EngineServiceInfo>, IDataProjectorRequired<EngineServiceInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final EngineServiceCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineServiceInfoProjector(EngineServiceCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public EngineServiceInfo project(ParameterContext<EngineServiceData> dataParameter) {

    EngineServiceInfo projection = new EngineServiceInfo();
    EngineServiceData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setStatus(data.getStatus());
      projection.setWaitTime(data.getWaitTime());
      projection.setBatchSize(data.getBatchSize());
      projection.setWorkflow(crudsInternal.getWorkflow(dataParameter.construct(data.getWorkflow())).getValueOrElse(EMPTY_DATA_REF));
      projection.setTimeWindowGroup(crudsInternal.getTimeWindowGroup(dataParameter.construct(data.getTimeWindowGroup())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(EngineServiceData data, ParameterContext<EngineServiceInfo> projectionParameter) {
    // don't set the data id
    EngineServiceInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setStatus(projection.getStatus());
    data.setWaitTime(projection.getWaitTime());
    data.setBatchSize(projection.getBatchSize());
    crudsInternal.setWorkflow(data, projectionParameter.construct(projection.getWorkflow()));
    crudsInternal.setTimeWindowGroup(data, projectionParameter.construct(projection.getTimeWindowGroup()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(EngineServiceInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<EngineServiceInfo> getProjectionClass() {
    return EngineServiceInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<EngineServiceInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
