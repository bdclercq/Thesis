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
import cabBookingCore.AddressData;
import cabBookingCore.AddressDetails;
import cabBookingCore.AddressCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface PersonCrudsInternal extends PersonCrudsLocal {

  /* get the underlying data object */
  CrudsResult<PersonData> getData(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRefFromData(ParameterContext<PersonData> personDataParameter);

  /** find the data objects */
  <S extends PersonFindDetails> SearchResult<PersonData> findData(ParameterContext<SearchDetails<S>> searchParameter);

  CrudsResult<PersonDetails> getDetails(ParameterContext<DataRef> dataRefParameter);
  CrudsResult<List<PersonDetails>> getDetailsListFromData(ParameterContext<Collection<PersonData>> dataListParameter);

  <P> SearchResult<P> project(ParameterContext<List<PersonData>> listParameter, String projection);

  DataRef idToDataRef(Long id);
  String getDisplayName(ParameterContext<PersonData> dataParameter);

  // anchor:projection-getters:start
  CrudsResult<DataRef> getAddress(ParameterContext<Long> idParameter);
  // anchor:projection-getters:end

  // anchor:projection-detail-getters:start
  CrudsResult<AddressDetails> getAddressDetails(ParameterContext<DataRef> dataRefParameter);
  // anchor:projection-detail-getters:end

  // anchor:projection-setters:start
  void setAddress(PersonData personData, ParameterContext<DataRef> dataRefParameter);
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
