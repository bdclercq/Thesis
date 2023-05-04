package account.metadata;

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
  private static String COMPONENT_NAME = "account";

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
    dataElementDefs.add(getDataElementDef_Account());
    dataElementDefs.add(getDataElementDef_Component());
    dataElementDefs.add(getDataElementDef_DataAccess());
    dataElementDefs.add(getDataElementDef_HelpInfo());
    dataElementDefs.add(getDataElementDef_Menu());
    dataElementDefs.add(getDataElementDef_MenuItem());
    dataElementDefs.add(getDataElementDef_Portal());
    dataElementDefs.add(getDataElementDef_Profile());
    dataElementDefs.add(getDataElementDef_Screen());
    dataElementDefs.add(getDataElementDef_ScreenProfile());
    dataElementDefs.add(getDataElementDef_User());
    dataElementDefs.add(getDataElementDef_UserGroup());
    // anchor:add-data-elements:end

    // anchor:custom-data-elements:start
    // anchor:custom-data-elements:end

    return dataElementDefs;
  }

  private static List<TaskElementDef> buildTaskElements() {
    List<TaskElementDef> taskElementDefs = new ArrayList<TaskElementDef>();

    // anchor:add-task-elements:start
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.acl", "Authentication", TransactionType.ATOMIC_INTERNAL));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.acl", "AuthorizationManager", TransactionType.ATOMIC_EXTERNAL));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.acl", "DataAuthorization", TransactionType.ATOMIC_INTERNAL));
    taskElementDefs.add(new TaskElementDef(COMPONENT_NAME, "net.democritus.acl", "TaskAuthorization", TransactionType.ATOMIC_INTERNAL));
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
  private static DataElementDef getDataElementDef_Account() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr", "Account");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("refId", "String"),
      new ValueFieldDef("fullName", "String"),
      new ValueFieldDef("address", "String"),
      new ValueFieldDef("zipCode", "String"),
      new ValueFieldDef("city", "String"),
      new ValueFieldDef("country", "String"),
      new ValueFieldDef("email", "String"),
      new ValueFieldDef("phone", "String"),
      new ValueFieldDef("style", "String"),
      new ValueFieldDef("status", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllAccounts"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByAddressEq",
        new FieldOperator("address", "LIKE")
      ),
      new FinderDef("findByCityEq",
        new FieldOperator("city", "LIKE")
      ),
      new FinderDef("findByCountryEq",
        new FieldOperator("country", "LIKE")
      ),
      new FinderDef("findByEmailEq",
        new FieldOperator("email", "LIKE")
      ),
      new FinderDef("findByFullNameEq",
        new FieldOperator("fullName", "LIKE")
      ),
      new FinderDef("findByRefIdEq",
        new FieldOperator("refId", "LIKE")
      ),
      new FinderDef("findByStatusEq",
        new FieldOperator("status", "LIKE")
      ),
      new FinderDef("findByZipCodeEq",
        new FieldOperator("zipCode", "LIKE")
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
          "refId",
          "fullName",
          "address",
          "zipCode",
          "city",
          "country",
          "email",
          "phone",
          "style",
          "status"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "refId",
          "fullName",
          "city",
          "status"
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

  private static DataElementDef getDataElementDef_Component() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.component", "Component");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllComponents"),
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

  private static DataElementDef getDataElementDef_DataAccess() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.acl", "DataAccess");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new LinkFieldDef("forProfile", "LN01", "DataRef", "account", "Profile"),
      new LinkFieldDef("forUser", "LN01", "DataRef", "account", "User"),
      new LinkFieldDef("userGroups", "LN03", "List", "account", "UserGroup"),
      new ValueFieldDef("element", "String"),
      new ValueFieldDef("target", "String"),
      new ValueFieldDef("functionality", "String"),
      new ValueFieldDef("authorized", "String"),
      new ValueFieldDef("lastModifiedAt", "Date"),
      new ValueFieldDef("enteredAt", "Date"),
      new ValueFieldDef("disabled", "String"),
      new LinkFieldDef("forUserGroup", "LN01", "DataRef", "account", "UserGroup")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllDataAccesss"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByElementEq",
        new FieldOperator("element", "LIKE")
      ),
      new FinderDef("findByElementEq_FunctionalityEq",
        new FieldOperator("element", "LIKE"),
        new FieldOperator("functionality", "LIKE")
      ),
      new FinderDef("findByElementEq_TargetEq",
        new FieldOperator("element", "LIKE"),
        new FieldOperator("target", "LIKE")
      ),
      new FinderDef("findByForProfileEq",
        new FieldOperator("forProfile", "=")
      ),
      new FinderDef("findByForProfileEq_ElementEq",
        new FieldOperator("element", "LIKE"),
        new FieldOperator("forProfile", "=")
      ),
      new FinderDef("findByForProfileEq_FunctionalityEq",
        new FieldOperator("forProfile", "="),
        new FieldOperator("functionality", "LIKE")
      ),
      new FinderDef("findByForUserEq",
        new FieldOperator("forUser", "=")
      ),
      new FinderDef("findByForUserEq_ElementEq",
        new FieldOperator("element", "LIKE"),
        new FieldOperator("forUser", "=")
      ),
      new FinderDef("findByForUserEq_FunctionalityEq",
        new FieldOperator("forUser", "="),
        new FieldOperator("functionality", "LIKE")
      ),
      new FinderDef("findByTargetEq",
        new FieldOperator("target", "LIKE")
      ),
      new FinderDef("findByForUserGroupEq_ElementEq",
        new FieldOperator("element", "LIKE"),
        new FieldOperator("forUserGroup", "=")
      ),
      new FinderDef("findByForUserEq_ElementEq_TargetEq_FunctionalityEq",
        new FieldOperator("forUser", "="),
        new FieldOperator("element", "LIKE"),
        new FieldOperator("target", "LIKE"),
        new FieldOperator("functionality", "LIKE")
      ),
      new FinderDef("findByForProfileEq_ElementEq_TargetEq_FunctionalityEq",
        new FieldOperator("forProfile", "="),
        new FieldOperator("element", "LIKE"),
        new FieldOperator("target", "LIKE"),
        new FieldOperator("functionality", "LIKE")
      ),
      new FinderDef("findByForUserGroupEq_ElementEq_TargetEq_FunctionalityEq",
        new FieldOperator("forUserGroup", "="),
        new FieldOperator("element", "LIKE"),
        new FieldOperator("target", "LIKE"),
        new FieldOperator("functionality", "LIKE")
      ),
      new FinderDef("findByForUserGroupEq",
        new FieldOperator("forUserGroup", "=")
      ),
      new FinderDef("findBySpecificationOrWildcard",
        new FieldOperator("forProfile", "="),
        new FieldOperator("forUserGroup", "="),
        new FieldOperator("forUser", "="),
        new FieldOperator("element", "LIKE"),
        new FieldOperator("target", "LIKE"),
        new FieldOperator("functionality", "LIKE")
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
          "forProfile",
          "forUser",
          "userGroups",
          "element",
          "target",
          "functionality",
          "authorized",
          "lastModifiedAt",
          "enteredAt",
          "disabled",
          "forUserGroup"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "forProfile",
          "element",
          "target",
          "functionality",
          "authorized"
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
        "query",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "element",
          "functionality",
          "target"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_HelpInfo() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.acl", "HelpInfo");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("description", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllHelpInfos"),
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
          "description"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "description"
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

  private static DataElementDef getDataElementDef_Menu() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr.menu", "Menu");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new LinkFieldDef("portal", "LN01", "DataRef", "account", "Portal"),
      new LinkFieldDef("profile", "LN01", "DataRef", "account", "Profile"),
      new LinkFieldDef("menuItems", "LN05", "List", "account", "MenuItem")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllMenus"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByPortalEq",
        new FieldOperator("portal", "=")
      ),
      new FinderDef("findByPortalEq_ProfileEq",
        new FieldOperator("portal", "="),
        new FieldOperator("profile", "=")
      ),
      new FinderDef("findByProfileEq",
        new FieldOperator("profile", "=")
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
          "portal",
          "profile",
          "menuItems"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "portal",
          "profile"
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

  private static DataElementDef getDataElementDef_MenuItem() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr.menu", "MenuItem");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new LinkFieldDef("menu", "LN01", "DataRef", "account", "Menu"),
      new LinkFieldDef("screen", "LN01", "DataRef", "account", "Screen"),
      new LinkFieldDef("menuItem", "LN01", "DataRef", "account", "MenuItem"),
      new LinkFieldDef("submenuItems", "LN05", "List", "account", "MenuItem"),
      new ValueFieldDef("sortOrder", "Integer")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllMenuItems"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByMenuEq",
        new FieldOperator("menu", "=")
      ),
      new FinderDef("findByMenuEq_ScreenEq",
        new FieldOperator("menu", "="),
        new FieldOperator("screen", "=")
      ),
      new FinderDef("findByMenuItemEq",
        new FieldOperator("menuItem", "=")
      ),
      new FinderDef("findByScreenEq",
        new FieldOperator("screen", "=")
      ),
      new FinderDef("findBySortOrderGt",
        new FieldOperator("sortOrder", ">")
      ),
      new FinderDef("findBySortOrderLt",
        new FieldOperator("sortOrder", "<")
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
          "menu",
          "screen",
          "menuItem",
          "submenuItems",
          "sortOrder"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "menu",
          "screen",
          "menuItem",
          "submenuItems",
          "sortOrder"
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

  private static DataElementDef getDataElementDef_Portal() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr", "Portal");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("version", "String"),
      new ValueFieldDef("description", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllPortals"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByNameEq_VersionEq",
        new FieldOperator("name", "LIKE"),
        new FieldOperator("version", "LIKE")
      ),
      new FinderDef("findByVersionEq",
        new FieldOperator("version", "LIKE")
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
          "version",
          "description"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "version"
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

  private static DataElementDef getDataElementDef_Profile() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr", "Profile");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new LinkFieldDef("screens", "LN03", "List", "account", "Screen"),
      new LinkFieldDef("userGroup", "LN01", "DataRef", "account", "UserGroup"),
      new ValueFieldDef("weight", "Integer")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllProfiles"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByUserGroupEq",
        new FieldOperator("userGroup", "=")
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
          "screens",
          "userGroup",
          "weight"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "screens",
          "userGroup",
          "weight"
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
          "weight"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_Screen() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr.menu", "Screen");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("link", "String"),
      new ValueFieldDef("sortOrder", "Integer"),
      new LinkFieldDef("component", "LN01", "DataRef", "account", "Component")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllScreens"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByComponentEq",
        new FieldOperator("component", "=")
      ),
      new FinderDef("findByLinkEq",
        new FieldOperator("link", "LIKE")
      ),
      new FinderDef("findByNameEq_ComponentEq",
        new FieldOperator("component", "="),
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findBySortOrderGt",
        new FieldOperator("sortOrder", ">")
      ),
      new FinderDef("findBySortOrderLt",
        new FieldOperator("sortOrder", "<")
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
          "link",
          "sortOrder",
          "component"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "link",
          "sortOrder",
          "component"
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

  private static DataElementDef getDataElementDef_ScreenProfile() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr.menu", "ScreenProfile");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new LinkFieldDef("screen", "LN01", "DataRef", "account", "Screen"),
      new LinkFieldDef("profile", "LN01", "DataRef", "account", "Profile"),
      new LinkFieldDef("screens", "LN03", "List", "account", "Screen")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllScreenProfiles"),
      new FinderDef("findByProfileEq",
        new FieldOperator("profile", "=")
      ),
      new FinderDef("findByScreenEq",
        new FieldOperator("screen", "=")
      ),
      new FinderDef("findByScreenEq_ProfileEq",
        new FieldOperator("profile", "="),
        new FieldOperator("screen", "=")
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
          "screen",
          "profile",
          "screens"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "screen",
          "profile",
          "screens"
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

  private static DataElementDef getDataElementDef_User() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr", "User");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("password", "StringPass"),
      new ValueFieldDef("fullName", "String"),
      new ValueFieldDef("email", "String"),
      new ValueFieldDef("mobile", "String"),
      new ValueFieldDef("language", "String"),
      new ValueFieldDef("firstName", "String"),
      new ValueFieldDef("lastName", "String"),
      new ValueFieldDef("persNr", "String"),
      new ValueFieldDef("lastModifiedAt", "Date"),
      new ValueFieldDef("enteredAt", "Date"),
      new ValueFieldDef("disabled", "String"),
      new ValueFieldDef("timeout", "Integer"),
      new LinkFieldDef("account", "LN01", "DataRef", "account", "Account"),
      new LinkFieldDef("profile", "LN01", "DataRef", "account", "Profile"),
      new LinkFieldDef("userGroups", "LN03", "List", "account", "UserGroup"),
      new ValueFieldDef("encryptedPassword", "StringPass")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllUsers"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByAccountEq",
        new FieldOperator("account", "=")
      ),
      new FinderDef("findByAccountEq_ProfileEq",
        new FieldOperator("account", "="),
        new FieldOperator("profile", "=")
      ),
      new FinderDef("findByFullNameEq",
        new FieldOperator("fullName", "LIKE")
      ),
      new FinderDef("findByPersNrEq",
        new FieldOperator("persNr", "LIKE")
      ),
      new FinderDef("findByProfileEq",
        new FieldOperator("profile", "=")
      ),
      new FinderDef("findByEmailEq",
        new FieldOperator("email", "LIKE")
      ),
      new FinderDef("findByNameEq_ProfileEq",
        new FieldOperator("name", "LIKE"),
        new FieldOperator("profile", "=")
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
          "password",
          "fullName",
          "email",
          "mobile",
          "language",
          "firstName",
          "lastName",
          "persNr",
          "lastModifiedAt",
          "enteredAt",
          "disabled",
          "timeout",
          "account",
          "profile",
          "userGroups",
          "encryptedPassword"
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
          "email",
          "language",
          "account",
          "profile",
          "userGroups"
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
        "input",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "password",
          "name"
        }
      ),
      new ProjectionDef(
        "detailsWithoutRefs",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "password",
          "fullName",
          "email",
          "mobile",
          "language",
          "firstName",
          "lastName",
          "persNr",
          "lastModifiedAt",
          "enteredAt",
          "disabled",
          "timeout",
          "encryptedPassword"
        }
      )
    };
    dataElement.setProjections(projections);

    CommandDef[] commands = new CommandDef[] {
      new CommandDef("changePassword", true,
        new IFieldDef[] {
          new ValueFieldDef("newPassword", "StringPass"),
          new ValueFieldDef("repeatNewPassword", "StringPass")
        })
    };
    dataElement.setCommands(commands);

    return dataElement;
  }

  private static DataElementDef getDataElementDef_UserGroup() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "net.democritus.usr", "UserGroup");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String"),
      new ValueFieldDef("type", "String"),
      new ValueFieldDef("lastModifiedAt", "Date"),
      new ValueFieldDef("enteredAt", "Date"),
      new ValueFieldDef("disabled", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllUserGroups"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByTypeEq",
        new FieldOperator("type", "LIKE")
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
          "lastModifiedAt",
          "enteredAt",
          "disabled"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "name",
          "type"
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
