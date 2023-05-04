package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

// anchor:base-imports:start
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import static net.democritus.sys.NullDataRef.EMPTY_DATA_REF;

import net.democritus.sys.Context;
import net.democritus.sys.DataRef;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.ElementRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.search.SearchDetails;

import net.democritus.projection.IDataElementProjector;
import net.democritus.projection.IDataProjectorRequired;

import net.democritus.projection.DataElementProjectorManager;
import net.democritus.projection.InvalidProjectionException;

import static net.palver.util.Options.none;
import static net.palver.util.Options.some;
import net.palver.util.Options.Option;
import net.palver.util.StringUtil;

import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.DiagnosticFieldFactory;
import net.democritus.sys.diagnostics.DiagnosticHelper;
import static net.democritus.sys.DiagnosticConstants.*;

import javax.persistence.Query;
import javax.persistence.TemporalType;
// anchor:base-imports:end

// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.ProfileData;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.ProfileCrudsInternal;
import net.democritus.usr.UserData;
import net.democritus.usr.UserDetails;
import net.democritus.usr.UserCrudsInternal;
import net.democritus.usr.UserGroupData;
import net.democritus.usr.UserGroupDetails;
import net.democritus.usr.UserGroupCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless
// @anchor:annotations:start
@Local({DataAccessCrudsLocal.class, DataAccessCrudsInternal.class})
// @anchor:annotations:end
public class DataAccessCruds /*@anchor:interfaces:start@*/implements DataAccessCrudsLocal, DataAccessCrudsInternal/*@anchor:interfaces:end@*/ {

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("account", "DataAccess");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  public static final String DISABLED_ERROR_MSG_KEY = "account.dataAccess.alreadyDisabled";

  @PersistenceContext(unitName="OnlineCabBooking_account")
  private EntityManager entityManager;

  @Resource
  private SessionContext sessionContext;

  private DataAccessFinderLocal dataAccessFinder;

