package validation.metadata;

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
  private static String COMPONENT_NAME = "validation";

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
    dataElementDefs.add(getDataElementDef_Validation());
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
  private static DataElementDef getDataElementDef_Validation() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.validation", "Validation");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllValidations"),
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
  // anchor:data-element-getters:end

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
