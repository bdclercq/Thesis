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

// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineServiceDetailsWithoutRefsProjector implements IDataElementProjector<EngineServiceData, EngineServiceDetailsWithoutRefs>, IDataProjectorRequired<EngineServiceDetailsWithoutRefs> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final EngineServiceCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public EngineServiceDetailsWithoutRefsProjector(EngineServiceCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public EngineServiceDetailsWithoutRefs project(ParameterContext<EngineServiceData> dataParameter) {

    EngineServiceDetailsWithoutRefs projection = new EngineServiceDetailsWithoutRefs();
    EngineServiceData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setStatus(data.getStatus());
      projection.setChanged(data.getChanged());
      projection.setBusy(data.getBusy());
      projection.setWaitTime(data.getWaitTime());
      projection.setCollector(data.getCollector());
      projection.setLastRunAt(new DateTimeConverter().fromDate(data.getLastRunAt()).getValue());
      projection.setBatchSize(data.getBatchSize());
      projection.setMaximumNumberOfNodes(data.getMaximumNumberOfNodes());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      projection.setWorkflowId(data.getWorkflow());
      projection.setTimeWindowGroupId(data.getTimeWindowGroup());
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(EngineServiceData data, ParameterContext<EngineServiceDetailsWithoutRefs> projectionParameter) {
    // don't set the data id
    EngineServiceDetailsWithoutRefs projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setStatus(projection.getStatus());
    data.setChanged(projection.getChanged());
    data.setBusy(projection.getBusy());
    data.setWaitTime(projection.getWaitTime());
    data.setCollector(projection.getCollector());
    data.setLastRunAt(new DateTimeConverter().asDate(projection.getLastRunAt()).getValue());
    data.setBatchSize(projection.getBatchSize());
    data.setMaximumNumberOfNodes(projection.getMaximumNumberOfNodes());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    crudsInternal.setWorkflow(data, projectionParameter.construct(DataRef.withId(projection.getWorkflowId())));
    crudsInternal.setTimeWindowGroup(data, projectionParameter.construct(DataRef.withId(projection.getTimeWindowGroupId())));
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(EngineServiceDetailsWithoutRefs projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "detailsWithoutRefs";
  }

  public Class<EngineServiceDetailsWithoutRefs> getProjectionClass() {
    return EngineServiceDetailsWithoutRefs.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<EngineServiceDetailsWithoutRefs> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
