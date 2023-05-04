package net.democritus.acl;

/**
 * This strategy denies rights by default. Rights will have to be defined in the access rights manager to allow them,
 * in order to override the default of this strategy.
 */

// @feature:authorization
public final class DenyDefaultAccessRightStrategy implements AccessRightStrategy {

  @Override
  public boolean getDefaultRight() {
    return false;
  }

  @Override
  public boolean combineRights(boolean... rights) {
    boolean combinedRight = getDefaultRight();
    if (rights.length != 0) {
      for (boolean right : rights) {
        combinedRight |= right;
      }
    }
    return combinedRight;
  }

}
