package net.democritus.acl;

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

public class DataAccessInfoProjector implements IDataElementProjector<DataAccessData, DataAccessInfo>, IDataProjectorRequired<DataAccessInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final DataAccessCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DataAccessInfoProjector(DataAccessCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public DataAccessInfo project(ParameterContext<DataAccessData> dataParameter) {

    DataAccessInfo projection = new DataAccessInfo();
    DataAccessData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setElement(data.getElement());
      projection.setTarget(data.getTarget());
      projection.setFunctionality(data.getFunctionality());
      projection.setAuthorized(data.getAuthorized());
      projection.setForProfile(crudsInternal.getForProfile(dataParameter.construct(data.getForProfile())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(DataAccessData data, ParameterContext<DataAccessInfo> projectionParameter) {
    // don't set the data id
    DataAccessInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setElement(projection.getElement());
    data.setTarget(projection.getTarget());
    data.setFunctionality(projection.getFunctionality());
    data.setAuthorized(projection.getAuthorized());
    crudsInternal.setForProfile(data, projectionParameter.construct(projection.getForProfile()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(DataAccessInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<DataAccessInfo> getProjectionClass() {
    return DataAccessInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<DataAccessInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
