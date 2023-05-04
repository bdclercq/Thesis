package net.democritus.assets;

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

public abstract class AssetCommand {

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  private static Map<String, Class<? extends ICommand>> commandMap = new HashMap<String, Class<? extends ICommand>>();
  static {
    // anchor:command-map:start
    commandMap.put("registerExternalAsset", RegisterExternalAsset.class);
    // anchor:command-map:end
    // @anchor:command-map:start
    // @anchor:command-map:end
    // anchor:custom-command-map:start
    // anchor:custom-command-map:end
  }

  private static Map<Class<? extends ICommand>, Boolean> commandLogMap = new HashMap<Class<? extends ICommand>, Boolean>();
  static {
    // anchor:command-log-map:start
    commandLogMap.put(RegisterExternalAsset.class, Boolean.FALSE);
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

  private AssetCommand() {};

  // anchor:command-classes:start
  public static class RegisterExternalAsset implements ICommand, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID commandId;
    // @anchor:registerExternalAsset-fields:start
    // @anchor:registerExternalAsset-fields:end
    // anchor:registerExternalAsset-fields:start
    private String mUri;

    // anchor:registerExternalAsset-fields:end

    @Override
    public UUID getCommandId() {
      return commandId;
    }

    @Override
    public void setCommandId(UUID commandId) {
      this.commandId = commandId;
    }

    // anchor:registerExternalAsset-methods:start
    public String getUri() {
      return this.mUri;
    }

    public void setUri(String uri) {
      this.mUri = uri;
    }
    // anchor:registerExternalAsset-methods:end
    // @anchor:registerExternalAsset-methods:start
    // @anchor:registerExternalAsset-methods:end
    // anchor:custom-command-registerExternalAsset:start
    // anchor:custom-command-registerExternalAsset:end
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
