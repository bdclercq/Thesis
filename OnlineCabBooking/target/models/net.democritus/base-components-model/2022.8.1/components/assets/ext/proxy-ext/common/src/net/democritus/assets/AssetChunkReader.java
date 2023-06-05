package net.democritus.assets;

import net.democritus.sys.DataRef;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class AssetChunkReader {

  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.assets.AssetChunkReader");

  private final DataRef asset;
  private final InputStream inputStream;
  private final int chunkSize;
  private int position;

  private byte[] nextChunk;

  public AssetChunkReader(DataRef asset, InputStream inputStream, int chunkSize) {
    this.asset = asset;
    this.inputStream = inputStream;
    this.chunkSize = chunkSize;
    position = 0;

    readChunk();
  }

  public AssetChunk getNext() {
    AssetChunk assetChunk = new AssetChunk();
    assetChunk.setAsset(asset);
    assetChunk.setPosition(position++);
    assetChunk.setContent(nextChunk);

    readChunk();
    assetChunk.setNext(hasNext());

    return assetChunk;
  }

  public boolean hasNext() {
    return nextChunk != null;
  }

  private void readChunk() {
    try {
      byte[] chunk = new byte[chunkSize];
      int read = inputStream.read(chunk);

      if (read == chunkSize) {
        nextChunk = chunk;
      } else if (read >= 0) {
        nextChunk = new byte[read];
        System.arraycopy(chunk, 0, nextChunk, 0, read);
      } else {
        nextChunk = null;
      }
    } catch (IOException e) {
      LOGGER.error("Could not read chunk", e);
      nextChunk = null;
    }
  }

  public void close() {
    try {
      inputStream.close();
    } catch (IOException e) {
      LOGGER.error("Could not close inputstream", e);
    }
  }

}