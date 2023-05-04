package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;

import java.io.IOException;
import java.io.InputStream;

public class AssetChunkInputStream extends InputStream {

  private final AssetAgentIf assetAgent;
  private final DataRef asset;

  private int chunkPosition;
  private int index;
  private byte[] chunk;
  private boolean isLastChunk;

  public AssetChunkInputStream(AssetAgentIf assetAgent, DataRef asset) {
    this.assetAgent = assetAgent;
    this.asset = asset;

    index = 0;
    chunkPosition = 0;
    chunk = new byte[0];
    isLastChunk = false;
  }

  @Override
  public int read() throws IOException {
    if (chunk.length <= index) {
      if (isLastChunk) {
        return -1;
      } else {
        loadNextChunk();
      }
    }

    byte b = chunk[index++];
    return ((int) b + 256) % 256; // negative bytes are represented as 256+x where x is the negative value (e.g. -5 becomes 256+(-5)=251)
  }

  private void loadNextChunk() throws IOException {
    AssetChunkRef assetChunkRef = getNextChunkReference();
    CrudsResult<AssetChunk> chunkResult = assetAgent.getAssetChunk(assetChunkRef);

    if (chunkResult.isError()) {
      throw new IOException("Failed to retrieve chunk");
    }

    AssetChunk assetChunk = chunkResult.getValue();
    chunk = assetChunk.getContent();
    index = 0;

    if (!assetChunk.hasNext()) {
      isLastChunk = true;
    }
  }

  private AssetChunkRef getNextChunkReference() {
    AssetChunkRef assetChunkRef = new AssetChunkRef();
    assetChunkRef.setAsset(asset);
    assetChunkRef.setPosition(chunkPosition++);
    return assetChunkRef;
  }

}