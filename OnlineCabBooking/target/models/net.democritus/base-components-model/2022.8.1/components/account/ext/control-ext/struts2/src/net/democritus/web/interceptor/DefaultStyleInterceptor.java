package net.democritus.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.democritus.sys.Context;
import net.democritus.sys.ParamTargetValueAgent;
import net.democritus.sys.UserContext;
import net.democritus.web.common.SessionKey;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * This interceptor puts a default "style" in the session when nobody is logged in (or perhaps as a
 * backstop if the user has no style configured?). The style must be known in time to help determine
 * a fragment to be included in the result jsp.
 */
// @feature:struts-styling
public class DefaultStyleInterceptor extends AbstractInterceptor {

  private static final String DEFAULT_STYLE = "nsxbootstrap";
  private static final String DEFAULT_STYLE_KEY = "defaultStyle";

  private static final Logger logger = LoggerFactory.getLogger(DefaultStyleInterceptor.class);

  /**
   * Override to handle interception.
   * <p>
   * Specified by: intercept in interface {@link Interceptor} Returns: the
   *
   * @param invocation the action invocation.
   * @return code, either returned from ActionInvocation.invoke(), or from the interceptor itself.
   * @throws Exception any system-level error, as defined in Action.execute().
   */
  @Override
  public String intercept(ActionInvocation invocation) throws Exception {

    final HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
    final HttpSession httpSession = httpServletRequest.getSession();

    if (httpSession.getAttribute("style") == null) {
      UserContext userContext = getUserContext(invocation.getInvocationContext().getSession());
      httpSession.setAttribute("style", getDefaultStyle(userContext));
    }

    return invocation.invoke();
  }

  private String getDefaultStyle(UserContext userContext) {
    try {
      ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(Context.from(userContext));
      final String value = paramTargetValueAgent.getParamTargetValue(DEFAULT_STYLE_KEY, "default");
      if (value != null && !value.equals("")) {
        return value;
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(
            "getDefaultStyle problem", e
        );
      }
    }
    return DEFAULT_STYLE;
  }

  private UserContext getUserContext(Map<String, Object> session) {
    UserContext userContext = (UserContext) session.get(SessionKey.USERCONTEXT.getKey());
    return userContext != null ? userContext : UserContext.NO_USER_CONTEXT;
  }

}
