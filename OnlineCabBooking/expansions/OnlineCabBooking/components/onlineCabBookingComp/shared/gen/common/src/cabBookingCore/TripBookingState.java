package cabBookingCore;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import net.democritus.sys.IStateDef;

// @anchor:imports:start
// @anchor:imports:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public enum TripBookingState implements IStateDef {
  CREATED("Created"),
  TO_BE_VALIDATED("To be validated"),
  VALIDATING("Validating"),
  VALID("Valid"),
  INVALID("Invalid"),
  CREATING_INVOICE("Creating invoice"),
  INVOICE_CREATED("Invoice created"),
  INVOICE_FAILED("Invoice failed"),
  NOT_MAPPED("<not mapped>");

  private String status;

  // @anchor:variables:start
  // @anchor:variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  TripBookingState(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean isSameAs(String status) {
    return this.status.equals(status);
  }

  public static TripBookingState getTripBookingState(String status) {
    for (TripBookingState tripBookingState : values()) {
      if (tripBookingState.isSameAs(status)) {
        return tripBookingState;
      }
    }
    return NOT_MAPPED;
  }

  // @anchor:methods:start
  // @anchor:methods:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
