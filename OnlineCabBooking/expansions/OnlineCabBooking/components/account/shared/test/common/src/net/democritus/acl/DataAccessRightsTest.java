package net.democritus.acl;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataAccessRightsTest {

  @Test
  public void defaultRights() {
    final DataAccessRights dataAccessRights = new DataAccessRights();
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertTrue(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void allowDefaultRights() {
    final DataAccessRights dataAccessRights = new DataAccessRights(new AllowDefaultAccessRightStrategy());
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertTrue(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void denyDefaultRights() {
    final DataAccessRights dataAccessRights = new DataAccessRights(new DenyDefaultAccessRightStrategy());
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertFalse(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void defaultRightsWildcard() {
    final DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.setCanAll(false);

    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertFalse(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void allowDefaultRightsWildcard() {
    final DataAccessRights dataAccessRights = new DataAccessRights(new AllowDefaultAccessRightStrategy());
    dataAccessRights.setCanAll(false);

    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertFalse(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void denyDefaultRightsWildcard() {
    final DataAccessRights dataAccessRights = new DataAccessRights(new DenyDefaultAccessRightStrategy());
    dataAccessRights.setCanAll(true);

    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertTrue(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void defaultRightsIndividual() {
    final DataAccessRights dataAccessRights = new DataAccessRights();
    dataAccessRights.setCanView(false);
    dataAccessRights.addAction(DataAccessFunctionality.CREATE.getFunctionality(), false);
    dataAccessRights.addAction("randomRight", false);

    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertFalse(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void allowDefaultRightsIndividual() {
    final DataAccessRights dataAccessRights = new DataAccessRights(new AllowDefaultAccessRightStrategy());
    dataAccessRights.setCanView(false);
    dataAccessRights.addAction(DataAccessFunctionality.CREATE.getFunctionality(), false);
    dataAccessRights.addAction("randomRight", false);

    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertFalse(dataAccessRights.isAllowed("randomRight"));
  }

  @Test
  public void denyDefaultRightsIndividual() {
    final DataAccessRights dataAccessRights = new DataAccessRights(new DenyDefaultAccessRightStrategy());
    dataAccessRights.setCanView(true);
    dataAccessRights.addAction(DataAccessFunctionality.CREATE.getFunctionality(), true);
    dataAccessRights.addAction("randomRight", true);

    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.CREATE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.DELETE));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.ENTRY));
    assertFalse(dataAccessRights.isAllowed(DataAccessFunctionality.MODIFY));
    assertTrue(dataAccessRights.isAllowed(DataAccessFunctionality.VIEW));
    assertTrue(dataAccessRights.isAllowed("randomRight"));
  }

}