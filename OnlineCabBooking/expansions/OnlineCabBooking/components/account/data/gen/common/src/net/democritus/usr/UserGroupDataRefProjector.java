package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;
import net.democritus.projection.IDataElementProjector;
import net.democritus.projection.IDataProjectorRequired;

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserGroupDataRefProjector implements IDataElementProjector<UserGroupData, DataRef>, IDataProjectorRequired<DataRef> {

  private final UserGroupCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public UserGroupDataRefProjector(UserGroupCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  // anchor:project:start
  public DataRef project(ParameterContext<UserGroupData> dataParameter) {

    DataRef projection = DataRef.withId(0L);
    projection.setComponentName("account");
    projection.setPackName("net.democritus.usr");
    projection.setClassName("UserGroup");

    UserGroupData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end
    }

    return projection;
  }
  // anchor:project:end

  // anchor:toData:start
  public void toData(UserGroupData data, ParameterContext<DataRef> projectionParameter) {
    // cannot modify with dataRef
  }
  // anchor:toData:end

  public DataRef getDataRef(DataRef projection) {
    return projection;
  }

  public String getName() {
    return "dataRef";
  }

  public Class<DataRef> getProjectionClass() {
    return DataRef.class;
  }

  public CrudsResult<Void> checkRequired(ParameterContext<DataRef> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
