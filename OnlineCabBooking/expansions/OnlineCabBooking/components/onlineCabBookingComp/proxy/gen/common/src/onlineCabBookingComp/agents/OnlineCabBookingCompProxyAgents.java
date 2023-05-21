package onlineCabBookingComp.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import cabBookingCore.AddressAgent;
import cabBookingCore.AddressAgentIf;
import cabBookingCore.CabAgent;
import cabBookingCore.CabAgentIf;
import cabBookingCore.CarTypeAgent;
import cabBookingCore.CarTypeAgentIf;
import cabBookingCore.CustomerAgent;
import cabBookingCore.CustomerAgentIf;
import cabBookingCore.DriverAgent;
import cabBookingCore.DriverAgentIf;
import cabBookingCore.InvoiceAgent;
import cabBookingCore.InvoiceAgentIf;
import cabBookingCore.PaymentAgent;
import cabBookingCore.PaymentAgentIf;
import cabBookingCore.PersonAgent;
import cabBookingCore.PersonAgentIf;
import cabBookingCore.TripBookingAgent;
import cabBookingCore.TripBookingAgentIf;
import cabBookingCore.TripBookingTaskStatusAgent;
import cabBookingCore.TripBookingTaskStatusAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class OnlineCabBookingCompProxyAgents implements OnlineCabBookingCompAgents {

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
    return new OnlineCabBookingCompProxyAgents(context);
  }

  @Deprecated
  public static OnlineCabBookingCompAgents getOnlineCabBookingCompAgents(UserContext userContext) {
    return new OnlineCabBookingCompProxyAgents(userContext);
  }

  public OnlineCabBookingCompProxyAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public OnlineCabBookingCompProxyAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public AddressAgentIf getAddressAgent() {
    if (addressAgent == null) {
      addressAgent = AddressAgent.getAddressAgent(context);
    }
    return addressAgent;
  }

  public CabAgentIf getCabAgent() {
    if (cabAgent == null) {
      cabAgent = CabAgent.getCabAgent(context);
    }
    return cabAgent;
  }

  public CarTypeAgentIf getCarTypeAgent() {
    if (carTypeAgent == null) {
      carTypeAgent = CarTypeAgent.getCarTypeAgent(context);
    }
    return carTypeAgent;
  }

  public CustomerAgentIf getCustomerAgent() {
    if (customerAgent == null) {
      customerAgent = CustomerAgent.getCustomerAgent(context);
    }
    return customerAgent;
  }

  public DriverAgentIf getDriverAgent() {
    if (driverAgent == null) {
      driverAgent = DriverAgent.getDriverAgent(context);
    }
    return driverAgent;
  }

  public InvoiceAgentIf getInvoiceAgent() {
    if (invoiceAgent == null) {
      invoiceAgent = InvoiceAgent.getInvoiceAgent(context);
    }
    return invoiceAgent;
  }

  public PaymentAgentIf getPaymentAgent() {
    if (paymentAgent == null) {
      paymentAgent = PaymentAgent.getPaymentAgent(context);
    }
    return paymentAgent;
  }

  public PersonAgentIf getPersonAgent() {
    if (personAgent == null) {
      personAgent = PersonAgent.getPersonAgent(context);
    }
    return personAgent;
  }

  public TripBookingAgentIf getTripBookingAgent() {
    if (tripBookingAgent == null) {
      tripBookingAgent = TripBookingAgent.getTripBookingAgent(context);
    }
    return tripBookingAgent;
  }

  public TripBookingTaskStatusAgentIf getTripBookingTaskStatusAgent() {
    if (tripBookingTaskStatusAgent == null) {
      tripBookingTaskStatusAgent = TripBookingTaskStatusAgent.getTripBookingTaskStatusAgent(context);
    }
    return tripBookingTaskStatusAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
