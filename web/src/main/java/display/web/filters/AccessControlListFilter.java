package display.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import display.web.AccessControlList;
import display.web.javabean.CustomerBean;

/**
 * Servlet Filter implementation class AccessControlListFilter
 */
public class AccessControlListFilter {

	public static void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		boolean skipFilter = (boolean) httpRequest.getAttribute("skipFilter");
		
		if (skipFilter) {
			chain.doFilter(request, response);
			return;
		}
		
		CustomerBean customerBean = (CustomerBean) httpRequest.getSession().getAttribute("customerBean");
		AccessControlList acl = AccessControlList.getInstance();
        String url = httpRequest.getRequestURI();

		
		if(! acl.isAllowed(url, customerBean)) {
			httpResponse.sendRedirect("/web");
    		return;
		}
		
		chain.doFilter(httpRequest, httpResponse);
			
		
	}
}
