package net.democritus.usr;

import java.io.Serializable;

// @feature:authentication
public class LoginInformation implements Serializable {

  private UserDetails user;
  private String userPageUrl;
  private String accountPageUrl;
  private boolean isAdmin;

  public UserDetails getUser() {
    return user;
  }

  public void setUser(UserDetails user) {
    this.user = user;
  }

  public String getUserPageUrl() {
    return userPageUrl;
  }

  public void setUserPageUrl(String userPageUrl) {
    this.userPageUrl = userPageUrl;
  }

  public String getAccountPageUrl() {
    return accountPageUrl;
  }

  public void setAccountPageUrl(String accountPageUrl) {
    this.accountPageUrl = accountPageUrl;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

}
