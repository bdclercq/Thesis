package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.TaskPerformer;
import net.democritus.sys.TaskPerformerByDataRef;
import cabBookingCore.TripBookingDetails;
// anchor:custom-imports:start
// anchor:custom-imports:end

/**
 * Remote interface for the session bean MakeTripBookingInvoice.
 */

public interface MakeTripBookingInvoiceRemote extends TaskPerformer<Void,TripBookingDetails>, TaskPerformerByDataRef<Void> {

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}

