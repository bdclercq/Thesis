package net.democritus.workflow;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.DataRef;
import net.democritus.sys.ProjectionRef;

import net.democritus.sys.ParameterContext;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.SearchResult;
import net.democritus.sys.search.SearchDetails;

import net.democritus.sys.command.ICommand;
import net.democritus.sys.command.CommandResult;

import java.util.Date;
import java.util.List;

// @anchor:imports:start
import net.democritus.upload.ImportFile;
import net.democritus.upload.ImportResult;
// @anchor:imports:end

// anchor:custom-imports:start
import net.democritus.sys.FlowResult;
import net.democritus.sys.TaskResult;
import net.democritus.sys.UserContext;
// anchor:custom-imports:end

/**
 * Remote interface for the entity bean Workflow,
 * representing a known Workflow
 */
public interface WorkflowRemote {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:crud-methods:start
  CrudsResult<DataRef> create(ParameterContext<WorkflowDetails> detailsParameter);
  CrudsResult<DataRef> modify(ParameterContext<WorkflowDetails> detailsParameter);
  <P> CrudsResult<DataRef> createOrModify(ParameterContext<P> projectionParameter);
  CrudsResult<Void> delete(ParameterContext<Long> idParameter);
  CrudsResult<Void> deleteByDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<DataRef> getDataRef(ParameterContext<Long> idParameter);
  CrudsResult<WorkflowInfo> getInfo(ParameterContext<Long> idParameter);
  CrudsResult<WorkflowDetails> getDetails(ParameterContext<Long> idParameter);
  <T> CrudsResult<T> getProjection(ParameterContext<ProjectionRef> projectionRefParameter);
  CrudsResult<DataRef> resolveDataRef(ParameterContext<DataRef> dataRefParameter);

  CrudsResult<WorkflowDetails> getDetailsFromDataRef(ParameterContext<DataRef> dataRef);

  CrudsResult<String> getName(ParameterContext<Long> idParameter);
  CrudsResult<DataRef> getId(ParameterContext<String> nameParameter);

  // anchor:crud-methods:end

  // anchor:search-methods:start
  <S extends WorkflowFindDetails, T> SearchResult<T> find(ParameterContext<SearchDetails<S>> searchParameter);
  // anchor:search-methods:end

  // anchor:business-methods:start
  // anchor:business-methods:end

  // @anchor:methods:start
  ImportResult importFile(ParameterContext<ImportFile> parameter);
  // @anchor:methods:end

  // anchor:custom-methods:start
  FlowResult<Void> runWorkflowWithEngineService(ParameterContext<DataRef> engineServiceParameter);
  FlowResult<Void> runWorkflowWithTargets(ParameterContext<DataRef> workflowParameter, List<DataRef> targetInstances);
  TaskResult<Void> recoverWorkflow(ParameterContext<DataRef> workflowParameter);
  CrudsResult<WorkflowDetails> getWorkflowById(ParameterContext<Long> workflowIdParameter);
  UserContext getResponsibleUserContext(ParameterContext<WorkflowDetails> workflowDetailsParameter);
  // anchor:custom-methods:end

}

