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

import display.web.javabean.CustomerBean;


public class LoginFilter {
	public static void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpRequest.setAttribute("skipFilter", false);
		
        CustomerBean customerBean = (CustomerBean) httpRequest.getSession().getAttribute("customerBean");

        String url = httpRequest.getRequestURI();

        boolean isUserLogged = !(customerBean == null || customerBean.getEmailUser().equals(""));
        boolean isGoingToRegisterOrLogin = (url.endsWith("/loginController.jsp") || url.endsWith("/registerController.jsp") || url.contains("assets"));
        boolean isGoingToLogout = url.endsWith("/logoutController.jsp");
        
        if (isUserLogged) {        	
        	if (isGoingToRegisterOrLogin) {
        		httpResponse.sendRedirect("/web");
        		return;
        	}
        	
        	if (isGoingToLogout) {
        		httpRequest.setAttribute("skipFilter", true);
        		AssistantInfoFilter.doFilter(request, response, chain);
        		return;
        	}
        } else  {        	
        	if (isGoingToRegisterOrLogin) {
        		httpRequest.setAttribute("skipFilter", true);
        		AssistantInfoFilter.doFilter(request, response, chain);
        		return;
        	} else {        		
        		httpResponse.sendRedirect("/web/mvc/controller/loginController.jsp");
        		return;
        	}
        }
        
		AssistantInfoFilter.doFilter(request, response, chain);
    }
}
