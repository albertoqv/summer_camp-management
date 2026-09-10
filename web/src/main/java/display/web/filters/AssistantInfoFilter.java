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

import business.managers.UsersManager;
import display.web.Container;
import display.web.javabean.CustomerBean;

/**
 * Servlet Filter implementation class AssistantInfoFilter
 */
public class AssistantInfoFilter {
	
	public static void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		CustomerBean customerBean = (CustomerBean) httpRequest.getSession().getAttribute("customerBean");
		UsersManager usersManager = Container.getInstance().getUserManager();
		String url = httpRequest.getRequestURI();
		String registerAssistantUrl = "/web/mvc/controller/registerAssistantController.jsp";

		boolean skipFilter = (boolean) httpRequest.getAttribute("skipFilter");
		boolean isGoingToRegisterAssistantInfo = url.equals(registerAssistantUrl);
		
		if(skipFilter) {
    		AccessControlListFilter.doFilter(request, response, chain);
			return;
		}

		boolean isAssistant = customerBean.getRoleUser().equals("ASSISTANT");
		boolean userHasAssistantInfo = usersManager.hasAssistantInfo(customerBean.getEmailUser());
		
        if (!isAssistant) {
    		AccessControlListFilter.doFilter(request, response, chain);
    		return;
        }
        
        if (isGoingToRegisterAssistantInfo && !userHasAssistantInfo) {
        	httpRequest.setAttribute("skipFilter", true);
    		AccessControlListFilter.doFilter(request, response, chain);
        	return;
        }

        if (!isGoingToRegisterAssistantInfo && !userHasAssistantInfo) {
        	httpResponse.sendRedirect(registerAssistantUrl);
            return;
        }
        
        if (isGoingToRegisterAssistantInfo && userHasAssistantInfo) {
        	httpResponse.sendRedirect("/web");
            return;
        }
        
        if(!isGoingToRegisterAssistantInfo && userHasAssistantInfo) {
    		AccessControlListFilter.doFilter(request, response, chain);
			return;
        }
	}

}