  /* linked bean variables */
  // anchor:link-variables:start
  @EJB
  private ProfileCrudsInternal profileLocal;
  @EJB
  private UserCrudsInternal userLocal;
  @EJB
  private UserGroupCrudsInternal userGroupLocal;
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DataAccessCruds.class);
  // @anchor:variables:end

  /* custom variables */
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private DataAccessDataRefProjector dataRefProjector;
  private DataAccessDetailsProjector detailsProjector;
  private DataAccessInfoProjector infoProjector;

  private DataElementProjectorManager<DataAccessData> elementProjectorManager;


  @PostConstruct
  private void init() {
    initFinder();
    initProjectorMapping();
    // @anchor:initialization:start
    // @anchor:initialization:end

    // anchor:custom-initialization:start
    // anchor:custom-initialization:end
  }

  private void initProjectorMapping() {
    DataAccessCrudsInternal dataAccessCrudsInternal = sessionContext.getBusinessObject(DataAccessCrudsInternal.class);

    detailsProjector = new DataAccessDetailsProjector(dataAccessCrudsInternal);
    infoProjector = new DataAccessInfoProjector(dataAccessCrudsInternal);
    dataRefProjector = new DataAccessDataRefProjector(dataAccessCrudsInternal);

    elementProjectorManager = new DataElementProjectorManager<DataAccessData>();
    elementProjectorManager.addProjector(detailsProjector);
    elementProjectorManager.addProjector(infoProjector);
    elementProjectorManager.addProjector(dataRefProjector);

    // anchor:additional-projectors:start
    elementProjectorManager.addProjector(new DataAccessQueryProjector(dataAccessCrudsInternal));
    // anchor:additional-projectors:end

    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  private void initFinder() {
    dataAccessFinder = new DataAccessFinderBean(entityManager);
  }

  // anchor:create:start
  public CrudsResult<DataRef> create(ParameterContext<DataAccessDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    DataAccessDetails details = detailsParameter.getValue();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    DataAccessData dataAccessData = new DataAccessData();
    try {

      // @anchor:create-beforeProjection:start
      // @anchor:create-beforeProjection:end

      // anchor:custom-create-beforeProjection:start
      // anchor:custom-create-beforeProjection:end

      detailsProjector.toData(dataAccessData, detailsParameter);

      // @anchor:create-afterProjection:start
      dataAccessData.setDisabled("no");
      UserContext userContext = context.getContext(UserContext.class).orElse((UserContext) null);
      if (userContext == null) {
        if (logger.isInfoEnabled()) {
          logger.info(
              "No user specified in UserContext in DataAccessCruds::create()"
          );
        }
      }
      Date timestamp = new Date();
      DataRef userRef = getUserRef(userContext);
      ParameterContext<DataRef> userRefParameter = context.withParameter(userRef);

      markCreation(dataAccessData, timestamp, userRefParameter);
      markModification(dataAccessData, timestamp, userRefParameter);
      // @anchor:create-afterProjection:end

      // anchor:custom-create-afterProjection:start
      // anchor:custom-create-afterProjection:end

    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot fill data object", e
        );
      }

      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    // @anchor:implicitNameFieldOnly-beforePersist:start
    String identity = details.getName();
    if (identity != null) {
      identity = identity.trim();
      identity = identity.length() > 0 ? identity : null;
    }
    // @anchor:implicitNameFieldOnly-beforePersist:end

    try {
      entityManager.persist(dataAccessData);

      // @anchor:implicitNameFieldOnly-afterPersist:start
      if (identity == null) {
        identity = "D-" + dataAccessData.getId().toString();
      }
      dataAccessData.setName(identity);
      entityManager.flush();
      // @anchor:implicitNameFieldOnly-afterPersist:end

      if (dataAccessData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Created DataAccessCruds with { id : " + dataAccessData.getId() + ", name: " + dataAccessData.getName() + " }"
          );
        }
      }
    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot perform entry creation", e
        );
      }
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(dataAccessData));

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // anchor:create:end

  // anchor:modify:start
  public CrudsResult<DataRef> modify(ParameterContext<DataAccessDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    DataAccessDetails dataAccessDetails = detailsParameter.getValue();
    DataRef dataRef = dataAccessDetails.getDataRef();

    // @anchor:preModify:start
    // @anchor:preModify:end
    // anchor:custom-preModify:start
    // anchor:custom-preModify:end
    // @anchor:preModify-validation:start
    if (dataAccessDetails.getDisabled() == null || dataAccessDetails.getDisabled().equals("")) {
      dataAccessDetails.setDisabled("no");
    }
    detailsParameter = detailsParameter.construct(dataAccessDetails);
    // @anchor:preModify-validation:end

    try {
      CrudsResult<DataAccessData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      DataAccessData dataAccessData = dataResult.getValue();

      // @anchor:modify-beforeProjection:start
      if ("yes".equals(dataAccessData.getDisabled())) {
        return getDiagnosticHelper().createCrudsError(DISABLED_ERROR_MSG_KEY);
      }
      // @anchor:modify-beforeProjection:end
      // anchor:custom-modify-beforeProjection:start
      // anchor:custom-modify-beforeProjection:end

      detailsProjector.toData(dataAccessData, detailsParameter);

      // @anchor:modify-afterProjection:start
      UserContext userContext = context.getContext(UserContext.class).orElse((UserContext) null);
      if (userContext == null) {
        if (logger.isInfoEnabled()) {
          logger.info(
              "No user specified in UserContext in DataAccessCruds::modifyWithProjection()"
          );
        }
      }
      DataRef userRef = getUserRef(userContext);
      Date timestamp = new Date();

      markModification(dataAccessData, timestamp, context.withParameter(userRef));
      // @anchor:modify-afterProjection:end
      // anchor:custom-modify-afterProjection:start
      // anchor:custom-modify-afterProjection:end

      entityManager.flush();
      CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(dataAccessData));

      if (dataAccessData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Modified DataAccessCruds with { id : " + dataAccessData.getId() + ", name: " + dataAccessData.getName() + " }"
          );
        }
      }

      // @anchor:postModify:start
      // @anchor:postModify:end
      // anchor:custom-postModify:start
      // anchor:custom-postModify:end

      return result;

    // @anchor:modify-exceptions:start
    // @anchor:modify-exceptions:end
    } catch(Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "DataAccessCruds.modify() failed with ID = " + dataRef.getId(), e
        );
      }
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }
  }
  // anchor:modify:end

  // anchor:createOrModify:start
  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    P projection = projectionParameter.getValue();
    net.democritus.sys.Context context = projectionParameter.getContext();
    CrudsResult<DataRef> result;

    // @anchor:preCreateOrModify:start
    // @anchor:preCreateOrModify:end
    // anchor:custom-preCreateOrModify:start
    // anchor:custom-preCreateOrModify:end

    if (projection instanceof DataAccessDetails) {
      DataAccessDetails details = (DataAccessDetails) projection;
      DataRef dataRef = details.getDataRef();
      CrudsResult<DataRef> dataRefResult = resolveDataRef(context.<DataRef>withParameter(dataRef));
      if (dataRefResult.isSuccess() && DataRefValidation.isDataRefDefined(dataRefResult.getValue())) {
        result = modify(context.withParameter(details));
      } else {
        result = create(context.withParameter(details));
      }
    } else {
      result = diagnosticHelper.createCrudsError("projection not supported");
      // @anchor:createOrModify-projection:start
      // @anchor:createOrModify-projection:end
      // anchor:custom-createOrModify-projection:start
      // anchor:custom-createOrModify-projection:end
    }

    if (result.isError()) {
      return result;
    }

    // @anchor:postCreateOrModify:start
    // @anchor:postCreateOrModify:end
    // anchor:custom-postCreateOrModify:start
    // anchor:custom-postCreateOrModify:end

    return result;
  }
  // anchor:createOrModify:end

  // anchor:delete:start
  public CrudsResult<Void> delete(ParameterContext<DataRef> dataRefParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }

    Context context = dataRefParameter.getContext();
    DataRef dataRef = dataRefParameter.getValue();

    // @anchor:preDelete-fetch:start
    // @anchor:preDelete-fetch:end
    // anchor:custom-preDelete-fetch:start
    // anchor:custom-preDelete-fetch:end

    CrudsResult<DataRef> dataRefResult = resolveDataRef(dataRefParameter);
    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    dataRef = dataRefResult.getValue();

    // @anchor:preDelete:start
    boolean deletePermanent = false;
    // @anchor:preDelete:end
    // anchor:custom-preDelete:start
    // anchor:custom-preDelete:end

    // @anchor:preDelete-validation:start
    // @anchor:preDelete-validation:end

    try {
      CrudsResult<DataAccessData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      DataAccessData dataAccessData = dataResult.getValue();

      // anchor:custom-delete:start
      // anchor:custom-delete:end
      // @anchor:delete:start
      if (deletePermanent) {
        entityManager.remove(dataAccessData);
      } else {
        dataAccessData.setDisabled("yes");
        entityManager.flush();
      }
      // @anchor:delete:end

      // @anchor:postDelete:start
      // @anchor:postDelete:end
      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      if (dataAccessData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Deleted DataAccessCruds with { id : " + dataAccessData.getId() + ", name: " + dataAccessData.getName() + " }"
          );
        }
      }

      return CrudsResult.success(null);
    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }
  }
  // anchor:delete:end

  // anchor:findMethods:start
  public CrudsResult<DataRef> getDataRefFromData(ParameterContext<DataAccessData> dataParameter) {
    DataRef dataRef = dataRefProjector.project(dataParameter);
    return CrudsResult.success(dataRef);
  }

  public CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();
    if (id == null || id == 0L) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    try {
      DataAccessData dataAccessData = entityManager.find(DataAccessData.class, id);
      // @anchor:getDataRefFromId-afterFetch:start
      // @anchor:getDataRefFromId-afterFetch:end
      // anchor:custom-getDataRefFromId-afterFetch:start
      // anchor:custom-getDataRefFromId-afterFetch:end
      return getDataRefFromData(idParameter.construct(dataAccessData));
    } catch (Exception e) {
      sessionContext.setRollbackOnly();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Get DataRef from ID failed due to unexpected Exception", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }

  private CrudsResult<DataAccessData> getDataById(ParameterContext<Long> idParameter) {
    DataAccessData data = entityManager.find(DataAccessData.class, idParameter.getValue());
    if (data == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
    return CrudsResult.success(data);
  }

  public CrudsResult<DataRef> getDataRefFromName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    CrudsResult<DataAccessData> dataResult = getDataByName(nameParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }

    // @anchor:getDataRefFromName-afterFetch:start
    // @anchor:getDataRefFromName-afterFetch:end
    // anchor:custom-getDataRefFromName-afterFetch:start
    // anchor:custom-getDataRefFromName-afterFetch:end
    return getDataRefFromData(nameParameter.construct(dataResult.getValue()));
  }

  private CrudsResult<DataAccessData> getDataByName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    try {
      DataAccessFindByNameEqDetails findByNameEqDetails = new DataAccessFindByNameEqDetails();
      findByNameEqDetails.setName(name);

      SearchDetails<DataAccessFindByNameEqDetails> searchDetails =
        new SearchDetails<>(findByNameEqDetails);

      ParameterContext<SearchDetails<DataAccessFindByNameEqDetails>> searchParameter =
        nameParameter.construct(searchDetails);

      SearchResult<DataAccessData> searchResult = findData(searchParameter);

      if (searchResult.isError()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      List<DataAccessData> list = searchResult.getResults();
      if (list.isEmpty()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      return CrudsResult.success(list.get(0));

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot find object id " + name, e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }

  public CrudsResult<DataAccessData> getData(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    Long id = dataRef.getId();
    CrudsResult<DataAccessData> result;
    if (id != null && id.longValue() != 0L) {
      result = getDataById(dataRefParameter.construct(id));
    } else {
      result = getDataFromStrategy(dataRefParameter);
    }
    // @anchor:getData-after:start
    // @anchor:getData-after:end
    // anchor:custom-getData-after:start
    // anchor:custom-getData-after:end
    return result;
  }

  private CrudsResult<DataAccessData> getDataFromStrategy(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    Long id = dataRef.getId();
    CrudsResult<DataAccessData> result = getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    // @anchor:getData-strategy:start
    // @anchor:getData-strategy:end
    return getDataByName(dataRefParameter.construct(dataRef.getName()));
  }
  // anchor:findMethods:end

  // anchor:searchMethods:start
  /* searching */

  public <S extends DataAccessFindDetails> SearchResult<DataAccessData> findData(ParameterContext<SearchDetails<S>> searchParameter) {
    return dataAccessFinder.find(searchParameter);
  }

  public <S extends DataAccessFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<DataAccessData> dataResult = findData(searchParameter);

    if (dataResult.isError()) {
      return SearchResult.error(dataResult.getDiagnostics());
    }

    String projection = searchParameter.getValue().getProjection();
    IDataElementProjector<DataAccessData, T> projector = getElementProjector(projection);

    List<T> projected = project(searchParameter.getContext(), dataResult.getResults(), projector);
    return SearchResult.success(projected, dataResult.getTotalNumberOfItems());
  }

  /**
   * Internal method, do not use
   */
  private <T> List<T> project(Context context, List<DataAccessData> fromItems, IDataElementProjector<DataAccessData, T> projector) {
    return fromItems.stream()
        .map(context::withParameter)
        .map(projector::project)
        .collect(Collectors.toList());
  }

  /**
   * Internal method, do not use
   */
  @Deprecated
  private <T> List<T> project(UserContext userContext, List<DataAccessData> fromItems, IDataElementProjector<DataAccessData, T> projector) {
    return project(Context.from(userContext), fromItems, projector);
  }

  @Override
  public <P> SearchResult<P> project(ParameterContext<List<DataAccessData>> listParameter, String projection) {
    IDataElementProjector<DataAccessData, P> elementProjector = getElementProjector(projection);
    List<DataAccessData> list = listParameter.getValue();
    List<P> resultList = project(listParameter.getContext(), list, elementProjector);

    return SearchResult.success(resultList);
  }

  // anchor:searchMethods:end

  // anchor:projectors:start
  // anchor:projectors:end

  // anchor:link-getters:start
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getForProfile(ParameterContext<Long> idParameter) {
    try {
      return profileLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getForUser(ParameterContext<Long> idParameter) {
    try {
      return userLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<List<DataRef>> getUserGroups(ParameterContext<DataAccessData> dataAccessDataParameter) {
    DataAccessData dataAccessData = dataAccessDataParameter.getValue();
    try {
      List<DataRef> dataRefVec = new ArrayList<DataRef>();
      Collection<UserGroupData> userGroupDatas = dataAccessData.getUserGroups();

      if (userGroupDatas != null) {
        for (UserGroupData data: userGroupDatas) {
          ParameterContext<UserGroupData> dataParameter = dataAccessDataParameter.construct(data);
          CrudsResult<DataRef> result = userGroupLocal.getDataRefFromData(dataParameter);
          if (result.isSuccess()) {
            dataRefVec.add(result.getValue());
          } else {
            return result.convertError();
          }
        }
      }

      return CrudsResult.success(dataRefVec);

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link vector", e
        );
      }
      return getDiagnosticHelper().createCrudsError("cannot retrieve links in getUserGroups");
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getForUserGroup(ParameterContext<Long> idParameter) {
    try {
      return userGroupLocal.getDataRefFromId(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link", e
        );
      }
      return CrudsResult.success(EMPTY_DATA_REF);
    }
  }

  public CrudsResult<ProfileDetails> getForProfileDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return profileLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<UserDetails> getForUserDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return userLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<List<UserGroupDetails>> getUserGroupsDetails(ParameterContext<DataAccessData> dataParameter) {
    DataAccessData dataAccessData = dataParameter.getValue();
    List<UserGroupDetails> detailsVec = new ArrayList<UserGroupDetails>();

    try {
      CrudsResult<List<UserGroupDetails>> detailsListResult =
        userGroupLocal.getDetailsListFromData(dataParameter.construct(dataAccessData.getUserGroups()));
      detailsVec.addAll(detailsListResult.getValue());

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link vector", e
        );
      }
    }
    return CrudsResult.success(detailsVec);
  }

  public CrudsResult<UserGroupDetails> getForUserGroupDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return userGroupLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  // anchor:link-getters:end

  // anchor:link-setters:start
  @Override
  public void setForProfile(DataAccessData dataAccessData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      dataAccessData.setForProfile(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = profileLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      dataAccessData.setForProfile(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Profile instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setForUser(DataAccessData dataAccessData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      dataAccessData.setForUser(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = userLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      dataAccessData.setForUser(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find User instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setUserGroups(DataAccessData dataAccessData, ParameterContext<List<DataRef>> dataRefListParameter) {
    List<DataRef> aUserGroupsVec = dataRefListParameter.getValue();
    ArrayList<UserGroupData> aArrayList = new ArrayList<UserGroupData>();
    for (DataRef dataRef : aUserGroupsVec) {
      CrudsResult<UserGroupData> result = userGroupLocal.getData(dataRefListParameter.construct(dataRef));
      if (result.isSuccess()) {
        aArrayList.add(result.getValue());
      } else {
        throw new IllegalStateException("Failed to find UserGroup matching " + dataRef);
      }
    }
    dataAccessData.setUserGroups(aArrayList);
  }
  @Override
  public void setForUserGroup(DataAccessData dataAccessData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      dataAccessData.setForUserGroup(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = userGroupLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      dataAccessData.setForUserGroup(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find UserGroup instance '" + dataRefParameter.getValue() + "'");
    }
  }

  // anchor:link-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-method-wrappers:start
  // anchor:calculation-method-wrappers:end

  // anchor:custom-projections:start
  // anchor:custom-projections:end

  // anchor:projectMethods:start
  private <P> IDataElementProjector<DataAccessData, P> getElementProjector(P projection) {
    @SuppressWarnings("unchecked")
    Class<P> projectionClass = (Class<P>) projection.getClass();
    return elementProjectorManager.getProjector(projectionClass);
  }

  @Override
  public String getDisplayName(ParameterContext<DataAccessData> dataParameter) {
    Option<String> optCustomDisplayName = getCustomDisplayName(dataParameter);
    if (optCustomDisplayName.isDefined()) {
      return optCustomDisplayName.getValue();
    }
    DataAccessData data = dataParameter.getValue();
    String displayName;

    displayName = data.getName();

    return displayName;
  }

  private Option<String> getCustomDisplayName(ParameterContext<DataAccessData> dataParameter) {
    Option<String> result = none();

    /* return some(string), to override default displayName */
    // anchor:custom-displayName:start
    // anchor:custom-displayName:end
    return result;
  }

  private String nameFromDataRef(CrudsResult<DataRef> result) {
    DataRef value = result.getValue();
    return value == null ? "-" : value.getName();
  }

  private String nameFromValue(Object value) {
    return value == null ? "-" : value.toString();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <T> IDataElementProjector<DataAccessData, T> getElementProjector(String projectionName) {
    IDataElementProjector<DataAccessData, T> projector = elementProjectorManager.getProjector(projectionName);
    if (projector == null) {
      throw new InvalidProjectionException(new ElementRef("account", "DataAccess"), projectionName);
    }
    return projector;
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    ProjectionRef projectionRef = projectionRefParameter.getValue();

    DataRef dataRef = projectionRef.getDataRef();
    CrudsResult<DataAccessData> dataResult = getData(projectionRefParameter.construct(dataRef));
    DataAccessData dataAccessData;
    if (dataResult.isSuccess()) {
      dataAccessData = dataResult.getValue();
    } else {
      dataAccessData = new DataAccessData();
    }

    // anchor:custom-preProject:start
    // anchor:custom-preProject:end

    IDataElementProjector<DataAccessData, T> projector = getElementProjector(projectionRef.getProjection());
    T projection = projector.project(projectionRefParameter.construct(dataAccessData));

    if (dataAccessData != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Retrieved DataAccessCruds:" + projectionRef.getProjection() + " { id : " + dataAccessData.getId() + ", name: " + dataAccessData.getName() + " }"
        );
      }
    }

    return CrudsResult.success(projection);
  }

  public CrudsResult<DataAccessDetails> getDetails(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<DataAccessData> result = getData(dataRefParameter);
    if (result.isError()) {
      return result.convertError();
    }

    DataAccessData dataAccessData = result.getValue();
    DataAccessDetails dataAccessDetails = detailsProjector.project(dataRefParameter.construct(dataAccessData));

    return CrudsResult.success(dataAccessDetails);
  }

  public CrudsResult<List<DataAccessDetails>> getDetailsListFromData(ParameterContext<Collection<DataAccessData>> dataListParameter) {
    Collection<DataAccessData> dataList = dataListParameter.getValue();
    List<DataAccessDetails> list = new ArrayList<>(dataList.size());

    for (DataAccessData dataAccessData: dataList) {
      DataAccessDetails dataAccessDetails = detailsProjector.project(dataListParameter.construct(dataAccessData));
      list.add(dataAccessDetails);
    }

    return CrudsResult.success(list);
  }

  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return diagnosticHelper.createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
    if (dataRef.getId() != null && dataRef.getId() != 0L) {
      return CrudsResult.success(dataRef);
    }
    CrudsResult<DataAccessData> dataResult = getData(dataRefParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }
    DataAccessData data = dataResult.getValue();
    return getDataRefFromData(dataRefParameter.construct(data));
  }

  // anchor:projectMethods:end

  // @anchor:methods:start
  private void markCreation(DataAccessData dataAccessData, Date timestamp, ParameterContext<DataRef> userRefParameter) {
    dataAccessData.setEnteredAt(timestamp);
  }

  private void markModification(DataAccessData dataAccessData, Date timestamp, ParameterContext<DataRef> userRefParameter) {
    dataAccessData.setLastModifiedAt(timestamp);
  }

  private DataRef getUserRef(UserContext userContext) {
    DataRef userRef;
    if (userContext != null) {
      userRef = DataRef.withIdAndName(userContext.getId(), userContext.getUserName());
    } else {
      userRef = DataRef.withId(0L);
    }

    return userRef;
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

  /* utility methods */

  private DiagnosticHelper getDiagnosticHelper() {
    return diagnosticHelper;
  }

  private DiagnosticFactory getDiagnosticFactory() {
    return diagnosticFactory;
  }

  @Override
  public DataRef idToDataRef(Long id) {
    return new DataRef(
      id,
      "[no name]",
      "account",
      "net.democritus.acl",
      "DataAccess"
    );
  }
}
