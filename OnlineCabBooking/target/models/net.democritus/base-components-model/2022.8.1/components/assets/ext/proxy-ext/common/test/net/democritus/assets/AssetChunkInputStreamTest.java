package net.democritus.assets;

import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;
import net.democritus.sys.SearchDataRef;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AssetChunkInputStreamTest {

  private static final byte[][] TEST_INPUT = {
    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
    {0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
    {0, 1, 2, 3, 4, 4, 3, 2, 1, 0},
    {-4, -3, -2, -1, 0, 1, 2, 3, 4, 5}
  };

  private static final byte[] EXPECTED_OUTPUT = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
      9, 8, 7, 6, 5, 4, 3, 2, 1, 0,
      0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
      0, 1, 2, 3, 4, 4, 3, 2, 1, 0,
      -4, -3, -2, -1, 0, 1, 2, 3, 4, 5
  };

  private static final DataRef ASSET = DataRef.withIdAndName(1L, "testAsset");

  @Test
  public void testInputStream() throws Exception {
    TestAssetAgent agent = new TestAssetAgent();
    AssetChunkInputStream inputStream = new AssetChunkInputStream(agent, ASSET);

    byte[] output = new byte[50];
    int res = inputStream.read(output);

    Assert.assertEquals(50, res);
    Assert.assertArrayEquals(
        "expected:\n" + Arrays.toString(EXPECTED_OUTPUT) + "\nbut received:\n" + Arrays.toString(output),
        EXPECTED_OUTPUT, output);
  }


  private static AssetChunk createAssetChunk(AssetChunkRef ref) {
    assert ASSET.equals(ref.getAsset());
    assert ref.getPosition() < 5;

    int position = ref.getPosition();
    byte[] chunk = TEST_INPUT[position];

    AssetChunk assetChunk = new AssetChunk();
    assetChunk.setContent(chunk);
    assetChunk.setPosition(position);
    assetChunk.setAsset(ref.getAsset());
    assetChunk.setNext(position < 4);

    return assetChunk;
  }


  public class TestAssetAgent implements AssetAgentIf {

    @Override
    public CrudsResult<AssetChunk> getAssetChunk(AssetChunkRef reference) {
      AssetChunk assetChunk = createAssetChunk(reference);
      return CrudsResult.success(assetChunk);
    }

    @Override
    public CrudsResult<DataRef> create(AssetDetails assetDetails) {
      return null;
    }

    @Override
    public CrudsResult<DataRef> modify(AssetDetails assetDetails) {
      return null;
    }

    @Override
    public <P> CrudsResult<DataRef> modifyWithProjection(P projection) {
      return null;
    }

    @Override
    public CrudsResult<Void> delete(Long id) {
      return null;
    }

    @Override
    public <T> CrudsResult<T> getProjection(ProjectionRef projectionRef) {
      return null;
    }

    @Override
    public CrudsResult<AssetDetails> getDetails(DataRef dataRef) {
      return null;
    }

    @Override
    public CrudsResult<AssetDetails> getDetails(Long assetId) {
      return null;
    }

    @Override
    public CrudsResult<DataRef> getDataRef(String name) {
      return null;
    }

    @Override
    public <S, T> SearchResult<T> find(SearchDetails<S> searchDetails) {
      return null;
    }

    @Override
    public SearchResult<DataRef> findAllDataRefs() {
      return null;
    }

    @Override
    public SearchResult<AssetInfo> findAllInfos() {
      return null;
    }

    @Override
    public SearchResult<DataRef> findAllDataRefsOrderedBy(String orderBy) {
      return null;
    }

    @Override
    public SearchResult<DataRef> findDataRefs(SearchDataRef searchDataRef) {
      return null;
    }

    @Override
    public SearchResult<AssetInfo> findInfos(SearchDataRef searchDataRef) {
      return null;
    }

    @Override
    public SearchResult<AssetDetails> findDetails(SearchDataRef searchDataRef) {
      return null;
    }


  }
}