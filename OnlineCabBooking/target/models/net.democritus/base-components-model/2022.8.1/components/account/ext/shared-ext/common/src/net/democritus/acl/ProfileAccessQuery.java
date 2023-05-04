package net.democritus.acl;

import java.io.Serializable;

// @feature:authorization
public class ProfileAccessQuery implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String component;
  private final String element;

  public ProfileAccessQuery(String component, String element) {
    this.component = component;
    this.element = element;
  }

  public String getElement() {
    return element;
  }

  public String getComponent() {
    return component;
  }

}
