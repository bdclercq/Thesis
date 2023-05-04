// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:base-components:2022.8.1
package net.democritus.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.democritus.sys.Context;
import net.democritus.web.common.SessionKey;
import org.apache.struts2.ServletActionContext;

import java.util.Map;

// @anchor:imports:start
// @anchor:imports:end

public class LogoutAction extends ActionSupport {

  private String destinationUrl;

  @Override
  public String execute() throws Exception {
    Map<String, Object> session = ActionContext.getContext().getSession();
    Context context = (Context) session.get(SessionKey.CONTEXT.getKey());

    // @anchor:execute:start
    // @anchor:execute:end

    return Action.SUCCESS;
  }

  public String getDestinationUrl() {
    return destinationUrl;
  }

  // @anchor:methods:start
  // @anchor:methods:end

}
