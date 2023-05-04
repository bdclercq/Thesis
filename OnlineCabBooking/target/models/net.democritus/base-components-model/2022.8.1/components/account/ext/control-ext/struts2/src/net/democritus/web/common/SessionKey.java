package net.democritus.web.common;

// @feature:authorization
public enum SessionKey {
  DATA_ACCESS_RIGHTS_MAP_NAME("dataAccessRightsMap"),
  TASK_ACCESS_RIGHTS_MAP_NAME("taskAccessRightsMap"),
  USERCONTEXT("userContext"),
  CONTEXT("context"),
  ACCESS_INFO_KEY("AccessInfoRetrieverActionImpl");

  private final String key;

  SessionKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
