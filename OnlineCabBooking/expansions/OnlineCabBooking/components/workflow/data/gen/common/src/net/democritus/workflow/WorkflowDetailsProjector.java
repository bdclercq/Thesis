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

public class WorkflowDetailsProjector implements IDataElementProjector<WorkflowData, WorkflowDetails>, IDataProjectorRequired<WorkflowDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final WorkflowCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public WorkflowDetailsProjector(WorkflowCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public WorkflowDetails project(ParameterContext<WorkflowData> dataParameter) {

    WorkflowDetails projection = new WorkflowDetails();
    WorkflowData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setTarget(data.getTarget());
      projection.setComponentName(data.getComponentName());
      projection.setClassName(data.getClassName());
      projection.setSequencingStrategy(data.getSequencingStrategy());
      projection.setResponsible(crudsInternal.getResponsible(dataParameter.construct(data.getResponsible())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(WorkflowData data, ParameterContext<WorkflowDetails> projectionParameter) {
    // don't set the data id
    WorkflowDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setTarget(projection.getTarget());
    data.setComponentName(projection.getComponentName());
    data.setClassName(projection.getClassName());
    data.setSequencingStrategy(projection.getSequencingStrategy());
    crudsInternal.setResponsible(data, projectionParameter.construct(projection.getResponsible()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(WorkflowDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<WorkflowDetails> getProjectionClass() {
    return WorkflowDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<WorkflowDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
