package assets.metadata;

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
  private static String COMPONENT_NAME = "assets";

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
    dataElementDefs.add(getDataElementDef_Asset());
    dataElementDefs.add(getDataElementDef_ExternalAsset());
    dataElementDefs.add(getDataElementDef_FileAsset());
    dataElementDefs.add(getDataElementDef_InternalAsset());
    dataElementDefs.add(getDataElementDef_InternalAssetChunk());
    dataElementDefs.add(getDataElementDef_RemoteAsset());
    // anchor:add-data-elements:end

    // anchor:custom-data-elements:start
    // anchor:custom-data-elements:end

    return dataElementDefs;
  }

  private static List<TaskElementDef> buildTaskElements() {
    List<TaskElementDef> taskElementDefs = new ArrayList<TaskElementDef>();

    // anchor:add-task-elements:start
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
  private static DataElementDef getDataElementDef_Asset() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.assets", "Asset");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("type", "String"),
      new LinkFieldDef("fileAsset", "LN01", "DataRef", "assets", "FileAsset"),
      new LinkFieldDef("internalAsset", "LN01", "DataRef", "assets", "InternalAsset"),
      new CalculatedFieldDef("downloadLink", "StringLink"),
      new ValueFieldDef("contentType", "String"),
      new ValueFieldDef("byteSize", "Long"),
      new CalculatedFieldDef("fileSize", "String"),
      new ValueFieldDef("fileId", "String"),
      new ValueFieldDef("complete", "Boolean"),
      new LinkFieldDef("externalAsset", "LN01", "DataRef", "assets", "ExternalAsset")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllAssets"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByFileIdEq",
        new FieldOperator("fileId", "LIKE")
      ),
      new FinderDef("findByFileAssetEq",
        new FieldOperator("fileAsset", "=")
      ),
      new FinderDef("findByInternalAssetEq",
        new FieldOperator("internalAsset", "=")
      ),
      new FinderDef("findByExternalAssetEq",
        new FieldOperator("externalAsset", "=")
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
          "type",
          "fileAsset",
          "internalAsset",
          "contentType",
          "byteSize",
          "fileId",
          "complete",
          "externalAsset"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "type",
          "downloadLink"
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
        "downloadLink",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "type",
          "downloadLink",
          "contentType",
          "fileSize"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
      new CommandDef("registerExternalAsset", false,
        new IFieldDef[] {
          new ValueFieldDef("uri", "StringLink")
        })
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_ExternalAsset() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.assets", "ExternalAsset");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("uri", "StringLink"),
      new ValueFieldDef("byteSize", "Long"),
      new ValueFieldDef("contentType", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllExternalAssets"),
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
          "uri",
          "byteSize",
          "contentType"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "uri",
          "byteSize",
          "contentType"
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

  private static DataElementDef getDataElementDef_FileAsset() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.assets", "FileAsset");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("uploadUri", "StringFile")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllFileAssets"),
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
          "uploadUri"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "uploadUri"
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

  private static DataElementDef getDataElementDef_InternalAsset() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.assets", "InternalAsset");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllInternalAssets"),
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
          "name"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name"
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

  private static DataElementDef getDataElementDef_InternalAssetChunk() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.assets", "InternalAssetChunk");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("content", "byte[]"),
      new LinkFieldDef("internalAsset", "LN02", "DataRef", "assets", "InternalAsset"),
      new ValueFieldDef("byteSize", "Integer"),
      new ValueFieldDef("position", "Integer"),
      new ValueFieldDef("isLast", "Boolean")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllInternalAssetChunks"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByInternalAssetEq",
        new FieldOperator("internalAsset", "=")
      ),
      new FinderDef("findByInternalAssetEq_PositionEq",
        new FieldOperator("internalAsset", "="),
        new FieldOperator("position", "=")
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
          "content",
          "internalAsset",
          "byteSize",
          "position",
          "isLast"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "internalAsset",
          "byteSize",
          "position"
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

  private static DataElementDef getDataElementDef_RemoteAsset() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.assets", "RemoteAsset");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("url", "StringLink")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllRemoteAssets"),
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
          "url"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "url"
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
