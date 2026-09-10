<%@page import="business.dtos.ActivityDTO"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.MonitorDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="activityFormBean" scope="session" class="display.web.javabean.ActivityFormBean"></jsp:useBean>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Crear nueva actividad</title>
	<script src="${pageContext.request.contextPath}/scripts/createActivityValidations.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<form action="/web/createActivity" method="post">
				<p>Selecciona <%= activityFormBean.getNeededMonitors() %> monitores:</p>
				<fieldset>
					<legend>Monitores</legend>			
					<select name="selected_monitors" id="monitors_select" multiple>
					<%
						List<MonitorDTO> monitorDTOs = Container.getInstance().getCampsManager().listOfMonitors();
						for (MonitorDTO monitor: monitorDTOs){
							int monitorId = monitor.getId();
						%>
							<option value="<%= monitorId %>">
								<%= monitorId %>, 
								<%= monitor.getFirstName() %> <%= monitor.getLastName() %>
								<%= monitor.isSpecialEducator() ? ", Educador especial" : "" %>
							</option>
						<%
						}
					%>
					</select>
		
				</fieldset>
				<input type="submit" value="Enviar" onclick="validateThereAreEnoughtSelectedMonitors('${activityFormBean.getNeededMonitors()}', event)">
			</form>
		</main>
	</div>
</body>
</html>