package onlineCabBookingComp.metadata;

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
  private static String COMPONENT_NAME = "onlineCabBookingComp";

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
    dataElementDefs.add(getDataElementDef_Address());
    dataElementDefs.add(getDataElementDef_Cab());
    dataElementDefs.add(getDataElementDef_CarType());
    dataElementDefs.add(getDataElementDef_Customer());
    dataElementDefs.add(getDataElementDef_Driver());
    dataElementDefs.add(getDataElementDef_Payment());
    dataElementDefs.add(getDataElementDef_Person());
    dataElementDefs.add(getDataElementDef_TripBooking());
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
  private static DataElementDef getDataElementDef_Address() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "Address");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("state", "String"),
      new ValueFieldDef("city", "String"),
      new ValueFieldDef("pincode", "String"),
      new ValueFieldDef("street", "String"),
      new ValueFieldDef("houseNumber", "Integer")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllAddresss"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByPincodeEq",
        new FieldOperator("pincode", "LIKE")
      ),
      new FinderDef("findByCityEq",
        new FieldOperator("city", "LIKE")
      ),
      new FinderDef("findByStateEq",
        new FieldOperator("state", "LIKE")
      ),
      new FinderDef("findByStreetEq",
        new FieldOperator("street", "LIKE")
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
          "state",
          "city",
          "pincode",
          "street",
          "houseNumber"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "state",
          "street",
          "houseNumber"
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

  private static DataElementDef getDataElementDef_Cab() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "Cab");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("ratePerKm", "Integer"),
      new LinkFieldDef("carType", "LN01", "DataRef", "onlineCabBookingComp", "CarType"),
      new LinkFieldDef("driver", "LN01", "DataRef", "onlineCabBookingComp", "Driver")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllCabs"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByDriverEq",
        new FieldOperator("driver", "=")
      ),
      new FinderDef("findByCarTypeEq",
        new FieldOperator("carType", "=")
      ),
      new FinderDef("findAllCab")
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "ratePerKm",
          "carType",
          "driver"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "ratePerKm",
          "carType",
          "driver"
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

  private static DataElementDef getDataElementDef_CarType() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "CarType");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("name", "String")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllCarTypes"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findAllCarType")
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

  private static DataElementDef getDataElementDef_Customer() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "Customer");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("journeyStatus", "Boolean"),
      new LinkFieldDef("person", "LN01", "DataRef", "onlineCabBookingComp", "Person")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllCustomers"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByUsername"),
      new FinderDef("findAllCustomer")
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "journeyStatus",
          "person"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "journeyStatus",
          "person"
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

  private static DataElementDef getDataElementDef_Driver() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "Driver");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("licenseNo", "Integer"),
      new ValueFieldDef("rating", "Double"),
      new ValueFieldDef("isAvailable", "Boolean"),
      new LinkFieldDef("cab", "LN01", "DataRef", "onlineCabBookingComp", "Cab"),
      new LinkFieldDef("tripBooking", "LN01", "DataRef", "onlineCabBookingComp", "TripBooking")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllDrivers"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByLicenseNoEq",
        new FieldOperator("licenseNo", "=")
      ),
      new FinderDef("findByRatingEq",
        new FieldOperator("rating", "=")
      ),
      new FinderDef("findByIsAvailableEq",
        new FieldOperator("isAvailable", "=")
      ),
      new FinderDef("findByTripBookingEq",
        new FieldOperator("tripBooking", "=")
      ),
      new FinderDef("findByCabEq",
        new FieldOperator("cab", "=")
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
          "licenseNo",
          "rating",
          "isAvailable",
          "cab",
          "tripBooking"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "licenseNo",
          "rating",
          "isAvailable",
          "cab",
          "tripBooking"
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

  private static DataElementDef getDataElementDef_Payment() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "Payment");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("statusPayed", "Boolean")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllPayments"),
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
          "statusPayed"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "statusPayed"
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

  private static DataElementDef getDataElementDef_Person() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "Person");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new ValueFieldDef("username", "String"),
      new ValueFieldDef("password", "String"),
      new ValueFieldDef("email", "String"),
      new ValueFieldDef("mobile", "String"),
      new LinkFieldDef("address", "LN01", "DataRef", "onlineCabBookingComp", "Address")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllPersons"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByEmailEq",
        new FieldOperator("email", "LIKE")
      ),
      new FinderDef("findByAddressEq",
        new FieldOperator("address", "=")
      ),
      new FinderDef("findByMobileEq",
        new FieldOperator("mobile", "LIKE")
      ),
      new FinderDef("findByUsernameEq",
        new FieldOperator("username", "LIKE")
      ),
      new FinderDef("findAllPerson")
    };
    dataElement.setFinders(finders);

    ProjectionDef[] projections = new ProjectionDef[] {
      new ProjectionDef(
        "details",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "username",
          "password",
          "email",
          "mobile",
          "address"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "username",
          "email",
          "mobile",
          "address"
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

  private static DataElementDef getDataElementDef_TripBooking() {
    DataElementDef dataElement = new DataElementDef(COMPONENT_NAME, "cabBookingCore", "TripBooking");
    IFieldDef[] fields = new IFieldDef[] {
      new ValueFieldDef("id", "Long"),
      new LinkFieldDef("customer", "LN01", "DataRef", "onlineCabBookingComp", "Customer"),
      new LinkFieldDef("driver", "LN01", "DataRef", "onlineCabBookingComp", "Driver"),
      new LinkFieldDef("fromLocation", "LN01", "DataRef", "onlineCabBookingComp", "Address"),
      new LinkFieldDef("toLocation", "LN01", "DataRef", "onlineCabBookingComp", "Address"),
      new ValueFieldDef("fromDateTime", "Date"),
      new ValueFieldDef("toDateTime", "Date"),
      new ValueFieldDef("km", "Double"),
      new ValueFieldDef("totalAmount", "Double"),
      new LinkFieldDef("payment", "LN01", "DataRef", "onlineCabBookingComp", "Payment")
    };
    dataElement.setFields(fields);

    FinderDef[] finders = new FinderDef[] {
      new FinderDef("findAllTripBookings"),
      new FinderDef("findByNameEq",
        new FieldOperator("name", "LIKE")
      ),
      new FinderDef("findByCustomerEq",
        new FieldOperator("customer", "=")
      ),
      new FinderDef("findByDriverEq",
        new FieldOperator("driver", "=")
      ),
      new FinderDef("findAllTripBooking"),
      new FinderDef("findByFromDateTimeEq",
        new FieldOperator("fromDateTime", "=")
      ),
      new FinderDef("findByToDateTimeEq",
        new FieldOperator("toDateTime", "=")
      ),
      new FinderDef("findByCustomerEq_FromDateTimeEq",
        new FieldOperator("customer", "="),
        new FieldOperator("fromDateTime", "=")
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
          "customer",
          "driver",
          "fromLocation",
          "toLocation",
          "fromDateTime",
          "toDateTime",
          "km",
          "totalAmount",
          "payment"
        }
      ),
      new ProjectionDef(
        "info",
        new CalculatedFieldDef[] {
        },
        new String[] {
          "id",
          "customer",
          "driver",
          "fromLocation",
          "toLocation",
          "fromDateTime",
          "toDateTime",
          "km",
          "totalAmount",
          "payment"
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
