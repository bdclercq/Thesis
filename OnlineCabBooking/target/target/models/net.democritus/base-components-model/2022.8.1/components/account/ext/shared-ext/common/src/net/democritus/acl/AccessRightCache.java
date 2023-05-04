package net.democritus.acl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// @feature:authorization
public class AccessRightCache implements Serializable {

  /*
   * Maps access right queries to the correct access rights in the following way, allowing for cache misses:
   *    component + element + functionality => accessRightCacheEntry
   */
  private Map<String, AccessRightCacheEntry> cache = new HashMap<String, AccessRightCacheEntry>();

  public AccessRightCacheResult getAccessRight(DataAccessQuery accessQuery) {
    String key = getKey(accessQuery);
    if (cache.containsKey(key)) {
      AccessRightCacheEntry entry = cache.get(key);
      return AccessRightCacheResult.authorization(entry.isAllowed(accessQuery.getFunctionality()), entry.getAccessRights());
    } else {
      return AccessRightCacheResult.notFound();
    }
  }

  public void registerAccessRights(DataAccessQuery accessQuery, ProfileAccessRights accessRights) {
    String keyNoComponent = getKey(accessQuery.getElement());
    cache.put(keyNoComponent, new AccessRightCacheEntry(accessRights));

    if (accessQuery.getComponent() != null) {
      String keyWComponent = getKey(accessQuery.getComponent(), accessQuery.getElement());
      cache.put(keyWComponent, new AccessRightCacheEntry(accessRights));
    }
  }

  private String getKey(DataAccessQuery dataAccessQuery) {
    String component = dataAccessQuery.getComponent();
    if (component == null) {
      return getKey(dataAccessQuery.getElement());
    } else {
      return getKey(component, dataAccessQuery.getElement());
    }
  }

  private String getKey(String component, String element) {
    return String.format("%s.%s", component, element);
  }

  private String getKey(String element) {
    return String.format("%s", element);
  }

}
