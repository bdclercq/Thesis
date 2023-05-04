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
import java.util.Date;

// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserDetailsProjector implements IDataElementProjector<UserData, UserDetails>, IDataProjectorRequired<UserDetails> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final UserCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public UserDetailsProjector(UserCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public UserDetails project(ParameterContext<UserData> dataParameter) {

    UserDetails projection = new UserDetails();
    UserData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setPassword(data.getPassword());
      projection.setFullName(data.getFullName());
      projection.setEmail(data.getEmail());
      projection.setMobile(data.getMobile());
      projection.setLanguage(data.getLanguage());
      projection.setFirstName(data.getFirstName());
      projection.setLastName(data.getLastName());
      projection.setPersNr(data.getPersNr());
      projection.setLastModifiedAt(data.getLastModifiedAt());
      projection.setEnteredAt(data.getEnteredAt());
      projection.setDisabled(data.getDisabled());
      projection.setTimeout(data.getTimeout());
      projection.setEncryptedPassword(data.getEncryptedPassword());
      projection.setAccount(crudsInternal.getAccount(dataParameter.construct(data.getAccount())).getValueOrElse(EMPTY_DATA_REF));
      projection.setProfile(crudsInternal.getProfile(dataParameter.construct(data.getProfile())).getValueOrElse(EMPTY_DATA_REF));
      projection.setUserGroups(crudsInternal.getUserGroups(dataParameter).getValueOrElse(EMPTY_DATA_REF_COLLECTION));
      projection.setAccountDetails(crudsInternal.getAccountDetails(dataParameter.construct(projection.getAccount())).getValueOrElse(null));
      projection.setProfileDetails(crudsInternal.getProfileDetails(dataParameter.construct(projection.getProfile())).getValueOrElse(null));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(UserData data, ParameterContext<UserDetails> projectionParameter) {
    // don't set the data id
    UserDetails projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setPassword(projection.getPassword());
    data.setFullName(projection.getFullName());
    data.setEmail(projection.getEmail());
    data.setMobile(projection.getMobile());
    data.setLanguage(projection.getLanguage());
    data.setFirstName(projection.getFirstName());
    data.setLastName(projection.getLastName());
    data.setPersNr(projection.getPersNr());
    data.setDisabled(projection.getDisabled());
    data.setTimeout(projection.getTimeout());
    data.setEncryptedPassword(projection.getEncryptedPassword());
    crudsInternal.setAccount(data, projectionParameter.construct(projection.getAccount()));
    crudsInternal.setProfile(data, projectionParameter.construct(projection.getProfile()));
    crudsInternal.setUserGroups(data, projectionParameter.construct(projection.getUserGroups()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(UserDetails projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "details";
  }

  public Class<UserDetails> getProjectionClass() {
    return UserDetails.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<UserDetails> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
