// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
package account.settings;

import net.palver.logging.LoggerFactory;
import net.palver.logging.Logger;
import net.palver.util.Options;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

// @feature:component-configuration
public class AccountApplicationSettings {

  private static final Logger logger = LoggerFactory.getLogger(AccountApplicationSettings.class);

  private static final Properties properties = new Properties();

  static {
    try {
      final Enumeration<URL> resources = AccountApplicationSettings.class.getClassLoader()
          .getResources("account.ns.properties");

      while (resources.hasMoreElements()) {
        final URL resourceUrl = resources.nextElement();
        if (resourceUrl != null) {
          try {
            final InputStream inputStream = resourceUrl.openStream();
            if (inputStream != null) {
              properties.load(inputStream);
            }
          } catch (IOException e) {
            if (logger.isDebugEnabled()) {
              logger.debug(
                  "Failed to open stream for 'account.ns.properties' file in classPath"
              );
            }
          }
        }
      }
      if (logger.isDebugEnabled()) {
        logger.debug(
            "'account.ns.properties' loaded"
        );
      }
    } catch (IOException e) {
      if (logger.isInfoEnabled()) {
        logger.info(
            "No properties found for component 'account'", e
        );
      }
    }
  }

  public static Options.Option<String> getProperty(String name) {
    final String property = properties.getProperty(name);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "account property key='" + name + "' value='" + property + "'"
      );
    }
    return Options.notNull(property);
  }

}
