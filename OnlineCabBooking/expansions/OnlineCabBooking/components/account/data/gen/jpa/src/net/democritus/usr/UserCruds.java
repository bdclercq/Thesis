package net.democritus.usr;

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
import net.democritus.usr.AccountData;
import net.democritus.usr.AccountDetails;
import net.democritus.usr.AccountCrudsInternal;
import net.democritus.usr.ProfileData;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.ProfileCrudsInternal;
import net.democritus.usr.UserGroupData;
import net.democritus.usr.UserGroupDetails;
import net.democritus.usr.UserGroupCrudsInternal;
// anchor:imports:end

// anchor:valueType-imports:start
import java.util.Date;
// anchor:valueType-imports:end

// anchor:custom-imports:start
import net.democritus.encrypt.EncryptPassword;
// anchor:custom-imports:end

@Stateless
// @anchor:annotations:start
@Local({UserCrudsLocal.class, UserCrudsInternal.class})
// @anchor:annotations:end
public class UserCruds /*@anchor:interfaces:start@*/implements UserCrudsLocal, UserCrudsInternal/*@anchor:interfaces:end@*/ {

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("account", "User");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  public static final String DISABLED_ERROR_MSG_KEY = "account.user.alreadyDisabled";

  @PersistenceContext(unitName="OnlineCabBooking_account")
  private EntityManager entityManager;

  @Resource
  private SessionContext sessionContext;

  private UserFinderLocal userFinder;

  /* linked bean variables */
  // anchor:link-variables:start
  @EJB
  private AccountCrudsInternal accountLocal;
  @EJB
  private ProfileCrudsInternal profileLocal;
  @EJB
  private UserGroupCrudsInternal userGroupLocal;
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(UserCruds.class);
  // @anchor:variables:end

  /* custom variables */
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private UserDataRefProjector dataRefProjector;
  private UserDetailsProjector detailsProjector;
  private UserInfoProjector infoProjector;

  private DataElementProjectorManager<UserData> elementProjectorManager;


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
    UserCrudsInternal userCrudsInternal = sessionContext.getBusinessObject(UserCrudsInternal.class);

    detailsProjector = new UserDetailsProjector(userCrudsInternal);
    infoProjector = new UserInfoProjector(userCrudsInternal);
    dataRefProjector = new UserDataRefProjector(userCrudsInternal);

    elementProjectorManager = new DataElementProjectorManager<UserData>();
    elementProjectorManager.addProjector(detailsProjector);
    elementProjectorManager.addProjector(infoProjector);
    elementProjectorManager.addProjector(dataRefProjector);

    // anchor:additional-projectors:start
    elementProjectorManager.addProjector(new UserInputProjector(userCrudsInternal));
    elementProjectorManager.addProjector(new UserDetailsWithoutRefsProjector(userCrudsInternal));
    // anchor:additional-projectors:end

