package workflow.metadata;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import java.util.ArrayList;
import java.util.List;

import net.democritus.metadata.AbstractComponentMetaData;
import net.democritus.metadata.DataElementDef;
import net.democritus.metadata.FlowElementDef;
import net.democritus.metadata.TaskElementDef;
import net.democritus.metadata.ResourceDef;
import net.democritus.metadata.IFieldDef;
import net.democritus.metadata.ValueFieldDef;
import net.democritus.metadata.CalculatedFieldDef;
import net.democritus.metadata.LinkFieldDef;
import net.democritus.metadata.LinkDriverDef;
import net.democritus.metadata.IndirectFieldDef;
import net.democritus.metadata.LinkTarget;
import net.democritus.metadata.FinderDef;
import net.democritus.metadata.FieldOperator;
import net.democritus.metadata.ProjectionDef;
import net.democritus.metadata.CommandDef;
import net.democritus.metadata.TransactionType;

// @anchor:imports:start
// @anchor:imports:end

public class ComponentMetaData extends AbstractComponentMetaData {

  private static String DEPLOYMENT_NAME = "OnlineCabBooking";
  private static String COMPONENT_NAME = "workflow";

  // @anchor:variables:start
  // @anchor:variables:end

  public ComponentMetaData() {
    super(
      DEPLOYMENT_NAME,
      COMPONENT_NAME,
      buildDataElements(),
      buildTaskElements(),
      buildFlowElements(),
      buildResources());
  }

  private static List<DataElementDef> buildDataElements() {
    List<DataElementDef> dataElementDefs = new ArrayList<DataElementDef>();

    // anchor:add-data-elements:start
    dataElementDefs.add(getDataElementDef_EngineNode());
    dataElementDefs.add(getDataElementDef_EngineNodeService());
    dataElementDefs.add(getDataElementDef_EngineService());
    dataElementDefs.add(getDataElementDef_StateTask());
    dataElementDefs.add(getDataElementDef_StateTimer());
    dataElementDefs.add(getDataElementDef_TimeTask());
    dataElementDefs.add(getDataElementDef_TimeWindow());
    dataElementDefs.add(getDataElementDef_TimeWindowGroup());
    dataElementDefs.add(getDataElementDef_Workflow());
    // anchor:add-data-elements:end

    // anchor:custom-data-elements:start
    // anchor:custom-data-elements:end

    return dataElementDefs;
  }

