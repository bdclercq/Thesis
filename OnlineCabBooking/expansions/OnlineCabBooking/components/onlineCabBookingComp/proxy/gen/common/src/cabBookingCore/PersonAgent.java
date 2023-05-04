package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.ArrayList;
import java.util.List;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;


import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;

import net.democritus.sys.search.SearchDetails;
import net.democritus.support.Paging;
// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import cabBookingCore.AddressAgent;
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class PersonAgent implements PersonAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(PersonAgent.class);

  private static final PersonProxy personProxy = PersonProxy.getPersonProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private PersonAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static PersonAgent getPersonAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new PersonAgent(context, userContext);
  }

  @Deprecated
  public static PersonAgent getPersonAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new PersonAgent(context, userContext);
  }

  @Deprecated
  public static PersonAgent getPersonAgent() {
    return getPersonAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    PersonFindAllPersonsDetails finder = new PersonFindAllPersonsDetails();
    return personProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<PersonInfo> findAllInfos() {
    PersonFindAllPersonsDetails finder = new PersonFindAllPersonsDetails();
    return personProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long personId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(personId));
    CrudsResult<DataRef> result = personProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public PersonInfo getInfo(Long personId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(personId));
    CrudsResult<PersonInfo> result = personProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new PersonInfo();
    }
  }

  public PersonDetails getDetails_old(Long personId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(personId));
    CrudsResult<PersonDetails> result = personProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new PersonDetails();
    }
  }

  public CrudsResult<PersonDetails> getDetails(Long personId) {
    return personProxy.getDetails(createParameter(personId));
  }

  public CrudsResult<PersonDetails> getDetails(DataRef dataRef) {
    return personProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return personProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return personProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long personId) {
    CrudsResult<String> result = personProxy.getName(createParameter(personId));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      if (logger.isErrorEnabled()) {
          logger.error(
            "getName() failed"
          );
      }
      return "";
    }
  }

  public Long getId(String personName) {
    CrudsResult<DataRef> result = personProxy.getId(createParameter(personName));
    if (result.isSuccess()) {
      return result.getValue().getId();
    } else {
      if (logger.isErrorEnabled()) {
          logger.error(
            "getId() failed"
          );
      }
      return null;
    }
  }

  public CrudsResult<DataRef> getDataRef(String name) {
    return personProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Person instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(PersonDetails details) {
    return personProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Person instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(PersonDetails details) {
    return personProxy.modify(createParameter(details));
  }

  /**
   * Create a Person instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return personProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Person instance by id.
   *
   * @deprecated Use {@link PersonAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return personProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Person instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return personProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Person element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends PersonFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return personProxy.find(createParameter(searchDetails));
  }

  /*========== Context ==========*/

  private <T> ParameterContext<T> createParameter(T value) {
    return mContext.withParameter(value);
  }

  private <T> ParameterContext<Void> createEmptyParameter() {
    return mContext.emptyParameter();
  }

  private UserContext getUserContext() {
    return mUserContext;
  }

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

