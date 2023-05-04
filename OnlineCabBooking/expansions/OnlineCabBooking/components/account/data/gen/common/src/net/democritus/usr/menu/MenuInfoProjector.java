package net.democritus.usr.menu;

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

public class MenuInfoProjector implements IDataElementProjector<MenuData, MenuInfo>, IDataProjectorRequired<MenuInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final MenuCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public MenuInfoProjector(MenuCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public MenuInfo project(ParameterContext<MenuData> dataParameter) {

    MenuInfo projection = new MenuInfo();
    MenuData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());

      // anchor:project-setters:start
      projection.setName(data.getName());
      projection.setPortal(crudsInternal.getPortal(dataParameter.construct(data.getPortal())).getValueOrElse(EMPTY_DATA_REF));
      projection.setProfile(crudsInternal.getProfile(dataParameter.construct(data.getProfile())).getValueOrElse(EMPTY_DATA_REF));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(MenuData data, ParameterContext<MenuInfo> projectionParameter) {
    // don't set the data id
    MenuInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setName(projection.getName());
    crudsInternal.setPortal(data, projectionParameter.construct(projection.getPortal()));
    crudsInternal.setProfile(data, projectionParameter.construct(projection.getProfile()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(MenuInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<MenuInfo> getProjectionClass() {
    return MenuInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<MenuInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
