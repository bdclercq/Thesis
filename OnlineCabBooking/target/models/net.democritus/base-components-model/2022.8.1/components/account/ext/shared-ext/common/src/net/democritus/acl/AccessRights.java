package net.democritus.acl;

// @feature:authorization
public class AccessRights {

  private DataAccessRights dataAccessRights;
  private TaskAccessRights taskAccessRights;

  public DataAccessRights getDataAccessRights() {
    return dataAccessRights;
  }

  public void setDataAccessRights(DataAccessRights dataAccessRights) {
    this.dataAccessRights = dataAccessRights;
  }

  public TaskAccessRights getTaskAccessRights() {
    return taskAccessRights;
  }

  public void setTaskAccessRights(TaskAccessRights taskAccessRights) {
    this.taskAccessRights = taskAccessRights;
  }

}
