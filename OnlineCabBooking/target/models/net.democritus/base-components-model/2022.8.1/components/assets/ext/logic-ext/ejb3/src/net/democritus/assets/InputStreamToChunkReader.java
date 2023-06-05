package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.Diagnostic;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamToChunkReader {
  private static final Logger logger = LoggerFactory.getLogger(InputStreamToChunkReader.class);

  private static final Diagnostic NO_CHUNK = Diagnostic.error("assets", "fileAsset", "assets.fileAsset.noMoreChunks");

  private final InputStream inputStream;

  public InputStreamToChunkReader(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public CrudsResult<AssetChunk> readChunk(AssetChunkRef chunkRef, int chunkSize) throws IOException {
    long offset = (long) chunkSize * (long) chunkRef.getPosition();
    byte[] chunk = new byte[chunkSize];
    boolean hasNext;

    long skipped = inputStream.skip(offset);
    if (skipped != offset) {
      logger.warn("InputStream.skip skipped " + skipped + " bytes instead of " + offset);
    }

    int length = inputStream.read(chunk);
    if (length == -1) {
      return CrudsResult.error(NO_CHUNK);
    } else if (length < chunkSize) {
      byte[] tmp = new byte[length];
      System.arraycopy(chunk, 0, tmp, 0, length);
      chunk = tmp;
    }
    hasNext = inputStream.read() != -1;

    AssetChunk assetChunk = new AssetChunk();
    assetChunk.setAsset(chunkRef.getAsset());
    assetChunk.setPosition(chunkRef.getPosition());
    assetChunk.setContent(chunk);
    assetChunk.setNext(hasNext);

    return CrudsResult.success(assetChunk);
  }

}
