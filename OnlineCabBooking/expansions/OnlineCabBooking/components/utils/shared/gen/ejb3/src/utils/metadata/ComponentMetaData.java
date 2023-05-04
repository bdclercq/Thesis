package utils.metadata;

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
  private static String COMPONENT_NAME = "utils";

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
    dataElementDefs.add(getDataElementDef_Execution());
    dataElementDefs.add(getDataElementDef_IdCounter());
    dataElementDefs.add(getDataElementDef_ParamTargetValue());
    dataElementDefs.add(getDataElementDef_TagValuePair());
    dataElementDefs.add(getDataElementDef_Thumbnail());
    // anchor:add-data-elements:end

    // anchor:custom-data-elements:start
    // anchor:custom-data-elements:end

    return dataElementDefs;
  }

  private static List<TaskElementDef> buildTaskElements() {
    List<TaskElementDef> taskElementDefs = new ArrayList<TaskElementDef>();

    // anchor:add-task-elements:start
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.sys", "Executor", TransactionType.ATOMIC_EXTERNAL));
    // anchor:add-task-elements:end

    // anchor:custom-task-elements:start
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
  private static DataElementDef getDataElementDef_Execution() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.sys", "Execution");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("component", "String"),
      new ValueFieldDef("element", "String"),
      new ValueFieldDef("packageName", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllExecutions"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByElementEq",
        new FieldOperator("element", "LIKE")
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
          "component",
          "element",
          "packageName"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "component",
          "element",
          "packageName"
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

  private static DataElementDef getDataElementDef_IdCounter() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.sys", "IdCounter");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("counter", "Long")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllIdCounters"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
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
          "counter"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "counter"
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

  private static DataElementDef getDataElementDef_ParamTargetValue() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.sys", "ParamTargetValue");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("param", "String"),
      new ValueFieldDef("target", "String"),
      new ValueFieldDef("value", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllParamTargetValues"),
      new FinderDef("findByParamEq",
        new FieldOperator("param", "LIKE")
      ),
      new FinderDef("findByParamEq_TargetEq",
        new FieldOperator("param", "LIKE"),
        new FieldOperator("target", "LIKE")
      ),
      new FinderDef("findByParamEq_ValueEq",
        new FieldOperator("param", "LIKE"),
        new FieldOperator("value", "LIKE")
      ),
      new FinderDef("findByTargetEq",
        new FieldOperator("target", "LIKE")
      ),
      new FinderDef("findByTargetEq_ValueEq",
        new FieldOperator("target", "LIKE"),
        new FieldOperator("value", "LIKE")
      ),
      new FinderDef("findByValueEq",
        new FieldOperator("value", "LIKE")
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
          "param",
          "target",
          "value"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "param",
          "target",
          "value"
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

  private static DataElementDef getDataElementDef_TagValuePair() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.sys", "TagValuePair");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("tag", "String"),
      new ValueFieldDef("value", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllTagValuePairs"),
      new FinderDef("findByTagEq",
        new FieldOperator("tag", "LIKE")
      ),
      new FinderDef("findByTagEq_ValueEq",
        new FieldOperator("tag", "LIKE"),
        new FieldOperator("value", "LIKE")
      ),
      new FinderDef("findByValueEq",
        new FieldOperator("value", "LIKE")
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
          "tag",
          "value"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "tag",
          "value"
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

  private static DataElementDef getDataElementDef_Thumbnail() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.gui", "Thumbnail");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("fullName", "String"),
      new ValueFieldDef("uri", "String"),
      new ValueFieldDef("depth", "Integer"),
      new ValueFieldDef("border", "Integer"),
      new ValueFieldDef("thumbType", "String"),
      new ValueFieldDef("thumbName", "String"),
      new ValueFieldDef("targetType", "String"),
      new ValueFieldDef("targetName", "String"),
      new ValueFieldDef("targetId", "Long"),
      new ValueFieldDef("leftX", "Integer"),
      new ValueFieldDef("topY", "Integer"),
      new ValueFieldDef("width", "Integer"),
      new ValueFieldDef("height", "Integer"),
      new ValueFieldDef("clickAction", "String"),
      new ValueFieldDef("hooverAction", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllThumbnails"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByDepthEq",
        new FieldOperator("depth", "=")
      ),
      new FinderDef("findByTargetNameEq",
        new FieldOperator("targetName", "LIKE")
      ),
      new FinderDef("findByTargetTypeEq",
        new FieldOperator("targetType", "LIKE")
      ),
      new FinderDef("findByThumbTypeEq",
        new FieldOperator("thumbType", "LIKE")
      ),
      new FinderDef("findByUriEq",
        new FieldOperator("uri", "LIKE")
      ),
      new FinderDef("findByUriEq_DepthEq",
        new FieldOperator("depth", "="),
        new FieldOperator("uri", "LIKE")
      ),
      new FinderDef("findByUriEq_TargetTypeEq",
        new FieldOperator("targetType", "LIKE"),
        new FieldOperator("uri", "LIKE")
      ),
      new FinderDef("findByUriEq_ThumbTypeEq",
        new FieldOperator("thumbType", "LIKE"),
        new FieldOperator("uri", "LIKE")
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
          "fullName",
          "uri",
          "depth",
          "border",
          "thumbType",
          "thumbName",
          "targetType",
          "targetName",
          "targetId",
          "leftX",
          "topY",
          "width",
          "height",
          "clickAction",
          "hooverAction"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "fullName",
          "uri",
          "depth",
          "thumbType",
          "targetType",
          "leftX",
          "topY"
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
