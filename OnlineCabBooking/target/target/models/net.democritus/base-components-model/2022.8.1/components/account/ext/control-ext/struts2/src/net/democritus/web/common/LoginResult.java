package net.democritus.web.common;

import com.opensymphony.xwork2.Action;

// @feature:authentication
public class LoginResult {

  public static final String SUCCESS = Action.SUCCESS;
  public static final String FAILED = Action.LOGIN;
  public static final String DUPLICATE = "duplicate";
  public static final String REDIRECT = "redirect";

}