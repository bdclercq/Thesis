package net.democritus.assets.action;

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
import assets.context.ContextRetriever;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.ICommandValidator;
import net.democritus.sys.command.CommandResult;

import static net.palver.util.Options.Option;

import net.democritus.assets.AssetCommand;
import net.democritus.assets.AssetCommandValidator;
import net.democritus.assets.AssetAgent;

// @anchor:imports:start
import net.democritus.sys.command.CommandError;
import net.democritus.sys.validation.FieldError;
import java.util.List;
import java.util.Map;
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class AssetCommandPerformer extends ActionSupport implements Preparable {
  private ICommand command;
  private CommandResult commandResult;
  private AssetAgent assetAgent;
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

    Class<? extends ICommand> clazz = AssetCommand.getCommandByName(commandName);
    if (clazz == null) {
      throw new RuntimeException("Command not found: " + commandName);
    }

    command = clazz.newInstance();

    if (assetAgent == null) {
      assetAgent = AssetAgent.getAssetAgent(ContextRetriever.getContext());
    }

    // anchor:custom-preparation:start
    // anchor:custom-preparation:end
  }

  @Override public void validate() {
    Option<ICommandValidator> optValidator = AssetCommandValidator.getValidator(commandName);
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
    HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

    if (!httpServletRequest.getMethod().equals("POST")) {
      HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
      httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      addActionError("This method should be called using POST");
      return Action.SUCCESS;
    }
    // @anchor:execute-validation:end

    commandResult = assetAgent.perform(command);

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
        FieldError fieldError = new FieldError(entry.getKey(), "assets.Asset." + entry.getKey(), errorMessage);
        commandError.addFieldError(fieldError);
      }
    }
  }
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
