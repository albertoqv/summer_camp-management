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
	</head>
	<body>
		<jsp:include page="../../common/components/header.jsp"></jsp:include>
		<main>
			<aside>
				<ul>
					<li><a href="/web/inscriptions">Inscripciones realizadas</a></li>
					<li><a href="/web/availableCamps">Campamentos disponibles</a></li>
				</ul>
			</aside>
			<form action="/web/enroll" method="post">

				<p>
					Vas a realizar una inscripción del tipo tardía, esto implica que no se podrá cancelar.
				</p>
				
				<input type="checkbox" id="wasConfirmed" name="wasConfirmed" value="true" />
    			<label for="wasConfirmed">Acepto las condiciones de la inscripción tardía</label>
				
				<input name="alreadyAdvise" type="hidden" value="true" />
				
				<input type="submit" value="Enviar"><br>
			</form>
		</main>
	</body>
</html>