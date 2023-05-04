// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.assets;

public enum AssetType {
  FILE,
  INTERNAL,
  REMOTE
  // @anchor:types:start
  , 
  // @anchor:types:end
  // anchor:custom-types:start
  // anchor:custom-types:end
  ;

  public boolean isDefaultType() {
    switch (this) {
      case FILE:
      case INTERNAL:
      case REMOTE:
        return true;
      default:
        return false;
    }
  }

}
