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

public class MenuItemInfoProjector implements IDataElementProjector<MenuItemData, MenuItemInfo>, IDataProjectorRequired<MenuItemInfo> {

  private static final List<DataRef> EMPTY_DATA_REF_COLLECTION = new ArrayList<>();

  private final MenuItemCrudsInternal crudsInternal;

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public MenuItemInfoProjector(MenuItemCrudsInternal crudsInternal) {
    this.crudsInternal = crudsInternal;
  }

  public MenuItemInfo project(ParameterContext<MenuItemData> dataParameter) {

    MenuItemInfo projection = new MenuItemInfo();
    MenuItemData data = dataParameter.getValue();

    if (data != null) {
      projection.setId(data.getId());
      projection.setName(crudsInternal.getDisplayName(dataParameter));

      // anchor:project-setters:start
      projection.setSortOrder(data.getSortOrder());
      projection.setMenu(crudsInternal.getMenu(dataParameter.construct(data.getMenu())).getValueOrElse(EMPTY_DATA_REF));
      projection.setScreen(crudsInternal.getScreen(dataParameter.construct(data.getScreen())).getValueOrElse(EMPTY_DATA_REF));
      projection.setMenuItem(crudsInternal.getMenuItem(dataParameter.construct(data.getMenuItem())).getValueOrElse(EMPTY_DATA_REF));
      projection.setSubmenuItems(crudsInternal.getSubmenuItems(dataParameter).getValueOrElse(EMPTY_DATA_REF_COLLECTION));
      // anchor:project-setters:end
      // @anchor:project-setters:start
      // @anchor:project-setters:end
      // anchor:custom-project-setters:start
      // anchor:custom-project-setters:end

    }

    return projection;
  }

  public void toData(MenuItemData data, ParameterContext<MenuItemInfo> projectionParameter) {
    // don't set the data id
    MenuItemInfo projection = projectionParameter.getValue();

    // anchor:toData-setters:start
    data.setSortOrder(projection.getSortOrder());
    crudsInternal.setMenu(data, projectionParameter.construct(projection.getMenu()));
    crudsInternal.setScreen(data, projectionParameter.construct(projection.getScreen()));
    crudsInternal.setMenuItem(data, projectionParameter.construct(projection.getMenuItem()));
    // anchor:toData-setters:end
    // @anchor:toData-setters:start
    // @anchor:toData-setters:end
    // anchor:custom-toData-setters:start
    // anchor:custom-toData-setters:end
  }

  public DataRef getDataRef(MenuItemInfo projection) {
    return projection.getDataRef();
  }

  public String getName() {
    return "info";
  }

  public Class<MenuItemInfo> getProjectionClass() {
    return MenuItemInfo.class;
  }

  public String checkNullTranslation(String translatedValue) {
    return translatedValue != null ? translatedValue : "-- no translation --";
  }

  public CrudsResult<Void> checkRequired(ParameterContext<MenuItemInfo> projectionParameter) {
    return CrudsResult.success();
  }

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
