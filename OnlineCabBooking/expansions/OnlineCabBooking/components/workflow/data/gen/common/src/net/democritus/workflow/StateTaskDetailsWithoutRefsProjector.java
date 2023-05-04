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

public class StateTaskDetailsWithoutRefsProjector implements IDataElementProjector<StateTaskData, StateTaskDetailsWithoutRefs>, IDataProjectorRequired<StateTaskDetailsWithoutRefs> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final StateTaskCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public StateTaskDetailsWithoutRefsProjector(StateTaskCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public StateTaskDetailsWithoutRefs project(ParameterContext<StateTaskData> dataParameter) {

    StateTaskDetailsWithoutRefs projection = new StateTaskDetailsWithoutRefs();
    StateTaskData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setProcessor(data.getProcessor());
      projection.setImplementation(data.getImplementation());
      projection.setParams(data.getParams());
      projection.setBeginState(data.getBeginState());
      projection.setInterimState(data.getInterimState());
      projection.setFailedState(data.getFailedState());
      projection.setEndState(data.getEndState());
      projection.setMaxConcurrentTasks(data.getMaxConcurrentTasks());
      projection.setTimeout(data.getTimeout());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      projection.setWorkflowId(data.getWorkflow());
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(StateTaskData data, ParameterContext<StateTaskDetailsWithoutRefs> projectionParameter) {
    // don't set the data id
    StateTaskDetailsWithoutRefs projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setProcessor(projection.getProcessor());
    data.setImplementation(projection.getImplementation());
    data.setParams(projection.getParams());
    data.setBeginState(projection.getBeginState());
    data.setInterimState(projection.getInterimState());
    data.setFailedState(projection.getFailedState());
    data.setEndState(projection.getEndState());
    data.setMaxConcurrentTasks(projection.getMaxConcurrentTasks());
    data.setTimeout(projection.getTimeout());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    crudsInternal.setWorkflow(data, projectionParameter.construct(DataRef.withId(projection.getWorkflowId())));
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(StateTaskDetailsWithoutRefs projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "detailsWithoutRefs";
  }

  public Class<StateTaskDetailsWithoutRefs> getProjectionClass() {
    return StateTaskDetailsWithoutRefs.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<StateTaskDetailsWithoutRefs> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
