package net.democritus.assets;

import net.democritus.sys.DataRef;

import java.io.Serializable;

public class AssetChunk implements Serializable {

  private DataRef asset;
  private byte[] content;
  private int position;
  private boolean next;

  public DataRef getAsset() {
    return asset;
  }

  public void setAsset(DataRef asset) {
    this.asset = asset;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public boolean hasNext() {
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }

}
