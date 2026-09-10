<%@page import="business.dtos.InscriptionDTO"%>
<%@page import="display.web.javabean.CampBean"%>
<%@page import="display.web.javabean.ActivityBean"%>
<%@page import="business.enums.TimeSlot"%>
<%@page import="business.dtos.ActivityDTO"%>
<%@page import="display.web.Container"%>
<%@page import="business.dtos.MonitorDTO"%>
<%@page import="business.utilities.Utils"%>
<%@page import="business.enums.EducativeLevel"%>
<%@page import="business.dtos.CampDTO"%>
<%@page import="java.util.List"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<jsp:useBean  id="listOfInscriptions" scope="session" class="display.web.javabean.ListOfInscriptionsBean"></jsp:useBean>

<ul class="list_of_camps_ul">
	<%
	List<InscriptionDTO> inscriptionDTOs = listOfInscriptions.getInscriptions();
	
	for(InscriptionDTO inscription : inscriptionDTOs) {
		CampDTO camp = Container.getInstance().getCampsManager().getCamp(inscription.getCampId());
		CampBean campBean = new CampBean();
		campBean.setCamp(camp);
		int nInscriptions = Container.getInstance().getInscriptionManager().getNumberOfInscriptions(camp);
		int availableInscriptions = camp.getCapacity() - nInscriptions;
		campBean.setAvailableInscriptions(availableInscriptions);
		request.getSession().setAttribute("campBean", campBean);
		String cancelBtnStr = inscription.canBeCanceled() ? "true" : "false";
		%>
		<li class="camp_details_div list_of_camps_li available_camps_li">
			<jsp:include page="./campDetails.jsp">
				<jsp:param name="cancelBtn" value="<%= cancelBtnStr %>"/>
				<jsp:param name="inscriptionId" value="<%= inscription.getInscriptionIdentifier() %>"/>
			</jsp:include>
		</li>	
		<%
		request.getSession().setAttribute("campBean", null);
	}
	%>	
</ul>