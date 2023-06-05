package net.democritus.web.interceptor;

import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.UserContext;
import net.democritus.web.common.SessionKey;

import java.util.Map;

public class ActionClassFinder {

  private final Map<String, Object> session;

  public ActionClassFinder(Map<String, Object> session) {
    this.session = session;
  }

  public String getActionClassName(String actionClassKey, String defaultValue) {

    Object actionImpl = session.get(actionClassKey);
    if (actionImpl != null) {
      return actionImpl.toString();
    }

    ParamTargetValueAgent pTVAgent = ParamTargetValueAgent.getParamTargetValueAgent(getUserContext());
    String paramTargetValue = pTVAgent.getParamTargetValue(actionClassKey, null);
    if (paramTargetValue.isEmpty()) {
      return defaultValue;
    }
    return paramTargetValue;
  }

  private UserContext getUserContext() {
    UserContext userContext = (UserContext) session.get(SessionKey.USERCONTEXT.getKey());
    if (userContext != null) {
      return userContext;
    } else {
      return UserContext.NO_USER_CONTEXT;
    }
  }

}
