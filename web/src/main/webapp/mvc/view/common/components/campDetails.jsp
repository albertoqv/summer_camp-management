<%@page import="display.web.javabean.ActivityBean"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.ActivityDTO"%>
<%@page import="java.util.List"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.enums.EducativeLevel"%>
<%@page import="business.dtos.CampDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="campBean" scope="session" class="display.web.javabean.CampBean"></jsp:useBean>

<%
CampDTO camp = campBean.getCamp();

String educativeLevel = camp.getEducativeLevel() == EducativeLevel.ELEMENTARY 
	? "Infantil"
	: camp.getEducativeLevel() == EducativeLevel.PRESCHOOL
		? "Juvenil"
		: "Adolescente";

String ppalMonitor =  camp.getPrincipalMonitor().getFirstName() + " " + camp.getPrincipalMonitor().getLastName();
String specialMonitor = camp.getSpecialMonitor() != null
						? camp.getSpecialMonitor().getFirstName() + " " + camp.getSpecialMonitor().getLastName()
						: "No";

boolean cancelBtn = request.getParameter("cancelBtn").equals("true") ? true : false;

%>

<% 
if(cancelBtn) {
	%>
	<form action="/web/deleteInscription" method="post">
	<%			
}
%>
	<details>
		<summary>
			<div>
				#<%= camp.getCampID() %>, 
				Fecha de inicio: <span class="camp_details_start_date"><%= Utils.getStringDate(camp.getStart()) %></span>, 
				Nivel educativo: <span class="camp_details_educative_level"><%= educativeLevel %></span>,
				Plazas disponibles: <span class="camp_details_available_inscriptions"><%= campBean.getAvailableInscriptions() %></span>
			</div>
			<% 
			if(cancelBtn) {
				%>
					<input type="hidden" name="inscriptionId" value="<%= request.getParameter("inscriptionId") %>" />
					<input class="btn cancel_btn" type="submit" value="Cancelar" />
				<%			
			}
			%>
		</summary>
		<div class="details_div">
			<p>Monitor principal: <%= ppalMonitor %></p>
			<p>Monitor especial: <%= specialMonitor %></p>
		 	<h3>Actividades</h3>
			<ul>
				<%
				List<ActivityDTO> activities = Container.getInstance().getCampsManager().getActivitiesOfACamp(camp);
				
				for(ActivityDTO activity : activities) {
					ActivityBean activityBean = new ActivityBean();
					activityBean.setActivity(activity);
					request.getSession().setAttribute("activityBean", activityBean);
					%>							
					<li>
						<jsp:include page="./activityDetails.jsp"></jsp:include>
					</li>
					<%
						request.getSession().setAttribute("activityBean", null);
				}
				%>
			</ul>
		</div>
	</details>
	
<% 
if(cancelBtn) {
	%>
	</form>		
	<%			
}
%>