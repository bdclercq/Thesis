package net.democritus.wfe;

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

public abstract class EngineServiceCommand {

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private static Map<String, Class<? extends ICommand>> commandMap = new HashMap<String, Class<? extends ICommand>>();
  static {
    // anchor:command-map:start
    commandMap.put("startEngine", StartEngine.class);
    commandMap.put("stopEngine", StopEngine.class);
    commandMap.put("disableAllEngines", DisableAllEngines.class);
    commandMap.put("enableAllEngines", EnableAllEngines.class);
    commandMap.put("refreshEngine", RefreshEngine.class);
    // anchor:command-map:end
    // @anchor:command-map:start
    // @anchor:command-map:end
    // anchor:custom-command-map:start
    // anchor:custom-command-map:end
  }

  private static Map<Class<? extends ICommand>, Boolean> commandLogMap = new HashMap<Class<? extends ICommand>, Boolean>();
  static {
    // anchor:command-log-map:start
    commandLogMap.put(StartEngine.class, Boolean.FALSE);
    commandLogMap.put(StopEngine.class, Boolean.FALSE);
    commandLogMap.put(DisableAllEngines.class, Boolean.FALSE);
    commandLogMap.put(EnableAllEngines.class, Boolean.FALSE);
    commandLogMap.put(RefreshEngine.class, Boolean.FALSE);
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

  private EngineServiceCommand() {};

  // anchor:command-classes:start
  public static class StartEngine implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:startEngine-fields:start
    // @anchor:startEngine-fields:end
    // anchor:startEngine-fields:start
    private DataRef target;

    // anchor:startEngine-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:startEngine-methods:start
    public DataRef getTarget() {
      return target;
    }

    public void setTarget(DataRef target) {
      this.target = target;
    }

    // anchor:startEngine-methods:end
    // @anchor:startEngine-methods:start
    // @anchor:startEngine-methods:end
    // anchor:custom-command-startEngine:start
    // anchor:custom-command-startEngine:end
  }
  public static class StopEngine implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:stopEngine-fields:start
    // @anchor:stopEngine-fields:end
    // anchor:stopEngine-fields:start
    private DataRef target;

    // anchor:stopEngine-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:stopEngine-methods:start
    public DataRef getTarget() {
      return target;
    }

    public void setTarget(DataRef target) {
      this.target = target;
    }

    // anchor:stopEngine-methods:end
    // @anchor:stopEngine-methods:start
    // @anchor:stopEngine-methods:end
    // anchor:custom-command-stopEngine:start
    // anchor:custom-command-stopEngine:end
  }
  public static class DisableAllEngines implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:disableAllEngines-fields:start
    // @anchor:disableAllEngines-fields:end
    // anchor:disableAllEngines-fields:start
    // anchor:disableAllEngines-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:disableAllEngines-methods:start
    // anchor:disableAllEngines-methods:end
    // @anchor:disableAllEngines-methods:start
    // @anchor:disableAllEngines-methods:end
    // anchor:custom-command-disableAllEngines:start
    // anchor:custom-command-disableAllEngines:end
  }
  public static class EnableAllEngines implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:enableAllEngines-fields:start
    // @anchor:enableAllEngines-fields:end
    // anchor:enableAllEngines-fields:start
    // anchor:enableAllEngines-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:enableAllEngines-methods:start
    // anchor:enableAllEngines-methods:end
    // @anchor:enableAllEngines-methods:start
    // @anchor:enableAllEngines-methods:end
    // anchor:custom-command-enableAllEngines:start
    // anchor:custom-command-enableAllEngines:end
  }
  public static class RefreshEngine implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:refreshEngine-fields:start
    // @anchor:refreshEngine-fields:end
    // anchor:refreshEngine-fields:start
    private DataRef target;

    // anchor:refreshEngine-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:refreshEngine-methods:start
    public DataRef getTarget() {
      return target;
    }

    public void setTarget(DataRef target) {
      this.target = target;
    }

    // anchor:refreshEngine-methods:end
    // @anchor:refreshEngine-methods:start
    // @anchor:refreshEngine-methods:end
    // anchor:custom-command-refreshEngine:start
    // anchor:custom-command-refreshEngine:end
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
