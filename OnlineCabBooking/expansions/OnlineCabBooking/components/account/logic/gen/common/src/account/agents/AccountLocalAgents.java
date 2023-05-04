package account.agents;

import net.democritus.sys.Context;
import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.usr.AccountLocalAgent;
import net.democritus.usr.AccountAgentIf;
import net.democritus.component.ComponentLocalAgent;
import net.democritus.component.ComponentAgentIf;
import net.democritus.acl.DataAccessLocalAgent;
import net.democritus.acl.DataAccessAgentIf;
import net.democritus.acl.HelpInfoLocalAgent;
import net.democritus.acl.HelpInfoAgentIf;
import net.democritus.usr.menu.MenuLocalAgent;
import net.democritus.usr.menu.MenuAgentIf;
import net.democritus.usr.menu.MenuItemLocalAgent;
import net.democritus.usr.menu.MenuItemAgentIf;
import net.democritus.usr.PortalLocalAgent;
import net.democritus.usr.PortalAgentIf;
import net.democritus.usr.ProfileLocalAgent;
import net.democritus.usr.ProfileAgentIf;
import net.democritus.usr.menu.ScreenLocalAgent;
import net.democritus.usr.menu.ScreenAgentIf;
import net.democritus.usr.menu.ScreenProfileLocalAgent;
import net.democritus.usr.menu.ScreenProfileAgentIf;
import net.democritus.usr.UserLocalAgent;
import net.democritus.usr.UserAgentIf;
import net.democritus.usr.UserGroupLocalAgent;
import net.democritus.usr.UserGroupAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public class AccountLocalAgents implements AccountAgents {

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
    return new AccountLocalAgents(context);
  }

  @Deprecated
  public static AccountAgents getAccountAgents(UserContext userContext) {
    return new AccountLocalAgents(userContext);
  }

  public AccountLocalAgents(Context context) {
    this.context = context;
  }

  @Deprecated
  public AccountLocalAgents(UserContext userContext) {
    this.context = Context.from(userContext);
  }

  // anchor:data-element-agent-getters:start
  public AccountAgentIf getAccountAgent() {
    if (accountAgent == null) {
      accountAgent = AccountLocalAgent.getAccountAgent(context);
    }
    return accountAgent;
  }

  public ComponentAgentIf getComponentAgent() {
    if (componentAgent == null) {
      componentAgent = ComponentLocalAgent.getComponentAgent(context);
    }
    return componentAgent;
  }

  public DataAccessAgentIf getDataAccessAgent() {
    if (dataAccessAgent == null) {
      dataAccessAgent = DataAccessLocalAgent.getDataAccessAgent(context);
    }
    return dataAccessAgent;
  }

  public HelpInfoAgentIf getHelpInfoAgent() {
    if (helpInfoAgent == null) {
      helpInfoAgent = HelpInfoLocalAgent.getHelpInfoAgent(context);
    }
    return helpInfoAgent;
  }

  public MenuAgentIf getMenuAgent() {
    if (menuAgent == null) {
      menuAgent = MenuLocalAgent.getMenuAgent(context);
    }
    return menuAgent;
  }

  public MenuItemAgentIf getMenuItemAgent() {
    if (menuItemAgent == null) {
      menuItemAgent = MenuItemLocalAgent.getMenuItemAgent(context);
    }
    return menuItemAgent;
  }

  public PortalAgentIf getPortalAgent() {
    if (portalAgent == null) {
      portalAgent = PortalLocalAgent.getPortalAgent(context);
    }
    return portalAgent;
  }

  public ProfileAgentIf getProfileAgent() {
    if (profileAgent == null) {
      profileAgent = ProfileLocalAgent.getProfileAgent(context);
    }
    return profileAgent;
  }

  public ScreenAgentIf getScreenAgent() {
    if (screenAgent == null) {
      screenAgent = ScreenLocalAgent.getScreenAgent(context);
    }
    return screenAgent;
  }

  public ScreenProfileAgentIf getScreenProfileAgent() {
    if (screenProfileAgent == null) {
      screenProfileAgent = ScreenProfileLocalAgent.getScreenProfileAgent(context);
    }
    return screenProfileAgent;
  }

  public UserAgentIf getUserAgent() {
    if (userAgent == null) {
      userAgent = UserLocalAgent.getUserAgent(context);
    }
    return userAgent;
  }

  public UserGroupAgentIf getUserGroupAgent() {
    if (userGroupAgent == null) {
      userGroupAgent = UserGroupLocalAgent.getUserGroupAgent(context);
    }
    return userGroupAgent;
  }
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
