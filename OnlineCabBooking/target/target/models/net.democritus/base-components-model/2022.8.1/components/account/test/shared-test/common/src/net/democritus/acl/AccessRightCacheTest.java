package net.democritus.acl;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessRightCacheTest {

  @Test
  public void shouldReturnNotFoundOnCacheMiss() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery accessQuery = accessQuery("account", "user", "view");

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(accessQuery);
    assertTrue(cacheEntry.isNotFound());
  }

  @Test
  public void shouldReturnAuthorizedIfAuthorized() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery accessQuery = accessQuery("account", "user", "view");

    DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.addAction("view", true);
    accessRightCache.registerAccessRights(accessQuery, dataAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(accessQuery);
    assertTrue(cacheEntry.isFound());
    assertTrue(cacheEntry.isAllowed());
  }

  @Test
  public void shouldReturnNotAuthorizedIfNotAuthorized() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery accessQuery = accessQuery("account", "user", "view");

    DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.addAction("view", false);
    accessRightCache.registerAccessRights(accessQuery, dataAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(accessQuery);
    assertTrue(cacheEntry.isFound());
    assertFalse(cacheEntry.isAllowed());
  }

  @Test
  public void shouldCacheAllAccessRights() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery viewAccessQuery = accessQuery("account", "user", "view");
    DataAccessQuery editAccessQuery = accessQuery("account", "user", "edit");

    DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.addAction("view", true);
    dataAccessRights.addAction("edit", true);
    accessRightCache.registerAccessRights(viewAccessQuery, dataAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(editAccessQuery);
    assertTrue(cacheEntry.isFound());
    assertTrue(cacheEntry.isAllowed());
  }

  @Test
  public void shouldReturnFullDataAccessRights() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery previousAccessQuery = accessQuery("account", "user", "view");
    DataAccessQuery accessQuery = accessQuery("account", "user", null);

    DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.addAction("view", true);
    dataAccessRights.addAction("edit", true);
    accessRightCache.registerAccessRights(previousAccessQuery, dataAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(accessQuery);
    assertTrue(cacheEntry.isFound());
    assertTrue(cacheEntry.getAccessRights().isCanView());
    assertTrue(cacheEntry.getAccessRights().isCanEdit());
  }

  @Test
  public void shouldCacheTaskAccessRights() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery viewAccessQuery = accessQuery("account", "authentication", "execute");

    TaskAccessRights taskAccessRights = new TaskAccessRights();
    taskAccessRights.setTaskName("account.authentication");
    taskAccessRights.setCanExecute(false);

    accessRightCache.registerAccessRights(viewAccessQuery, taskAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(viewAccessQuery);
    assertTrue(cacheEntry.isFound());
    assertFalse(cacheEntry.isAllowed());
  }

  @Test
  public void shouldFindAccessRightIfNoComponentPassed() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery accessQueryWithComponent = accessQuery("account", "user", "view");
    DataAccessQuery accessQueryWithoutComponent = accessQuery(null, "user", "view");

    DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.addAction("view", true);
    accessRightCache.registerAccessRights(accessQueryWithComponent, dataAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(accessQueryWithoutComponent);
    assertTrue(cacheEntry.isFound());
    assertTrue(cacheEntry.isAllowed());
  }

  @Test
  public void shouldNotConfuseDataElementsFromDifferentComponents() {
    AccessRightCache accessRightCache = new AccessRightCache();

    DataAccessQuery accountAccessQuery = accessQuery("account", "user", "view");
    DataAccessQuery bananaAccessQuery = accessQuery("banana", "user", "view");

    DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.addAction("view", true);
    accessRightCache.registerAccessRights(accountAccessQuery, dataAccessRights);

    AccessRightCacheResult cacheEntry = accessRightCache.getAccessRight(bananaAccessQuery);
    assertTrue(cacheEntry.isNotFound());
  }

  private DataAccessQuery accessQuery(String component, String element, String functionality) {
    return new AccessQueryBuilder()
        .setComponent(component)
        .setElement(element)
        .setFunctionality(functionality)
        .createAccessQuery();
  }

}