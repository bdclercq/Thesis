package utils.agents;

import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.sys.ExecutionAgentIf;
import net.democritus.sys.IdCounterAgentIf;
import net.democritus.sys.ParamTargetValueAgentIf;
import net.democritus.sys.TagValuePairAgentIf;
import net.democritus.gui.ThumbnailAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface UtilsAgents {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:data-element-agent-getters:start
  ExecutionAgentIf getExecutionAgent();
  IdCounterAgentIf getIdCounterAgent();
  ParamTargetValueAgentIf getParamTargetValueAgent();
  TagValuePairAgentIf getTagValuePairAgent();
  ThumbnailAgentIf getThumbnailAgent();
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
