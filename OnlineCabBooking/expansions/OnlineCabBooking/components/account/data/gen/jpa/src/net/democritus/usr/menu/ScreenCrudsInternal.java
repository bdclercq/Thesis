package net.democritus.usr.menu;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Collection;
import java.util.List;

import net.democritus.sys.DataRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.search.SearchDetails;

import java.util.Date;
import java.util.List;

// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import net.democritus.component.ComponentData;
import net.democritus.component.ComponentDetails;
import net.democritus.component.ComponentCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ScreenCrudsInternal extends ScreenCrudsLocal {

  /* get the underlying data object */
  CrudsResult<ScreenData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<ScreenData> screenDataParameter);

  /** find the data objects */
  <S extends ScreenFindDetails> SearchResult<ScreenData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<ScreenDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<ScreenDetails>> getDetailsListFromData(ParameterContext<Collection<ScreenData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<ScreenData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<ScreenData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getComponent(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<ComponentDetails> getComponentDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setComponent(ScreenData screenData, ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-wrapper-methods:start
  // anchor:calculation-wrapper-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
