package net.democritus.assets;

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
import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

// @anchor:imports:start
// @anchor:imports:end

// anchor:imports:start
import net.democritus.assets.FileAssetAgent;
import net.democritus.assets.InternalAssetAgent;
import net.democritus.assets.ExternalAssetAgent;
// anchor:imports:end

// anchor:custom-imports:start
import net.democritus.sys.ParamTargetValueAgent;

import java.io.InputStream;
// anchor:custom-imports:end

public class AssetAgent implements AssetAgentIf {

  private static final Logger logger = LoggerFactory.getLogger(AssetAgent.class);

  private static final AssetProxy assetProxy = AssetProxy.getAssetProxy();

  private final UserContext mUserContext;
  private final Context mContext;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  /*========== Singleton constructor and access ==========*/

  private AssetAgent(Context context, UserContext userContext) {
    this.mContext = context;
    this.mUserContext = userContext;
  }

  public static AssetAgent getAssetAgent(Context context) {
    UserContext userContext = context
        .getContext(UserContext.class)
        .orElse(UserContext.NO_USER_CONTEXT);
    return new AssetAgent(context, userContext);
  }

  @Deprecated
  public static AssetAgent getAssetAgent(UserContext userContext) {
    Context context = Context.from(userContext);
    return new AssetAgent(context, userContext);
  }

  @Deprecated
  public static AssetAgent getAssetAgent() {
    return getAssetAgent(UserContext.NO_USER_CONTEXT);
  }

  /*========== Master/Detail info methods ==========*/

  // anchor:instance-methods:start
  public SearchResult<DataRef> findAllDataRefs() {
    AssetFindAllAssetsDetails finder = new AssetFindAllAssetsDetails();
    return assetProxy.find(createParameter(SearchDetails.fetchAllDataRef(finder)));
  }

  public SearchResult<AssetInfo> findAllInfos() {
    AssetFindAllAssetsDetails finder = new AssetFindAllAssetsDetails();
    return assetProxy.find(createParameter(SearchDetails.fetchAll(finder)));
  }

