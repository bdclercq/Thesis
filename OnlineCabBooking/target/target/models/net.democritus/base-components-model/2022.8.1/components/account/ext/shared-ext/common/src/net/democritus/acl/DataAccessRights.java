package net.democritus.acl;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Extension on ProfileAccessRight to add a create parameter and wildcard support.
 */

// @feature:authorization
public class DataAccessRights extends ProfileAccessRights implements Serializable {

  private static final long serialVersionUID = 1L;
  private final AccessRightStrategy accessRightStrategy;
  private boolean canAll;
  private boolean canCreate;
  private final HashMap<String, Boolean> actions = new HashMap<>();

  /**
   * Returns a DataAccessRights object which uses allows all rights by default.
   *
   * @deprecated This constructor exists for backwards compatibility. Allowing all permissions by default is not
   *             considered to be a good security practice. As such you should explicitly define this strategy if
   *             needed, by passing a {@link AllowDefaultAccessRightStrategy} instance to the constructor of
   *             {@link DataAccessRights}. E.g.: {@code new DataAccessRights(new AllowDefaultAccessRightStrategy())}.
   */
  @Deprecated
  public DataAccessRights() {
    this(new AllowDefaultAccessRightStrategy());
  }

  public DataAccessRights(AccessRightStrategy accessRightStrategy) {
    this.accessRightStrategy = accessRightStrategy;
    setCanAll(accessRightStrategy.getDefaultRight());
    setCanCreate(accessRightStrategy.getDefaultRight());
    setCanDelete(accessRightStrategy.getDefaultRight());
    setCanEdit(accessRightStrategy.getDefaultRight());
    setCanView(accessRightStrategy.getDefaultRight());
  }

  public boolean isCanAll() {
    return canAll;
  }

  public void setCanAll(boolean canAll) {
    this.canAll = canAll;
  }

  public boolean isCanCreate() {
    return canCreate;
  }

  public void setCanCreate(boolean canCreate) {
    this.canCreate = canCreate;
  }

  public void addAction(String name, Boolean isAllowed) {
    boolean allowed = Boolean.TRUE.equals(isAllowed);
    DataAccessFunctionality functionality = DataAccessFunctionality.getDataAccessFunctionality(name);
    switch (functionality) {
      case ALL:
        setCanAll(allowed);

        // The front-end accesses the properties of the object rather than calling the isAllowed() method
        setCanView(accessRightStrategy.combineRights(isCanAll(), isCanView()));
        setCanCreate(accessRightStrategy.combineRights(isCanAll(), isCanCreate()));
        setCanEdit(accessRightStrategy.combineRights(isCanAll(), isCanEdit()));
        setCanDelete(accessRightStrategy.combineRights(isCanAll(), isCanDelete()));
        break;
      case VIEW:
        setCanView(accessRightStrategy.combineRights(isCanAll(), allowed));
        break;
      case CREATE:
        setCanCreate(accessRightStrategy.combineRights(isCanAll(), allowed));
        break;
      case DELETE:
        setCanDelete(accessRightStrategy.combineRights(isCanAll(), allowed));
        break;
      case MODIFY:
        setCanEdit(accessRightStrategy.combineRights(isCanAll(), allowed));
        break;
      case ENTRY:
        setCanCreate(accessRightStrategy.combineRights(isCanAll(), allowed));
        setCanEdit(accessRightStrategy.combineRights(isCanAll(), allowed));
        break;
      case NOT_MAPPED:
        actions.put(name, accessRightStrategy.combineRights(isCanAll(), allowed));
        break;
    }
  }

  public boolean isAllowed(String name) {
    boolean allowed;
    DataAccessFunctionality functionality = DataAccessFunctionality.getDataAccessFunctionality(name);
    switch (functionality) {
      case VIEW:
        allowed = isCanView();
        break;
      case CREATE:
        allowed = isCanCreate();
        break;
      case DELETE:
        allowed = isCanDelete();
        break;
      case MODIFY:
        allowed = isCanEdit();
        break;
      default:
        Boolean result = actions.get(name);
        allowed = result == null ? accessRightStrategy.getDefaultRight() : result;
        break;
    }
    return accessRightStrategy.combineRights(isCanAll(), allowed);
  }

  public boolean isAllowed(DataAccessFunctionality functionality) {
    boolean allowed;
    switch (functionality) {
      case VIEW:
        allowed = isCanView();
        break;
      case CREATE:
        allowed = isCanCreate();
        break;
      case DELETE:
        allowed = isCanDelete();
        break;
      case MODIFY:
        allowed = isCanEdit();
        break;
      default:
        allowed = accessRightStrategy.getDefaultRight();
        break;
    }
    return accessRightStrategy.combineRights(isCanAll(), allowed);
  }

  public HashMap<String, Boolean> getActions() {
    return actions;
  }

  /**
   * Returns a DataAccessRights object which denies all rights by default.
   *
   * @deprecated This method returns an instance of the deprecated {@link WhiteListingDataAccessRights} class. This can
   *             now be replaced by passing an {@link DenyDefaultAccessRightStrategy} instance to the constructor of
   *             {@link DataAccessRights}. E.g.: {@code new DataAccessRights(new DenyDefaultAccessRightStrategy())}.
   */
  @Deprecated
  public static DataAccessRights noAccessRights() {
    return new WhiteListingDataAccessRights();
  }

}