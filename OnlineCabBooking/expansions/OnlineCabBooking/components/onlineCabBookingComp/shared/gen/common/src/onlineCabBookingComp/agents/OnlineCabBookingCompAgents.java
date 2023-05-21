package onlineCabBookingComp.agents;

import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import cabBookingCore.AddressAgentIf;
import cabBookingCore.CabAgentIf;
import cabBookingCore.CarTypeAgentIf;
import cabBookingCore.CustomerAgentIf;
import cabBookingCore.DriverAgentIf;
import cabBookingCore.InvoiceAgentIf;
import cabBookingCore.PaymentAgentIf;
import cabBookingCore.PersonAgentIf;
import cabBookingCore.TripBookingAgentIf;
import cabBookingCore.TripBookingTaskStatusAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface OnlineCabBookingCompAgents {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:data-element-agent-getters:start
  AddressAgentIf getAddressAgent();
  CabAgentIf getCabAgent();
  CarTypeAgentIf getCarTypeAgent();
  CustomerAgentIf getCustomerAgent();
  DriverAgentIf getDriverAgent();
  InvoiceAgentIf getInvoiceAgent();
  PaymentAgentIf getPaymentAgent();
  PersonAgentIf getPersonAgent();
  TripBookingAgentIf getTripBookingAgent();
  TripBookingTaskStatusAgentIf getTripBookingTaskStatusAgent();
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
