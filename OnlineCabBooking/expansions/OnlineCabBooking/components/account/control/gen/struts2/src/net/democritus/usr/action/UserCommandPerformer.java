package net.democritus.usr.action;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import org.apache.struts2.ServletActionContext;

import net.democritus.json.JsonResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.Context;
import net.democritus.acl.struts2.UserContextRetriever;
import account.context.ContextRetriever;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.ICommandValidator;
import net.democritus.sys.command.CommandResult;

import static net.palver.util.Options.Option;

import net.democritus.usr.UserCommand;
import net.democritus.usr.UserCommandValidator;
import net.democritus.usr.UserAgent;

// @anchor:imports:start
import net.democritus.sys.command.CommandError;
import net.democritus.sys.validation.FieldError;
import java.util.List;
import java.util.Map;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class UserCommandPerformer extends ActionSupport implements Preparable {
  private ICommand command;
  private CommandResult commandResult;
  private UserAgent userAgent;
  private String commandName;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public ICommand getCommand() {
    return command;
  }

  public void setCommand(ICommand command) {
    this.command = command;
  }

  public CommandResult getCommandResult() {
      return commandResult;
  }

  /**
   * @deprecated Use {@link ContextRetriever} instead
   */
  @Deprecated
  private static UserContext getUserContext() {
    return UserContextRetriever.getUserContext();
  }

  public void setCommandName(String commandName) {
    // commandName is accessed and set directly from request parameters in prepare()
  }

  @Override
  public void prepare() throws Exception {
    HttpServletRequest request = ServletActionContext.getRequest();
    commandName = request.getParameter("commandName");

    if (commandName == null) {
        throw new RuntimeException("Parameter 'commandName' not specified");
    }

    Class<? extends ICommand> clazz = UserCommand.getCommandByName(commandName);
    if (clazz == null) {
      throw new RuntimeException("Command not found: " + commandName);
    }

    command = clazz.newInstance();

    if (userAgent == null) {
      userAgent = UserAgent.getUserAgent(ContextRetriever.getContext());
    }

    // anchor:custom-preparation:start
    // anchor:custom-preparation:end
  }

  @Override public void validate() {
    Option<ICommandValidator> optValidator = UserCommandValidator.getValidator(commandName);
    if (optValidator.isEmpty()) {
      return;
    }

    commandResult = optValidator.getValue().validate(command);

    // @anchor:validation:start
    if (this.hasFieldErrors()) {
      convertStrutsFieldErrors(getFieldErrors());
    }
    // @anchor:validation:end

    // anchor:custom-validation:start
    // anchor:custom-validation:end

    if (commandResult.isError()) {
      // mark action error, full errors are returned in commandResult
      addActionError("Validation failed");
    }
  }

  @Override
  public String execute() {

    // @anchor:execute-validation:start
    // @anchor:execute-validation:end

    commandResult = userAgent.perform(command);

    return SUCCESS;
  }

  // @anchor:methods:start
  private void convertStrutsFieldErrors(Map<String, List<String>> fieldErrors) {
    if (commandResult.isSuccess()) {
      commandResult = CommandResult.error(command.getCommandId(), "Command has errors");
    }
    CommandError commandError = (CommandError) this.commandResult;
    for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
      for (String errorMessage : entry.getValue()) {
        FieldError fieldError = new FieldError(entry.getKey(), "account.User." + entry.getKey(), errorMessage);
        commandError.addFieldError(fieldError);
      }
    }
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
