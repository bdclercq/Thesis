package assets.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.assets.AssetLocalAgent;
import net.democritus.assets.AssetAgentIf;
import net.democritus.assets.ExternalAssetLocalAgent;
import net.democritus.assets.ExternalAssetAgentIf;
import net.democritus.assets.FileAssetLocalAgent;
import net.democritus.assets.FileAssetAgentIf;
import net.democritus.assets.InternalAssetLocalAgent;
import net.democritus.assets.InternalAssetAgentIf;
import net.democritus.assets.InternalAssetChunkLocalAgent;
import net.democritus.assets.InternalAssetChunkAgentIf;
import net.democritus.assets.RemoteAssetLocalAgent;
import net.democritus.assets.RemoteAssetAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class AssetsLocalAgents implements AssetsAgents {

  private final Context context;

  // anchor:data-element-agent-variables:start
  private AssetAgentIf assetAgent;
  private ExternalAssetAgentIf externalAssetAgent;
  private FileAssetAgentIf fileAssetAgent;
  private InternalAssetAgentIf internalAssetAgent;
  private InternalAssetChunkAgentIf internalAssetChunkAgent;
  private RemoteAssetAgentIf remoteAssetAgent;
  // anchor:data-element-agent-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static AssetsAgents getAssetsAgents(Context context) {
    return new AssetsLocalAgents(context);
  }

  @Deprecated
  public static AssetsAgents getAssetsAgents(UserContext userContext) {
    return new AssetsLocalAgents(userContext);
  }

  public AssetsLocalAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public AssetsLocalAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public AssetAgentIf getAssetAgent() {
    if (assetAgent == null) {
      assetAgent = AssetLocalAgent.getAssetAgent(context);
    }
    return assetAgent;
  }

  public ExternalAssetAgentIf getExternalAssetAgent() {
    if (externalAssetAgent == null) {
      externalAssetAgent = ExternalAssetLocalAgent.getExternalAssetAgent(context);
    }
    return externalAssetAgent;
  }

  public FileAssetAgentIf getFileAssetAgent() {
    if (fileAssetAgent == null) {
      fileAssetAgent = FileAssetLocalAgent.getFileAssetAgent(context);
    }
    return fileAssetAgent;
  }

  public InternalAssetAgentIf getInternalAssetAgent() {
    if (internalAssetAgent == null) {
      internalAssetAgent = InternalAssetLocalAgent.getInternalAssetAgent(context);
    }
    return internalAssetAgent;
  }

  public InternalAssetChunkAgentIf getInternalAssetChunkAgent() {
    if (internalAssetChunkAgent == null) {
      internalAssetChunkAgent = InternalAssetChunkLocalAgent.getInternalAssetChunkAgent(context);
    }
    return internalAssetChunkAgent;
  }

  public RemoteAssetAgentIf getRemoteAssetAgent() {
    if (remoteAssetAgent == null) {
      remoteAssetAgent = RemoteAssetLocalAgent.getRemoteAssetAgent(context);
    }
    return remoteAssetAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
