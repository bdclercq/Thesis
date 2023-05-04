package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

class InternalAssetChunkUploader {

  private static final Logger LOGGER = LoggerFactory.getLogger(net.democritus.assets.InternalAssetChunkUploader.class);

  CrudsResult<Void> upload(ParameterContext<AssetChunk> chunkParam, InternalAssetDetails internalAsset) {
    InternalAssetChunkLocalAgent internalAssetChunkAgent = getInternalAssetChunkAgent(chunkParam);
    AssetChunk assetChunk = chunkParam.getValue();

    byte[] chunk = assetChunk.getContent();

    InternalAssetChunkDetails details = new InternalAssetChunkDetails();
    details.setContent(chunk);
    details.setByteSize(chunk.length);
    details.setInternalAsset(internalAsset.getDataRef());
    details.setPosition(assetChunk.getPosition());
    details.setIsLast(!assetChunk.hasNext());

    CrudsResult<DataRef> createResult = internalAssetChunkAgent.create(details);
    if (createResult.isSuccess()) {
      return CrudsResult.success();
    } else {
      LOGGER.error("Error while creating internal asset chunk");
      return createResult.convertError();
    }
  }

  private InternalAssetChunkLocalAgent getInternalAssetChunkAgent(ParameterContext<AssetChunk> chunkParam) {
    return InternalAssetChunkLocalAgent.getInternalAssetChunkAgent(chunkParam.getUserContext());
  }

}
