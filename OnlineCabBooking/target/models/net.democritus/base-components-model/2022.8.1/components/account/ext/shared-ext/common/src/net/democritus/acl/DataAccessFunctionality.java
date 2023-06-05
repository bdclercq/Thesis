package net.democritus.acl;

// @feature:authorization
public enum DataAccessFunctionality {
  /**
   * Wildcard indicating all rights. The wildcard has a different effect depending on the {@link AccessRightStrategy}
   * used. If the strategy allows all by default, the wildcard can be used to disable all rights. If the strategy denies
   * all by default, the wildcard can be used to enable all rights.
   */
  ALL("all"),
  MODIFY("modify"),
  CREATE("create"),
  DELETE("delete"),
  VIEW("status"),
  //ENTRY is added for backwards compatibility issues. Should not be used in conjunction with modify and create
  @Deprecated
  ENTRY("entry"),
  NOT_MAPPED("<not mapped>");

  private static final DataAccessFunctionality[] STANDARD_FUNCTIONALITIES = {CREATE, MODIFY, DELETE, VIEW};

  private final String functionality;

  DataAccessFunctionality(String functionality) {
    this.functionality = functionality;
  }

  public String getFunctionality() {
    return functionality;
  }

  public boolean isSameAs(String status) {
    return this.functionality.equals(status);
  }

  public static DataAccessFunctionality getDataAccessFunctionality(String status) {
    for (DataAccessFunctionality transactionState : values()) {
      if (transactionState.isSameAs(status)) {
        return transactionState;
      }
    }
    return NOT_MAPPED;
  }

  public static DataAccessFunctionality[] getStandardFunctionalities() {
    return STANDARD_FUNCTIONALITIES;
  }
}