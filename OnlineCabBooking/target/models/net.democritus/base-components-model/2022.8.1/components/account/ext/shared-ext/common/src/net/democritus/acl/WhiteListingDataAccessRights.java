package net.democritus.acl;

/**
 * As opposed to the default DataAccessRights behaviour, instances of this class will reject all actions
 * that are not explicitly defined.
 *
 * @deprecated This class should be replaced by passing an {@link DenyDefaultAccessRightStrategy} instance to the
 *             constructor of {@link DataAccessRights}. This is analogous to the {@link TaskAccessRights} class, which
 *             has no subclass. E.g.: {@code new DataAccessRights(new AllowListAccessRightStrategy())}.
 */
// @feature:authorization
@Deprecated
public class WhiteListingDataAccessRights extends DataAccessRights {

  public WhiteListingDataAccessRights() {
    super(new DenyDefaultAccessRightStrategy());
  }

}
