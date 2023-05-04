package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ParameterContext;

public interface CustomAssetImplementation {

  CrudsResult<AssetChunk> getAssetChunk(ParameterContext<AssetChunkRef> assetChunkRefParameter);
  CrudsResult<Void> uploadChunk(ParameterContext<AssetChunk> assetChunkParameter);
  CrudsResult<Void> delete(ParameterContext<DataRef> assetDataRefParameter);

}