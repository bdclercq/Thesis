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

public class DriverInfoProjector implements IDataElementProjector<DriverData, DriverInfo>, IDataProjectorRequired<DriverInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final DriverCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DriverInfoProjector(DriverCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public DriverInfo project(ParameterContext<DriverData> dataParameter) {

    DriverInfo projection = new DriverInfo();
    DriverData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setLicenseNo(data.getLicenseNo());
      projection.setRating(data.getRating());
      projection.setIsAvailable(data.getIsAvailable());
      projection.setCab(crudsInternal.getCab(dataParameter.construct(data.getCab())).getValueOrElse(EMPTY_DATA_REF));
      projection.setTripBooking(crudsInternal.getTripBooking(dataParameter.construct(data.getTripBooking())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(DriverData data, ParameterContext<DriverInfo> projectionParameter) {
    // don't set the data id
    DriverInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setLicenseNo(projection.getLicenseNo());
    data.setRating(projection.getRating());
    data.setIsAvailable(projection.getIsAvailable());
    crudsInternal.setCab(data, projectionParameter.construct(projection.getCab()));
    crudsInternal.setTripBooking(data, projectionParameter.construct(projection.getTripBooking()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(DriverInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<DriverInfo> getProjectionClass() {
    return DriverInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<DriverInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
