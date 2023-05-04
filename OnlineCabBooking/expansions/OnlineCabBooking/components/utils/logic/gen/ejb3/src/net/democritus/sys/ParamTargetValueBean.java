package net.democritus.sys;

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
import net.democritus.sys.ParamTargetValueCsvImporterImpl;
import javax.ejb.Remote;
import javax.ejb.Local;
// @anchor:imports:end

// anchor:imports:start
// anchor:imports:end

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// anchor:custom-imports:start
import static net.palver.util.Options.none;
import static net.palver.util.Options.some;
// anchor:custom-imports:end

@Stateless()
// @anchor:annotations:start
@Remote(ParamTargetValueRemote.class)
@Local(ParamTargetValueLocal.class)
// @anchor:annotations:end
@DeclareRoles({
// anchor:custom-declare-roles:start
// anchor:custom-declare-roles:end
})
public class ParamTargetValueBean /*@anchor:interfaces:start@*/implements ParamTargetValueRemote, ParamTargetValueLocal/*@anchor:interfaces:end@*/{

  private final DiagnosticHelper diagnosticHelper = new DiagnosticHelper("utils", "ParamTargetValue");
  private final DiagnosticFactory diagnosticFactory = diagnosticHelper.getDiagnosticFactory();

  @Resource
  private SessionContext sessionContext;

  // anchor:entity-managers:start
  // anchor:entity-managers:end

  // anchor:link-variables:start
  // anchor:link-variables:end

  // @anchor:variables:start
  private static final Logger logger = LoggerFactory.getLogger(ParamTargetValueBean.class);
  @Deprecated
  @PersistenceContext(unitName="OnlineCabBooking_utils")
  private EntityManager entityManager;

  @EJB
  ParamTargetValueCrudsLocal paramTargetValueCrudsLocal;
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Bean lifecycle methods ==========*/

  // anchor:crudsMethods:start
  // @anchor:create-annotations:start
  // @anchor:create-annotations:end
  // anchor:custom-create-annotations:start
  // anchor:custom-create-annotations:end
  public CrudsResult<DataRef> create(ParameterContext<ParamTargetValueDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
      return getDiagnosticHelper().createCrudsError(CREATE_ERROR_MSG_KEY);
    }

    ParamTargetValueDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preCreate:start
    // @anchor:preCreate:end

    // anchor:custom-preCreate:start
    // anchor:custom-preCreate:end

    // @anchor:preCreate-validation:start
    // @anchor:preCreate-validation:end

    CrudsResult<DataRef> result = paramTargetValueCrudsLocal.create(context.withParameter(details));

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
  public CrudsResult<DataRef> modify(ParameterContext<ParamTargetValueDetails> detailsParameter) {
    if (sessionContext.getRollbackOnly()) {
        return getDiagnosticHelper().createCrudsError(MODIFY_ERROR_MSG_KEY);
    }

    ParamTargetValueDetails details = detailsParameter.getValue();
    @SuppressWarnings("unused")
    UserContext userContext = detailsParameter.getUserContext();
    net.democritus.sys.Context context = detailsParameter.getContext();

    // @anchor:preModify:start
    // @anchor:preModify:end

    // anchor:custom-preModify:start
    // anchor:custom-preModify:end

    // @anchor:preModify-validation:start
    // @anchor:preModify-validation:end

    CrudsResult<DataRef> result = paramTargetValueCrudsLocal.modify(context.withParameter(details));
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

    CrudsResult<DataRef> result = paramTargetValueCrudsLocal.createOrModify(projectionParameter);
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
      CrudsResult<Void> result = paramTargetValueCrudsLocal.delete(context.withParameter(dataRef));

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
    return paramTargetValueCrudsLocal.getDataRefFromName(nameParameter);
  }

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    // @anchor:before-getName:start
    // @anchor:before-getName:end
    CrudsResult<DataRef> dataRefResult = paramTargetValueCrudsLocal.getDataRefFromId(idParameter);