  public DataRef getDataRef(Long assetId) {
    ProjectionRef projectionRef = new ProjectionRef("dataRef", DataRef.withId(assetId));
    CrudsResult<DataRef> result = assetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return EMPTY_DATA_REF;
    }
  }

  public AssetInfo getInfo(Long assetId) {
    ProjectionRef projectionRef = new ProjectionRef("info", DataRef.withId(assetId));
    CrudsResult<AssetInfo> result = assetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new AssetInfo();
    }
  }

  public AssetDetails getDetails_old(Long assetId) {
    ProjectionRef projectionRef = new ProjectionRef("details", DataRef.withId(assetId));
    CrudsResult<AssetDetails> result = assetProxy.getProjection(createParameter(projectionRef));
    if (result.isSuccess()) {
      return result.getValue();
    } else {
      return new AssetDetails();
    }
  }

  public CrudsResult<AssetDetails> getDetails(Long assetId) {
    return assetProxy.getDetails(createParameter(assetId));
  }

  public CrudsResult<AssetDetails> getDetails(DataRef dataRef) {
    return assetProxy.getDetails(createParameter(dataRef.getId()));
  }

  /**
   * Fetch the target projection for the target instance
   *
   * @param projectionRef should contain the target projection name and a dataRef pointing to the target instance
   * @return a success result with the target projection or an error result
   */
  public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
    return assetProxy.getProjection(createParameter(projectionRef));
  }

  /**
   * Check if the instance referenced by the dataRef exists, and if so, return a new DataRef with more information
   *
   * @param dataRef a dataRef containing enough information to find the instance
   * @return success with new dataRef if the instance could be found
   */
  public CrudsResult<DataRef> resolveDataRef(DataRef dataRef) {
    return assetProxy.resolveDataRef(createParameter(dataRef));
  }
  // anchor:instance-methods:end

  /*========== Name/Id resolution methods ==========*/

  // anchor:name-id-resolution:start
  public String getName(Long assetId) {
    CrudsResult<String> result = assetProxy.getName(createParameter(assetId));
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

  public Long getId(String assetName) {
    CrudsResult<DataRef> result = assetProxy.getId(createParameter(assetName));
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
    return assetProxy.getId(createParameter(name));
  }
  // anchor:name-id-resolution:end

  /*========== CRUD methods ==========*/

  // anchor:crud-methods:start
  /**
   * Create a new Asset instance
   *
   * @param details a details representation of the new instance
   * @return success if the instance was created, containing a dataRef pointing to the new instance
   */
  public CrudsResult<DataRef> create(AssetDetails details) {
    return assetProxy.create(createParameter(details));
  }

  /**
   * Modify an existing Asset instance
   *
   * @param details a details representation of the instance
   * @return success if the instance was modified, containing a dataRef pointing to the modified instance
   */
  public CrudsResult<DataRef> modify(AssetDetails details) {
    return assetProxy.modify(createParameter(details));
  }

  /**
   * Create a Asset instance or modify it if it already exists
   *
   * @param projection a representation of the instance
   * @return success if the instance was modified or created, containing a dataRef pointing to the instance
   */
  public <P> CrudsResult<DataRef> createOrModify(P projection) {
    return assetProxy.createOrModify(createParameter(projection));
  }

  /**
   * Delete target Asset instance by id.
   *
   * @deprecated Use {@link AssetAgent#delete(net.democritus.sys.DataRef))} instead
   * @param oid the database id of the target instance
   * @return success if the instance was deleted
   */
  @Deprecated
  public CrudsResult<Void> delete(Long oid) {
    return assetProxy.delete(createParameter(oid));
  }

  /**
   * Delete target Asset instance.
   * If id is not defined, there will be an attempt to find the instance based on the name or functional key.
   *
   * @param target a dataRef to the target instance
   * @return success if the instance was deleted
   */
  public CrudsResult<Void> delete(DataRef target) {
    return assetProxy.deleteByDataRef(createParameter(target));
  }
  // anchor:crud-methods:end

  /*========== Finders ==========*/

  /**
   * Find instances of the Asset element
   *
   * @param searchDetails a searchDetails containing a finder details object and other search parameters
   * @return success with the instances matching the finder or an error result in case of a technical failure
   */
  public <S extends AssetFindDetails, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
    return assetProxy.find(createParameter(searchDetails));
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

  /*========= Handle commands ========*/

  /**
   * Perform target command
   *
   * @param command a command object representing the command
   * @return success if the command was executed successfully
   */
  public <T extends ICommand> CommandResult perform(T command) {
    return assetProxy.perform(createParameter(command));
  }

  // @anchor:methods:start
  // @anchor:methods:end

  /*========== Custom methods ========*/

  // anchor:custom-methods:start
  public CrudsResult<AssetChunk> getAssetChunk(AssetChunkRef reference) {
    return assetProxy.getAssetChunk(createParameter(reference));
  }

  public CrudsResult<DataRef> uploadAsset(AssetStream assetStream) {
    AssetDetails assetDetails = assetStream.getAssetDetails();
    InputStream inputStream = assetStream.getInputStream();

    CrudsResult<DataRef> createResult = create(assetDetails);
    if (createResult.isError()) {
      return createResult.convertError();
    }
    DataRef assetRef = createResult.getValue();
    assetDetails.setId(assetRef.getId());

    return uploadStream(assetRef, inputStream);
  }

  private CrudsResult<DataRef> uploadStream(DataRef assetRef, InputStream inputStream) {
    int chunkSize = getChunkSize();

    AssetChunkReader assetChunkReader = new AssetChunkReader(assetRef, inputStream, chunkSize);
    try {
      while (assetChunkReader.hasNext()) {
        AssetChunk assetChunk = assetChunkReader.getNext();
        CrudsResult<Void> uploadResult = assetProxy.uploadChunk(createParameter(assetChunk));

        if (uploadResult.isError()) {
          return uploadResult.convertError();
        }
      }
      return CrudsResult.success(assetRef);
    } finally {
      assetChunkReader.close();
    }
  }

  public CrudsResult<AssetStream> getAssetStream(DataRef assetRef) {
    CrudsResult<AssetDetails> crudsResult = getDetails(assetRef);
    if (crudsResult.isError()) {
      return crudsResult.convertError();
    }
    AssetDetails assetDetails = crudsResult.getValue();

    InputStream inputStream = new AssetChunkInputStream(this, assetRef);

    AssetStream assetStream = new AssetStream();
    assetStream.setAssetDetails(assetDetails);
    assetStream.setInputStream(inputStream);

    return CrudsResult.success(assetStream);
  }

  private int getChunkSize() {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(mContext);
    String chunkSize = paramTargetValueAgent.getParamTargetValue("assetChunkSize", "default");
    if (chunkSize == null || chunkSize.isEmpty()) {
      return 1024 * 128;
    } else {
      return Integer.parseInt(chunkSize);
    }
  }
  // anchor:custom-methods:end
}

