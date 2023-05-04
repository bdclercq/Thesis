package net.democritus.workflow;

import net.democritus.sys.Context;
import net.democritus.sys.ParamTargetValueAgent;
import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

// @feature:multi-node-workflow
@SuppressWarnings("java:S2119")
public class EngineNodeConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(EngineNodeConfig.class);

  private static final long DEFAULT_STARTER_INTERVAL = 2 * 60 * 1000L;
  private static final long DEFAULT_CHECK_INTERVAL = 5 * 60 * 1000L;

  private static final long DEFAULT_CHECK_OFFSET = new Random().nextInt(3000);
  private static final long DEFAULT_STARTER_OFFSET = DEFAULT_CHECK_OFFSET + 500 + new Random().nextInt(2000);

  private static final long DEFAULT_TIMEOUT = 10 * 60 * 1000L;
  private static final long DEFAULT_CLEANUP = 24 * 3600 * 1000L;

  private static final String ENGINE_NODE_NAME;

  static {
    String engineNodeName;
    try {
      engineNodeName = ManagementFactory.getRuntimeMXBean().getName();
    } catch (Exception e) {
      LOGGER.error("Failed to define Engine Node Name, picking random name", e);
      engineNodeName = UUID.randomUUID().toString();
    }
    ENGINE_NODE_NAME = engineNodeName;
  }

  private EngineNodeConfig() {}

  /**
   * Returns the interval at which the {@link net.democritus.wfe.EngineStarterBean} checks for engineServices to start up.
   * @see <a href="https://foundation.stars-end.net/development/base-components/workflow/engine-starter.html">Starting Engines</a>
   *
   * @return EngineStarterBean timer interval
   */
  public static long getEngineStarterInterval() {
    Long interval = getParamTargetValue("workflow.startEngines.interval");
    return interval == null ? DEFAULT_STARTER_INTERVAL : interval;
  }

  /**
   * Returns the interval at which the {@link net.democritus.wfe.EngineHealthBean} checks for recoverable instances.
   * @see <a href="https://foundation.stars-end.net/development/base-components/workflow/interrupt-recovery.html#solution-for-claimable-workflows-check-engine-health-task">Check Engine Health task</a>
   *
   * @return EngineHealthBean timer interval
   */
  public static long getHealthCheckInterval() {
    Long interval = getParamTargetValue("workflow.checkHealth.interval");
    return interval == null ? DEFAULT_CHECK_INTERVAL : interval;
  }

  /**
   * Returns the cutoff time used to find unresponsive Engine nodes
   * @see <a href="https://foundation.stars-end.net/development/base-components/workflow/unresponsive-node-detection.html">Detection of unresponsive Engine Nodes</a>
   *
   * @return cutoff time for unresponsive nodes
   */
  static Date getExpirationCutOffTime() {
    Long timeoutValue = getParamTargetValue("workflow.checkHealth.timeout");
    long timeout = timeoutValue == null ? DEFAULT_TIMEOUT : timeoutValue;
    long now = new Date().getTime();
    return new Date(now - timeout);
  }

  /**
   * Returns the cutoff time used to clean up unresponsive nodes
   *
   * @see <a href="https://foundation.stars-end.net/development/base-components/workflow/unresponsive-node-detection.html">Detection of unresponsive Engine Nodes</a>
   *
   * @return cutoff time for cleaning unresponsive nodes
   */
  static Date getCleanupCutOffTime() {
    Long timeoutValue = getParamTargetValue("workflow.checkHealth.cleanup");
    long timeout = timeoutValue == null ? DEFAULT_CLEANUP : timeoutValue;
    long now = new Date().getTime();
    return new Date(now - timeout);
  }

  /**
   * @return Time before {@link net.democritus.wfe.EngineStarterBean} starts its timer
   */
  public static long getEngineStarterOffset() {
    return DEFAULT_STARTER_OFFSET;
  }

  /**
   * @return Time before {@link net.democritus.wfe.EngineHealthBean} starts its timer
   */
  public static long getHealthCheckOffset() {
    return DEFAULT_CHECK_OFFSET;
  }

  private static Long getParamTargetValue(String key) {
    ParamTargetValueAgent paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(Context.emptyContext());
    String interval = paramTargetValueAgent.getParamTargetValue(key, "default");
    if (!interval.isEmpty()) {
      try {
        return Long.parseLong(interval);
      } catch (NumberFormatException e) {
        LOGGER.warn("Could not parse value for '" + key + "'", e);
      }
    }
    return null;
  }

  public static String getEngineNodeName() {
    return ENGINE_NODE_NAME;
  }
}
