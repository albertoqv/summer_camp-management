<%@page import="java.util.Date"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="display.web.Container"%>
<%@page import="business.exceptions.dao.NotFoundException"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="assistantBean" scope="session" class="display.web.javabean.AssistantInfoBean"></jsp:useBean>

<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";

String assistantDni = request.getParameter("dni");
String assistantFirstName = request.getParameter("firstName");
String assistantLastName = request.getParameter("lastName");
String assistantBirthDate = request.getParameter("birthDate");
String requireSpecialAttentionString = request.getParameter("requireSpecialAttention");

if (Container.getInstance().getUserManager().hasAssistantInfo(customerBean.getEmailUser())) {
	response.sendRedirect("../../index.jsp");
	return;
}

if (assistantDni != null && assistantFirstName != null && assistantLastName != null && assistantBirthDate != null) {
	boolean requireSpecialAttention = requireSpecialAttentionString != null &&  requireSpecialAttentionString.equals("on") ? true : false;
	
	if(!(assistantDni.length() == 9)) {
		mensajeNextPage = "DNI invalido";
	} else {		
		int dni = Integer.valueOf(assistantDni.substring(0, 8));
		Date date = Utils.parseDateBrowserFormat(assistantBirthDate);
		AssistantDTO assistantDTO = new AssistantDTO(dni, assistantFirstName, assistantLastName, date, requireSpecialAttention);
		
		Container.getInstance().getAssistantsManager().registerAssistant(assistantDTO);
		Container.getInstance().getUserManager().registerAssitantInfo(customerBean.getEmailUser(), dni);
		
		assistantBean.setDni(dni);
		assistantBean.setFirstName(assistantFirstName);
		assistantBean.setLastName(assistantLastName);
		assistantBean.setBirthDate(Utils.getStringDate(date));
		assistantBean.setRequireSpecialAttention(requireSpecialAttention);
	}
}

%>

<jsp:forward page="../view/assistant/registerAssistantView.jsp">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>