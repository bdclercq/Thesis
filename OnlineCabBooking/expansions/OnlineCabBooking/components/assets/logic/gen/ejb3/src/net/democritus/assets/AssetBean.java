package net.democritus.assets;

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
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.assets.FileAssetLocal;
import net.democritus.assets.FileAssetDetails;
import net.democritus.assets.InternalAssetLocal;
import net.democritus.assets.InternalAssetDetails;
import net.democritus.assets.ExternalAssetLocal;
import net.democritus.assets.ExternalAssetDetails;
// anchor:imports:end

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(AssetRemote.class)
@Local(AssetLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class AssetBean /*@anchor:interfaces:start@*/implements AssetRemote, AssetLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("assets", "Asset");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  @EJB private FileAssetLocal fileAssetLocal;

  @EJB private InternalAssetLocal internalAssetLocal;

  @EJB private ExternalAssetLocal externalAssetLocal;

  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(AssetBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_assets")
  private EntityManager entityManager;

  @EJB
  AssetCrudsLocal assetCrudsLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<AssetDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    AssetDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
        CrudsResult<Void> initResult = initAsset(detailsParameter);
        if (initResult.isError()) {
          return initResult.convertError();
        }
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    CrudsResult<Void> uniqueResult = checkUnique(detailsParameter);
    if (uniqueResult.isError()) {
      sessionContext.setRollbackOnly();
      return uniqueResult.convertError();
    }
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = assetCrudsLocal.create(context.withParameter(details));

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
  public CrudsResult<DataRef> modify(ParameterContext<AssetDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    AssetDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
        if (true) {
          return diagnosticHelper.createCrudsError("Cannot be modified");
        }
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    CrudsResult<Void> uniqueResult = checkUnique(detailsParameter);
    if (uniqueResult.isError()) {
      sessionContext.setRollbackOnly();
      return uniqueResult.convertError();
    }
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = assetCrudsLocal.modify(context.withParameter(details));
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

    CrudsResult<DataRef> result = assetCrudsLocal.createOrModify(projectionParameter);
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
    deleteLinkedAssets(idParameter.construct(getDataRef(id)));
    // @anchor:preDelete:end

    // anchor:custom-preDelete:start
    // anchor:custom-preDelete:end

    try {
      // @anchor:preDelete-tryBlock:start
      // @anchor:preDelete-tryBlock:end

      DataRef dataRef = idToDataRef(idParameter.getValue());
      CrudsResult<Void> result = assetCrudsLocal.delete(context.withParameter(dataRef));

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
    return assetCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = assetCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return assetCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public AssetInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public AssetDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public AssetDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return assetCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return assetCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<AssetInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<AssetDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<AssetDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<AssetDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
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

    result = assetCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends AssetFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    String projection = searchParameter.getValue().getProjection();
    // anchor:custom-before-find:end

    result = assetCrudsLocal.find(searchParameter);

    // anchor:custom-after-find:start
    // anchor:custom-after-find:end

    return result;
  }

  // anchor:findBy:end

  /*========== Command implementations ==========*/

  // anchor:command-implementations:start
  // @anchor:command-registerExternalAsset-annotations:start
  // @anchor:command-registerExternalAsset-annotations:end
  // anchor:custom-command-registerExternalAsset-annotations:start
  // anchor:custom-command-registerExternalAsset-annotations:end
  public CommandResult registerExternalAsset(ParameterContext<AssetCommand.RegisterExternalAsset> commandParameter) {
    AssetCommand.RegisterExternalAsset registerExternalAssetCommand = commandParameter.getValue();
    CommandResult commandResult = CommandResult.success(registerExternalAssetCommand);

    // @anchor:command-registerExternalAsset-before:start
    // @anchor:command-registerExternalAsset-before:end
    // anchor:custom-command-registerExternalAsset-before:start
    // anchor:custom-command-registerExternalAsset-before:end

    IParameterContextFactory parameterFactory = commandParameter.getFactory();

    // @anchor:command-registerExternalAsset:start
    // @anchor:command-registerExternalAsset:end
    // anchor:custom-command-registerExternalAsset:start
    ExternalAssetDetails externalAssetDetails = new ExternalAssetDetails();
    externalAssetDetails.setUri(registerExternalAssetCommand.getUri());

    CrudsResult<DataRef> crudsResult = externalAssetLocal.create(commandParameter.construct(externalAssetDetails));
    if (crudsResult.isError()) {
      return CommandResult.error(registerExternalAssetCommand, "Could not create external asset");
    }

    CrudsResult<ExternalAssetDetails> detailsResult = externalAssetLocal.getDetails(parameterFactory.construct(crudsResult.getValue().getId()));
    if (detailsResult.isError()) {
      return CommandResult.error(registerExternalAssetCommand, "Could not retrieve created external asset");
    }

    externalAssetDetails = detailsResult.getValue();

    AssetDetails assetDetails = new AssetDetails();
    assetDetails.setName(externalAssetDetails.getName());
    assetDetails.setByteSize(externalAssetDetails.getByteSize());
    assetDetails.setContentType(externalAssetDetails.getContentType());
    assetDetails.setType(AssetType.REMOTE.name());
    assetDetails.setExternalAsset(crudsResult.getValue());
    assetDetails.setComplete(true);

    CrudsResult<DataRef> createResult = create(parameterFactory.construct(assetDetails));
    if (createResult.isError()) {
      return CommandResult.error(registerExternalAssetCommand, "Could not create asset");
    }
    // anchor:custom-command-registerExternalAsset:end

    // @anchor:command-registerExternalAsset-after:start
    // @anchor:command-registerExternalAsset-after:end
    // anchor:custom-command-registerExternalAsset-after:start
    // anchor:custom-command-registerExternalAsset-after:end

    return commandResult;
  }
  // anchor:command-implementations:end

  /*========== Import/Export ==========*/

  // anchor:import-export:start
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
        "assets",
        "net.democritus.assets",
        "Asset"
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
    if (command instanceof AssetCommand.RegisterExternalAsset) {
      return registerExternalAsset((ParameterContext<AssetCommand.RegisterExternalAsset>) commandParameter);
    }
    // anchor:command-dispatch:end

    return CommandResult.error(command, "not dispatched");
  }
  private <P> CrudsResult<Void> checkUnique(ParameterContext<P> projectionParameter) {
    AssetLocal assetLocal = sessionContext.getBusinessObject(AssetLocal.class);
    return new AssetUniquenessValidation<P>(assetLocal, projectionParameter).checkUnique();
  }
  private CrudsResult<Void> initAsset(ParameterContext<AssetDetails> parameter) {
    AssetDetails details = parameter.getValue();
    AssetType type = AssetType.valueOf(details.getType());

    switch (type) {
      case FILE:
        return createFileAsset(parameter);
      case INTERNAL:
        return createInternalAsset(parameter);
      case REMOTE:
        return CrudsResult.success();
      // anchor:init-asset-types:start
      // anchor:init-asset-types:end
      default:
        return diagnosticHelper.createCrudsError("Not yet implemented");
    }
  }

  @Override
  public CrudsResult<AssetChunk> getAssetChunk(ParameterContext<AssetChunkRef> refParam) {
    AssetChunkRef assetChunkRef = refParam.getValue();
    AssetDetails details = getDetails(assetChunkRef.getAsset());
    AssetType type = AssetType.valueOf(details.getType());

    switch (type) {
      case FILE:
        return fileAssetLocal.getAssetChunk(refParam);
      case INTERNAL:
        return internalAssetLocal.getAssetChunk(refParam);
      case REMOTE:
        return externalAssetLocal.getAssetChunk(refParam);
      // anchor:get-chunk-types:start
      // anchor:get-chunk-types:end
      default:
        return diagnosticHelper.createCrudsError("Unknown type " + details.getType());
    }
  }

  @Override
  public CrudsResult<Void> uploadChunk(ParameterContext<AssetChunk> parameter) {
    DataRef asset = parameter.getValue().getAsset();
    AssetDetails details = getDetails(asset);
    AssetType type = AssetType.valueOf(details.getType());

    switch (type) {
      case FILE:
        return fileAssetLocal.uploadAssetChunk(parameter);
      case INTERNAL:
        return internalAssetLocal.uploadAssetChunk(parameter);
      // anchor:upload-chunk-types:start
      // anchor:upload-chunk-types:end
      default:
        return diagnosticHelper.createCrudsError("Not yet implemented");
    }
  }

  public CrudsResult<Void> deleteLinkedAssets(ParameterContext<DataRef> parameter) {
    DataRef asset = parameter.getValue();
    AssetDetails details = getDetails(asset);
    AssetType type = AssetType.valueOf(details.getType());

    switch (type) {
      case FILE:
      case INTERNAL:
      case REMOTE:
        // Errors are not handled here as we want to remove any linked asset types, even if the data is inconsistent

        DataRef fileAsset = details.getFileAsset();
        if (fileAsset.getId() != 0) {
          fileAssetLocal.delete(parameter.construct(fileAsset.getId()));
        }

        DataRef internalAsset = details.getInternalAsset();
        if (internalAsset.getId() != 0) {
          internalAssetLocal.delete(parameter.construct(internalAsset.getId()));
        }
        return CrudsResult.success();
      // anchor:delete-linked-asset-types:start
      // anchor:delete-linked-asset-types:end
      default:
        return diagnosticHelper.createCrudsError("Not yet implemented");
    }
  }

  // anchor:asset-custom-type-methods:start
  // anchor:asset-custom-type-methods:end
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  private CrudsResult<Void> createFileAsset(ParameterContext<AssetDetails> parameter) {
    AssetDetails details = parameter.getValue();

    FileAssetDetails fileAssetDetails = new FileAssetDetails();
    fileAssetDetails.setName(details.getName());

    CrudsResult<DataRef> crudsResult = fileAssetLocal.create(parameter.construct(fileAssetDetails));
    if (crudsResult.isSuccess()) {
      details.setFileAsset(crudsResult.getValue());
      return CrudsResult.success();
    } else {
      return crudsResult.convertError();
    }
  }

  private CrudsResult<Void> createInternalAsset(ParameterContext<AssetDetails> parameter) {
    AssetDetails details = parameter.getValue();

    InternalAssetDetails internalAssetDetails = new InternalAssetDetails();
    internalAssetDetails.setName(details.getName());

    CrudsResult<DataRef> crudsResult = internalAssetLocal.create(parameter.construct(internalAssetDetails));
    if (crudsResult.isSuccess()) {
      details.setInternalAsset(crudsResult.getValue());
      return CrudsResult.success();
    } else {
      return crudsResult.convertError();
    }
  }
  // anchor:custom-methods:end

}
