<%@page import="display.web.javabean.CampBean"%>
<%@page import="business.enums.EducativeLevel"%>
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

<ul class="list_of_camps_ul">
	<%
	List<CampDTO> camps = listOfCampsBean.getCamps();
	
	for(CampDTO camp : camps) {
		CampBean campBean = new CampBean();
		campBean.setCamp(camp);
		int nInscriptions = Container.getInstance().getInscriptionManager().getNumberOfInscriptions(camp);
		int availableInscriptions = camp.getCapacity() - nInscriptions;
		campBean.setAvailableInscriptions(availableInscriptions);
		request.getSession().setAttribute("campBean", campBean);
		%>
		<li class="list_of_camps_li">
			<jsp:include page="./campDetails.jsp">
				<jsp:param name="cancelBtn" value="false"/>
			</jsp:include>
		</li>	
		<%
		request.getSession().setAttribute("campBean", null);
	}
	%>	
</ul>