package net.democritus.usr;

import net.democritus.acl.AccessRightCache;
import net.democritus.sys.BasicUserContext;

import java.util.HashSet;
import java.util.Set;

// @feature:authentication
public class ExtendedUserContext extends BasicUserContext implements UserContextWithProfile, UserContextWithUserGroups,
    UserContextWithRights, InternalUserContext {

  private String profileName;
  private Set<String> userGroups;
  private final AccessRightCache accessRightCache;

  public ExtendedUserContext(UserDetails userDetails, String profile, Set<String> userGroupsFromUser) {
    super(userDetails.getDataRef());
    this.setProfileName(profile);
    this.setUserGroups(new HashSet<String>(userGroupsFromUser));
    this.accessRightCache = new AccessRightCache();
  }

  @Override
  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  @Override
  public Set<String> getUserGroups() {
    return userGroups;
  }

  public void setUserGroups(Set<String> userGroups) {
    this.userGroups = userGroups;
  }

  public AccessRightCache getAccessRights() {
    return accessRightCache;
  }

}

