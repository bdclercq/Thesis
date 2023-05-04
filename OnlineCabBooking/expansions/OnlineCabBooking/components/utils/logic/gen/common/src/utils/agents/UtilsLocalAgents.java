package utils.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.sys.ExecutionLocalAgent;
import net.democritus.sys.ExecutionAgentIf;
import net.democritus.sys.IdCounterLocalAgent;
import net.democritus.sys.IdCounterAgentIf;
import net.democritus.sys.ParamTargetValueLocalAgent;
import net.democritus.sys.ParamTargetValueAgentIf;
import net.democritus.sys.TagValuePairLocalAgent;
import net.democritus.sys.TagValuePairAgentIf;
import net.democritus.gui.ThumbnailLocalAgent;
import net.democritus.gui.ThumbnailAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class UtilsLocalAgents implements UtilsAgents {

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
    return new UtilsLocalAgents(context);
  }

  @Deprecated
  public static UtilsAgents getUtilsAgents(UserContext userContext) {
    return new UtilsLocalAgents(userContext);
  }

  public UtilsLocalAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public UtilsLocalAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public ExecutionAgentIf getExecutionAgent() {
    if (executionAgent == null) {
      executionAgent = ExecutionLocalAgent.getExecutionAgent(context);
    }
    return executionAgent;
  }

  public IdCounterAgentIf getIdCounterAgent() {
    if (idCounterAgent == null) {
      idCounterAgent = IdCounterLocalAgent.getIdCounterAgent(context);
    }
    return idCounterAgent;
  }

  public ParamTargetValueAgentIf getParamTargetValueAgent() {
    if (paramTargetValueAgent == null) {
      paramTargetValueAgent = ParamTargetValueLocalAgent.getParamTargetValueAgent(context);
    }
    return paramTargetValueAgent;
  }

  public TagValuePairAgentIf getTagValuePairAgent() {
    if (tagValuePairAgent == null) {
      tagValuePairAgent = TagValuePairLocalAgent.getTagValuePairAgent(context);
    }
    return tagValuePairAgent;
  }

  public ThumbnailAgentIf getThumbnailAgent() {
    if (thumbnailAgent == null) {
      thumbnailAgent = ThumbnailLocalAgent.getThumbnailAgent(context);
    }
    return thumbnailAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
