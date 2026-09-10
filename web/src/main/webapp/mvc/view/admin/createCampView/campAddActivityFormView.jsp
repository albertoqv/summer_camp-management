<%@page import="business.enums.EducativeLevel"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.ActivityDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear nuevo campamento</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<form action="/web/createCamp" method="post">
				<p>Seleccione la primera actividad del campamento:</p>
				<fieldset>
					<legend>Actividades</legend>			
					<%
						List<ActivityDTO> activityDTOs = Container.getInstance().getCampsManager().listOfActivities();
						for (ActivityDTO activity: activityDTOs){
							String activityName = activity.getActivityName();
							String educativeLevel = activity.getEducativeLevel() == EducativeLevel.ELEMENTARY 
									? "Infantil"
									: activity.getEducativeLevel() == EducativeLevel.PRESCHOOL
										? "Juvenil"
										: "Adolescente";
						%>
							<input type="radio" id="<%= activityName %>" name="activity_name" value="<%= activityName %>" required>
							<label for="<%= activityName %>"><%= activityName %>, Nivel educativo: <%= educativeLevel %></label><br>
						<%
						}
					%>
		
				</fieldset>
				<input type="submit" value="Siguiente">
			</form>
		</main>
	</div>
</body>
</html>