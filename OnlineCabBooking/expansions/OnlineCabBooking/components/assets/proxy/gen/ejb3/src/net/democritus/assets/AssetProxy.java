package net.democritus.assets;

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
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Singleton representing the agent in the webtier,
 * to the business logic of the Asset in the EJB tier
 */

public class AssetProxy {

  private static final Diagnostic UNKNOWN_ERROR = Diagnostic.error("assets", "Asset", "unknownError");
  private static final Diagnostic CREATE_ERROR = Diagnostic.error("assets", "Asset", "assets.asset.createFailed");
  private static final Diagnostic MODIFY_ERROR = Diagnostic.error("assets", "Asset", "assets.asset.modifyFailed");
  private static final Diagnostic DELETE_ERROR = Diagnostic.error("assets", "Asset", "assets.asset.deleteFailed");
  private static final Diagnostic SEARCH_ERROR = Diagnostic.error("assets", "Asset", "assets.asset.findFailed");

  private static final Logger logger = LoggerFactory.getLogger(AssetProxy.class);

  private AssetRemote assetRemote = null;
  private static AssetProxy assetProxy = null;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private AssetProxy() {
    try {
      ComponentJNDI componentJNDI = ComponentJNDI.getComponentJNDI("assets");
      String remoteName = componentJNDI.getDataRemoteName("Asset");
      assetRemote = componentJNDI.lookupRemote(remoteName);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not connect to the Home interfaces", e
        );
      }
    }
  }

  public static AssetProxy getAssetProxy() {
    if (assetProxy == null) {
      assetProxy = new AssetProxy();
    }
    return assetProxy;
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance:start
  public CrudsResult<AssetDetails> getDetails(ParameterContext<Long> idParameter) {
    try {
      return assetRemote.getDetails(idParameter);
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
      return (CrudsResult<T>) assetRemote.getProjection(projectionRefParameter);
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
      return (CrudsResult<DataRef>) assetRemote.resolveDataRef(dataRefParameter);
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

  public <S extends AssetFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter) {
    try {
      return assetRemote.find(searchParameter);
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
      return assetRemote.getName(idParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve name for assetId", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  public CrudsResult<DataRef> getId(ParameterContext<String> nameParameter) {
    try {
      return assetRemote.getId(nameParameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve id for assetName", e
        );
      }
      return CrudsResult.error(SEARCH_ERROR);
    }
  }

  /*========== Create/Modify CRUD methods ==========*/

  // anchor:crud:start
  public CrudsResult<DataRef> create(ParameterContext<AssetDetails> detailsParameter) {
    try {
      return assetRemote.create(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create Asset", re
        );
      }
      return CrudsResult.error(CREATE_ERROR);
    }
  }

  public CrudsResult<DataRef> modify(ParameterContext<AssetDetails> detailsParameter) {
    try {
      return assetRemote.modify(detailsParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not modify Asset", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter) {
    try {
      return assetRemote.createOrModify(projectionParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not create nor modify Asset", re
        );
      }
      return CrudsResult.error(MODIFY_ERROR);
    }
  }

  public CrudsResult<Void> delete(ParameterContext<Long> idParameter) {
    try {
      return assetRemote.delete(idParameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete Asset", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }

  public CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> parameter) {
    try {
      return assetRemote.deleteByDataRef(parameter);
    } catch (RuntimeException re) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not delete Asset", re
        );
      }
      return CrudsResult.error(DELETE_ERROR);
    }
  }
  // anchor:crud:end

  // @anchor:methods:start

  /*========== Command Handling ==========*/

  public <T extends ICommand> CommandResult perform(ParameterContext<T> commandParameter) {
    try {
      return assetRemote.perform(commandParameter);
    } catch (Exception e) {
      T command = commandParameter.getValue();
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not execute command " + command.getClass(), e
        );
      }
      return CommandResult.error(command.getCommandId(), "Could not execute command");
    }
  }
  // @anchor:methods:end

  /*=========== Customisation ===========*/

  // anchor:custom-methods:start
  public CrudsResult<AssetChunk> getAssetChunk(ParameterContext<AssetChunkRef> refParam) {
    try {
      return assetRemote.getAssetChunk(refParam);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not retrieve chunk ", e
        );
      }
      return CrudsResult.error(UNKNOWN_ERROR);
    }
  }

  public CrudsResult<Void> uploadChunk(ParameterContext<AssetChunk> parameter) {
    try {
      return assetRemote.uploadChunk(parameter);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "Could not upload chunk ", e
        );
      }
      return CrudsResult.error(UNKNOWN_ERROR);
    }
  }
  // anchor:custom-methods:end
}

