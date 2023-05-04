package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.Diagnostic;
import net.democritus.sys.ParameterContext;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.search.SearchDetails;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

class InternalAssetChunkRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger(InternalAssetChunkRetriever.class);
  private static final Diagnostic NO_CHUNK = Diagnostic.error("assets", "internalAsset", "assets.internalAsset.nochunk");

  CrudsResult<AssetChunk> retrieve(ParameterContext<AssetChunkRef> refParam, DataRef internalAsset) {
    InternalAssetChunkLocalAgent internalAssetChunkAgent = getInternalAssetChunkAgent(refParam.getUserContext());
    AssetChunkRef chunkRef = refParam.getValue();

    InternalAssetChunkFindByInternalAssetEq_PositionEqDetails finder = new InternalAssetChunkFindByInternalAssetEq_PositionEqDetails();
    finder.setInternalAsset(internalAsset);
    finder.setPosition(chunkRef.getPosition());

    SearchDetails<InternalAssetChunkFindByInternalAssetEq_PositionEqDetails> searchDetails = new SearchDetails<InternalAssetChunkFindByInternalAssetEq_PositionEqDetails>(finder);
    searchDetails.setProjection("details");

    SearchResult<InternalAssetChunkDetails> searchResult = internalAssetChunkAgent.find(searchDetails);

    if (searchResult.isError()) {
      LOGGER.error("Could not retrieve chunk");
      return CrudsResult.error(
              searchResult.getDiagnostics()
      );
    }

    if (searchResult.getFirst().isEmpty()) {
      LOGGER.error("Could not find chunk with position " + chunkRef.getPosition());
      return CrudsResult.error(NO_CHUNK);
    }

    InternalAssetChunkDetails internalAssetChunkDetails = searchResult.getFirst().getValue();

    AssetChunk assetChunk = new AssetChunk();
    assetChunk.setAsset(chunkRef.getAsset());
    assetChunk.setPosition(chunkRef.getPosition());
    assetChunk.setContent(internalAssetChunkDetails.getContent());
    assetChunk.setNext(!internalAssetChunkDetails.getIsLast());

    return CrudsResult.success(assetChunk);
  }

  private InternalAssetChunkLocalAgent getInternalAssetChunkAgent(UserContext userContext) {
    return InternalAssetChunkLocalAgent.getInternalAssetChunkAgent(userContext);
  }

}
