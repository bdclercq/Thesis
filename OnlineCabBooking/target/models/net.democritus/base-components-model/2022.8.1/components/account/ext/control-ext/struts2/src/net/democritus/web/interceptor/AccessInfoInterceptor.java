package net.democritus.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.democritus.web.action.AccessInfoRetrieverAction;
import net.democritus.web.common.SessionKey;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.util.Map;

// @feature:authorization
public class AccessInfoInterceptor extends AbstractInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(AccessInfoInterceptor.class.getCanonicalName());

  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_ACCESSINFO_ACTION = "net.democritus.web.action.AccessInfoRetrieverAction";

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
      String accessActionName = getAccessInfoRetrieverActionImpl(session);
      if (logger.isDebugEnabled()) {
        logger.debug("accessInfoRetrieverActionImpl = " + accessActionName);
      }
      session.put(SessionKey.ACCESS_INFO_KEY.getKey(), accessActionName);

      AccessInfoRetrieverAction accessInfoAction = accessInfoRetrieverAction(accessActionName);
      accessInfoAction.setActionName(invocation.getProxy().getActionName());
      accessInfoAction.setSession(session);
      String result = accessInfoAction.execute();
      // access?
      if (isSuccess(result)) {
        return invocation.invoke();
      }

      return result;
    } catch (Exception e) {
      logger.error("access info interceptor problem", e);
      return Action.LOGIN;
    }
  }

  private AccessInfoRetrieverAction accessInfoRetrieverAction(String className) {
    try {
      Class<?> accessClass = Class.forName(className);
      Object o = accessClass.getConstructor().newInstance();
      if (o instanceof AccessInfoRetrieverAction) {
        return (AccessInfoRetrieverAction) o;
      }
    } catch (Exception e) {
      logger.error("access info retriever action problem", e);
    }
    return new AccessInfoRetrieverAction();
  }

  private String getAccessInfoRetrieverActionImpl(Map<String, Object> session) {
    return new ActionClassFinder(session).getActionClassName(SessionKey.ACCESS_INFO_KEY.getKey(), DEFAULT_ACCESSINFO_ACTION);
  }

  private boolean isLoggedIn(Map<String, Object> session) {
    return session.containsKey("userId");
  }

  private boolean isSuccess(String result) {
    return result.equals(Action.SUCCESS);
  }

}
