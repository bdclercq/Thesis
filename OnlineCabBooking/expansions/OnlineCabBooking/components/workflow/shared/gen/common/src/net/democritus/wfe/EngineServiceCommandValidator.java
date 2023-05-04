package net.democritus.wfe;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.ICommandValidator;
import net.democritus.sys.command.CommandResult;
import net.democritus.validation.ValidationResult;
import net.democritus.sys.validation.FieldError;

import static net.palver.util.Options.Option;
import static net.palver.util.Options.some;
import static net.palver.util.Options.none;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// @anchor:imports:start
// @anchor:imports:end

// anchor:valuetype-imports:start
// anchor:valuetype-imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class EngineServiceCommandValidator {
  private static Map<String, ICommandValidator> validators = new HashMap<String, ICommandValidator>();

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  static {
    // anchor:command-validator-map:start
    validators.put("startEngine", new StartEngineValidator());
    validators.put("stopEngine", new StopEngineValidator());
    validators.put("disableAllEngines", new DisableAllEnginesValidator());
    validators.put("enableAllEngines", new EnableAllEnginesValidator());
    validators.put("refreshEngine", new RefreshEngineValidator());
    // anchor:command-validator-map:end

    // anchor:custom-command-validator-map:start
    // anchor:custom-command-validator-map:end
  }

  public static Option<ICommandValidator> getValidator(String commandName) {
    ICommandValidator validator = validators.get(commandName);
    if (validator != null) {
      return some(validator);
    } else {
      return none();
    }
  }

  private static CommandResult createResult(ICommand command, List<FieldError> fieldErrors) {
    if (fieldErrors.isEmpty()) {
      return CommandResult.success(command.getCommandId());
    } else {
      return CommandResult.error(command.getCommandId(), "Validation error(s)", fieldErrors);
    }
  }

  // anchor:command-classes:start
  public static class StartEngineValidator implements ICommandValidator {
    @Override
    public CommandResult validate(ICommand cmd) {
      EngineServiceCommand.StartEngine startEngineCommand = (EngineServiceCommand.StartEngine) cmd;
      List<ValidationResult> validationResults = new LinkedList<ValidationResult>();
      List<FieldError> fieldErrors = new LinkedList<FieldError>();
      ValidationResult result;

      // anchor:custom-command-startEngine:start
      // anchor:custom-command-startEngine:end

      CommandResult commandResult = createResult(startEngineCommand, fieldErrors);

      return commandResult;
    }
  }

  public static class StopEngineValidator implements ICommandValidator {
    @Override
    public CommandResult validate(ICommand cmd) {
      EngineServiceCommand.StopEngine stopEngineCommand = (EngineServiceCommand.StopEngine) cmd;
      List<ValidationResult> validationResults = new LinkedList<ValidationResult>();
      List<FieldError> fieldErrors = new LinkedList<FieldError>();
      ValidationResult result;

      // anchor:custom-command-stopEngine:start
      // anchor:custom-command-stopEngine:end

      CommandResult commandResult = createResult(stopEngineCommand, fieldErrors);

      return commandResult;
    }
  }

  public static class DisableAllEnginesValidator implements ICommandValidator {
    @Override
    public CommandResult validate(ICommand cmd) {
      EngineServiceCommand.DisableAllEngines disableAllEnginesCommand = (EngineServiceCommand.DisableAllEngines) cmd;
      List<ValidationResult> validationResults = new LinkedList<ValidationResult>();
      List<FieldError> fieldErrors = new LinkedList<FieldError>();
      ValidationResult result;

      // anchor:custom-command-disableAllEngines:start
      // anchor:custom-command-disableAllEngines:end

      CommandResult commandResult = createResult(disableAllEnginesCommand, fieldErrors);

      return commandResult;
    }
  }

  public static class EnableAllEnginesValidator implements ICommandValidator {
    @Override
    public CommandResult validate(ICommand cmd) {
      EngineServiceCommand.EnableAllEngines enableAllEnginesCommand = (EngineServiceCommand.EnableAllEngines) cmd;
      List<ValidationResult> validationResults = new LinkedList<ValidationResult>();
      List<FieldError> fieldErrors = new LinkedList<FieldError>();
      ValidationResult result;

      // anchor:custom-command-enableAllEngines:start
      // anchor:custom-command-enableAllEngines:end

      CommandResult commandResult = createResult(enableAllEnginesCommand, fieldErrors);

      return commandResult;
    }
  }

  public static class RefreshEngineValidator implements ICommandValidator {
    @Override
    public CommandResult validate(ICommand cmd) {
      EngineServiceCommand.RefreshEngine refreshEngineCommand = (EngineServiceCommand.RefreshEngine) cmd;
      List<ValidationResult> validationResults = new LinkedList<ValidationResult>();
      List<FieldError> fieldErrors = new LinkedList<FieldError>();
      ValidationResult result;

      // anchor:custom-command-refreshEngine:start
      // anchor:custom-command-refreshEngine:end

      CommandResult commandResult = createResult(refreshEngineCommand, fieldErrors);

      return commandResult;
    }
  }
  // anchor:command-classes:end

  // anchor:custom-command-validator-classes:start
  // anchor:custom-command-validator-classes:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
