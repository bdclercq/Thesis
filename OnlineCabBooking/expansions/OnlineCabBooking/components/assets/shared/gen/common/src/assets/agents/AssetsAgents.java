package assets.agents;

import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.assets.AssetAgentIf;
import net.democritus.assets.ExternalAssetAgentIf;
import net.democritus.assets.FileAssetAgentIf;
import net.democritus.assets.InternalAssetAgentIf;
import net.democritus.assets.InternalAssetChunkAgentIf;
import net.democritus.assets.RemoteAssetAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AssetsAgents {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:data-element-agent-getters:start
  AssetAgentIf getAssetAgent();
  ExternalAssetAgentIf getExternalAssetAgent();
  FileAssetAgentIf getFileAssetAgent();
  InternalAssetAgentIf getInternalAssetAgent();
  InternalAssetChunkAgentIf getInternalAssetChunkAgent();
  RemoteAssetAgentIf getRemoteAssetAgent();
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
