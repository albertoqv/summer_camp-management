<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="business.enums.UserRole"%>
<%@page import="business.dtos.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="display.web.Container"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="assistantBean" scope="session" class="display.web.javabean.AssistantInfoBean"></jsp:useBean>
 
 
<%

String passwordUser = request.getParameter("password");
String roleUser = request.getParameter("role");

if(customerBean.getRoleUser().equals("ASSISTANT")){
	AssistantDTO assistant = Container.getInstance().getUserManager().getAssistantInfo(customerBean.getEmailUser());
	assistantBean.setDni(assistant.getId());
	assistantBean.setFirstName(assistant.getFirstName());
	assistantBean.setLastName(assistant.getLastName());
	assistantBean.setBirthDate(Utils.getStringDate(assistant.getBirthDate()));
	assistantBean.setRequireSpecialAttention(assistant.isRequireSpecialAttention());
}

if(passwordUser != null && roleUser != null) {
	UserDTO user = new UserDTO(customerBean.getEmailUser(), passwordUser, UserRole.valueOf(roleUser));
	Container.getInstance().getUserManager().updateUser(user);
	
	if(customerBean.getRoleUser().equals("ASSISTANT")){
		String assistantFirstName = request.getParameter("firstName");
		String assistantLastName = request.getParameter("lastName");
		String requireSpecialAttentionString = request.getParameter("requireSpecialAttention");
		
		if(assistantFirstName != null && assistantLastName != null && requireSpecialAttentionString != null) {
			AssistantDTO assistantModified = new AssistantDTO(
					assistantBean.getDni(), 
					assistantFirstName,
					assistantLastName,
					Utils.parseDate(assistantBean.getBirthDate()),
					requireSpecialAttentionString.equals("true") ? true : false);
			Container.getInstance().getAssistantsManager().updateAssistant(assistantModified);
			assistantBean.setFirstName(assistantFirstName);
			assistantBean.setLastName(assistantLastName);
		}
	}
}

%> 
    
<jsp:forward page="../view/common/modifyUserView.jsp">
	<jsp:param value="" name="message"/>
</jsp:forward>