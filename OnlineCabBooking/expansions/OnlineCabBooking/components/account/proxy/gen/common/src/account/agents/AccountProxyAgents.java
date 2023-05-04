package account.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.usr.AccountAgent;
import net.democritus.usr.AccountAgentIf;
import net.democritus.component.ComponentAgent;
import net.democritus.component.ComponentAgentIf;
import net.democritus.acl.DataAccessAgent;
import net.democritus.acl.DataAccessAgentIf;
import net.democritus.acl.HelpInfoAgent;
import net.democritus.acl.HelpInfoAgentIf;
import net.democritus.usr.menu.MenuAgent;
import net.democritus.usr.menu.MenuAgentIf;
import net.democritus.usr.menu.MenuItemAgent;
import net.democritus.usr.menu.MenuItemAgentIf;
import net.democritus.usr.PortalAgent;
import net.democritus.usr.PortalAgentIf;
import net.democritus.usr.ProfileAgent;
import net.democritus.usr.ProfileAgentIf;
import net.democritus.usr.menu.ScreenAgent;
import net.democritus.usr.menu.ScreenAgentIf;
import net.democritus.usr.menu.ScreenProfileAgent;
import net.democritus.usr.menu.ScreenProfileAgentIf;
import net.democritus.usr.UserAgent;
import net.democritus.usr.UserAgentIf;
import net.democritus.usr.UserGroupAgent;
import net.democritus.usr.UserGroupAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class AccountProxyAgents implements AccountAgents {

  private final Context context;

  // anchor:data-element-agent-variables:start
  private AccountAgentIf accountAgent;
  private ComponentAgentIf componentAgent;
  private DataAccessAgentIf dataAccessAgent;
  private HelpInfoAgentIf helpInfoAgent;
  private MenuAgentIf menuAgent;
  private MenuItemAgentIf menuItemAgent;
  private PortalAgentIf portalAgent;
  private ProfileAgentIf profileAgent;
  private ScreenAgentIf screenAgent;
  private ScreenProfileAgentIf screenProfileAgent;
  private UserAgentIf userAgent;
  private UserGroupAgentIf userGroupAgent;
  // anchor:data-element-agent-variables:end

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  public static AccountAgents getAccountAgents(Context context) {
    return new AccountProxyAgents(context);
  }

  @Deprecated
  public static AccountAgents getAccountAgents(UserContext userContext) {
    return new AccountProxyAgents(userContext);
  }

  public AccountProxyAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public AccountProxyAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public AccountAgentIf getAccountAgent() {
    if (accountAgent == null) {
      accountAgent = AccountAgent.getAccountAgent(context);
    }
    return accountAgent;
  }

  public ComponentAgentIf getComponentAgent() {
    if (componentAgent == null) {
      componentAgent = ComponentAgent.getComponentAgent(context);
    }
    return componentAgent;
  }

  public DataAccessAgentIf getDataAccessAgent() {
    if (dataAccessAgent == null) {
      dataAccessAgent = DataAccessAgent.getDataAccessAgent(context);
    }
    return dataAccessAgent;
  }

  public HelpInfoAgentIf getHelpInfoAgent() {
    if (helpInfoAgent == null) {
      helpInfoAgent = HelpInfoAgent.getHelpInfoAgent(context);
    }
    return helpInfoAgent;
  }

  public MenuAgentIf getMenuAgent() {
    if (menuAgent == null) {
      menuAgent = MenuAgent.getMenuAgent(context);
    }
    return menuAgent;
  }

  public MenuItemAgentIf getMenuItemAgent() {
    if (menuItemAgent == null) {
      menuItemAgent = MenuItemAgent.getMenuItemAgent(context);
    }
    return menuItemAgent;
  }

  public PortalAgentIf getPortalAgent() {
    if (portalAgent == null) {
      portalAgent = PortalAgent.getPortalAgent(context);
    }
    return portalAgent;
  }

  public ProfileAgentIf getProfileAgent() {
    if (profileAgent == null) {
      profileAgent = ProfileAgent.getProfileAgent(context);
    }
    return profileAgent;
  }

  public ScreenAgentIf getScreenAgent() {
    if (screenAgent == null) {
      screenAgent = ScreenAgent.getScreenAgent(context);
    }
    return screenAgent;
  }

  public ScreenProfileAgentIf getScreenProfileAgent() {
    if (screenProfileAgent == null) {
      screenProfileAgent = ScreenProfileAgent.getScreenProfileAgent(context);
    }
    return screenProfileAgent;
  }

  public UserAgentIf getUserAgent() {
    if (userAgent == null) {
      userAgent = UserAgent.getUserAgent(context);
    }
    return userAgent;
  }

  public UserGroupAgentIf getUserGroupAgent() {
    if (userGroupAgent == null) {
      userGroupAgent = UserGroupAgent.getUserGroupAgent(context);
    }
    return userGroupAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
