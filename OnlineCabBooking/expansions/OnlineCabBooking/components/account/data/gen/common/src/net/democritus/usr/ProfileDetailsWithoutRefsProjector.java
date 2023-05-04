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

public class ProfileDetailsWithoutRefsProjector implements IDataElementProjector<ProfileData, ProfileDetailsWithoutRefs>, IDataProjectorRequired<ProfileDetailsWithoutRefs> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final ProfileCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ProfileDetailsWithoutRefsProjector(ProfileCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public ProfileDetailsWithoutRefs project(ParameterContext<ProfileData> dataParameter) {

    ProfileDetailsWithoutRefs projection = new ProfileDetailsWithoutRefs();
    ProfileData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setWeight(data.getWeight());
      // anchor:project-setters:end
      // @anchor:project-setters:start
      projection.setUserGroupId(data.getUserGroup());
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(ProfileData data, ParameterContext<ProfileDetailsWithoutRefs> projectionParameter) {
    // don't set the data id
    ProfileDetailsWithoutRefs projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    data.setWeight(projection.getWeight());
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    crudsInternal.setUserGroup(data, projectionParameter.construct(DataRef.withId(projection.getUserGroupId())));
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(ProfileDetailsWithoutRefs projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "detailsWithoutRefs";
  }

  public Class<ProfileDetailsWithoutRefs> getProjectionClass() {
    return ProfileDetailsWithoutRefs.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<ProfileDetailsWithoutRefs> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
