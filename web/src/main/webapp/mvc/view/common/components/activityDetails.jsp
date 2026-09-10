<%@page import="business.dtos.MonitorDTO"%>
<%@page import="java.util.List"%>
<%@page import="display.web.Container"%>
<%@page import="business.enums.TimeSlot"%>
<%@page import="business.enums.EducativeLevel"%>
<%@page import="business.dtos.ActivityDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="activityBean" scope="session" class="display.web.javabean.ActivityBean"></jsp:useBean>

<%
ActivityDTO activity = activityBean != null ? activityBean.getActivity() : null;

String educativeLevel = activity.getEducativeLevel() == EducativeLevel.ELEMENTARY 
	? "Infantil"
	: activity.getEducativeLevel() == EducativeLevel.PRESCHOOL
		? "Juvenil"
		: "Adolescente";
%>

<details class="activity_details">
	<summary><%= activity.getActivityName() %></summary>
	<div class="details_div">
		<p>Nivel Educativo: <%= educativeLevel %></p>
				<p>Horario: <%= activity.getTimeSlot() == TimeSlot.AFTERNOON ? "Tarde" : "Mañana" %></p>
		<h4>Monitores</h4>
		<ul>
			<%
				List<MonitorDTO> monitors = Container.getInstance().getCampsManager().getMonitorsOfAnActivity(activity);
				for (MonitorDTO monitor : monitors) {
				%>
					<li>
						<%= monitor.getId() %>, 
						<%= monitor.getFirstName() %> <%= monitor.getLastName() %>
						<%= monitor.isSpecialEducator() ? ", Educador especial" : "" %>
					</li>
				<%
				}
			%>
		</ul>
	</div>
</details>