  private static List<TaskElementDef> buildTaskElements() {
    List<TaskElementDef> taskElementDefs = new ArrayList<TaskElementDef>();

    // anchor:add-task-elements:start
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "CheckEngineHealth", TransactionType.AGGREGATE));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "RestoreEngines", TransactionType.AGGREGATE));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "ShutdownEnginesTask", TransactionType.AGGREGATE));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.workflow", "StartAllEnginesTask", TransactionType.AGGREGATE));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "StartEngineTask", TransactionType.ATOMIC_INTERNAL));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.workflow", "StopAllEnginesTask", TransactionType.AGGREGATE));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "StopEngineTask", TransactionType.ATOMIC_INTERNAL));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "TimerHandler", TransactionType.ATOMIC_EXTERNAL));
    // anchor:add-task-elements:end

    // anchor:custom-task-elements:start
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.wfe", "AsyncTaskExecution"));
    // anchor:custom-task-elements:end

    return taskElementDefs;
  }

  private static List<FlowElementDef> buildFlowElements() {
    List<FlowElementDef> flowElementDefs = new ArrayList<FlowElementDef>();

    // anchor:add-flow-elements:start
    // anchor:add-flow-elements:end

    // anchor:custom-flow-elements:start
    // anchor:custom-flow-elements:end

    return flowElementDefs;
  }

  private static List<ResourceDef> buildResources() {
    List<ResourceDef> resourceDefs = new ArrayList<ResourceDef>();

    // anchor:custom-resources:start
    // anchor:custom-resources:end

    return resourceDefs;
  }

  // anchor:data-element-getters:start
  private static DataElementDef getDataElementDef_EngineNode() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.wfe", "EngineNode");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("status", "String"),
      new ValueFieldDef("hostname", "String"),
      new ValueFieldDef("master", "Boolean"),
      new ValueFieldDef("lastActive", "DateLong"),
      new ValueFieldDef("activeSince", "DateLong")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllEngineNodes"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByMasterEq",
        new FieldOperator("master", "=")
      ),
      new FinderDef("findByMasterEq_LastActiveLt",
        new FieldOperator("master", "="),
        new FieldOperator("lastActive", "<")
      ),
      new FinderDef("findByLastActiveLt",
        new FieldOperator("lastActive", "<")
      ),
      new FinderDef("findByStatusEq",
        new FieldOperator("status", "LIKE")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "status",
          "hostname",
          "master",
          "lastActive",
          "activeSince"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "status",
          "hostname",
          "master"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_EngineNodeService() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.wfe", "EngineNodeService");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("status", "String"),
      new LinkFieldDef("engineNode", "LN02", "DataRef", "workflow", "EngineNode"),
      new LinkFieldDef("engineService", "LN02", "DataRef", "workflow", "EngineService"),
      new ValueFieldDef("lastRunAt", "DateLong"),
      new ValueFieldDef("nextRun", "DateLong")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllEngineNodeServices"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByStatusEq",
        new FieldOperator("status", "LIKE")
      ),
      new FinderDef("findByEngineNodeEq",
        new FieldOperator("engineNode", "=")
      ),
      new FinderDef("findByEngineServiceEq_StatusNe",
        new FieldOperator("engineService", "="),
        new FieldOperator("status", "NOT LIKE")
      ),
      new FinderDef("findByEngineServiceEq",
        new FieldOperator("engineService", "=")
      ),
      new FinderDef("findByEngineNodeEq_EngineServiceEq",
        new FieldOperator("engineNode", "="),
        new FieldOperator("engineService", "=")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "status",
          "engineNode",
          "engineService",
          "lastRunAt",
          "nextRun"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "status",
          "engineNode",
          "engineService",
          "lastRunAt",
          "nextRun"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      ),
      new ProjectionDef(
        "updateDetails",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "status",
          "lastRunAt",
          "nextRun"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_EngineService() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.wfe", "EngineService");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("status", "String"),
      new ValueFieldDef("changed", "String"),
      new ValueFieldDef("busy", "String"),
      new ValueFieldDef("waitTime", "Integer"),
      new ValueFieldDef("collector", "Long"),
      new LinkFieldDef("workflow", "LN01", "DataRef", "workflow", "Workflow"),
      new LinkFieldDef("timeWindowGroup", "LN01", "DataRef", "workflow", "TimeWindowGroup"),
      new ValueFieldDef("lastRunAt", "validation.DateTime"),
      new ValueFieldDef("batchSize", "Integer"),
      new ValueFieldDef("maximumNumberOfNodes", "Integer"),
      new LinkFieldDef("engineNodeServices", "LN04", "List", "workflow", "EngineNodeService")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllEngineServices"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByBusyEq",
        new FieldOperator("busy", "LIKE")
      ),
      new FinderDef("findByChangedEq",
        new FieldOperator("changed", "LIKE")
      ),
      new FinderDef("findByCollectorEq",
        new FieldOperator("collector", "=")
      ),
      new FinderDef("findByLastRunAtGt",
        new FieldOperator("lastRunAt", ">")
      ),
      new FinderDef("findByLastRunAtLt",
        new FieldOperator("lastRunAt", "<")
      ),
      new FinderDef("findByNameEq_CollectorEq",
        new FieldOperator("collector", "="),
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByStatusEq",
        new FieldOperator("status", "LIKE")
      ),
      new FinderDef("findByWorkflowEq",
        new FieldOperator("workflow", "=")
      ),
      new FinderDef("findByTimeWindowGroupEq",
        new FieldOperator("timeWindowGroup", "=")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "status",
          "changed",
          "busy",
          "waitTime",
          "collector",
          "workflow",
          "timeWindowGroup",
          "lastRunAt",
          "batchSize",
          "maximumNumberOfNodes",
          "engineNodeServices"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "status",
          "waitTime",
          "workflow",
          "timeWindowGroup",
          "batchSize"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      ),
      new ProjectionDef(
        "runningState",
        new CalculatedFieldDef[] {
          new CalculatedFieldDef("running", "String"),
          new CalculatedFieldDef("nextRun", "DateLong")
        },
        new String[] {
          "id",
          "name",
          "status",
          "busy",
          "waitTime",
          "timeWindowGroup",
          "lastRunAt",
          "batchSize",
          "maximumNumberOfNodes"
        }
      ),
      new ProjectionDef(
        "detailsWithoutRefs",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "status",
          "changed",
          "busy",
          "waitTime",
          "collector",
          "lastRunAt",
          "batchSize",
          "maximumNumberOfNodes"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
      new CommandDef("startEngine", true,
        new IFieldDef[] {
        }),
      new CommandDef("stopEngine", true,
        new IFieldDef[] {
        }),
      new CommandDef("disableAllEngines", false,
        new IFieldDef[] {
        }),
      new CommandDef("enableAllEngines", false,
        new IFieldDef[] {
        }),
      new CommandDef("refreshEngine", true,
        new IFieldDef[] {
        })
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_StateTask() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.workflow", "StateTask");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("processor", "String"),
      new ValueFieldDef("implementation", "String"),
      new ValueFieldDef("params", "String"),
      new ValueFieldDef("beginState", "String"),
      new ValueFieldDef("interimState", "String"),
      new ValueFieldDef("failedState", "String"),
      new ValueFieldDef("endState", "String"),
      new LinkFieldDef("workflow", "LN01", "DataRef", "workflow", "Workflow"),
      new ValueFieldDef("maxConcurrentTasks", "Integer"),
      new ValueFieldDef("timeout", "Long")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllStateTasks"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByBeginStateEq",
        new FieldOperator("beginState", "LIKE")
      ),
      new FinderDef("findByEndStateEq",
        new FieldOperator("endState", "LIKE")
      ),
      new FinderDef("findByFailedStateEq",
        new FieldOperator("failedState", "LIKE")
      ),
      new FinderDef("findByWorkflowEq",
        new FieldOperator("workflow", "=")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "implementation",
          "params",
          "beginState",
          "interimState",
          "failedState",
          "endState",
          "workflow",
          "maxConcurrentTasks",
          "timeout"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "params",
          "beginState",
          "endState",
          "workflow"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      ),
      new ProjectionDef(
        "detailsWithoutRefs",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "implementation",
          "params",
          "beginState",
          "interimState",
          "failedState",
          "endState",
          "maxConcurrentTasks",
          "timeout"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_StateTimer() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.workflow", "StateTimer");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("processor", "String"),
      new ValueFieldDef("implementation", "String"),
      new ValueFieldDef("params", "String"),
      new ValueFieldDef("beginState", "String"),
      new ValueFieldDef("targetState", "String"),
      new ValueFieldDef("alteredState", "String"),
      new ValueFieldDef("allowedPeriod", "Long"),
      new ValueFieldDef("requiredAction", "String"),
      new LinkFieldDef("workflow", "LN01", "DataRef", "workflow", "Workflow")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllStateTimers"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByAlteredStateEq",
        new FieldOperator("alteredState", "LIKE")
      ),
      new FinderDef("findByBeginStateEq",
        new FieldOperator("beginState", "LIKE")
      ),
      new FinderDef("findByTargetStateEq",
        new FieldOperator("targetState", "LIKE")
      ),
      new FinderDef("findByWorkflowEq",
        new FieldOperator("workflow", "=")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "implementation",
          "params",
          "beginState",
          "targetState",
          "alteredState",
          "allowedPeriod",
          "requiredAction",
          "workflow"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "beginState",
          "targetState",
          "allowedPeriod",
          "requiredAction",
          "workflow"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_TimeTask() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.workflow", "TimeTask");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("processor", "String"),
      new ValueFieldDef("implementation", "String"),
      new ValueFieldDef("params", "String"),
      new ValueFieldDef("triggerState", "String"),
      new ValueFieldDef("intervalPeriod", "Long"),
      new ValueFieldDef("requiredAction", "String"),
      new LinkFieldDef("workflow", "LN01", "DataRef", "workflow", "Workflow")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllTimeTasks"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByIntervalPeriodEq",
        new FieldOperator("intervalPeriod", "=")
      ),
      new FinderDef("findByTriggerStateEq",
        new FieldOperator("triggerState", "LIKE")
      ),
      new FinderDef("findByWorkflowEq",
        new FieldOperator("workflow", "=")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "implementation",
          "params",
          "triggerState",
          "intervalPeriod",
          "requiredAction",
          "workflow"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "processor",
          "triggerState",
          "intervalPeriod",
          "requiredAction",
          "workflow"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_TimeWindow() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.wfe", "TimeWindow");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("startTime", "String"),
      new ValueFieldDef("stopTime", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllTimeWindows"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByStartTimeEq",
        new FieldOperator("startTime", "LIKE")
      ),
      new FinderDef("findByStopTimeEq",
        new FieldOperator("stopTime", "LIKE")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "startTime",
          "stopTime"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "startTime",
          "stopTime"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_TimeWindowGroup() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.wfe", "TimeWindowGroup");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("visible", "String"),
      new LinkFieldDef("timeWindows", "LN03", "List", "workflow", "TimeWindow")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllTimeWindowGroups"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByVisibleEq",
        new FieldOperator("visible", "LIKE")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "visible",
          "timeWindows"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "visible",
          "timeWindows"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_Workflow() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.workflow", "Workflow");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("target", "String"),
      new ValueFieldDef("componentName", "String"),
      new ValueFieldDef("className", "String"),
      new LinkFieldDef("responsible", "LN01", "DataRef", "account", "User"),
      new ValueFieldDef("sequencingStrategy", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllWorkflows"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByResponsibleEq",
        new FieldOperator("responsible", "=")
      ),
      new FinderDef("findByTargetEq",
        new FieldOperator("target", "LIKE")
      )
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "target",
          "componentName",
          "className",
          "responsible",
          "sequencingStrategy"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "componentName",
          "className",
          "sequencingStrategy"
        }
      ),
      new ProjectionDef(
        "dataRef",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }
  // anchor:data-element-getters:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
