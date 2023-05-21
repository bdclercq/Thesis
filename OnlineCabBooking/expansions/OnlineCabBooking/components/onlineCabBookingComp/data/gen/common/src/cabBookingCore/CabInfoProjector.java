package cabBookingCore;

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

public class CabInfoProjector implements IDataElementProjector<CabData, CabInfo>, IDataProjectorRequired<CabInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final CabCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public CabInfoProjector(CabCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public CabInfo project(ParameterContext<CabData> dataParameter) {

    CabInfo projection = new CabInfo();
    CabData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setRatePerKm(data.getRatePerKm());
      projection.setName(data.getName());
      projection.setCarType(crudsInternal.getCarType(dataParameter.construct(data.getCarType())).getValueOrElse(EMPTY_DATA_REF));
      projection.setDriver(crudsInternal.getDriver(dataParameter.construct(data.getDriver())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(CabData data, ParameterContext<CabInfo> projectionParameter) {
    // don't set the data id
    CabInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setRatePerKm(projection.getRatePerKm());
    data.setName(projection.getName());
    crudsInternal.setCarType(data, projectionParameter.construct(projection.getCarType()));
    crudsInternal.setDriver(data, projectionParameter.construct(projection.getDriver()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(CabInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<CabInfo> getProjectionClass() {
    return CabInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<CabInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
