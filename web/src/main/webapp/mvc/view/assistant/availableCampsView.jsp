<%@page import="business.utilities.Utils"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="listOfCampsBean" scope="session" class="display.web.javabean.ListOfCampsBean"></jsp:useBean>

<%
List<CampDTO> camps = Container.getInstance().getInscriptionManager().avaliableCamps(Utils.getCurrentDate());
listOfCampsBean.setCamps(camps);
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Campamentos disponibles</title>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
		<script defer src="${pageContext.request.contextPath}/scripts/filterAvailableCamps.js"></script>
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		
		<div class="content_container">
			<div class="navbar_container">
				<jsp:include page="./navbarAssistant.jsp">
					<jsp:param name="selected" value="availableCamps"/>
				</jsp:include>

				<form class="filter_form">
					<label for="since">Desde:</label>
					<input class="form_input" type="date" name="since" id="since" required>
					<br>
					
					<label for="until">Hasta:</label>
					<input class="form_input" type="date" name="until" id="until" required>
					<br>
					
					<label for="educative_level">Nivel Educativo:</label>
					<select class="form_input" name="educative_level" id="educative_level">
					  <option value="">Sin filtrar</option>
					  <option value="Infantil">Infantil</option>
					  <option value="Juvenil">Juvenil</option>
					  <option value="Adolescente">Adolescente</option>
					</select>
					<br>
					
					<label for="capacity">Capacidad:</label>
					<input class="form_input" type="number" name="capacity" id="capacity" min="0" max="999" required>
					<br>
				</form>
			</div>
			<main>
				
				<jsp:include page="../common/components/message.jsp"></jsp:include>
				
				<form action="/web/enroll" method="post">
					<label for="type">Tipo de inscripción:</label>
					<select class="form_input" name="type" id="type">
					  <option value="COMPLETE" selected>Completa</option>
					  <option value="PARTIAL">Parcial</option>
					</select>
					<br>
					<input class="submit_btn" type="submit" value="Inscribirse"><br>
					<jsp:include page="../common/components/listOfAvailableCamps.jsp"></jsp:include>
				</form>
			</main>
		</div>
	</body>
</html>