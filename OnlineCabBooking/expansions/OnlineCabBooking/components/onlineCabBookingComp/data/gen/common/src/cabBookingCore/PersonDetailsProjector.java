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

public class PersonDetailsProjector implements IDataElementProjector<PersonData, PersonDetails>, IDataProjectorRequired<PersonDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final PersonCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public PersonDetailsProjector(PersonCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public PersonDetails project(ParameterContext<PersonData> dataParameter) {

    PersonDetails projection = new PersonDetails();
    PersonData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setUsername(data.getUsername());
      projection.setPassword(data.getPassword());
      projection.setEmail(data.getEmail());
      projection.setMobile(data.getMobile());
      projection.setAddress(crudsInternal.getAddress(dataParameter.construct(data.getAddress())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(PersonData data, ParameterContext<PersonDetails> projectionParameter) {
    // don't set the data id
    PersonDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setUsername(projection.getUsername());
    data.setPassword(projection.getPassword());
    data.setEmail(projection.getEmail());
    data.setMobile(projection.getMobile());
    crudsInternal.setAddress(data, projectionParameter.construct(projection.getAddress()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(PersonDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<PersonDetails> getProjectionClass() {
    return PersonDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<PersonDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