    // anchor:custom-projection-mapping:start
    detailsProjector = new UserDetailsWithoutPasswordsProjector(userCrudsInternal);
    elementProjectorManager.addProjector(detailsProjector);
    // anchor:custom-projection-mapping:end
  }

  private void initFinder() {
    userFinder = new UserFinderBean(entityManager);
  }

  // anchor:create:start
  public CrudsResult<DataRef> create(ParameterContext<UserDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    UserDetails details = detailsParameter.getValue();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    String password = details.getPassword();
    String encryptedPassword = details.getEncryptedPassword();

    if (!isValidPassword(password) && !isValidPassword(encryptedPassword)) {
      return getDiagnosticHelper().createCrudsError("account.user.userName.notValid");
    }

    if (isValidPassword(encryptedPassword)) {
      // encrypt password
      details.setEncryptedPassword(EncryptPassword.encrypt(details.getName(), encryptedPassword));
    }
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    UserData userData = new UserData();
    try {

      // @anchor:create-beforeProjection:start
      // @anchor:create-beforeProjection:end

      // anchor:custom-create-beforeProjection:start
      // anchor:custom-create-beforeProjection:end

      detailsProjector.toData(userData, detailsParameter);

      // @anchor:create-afterProjection:start
      userData.setDisabled("no");
      UserContext userContext = context.getContext(UserContext.class).orElse((UserContext) null);
      if (userContext == null) {
        if (logger.isInfoEnabled()) {
          logger.info(
              "No user specified in UserContext in UserCruds::create()"
          );
        }
      }
      Date timestamp = new Date();
      DataRef userRef = getUserRef(userContext);
      ParameterContext<DataRef> userRefParameter = context.withParameter(userRef);

      markCreation(userData, timestamp, userRefParameter);
      markModification(userData, timestamp, userRefParameter);
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
    // @anchor:implicitNameFieldOnly-beforePersist:end

    try {
      entityManager.persist(userData);

      // @anchor:implicitNameFieldOnly-afterPersist:start
      // @anchor:implicitNameFieldOnly-afterPersist:end

      if (userData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Created UserCruds with { id : " + userData.getId() + ", name: " + userData.getName() + " }"
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

    CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(userData));

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // anchor:create:end

  // anchor:modify:start
  public CrudsResult<DataRef> modify(ParameterContext<UserDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    Context context = detailsParameter.getContext();
    UserDetails userDetails = detailsParameter.getValue();
    DataRef dataRef = userDetails.getDataRef();

    // @anchor:preModify:start
    // @anchor:preModify:end
    // anchor:custom-preModify:start
    UserData existingUserData = getData(detailsParameter.construct(userDetails.getDataRef())).getValue();
    String password = userDetails.getPassword();
    String encryptedPassword = userDetails.getEncryptedPassword();

    if (!isValidPassword(password)) {
      // preserve password
      userDetails.setPassword(existingUserData.getPassword());
    }

    if (isValidPassword(encryptedPassword)) {
      // encrypt password
      userDetails.setEncryptedPassword(EncryptPassword.encrypt(userDetails.getName(), encryptedPassword));
    } else {
      // preserve password
      userDetails.setEncryptedPassword(existingUserData.getEncryptedPassword());
    }
    // anchor:custom-preModify:end
    // @anchor:preModify-validation:start
    if (userDetails.getDisabled() == null || userDetails.getDisabled().equals("")) {
      userDetails.setDisabled("no");
    }
    detailsParameter = detailsParameter.construct(userDetails);
    // @anchor:preModify-validation:end

    try {
      CrudsResult<UserData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      UserData userData = dataResult.getValue();

      // @anchor:modify-beforeProjection:start
      if ("yes".equals(userData.getDisabled())) {
        return getDiagnosticHelper().createCrudsError(DISABLED_ERROR_MSG_KEY);
      }
      // @anchor:modify-beforeProjection:end
      // anchor:custom-modify-beforeProjection:start
      // anchor:custom-modify-beforeProjection:end

      detailsProjector.toData(userData, detailsParameter);

      // @anchor:modify-afterProjection:start
      UserContext userContext = context.getContext(UserContext.class).orElse((UserContext) null);
      if (userContext == null) {
        if (logger.isInfoEnabled()) {
          logger.info(
              "No user specified in UserContext in UserCruds::modifyWithProjection()"
          );
        }
      }
      DataRef userRef = getUserRef(userContext);
      Date timestamp = new Date();

      markModification(userData, timestamp, context.withParameter(userRef));
      // @anchor:modify-afterProjection:end
      // anchor:custom-modify-afterProjection:start
      // anchor:custom-modify-afterProjection:end

      entityManager.flush();
      CrudsResult<DataRef> result = getDataRefFromData(detailsParameter.construct(userData));

      if (userData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Modified UserCruds with { id : " + userData.getId() + ", name: " + userData.getName() + " }"
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
            "UserCruds.modify() failed with ID = " + dataRef.getId(), e
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

    if (projection instanceof UserDetails) {
      UserDetails details = (UserDetails) projection;
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
      CrudsResult<UserData> dataResult = getData(context.withParameter(dataRef));
      if (dataResult.isError()) {
        return dataResult.convertError();
      }
      UserData userData = dataResult.getValue();

      // anchor:custom-delete:start
      // anchor:custom-delete:end
      // @anchor:delete:start
      if (deletePermanent) {
        entityManager.remove(userData);
      } else {
        userData.setDisabled("yes");
        entityManager.flush();
      }
      // @anchor:delete:end

      // @anchor:postDelete:start
      // @anchor:postDelete:end
      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      if (userData != null) {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "Deleted UserCruds with { id : " + userData.getId() + ", name: " + userData.getName() + " }"
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
  public CrudsResult<DataRef> getDataRefFromData(ParameterContext<UserData> dataParameter) {
    DataRef dataRef = dataRefProjector.project(dataParameter);
    return CrudsResult.success(dataRef);
  }

  public CrudsResult<DataRef> getDataRefFromId(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();
    if (id == null || id == 0L) {
      return CrudsResult.success(EMPTY_DATA_REF);
    }

    try {
      UserData userData = entityManager.find(UserData.class, id);
      // @anchor:getDataRefFromId-afterFetch:start
      // @anchor:getDataRefFromId-afterFetch:end
      // anchor:custom-getDataRefFromId-afterFetch:start
      // anchor:custom-getDataRefFromId-afterFetch:end
      return getDataRefFromData(idParameter.construct(userData));
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

  private CrudsResult<UserData> getDataById(ParameterContext<Long> idParameter) {
    UserData data = entityManager.find(UserData.class, idParameter.getValue());
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

    CrudsResult<UserData> dataResult = getDataByName(nameParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }

    // @anchor:getDataRefFromName-afterFetch:start
    // @anchor:getDataRefFromName-afterFetch:end
    // anchor:custom-getDataRefFromName-afterFetch:start
    // anchor:custom-getDataRefFromName-afterFetch:end
    return getDataRefFromData(nameParameter.construct(dataResult.getValue()));
  }

  private CrudsResult<UserData> getDataByName(ParameterContext<String> nameParameter) {
    String name = nameParameter.getValue();
    if (name == null || name.trim().equals("")) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    try {
      UserFindByNameEqDetails findByNameEqDetails = new UserFindByNameEqDetails();
      findByNameEqDetails.setName(name);

      SearchDetails<UserFindByNameEqDetails> searchDetails =
        new SearchDetails<>(findByNameEqDetails);

      ParameterContext<SearchDetails<UserFindByNameEqDetails>> searchParameter =
        nameParameter.construct(searchDetails);

      SearchResult<UserData> searchResult = findData(searchParameter);

      if (searchResult.isError()) {
        return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
      }

      List<UserData> list = searchResult.getResults();
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

  public CrudsResult<UserData> getData(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    if (dataRef == null) {
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }

    Long id = dataRef.getId();
    CrudsResult<UserData> result;
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

  private CrudsResult<UserData> getDataFromStrategy(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    Long id = dataRef.getId();
    CrudsResult<UserData> result = getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    // @anchor:getData-strategy:start
    // @anchor:getData-strategy:end
    return getDataByName(dataRefParameter.construct(dataRef.getName()));
  }
  // anchor:findMethods:end

  // anchor:searchMethods:start
  /* searching */

  public <S extends UserFindDetails> SearchResult<UserData> findData(ParameterContext<SearchDetails<S>> searchParameter) {
    return userFinder.find(searchParameter);
  }

  public <S extends UserFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<UserData> dataResult = findData(searchParameter);

    if (dataResult.isError()) {
      return SearchResult.error(dataResult.getDiagnostics());
    }

    String projection = searchParameter.getValue().getProjection();
    IDataElementProjector<UserData, T> projector = getElementProjector(projection);

    List<T> projected = project(searchParameter.getContext(), dataResult.getResults(), projector);
    return SearchResult.success(projected, dataResult.getTotalNumberOfItems());
  }

  /**
   * Internal method, do not use
   */
  private <T> List<T> project(Context context, List<UserData> fromItems, IDataElementProjector<UserData, T> projector) {
    return fromItems.stream()
        .map(context::withParameter)
        .map(projector::project)
        .collect(Collectors.toList());
  }

  /**
   * Internal method, do not use
   */
  @Deprecated
  private <T> List<T> project(UserContext userContext, List<UserData> fromItems, IDataElementProjector<UserData, T> projector) {
    return project(Context.from(userContext), fromItems, projector);
  }

  @Override
  public <P> SearchResult<P> project(ParameterContext<List<UserData>> listParameter, String projection) {
    IDataElementProjector<UserData, P> elementProjector = getElementProjector(projection);
    List<UserData> list = listParameter.getValue();
    List<P> resultList = project(listParameter.getContext(), list, elementProjector);

    return SearchResult.success(resultList);
  }

  // anchor:searchMethods:end

  // anchor:projectors:start
  // anchor:projectors:end

  // anchor:link-getters:start
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> getAccount(ParameterContext<Long> idParameter) {
    try {
      return accountLocal.getDataRefFromId(idParameter);
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
  public CrudsResult<DataRef> getProfile(ParameterContext<Long> idParameter) {
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
  public CrudsResult<List<DataRef>> getUserGroups(ParameterContext<UserData> userDataParameter) {
    UserData userData = userDataParameter.getValue();
    try {
      List<DataRef> dataRefVec = new ArrayList<DataRef>();
      Collection<UserGroupData> userGroupDatas = userData.getUserGroups();

      if (userGroupDatas != null) {
        for (UserGroupData data: userGroupDatas) {
          ParameterContext<UserGroupData> dataParameter = userDataParameter.construct(data);
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

  public CrudsResult<AccountDetails> getAccountDetails(ParameterContext<DataRef> dataRefParameter) {
    try {
      return accountLocal.getDetails(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot dereference specified data bean link for details", e
        );
      }
      return getDiagnosticHelper().createCrudsError(SEARCH_ERROR_MSG_KEY);
    }
  }
  public CrudsResult<ProfileDetails> getProfileDetails(ParameterContext<DataRef> dataRefParameter) {
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
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<List<UserGroupDetails>> getUserGroupsDetails(ParameterContext<UserData> dataParameter) {
    UserData userData = dataParameter.getValue();
    List<UserGroupDetails> detailsVec = new ArrayList<UserGroupDetails>();

    try {
      CrudsResult<List<UserGroupDetails>> detailsListResult =
        userGroupLocal.getDetailsListFromData(dataParameter.construct(userData.getUserGroups()));
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

  // anchor:link-getters:end

  // anchor:link-setters:start
  @Override
  public void setAccount(UserData userData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      userData.setAccount(0L);
      return;
    }
    CrudsResult<DataRef> result;
    try {
      result = accountLocal.resolveDataRef(dataRefParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Cannot get specified data bean and add the link", e
        );
      }
      result = CrudsResult.error();
    }

    if (result.isSuccess()) {
      userData.setAccount(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Account instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setProfile(UserData userData, ParameterContext<DataRef> dataRefParameter) {
    if (!DataRefValidation.isDataRefDefined(dataRefParameter.getValue())) {
      userData.setProfile(0L);
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
      userData.setProfile(result.getValue().getId());
    } else {
      throw new IllegalStateException("Failed to find Profile instance '" + dataRefParameter.getValue() + "'");
    }
  }

  @Override
  public void setUserGroups(UserData userData, ParameterContext<List<DataRef>> dataRefListParameter) {
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
    userData.setUserGroups(aArrayList);
  }
  // anchor:link-setters:end

  // anchor:calculation-methods:start
  // anchor:calculation-methods:end

  // anchor:calculation-method-wrappers:start
  // anchor:calculation-method-wrappers:end

  // anchor:custom-projections:start
  // anchor:custom-projections:end

  // anchor:projectMethods:start
  private <P> IDataElementProjector<UserData, P> getElementProjector(P projection) {
    @SuppressWarnings("unchecked")
    Class<P> projectionClass = (Class<P>) projection.getClass();
    return elementProjectorManager.getProjector(projectionClass);
  }

  @Override
  public String getDisplayName(ParameterContext<UserData> dataParameter) {
    Option<String> optCustomDisplayName = getCustomDisplayName(dataParameter);
    if (optCustomDisplayName.isDefined()) {
      return optCustomDisplayName.getValue();
    }
    UserData data = dataParameter.getValue();
    String displayName;

    displayName = data.getName();

    return displayName;
  }

  private Option<String> getCustomDisplayName(ParameterContext<UserData> dataParameter) {
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
  private <T> IDataElementProjector<UserData, T> getElementProjector(String projectionName) {
    IDataElementProjector<UserData, T> projector = elementProjectorManager.getProjector(projectionName);
    if (projector == null) {
      throw new InvalidProjectionException(new ElementRef("account", "User"), projectionName);
    }
    return projector;
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    ProjectionRef projectionRef = projectionRefParameter.getValue();

    DataRef dataRef = projectionRef.getDataRef();
    CrudsResult<UserData> dataResult = getData(projectionRefParameter.construct(dataRef));
    UserData userData;
    if (dataResult.isSuccess()) {
      userData = dataResult.getValue();
    } else {
      userData = new UserData();
    }

    // anchor:custom-preProject:start
    // anchor:custom-preProject:end

    IDataElementProjector<UserData, T> projector = getElementProjector(projectionRef.getProjection());
    T projection = projector.project(projectionRefParameter.construct(userData));

    if (userData != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "Retrieved UserCruds:" + projectionRef.getProjection() + " { id : " + userData.getId() + ", name: " + userData.getName() + " }"
        );
      }
    }

    return CrudsResult.success(projection);
  }

  public CrudsResult<UserDetails> getDetails(ParameterContext<DataRef> dataRefParameter) {
    CrudsResult<UserData> result = getData(dataRefParameter);
    if (result.isError()) {
      return result.convertError();
    }

    UserData userData = result.getValue();
    UserDetails userDetails = detailsProjector.project(dataRefParameter.construct(userData));

    return CrudsResult.success(userDetails);
  }

  public CrudsResult<List<UserDetails>> getDetailsListFromData(ParameterContext<Collection<UserData>> dataListParameter) {
    Collection<UserData> dataList = dataListParameter.getValue();
    List<UserDetails> list = new ArrayList<>(dataList.size());

    for (UserData userData: dataList) {
      UserDetails userDetails = detailsProjector.project(dataListParameter.construct(userData));
      list.add(userDetails);
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
    CrudsResult<UserData> dataResult = getData(dataRefParameter);
    if (dataResult.isError()) {
      return dataResult.convertError();
    }
    UserData data = dataResult.getValue();
    return getDataRefFromData(dataRefParameter.construct(data));
  }

  // anchor:projectMethods:end

  // @anchor:methods:start
  private void markCreation(UserData userData, Date timestamp, ParameterContext<DataRef> userRefParameter) {
    userData.setEnteredAt(timestamp);
  }

  private void markModification(UserData userData, Date timestamp, ParameterContext<DataRef> userRefParameter) {
    userData.setLastModifiedAt(timestamp);
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
  private boolean isValidPassword(String plainPassword) {
    return plainPassword != null && !plainPassword.trim().isEmpty();
  }

  public CrudsResult<Boolean> checkPassword(ParameterContext<UserInput> parameter) {
    UserInput input = parameter.getValue();
    CrudsResult<UserData> result = getData(parameter.construct(new DataRef(input.getName())));
    if (result.isError()) {
      return result.convertError();
    }

    UserData userData = result.getValue();
    if (userData.getPassword() == null || "".equals(userData.getPassword())) {
      return CrudsResult.success(false);
    }
    boolean isValidPassword = userData.getPassword().equals(input.getPassword());
    return CrudsResult.success(isValidPassword);
  }

  public CrudsResult<Boolean> checkEncryptedPassword(ParameterContext<UserInput> parameter) {
    UserInput input = parameter.getValue();

    CrudsResult<UserData> result = getData(parameter.construct(new DataRef(input.getName())));
    if (result.isError()) {
      return result.convertError();
    }

    UserData userData = result.getValue();
    String inputPwEnc = EncryptPassword.encrypt(userData.getName(), input.getPassword());
    if (userData.getEncryptedPassword() == null || "".equals(userData.getEncryptedPassword())) {
      return CrudsResult.success(false);
    }
    boolean isValidPassword = userData.getEncryptedPassword().equals(inputPwEnc);
    return CrudsResult.success(isValidPassword);
  }
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
      "net.democritus.usr",
      "User"
    );
  }
}
