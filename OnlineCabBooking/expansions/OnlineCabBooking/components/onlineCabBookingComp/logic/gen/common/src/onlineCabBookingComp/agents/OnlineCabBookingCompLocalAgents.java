package onlineCabBookingComp.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import cabBookingCore.AddressLocalAgent;
import cabBookingCore.AddressAgentIf;
import cabBookingCore.CabLocalAgent;
import cabBookingCore.CabAgentIf;
import cabBookingCore.CarTypeLocalAgent;
import cabBookingCore.CarTypeAgentIf;
import cabBookingCore.CustomerLocalAgent;
import cabBookingCore.CustomerAgentIf;
import cabBookingCore.DriverLocalAgent;
import cabBookingCore.DriverAgentIf;
import cabBookingCore.InvoiceLocalAgent;
import cabBookingCore.InvoiceAgentIf;
import cabBookingCore.PaymentLocalAgent;
import cabBookingCore.PaymentAgentIf;
import cabBookingCore.PersonLocalAgent;
import cabBookingCore.PersonAgentIf;
import cabBookingCore.TripBookingLocalAgent;
import cabBookingCore.TripBookingAgentIf;
import cabBookingCore.TripBookingTaskStatusLocalAgent;
import cabBookingCore.TripBookingTaskStatusAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class OnlineCabBookingCompLocalAgents implements OnlineCabBookingCompAgents {

  private final Context context;

  // anchor:data-element-agent-variables:start
  private AddressAgentIf addressAgent;
  private CabAgentIf cabAgent;
  private CarTypeAgentIf carTypeAgent;
  private CustomerAgentIf customerAgent;
  private DriverAgentIf driverAgent;
  private InvoiceAgentIf invoiceAgent;
  private PaymentAgentIf paymentAgent;
  private PersonAgentIf personAgent;
  private TripBookingAgentIf tripBookingAgent;
  private TripBookingTaskStatusAgentIf tripBookingTaskStatusAgent;
  // anchor:data-element-agent-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static OnlineCabBookingCompAgents getOnlineCabBookingCompAgents(Context context) {
    return new OnlineCabBookingCompLocalAgents(context);
  }

  @Deprecated
  public static OnlineCabBookingCompAgents getOnlineCabBookingCompAgents(UserContext userContext) {
    return new OnlineCabBookingCompLocalAgents(userContext);
  }

  public OnlineCabBookingCompLocalAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public OnlineCabBookingCompLocalAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public AddressAgentIf getAddressAgent() {
    if (addressAgent == null) {
      addressAgent = AddressLocalAgent.getAddressAgent(context);
    }
    return addressAgent;
  }

  public CabAgentIf getCabAgent() {
    if (cabAgent == null) {
      cabAgent = CabLocalAgent.getCabAgent(context);
    }
    return cabAgent;
  }

  public CarTypeAgentIf getCarTypeAgent() {
    if (carTypeAgent == null) {
      carTypeAgent = CarTypeLocalAgent.getCarTypeAgent(context);
    }
    return carTypeAgent;
  }

  public CustomerAgentIf getCustomerAgent() {
    if (customerAgent == null) {
      customerAgent = CustomerLocalAgent.getCustomerAgent(context);
    }
    return customerAgent;
  }

  public DriverAgentIf getDriverAgent() {
    if (driverAgent == null) {
      driverAgent = DriverLocalAgent.getDriverAgent(context);
    }
    return driverAgent;
  }

  public InvoiceAgentIf getInvoiceAgent() {
    if (invoiceAgent == null) {
      invoiceAgent = InvoiceLocalAgent.getInvoiceAgent(context);
    }
    return invoiceAgent;
  }

  public PaymentAgentIf getPaymentAgent() {
    if (paymentAgent == null) {
      paymentAgent = PaymentLocalAgent.getPaymentAgent(context);
    }
    return paymentAgent;
  }

  public PersonAgentIf getPersonAgent() {
    if (personAgent == null) {
      personAgent = PersonLocalAgent.getPersonAgent(context);
    }
    return personAgent;
  }

  public TripBookingAgentIf getTripBookingAgent() {
    if (tripBookingAgent == null) {
      tripBookingAgent = TripBookingLocalAgent.getTripBookingAgent(context);
    }
    return tripBookingAgent;
  }

  public TripBookingTaskStatusAgentIf getTripBookingTaskStatusAgent() {
    if (tripBookingTaskStatusAgent == null) {
      tripBookingTaskStatusAgent = TripBookingTaskStatusLocalAgent.getTripBookingTaskStatusAgent(context);
    }
    return tripBookingTaskStatusAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
