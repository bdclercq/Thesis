package validation.agents;

import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.validation.ValidationAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface ValidationAgents {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:data-element-agent-getters:start
  ValidationAgentIf getValidationAgent();
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
