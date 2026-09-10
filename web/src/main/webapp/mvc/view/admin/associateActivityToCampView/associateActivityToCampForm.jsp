<%@page import="business.dtos.CampDTO"%>
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
<title>Asociar actividad a campamento</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<form action="/web/associateActivityToCamp" method="post">
				<p>Seleccione la actividad y el campamento:</p>
				<label for="camp_id">Campamento:</label>
				<select name="camp_id" id="camp_id">
						
					<%
						List<CampDTO> campDTOs = Container.getInstance().getCampsManager().listOfCamps();
						for (CampDTO camp: campDTOs){
							String educativeLevel = camp.getEducativeLevel() == EducativeLevel.ELEMENTARY 
									? "Infantil"
									: camp.getEducativeLevel() == EducativeLevel.PRESCHOOL
										? "Juvenil"
										: "Adolescente";
						%>
						    <option value="<%= camp.getCampID() %>">#<%= camp.getCampID() %>, Nivel educativo: <%= educativeLevel %></option>
						<%
						}
					%>
				</select>
				<br>
				
				<label for="activity_name">Actividad:</label>
				<select name="activity_name" id="activity_name">
						
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
						    <option value="<%= activityName %>"><%= activityName %>, Nivel educativo: <%= educativeLevel %></option>
						<%
						}
					%>
				</select>
				<br>
				<input type="submit" value="Siguiente">
			</form>
		</main>
	</div>
</body>
</html>