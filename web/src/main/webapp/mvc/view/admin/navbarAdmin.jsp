<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
String selectedStr = request.getParameter("selected") != null ? request.getParameter("selected") : "";
boolean activityManager = false;
boolean campsManager = false;
boolean monitorsManager = false;
boolean assistantsManager = false;


if(selectedStr.equals("activityManager")){
	activityManager = true;
} else if(selectedStr.equals("campsManager")) {
	campsManager = true;
} else if (selectedStr.equals("monitorsManager")) {
	monitorsManager = true;
} else if(selectedStr.equals("assistantsManager")) {
	assistantsManager = true;
}
%>
    
<aside class="navbar">
  <ul class="navbar_ul">
    <li class="navbar_li  <%= campsManager ? "navbar_li_selected"  : "" %>" ><a class="navbar_a" href="/web/campsManager">Campamentos</a></li>
    <li class="navbar_li  <%= activityManager ? "navbar_li_selected"  : "" %>"><a class="navbar_a" href="/web/activitiesManager">Actividades</a></li>
    <li class="navbar_li  <%= monitorsManager ? "navbar_li_selected"  : "" %>"><a class="navbar_a" href="/web/monitorsManager">Monitores</a></li>
    <li class="navbar_li  <%= assistantsManager ? "navbar_li_selected"  : "" %>"><a class="navbar_a" href="/web/assistantsManager">Asistentes</a></li>
  </ul>
</aside>