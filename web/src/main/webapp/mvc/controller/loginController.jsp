<%@page import="business.exceptions.user.WrongCredentialsException"%>
<%@page import="business.exceptions.dao.NotFoundException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="business.dtos.UserDTO,display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="messageBean" scope="session" class="display.web.javabean.MessageBean"></jsp:useBean>

<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";

String emailUser = request.getParameter("email");
String passwordUser = request.getParameter("password");

if (emailUser != null && passwordUser != null) {
	try {
			UserDTO user = Container.getInstance().getUserManager().verifyCredentials(emailUser, passwordUser);
			customerBean.setEmailUser(emailUser);
			customerBean.setPasswordUser(passwordUser);
			customerBean.setRoleUser(user.getRole().name());
			response.sendRedirect(nextPage);
			return;
	} catch (WrongCredentialsException e) {			
		messageBean.setError("El email o la contraseña son incorrectos");
	}
}

%>

<jsp:forward page="../view/common/loginView.jsp"></jsp:forward>