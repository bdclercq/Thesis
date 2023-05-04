package net.democritus.acl;

import java.io.Serializable;

// @feature:authorization
public interface AccessRightStrategy extends Serializable {

  /**
   * Returns the default right authorization for the strategy.
   */
  boolean getDefaultRight();

  /**
   * Combines multiple right authorization values for the strategy. This typically means combining multiple values that
   * apply to a single right.
   *
   * @param rights The right authorization values.
   * @return True if authorized.
   */
  boolean combineRights(boolean... rights);

}
