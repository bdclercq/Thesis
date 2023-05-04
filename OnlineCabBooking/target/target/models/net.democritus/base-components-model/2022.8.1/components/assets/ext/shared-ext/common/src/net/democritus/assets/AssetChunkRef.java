package net.democritus.assets;

import net.democritus.sys.DataRef;

import java.io.Serializable;

public class AssetChunkRef implements Serializable {

  private DataRef asset;
  private int position;

  public DataRef getAsset() {
    return asset;
  }

  public void setAsset(DataRef asset) {
    this.asset = asset;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AssetChunkRef that = (AssetChunkRef) o;

    if (position != that.position) return false;
    return asset != null ? asset.getId().equals(that.asset.getId()) : that.asset == null;
  }

  @Override
  public int hashCode() {
    int result = asset != null ? asset.getId().hashCode() : 0;
    result = 31 * result + position;
    return result;
  }

}
