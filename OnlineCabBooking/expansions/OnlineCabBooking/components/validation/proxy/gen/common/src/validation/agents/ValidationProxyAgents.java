package validation.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.validation.ValidationAgent;
import net.democritus.validation.ValidationAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class ValidationProxyAgents implements ValidationAgents {

  private final Context context;

  // anchor:data-element-agent-variables:start
  private ValidationAgentIf validationAgent;
  // anchor:data-element-agent-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static ValidationAgents getValidationAgents(Context context) {
    return new ValidationProxyAgents(context);
  }

  @Deprecated
  public static ValidationAgents getValidationAgents(UserContext userContext) {
    return new ValidationProxyAgents(userContext);
  }

  public ValidationProxyAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public ValidationProxyAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public ValidationAgentIf getValidationAgent() {
    if (validationAgent == null) {
      validationAgent = ValidationAgent.getValidationAgent(context);
    }
    return validationAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
