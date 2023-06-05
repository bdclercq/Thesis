package net.democritus.io;

import net.democritus.sys.ParamTargetValueAgent;

import net.democritus.sys.UserContext;
import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;

/**
 * Singleton, representing the agent in the webtier to the business logic of the file reporting in
 * the web tier.
 */
//@feature:csv-reporters
public class ReporterAgent {

  private Logger logger = LoggerFactory.getLogger("net.democritus.io.ReporterAgent");

  private static ReporterAgent reporterAgent = null;
  private String reportDirectory = "";
  private ParamTargetValueAgent paramTargetValueAgent = null;
  private String defaultReportDirectory = "file:/home/jonas/reports/";

  private ReporterAgent() {
    paramTargetValueAgent = ParamTargetValueAgent.getParamTargetValueAgent(UserContext.NO_USER_CONTEXT);
    reportDirectory = paramTargetValueAgent.getParamTargetValue("reportDirectory", "default");
    if (reportDirectory.equals("")) {
      reportDirectory = defaultReportDirectory;
    }
  }

  public static ReporterAgent getReporterAgent() {
    if (reporterAgent == null) {
      reporterAgent = new ReporterAgent();
    }
    return reporterAgent;
  }

  public String getReportPathname(String fileName) {
    String pathName = reportDirectory + fileName + ".csv";
    if (logger.isInfoEnabled()) {
        logger.info(
            "Reporting to " + pathName
        );
    }
    return pathName;
  }

}
