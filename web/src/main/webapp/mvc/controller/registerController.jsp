<%@page import="business.exceptions.user.InvalidRoleException"%>
<%@page import="business.exceptions.user.RegisterException"%>
<%@page import="business.exceptions.user.UserAlreadyExistsException"%>
<%@page import="business.enums.UserRole"%>
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
String roleString = request.getParameter("role");

if (emailUser != null && passwordUser != null && roleString != null) {
	try {
		Container.getInstance().getUserManager().registerUser(emailUser, passwordUser, roleString);
		customerBean.setEmailUser(emailUser);
		customerBean.setPasswordUser(passwordUser);
		customerBean.setRoleUser(roleString);
		response.sendRedirect(nextPage);	
		return;
	}catch (InvalidRoleException e) {
		messageBean.setError("El rol proporcionado es invalido.");
	}catch (UserAlreadyExistsException e) {
		messageBean.setError("Ya existe un usuario con ese email.");
	}catch (RegisterException e) {
		messageBean.setError("Error al registrar");
	}
}

%>

<jsp:forward page="../view/common/registerView.jsp">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>