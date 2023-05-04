package utils.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.sys.ExecutionAgent;
import net.democritus.sys.ExecutionAgentIf;
import net.democritus.sys.IdCounterAgent;
import net.democritus.sys.IdCounterAgentIf;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.ParamTargetValueAgentIf;
import net.democritus.sys.TagValuePairAgent;
import net.democritus.sys.TagValuePairAgentIf;
import net.democritus.gui.ThumbnailAgent;
import net.democritus.gui.ThumbnailAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class UtilsProxyAgents implements UtilsAgents {

  private final Context context;

  // anchor:data-element-agent-variables:start
  private ExecutionAgentIf executionAgent;
  private IdCounterAgentIf idCounterAgent;
  private ParamTargetValueAgentIf paramTargetValueAgent;
  private TagValuePairAgentIf tagValuePairAgent;
  private ThumbnailAgentIf thumbnailAgent;
  // anchor:data-element-agent-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static UtilsAgents getUtilsAgents(Context context) {
    return new UtilsProxyAgents(context);
  }

  @Deprecated
  public static UtilsAgents getUtilsAgents(UserContext userContext) {
    return new UtilsProxyAgents(userContext);
  }

  public UtilsProxyAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public UtilsProxyAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public ExecutionAgentIf getExecutionAgent() {
    if (executionAgent == null) {
      executionAgent = ExecutionAgent.getExecutionAgent(context);
    }
    return executionAgent;
  }

  public IdCounterAgentIf getIdCounterAgent() {
    if (idCounterAgent == null) {
      idCounterAgent = IdCounterAgent.getIdCounterAgent(context);
    }
    return idCounterAgent;
  }

  public ParamTargetValueAgentIf getParamTargetValueAgent() {
    if (paramTargetValueAgent == null) {
      paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(context);
    }
    return paramTargetValueAgent;
  }

  public TagValuePairAgentIf getTagValuePairAgent() {
    if (tagValuePairAgent == null) {
      tagValuePairAgent = TagValuePairAgent.getTagValuePairAgent(context);
    }
    return tagValuePairAgent;
  }

  public ThumbnailAgentIf getThumbnailAgent() {
    if (thumbnailAgent == null) {
      thumbnailAgent = ThumbnailAgent.getThumbnailAgent(context);
    }
    return thumbnailAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
