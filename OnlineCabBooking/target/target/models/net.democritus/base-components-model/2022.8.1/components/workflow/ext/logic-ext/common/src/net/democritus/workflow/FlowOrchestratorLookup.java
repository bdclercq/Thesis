package net.democritus.workflow;

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.FlowOrchestrator;

import javax.naming.NamingException;

public class FlowOrchestratorLookup {

  /**
   * @param workflow details object of the target workflow
   * @return returns FlowOrchestrator (workflow engine), null if not found
   */
  public static FlowOrchestrator<?, WorkflowDetails> getFlowOrchestrator(WorkflowDetails workflow) {
    ComponentJNDI workflowComponent = ComponentJNDI.getComponentJNDI(workflow.getComponentName());

    String workflowEngineName = workflow.getClassName() + "Engine";
    try {
      String engineLocalName = workflowComponent.getFlowLocalName(workflowEngineName);
      return workflowComponent.lookupLocal(engineLocalName);
    } catch (NamingException e) {
      // local engine not found, try remote engine
    }

    try {
      String engineRemoteName = workflowComponent.getFlowRemoteName(workflowEngineName);
      return workflowComponent.lookupRemote(engineRemoteName);
    } catch (NamingException e) {
      // remote engine not found
      return null;
    } catch (Exception e) {
      throw new IllegalStateException("Failed to find EngineBean for workflow='" + workflow.getName() + "' with flowElementName='" + workflow.getClassName() + "'. Expected ComponentMetaData to contain a flowElementDef with name '" + workflowEngineName + "'", e);
    }
  }

}
