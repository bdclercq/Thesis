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

public class TripBookingTaskStatusDetailsProjector implements IDataElementProjector<TripBookingTaskStatusData, TripBookingTaskStatusDetails>, IDataProjectorRequired<TripBookingTaskStatusDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final TripBookingTaskStatusCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public TripBookingTaskStatusDetailsProjector(TripBookingTaskStatusCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public TripBookingTaskStatusDetails project(ParameterContext<TripBookingTaskStatusData> dataParameter) {

    TripBookingTaskStatusDetails projection = new TripBookingTaskStatusDetails();
    TripBookingTaskStatusData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setStartedAt(data.getStartedAt());
      projection.setFinishedAt(data.getFinishedAt());
      projection.setStatus(data.getStatus());
      projection.setStateTask(crudsInternal.getStateTask(dataParameter.construct(data.getStateTask())).getValueOrElse(EMPTY_DATA_REF));
      projection.setTripBooking(crudsInternal.getTripBooking(dataParameter.construct(data.getTripBooking())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(TripBookingTaskStatusData data, ParameterContext<TripBookingTaskStatusDetails> projectionParameter) {
    // don't set the data id
    TripBookingTaskStatusDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setStartedAt(projection.getStartedAt());
    data.setFinishedAt(projection.getFinishedAt());
    data.setStatus(projection.getStatus());
    crudsInternal.setStateTask(data, projectionParameter.construct(projection.getStateTask()));
    crudsInternal.setTripBooking(data, projectionParameter.construct(projection.getTripBooking()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(TripBookingTaskStatusDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<TripBookingTaskStatusDetails> getProjectionClass() {
    return TripBookingTaskStatusDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<TripBookingTaskStatusDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
