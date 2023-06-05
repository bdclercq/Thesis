package net.democritus.assets;

import java.io.InputStream;

public class AssetStream {

  private AssetDetails assetDetails;
  private InputStream inputStream;

  public AssetDetails getAssetDetails() {
    return assetDetails;
  }

  public void setAssetDetails(AssetDetails assetDetails) {
    this.assetDetails = assetDetails;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

}