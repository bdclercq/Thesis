package net.democritus.usr;

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

public class AccountDetailsProjector implements IDataElementProjector<AccountData, AccountDetails>, IDataProjectorRequired<AccountDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final AccountCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public AccountDetailsProjector(AccountCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public AccountDetails project(ParameterContext<AccountData> dataParameter) {

    AccountDetails projection = new AccountDetails();
    AccountData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setRefId(data.getRefId());
      projection.setFullName(data.getFullName());
      projection.setAddress(data.getAddress());
      projection.setZipCode(data.getZipCode());
      projection.setCity(data.getCity());
      projection.setCountry(data.getCountry());
      projection.setEmail(data.getEmail());
      projection.setPhone(data.getPhone());
      projection.setStyle(data.getStyle());
      projection.setStatus(data.getStatus());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(AccountData data, ParameterContext<AccountDetails> projectionParameter) {
    // don't set the data id
    AccountDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setRefId(projection.getRefId());
    data.setFullName(projection.getFullName());
    data.setAddress(projection.getAddress());
    data.setZipCode(projection.getZipCode());
    data.setCity(projection.getCity());
    data.setCountry(projection.getCountry());
    data.setEmail(projection.getEmail());
    data.setPhone(projection.getPhone());
    data.setStyle(projection.getStyle());
    data.setStatus(projection.getStatus());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(AccountDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<AccountDetails> getProjectionClass() {
    return AccountDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<AccountDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
