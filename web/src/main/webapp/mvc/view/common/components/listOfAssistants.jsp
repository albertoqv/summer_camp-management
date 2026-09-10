<%@page import="business.dtos.MonitorDTO"%>
<%@page import="business.enums.EducativeLevel"%>
<%@page import="business.dtos.ActivityDTO"%>
<%@page import="java.util.List"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.dtos.AssistantDTO"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="display.web.javabean.AssistantInfoBean"%>
<%@page import="business.enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="display.web.Container" %>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="listOfCampsBean" scope="session" class="display.web.javabean.ListOfCampsBean"></jsp:useBean>

<table>
	<thead>
		<tr>
			<th>DNI</th>
			<th>Nombre</th>
			<th>Apellidos</th>
			<th>Edad</th>
			<th>Requiere Atención Especial</th>
		</tr>
	</thead>
	<tbody>
		<%
		List<AssistantDTO> assistantDTOs = Container.getInstance().getAssistantsManager().getListOfRegisteredAssistant();
		
		for(AssistantDTO assistant : assistantDTOs) {
			long assistantAge = Utils.yearsBetween(assistant.getBirthDate(), Utils.getCurrentDate());
			String educativeLevelStr = "";
			
			if ((assistantAge >= 4) && (assistantAge <= 6)) {
				educativeLevelStr = "(Infantil)";
			} else if ((assistantAge >= 7) && (assistantAge <= 12)) {
				educativeLevelStr = "(Juvenil)";
			} else if ((assistantAge >= 13) && (assistantAge <= 17)) {
				educativeLevelStr = "(Adolescente)";
			} else {
				educativeLevelStr = "(No valido)";
			}
		
		%>
		
			<tr>
				<td><%= assistant.getId() %></td>
				<td><%= assistant.getFirstName() %></td>
				<td><%= assistant.getLastName() %></td>
				<td><%= Utils.getStringDate(assistant.getBirthDate()) %> <%= educativeLevelStr %></td>
				<td><%= assistant.isRequireSpecialAttention() ? "Si" : "No" %></td>
			</tr>	
		<%
		}
		%>
	</tbody>
</table>