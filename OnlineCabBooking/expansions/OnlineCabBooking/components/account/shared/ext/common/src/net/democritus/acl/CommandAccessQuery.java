package net.democritus.acl;

import java.io.Serializable;

// @feature:authorization
public class CommandAccessQuery implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String component;
  private final String element;
  private final String commandName;

  public CommandAccessQuery(String component, String element, String commandName) {
    this.component = component;
    this.element = element;
    this.commandName = commandName;
  }

  public String getElement() {
    return element;
  }

  public String getComponent() {
    return component;
  }

  public String getCommandName() {
    return commandName;
  }

}
