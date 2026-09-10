<%@page import="java.util.List"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="display.web.javabean.AssistantInfoBean"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<%
if(customerBean.getRoleUser().equals("ADMIN")) {
%>
<jsp:forward page="./mvc/view/admin/adminHomeView.jsp"></jsp:forward>
<%
} else {
%>
<jsp:forward page="./mvc/view/assistant/assistantHomeView.jsp"></jsp:forward>
<%
}
%>
