package net.democritus.web.action;

import com.opensymphony.xwork2.Action;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

// Use SessionTerminatorAction instead
@Deprecated
public class EnvRetrieverAction {

  private static final Logger logger = LoggerFactory.getLogger(EnvRetrieverAction.class);

  @Deprecated
  public String execute() throws Exception {
    return Action.SUCCESS;
  }

}
