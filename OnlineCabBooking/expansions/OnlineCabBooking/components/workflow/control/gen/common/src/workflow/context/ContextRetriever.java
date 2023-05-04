package workflow.context;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import com.opensymphony.xwork2.ActionContext;
import net.democritus.acl.struts2.UserContextRetriever;
import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

import java.util.Map;

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

public class ContextRetriever {

  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static Context getContext() {
    Map<String, Object> session = ActionContext.getContext().getSession();
    Context context = (Context) session.get("context");
    if (context == null) {
      context = Context.emptyContext();
    }
    if (context.getContext(UserContext.class).isEmpty()) {
      UserContext userContext = UserContextRetriever.getUserContext();
      if (userContext != null) {
        context = context.extend(userContext);
      }
    }
    // @anchor:context:start
    // @anchor:context:end
    // anchor:custom-context:start
    // anchor:custom-context:end
    return context;
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end
}
