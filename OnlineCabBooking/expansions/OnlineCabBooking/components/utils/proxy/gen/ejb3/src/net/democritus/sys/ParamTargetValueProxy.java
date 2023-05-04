package net.democritus.sys;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.Date;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.Diagnostic;

import net.democritus.sys.search.SearchDetails;

import net.democritus.sys.ParameterContext;
import net.democritus.jndi.ComponentJNDI;

// @anchor:imports:start
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton representing the agent in the webtier,
 * to the business logic of the ParamTargetValue in the EJB tier
 */

public class ParamTargetValueProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("utils", "ParamTargetValue", "unknownError");
  private static final Diagnostic CREATE_ERROR = Diagnostic.error("utils", "ParamTargetValue", "utils.paramTargetValue.createFailed");
  private static final Diagnostic MODIFY_ERROR = Diagnostic.error("utils", "ParamTargetValue", "utils.paramTargetValue.modifyFailed");
  private static final Diagnostic DELETE_ERROR = Diagnostic.error("utils", "ParamTargetValue", "utils.paramTargetValue.deleteFailed");
  private static final Diagnostic SEARCH_ERROR = Diagnostic.error("utils", "ParamTargetValue", "utils.paramTargetValue.findFailed");

  private static final Logger logger = LoggerFactory.getLogger(ParamTargetValueProxy.class);

  private ParamTargetValueRemote paramTargetValueRemote = null;
  private static ParamTargetValueProxy paramTargetValueProxy = null;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private ParamTargetValueProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("utils");
      String remoteName = componentJNDI.getDataRemoteName("ParamTargetValue");
      paramTargetValueRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static ParamTargetValueProxy getParamTargetValueProxy() {
    if (paramTargetValueProxy == null) {
      paramTargetValueProxy = new ParamTargetValueProxy();
    }
    return paramTargetValueProxy;
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance:start
  public CrudsResult<ParamTargetValueDetails> getDetails(ParameterContext<Long> idParameter) {
    try {
      return paramTargetValueRemote.getDetails(idParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getDetails() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter) {
    try {
      return (CrudsResult<T>) paramTargetValueRemote.getProjection(projectionRefParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getProjection() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter) {
    try {
      return (CrudsResult<DataRef>) paramTargetValueRemote.resolveDataRef(dataRefParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "resolveDataRef() failed", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }
  // anchor:instance:end

  /*========== Individual finders ==========*/

  public <S extends ParamTargetValueFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    try {
      return paramTargetValueRemote.find(searchParameter);
    } catch(Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "find() failed", e
        );
      }
      return SearchResult.error(SEARCH_ERROR);
    }
  }

  /*========== Name/Id resolution methods ==========*/

  public CrudsResult<String> getName(ParameterContext<Long> idParameter) {
    try {
      return paramTargetValueRemote.getName(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve name for paramTargetValueId", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    try {
      return paramTargetValueRemote.getId(nameParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve id for paramTargetValueName", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  /*========== Create/Modify CRUD methods ==========*/

  // anchor:crud:start
  public CrudsResult<DataRef> create(ParameterContext<ParamTargetValueDetails> detailsParameter) {
    try {
      return paramTargetValueRemote.create(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create ParamTargetValue", re
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }

  public CrudsResult<DataRef> modify(ParameterContext<ParamTargetValueDetails> detailsParameter) {
    try {
      return paramTargetValueRemote.modify(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not modify ParamTargetValue", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    try {
      return paramTargetValueRemote.createOrModify(projectionParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create nor modify ParamTargetValue", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    try {
      return paramTargetValueRemote.delete(idParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete ParamTargetValue", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }

  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> parameter) {
    try {
      return paramTargetValueRemote.deleteByDataRef(parameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete ParamTargetValue", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }
  // anchor:crud:end

  // @anchor:methods:start
  public ImportResult importFile(ParameterContext<ImportFile> parameter) {
    try {
      return paramTargetValueRemote.importFile(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not import file", e
        );
      }
      return ImportResult.globalError("Unexpected error");
    }
  }
  // @anchor:methods:end

  /*=========== Customisation ===========*/

  // anchor:custom-methods:start
  public String getParamTargetValue(ParameterContext<ParamTargetValueFindByParamEq_TargetEqDetails> parameter) {
    try {
      return paramTargetValueRemote.getParamTargetValue(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could find ParamTargetValue", e
        );
      }
      return "";
    }
  }

  public boolean isParamTargetValueActive(ParameterContext<ParamTargetValueFindByParamEq_TargetEqDetails> parameter, boolean defaultValue) {
    try {
      return paramTargetValueRemote.isParamTargetValueActive(parameter, defaultValue);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could find ParamTargetValue", e
        );
      }
      return defaultValue;
    }
  }

  public CrudsResult<DataRef> setParamTargetValue(ParameterContext<ParamTargetValueDetails> parameter) {
    try {
      return paramTargetValueRemote.setParamTargetValue(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not set ParamTargetValue", e
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }
  // anchor:custom-methods:end
}

