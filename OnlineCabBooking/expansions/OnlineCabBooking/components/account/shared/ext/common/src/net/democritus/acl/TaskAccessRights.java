package net.democritus.acl;

import java.io.Serializable;

/**
 * Extension on ProfileAccessRight for task permissions.
 */

// @feature:authorization
public class TaskAccessRights extends ProfileAccessRights implements Serializable {

  private final AccessRightStrategy accessRightStrategy;
  private String taskName;
  private boolean canExecute;

  /**
   * Returns a TaskAccessRights object which uses allows all rights by default.
   *
   * @deprecated This constructor exists for backwards compatibility. Allowing all permissions by default is not
   *             considered to be a good security practice. As such you should explicitly define this strategy if
   *             needed, by passing a {@link AllowDefaultAccessRightStrategy} instance to the constructor of
   *             {@link TaskAccessRights}. E.g.: {@code new DataAccessRights(new AllowDefaultAccessRightStrategy())}.
   */
  @Deprecated
  public TaskAccessRights() {
    this(new AllowDefaultAccessRightStrategy());
  }

  public TaskAccessRights(AccessRightStrategy accessRightStrategy) {
    this.accessRightStrategy = accessRightStrategy;
    setCanExecute(accessRightStrategy.getDefaultRight());
    setCanDelete(accessRightStrategy.getDefaultRight());
    setCanEdit(accessRightStrategy.getDefaultRight());
    setCanView(accessRightStrategy.getDefaultRight());
  }

  public boolean isCanExecute() {
    return canExecute;
  }

  public void setCanExecute(boolean canExecute) {
    this.canExecute = canExecute;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public static TaskAccessRights noAccessRights() {
    return new TaskAccessRights(new DenyDefaultAccessRightStrategy());
  }

}
