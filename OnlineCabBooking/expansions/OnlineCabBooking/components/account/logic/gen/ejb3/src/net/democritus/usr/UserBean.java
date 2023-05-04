package net.democritus.usr;

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
import net.democritus.usr.UserCsvImporterImpl;
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.usr.AccountLocal;
import net.democritus.usr.AccountDetails;
import net.democritus.usr.ProfileLocal;
import net.democritus.usr.ProfileDetails;
import net.democritus.usr.UserGroupLocal;
import net.democritus.usr.UserGroupDetails;
// anchor:imports:end

// anchor:valuetype-imports:start
import java.util.Date;

// anchor:valuetype-imports:end

// anchor:custom-imports:start
import net.democritus.encrypt.EncryptPassword;
import net.democritus.sys.ParamTargetValueAgent;
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(UserRemote.class)
@Local(UserLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class UserBean /*@anchor:interfaces:start@*/implements UserRemote, UserLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("account", "User");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  @EJB private AccountLocal accountLocal;

  @EJB private ProfileLocal profileLocal;

  @EJB private UserGroupLocal userGroupLocal;

  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(UserBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_account")
  private EntityManager entityManager;

  @EJB
  UserCrudsLocal userCrudsLocal;
  @EJB private UserAuthenticationLocal userAuthenticationLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<UserDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    UserDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    String plainPassword = details.getPassword();

    if (useEncryption(detailsParameter.getContext())) {
      details.setPassword("");
      details.setEncryptedPassword(plainPassword);
    } else {
      details.setPassword(plainPassword);
      details.setEncryptedPassword("");
    }
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    CrudsResult<Void> uniqueResult = checkUnique(detailsParameter);
    if (uniqueResult.isError()) {
      sessionContext.setRollbackOnly();
      return uniqueResult.convertError();
    }
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = userCrudsLocal.create(context.withParameter(details));

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
  public CrudsResult<DataRef> modify(ParameterContext<UserDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    UserDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
    String plainPassword = details.getPassword();

    if (useEncryption(detailsParameter.getContext())) {
      details.setPassword("");
      details.setEncryptedPassword(plainPassword);
    } else {
      details.setPassword(plainPassword);
      details.setEncryptedPassword("");
    }
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    CrudsResult<Void> uniqueResult = checkUnique(detailsParameter);
    if (uniqueResult.isError()) {
      sessionContext.setRollbackOnly();
      return uniqueResult.convertError();
    }
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = userCrudsLocal.modify(context.withParameter(details));
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
    CrudsResult<Void> uniqueResult = checkUnique(projectionParameter);
    if (uniqueResult.isError()) {
      sessionContext.setRollbackOnly();
      return uniqueResult.convertError();
    }
    // @anchor:preCreateOrModify-validation:end

    CrudsResult<DataRef> result = userCrudsLocal.createOrModify(projectionParameter);
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
      CrudsResult<Void> result = userCrudsLocal.delete(context.withParameter(dataRef));

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
    return userCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = userCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return userCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public UserInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public UserDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public UserDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return userCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return userCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<UserInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<UserDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<UserDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<UserDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
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

    result = userCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends UserFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    result = userCrudsLocal.find(searchParameter);

    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return result;
  }

  // anchor:findBy:end

  /*========== Command implementations ==========*/

  // anchor:command-implementations:start
  // @anchor:command-changePassword-annotations:start
  // @anchor:command-changePassword-annotations:end
  // anchor:custom-command-changePassword-annotations:start
  // anchor:custom-command-changePassword-annotations:end
  public CommandResult changePassword(ParameterContext<UserCommand.ChangePassword> commandParameter) {
    UserCommand.ChangePassword changePasswordCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(changePasswordCommand);

    // @anchor:command-changePassword-before:start
    // @anchor:command-changePassword-before:end
    // anchor:custom-command-changePassword-before:start
    // anchor:custom-command-changePassword-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    CrudsResult<UserDetails> targetResult = getDetailsFromDataRef(parameterFactory.construct(changePasswordCommand.getTarget()));

    UserDetails userDetails = targetResult.getValue();

    // @anchor:command-changePassword:start
    // @anchor:command-changePassword:end
    // anchor:custom-command-changePassword:start
    userDetails.setPassword(changePasswordCommand.getNewPassword());
    CrudsResult<DataRef> crudsResult = modify(parameterFactory.construct(userDetails));
    if (crudsResult.isError()) {
      commandResult = CommandResult.error(changePasswordCommand, "failed to modify user");
    }
    // anchor:custom-command-changePassword:end

    // @anchor:command-changePassword-after:start
    // @anchor:command-changePassword-after:end
    // anchor:custom-command-changePassword-after:start
    // anchor:custom-command-changePassword-after:end

    return commandResult;
  }
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
      importResult = new UserCsvImporterImpl().importFile(parameter);
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
        "net.democritus.usr",
        "User"
    );
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  public <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter) {
    return executeCommand(commandParameter);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <T extends ICommand> CommandResult dispatchCommand(ParameterContext<T> commandParameter) {

    // anchor:custom-before-dispatch:start
    // anchor:custom-before-dispatch:end

    CommandResult commandResult = executeCommand(commandParameter);

    // anchor:custom-after-dispatch:start
    // anchor:custom-after-dispatch:end

    if (commandResult.isError()) {
        sessionContext.setRollbackOnly();
    }

    return commandResult;
  }

  @SuppressWarnings("unchecked")
  private <T extends ICommand> CommandResult executeCommand(ParameterContext<T> commandParameter) {
    ICommand command = commandParameter.getValue();

    // anchor:command-dispatch:start
    if (command instanceof UserCommand.ChangePassword) {
      return changePassword((ParameterContext<UserCommand.ChangePassword>) commandParameter);
    }
    // anchor:command-dispatch:end

    return CommandResult.error(command, "not dispatched");
  }
  private <P> CrudsResult<Void> checkUnique(ParameterContext<P> projectionParameter) {
    UserLocal userLocal = sessionContext.getBusinessObject(UserLocal.class);
    return new UserUniquenessValidation<P>(userLocal, projectionParameter).checkUnique();
  }
  @Override
  public <V> AuthenticationResult authenticate(ParameterContext<AuthenticationDetails<V>> authenticationDetailsParameter) {
    return userAuthenticationLocal.authenticate(authenticationDetailsParameter);
  }
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  private boolean useEncryption(net.democritus.sys.Context context) {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(context);
    return paramTargetValueAgent.isParamTargetValueActive("useEncryptedPassword", "default", false);
  }

  public CrudsResult<Boolean> checkPassword(ParameterContext<UserInput> parameter) {
    return userCrudsLocal.checkPassword(parameter);
  }

  public CrudsResult<Boolean> checkEncryptedPassword(ParameterContext<UserInput> parameter) {
    return userCrudsLocal.checkEncryptedPassword(parameter);
  }
  // anchor:custom-methods:end

}
