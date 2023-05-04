package net.democritus.wfe;

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
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton representing the agent in the webtier,
 * to the business logic of the TimeWindowGroup in the EJB tier
 */

public class TimeWindowGroupProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("workflow", "TimeWindowGroup", "unknownError");
  private static final Diagnostic CREATE_ERROR = Diagnostic.error("workflow", "TimeWindowGroup", "workflow.timeWindowGroup.createFailed");
  private static final Diagnostic MODIFY_ERROR = Diagnostic.error("workflow", "TimeWindowGroup", "workflow.timeWindowGroup.modifyFailed");
  private static final Diagnostic DELETE_ERROR = Diagnostic.error("workflow", "TimeWindowGroup", "workflow.timeWindowGroup.deleteFailed");
  private static final Diagnostic SEARCH_ERROR = Diagnostic.error("workflow", "TimeWindowGroup", "workflow.timeWindowGroup.findFailed");

  private static final Logger logger = LoggerFactory.getLogger(TimeWindowGroupProxy.class);

  private TimeWindowGroupRemote timeWindowGroupRemote = null;
  private static TimeWindowGroupProxy timeWindowGroupProxy = null;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private TimeWindowGroupProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("workflow");
      String remoteName = componentJNDI.getDataRemoteName("TimeWindowGroup");
      timeWindowGroupRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static TimeWindowGroupProxy getTimeWindowGroupProxy() {
    if (timeWindowGroupProxy == null) {
      timeWindowGroupProxy = new TimeWindowGroupProxy();
    }
    return timeWindowGroupProxy;
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance:start
  public CrudsResult<TimeWindowGroupDetails> getDetails(ParameterContext<Long> idParameter) {
    try {
      return timeWindowGroupRemote.getDetails(idParameter);
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
      return (CrudsResult<T>) timeWindowGroupRemote.getProjection(projectionRefParameter);
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
      return (CrudsResult<DataRef>) timeWindowGroupRemote.resolveDataRef(dataRefParameter);
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

  public <S extends TimeWindowGroupFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    try {
      return timeWindowGroupRemote.find(searchParameter);
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
      return timeWindowGroupRemote.getName(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve name for timeWindowGroupId", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    try {
      return timeWindowGroupRemote.getId(nameParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve id for timeWindowGroupName", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  /*========== Create/Modify CRUD methods ==========*/

  // anchor:crud:start
  public CrudsResult<DataRef> create(ParameterContext<TimeWindowGroupDetails> detailsParameter) {
    try {
      return timeWindowGroupRemote.create(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create TimeWindowGroup", re
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }

  public CrudsResult<DataRef> modify(ParameterContext<TimeWindowGroupDetails> detailsParameter) {
    try {
      return timeWindowGroupRemote.modify(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not modify TimeWindowGroup", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    try {
      return timeWindowGroupRemote.createOrModify(projectionParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create nor modify TimeWindowGroup", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    try {
      return timeWindowGroupRemote.delete(idParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete TimeWindowGroup", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }

  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> parameter) {
    try {
      return timeWindowGroupRemote.deleteByDataRef(parameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete TimeWindowGroup", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }
  // anchor:crud:end

  // @anchor:methods:start
  // @anchor:methods:end

  /*=========== Customisation ===========*/

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}

