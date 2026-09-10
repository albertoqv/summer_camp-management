<%@page import="business.dtos.MonitorDTO"%>
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
<title>Asociar monitor especial a un campamento</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<form action="/web/associateSpecialMonitorToCamp" method="post">
				<p>Seleccione el monitor y el campamento:</p>
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
				
				<label for="monitor_id">Monitor:</label>
				<select name="monitor_id" id="monitor_id">
						
					<%
						List<MonitorDTO> monitorDTOs = Container.getInstance().getCampsManager().listOfMonitors();
						for (MonitorDTO monitor: monitorDTOs){
						%>
						    <option value="<%= monitor.getId() %>"><%= monitor.getId() %>, <%= monitor.getFirstName() %> <%= monitor.getLastName() %><%= monitor.isSpecialEducator() ? ", Educador especial" : "" %></option>
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