<%@page import="display.web.javabean.MessageType"%>
<%@page import="business.utilities.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="messageBean" scope="session" class="display.web.javabean.MessageBean"></jsp:useBean>
   
<%
if(!messageBean.getMessage().equals("")){
	if(messageBean.getType() == MessageType.ERROR){
		%>
		<p style="color: red;"><%= messageBean.getMessage() %></p>
		<%
	}else if(messageBean.getType() == MessageType.SUCCESS) {
		%>
		<p style="color: green;"><%= messageBean.getMessage() %></p>
		<%		
	} else if (messageBean.getType() == MessageType.INFO) {
		%>
		<p style="color: blue;"><%= messageBean.getMessage() %></p>
		<%
	}
}
%>