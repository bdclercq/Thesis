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
import java.util.Date;

// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class DataAccessDetailsProjector implements IDataElementProjector<DataAccessData, DataAccessDetails>, IDataProjectorRequired<DataAccessDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final DataAccessCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public DataAccessDetailsProjector(DataAccessCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public DataAccessDetails project(ParameterContext<DataAccessData> dataParameter) {

    DataAccessDetails projection = new DataAccessDetails();
    DataAccessData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setElement(data.getElement());
      projection.setTarget(data.getTarget());
      projection.setFunctionality(data.getFunctionality());
      projection.setAuthorized(data.getAuthorized());
      projection.setLastModifiedAt(data.getLastModifiedAt());
      projection.setEnteredAt(data.getEnteredAt());
      projection.setDisabled(data.getDisabled());
      projection.setForProfile(crudsInternal.getForProfile(dataParameter.construct(data.getForProfile())).getValueOrElse(EMPTY_DATA_REF));
      projection.setForUser(crudsInternal.getForUser(dataParameter.construct(data.getForUser())).getValueOrElse(EMPTY_DATA_REF));
      projection.setUserGroups(crudsInternal.getUserGroups(dataParameter).getValueOrElse(EMPTY_DATA_REF_COLLECTION));
      projection.setForUserGroup(crudsInternal.getForUserGroup(dataParameter.construct(data.getForUserGroup())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(DataAccessData data, ParameterContext<DataAccessDetails> projectionParameter) {
    // don't set the data id
    DataAccessDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setElement(projection.getElement());
    data.setTarget(projection.getTarget());
    data.setFunctionality(projection.getFunctionality());
    data.setAuthorized(projection.getAuthorized());
    data.setDisabled(projection.getDisabled());
    crudsInternal.setForProfile(data, projectionParameter.construct(projection.getForProfile()));
    crudsInternal.setForUser(data, projectionParameter.construct(projection.getForUser()));
    crudsInternal.setUserGroups(data, projectionParameter.construct(projection.getUserGroups()));
    crudsInternal.setForUserGroup(data, projectionParameter.construct(projection.getForUserGroup()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(DataAccessDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<DataAccessDetails> getProjectionClass() {
    return DataAccessDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<DataAccessDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
