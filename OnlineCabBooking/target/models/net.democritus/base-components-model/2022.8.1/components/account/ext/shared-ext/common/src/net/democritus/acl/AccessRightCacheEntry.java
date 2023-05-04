package net.democritus.acl;

import java.io.Serializable;

// @feature:authorization
class AccessRightCacheEntry implements Serializable {

  private final ProfileAccessRights accessRights;

  public AccessRightCacheEntry(ProfileAccessRights accessRights) {
    this.accessRights = accessRights;
  }

  public ProfileAccessRights getAccessRights() {
    return accessRights;
  }

  public boolean isAllowed(String action) {
    if (accessRights instanceof DataAccessRights) {
      return ((DataAccessRights) accessRights).isAllowed(action);
    } else if (accessRights instanceof TaskAccessRights) {
      return ((TaskAccessRights) accessRights).isCanExecute();
    } else {
      return false;
    }
  }

}
