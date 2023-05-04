package net.democritus.sys;

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

public class ParamTargetValueInfoProjector implements IDataElementProjector<ParamTargetValueData, ParamTargetValueInfo>, IDataProjectorRequired<ParamTargetValueInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final ParamTargetValueCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ParamTargetValueInfoProjector(ParamTargetValueCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public ParamTargetValueInfo project(ParameterContext<ParamTargetValueData> dataParameter) {

    ParamTargetValueInfo projection = new ParamTargetValueInfo();
    ParamTargetValueData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setParam(data.getParam());
      projection.setTarget(data.getTarget());
      projection.setValue(data.getValue());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(ParamTargetValueData data, ParameterContext<ParamTargetValueInfo> projectionParameter) {
    // don't set the data id
    ParamTargetValueInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setParam(projection.getParam());
    data.setTarget(projection.getTarget());
    data.setValue(projection.getValue());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(ParamTargetValueInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<ParamTargetValueInfo> getProjectionClass() {
    return ParamTargetValueInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<ParamTargetValueInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
