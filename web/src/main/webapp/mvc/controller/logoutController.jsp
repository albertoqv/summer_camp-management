<%@page import="business.exceptions.dao.NotFoundException"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="assistantBean" scope="session" class="display.web.javabean.AssistantInfoBean"></jsp:useBean>

<%
String nextPage = "../../index.jsp";
String mensajeNextPage = "";
if (customerBean != null) {
	customerBean.setEmailUser("");
}

if (assistantBean != null) {
	assistantBean.setDni(-1);
}

response.sendRedirect(nextPage);
%>