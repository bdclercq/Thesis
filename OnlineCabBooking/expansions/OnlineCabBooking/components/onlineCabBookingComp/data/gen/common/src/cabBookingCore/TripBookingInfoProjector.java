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
import java.util.Date;

// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class TripBookingInfoProjector implements IDataElementProjector<TripBookingData, TripBookingInfo>, IDataProjectorRequired<TripBookingInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final TripBookingCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TripBookingInfoProjector(TripBookingCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public TripBookingInfo project(ParameterContext<TripBookingData> dataParameter) {

    TripBookingInfo projection = new TripBookingInfo();
    TripBookingData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setFromDateTime(data.getFromDateTime());
      projection.setToDateTime(data.getToDateTime());
      projection.setKm(data.getKm());
      projection.setTotalAmount(data.getTotalAmount());
      projection.setCustomer(crudsInternal.getCustomer(dataParameter.construct(data.getCustomer())).getValueOrElse(EMPTY_DATA_REF));
      projection.setDriver(crudsInternal.getDriver(dataParameter.construct(data.getDriver())).getValueOrElse(EMPTY_DATA_REF));
      projection.setFromLocation(crudsInternal.getFromLocation(dataParameter.construct(data.getFromLocation())).getValueOrElse(EMPTY_DATA_REF));
      projection.setToLocation(crudsInternal.getToLocation(dataParameter.construct(data.getToLocation())).getValueOrElse(EMPTY_DATA_REF));
      projection.setPayment(crudsInternal.getPayment(dataParameter.construct(data.getPayment())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(TripBookingData data, ParameterContext<TripBookingInfo> projectionParameter) {
    // don't set the data id
    TripBookingInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setFromDateTime(projection.getFromDateTime());
    data.setToDateTime(projection.getToDateTime());
    data.setKm(projection.getKm());
    data.setTotalAmount(projection.getTotalAmount());
    crudsInternal.setCustomer(data, projectionParameter.construct(projection.getCustomer()));
    crudsInternal.setDriver(data, projectionParameter.construct(projection.getDriver()));
    crudsInternal.setFromLocation(data, projectionParameter.construct(projection.getFromLocation()));
    crudsInternal.setToLocation(data, projectionParameter.construct(projection.getToLocation()));
    crudsInternal.setPayment(data, projectionParameter.construct(projection.getPayment()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(TripBookingInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<TripBookingInfo> getProjectionClass() {
    return TripBookingInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<TripBookingInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
