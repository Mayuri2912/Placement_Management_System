package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * BUG FIX: "Browser Back button reopens dashboard after logout".
 *
 * Root cause: every protected servlet/JSP already checks the session
 * correctly (session.getAttribute("admin")/"student") and redirects to the
 * login page when it is missing - that part was already working. The actual
 * bug is that NOTHING in the application ever sent Cache-Control headers, so
 * a browser's own back/forward cache (bfcache) could repaint a previously
 * rendered protected page from its local cache WITHOUT ever sending a new
 * request to the server. In that situation no server-side code runs at all,
 * so the (correct) session check never gets a chance to fire.
 *
 * This filter is purely additive - a brand new class in a brand new package.
 * It does not modify, replace, or remove any existing servlet or JSP; those
 * all keep their own session checks exactly as before. This filter simply
 * guarantees every response tells the browser not to cache it, so the Back
 * button always triggers a real request that the existing session checks
 * can correctly intercept.
 */
@WebFilter("/*")
public class NoCacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // no initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // no cleanup needed
    }
}
