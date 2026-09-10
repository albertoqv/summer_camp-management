<%@page import="business.dtos.InscriptionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="display.web.javabean.AssistantInfoBean"%>
<%@page import="business.enums.UserRole"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="listOfInscriptions" scope="session" class="display.web.javabean.ListOfInscriptionsBean"></jsp:useBean>


<%
AssistantDTO assistant = Container.getInstance().getUserManager().getAssistantInfo(customerBean.getEmailUser());

List<InscriptionDTO> inscriptions = Container.getInstance().getInscriptionManager().getInscriptionsOfAnAssistant(assistant);

listOfInscriptions.setInscriptions(inscriptions);
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Summer Camp</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		<div class="content_container">
			<jsp:include page="./navbarAssistant.jsp">
				<jsp:param name="selected" value="inscriptions"/>
			</jsp:include>
			<main>
					<jsp:include page="../common/components/listOfInscriptions.jsp"></jsp:include>
			</main>
		</div>
	</body>
</html>