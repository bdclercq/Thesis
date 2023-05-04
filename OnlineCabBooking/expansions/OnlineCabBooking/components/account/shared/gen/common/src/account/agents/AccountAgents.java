package account.agents;

import net.democritus.sys.UserContext;

// anchor:data-element-agents-import:start
import net.democritus.usr.AccountAgentIf;
import net.democritus.component.ComponentAgentIf;
import net.democritus.acl.DataAccessAgentIf;
import net.democritus.acl.HelpInfoAgentIf;
import net.democritus.usr.menu.MenuAgentIf;
import net.democritus.usr.menu.MenuItemAgentIf;
import net.democritus.usr.PortalAgentIf;
import net.democritus.usr.ProfileAgentIf;
import net.democritus.usr.menu.ScreenAgentIf;
import net.democritus.usr.menu.ScreenProfileAgentIf;
import net.democritus.usr.UserAgentIf;
import net.democritus.usr.UserGroupAgentIf;
// anchor:data-element-agents-import:end

// anchor:custom-imports:start
// anchor:custom-imports:end

public interface AccountAgents {

  // anchor:custom-variables:start
  // anchor:custom-variables:end

  // anchor:data-element-agent-getters:start
  AccountAgentIf getAccountAgent();
  ComponentAgentIf getComponentAgent();
  DataAccessAgentIf getDataAccessAgent();
  HelpInfoAgentIf getHelpInfoAgent();
  MenuAgentIf getMenuAgent();
  MenuItemAgentIf getMenuItemAgent();
  PortalAgentIf getPortalAgent();
  ProfileAgentIf getProfileAgent();
  ScreenAgentIf getScreenAgent();
  ScreenProfileAgentIf getScreenProfileAgent();
  UserAgentIf getUserAgent();
  UserGroupAgentIf getUserGroupAgent();
  // anchor:data-element-agent-getters:end

  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
