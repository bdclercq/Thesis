package net.democritus.web.common;

import com.opensymphony.xwork2.ActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// @feature:unique-session
public class UniqueSessionKeeper {

  public Map getSessionMap(ActionContext actionContext) {
    Map<String, Object> application = actionContext.getApplication();
    Object sessionMap = application.get(SessionAttribute.SESSION_MAP);
    if (sessionMap == null) {
      return Collections.emptyMap();
    } else {
      return asMap(sessionMap);
    }
  }

  public Map getSessionMap(HttpSession httpSession) {
    ServletContext servletContext = httpSession.getServletContext();
    Object sessionMap = servletContext.getAttribute(SessionAttribute.SESSION_MAP);
    if (sessionMap == null) {
      return Collections.emptyMap();
    } else {
      return asMap(sessionMap);
    }
  }

  public Map getOrInitSessionMap(ActionContext actionContext) {
    Map<String, Object> application = actionContext.getApplication();
    Object sessionList = application.get(SessionAttribute.SESSION_MAP);
    if (sessionList == null) {
      return initSessionMap(application);
    } else {
      return asMap(sessionList);
    }
  }

  private Map initSessionMap(Map<String, Object> application) {
    Map map = new HashMap<String, HttpSession>();
    application.put(SessionAttribute.SESSION_MAP, map);
    return map;
  }

  private Map asMap(Object sessionList) {
    if (sessionList instanceof Map) {
      return (Map) sessionList;
    } else {
      throw new IllegalStateException(
          "Unexpected type for " + SessionAttribute.SESSION_MAP
              + " : " + sessionList.getClass());
    }
  }

}
