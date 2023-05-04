package net.democritus.acl;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.naming.Context;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NamedQueries;
import javax.persistence.Query;

import net.democritus.support.ejb3.SearchDataRefToSearchDetailsMapper;

import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.DiagnosticReason;
import net.democritus.sys.DiagnosticFactory;
import net.democritus.sys.DataRefValidation;
import net.democritus.sys.diagnostics.DiagnosticHelper;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.IParameterContextFactory;
import net.democritus.sys.search.SearchDetails;
import net.democritus.projection.IDataElementProjector;

import static net.democritus.sys.DiagnosticConstants.*;
import static net.palver.util.Options.Option;



// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.acl.DataAccessCsvImporterImpl;
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.ProfileLocal;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.UserLocal;
import net.democritus.usr.UserDetails;
import net.democritus.usr.UserGroupLocal;
import net.democritus.usr.UserGroupDetails;
// anchor:imports:end

// anchor:valuetype-imports:start
import java.util.Date;

// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(DataAccessRemote.class)
@Local(DataAccessLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class DataAccessBean /*@anchor:interfaces:start@*/implements DataAccessRemote, DataAccessLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("account", "DataAccess");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  @EJB private ProfileLocal profileLocal;

  @EJB private UserLocal userLocal;

  @EJB private UserGroupLocal userGroupLocal;

  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(DataAccessBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_account")
  private EntityManager entityManager;

  @EJB
  DataAccessCrudsLocal dataAccessCrudsLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
    private static final String NO_USER_ID = "account.dataAccess.noUserId";
    private static final String NO_PROFILE_FOR_USER = "account.dataAccess.noProfileForUser";
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<DataAccessDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    DataAccessDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = dataAccessCrudsLocal.create(context.withParameter(details));

    if (result.isError()) {
      return result;
    }

    // @anchor:postCreate:start
    // @anchor:postCreate:end

    // anchor:custom-postCreate:start
    // anchor:custom-postCreate:end
    return result;
  }

  // @anchor:modify-annotations:start
  // @anchor:modify-annotations:end
  // anchor:custom-modify-annotations:start
  // anchor:custom-modify-annotations:end
  public CrudsResult<DataRef> modify(ParameterContext<DataAccessDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    DataAccessDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = dataAccessCrudsLocal.modify(context.withParameter(details));
    if (result.isError()) {
      return result;
    }

    // @anchor:postModify:start
    // @anchor:postModify:end

    // anchor:custom-postModify:start
    // anchor:custom-postModify:end

    return result;
  }

  // @anchor:createOrModify-annotations:start
  // @anchor:createOrModify-annotations:end
  // anchor:custom-createOrModify-annotations:start
  // anchor:custom-createOrModify-annotations:end
  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    P projection = projectionParameter.getValue();
    net.democritus.sys.Context context = projectionParameter.getContext();

    // @anchor:preCreateOrModify:start
    // @anchor:preCreateOrModify:end
    // anchor:custom-preCreateOrModify:start
    // anchor:custom-preCreateOrModify:end

    // @anchor:preCreateOrModify-validation:start
    // @anchor:preCreateOrModify-validation:end

    CrudsResult<DataRef> result = dataAccessCrudsLocal.createOrModify(projectionParameter);
    if (result.isError()) {
      return result;
    }

    // @anchor:postCreateOrModify:start
    // @anchor:postCreateOrModify:end
    // anchor:custom-postCreateOrModify:start
    // anchor:custom-postCreateOrModify:end

    return result;
  }

  // @anchor:delete-annotations:start
  // @anchor:delete-annotations:end
  // anchor:custom-delete-annotations:start
  // anchor:custom-delete-annotations:end
  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }

    net.democritus.sys.Context context = idParameter.getContext();
    Long id = idParameter.getValue();

    // @anchor:preDelete:start
    // @anchor:preDelete:end

    // anchor:custom-preDelete:start
    // anchor:custom-preDelete:end

    try {
      // @anchor:preDelete-tryBlock:start
      // @anchor:preDelete-tryBlock:end

      DataRef dataRef = idToDataRef(idParameter.getValue());
      CrudsResult<Void> result = dataAccessCrudsLocal.delete(context.withParameter(dataRef));

      if (result.isError()) {
          return result;
      }

      // @anchor:postDelete:start
      // @anchor:postDelete:end

      // anchor:custom-postDelete:start
      // anchor:custom-postDelete:end

      return CrudsResult.success(null);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Delete failed for ID = " + id, e
        );
      }
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }
  }

  // @anchor:deleteByDataRef-annotations:start
  // @anchor:deleteByDataRef-annotations:end
  // anchor:custom-deleteByDataRef-annotations:start
  // anchor:custom-deleteByDataRef-annotations:end
  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(DELETE_ERROR_MSG_KEY);
    }
    CrudsResult<DataRef> dataRefResult = resolveDataRef(dataRefParameter);
    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return delete(dataRefParameter.construct(dataRefResult.getValue().getId()));
  }
  // anchor:crudsMethods:end

  /*========== Business logic methods ==========*/

  // anchor:businessLogic:start
  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    return dataAccessCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = dataAccessCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return dataAccessCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public DataAccessInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public DataAccessDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public DataAccessDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return dataAccessCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return dataAccessCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataAccessInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataAccessDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<DataAccessDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataAccessDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
    DataRef dataRef = dataRefParameter.getValue();
    ProjectionRef projectionRef = new ProjectionRef("details", dataRef);
    return getProjection(dataRefParameter.construct(projectionRef));
  }

  // anchor:businessLogic:end

  /*========== findBy methods ==========*/

  // anchor:findBy:start
  // anchor:custom-projections:start
  // anchor:custom-projections:end

  private Map<String, IDataElementProjector> elementProjectorMap = new HashMap<>();
  {
    /* no longer used */
    // anchor:custom-projection-mapping:start
    // anchor:custom-projection-mapping:end
  }

  // anchor:custom-getProjection-annotations:start
  // anchor:custom-getProjection-annotations:end
  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    CrudsResult<T> result;

    ProjectionRef projectionRef = projectionRefParameter.getValue();
    DataRef dataRef = projectionRef.getDataRef();

    // @anchor:before-getProjection:start
    // @anchor:before-getProjection:end

    // anchor:custom-before-getProjection:start
    // anchor:custom-before-getProjection:end

    result = dataAccessCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends DataAccessFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    result = dataAccessCrudsLocal.find(searchParameter);

    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return result;
  }

  // anchor:findBy:end

  /*========== Command implementations ==========*/

  // anchor:command-implementations:start
  // anchor:command-implementations:end

  /*========== Import/Export ==========*/

  // anchor:import-export:start
  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public ImportResult importFile(ParameterContext<ImportFile> parameter) {
    String type = parameter.getValue().getType();
    ImportResult importResult;
    if (false) {
    // @anchor:import-implementations:start
    } else if ("csv".equals(type)) {
      importResult = new DataAccessCsvImporterImpl().importFile(parameter);
    // @anchor:import-implementations:end
    // anchor:custom-import-implementations:start
    // anchor:custom-import-implementations:end
    } else {
      // @anchor:import-unsupported:start
      // @anchor:import-unsupported:end
      // anchor:custom-import-unsupported:start
      // anchor:custom-import-unsupported:end
      return ImportResult.globalError("Unsupported import type (" + type + ")");
    }
    // @anchor:post-import:start
    // @anchor:post-import:end
    // anchor:custom-post-import:start
    // anchor:custom-post-import:end
    return importResult;
  }
  // anchor:import-export:end

  /*========== utility ==========*/

  private DiagnosticHelper getDiagnosticHelper() {
    return diagnosticHelper;
  }

  private DiagnosticFactory getDiagnosticFactory() {
    return diagnosticFactory;
  }

  private DataRef idToDataRef(Long id) {
    return new DataRef(
        id,
        "[no name]",
        "account",
        "net.democritus.acl",
        "DataAccess"
    );
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  private CrudsResult<ProfileAccessRights> createProfileAccessRights(UserContext userContext, DataRef profileRef, ProfileAccessQuery accessQuery) {
    DataAccessFindByForProfileEq_ElementEqDetails details = new DataAccessFindByForProfileEq_ElementEqDetails();
    details.setElement(accessQuery.getElement());
    details.setForProfile(profileRef);

    SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails> searchParameter = new SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails>(details);
    ParameterContext<SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails>> parameterContext = new ParameterContext<SearchDetails<DataAccessFindByForProfileEq_ElementEqDetails>>(userContext, searchParameter);
    SearchResult<DataAccessInfo> searchResult = find(parameterContext);

    if (searchResult.isError()) {
      return getDiagnosticHelper().createCrudsError(searchResult.getDiagnostics().get(0));
    }

    ProfileAccessRights profileAccessRights = new ProfileAccessRights();

    for (DataAccessInfo info : searchResult.getResults()) {
      String functionality = info.getFunctionality();
      String authorized = info.getAuthorized();

      if ("delete".equals(functionality)) {
        profileAccessRights.setCanDelete(isAllowed(authorized));
      } else if ("entry".equals(functionality)) {
        profileAccessRights.setCanEdit(isAllowed(authorized));
      } else if ("status".equals(functionality)) {
        profileAccessRights.setCanView(isAllowed(authorized));
      }
    }

    return CrudsResult.success(profileAccessRights);
  }

  private boolean isAllowed(String authorized) {
    return !"no".equals(authorized);
  }

  public CrudsResult<ProfileAccessRights> getProfileAccessRights(ParameterContext<ProfileAccessQuery> queryParameter) {
    UserContext userContext = queryParameter.getUserContext();

    Long userId = userContext.getId();
    if (userId == null) {
      return getDiagnosticHelper().createCrudsError(NO_USER_ID);
    }

    CrudsResult<UserDetails> userDetails = userLocal.getDetails(queryParameter.construct(userId));
    DataRef profileRef = userDetails.getValue().getProfile();
    if (profileRef.getId() == 0) {
      return getDiagnosticHelper().createCrudsError(NO_PROFILE_FOR_USER);
    }

    CrudsResult<ProfileAccessRights> result = createProfileAccessRights(userContext, profileRef, queryParameter.getValue());

    return result;
  }
  // anchor:custom-methods:end

}
