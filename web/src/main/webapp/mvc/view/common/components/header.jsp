<%@page import="business.utilities.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
    
<header class="header">
	<a href="/web/"><img class="header_logo_image" width="180px" src="${pageContext.request.contextPath}/assets/logo_header.png" alt="Logo de la empresa"/></a>
	<details class="header_user_details">	
		<summary class="header_user_wellcome_summary">¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! <%= Utils.getStringDate(Utils.getCurrentDate()) %></summary>
		<div class="header_user_details_info">
			<a class="modify_user_link" href="/web/mvc/controller/modifyUserController.jsp">Modificar datos de usuario</a>
			<a class="logout_link" href="/web/mvc/controller/logoutController.jsp">Desconectar</a>
		</div>
	</details>
</header>