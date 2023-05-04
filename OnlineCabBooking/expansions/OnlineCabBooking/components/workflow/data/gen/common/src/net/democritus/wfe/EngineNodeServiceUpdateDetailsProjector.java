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
import java.util.Date;

// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineNodeServiceUpdateDetailsProjector implements IDataElementProjector<EngineNodeServiceData, EngineNodeServiceUpdateDetails>, IDataProjectorRequired<EngineNodeServiceUpdateDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final EngineNodeServiceCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineNodeServiceUpdateDetailsProjector(EngineNodeServiceCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public EngineNodeServiceUpdateDetails project(ParameterContext<EngineNodeServiceData> dataParameter) {

    EngineNodeServiceUpdateDetails projection = new EngineNodeServiceUpdateDetails();
    EngineNodeServiceData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setStatus(data.getStatus());
      projection.setLastRunAt(data.getLastRunAt());
      projection.setNextRun(data.getNextRun());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(EngineNodeServiceData data, ParameterContext<EngineNodeServiceUpdateDetails> projectionParameter) {
    // don't set the data id
    EngineNodeServiceUpdateDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setStatus(projection.getStatus());
    data.setLastRunAt(projection.getLastRunAt());
    data.setNextRun(projection.getNextRun());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(EngineNodeServiceUpdateDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "updateDetails";
  }

  public Class<EngineNodeServiceUpdateDetails> getProjectionClass() {
    return EngineNodeServiceUpdateDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<EngineNodeServiceUpdateDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
