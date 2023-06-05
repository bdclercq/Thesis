package net.democritus.acl;

import java.io.Serializable;

// @feature:authorization
class AccessRightCacheResult implements Serializable {

  private final boolean isAllowed;
  private final boolean isFound;
  private final ProfileAccessRights accessRights;

  private AccessRightCacheResult(boolean isAllowed, boolean isFound, ProfileAccessRights accessRights) {
    this.isAllowed = isAllowed;
    this.isFound = isFound;
    this.accessRights = accessRights;
  }

  static AccessRightCacheResult notFound() {
    return new AccessRightCacheResult(false, false, null);
  }

  static AccessRightCacheResult authorization(boolean isAllowed, ProfileAccessRights accessRights) {
    return new AccessRightCacheResult(isAllowed, true, accessRights);
  }

  public boolean isAllowed() {
    return isAllowed;
  }

  public boolean isFound() {
    return isFound;
  }

  public boolean isNotFound() {
    return !isFound;
  }

  public ProfileAccessRights getAccessRights() {
    return accessRights;
  }

}