    if (dataRefResult.isError()) {
      return dataRefResult.convertError();
    }
    return CrudsResult.success(dataRefResult.getValue().getName());
  }

  public DataRef getDataRef(Long id) {
    return paramTargetValueCrudsLocal.getDataRefFromId(ParameterContext.create(null, id)).getValue();
  }

  @Deprecated
  public ParamTargetValueInfo getInfo(Long id) {
    return getInfo(ParameterContext.create(null, id)).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public ParamTargetValueDetails getDetails(Long id) {
    return getDetailsFromDataRef(ParameterContext.create(null, idToDataRef(id))).getValue();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public ParamTargetValueDetails getDetails(DataRef dataRef) {
    return getDetailsFromDataRef(ParameterContext.create(null, dataRef)).getValue();
  }

  public CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter) {
    return paramTargetValueCrudsLocal.getDataRefFromId(idParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    return paramTargetValueCrudsLocal.resolveDataRef(dataRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<ParamTargetValueInfo> getInfo(ParameterContext<Long> idParameter) {
    DataRef dataRef = idToDataRef(idParameter.getValue());
    ProjectionRef projectionRef = new ProjectionRef("info", dataRef);
    ParameterContext<ProjectionRef> projectionRefParameter = idParameter.construct(projectionRef );
    return getProjection(projectionRefParameter);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<ParamTargetValueDetails> getDetails(ParameterContext<Long> idParameter) {
    Long id = idParameter.getValue();

    // @anchor:preGetDetails:start
    // @anchor:preGetDetails:end

    DataRef dataRef = idToDataRef(id);
    CrudsResult<ParamTargetValueDetails> result = getDetailsFromDataRef(idParameter.construct(dataRef));

    if (result.isError()) {
      return result;
    }

    // @anchor:postGetDetails:start
    // @anchor:postGetDetails:end

    return result;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public CrudsResult<ParamTargetValueDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRefParameter) {
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

    result = paramTargetValueCrudsLocal.getProjection(projectionRefParameter);

    // anchor:custom-after-getProjection:start
    // anchor:custom-after-getProjection:end

    return result;
  }

  // anchor:custom-find-annotations:start
  // anchor:custom-find-annotations:end
  public <S extends ParamTargetValueFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    SearchResult<T> result;
    // anchor:custom-before-find:start
    // anchor:custom-before-find:end

    result = paramTargetValueCrudsLocal.find(searchParameter);

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
      importResult = new ParamTargetValueCsvImporterImpl().importFile(parameter);
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
        "utils",
        "net.democritus.sys",
        "ParamTargetValue"
    );
  }

  // anchor:compare-set-methods:start
  // anchor:compare-set-methods:end

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== customizations ==========*/

  // anchor:custom-methods:start
  /**
   * Retrieve a ParamTargetValue from the database using the provided FindByParamEq_TargetEq finder.
   * <p>
   * If the ParamTargetValue is not found, it will search with Target set to {@code "default"}.
   * If that is not found either, an empty string is returned.
   *
   * @param parameter the ParamTarget finder
   * @return ParamTarget's value, or empty string if none is found
   */
  public String getParamTargetValue(ParameterContext<ParamTargetValueFindByParamEq_TargetEqDetails> parameter) {
    {
      ParamTargetValueFindByParamEq_TargetEqDetails finder = parameter.getValue();
      SearchDetails<ParamTargetValueFindByParamEq_TargetEqDetails> searchDetails = SearchDetails.fetchNDetails(finder, 1);
      searchDetails.setSkipCount(true);
      SearchResult<ParamTargetValueDetails> searchResult = find(parameter.construct(searchDetails));
      Option<String> maybeValue = getValue(searchResult);
      if (maybeValue.isDefined()) {
        return maybeValue.getValue();
      }
    }

    {
      // recreate the finder to avoid modifying the state of the provided one
      ParamTargetValueFindByParamEq_TargetEqDetails defaultFinder = new ParamTargetValueFindByParamEq_TargetEqDetails();
      defaultFinder.setParam(parameter.getValue().getParam());
      defaultFinder.setTarget("default");

      SearchDetails<ParamTargetValueFindByParamEq_TargetEqDetails> searchDetails = SearchDetails.fetchNDetails(defaultFinder, 1);
      searchDetails.setSkipCount(true);
      SearchResult<ParamTargetValueDetails> searchResult = find(parameter.construct(searchDetails));
      Option<String> maybeValue = getValue(searchResult);
      if (maybeValue.isDefined()) {
        return maybeValue.getValue();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "ParamTargetValue with parameter '" + parameter.getValue().getParam() + "' not found, returning \"\" instead"
      );
    }
    return "";
  }

  /**
   * Return ParamTarget value cast to boolean.
   * <p>
   * The value should be one of (case insensitive) {@code yes, no, true, false},
   * or empty string (converted to false).
   * <p>
   * If no value was found, the value is empty, or is not vild, the {@code defaultValue} is returned instead.
   *
   * @param parameter    ParamTargetValue finder
   * @param defaultValue the default value to use if no value was found
   * @return value converted to boolean
   */
  public boolean isParamTargetValueActive(ParameterContext<ParamTargetValueFindByParamEq_TargetEqDetails> parameter,
                                          boolean defaultValue) {
    String value = getParamTargetValue(parameter);
    if (value.isEmpty()) {
      return defaultValue;
    }
    if (value.equalsIgnoreCase("no") || value.equalsIgnoreCase("false")) {
      return false;
    }
    if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true")) {
      return true;
    }
    if (logger.isWarnEnabled()) {
      logger.warn("ParamTargetValue's value should be one of [yes, no, true, false, ''], got '" + value + "'." +
          " Returning defaultValue '" + defaultValue + "'.");
    }
    return defaultValue;
  }

  /**
   * Set a value of a ParamTargetValue element.
   * <p>
   * If a ParamTargetValue with specified ParamTarget doesn't exist, it is created, otherwise the value is updated.
   *
   * @param parameter the new value of ParamTargetValue
   * @return stateful update result
   */
  public CrudsResult<DataRef> setParamTargetValue(ParameterContext<ParamTargetValueDetails> parameter) {
    ParamTargetValueDetails newDetails = parameter.getValue();

    ParamTargetValueFindByParamEq_TargetEqDetails finder = new ParamTargetValueFindByParamEq_TargetEqDetails();
    finder.setParam(newDetails.getParam());
    finder.setTarget(newDetails.getTarget());
    SearchDetails<ParamTargetValueFindByParamEq_TargetEqDetails> searchDetails = SearchDetails.fetchNDetails(finder, 1);
    searchDetails.setSkipCount(true);
    SearchResult<ParamTargetValueDetails> searchResult = find(parameter.construct(searchDetails));
    if (searchResult.isError()) {
      return CrudsResult.error(searchResult.getDiagnostics());
    }

    if (searchResult.getTotalNumberOfItems() == 0) {
      return create(parameter.construct(newDetails));
    } else {
      ParamTargetValueDetails existingDetails = searchResult.getFirst().getValue();
      existingDetails.setValue(newDetails.getValue());

      return modify(parameter.construct(existingDetails));
    }
  }

  private Option<String> getValue(SearchResult<ParamTargetValueDetails> searchResult) {
    if (searchResult.getFirst().isDefined()) {
      ParamTargetValueDetails paramTargetValueDetails = searchResult.getFirst().getValue();
      String value = paramTargetValueDetails.getValue();
      return some(value != null ? value : "");
    } else {
      return none();
    }
  }
  // anchor:custom-methods:end

}
