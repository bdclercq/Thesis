package net.democritus.acl;

import net.democritus.sys.Context;
import net.democritus.sys.CrudsResult;
import net.democritus.sys.DataRef;
import net.democritus.sys.UserContext;
import net.democritus.usr.ProfileLocalAgent;
import net.democritus.usr.UserContextWithProfile;
import net.democritus.usr.UserContextWithUserGroups;
import net.democritus.usr.UserGroupLocalAgent;
import net.democritus.usr.UserLocalAgent;

import java.util.HashSet;
import java.util.Set;

// @feature:authentication
public class AccountInformationManager {

  private final Context context;
  private final UserContext userContext;

  public AccountInformationManager(Context context) {
    this.context = context;
    this.userContext = context.getContext(UserContext.class).orElse(UserContext.NO_USER_CONTEXT);
  }

  public DataRef getUser() throws Exception {
    UserLocalAgent userLocalAgent = UserLocalAgent.getUserAgent(context);
    CrudsResult<DataRef> crudsResult = userLocalAgent.getDataRef(userContext.getUserName());
    if (crudsResult.isError() || crudsResult.getValue().getId() == 0) {
      throw new Exception("error finding user with name " + userContext.getUserName());
    }
    return crudsResult.getValue();
  }

  public DataRef getProfile() throws Exception {
    String profileName = "";
    if (userContext instanceof UserContextWithProfile) {
      profileName = ((UserContextWithProfile) userContext).getProfileName();
    }
    if (profileName == null || "".equals(profileName)) {
      return null;
    }
    ProfileLocalAgent agent = ProfileLocalAgent.getProfileAgent(context);
    CrudsResult<DataRef> crudsResult = agent.getDataRef(profileName);
    if (crudsResult.isError() || crudsResult.getValue().getId() == 0) {
      return null;
    }
    return crudsResult.getValue();
  }

  public Set<String> getUserGroups() throws Exception {
    Set<String> userGroups = new HashSet<>();
    if (userContext instanceof UserContextWithUserGroups) {
      userGroups = ((UserContextWithUserGroups) userContext).getUserGroups();
    }
    return userGroups;
  }

  public DataRef getUserGroup(String userGroupName) throws Exception {
    UserGroupLocalAgent userGroupLocalAgent = UserGroupLocalAgent.getUserGroupAgent(context);
    CrudsResult<DataRef> crudsResult = userGroupLocalAgent.getDataRef(userGroupName);
    if (crudsResult.isError() || crudsResult.getValue().getId() == 0) {
      throw new Exception("error finding user with name " + userGroupName);
    }
    return crudsResult.getValue();
  }

}
