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

public class EngineNodeDetailsProjector implements IDataElementProjector<EngineNodeData, EngineNodeDetails>, IDataProjectorRequired<EngineNodeDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final EngineNodeCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineNodeDetailsProjector(EngineNodeCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public EngineNodeDetails project(ParameterContext<EngineNodeData> dataParameter) {

    EngineNodeDetails projection = new EngineNodeDetails();
    EngineNodeData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setStatus(data.getStatus());
      projection.setHostname(data.getHostname());
      projection.setMaster(data.getMaster());
      projection.setLastActive(data.getLastActive());
      projection.setActiveSince(data.getActiveSince());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(EngineNodeData data, ParameterContext<EngineNodeDetails> projectionParameter) {
    // don't set the data id
    EngineNodeDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setStatus(projection.getStatus());
    data.setHostname(projection.getHostname());
    data.setMaster(projection.getMaster());
    data.setLastActive(projection.getLastActive());
    data.setActiveSince(projection.getActiveSince());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(EngineNodeDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<EngineNodeDetails> getProjectionClass() {
    return EngineNodeDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<EngineNodeDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
