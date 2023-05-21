package OnlineCabBooking.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

// @anchor:imports:start
// @anchor:imports:end
// anchor:custom-imports:start
// anchor:custom-imports:end

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1
public class HSTSFilter implements Filter {

  // anchor:variables:start
  private static String stsHeader = "max-age=31622400; includeSubDomains";
  // anchor:variables:end
  // @anchor:variables:start
  // @anchor:variables:end
  // anchor:custom-variables:start
  // anchor:custom-variables:end

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // @anchor:init:start
    // @anchor:init:end
    // anchor:custom-init:start
    // anchor:custom-init:end
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    // @anchor:do-filter-before:start
    // @anchor:do-filter-before:end
    // anchor:custom-do-filter-before:start
    // anchor:custom-do-filter-before:end
    httpResponse.setHeader("Strict-Transport-Security", stsHeader);
    chain.doFilter(request, httpResponse);
  }

  @Override
  public void destroy() {
    // @anchor:destroy:start
    // @anchor:destroy:end
    // anchor:custom-destroy:start
    // anchor:custom-destroy:end
  }

  // @anchor:methods:start
  // @anchor:methods:end
  // anchor:custom-methods:start
  // anchor:custom-methods:end

}
