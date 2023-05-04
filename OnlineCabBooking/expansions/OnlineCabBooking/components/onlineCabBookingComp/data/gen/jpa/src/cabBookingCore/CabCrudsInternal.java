package cabBookingCore;

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
import cabBookingCore.CarTypeData;
import cabBookingCore.CarTypeDetails;
import cabBookingCore.CarTypeCrudsInternal;

import cabBookingCore.DriverData;
import cabBookingCore.DriverDetails;
import cabBookingCore.DriverCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface CabCrudsInternal extends CabCrudsLocal {

  /* get the underlying data object */
  CrudsResult<CabData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<CabData> cabDataParameter);

  /** find the data objects */
  <S extends CabFindDetails> SearchResult<CabData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<CabDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<CabDetails>> getDetailsListFromData(ParameterContext<Collection<CabData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<CabData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<CabData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getCarType(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getDriver(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<CarTypeDetails> getCarTypeDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<DriverDetails> getDriverDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setCarType(CabData cabData, ParameterContext<DataRef> dataRefParameter);
  void setDriver(CabData cabData, ParameterContext<DataRef> dataRefParameter);
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
