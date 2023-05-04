package net.democritus.workflow;

import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.net.InetAddress;

// @feature:multi-node-workflow
public class HostNameRetriever {
  private static final Logger LOGGER = LoggerFactory.getLogger(HostNameRetriever.class);

  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      LOGGER.debug("Could not retrieve hostname", e);
      return "<unknown>";
    }
  }
}
