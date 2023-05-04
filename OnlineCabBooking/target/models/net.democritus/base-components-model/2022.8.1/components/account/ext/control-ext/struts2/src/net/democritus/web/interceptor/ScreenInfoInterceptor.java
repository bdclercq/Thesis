package net.democritus.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.democritus.web.action.ScreenInfoRetrieverAction;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.util.Map;

// @feature:struts-navigation
public class ScreenInfoInterceptor extends AbstractInterceptor {

  private static final String SCREEN_INFO_KEY = "ScreenInfoRetrieverActionImpl";
  private static final Logger logger = LoggerFactory.getLogger(ScreenInfoInterceptor.class.getCanonicalName());

  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_SCREENINFO_ACTION = "net.democritus.web.action.ScreenInfoRetrieverAction";

  /**
   * Override to handle interception.
   * <p>
   * Specified by: intercept in interface {@link Interceptor} Returns: the
   *
   * @param invocation the action invocation.
   * @return code, either returned from ActionInvocation.invoke(), or from the interceptor itself.
   */
  @Override
  public String intercept(ActionInvocation invocation) {
    ActionContext context = invocation.getInvocationContext();
    Map<String, Object> session = context.getSession();

    if (!isLoggedIn(session)) {
      return Action.LOGIN;
    }

    try {
      String screenActionName = getScreenInfoRetrieverActionImpl(session);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "screenInfoRetrieverActionImpl = " + screenActionName
        );
      }

      session.put(SCREEN_INFO_KEY, screenActionName);

      ScreenInfoRetrieverAction screenInfoAction = screenInfoRetrieverAction(screenActionName);
      screenInfoAction.setSession(session);
      screenInfoAction.setActionName(invocation.getProxy().getActionName());
      String result = screenInfoAction.execute();
      if (isSuccess(result)) {
        return invocation.invoke();
      }

      return result;
    } catch (Exception e) {
      logger.error("screen info interceptor problem", e);
      return Action.LOGIN;
    }
  }

  private ScreenInfoRetrieverAction screenInfoRetrieverAction(String className) {
    try {
      Class<?> accessClass = Class.forName(className);
      Object o = accessClass.getConstructor().newInstance();
      if (o instanceof ScreenInfoRetrieverAction) {
        return (ScreenInfoRetrieverAction) o;
      }
    } catch (Exception e) {
      logger.error("screen info retriever action problem", e);
    }
    return new ScreenInfoRetrieverAction();
  }

  private String getScreenInfoRetrieverActionImpl(Map<String, Object> session) {
    return new ActionClassFinder(session).getActionClassName(SCREEN_INFO_KEY, DEFAULT_SCREENINFO_ACTION);
  }

  private boolean isLoggedIn(Map<String, Object> session) {
    return session.containsKey("userId");
  }

  private boolean isSuccess(String result) {
    return result.equals(Action.SUCCESS);
  }

}
