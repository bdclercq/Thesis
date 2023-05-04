package net.democritus.usr;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.DataRef;
import net.democritus.sys.command.ICommand;

import java.io.Serializable;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public abstract class UserCommand {

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private static Map<String, Class<? extends ICommand>> commandMap = new HashMap<String, Class<? extends ICommand>>();
  static {
    // anchor:command-map:start
    commandMap.put("changePassword", ChangePassword.class);
    // anchor:command-map:end
    // @anchor:command-map:start
    // @anchor:command-map:end
    // anchor:custom-command-map:start
    // anchor:custom-command-map:end
  }

  private static Map<Class<? extends ICommand>, Boolean> commandLogMap = new HashMap<Class<? extends ICommand>, Boolean>();
  static {
    // anchor:command-log-map:start
    commandLogMap.put(ChangePassword.class, Boolean.FALSE);
    // anchor:command-log-map:end
    // @anchor:command-log-map:start
    // @anchor:command-log-map:end
  }

  public static Class<? extends ICommand> getCommandByName(String commandName) {
    return commandMap.get(commandName);
  }

  public static Class<? extends ICommand> getCommand(String commandName) {
    return commandMap.get(commandName);
  }

  public static boolean shouldLog(ICommand command) {
    return commandLogMap.get(command.getClass());
  }

  private UserCommand() {};

  // anchor:command-classes:start
  public static class ChangePassword implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:changePassword-fields:start
    // @anchor:changePassword-fields:end
    // anchor:changePassword-fields:start
    private DataRef target;
    private String mNewPassword;
    private String mRepeatNewPassword;

    // anchor:changePassword-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:changePassword-methods:start
    public DataRef getTarget() {
      return target;
    }

    public void setTarget(DataRef target) {
      this.target = target;
    }

    public String getNewPassword() {
      return this.mNewPassword;
    }

    public String getRepeatNewPassword() {
      return this.mRepeatNewPassword;
    }

    public void setNewPassword(String newPassword) {
      this.mNewPassword = newPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
      this.mRepeatNewPassword = repeatNewPassword;
    }
    // anchor:changePassword-methods:end
    // @anchor:changePassword-methods:start
    // @anchor:changePassword-methods:end
    // anchor:custom-command-changePassword:start
    // anchor:custom-command-changePassword:end
  }
  // anchor:command-classes:end
  // @anchor:command-classes:start
  // @anchor:command-classes:end
  // anchor:custom-command-classes:start
  // anchor:custom-command-classes:end

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
