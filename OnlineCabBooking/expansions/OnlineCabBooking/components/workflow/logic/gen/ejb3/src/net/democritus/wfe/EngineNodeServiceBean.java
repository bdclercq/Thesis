package net.democritus.wfe;

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

import net.democritus.state.StateUpdate;


// @anchor:imports:start
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
import net.democritus.wfe.EngineNodeLocal;
import net.democritus.wfe.EngineNodeDetails;
import net.democritus.wfe.EngineServiceLocal;
import net.democritus.wfe.EngineServiceDetails;
// anchor:imports:end

// anchor:valuetype-imports:start
import java.util.Date;

// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(EngineNodeServiceRemote.class)
@Local(EngineNodeServiceLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class EngineNodeServiceBean /*@anchor:interfaces:start@*/implements EngineNodeServiceRemote, EngineNodeServiceLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("workflow", "EngineNodeService");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  @EJB private EngineNodeLocal engineNodeLocal;

  @EJB private EngineServiceLocal engineServiceLocal;

  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(EngineNodeServiceBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_workflow")
  private EntityManager entityManager;

  @EJB
  EngineNodeServiceCrudsLocal engineNodeServiceCrudsLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<EngineNodeServiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    EngineNodeServiceDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = engineNodeServiceCrudsLocal.create(context.withParameter(details));

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
  public CrudsResult<DataRef> modify(ParameterContext<EngineNodeServiceDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    EngineNodeServiceDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = engineNodeServiceCrudsLocal.modify(context.withParameter(details));
    if (result.isError()) {
      return result;
    }

    // @anchor:postModify:start
    // @anchor:postModify:end

    // anchor:custom-postModify:start
    // anchor:custom-postModify:end

    return result;
  }

  // @anchor:modifyWithProjection-annotations:start
  // @anchor:modifyWithProjection-annotations:end
  // anchor:custom-modifyWithProjection-annotations:start
  // anchor:custom-modifyWithProjection-annotations:end
  public <P> CrudsResult<DataRef> modifyWithProjection(ParameterContext<P> projectionParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    // @anchor:preModify-projection:start
    // @anchor:preModify-projection:end

    // anchor:custom-preModify-projection:start
    // anchor:custom-preModify-projection:end

    // @anchor:preModify-projection-validation:start
    // @anchor:preModify-projection-validation:end

    CrudsResult<DataRef> result = engineNodeServiceCrudsLocal.modifyWithProjection(projectionParameter);
    if (result.isError()) {
      return result;
    }

    // @anchor:postModify-projection:start
    // @anchor:postModify-projection:end

    // anchor:custom-postModify-projection:start
    // anchor:custom-postModify-projection:end

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

    CrudsResult<DataRef> result = engineNodeServiceCrudsLocal.createOrModify(projectionParameter);
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
      CrudsResult<Void> result = engineNodeServiceCrudsLocal.delete(context.withParameter(dataRef));

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
    return engineNodeServiceCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = engineNodeServiceCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return engineNodeServiceCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public EngineNodeServiceInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public EngineNodeServiceDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public EngineNodeServiceDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return engineNodeServiceCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return engineNodeServiceCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<EngineNodeServiceInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<EngineNodeServiceDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<EngineNodeServiceDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<EngineNodeServiceDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
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

    result = engineNodeServiceCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends EngineNodeServiceFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    result = engineNodeServiceCrudsLocal.find(searchParameter);

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
        "workflow",
        "net.democritus.wfe",
        "EngineNodeService"
    );
  }

  // anchor:compare-set-methods:start
  @Override
  public CrudsResult<Void> compareAndSetStatus(ParameterContext<StateUpdate> parameter) {
    return engineNodeServiceCrudsLocal.compareAndSetStatus(parameter);
  }
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  @Override
  public CrudsResult<Void> setNotResponding(ParameterContext<DataRef> parameter) {
      EngineNodeServiceDetails details = getDetails(parameter.getValue());
      details.setStatus(EngineNodeServiceState.NOT_RESPONDING.getStatus());
      details.setNextRun(null);

      CrudsResult<DataRef> modifyResult = modify(parameter.construct(details));
      if (modifyResult.isError()) {
          return modifyResult.convertError();
      }

      return CrudsResult.success();
  }

  @Override
  public CrudsResult<Void> setReadyForShutdown(ParameterContext<DataRef> parameter) {
    EngineNodeServiceDetails details = getDetails(parameter.getValue());
    details.setStatus(EngineNodeServiceState.READY_FOR_SHUTDOWN.getStatus());
    details.setNextRun(null);

    CrudsResult<DataRef> modifyResult = modify(parameter.construct(details));
    if (modifyResult.isError()) {
      return modifyResult.convertError();
    }

    return CrudsResult.success();
  }

  @Override
  public CrudsResult<EngineNodeServiceDetails> getEngineNodeServiceForEngineService(ParameterContext<DataRef> parameter) {
    EngineNodeContext engineNodeContext = parameter.getContext()
        .getContext(EngineNodeContext.class)
        .orElse(fetchEngineNodeContext(parameter.getContext()));

    EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails finder = new EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails();
    finder.setEngineService(parameter.getValue());
    finder.setEngineNode(engineNodeContext.getEngineNode());

    SearchDetails<EngineNodeServiceFindByEngineNodeEq_EngineServiceEqDetails> searchDetails = SearchDetails.fetchNDetails(finder, 1);
    searchDetails.setSkipCount(true);

    SearchResult<EngineNodeServiceDetails> searchResult = find(parameter.construct(searchDetails));
    if (searchResult.isError()) {
      return CrudsResult.error();
    } else if (searchResult.getFirst().isDefined()) {
      return CrudsResult.success(searchResult.getFirst().getValue());
    } else {
      EngineNodeServiceDetails engineNodeService = new EngineNodeServiceDetails();
      engineNodeService.setName(parameter.getValue().getName());
      engineNodeService.setEngineNode(engineNodeContext.getEngineNode());
      engineNodeService.setEngineService(parameter.getValue());
      engineNodeService.setStatusEnum(EngineNodeServiceState.WAITING);

      CrudsResult<DataRef> result = create(parameter.construct(engineNodeService));
      if (result.isError()) {
        return result.convertError();
      }
      return getDetailsFromDataRef(parameter.construct(result.getValue()));
    }
  }

  private EngineNodeContext fetchEngineNodeContext(net.democritus.sys.Context context) {
    return new EngineNodeContextRetriever(context).retrieveEngineNodeContext();
  }
  // anchor:custom-methods:end

}
