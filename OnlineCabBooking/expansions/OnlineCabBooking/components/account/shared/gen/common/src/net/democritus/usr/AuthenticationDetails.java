// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.usr;

import java.io.Serializable;

public class AuthenticationDetails<T> implements Serializable {

  private T details;

  public T getDetails() {
    return details;
  }

  public void setDetails(T details) {
    this.details = details;
  }

  /**
   * Constructs a new {@link AuthenticationDetails} object with the same values, but a different given details object.
   *
   * @param details    The given details object.
   * @param <TDetails> The type of the given details object.
   * @return The new object.
   */
  public <TDetails> AuthenticationDetails<TDetails> construct(TDetails details) {
    AuthenticationDetails<TDetails> authenticationDetails = new AuthenticationDetails<>();
    authenticationDetails.setDetails(details);

    return authenticationDetails;
  }

  /**
   * Constructs a new {@link AuthenticationDetails} object for a given details object.
   *
   * @param details    The given details object.
   * @param <TDetails> The type of the given details object.
   * @return The new object.
   */
  public static <TDetails> AuthenticationDetails<TDetails> from(TDetails details) {
    AuthenticationDetails<TDetails> authenticationDetails = new AuthenticationDetails<>();
    authenticationDetails.setDetails(details);

    return authenticationDetails;
  }

}
