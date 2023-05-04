package net.democritus.sys;

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
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:imports:start
// anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ParamTargetValueAgent implements ParamTargetValueAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(ParamTargetValueAgent.class);

  private static final ParamTargetValueProxy paramTargetValueProxy = ParamTargetValueProxy.getParamTargetValueProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private ParamTargetValueAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static ParamTargetValueAgent getParamTargetValueAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new ParamTargetValueAgent(context, userContext);
  }

  @Deprecated
  public static ParamTargetValueAgent getParamTargetValueAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new ParamTargetValueAgent(context, userContext);
  }

  @Deprecated
  public static ParamTargetValueAgent getParamTargetValueAgent() {
    return getParamTargetValueAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    ParamTargetValueFindAllParamTargetValuesDetails finder = new ParamTargetValueFindAllParamTargetValuesDetails();
    return paramTargetValueProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<ParamTargetValueInfo> findAllInfos() {
    ParamTargetValueFindAllParamTargetValuesDetails finder = new ParamTargetValueFindAllParamTargetValuesDetails();
    return paramTargetValueProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long paramTargetValueId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(paramTargetValueId));
    CrudsResult<DataRef> result = paramTargetValueProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public ParamTargetValueInfo getInfo(Long paramTargetValueId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(paramTargetValueId));
    CrudsResult<ParamTargetValueInfo> result = paramTargetValueProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ParamTargetValueInfo();
    }
  }

  public ParamTargetValueDetails getDetails_old(Long paramTargetValueId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(paramTargetValueId));
    CrudsResult<ParamTargetValueDetails> result = paramTargetValueProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new ParamTargetValueDetails();
    }
  }

  public CrudsResult<ParamTargetValueDetails> getDetails(Long paramTargetValueId) {
    return paramTargetValueProxy.getDetails(createParameter(paramTargetValueId));
  }

  public CrudsResult<ParamTargetValueDetails> getDetails(DataRef dataRef) {
    return paramTargetValueProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return paramTargetValueProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return paramTargetValueProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long paramTargetValueId) {
    CrudsResult<String> result = paramTargetValueProxy.getName(createParameter(paramTargetValueId));
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

  public Long getId(String paramTargetValueName) {
    CrudsResult<DataRef> result = paramTargetValueProxy.getId(createParameter(paramTargetValueName));
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
    return paramTargetValueProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new ParamTargetValue instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(ParamTargetValueDetails details) {
    return paramTargetValueProxy.create(createParameter(details));
  }

  /**
   * Modify an existing ParamTargetValue instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(ParamTargetValueDetails details) {
    return paramTargetValueProxy.modify(createParameter(details));
  }

  /**
   * Create a ParamTargetValue instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return paramTargetValueProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target ParamTargetValue instance by id.
   *
   * @deprecated Use {@link ParamTargetValueAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return paramTargetValueProxy.delete(createParameter(oid));
  }

  /**
   * Delete target ParamTargetValue instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return paramTargetValueProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the ParamTargetValue element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends ParamTargetValueFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return paramTargetValueProxy.find(createParameter(searchDetails));
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
  /**
   * Import a file containing ParamTargetValue data
   *
   * @param importFile an object containing data and a type
   * @return success if all instances were imported successfully, a list of errors otherwise
   */
  public ImportResult importFile(ImportFile importFile) {
    return paramTargetValueProxy.importFile(createParameter(importFile));
  }
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  public String getParamTargetValue(String paramName, String targetName) {
    ParamTargetValueFindByParamEq_TargetEqDetails finder = new ParamTargetValueFindByParamEq_TargetEqDetails();
    finder.setParam(paramName);
    finder.setTarget(targetName);
    return paramTargetValueProxy.getParamTargetValue(createParameter(finder));
  }

  public boolean isParamTargetValueActive(String paramName, String targetName, boolean defaultValue) {
    ParamTargetValueFindByParamEq_TargetEqDetails finder = new ParamTargetValueFindByParamEq_TargetEqDetails();
    finder.setParam(paramName);
    finder.setTarget(targetName);
    return paramTargetValueProxy.isParamTargetValueActive(createParameter(finder), defaultValue);
  }

  public CrudsResult<DataRef> setParamTargetValue(String paramName, String targetName, Object value) {
    final ParamTargetValueDetails details = new ParamTargetValueDetails();
    details.setParam(paramName);
    details.setTarget(targetName);
    details.setValue(String.valueOf(value));
    return paramTargetValueProxy.setParamTargetValue(createParameter(details));
  }
  // anchor:custom-methods:end
}

