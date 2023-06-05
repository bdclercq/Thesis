package net.democritus.web.common;

import com.opensymphony.xwork2.ActionContext;
import net.democritus.acl.ProfileAccessRights;
import net.democritus.sys.Context;
import net.democritus.sys.SearchResult;
import net.democritus.sys.UserContext;
import net.democritus.sys.search.SearchDetails;
import net.democritus.usr.UserAgent;
import net.democritus.usr.UserContextWithProfile;
import net.democritus.usr.UserDetails;
import net.democritus.usr.UserFindByNameEqDetails;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

// @feature:authentication
public class LoginUserSessionHelper {

  public void createUserSession(UserContext userContext) {
    createUserSession(Context.from(userContext));
  }

  public void createUserSession(Context context) {
    Map<String, Object> session = ActionContext.getContext().getSession();
    UserContext userContext = context.getContext(UserContext.class).orElse(UserContext.NO_USER_CONTEXT);
    SearchResult<UserDetails> searchResult = findUserDetails(userContext);

    session.put(SessionKey.CONTEXT.getKey(), context);
    configureGeneralSessionSettings(session, userContext);

    if (searchResult.getFirst().isDefined()) {
      UserDetails value = searchResult.getFirst().getValue();
      configureSessionWithUserDetails(session, value);
    } else {
      configureSessionWithUserContext(session, userContext);
    }
  }

  private void configureGeneralSessionSettings(Map<String, Object> session, UserContext userContext) {
    session.put(SessionKey.USERCONTEXT.getKey(), userContext);
    session.put("valid", "yes");

    session.put("language", userContext.getLanguage());

    // reset access rights
    session.put(SessionKey.DATA_ACCESS_RIGHTS_MAP_NAME.getKey(), new HashMap<String, ProfileAccessRights>());
    session.put(SessionKey.TASK_ACCESS_RIGHTS_MAP_NAME.getKey(), new HashMap<String, ProfileAccessRights>());
  }

  private void configureSessionWithUserDetails(Map<String, Object> session, UserDetails userDetails) {
    String userName = userDetails.getName();

    setUserSessionTimeOut(userDetails);

    session.put(SessionAttribute.USERNAME, userName);
    session.put("fullName", userDetails.getFullName());
    session.put("userId", userDetails.getId());
    if (userDetails.getAccountDetails() != null) {
      session.put("accountName", userDetails.getAccountDetails().getName());
      session.put("accountId", userDetails.getAccountDetails().getId());
      session.put("style", userDetails.getAccountDetails().getStyle());
    } else {
      session.put("accountName", "");
      session.put("accountId", "");
      session.put("style", "");
    }
    if (userDetails.getProfileDetails() != null) {
      session.put("role", userDetails.getProfileDetails().getName());
      session.put("profileName", userDetails.getProfileDetails().getName());
      session.put("profileId", userDetails.getProfileDetails().getId());
    } else {
      session.put("role", "");
      session.put("profileName", "");
      session.put("profileId", "");
    }
  }

  private void configureSessionWithUserContext(Map<String, Object> session, UserContext userContext) {
    String userName = userContext.getUserName();

    session.put(SessionAttribute.USERNAME, userName);

    if (userContext instanceof UserContextWithProfile) {
      String profileName = ((UserContextWithProfile) userContext).getProfileName();
      session.put("role", profileName);
      session.put("profileName", profileName);
    } else {
      session.put("role", "");
      session.put("profileName", "");
    }
  }

  private SearchResult<UserDetails> findUserDetails(UserContext userContext) {
    UserFindByNameEqDetails finder = new UserFindByNameEqDetails();
    finder.setName(userContext.getUserName());
    SearchDetails<UserFindByNameEqDetails> searchDetails = new SearchDetails<>(finder);
    searchDetails.setProjection("details");
    searchDetails.setSkipCount(true);
    return UserAgent.getUserAgent(Context.emptyContext()).find(searchDetails);
  }

  public void createTemporaryUserSession(UserContext userContext) {
    Map<String, Object> session = ActionContext.getContext().getSession();
    String userName = userContext.getUserName();
    session.put(SessionAttribute.USERNAME, userName);
    session.put("valid", "yes");
  }

  private void setUserSessionTimeOut(UserDetails userDetails) {
    HttpSession httpSession = ServletActionContext.getRequest().getSession();
    httpSession.setMaxInactiveInterval(userDetails.getTimeout());
  }

}
