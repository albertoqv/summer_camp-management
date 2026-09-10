<%@page import="business.dtos.ActivityDTO"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.MonitorDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="campFormBean" scope="session" class="display.web.javabean.CampFormBean"></jsp:useBean>
   
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
			
				<p>Seleccione al monitor principal para el campamento:</p>
				<fieldset>
					<legend>Monitores de la actividad seleccionada</legend>			
					<%
						List<MonitorDTO> monitorDTOs = Container.getInstance().getCampsManager().getMonitorsOfAnActivity(new ActivityDTO(campFormBean.getActivityName(), null, null, 0, 0));
						for (MonitorDTO monitor: monitorDTOs){
							int monitorId = monitor.getId();
						%>
							<input type="radio" id="<%= monitorId %>" name="monitor_id" value="<%= monitorId %>" required>
							<label for="<%= monitorId %>"><%= monitorId %>, <%= monitor.getFirstName() %> <%= monitor.getLastName() %></label><br>
						<%
						}
					%>
		
				</fieldset>
				<input type="submit" value="Enviar">
			</form>
		</main>
	</div>
</body>
</html>