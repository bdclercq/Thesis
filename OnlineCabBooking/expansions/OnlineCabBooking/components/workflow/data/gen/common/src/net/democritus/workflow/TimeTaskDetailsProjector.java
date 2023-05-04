package net.democritus.workflow;

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

public class TimeTaskDetailsProjector implements IDataElementProjector<TimeTaskData, TimeTaskDetails>, IDataProjectorRequired<TimeTaskDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final TimeTaskCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TimeTaskDetailsProjector(TimeTaskCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public TimeTaskDetails project(ParameterContext<TimeTaskData> dataParameter) {

    TimeTaskDetails projection = new TimeTaskDetails();
    TimeTaskData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setProcessor(data.getProcessor());
      projection.setImplementation(data.getImplementation());
      projection.setParams(data.getParams());
      projection.setTriggerState(data.getTriggerState());
      projection.setIntervalPeriod(data.getIntervalPeriod());
      projection.setRequiredAction(data.getRequiredAction());
      projection.setWorkflow(crudsInternal.getWorkflow(dataParameter.construct(data.getWorkflow())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(TimeTaskData data, ParameterContext<TimeTaskDetails> projectionParameter) {
    // don't set the data id
    TimeTaskDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setProcessor(projection.getProcessor());
    data.setImplementation(projection.getImplementation());
    data.setParams(projection.getParams());
    data.setTriggerState(projection.getTriggerState());
    data.setIntervalPeriod(projection.getIntervalPeriod());
    data.setRequiredAction(projection.getRequiredAction());
    crudsInternal.setWorkflow(data, projectionParameter.construct(projection.getWorkflow()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(TimeTaskDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<TimeTaskDetails> getProjectionClass() {
    return TimeTaskDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<TimeTaskDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
